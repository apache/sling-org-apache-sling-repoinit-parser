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

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class DeleteGroup extends Operation {
    private final String groupname;

    /**
     * Operation that deletes a group.
     * 
     * @param groupname the name of the group to delete
     */
    public DeleteGroup(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitDeleteGroup(this);
    }

    @Override
    protected String getParametersDescription() {
        return groupname;
    }

    public String getGroupname() {
        return groupname;
    }
}
