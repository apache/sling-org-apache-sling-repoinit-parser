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
package org.apache.sling.repoinit.parser.test;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.apache.sling.repoinit.parser.operations.AclLine;
import org.apache.sling.repoinit.parser.operations.AddGroupMembers;
import org.apache.sling.repoinit.parser.operations.AddMixins;
import org.apache.sling.repoinit.parser.operations.CreateGroup;
import org.apache.sling.repoinit.parser.operations.CreatePath;
import org.apache.sling.repoinit.parser.operations.CreateServiceUser;
import org.apache.sling.repoinit.parser.operations.CreateUser;
import org.apache.sling.repoinit.parser.operations.DeleteAclPaths;
import org.apache.sling.repoinit.parser.operations.DeleteAclPrincipalBased;
import org.apache.sling.repoinit.parser.operations.DeleteAclPrincipals;
import org.apache.sling.repoinit.parser.operations.DeleteGroup;
import org.apache.sling.repoinit.parser.operations.DeleteServiceUser;
import org.apache.sling.repoinit.parser.operations.DeleteUser;
import org.apache.sling.repoinit.parser.operations.DisableServiceUser;
import org.apache.sling.repoinit.parser.operations.EnsureAclPrincipalBased;
import org.apache.sling.repoinit.parser.operations.EnsureNodes;
import org.apache.sling.repoinit.parser.operations.OperationVisitor;
import org.apache.sling.repoinit.parser.operations.PropertyLine;
import org.apache.sling.repoinit.parser.operations.RegisterNamespace;
import org.apache.sling.repoinit.parser.operations.RegisterNodetypes;
import org.apache.sling.repoinit.parser.operations.RegisterPrivilege;
import org.apache.sling.repoinit.parser.operations.RemoveAcePaths;
import org.apache.sling.repoinit.parser.operations.RemoveAcePrincipalBased;
import org.apache.sling.repoinit.parser.operations.RemoveAcePrincipals;
import org.apache.sling.repoinit.parser.operations.RemoveGroupMembers;
import org.apache.sling.repoinit.parser.operations.RemoveMixins;
import org.apache.sling.repoinit.parser.operations.SetAclPaths;
import org.apache.sling.repoinit.parser.operations.SetAclPrincipalBased;
import org.apache.sling.repoinit.parser.operations.SetAclPrincipals;
import org.apache.sling.repoinit.parser.operations.SetProperties;

/** OperationVisitor that dumps the operations using
 *  their toString() methods
 */
class OperationToStringVisitor implements OperationVisitor {

    private final PrintWriter out;

    OperationToStringVisitor(PrintWriter pw) {
        out = pw;
    }

    @Override
    public void visitCreateServiceUser(CreateServiceUser s) {
        out.println(s.toString());
    }

    @Override
    public void visitDeleteServiceUser(DeleteServiceUser s) {
        out.println(s.toString());
    }

    @Override
    public void visitCreateUser(CreateUser u) {
        final StringBuilder sb = new StringBuilder();
        sb.append(u.toString());
        if (u.getPassword() != null) {
            sb.append(", password=").append(u.getPassword());
        }
        if (u.getPasswordEncoding() != null) {
            sb.append(", passwordEncoding=").append(u.getPasswordEncoding());
        }
        out.println(sb.toString());
    }

    @Override
    public void visitDeleteUser(DeleteUser u) {
        out.println(u.toString());
    }

