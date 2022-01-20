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

package org.apache.sling.repoinit.parser.operations;

import org.apache.sling.repoinit.parser.impl.RepoInitParserService;
import org.apache.sling.repoinit.parser.test.ParserTest;
import org.apache.sling.repoinit.parser.test.ParserTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.function.Supplier;

/** Similar to {@link ParserTest} but uses {@link Operation#asRepoInitString()})
 *  to rebuild the input script after parsing it, to verify that that operation
 *  returns equivalent statements.
 */
@RunWith(Parameterized.class)
public class AsRepoInitTest {

    private final Supplier<ParserTestCase> testCaseSupplier;

    @Parameters(name="{0}")
    public static Collection<Object[]> data() throws IOException {
        return ParserTestCase.buildTestDataSuppliers();
    }

    public AsRepoInitTest(String testName, Supplier<ParserTestCase> testCaseSupplier) {
        this.testCaseSupplier = testCaseSupplier;
    }

    /** Rebuild the input script using {@link Operation#asRepoInitString()}) */
    private static Reader rebuildInputScript(Reader input) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Operation o : new RepoInitParserService().parse(input)) {
            sb.append(o.asRepoInitString());
        }
        return new StringReader(sb.toString());
    }

    @Test
    public void checkResultAsRepoInit() throws Exception {
        try(ParserTestCase tc = testCaseSupplier.get()){
            ParserTestCase.validate(rebuildInputScript(tc.input), tc.expected, tc);
        }
    }

    @Test
    public void checkRepoInitStatementNewline() throws Exception {
        try (ParserTestCase tc = testCaseSupplier.get()) {
            for (Operation o : new RepoInitParserService().parse(tc.input)) {
                String repoinitStatement = o.asRepoInitString();
                assertTrue(
                        "Operation.asRepoInitString() should always end with an-OS compatible line separator. Not found for "
                                + o.toString(),
                        repoinitStatement.endsWith(System.lineSeparator()));
            }
        }
    }
}