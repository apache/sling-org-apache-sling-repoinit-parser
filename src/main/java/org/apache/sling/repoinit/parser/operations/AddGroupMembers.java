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

import java.util.List;

public class AddGroupMembers extends Operation {
    private final String groupname;
    private final List<String> members;


    /**
     * Operation that add members to a group.
     *
     * @param members   members of the group
     * @param groupname the name of the group to whch members are to be added
     */
    public AddGroupMembers(List<String> members, String groupname) {
        this.groupname = groupname;
        this.members = members;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitAddGroupMembers(this);
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append(members);
        sb.append(" in group ").append(groupname);
        return sb.toString();
    }

    public String getGroupname() {
        return groupname;
    }

    public List<String> getMembers() {
        return members;
    }
}
