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

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class RemoveMixins extends BaseMixinsOperation {

    private static final String FROM = "from";

    public RemoveMixins(List<String> mixins, List<String> paths) {
        super(mixins, paths);
    }

    @Override
    public void accept(OperationVisitor v) {
        v.visitRemoveMixins(this);
    }

    @Override
    protected String getParametersDescription() {
        return getParametersDescription(FROM);
    }

    @NotNull
    @Override
    public String asRepoInitString() {
        return asRepoInitString("remove", FROM);
    }

}