    @Override
    public void visitSetAclPrincipal(SetAclPrincipals s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for ");
        for (String p : s.getPrincipals()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitSetAclPaths(SetAclPaths s) {
        out.print(s.getClass().getSimpleName());
        out.print(" on ");
        for (String p : s.getPaths()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitSetAclPrincipalBased(SetAclPrincipalBased s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for ");
        for (String p : s.getPrincipals()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitEnsureAclPrincipalBased(EnsureAclPrincipalBased s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for ");
        for (String p : s.getPrincipals()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitRemoveAcePrincipal(RemoveAcePrincipals s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for ");
        for (String p : s.getPrincipals()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitRemoveAcePaths(RemoveAcePaths s) {
        out.print(s.getClass().getSimpleName());
        out.print(" on ");
        for (String p : s.getPaths()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitRemoveAcePrincipalBased(RemoveAcePrincipalBased s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for ");
        for (String p : s.getPrincipals()) {
            out.print(p);
            out.print(' ');
        }

        dumpAclOptions(s.getOptions());

        out.println();
        dumpAclLines(s.getLines());
    }

    @Override
    public void visitDeleteAclPrincipals(DeleteAclPrincipals s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for");
        for (String p : s.getPrincipals()) {
            out.print(' ');
            out.print(p);
        }
        out.println();
    }

    @Override
    public void visitDeleteAclPaths(DeleteAclPaths s) {
        out.print(s.getClass().getSimpleName());
        out.print(" on");
        for (String p : s.getPaths()) {
            out.print(' ');
            out.print(p);
        }
        out.println();
    }

    @Override
    public void visitDeleteAclPrincipalBased(DeleteAclPrincipalBased s) {
        out.print(s.getClass().getSimpleName());
        out.print(" for");
        for (String p : s.getPrincipals()) {
            out.print(' ');
            out.print(p);
        }
        out.println();
    }

    @Override
    public void visitCreatePath(CreatePath cp) {
        out.println(cp.toString());
        List<PropertyLine> propLines = cp.getPropertyLines();
        for (PropertyLine p : propLines) {
            out.print("  ");
            out.println(p);
        }
    }

    @Override
    public void visitEnsureNodes(EnsureNodes en) {
        out.println(en.toString());
        List<PropertyLine> propLines = en.getPropertyLines();
        for (PropertyLine p : propLines) {
            out.print("  ");
            out.println(p);
        }
    }

    @Override
    public void visitRegisterNamespace(RegisterNamespace rn) {
        out.println(rn.toString());
    }

    @Override
    public void visitRegisterNodetypes(RegisterNodetypes nt) {
        out.println(nt.toString());
    }

    @Override
    public void visitRegisterPrivilege(RegisterPrivilege rp) {
        out.println(rp.toString());
    }

    @Override
    public void visitDisableServiceUser(DisableServiceUser dsu) {
        out.println(dsu.toString());
    }

    private void dumpAclLines(Collection<AclLine> c) {
        for (AclLine line : c) {
            out.print("  ");
            out.println(line);
        }
    }

    private void dumpAclOptions(List<String> options) {
        if (options != null && options.size() > 0) {
            out.print("ACLOptions=" + options);
        }
    }

    @Override
    public void visitCreateGroup(CreateGroup g) {
        out.println(g.toString());
    }

    @Override
    public void visitDeleteGroup(DeleteGroup g) {
        out.println(g.toString());
    }

    @Override
    public void visitAddGroupMembers(AddGroupMembers am) {
        out.print(am.getClass().getSimpleName());
        out.print(" ");
        List<String> members = am.getMembers();
        for (String p : members) {
            out.print(p);
            out.print(" ");
        }
        out.print("in group ");
        out.print(am.getGroupname());
        out.println();
    }

    @Override
    public void visitRemoveGroupMembers(RemoveGroupMembers rm) {
        out.print(rm.getClass().getSimpleName());
        out.print(" ");
        List<String> members = rm.getMembers();
        for (String p : members) {
            out.print(p);
            out.print(" ");
        }
        out.print("in group ");
        out.print(rm.getGroupname());
        out.println();
    }

    @Override
    public void visitSetProperties(SetProperties sp) {
        out.print(sp.getClass().getSimpleName());
        out.print(" on");
        for (String p : sp.getPaths()) {
            out.print(' ');
            out.print(p);
        }
        out.println();
        List<PropertyLine> lines = sp.getPropertyLines();
        for (PropertyLine p : lines) {
            out.print("  ");
            out.println(p);
        }
    }

    @Override
    public void visitAddMixins(AddMixins am) {
        out.println(am.toString());
    }

    @Override
    public void visitRemoveMixins(RemoveMixins rm) {
        out.println(rm.toString());
    }
}
