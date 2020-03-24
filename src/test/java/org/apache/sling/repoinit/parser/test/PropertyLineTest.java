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

import org.apache.sling.repoinit.parser.operations.PropertyLine;
import org.junit.Test;

public class PropertyLineTest {

    @Test
    public void testDefaultPropertyType() {
        final PropertyLine p = new PropertyLine("someName", null, null, false);
        assertEquals(PropertyLine.PropertyType.String, p.getPropertyType());
    }

    @Test
    public void testValidPropertyType() {
        final PropertyLine p = new PropertyLine("someName", "Boolean", null, false);
        assertEquals(PropertyLine.PropertyType.Boolean, p.getPropertyType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPropertyType() {
        new PropertyLine("someName", "invalidTypeName", null, false);
    }
}
