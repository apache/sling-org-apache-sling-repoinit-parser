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

package org.apache.sling.repoinit.parser.operations;

import org.apache.sling.repoinit.parser.impl.WithPathOptions;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class CreateUser extends OperationWithPathOptions {
    private final String username;
    private final String passwordEncoding;
    private final String password;

    /** Operation that creates a user.
     * @param username the name of the user to create
     * @param passwordEncoding optional encoding for the supplied password
     * @param password optional password
     */
    public CreateUser(String username, String passwordEncoding, String password) {
        this(username, passwordEncoding, password, null);
    }

    /** Operation that creates a user.
     * @param username the name of the user to create
     * @param passwordEncoding optional encoding for the supplied password
     * @param password optional password
     * @param wpopt optional path
     */
    public CreateUser(String username, String passwordEncoding, String password, WithPathOptions wpopt) {
        super(wpopt);
        this.username = username;
        this.passwordEncoding = passwordEncoding;
        this.password = password;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitCreateUser(this);
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder(username);
        final String forced = isForcedPath() ? "forced " : "";
        if (getPath() != null) {
            sb.append(" with " + forced + "path ").append(getPath());
        }
        if(password != null) {
            if(passwordEncoding == null) {
                sb.append(" (with password)");
            } else {
                sb.append(" (with encoded password)");
            }
        }
        return sb.toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getPasswordEncoding() {
        return passwordEncoding;
    }
}
