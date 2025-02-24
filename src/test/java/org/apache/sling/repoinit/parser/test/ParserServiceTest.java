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

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.apache.sling.repoinit.parser.RepoInitParsingException;
import org.apache.sling.repoinit.parser.impl.RepoInitParserService;
import org.apache.sling.repoinit.parser.operations.CreateServiceUser;
import org.apache.sling.repoinit.parser.operations.Operation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ParserServiceTest {
    private RepoInitParserService service;

    @Before
    public void setUp() {
        service = new RepoInitParserService();
    }

    @Test
    public void noErrors() throws RepoInitParsingException {
        final Reader r = new StringReader("create service user foo");
        List<Operation> operations = service.parse(r);
        assertEquals(1, operations.size());
        assertEquals(CreateServiceUser.class, operations.get(0).getClass());
    }

    @Test
    public void syntaxError() throws RepoInitParsingException {
        final Reader r = new StringReader("not a valid statement");
        RepoInitParsingException exception = assertThrows(RepoInitParsingException.class, () -> service.parse(r));
        assertEquals(1, exception.getLine());
        assertEquals(1, exception.getColumn());
    }

    @Test
    public void syntaxErrorInSecondLine() throws RepoInitParsingException {
        final Reader r = new StringReader("create service user foo\n  not a valid statement");
        RepoInitParsingException exception = assertThrows(RepoInitParsingException.class, () -> service.parse(r));
        assertEquals(2, exception.getLine());
        assertEquals(3, exception.getColumn());
    }
}
