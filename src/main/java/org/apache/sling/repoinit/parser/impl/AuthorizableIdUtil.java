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
package org.apache.sling.repoinit.parser.impl;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AuthorizableIdUtil {

    private static final Pattern regex = Pattern.compile("\\s");

    private AuthorizableIdUtil() {
        // hidden
    }

    /**
     * Gets the authorizable ID handling cases where the value should be quoted
     * (e.g. the authorizable id is not just an alphanumeric string)
     * 
     * @param authorizableId the authorizable ID to get
     * @return the (potentially quoted) authorizable id
     */
    public static final String forRepoInitString(String authorizableId) {
        return !regex.matcher(authorizableId).find() ? authorizableId : "\"" + authorizableId + "\"";
    }

    /**
     * Gets the authorizable IDs handling cases where the value should be quoted
     * (e.g. the authorizable id is not just an alphanumeric string)
     * 
     * @param authorizableIds the authorizable IDs to get
     * @return the list of (potentially quoted) authorizable ids
     */
    public static final List<String> forRepoInitString(List<String> authorizableIds) {
        return authorizableIds.stream().map(AuthorizableIdUtil::forRepoInitString).collect(Collectors.toList());
    }
}
