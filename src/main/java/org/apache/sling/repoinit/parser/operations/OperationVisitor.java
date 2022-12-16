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

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface OperationVisitor {
    void visitCreateGroup(CreateGroup g);
    void visitDeleteGroup(DeleteGroup g);
    void visitCreateUser(CreateUser u);
    void visitDeleteUser(DeleteUser u);
    void visitCreateServiceUser(CreateServiceUser s);
    void visitDeleteServiceUser(DeleteServiceUser s);
    void visitSetAclPrincipal(SetAclPrincipals s);
    void visitSetAclPaths(SetAclPaths s);
    void visitSetAclPrincipalBased(SetAclPrincipalBased s);
    default void visitRemoveAcePrincipal(RemoveAcePrincipals s) { throw new UnsupportedOperationException(); }
    default void visitRemoveAcePaths(RemoveAcePaths s) { throw new UnsupportedOperationException(); }
    default void visitRemoveAcePrincipalBased(RemoveAcePrincipalBased s) { throw new UnsupportedOperationException(); }
    void visitCreatePath(CreatePath cp);
    void visitCreateNode(CreateNode cn);
    void visitRegisterNamespace(RegisterNamespace rn);
    void visitRegisterNodetypes(RegisterNodetypes b);
    void visitRegisterPrivilege(RegisterPrivilege rp);
    void visitDisableServiceUser(DisableServiceUser dsu);
    void visitAddGroupMembers(AddGroupMembers am);
    void visitRemoveGroupMembers(RemoveGroupMembers rm);
    void visitSetProperties(SetProperties sp);
    default void visitDeleteAclPrincipals(DeleteAclPrincipals s) { throw new UnsupportedOperationException(); }
    default void visitDeleteAclPaths(DeleteAclPaths s) { throw new UnsupportedOperationException(); }
    default void visitDeleteAclPrincipalBased(DeleteAclPrincipalBased s) { throw new UnsupportedOperationException(); }
    default void visitAddMixins(AddMixins s) { throw new UnsupportedOperationException(); }
    default void visitRemoveMixins(RemoveMixins s) { throw new UnsupportedOperationException(); }

}
