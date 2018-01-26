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
import java.util.Iterator;
import java.util.List;

public class RegisterPrivilege extends Operation {

    private final String privilegeName;
    private final boolean isAbstract;

    public String getPrivilegeName() {
        return privilegeName;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public List<String> getDeclaredAggregateNames() {
        return declaredAggregateNames;
    }

    private final List<String> declaredAggregateNames;

    public RegisterPrivilege(String privilegeName, boolean isAbstract, List<String> declaredAggregateNames) {
        this.privilegeName = privilegeName;
        this.isAbstract = isAbstract;
        this.declaredAggregateNames = Collections.unmodifiableList(new ArrayList<>(declaredAggregateNames));
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitRegisterPrivilege(this);
    }

    @Override
    protected String getParametersDescription() {
        return this.privilegeName + "," + this.isAbstract + "," + this.declaredAggregateNames;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("register ");
        if (this.isAbstract) {
            builder.append("abstract ");
        }
        builder.append("privilege ");
        builder.append(this.privilegeName);

        if (!this.declaredAggregateNames.isEmpty()) {
            builder.append(" with ");
            Iterator<String> iter = this.declaredAggregateNames.iterator();
            builder.append(iter.next());
            while (iter.hasNext()) {
                builder.append(',').append(iter.next());
            }
        }

        return builder.toString();
    }
}
