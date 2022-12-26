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
package org.doodle.broker.server.config;

import org.doodle.broker.frame.config.BrokerFrameAutoConfiguration;
import org.doodle.broker.server.BrokerServerAcceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = BrokerFrameAutoConfiguration.class)
@ConditionalOnBean(BrokerServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(BrokerServerProperties.class)
public class BrokerServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public BrokerServerAcceptor brokerServerAcceptor() {
    return new BrokerServerAcceptor(null);
  }
}
