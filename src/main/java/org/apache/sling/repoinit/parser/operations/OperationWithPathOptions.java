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
package org.apache.sling.repoinit.parser.operations;

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
abstract class OperationWithPathOptions extends Operation {
    private final WithPathOptions wpopt;

    OperationWithPathOptions(WithPathOptions wpopt) {
        this.wpopt = wpopt == null ? new WithPathOptions(null, false) : wpopt;
    }

    public String getPath() {
        return wpopt.path;
    }

    public boolean isForcedPath() {
        return wpopt.forcedPath;
    }

    @NotNull
    String asRepoInitString(@NotNull String type, @NotNull String name) {
        String path = wpopt.path;
        if (path == null || path.isEmpty()) {
            return String.format("create %s %s%n", type, name);
        } else {
            String forced = (wpopt.forcedPath) ? "forced " : "";
            return String.format("create %s %s with %spath %s%n", type, name, forced, path);
        }
    }
}
