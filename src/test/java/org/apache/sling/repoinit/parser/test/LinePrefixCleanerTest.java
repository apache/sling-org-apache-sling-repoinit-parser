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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.sling.repoinit.parser.operations.LinePrefixCleaner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LinePrefixCleanerTest {
    private final String prefix;
    private final String input;
    private final String expected;

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        final List<Object[]> result = new ArrayList<Object[]>();
        result.add(new Object[] {"", "", ""});
        result.add(new Object[] {"<< ", "", ""});
        result.add(new Object[] {"", "One\ntwo", "One\ntwo"});
        result.add(new Object[] {"<< ", "<< Three\n<< four", "Three\nfour"});
        result.add(new Object[] {"<", "<Five\nsix\n< seven", "Five\nsix\n seven"});
        return result;
    }

    public LinePrefixCleanerTest(String prefix, String input, String expected) {
        this.prefix = prefix;
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void cleanup() {
        final LinePrefixCleaner c = new LinePrefixCleaner();
        assertEquals(expected, c.removePrefix(prefix, input));
    }
}
