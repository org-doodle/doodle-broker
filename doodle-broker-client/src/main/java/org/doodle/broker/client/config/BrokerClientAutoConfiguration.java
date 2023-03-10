/*
 * Copyright (c) 2022-present Doodle. All rights reserved.
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
package org.doodle.broker.client.config;

import org.doodle.broker.client.BrokerRSocketRequesterBuilder;
import org.doodle.broker.frame.config.BrokerFrameAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.rsocket.RSocketRequester;

@SuppressWarnings("unused")
@AutoConfiguration(
    after = RSocketRequesterAutoConfiguration.class,
    before = BrokerFrameAutoConfiguration.class)
@ConditionalOnBean(BrokerClientMarkerConfiguration.Marker.class)
@ConditionalOnClass({RSocketRequester.class})
@EnableConfigurationProperties(BrokerClientProperties.class)
public class BrokerClientAutoConfiguration {

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public BrokerRSocketRequesterBuilder brokerRSocketRequesterBuilder(
      ObjectProvider<RSocketRequester.Builder> builderProvider,
      ObjectProvider<BrokerRSocketRequesterBuilder.Customizer> customizerProvider) {
    BrokerRSocketRequesterBuilder builder =
        BrokerRSocketRequesterBuilder.builder(
            builderProvider.getIfUnique(RSocketRequester::builder));
    customizerProvider.orderedStream().forEach(c -> c.customize(builder));
    return builder;
  }
}
