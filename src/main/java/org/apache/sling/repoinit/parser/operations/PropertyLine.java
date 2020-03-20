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

/** A single "set property" line */
public class PropertyLine {

    private final String name;
    private final String type;
    private final List<String> values;

    /**
     * Operation that sets property on a node.
     *  @param name  name of the property
     *  @param type   property type
     *  @param values  values of the property
     */
    public PropertyLine(String name, String type, List<String> values) {
        this.name = name;
        this.type = type;
        this.values = values;
    }

    public String getPropertyName() {return name;};

    public String getPropertyType() {return type;};

    public List<String> getPropertyValues() {
        return values;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" ");
        sb.append(name);
        sb.append("=");
        if (type != null) {
            sb.append("{");
            sb.append(type);
            sb.append("}");
        }
        sb.append(values);
        return sb.toString();
    }
}
