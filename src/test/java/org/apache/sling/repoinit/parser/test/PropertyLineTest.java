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

import java.util.Arrays;
import java.util.Date;

import org.apache.jackrabbit.util.ISO8601;
import org.apache.sling.repoinit.parser.operations.PropertyLine;
import org.apache.sling.repoinit.parser.impl.ParseException;
import org.junit.Test;

public class PropertyLineTest {

    @Test
    public void testDefaultPropertyType() throws ParseException {
        final PropertyLine p = new PropertyLine("someName", null, null, false);
        assertEquals(PropertyLine.PropertyType.String, p.getPropertyType());
    }

    @Test
    public void testValidPropertyType() throws ParseException {
        final PropertyLine p = new PropertyLine("someName", "Boolean", null, false);
        assertEquals(PropertyLine.PropertyType.Boolean, p.getPropertyType());
    }

    @Test(expected = ParseException.class)
    public void testInvalidPropertyType() throws ParseException {
        new PropertyLine("someName", "invalidTypeName", null, false);
    }

    @Test
    public void testValidDateFormat() throws ParseException {
        final Date now = new Date();
        final String [] value = { ISO8601.format(now) };
        final PropertyLine p = new PropertyLine("someName", "Date", Arrays.asList(value), false);
        assertEquals(ISO8601.parse(value[0]), p.getPropertyValues().get(0));
    }

    @Test(expected=ParseException.class)
    public void testInvalidDateFormat() throws ParseException {
        final String [] notAnIsoDate = { "really not a date" };
        new PropertyLine("someName", "Date", Arrays.asList(notAnIsoDate), false);
    }

    @Test
    public void testInvalidDateFormatAsString() throws ParseException {
        final String [] notAnIsoDate = { "2020-03-24" };
        final PropertyLine p = new PropertyLine("someName", "String", Arrays.asList(notAnIsoDate), false);
        assertEquals(notAnIsoDate[0], p.getPropertyValues().get(0));
    }
}
