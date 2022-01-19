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

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.repoinit.parser.impl.QuotableStringUtil;
import org.junit.Test;

public class QuotableStringUtilTest {

    @Test
    public void testAllAlphaNumeric() {
        assertEquals("administrators", QuotableStringUtil.forRepoInitString("administrators"));
        assertEquals("administrators1", QuotableStringUtil.forRepoInitString("administrators1"));
    }

    @Test
    public void testWithCommonSeparators() {
        assertEquals("sling-search-path-reader", QuotableStringUtil.forRepoInitString("sling-search-path-reader"));
        assertEquals("sling__search_path-reader", QuotableStringUtil.forRepoInitString("sling__search_path-reader"));
    }

    @Test
    public void testWithDomain() {
        assertEquals("auser@adomain.com", QuotableStringUtil.forRepoInitString("auser@adomain.com"));
    }

    @Test
    public void testWithWhitespace() {
        assertEquals("\"A User\"", QuotableStringUtil.forRepoInitString("A User"));
        assertEquals("\"A\tGroup\"", QuotableStringUtil.forRepoInitString("A\tGroup"));
    }

    @Test
    public void testWithNewLines() {
        assertEquals("\"A\nUser\"", QuotableStringUtil.forRepoInitString("A\nUser"));
        assertEquals("\"A\rGroup\"", QuotableStringUtil.forRepoInitString("A\rGroup"));
    }

    @Test
    public void testWithBackslashOnly() {
        assertEquals("\"A\\User\"", QuotableStringUtil.forRepoInitString("A\\User"));
    }

    @Test
    public void testList() {
        List<String> input = new ArrayList<>();
        input.add("a-user");
        input.add("A Group");

        List<String> expected = new ArrayList<>();
        expected.add("a-user");
        expected.add("\"A Group\"");

        assertEquals(expected, QuotableStringUtil.forRepoInitString(input));
    }

}
