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

import org.jetbrains.annotations.NotNull;

public class QuotableStringUtil {

    private static final Pattern REQUIRES_NO_QUOTES = Pattern.compile("[a-zA-Z0-9-_\\./:*@]+");
    private QuotableStringUtil() {
        // hidden
    }

    /**
     * Gets the string handling cases where the value should be quoted
     * (i.e. the string contains whitespace)
     * 
     * @param string the string to get in repoinit compatible form
     * @return the (potentially quoted) string
     */
    @NotNull
    public static final String forRepoInitString(@NotNull String string) {
        return REQUIRES_NO_QUOTES.matcher(string).matches() ? string :
            "\"" + string + "\"";
    }

    /**
     * Gets the strings handling cases where the value should be quoted
     * (i.e. the string contains whitespace)
     * 
     * @param strings the strings to get
     * @return the list of (potentially quoted) strings
     */
    @NotNull
    public static final List<String> forRepoInitString(@NotNull List<String> strings) {
        return strings.stream().map(QuotableStringUtil::forRepoInitString).collect(Collectors.toList());
    }
}
