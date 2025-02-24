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
import java.util.Map;
import java.util.TreeMap;

import org.osgi.annotation.versioning.ProviderType;

/** A single "set ACL" or "remove ACL" line */
@ProviderType
public class AclLine {

    private final Action action;

    public static final String PROP_PATHS = "paths";
    public static final String PROP_PRINCIPALS = "principals";
    public static final String PROP_PRIVILEGES = "privileges";
    public static final String PROP_NODETYPES = "nodetypes";
    public static final String PATH_REPOSITORY = ":repository";
    public static final String PATH_HOME = ":home:";
    public static final char ID_DELIMINATOR = ',';
    public static final char SUBTREE_DELIMINATOR = '#';

    public enum Action {
        REMOVE,
        REMOVE_ALL,
        DENY,
        ALLOW
    }

    private final Map<String, List<String>> properties;
    private List<RestrictionClause> restrictions;

    public AclLine(Action a) {
        action = a;
        properties = new TreeMap<>();
    }

    public Action getAction() {
        return action;
    }

    /**
     * Return the named multi-value property, or an empty list if not found.
     *
     * @param name the property to get the value of
     * @return the value of the property.
     */
    public List<String> getProperty(String name) {
        List<String> value = properties.get(name);
        return value != null ? value : Collections.<String>emptyList();
    }

    public void setProperty(String name, List<String> values) {
        properties.put(name, Collections.unmodifiableList(values));
    }

    public void setRestrictions(List<RestrictionClause> restrictions) {
        this.restrictions = restrictions;
    }

    public List<RestrictionClause> getRestrictions() {
        return (restrictions == null) ? Collections.<RestrictionClause>emptyList() : restrictions;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + action + " " + properties
                + (restrictions == null || restrictions.isEmpty() ? "" : " restrictions=" + restrictions);
    }
}
