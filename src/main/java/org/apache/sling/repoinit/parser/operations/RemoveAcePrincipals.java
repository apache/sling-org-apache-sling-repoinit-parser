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

import java.util.Collections;
import java.util.List;

import org.apache.sling.repoinit.parser.impl.QuotableStringUtil;
import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

/**
 * Remove ACL statement that groups a set of AclLines that all refer to the same set of principals.
 */
@ProviderType
public class RemoveAcePrincipals extends AclGroupBase {

    private final List<String> principals;

    public RemoveAcePrincipals(List<String> principals, List<AclLine> lines) {
        super(lines, Collections.emptyList());
        this.principals = Collections.unmodifiableList(principals);
    }

    @Override
    protected String getParametersDescription() {
        final StringBuilder sb = new StringBuilder();
        sb.append(principals);
        sb.append(super.getParametersDescription());
        return sb.toString();
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        String topline =
                String.format("remove ACE for %s%n", listToString(QuotableStringUtil.forRepoInitString(principals)));
        return asRepoInit(topline, true);
    }

    public List<String> getPrincipals() {
        return principals;
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitRemoveAcePrincipal(this);
    }
}
