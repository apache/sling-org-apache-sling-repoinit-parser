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
package org.apache.sling.repoinit.parser;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class RepoInitParsingException extends Exception {
    private static final long serialVersionUID = 1L;

    private final int line;
    private final int column;

    public RepoInitParsingException(String reason, Throwable cause) {
        super(reason, cause);
        this.line = -1;
        this.column = -1;
    }

    public RepoInitParsingException(Throwable cause) {
        this(cause, -1, -1);
    }

    public RepoInitParsingException(Throwable cause, int line, int column) {
        super(cause);
        this.line = line;
        this.column = column;
    }

    /**
     *
     * @return the line where the issue occurred or -1 if not known
     */
    public int getLine() {
        return line;
    }

    /**
     *
     * @return the column where the issue occurred or -1 if not known
     */
    public int getColumn() {
        return column;
    }
}
