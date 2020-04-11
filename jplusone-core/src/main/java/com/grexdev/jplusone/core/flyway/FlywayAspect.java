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

package com.grexdev.jplusone.core.flyway;

import com.grexdev.jplusone.core.tracking.TrackingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class FlywayAspect {

    private final TrackingContext trackingContext;

    @Around("execution(* org.flywaydb.core.Flyway.*(..))")
    public Object interceptFlywayOperation(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        try {
            trackingContext.disableRecording();
            return thisJoinPoint.proceed();
        } finally {
            trackingContext.enableRecording();
        }
    }

    // TODO: intercept liquidbase calls
}
