/*
 * Copyright (c) 2020 Adam Gaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grexdev.jplusone.asserts.api.builder.exclusion.simple;

import com.grexdev.jplusone.asserts.api.builder.ConditionDoneBuilder;

public interface SimpleExplicitOperationExclusionsBuilder extends ConditionDoneBuilder {

    ConditionDoneBuilder exceptFetchingData();

    ConditionDoneBuilder exceptFetchingDataVia(Class<?> clazz, String methodName);

    ConditionDoneBuilder exceptFetchingDataVia(String className, String methodName);

    ConditionDoneBuilder exceptModifyingData();

    ConditionDoneBuilder exceptModifyingDataVia(Class<?> clazz, String methodName);

    ConditionDoneBuilder exceptModifyingDataVia(String className, String methodName);

}
