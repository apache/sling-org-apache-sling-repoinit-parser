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

import java.util.List;

@ProviderType
public abstract class Operation {
    public abstract void accept(OperationVisitor v);
    public static final String DQUOTE = "\"";
    
    protected abstract String getParametersDescription();

    public abstract String asRepoInitString();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getParametersDescription();
    }
    
    public static String cleanupQuotedString(String s) {
        if(s == null) {
            return null;
        }
        s = s.trim();
        if(s.length() == 0) {
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
}
