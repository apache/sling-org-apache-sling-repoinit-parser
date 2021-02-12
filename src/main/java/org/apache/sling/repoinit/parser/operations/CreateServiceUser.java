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
public class CreateServiceUser extends ServiceUserOperation {

    public CreateServiceUser(String username) {
        this(username, null);
    }

    public CreateServiceUser(String username, WithPathOptions wph) {
        super(username, wph);
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitCreateServiceUser(this);
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.getParametersDescription());

        final String forced = isForcedPath() ? "forced " : "";
        if (getPath() != null) {
            sb.append(" with " + forced + "path ").append(getPath());
        }

        return sb.toString();
    }
}
