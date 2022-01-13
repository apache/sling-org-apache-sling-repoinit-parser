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
import java.util.Collection;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import org.apache.sling.repoinit.parser.impl.QuotableStringUtil;
import org.apache.sling.repoinit.parser.operations.AclLine.Action;
import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

/** Base class for operations that group AclLines */
@ProviderType 
abstract class AclGroupBase extends Operation {
    /**
     * Supported ACL options
     */
    public static final String ACL_OPTION_MERGE = "merge";
    public static final String ACL_OPTION_MERGE_PRESERVE = "mergePreserve";

    private final List<AclLine> lines;
    private final List<String> aclOptions;
    
    protected AclGroupBase(List<AclLine> lines) {
        this(lines,new ArrayList<String>());
    }
    protected AclGroupBase(List<AclLine> lines, List<String> aclOptions) {
        this.lines = Collections.unmodifiableList(lines);
        this.aclOptions = Collections.unmodifiableList(aclOptions);
    }
    
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        for(AclLine line : lines) {
            sb.append("\n  ").append(line.toString());
        }
        return sb.toString(); 
    }
    
    public Collection<AclLine> getLines() {
        return lines;
    }

    public List<String> getOptions() {
        return aclOptions;
    }

    String asRepoInit(@NotNull String topLine, boolean hasPathLines) {
        try (Formatter formatter = new Formatter()) {
            formatter.format("%s",topLine);
            for (AclLine line : lines) {
                String action = actionToString(line.getAction());
                String privileges = privilegesToString(line.getAction(), line.getProperty(AclLine.PROP_PRIVILEGES));
                String onOrFor;
                if (hasPathLines) {
                    String pathStr = pathsToString(line.getProperty(AclLine.PROP_PATHS));
                    onOrFor = (pathStr.isEmpty()) ? "" : " on " + pathStr;
                } else {
                    onOrFor = " for " + listToString(
                            QuotableStringUtil.forRepoInitString(line.getProperty(AclLine.PROP_PRINCIPALS)));
                }
                formatter.format("    %s %s%s%s%s%n", action, privileges, onOrFor,
                        nodetypesToString(line.getProperty(AclLine.PROP_NODETYPES)),
                        restrictionsToString(line.getRestrictions()));
            }
            formatter.format("end%n");
            return formatter.toString();
        }
    }

    @NotNull
    String getAclOptionsString() {
        return (aclOptions.isEmpty()) ? "" : " (ACLOptions="+ listToString(aclOptions)+")";
    }

    @NotNull
    static String privilegesToString(@NotNull AclLine.Action action, @NotNull List<String> privileges) {
        return (action == AclLine.Action.REMOVE_ALL) ? "*" : listToString(privileges);
    }

    @NotNull
    private static String nodetypesToString(@NotNull List<String> nodetypes) {
        return (nodetypes.isEmpty()) ? "" : " nodetypes " + listToString(nodetypes);
    }

    @NotNull
    private static String restrictionsToString(@NotNull List<RestrictionClause> restrictionClauses) {
        StringBuilder sb = new StringBuilder();
        for (RestrictionClause rc : restrictionClauses) {
            sb.append(" restriction(").append(rc.getName());
            for (String v : rc.getValues()) {
                sb.append(",").append(v);
            }
            sb.append(')');
        }
        return sb.toString();
    }

    @NotNull
    private static String actionToString(@NotNull AclLine.Action action) {
        if(action.equals(Action.REMOVE_ALL)) {
            return Action.REMOVE.toString().toLowerCase();
        }
        return action.toString().toLowerCase();
    }
}
