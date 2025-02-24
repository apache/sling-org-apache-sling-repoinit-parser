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

import java.util.UUID;

import org.apache.sling.repoinit.parser.operations.DisableServiceUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DisableServiceUserTest {

    public static final String USERNAME = "foo";

    @Test
    public void nonEmptyReason() {
        DisableServiceUser disableServiceUser = new DisableServiceUser(USERNAME, "some reason");
        assertEquals("some reason", disableServiceUser.getReason());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyReason() {
        new DisableServiceUser(USERNAME, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullReason() {
        new DisableServiceUser(USERNAME, null);
    }

    @Test
    public void accessReason() {
        final String reason = "because " + UUID.randomUUID();
        assertEquals(reason, new DisableServiceUser(USERNAME, reason).getReason());
    }

    @Test
    public void userType() {
        final String reason = "nothing";
        final boolean[] values = {false, true};
        for (boolean value : values) {
            final DisableServiceUser dsu = new DisableServiceUser(USERNAME, reason);
            dsu.setServiceUser(value);
            assertEquals(value, dsu.isServiceUser());
        }
    }
}
