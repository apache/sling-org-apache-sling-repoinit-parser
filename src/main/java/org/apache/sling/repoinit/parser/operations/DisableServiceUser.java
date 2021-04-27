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

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
/** The class name is for historical reasons but this actually manages
 *  both service and regular users.
 */
public class DisableServiceUser extends ServiceUserOperation {
    private final String reason;
    private boolean isServiceUser;
    
    public DisableServiceUser(String username, String reason) {
        super(username, null);
        this.reason = cleanupQuotedString(reason);
        if(this.reason == null || this.reason.length() == 0) {
            throw new IllegalArgumentException("A non-empty reason is required");
        }
    }

    public void setServiceUser(boolean b) {
        isServiceUser = b;
    }

    @Override
    public String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.getParametersDescription());
        if(isServiceUser) {
            sb.append(" (service user)");
        } else {
            sb.append(" (regular user)");
        }
        if(reason!=null) {
            sb.append(" : ");
            sb.append(reason);
        }
        return sb.toString();
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        final String userType = isServiceUser ? "service " : "";
        return String.format("disable %s user %s : %s%n", userType, username, escapeQuotes(reason));
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitDisableServiceUser(this);
    }

    public String getReason() {
        return reason;
    }

    public boolean isServiceUser() {
        return isServiceUser;
    }
}
