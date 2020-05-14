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

package com.grexdev.jplusone.core.registry;

import com.grexdev.jplusone.core.frame.FrameExtract;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;

@Slf4j
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class FrameStack {

    private final List<FrameExtract> callFrames;

    public FrameStack intersection(FrameStack otherFramesStack) {
        int endIndex = 0;
        Iterator<FrameExtract> framesIterator = callFrames.iterator();
        Iterator<FrameExtract> otherFramesIterator = otherFramesStack.callFrames.iterator();

        while (framesIterator.hasNext() && otherFramesIterator.hasNext()
                && Objects.equals(framesIterator.next(), otherFramesIterator.next())) {
            endIndex++;
        }

        return new FrameStack(callFrames.subList(0, endIndex));
    }

    public FrameStack substract(FrameStack otherFramesStack) {
        if (otherFramesStack.callFrames.size() > callFrames.size()) {
            log.warn("=================== Fail to substract frame stack =================");
            return new FrameStack(emptyList());
        }

        Iterator<FrameExtract> framesIterator = callFrames.iterator();
        Iterator<FrameExtract> otherFramesIterator = otherFramesStack.callFrames.iterator();

        while (otherFramesIterator.hasNext()) {
            FrameExtract frameExtract = framesIterator.next();
            FrameExtract otherFrameExtract = otherFramesIterator.next();

            if (!frameExtract.equals(otherFrameExtract)) {
                log.warn("=================== Fail to substract frame stack =================");
                return new FrameStack(emptyList());
            }
        }

        List<FrameExtract> remainingFrames = new ArrayList<>(callFrames.size() - otherFramesStack.callFrames.size());
        framesIterator.forEachRemaining(remainingFrames::add);

        return new FrameStack(remainingFrames);
    }

    Optional<FrameExtract> findLastMatchingFrame(Predicate<FrameExtract> predicate) {
        ListIterator<FrameExtract> iterator = callFrames.listIterator(callFrames.size());

        while (iterator.hasPrevious()) {
            FrameExtract frame = iterator.previous();

            if (predicate.test(frame)) {
                return Optional.ofNullable(frame);
            }
        }

        return Optional.empty();
    }

}