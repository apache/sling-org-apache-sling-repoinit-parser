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

/** A single "set property" line */
public class PropertyLine {

    private final String name;
    private final PropertyType propertyType;
    private final List<String> values;
    private boolean isDefault = false;

    /** Valid types for these properties */
    public static enum PropertyType {
        String,
        Long,
        Double,
        Date,
        Boolean
    }

    /**
     * Stores data for one line of a "set property" block
     *  @param name  name of the property
     *  @param typeString property type, as a String
     *  @param values  values of the property
     */
    public PropertyLine(String name, String typeString, List<String> values, boolean isDefault) {
        this.name = name;
        this.propertyType = typeString == null ? PropertyType.String : PropertyType.valueOf(typeString);
        this.values = values == null ? new ArrayList<>() : values;
        this.isDefault = isDefault;

    }

    /** @return the name of the property to set */
    public String getPropertyName() {return name;};

    /** @return the type of the property to set */
    public PropertyType getPropertyType() {return propertyType;};

    /** @return the list ot values of the property to set */
    public List<String> getPropertyValues() {
        return Collections.unmodifiableList(values);
    }

    /** True if this line is a "default" as opposed to a "set" instruction.
     * @return true if a previously existing value of this property is kept, instead
     *      of being overwritten like a "set" instruction does
     */
    public boolean isDefault() { return isDefault; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" ");
        if(isDefault()) {
            sb.append("default ");
        }

        sb.append(name);
        sb.append("=");
        sb.append("{");
        sb.append(propertyType.toString());
        sb.append("}");

        sb.append(values);
        return sb.toString();
    }
}