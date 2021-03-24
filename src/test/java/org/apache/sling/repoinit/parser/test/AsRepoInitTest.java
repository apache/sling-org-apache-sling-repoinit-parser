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

import org.apache.sling.repoinit.parser.RepoInitParsingException;
import org.apache.sling.repoinit.parser.impl.RepoInitParserService;
import org.apache.sling.repoinit.parser.operations.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** Test the parser using our test-* input/expected output files.
 *  The code of this class doesn't contain any actual tests, it
 *  just looks for test-*.txt files, parses them and verifies the
 *  results according to the test-*-output.txt files.
 */
@RunWith(Parameterized.class)
public class AsRepoInitTest {

    private final ParserTest.TestCase tc;

    @Parameters(name="{0}")
    public static Collection<Object[]> data() throws IOException {
        final List<Object []> result = new ArrayList<>();
        for(int i=0; i < 100; i++) {
            final ParserTest.TestCase tc = ParserTest.TestCase.build(i);
            if(tc != null) {
                result.add(new Object[] { tc });
            }
        }
        return result;

    }

    public AsRepoInitTest(ParserTest.TestCase tc) {
        this.tc = tc;
    }

    @Test
    public void checkResultAsRepoInit() throws Exception {
        StringBuilder repoInit = new StringBuilder();
        final List<Operation> expectedResult;
        try {
            expectedResult = new RepoInitParserService().parse(tc.input);
            for (Operation o : expectedResult) {
                repoInit.append(o.asRepoInitString());
            }
        } finally {
            tc.close();
        }

        try {
            List<Operation> ops = new RepoInitParserService().parse(new StringReader(repoInit.toString()));
            assertEquals(expectedResult.size(), ops.size());
            for (int i = 0; i < expectedResult.size(); i++) {
                Operation expected = expectedResult.get(i);
                Operation op = ops.get(i);
                assertEquals(expected.toString(), op.toString());
            }
        } catch (RepoInitParsingException e) {
            String msg = String.format("Parsing generated repo-init %n%s%nfailed with Exception:%n%s", repoInit.toString(), e.getMessage());
            fail(msg);
        }
    }
}
