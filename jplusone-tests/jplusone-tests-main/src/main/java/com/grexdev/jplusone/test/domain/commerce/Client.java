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

package com.grexdev.jplusone.test.domain.commerce;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Client {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client", optional = false) // still eager loading, why??? TwoPhaseLoad
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_profile_id")
    private ClientProfile clientProfile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<Order> orders = new HashSet<>();


}