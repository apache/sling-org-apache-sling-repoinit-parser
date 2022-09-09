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


import java.util.Formatter;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class RemoveMixins extends Operation {
    private final List<String> paths;
    private final List<String> mixins;

    public RemoveMixins(List<String> mixins, List<String> paths) {
        this.mixins = mixins;
        this.paths = paths;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitRemoveMixins(this);
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        if (mixins != null) {
            sb.append(String.join(",", mixins));
        }
        if (paths != null) {
            sb.append(" from ").append(String.join(",", paths));
        }
        return sb.toString();
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        // FIXME: see SLING-10238 for type and quoted values that cannot be generated
        //        exactly as they were originally defined in repo-init
        try (Formatter formatter = new Formatter()) {
            formatter.format("remove mixin %s from %s%n", listToString(mixins), pathsToString(paths));
            return formatter.toString();
        }
    }

    public List<String> getPaths() {
        return paths;
    }

    public List<String> getMixins () {return mixins;}


}
