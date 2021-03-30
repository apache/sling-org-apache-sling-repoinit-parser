/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.sling.repoinit.parser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.sling.repoinit.parser.RepoInitParsingException;
import org.apache.sling.repoinit.parser.impl.RepoInitParserService;
import org.apache.sling.repoinit.parser.operations.Operation;
import org.apache.sling.repoinit.parser.operations.OperationVisitor;

public class ParserTestCase implements Closeable {
    public final Reader input;
    final String inputFilename;
    public final InputStream expected;
    final String outputFilename;

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String PREFIX = "/testcases/test-";
    private static final int MAX_TEST_INDEX = 100;
    private static final int EXPECTED_TEST_COUNT = 30;

    @Override
    public String toString() {
        return inputFilename;
    }

    private ParserTestCase(int index) throws IOException {
        inputFilename = PREFIX + index + ".txt";
        final InputStream is = getClass().getResourceAsStream(inputFilename);
        if ( is != null ) {
            input = new InputStreamReader(is, "UTF-8");
        } else {
            input = null;
        }
        outputFilename = PREFIX + index + "-output.txt";
        expected = getClass().getResourceAsStream(outputFilename);
    }

    public static ParserTestCase build(int index) throws IOException {
        final ParserTestCase result = new ParserTestCase(index);
        if(result.input == null || result.expected == null) {
            return null;
        }
        return result;
    }

    public void close() {
        try {
            input.close();
        } catch(IOException ignored) {
        }
        try {
            expected.close();
        } catch(IOException ignored) {
        }
    }

    public static void validate(Reader validateInput, InputStream validateExpected, Closeable toClose) throws RepoInitParsingException, IOException {
        try {
            final String expected = IOUtils.toString(validateExpected, DEFAULT_ENCODING).trim();
            final StringWriter sw = new StringWriter();
            final OperationVisitor v = new OperationToStringVisitor(new PrintWriter(sw));
            final List<Operation> result = new RepoInitParserService().parse(validateInput);
            for(Operation o : result) {
                o.accept(v);
            }
            sw.flush();
            String actual = sw.toString().trim();
    
            // normalize line endings to ensure tests run on windows as well
            actual = actual.replaceAll("\r\n", "\n");
    
            assertEquals(expected, actual);
        } finally {
            toClose.close();
        }
    }

    public static Collection<Object[]> buildTestData() throws IOException {
        final List<Object []> result = new ArrayList<>();
        for(int i=0; i <= MAX_TEST_INDEX; i++) {
            final ParserTestCase tc = ParserTestCase.build(i);
            if(tc != null) {
                result.add(new Object[] { tc });
            }
        }
        if(result.size() < EXPECTED_TEST_COUNT) {
            fail("Expected at least " + EXPECTED_TEST_COUNT + " test cases but got only " + result.size());
        }
        return result;
    }
}