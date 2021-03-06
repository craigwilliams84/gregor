/*
* Copyright 2015 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.capgemini.gregor.internal;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

/**
 * Factory for creating a set of bean definitions required for a single
 * gregor kafka instance (eg. a producer or consumer).
 * 
 * @author craigwilliams84
 *
 */
public interface KafkaBeanDefinitionFactory<T> {
    
    /**
     * Create a set of bean definitions required for a kafka topic consumer.
     * 
     * @param instancerDetails The details for the gregor kafka instance (eg. consumer/producer)
     * that the bean definitions are to be created for.
     * @return The bean definition holder set
     */
    Set<BeanDefinitionHolder> create(T instanceDetails);
}
