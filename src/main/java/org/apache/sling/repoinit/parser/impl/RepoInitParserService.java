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
package org.apache.sling.repoinit.parser.impl;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.sling.repoinit.parser.RepoInitParser;
import org.apache.sling.repoinit.parser.RepoInitParsingException;
import org.apache.sling.repoinit.parser.operations.Operation;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/** ACL definitions parser service */
@Component(
        service = RepoInitParser.class,
        property = {Constants.SERVICE_VENDOR + "=The Apache Software Foundation"})
public class RepoInitParserService implements RepoInitParser {

    @Override
    public List<Operation> parse(final Reader r) throws RepoInitParsingException {
        // in order to avoid parsing problems with trailing comments we add a line feed at the end
        try (final Reader readerWrapper = new AddTailingLinefeedFilterReader(r)) {
            return new RepoInitParserImpl(readerWrapper).parse();
        } catch (ParseException e) {
            throw new RepoInitParsingException(e, e.currentToken.next.beginLine, e.currentToken.next.beginColumn);
        } catch (final IOException | TokenMgrError e) {
            throw new RepoInitParsingException(e);
        }
    }

    private static final class AddTailingLinefeedFilterReader extends FilterReader {

        private boolean alreadyAddedNewline;

        protected AddTailingLinefeedFilterReader(Reader in) {
            super(in);
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int result = super.read(cbuf, off, len);
            if (result == -1 && !alreadyAddedNewline) {
                cbuf[off] = '\n';
                alreadyAddedNewline = true;
                return 1;
            } else {
                return result;
            }
        }
    }
}
