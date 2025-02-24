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


import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.jackrabbit.util.ISO8601;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class SetProperties extends Operation {
    private final List<String> paths;
    private final List<PropertyLine> lines;

    public SetProperties(List<String> paths, List<PropertyLine> lines) {
        this.paths = paths;
        this.lines = lines;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitSetProperties(this);
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        for(PropertyLine line : lines) {
            sb.append("\n  ").append(line.toString());
        }
        return sb.toString();
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        // FIXME: see SLING-10238 for type and quoted values that cannot be generated
        //        exactly as they were originally defined in repo-init
        try (Formatter formatter = new Formatter()) {
            formatter.format("set properties on %s%n", pathsToString(paths));
            for (PropertyLine line : lines) {
                String typeMultiple = line.isMultiple() ? "[]" : "";
                String type = (line.getPropertyType() == null) ? "" : "{" + line.getPropertyType().name() + typeMultiple + "}";
                String values = valuesToString(line.getPropertyValues(), line.getPropertyType());
                if (line.isDefault()) {
                    formatter.format("default %s%s to %s%n", line.getPropertyName(), type, values);
                } else {
                    formatter.format("set %s%s to %s%n", line.getPropertyName(), type, values);
                }
            }
            formatter.format("end%n");
            return formatter.toString();
        }
    }

    @NotNull
    static String valuesToString(@NotNull List<Object> values, @Nullable PropertyLine.PropertyType type) {
        List<String> strings = values.stream()
                .map(o -> {
                    if (type == null || type == PropertyLine.PropertyType.String) {
                        return escapeQuotes(Objects.toString(o, ""));
                    } else if (type == PropertyLine.PropertyType.Date) {
                        return "\"" + ISO8601.format((Calendar) o) + "\"";
                    } else {
                        return Objects.toString(o, null);
                    }
                })
                .collect(Collectors.toList());
        return listToString(strings);
    }

    public List<String> getPaths() {
        return paths;
    }

    public List<PropertyLine> getPropertyLines () {return lines;}


}
