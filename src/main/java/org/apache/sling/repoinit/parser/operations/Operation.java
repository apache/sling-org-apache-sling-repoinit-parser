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

import java.util.List;
import java.util.stream.Collectors;

import org.apache.sling.repoinit.parser.impl.QuotableStringUtil;
import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public abstract class Operation {
    public abstract void accept(OperationVisitor v);

    public static final String DQUOTE = "\"";

    protected abstract String getParametersDescription();

    /**
     * Converts this operation instance to a RepoInit string representation
     * including the current operation parameters. The representation must be
     * parsable back into an equivalent operation and must end with a OS-compatible
     * line separator.
     *
     * @return the repoinit string for the operation
     */
    @NotNull
    public abstract String asRepoInitString();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getParametersDescription();
    }

    public static String cleanupQuotedString(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        if (s.length() == 0) {
            return null;
        }
        return s;
    }

    @NotNull
    static String escapeQuotes(@NotNull String s) {
        String esc = s.replace("\\", "\\\\");
        String escapequotes = esc.replace("\"", "\\\"");
        return "\"" + escapequotes + "\"";
    }

    @NotNull
    static String listToString(@NotNull List<String> list) {
        if (list.isEmpty()) {
            return "";
        } else {
            return String.join(",", list);
        }
    }

    @NotNull
    static String pathsToString(@NotNull List<String> paths) {
        return listToString(paths.stream()
                .map(s -> {
                    if (s.startsWith(":") && s.contains("#")) {
                        String func = s.substring(1, s.indexOf(":", 1));
                        String s2 = s.substring(func.length() + 2, s.lastIndexOf('#'));
                        if ("authorizable".equals(func)) {
                            s2 = QuotableStringUtil.forRepoInitString(s2);
                        }
                        String trailingPath = (s.endsWith("#")) ? "" : s.substring(s.indexOf("#") + 1);
                        return func + "(" + s2 + ")" + trailingPath;
                    } else {
                        return s;
                    }
                })
                .collect(Collectors.toList()));
    }
}
