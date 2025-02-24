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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.sling.repoinit.parser.impl.ParseException;
import org.apache.sling.repoinit.parser.impl.RepoInitParserImpl;
import org.apache.sling.repoinit.parser.operations.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class QuotableStringParserTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() throws IOException {

        List<Object[]> testCases = new ArrayList<>();

        testCases.add(new Object[] {"backslashes", "create group \"a\\group\"", "CreateGroup a\\group", true});
        testCases.add(new Object[] {"space", "create group \"a group\"", "CreateGroup a group", true});
        testCases.add(new Object[] {
            "unnecessarily quoted", "create group \"unnecessarily-quoted\"", "CreateGroup unnecessarily-quoted", false
        });

        return testCases;
    }

    private final String name;
    private final String statement;
    private final String expected;
    private final boolean reproducable;

    public QuotableStringParserTest(String name, String statement, String expected, boolean reproducable) {
        this.name = name;
        this.statement = statement;
        this.expected = expected;
        this.reproducable = reproducable;
    }

    @Test
    public void testParse() throws ParseException, IOException {
        Operation op = parseSingleStatement(statement);
        assertEquals("Test " + name + " failed", expected, op.toString());
    }

    @Test
    public void testReproduceParse() throws ParseException, IOException {
        Operation op = parseSingleStatement(statement);
        String expected = String.format("%s%n", statement);
        if (reproducable) {
            assertEquals(expected, op.asRepoInitString());
        } else {
            assertNotEquals(expected, op.asRepoInitString());
        }
    }

    private Operation parseSingleStatement(String statement) throws ParseException, IOException {
        final StringReader r = new StringReader(statement);
        try {
            List<Operation> operations = new RepoInitParserImpl(r).parse();
            assertEquals(1, operations.size());
            return operations.get(0);
        } finally {
            r.close();
        }
    }
}
