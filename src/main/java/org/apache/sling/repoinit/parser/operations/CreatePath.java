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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class CreatePath extends Operation {
    private List<PathSegmentDefinition> pathDef;
    private final String defaultPrimaryType;
    
    public CreatePath(String defaultPrimaryType) {
        this.pathDef = new ArrayList<>();
        this.defaultPrimaryType = defaultPrimaryType;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + pathDef;
    }
    
    @Override
    protected String getParametersDescription() {
        return pathDef.toString();
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        String defaultTypeStr = (defaultPrimaryType == null) ? "" : "("+defaultPrimaryType+") ";
        StringBuilder sb = new StringBuilder();
        for (PathSegmentDefinition psd : getDefinitions()) {
            sb.append("/").append(psd.getSegment());
            List<String> mixins = psd.getMixins();
            if (!psd.isDefaultPrimary() || !mixins.isEmpty()) {
                sb.append("(");
                if (!psd.isDefaultPrimary()) {
                    sb.append(psd.getPrimaryType());
                }
                if (!mixins.isEmpty()) {
                    sb.append(" mixin ").append(listToString(mixins));
                }
                sb.append(")");
            }
        }
        return String.format("create path %s%s%n", defaultTypeStr, sb.toString());
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitCreatePath(this);
    }

    public void addSegment(String path, String primaryType) {
        addSegment(path, primaryType, null);
    }

    public void addSegment(String path, String primaryType, List<String> mixins) {
        // We might get a path like /var/discovery, in which case
        // the specified primary type applies to the last
        // segment only
        final String [] segments = path.split("/");
        for(int i=0; i < segments.length; i++) {
            if(segments[i].length() == 0) {
                continue;
            }
            String pt = defaultPrimaryType;
            boolean isDefaultPrimary = true;
            List<String> ms = null;
            if(i == segments.length -1) {
                if (primaryType != null) {
                    pt = primaryType;
                    isDefaultPrimary = false;
                }
                if (mixins != null && ! mixins.isEmpty()) {
                    ms = mixins;
                }
            }
            pathDef.add(new PathSegmentDefinition(segments[i], pt, ms, isDefaultPrimary));
        }
    }
    
    public List<PathSegmentDefinition> getDefinitions() {
        return Collections.unmodifiableList(pathDef);
    }
}
