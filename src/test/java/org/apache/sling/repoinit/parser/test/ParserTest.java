/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.repoinit.parser.test;

import java.io.IOException;
import java.util.Collection;

import org.apache.sling.repoinit.parser.RepoInitParsingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/** Test the parser using our test-* input/expected output files.
 *  The code of this class doesn't contain any actual tests, it
 *  just looks for test-*.txt files, parses them and verifies the
 *  results according to the test-*-output.txt files.
 */
@RunWith(Parameterized.class)
public class ParserTest {

    private final ParserTestCase tc;

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() throws IOException {
        return ParserTestCase.buildTestData();
    }

    public ParserTest(ParserTestCase tc) {
        this.tc = tc;
    }

    @Test
    public void checkResult() throws RepoInitParsingException, IOException {
        ParserTestCase.validate(tc.input, tc.expected, tc);
    }
}
