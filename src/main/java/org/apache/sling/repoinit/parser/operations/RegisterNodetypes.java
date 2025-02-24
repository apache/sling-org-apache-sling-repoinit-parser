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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

/** An embedded block of text */
@ProviderType
public class RegisterNodetypes extends Operation {
    private final String cndStatements;

    /** Optional prefix used at the beginning of CND lines,
     *  to avoid conflicts with Sling provisioning
     *  model parser. If present at the beginning of CND lines,
     *  this string is removed.
     */
    public static final String CND_OPTIONAL_PREFIX = "<< ";

    public RegisterNodetypes(String cndStatements) {
        this.cndStatements = new LinePrefixCleaner().removePrefix(CND_OPTIONAL_PREFIX, cndStatements);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":\n" + getParametersDescription();
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getCndStatements());
        return sb.toString();
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        try (Formatter formatter = new Formatter()) {
            for (String nodetypeRegistrationSentence :
                    generateRepoInitLines(new BufferedReader(new StringReader(cndStatements)))) {
                formatter.format("%s%n", nodetypeRegistrationSentence);
            }
            return formatter.toString();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    @NotNull
    public static List<String> generateRepoInitLines(@NotNull BufferedReader rawLines) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("register nodetypes");
        lines.add("<<===");

        String raw;
        while ((raw = rawLines.readLine()) != null) {
            if (raw.isEmpty()) {
                lines.add("");
            } else {
                lines.add("<< " + raw);
            }
        }
        lines.add("===>>");
        return lines;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitRegisterNodetypes(this);
    }

    public String getCndStatements() {
        return cndStatements;
    }
}
