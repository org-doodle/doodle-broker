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
package org.doodle.broker.frame.config;

import org.doodle.broker.frame.BrokerFrameDecoder;
import org.doodle.broker.frame.BrokerFrameEncoder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;

@AutoConfiguration(before = RSocketStrategiesAutoConfiguration.class)
@ConditionalOnClass({RSocketStrategies.class, ProtobufEncoder.class, ProtobufDecoder.class})
public class BrokerFrameAutoConfiguration {

  @Bean
  public RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
    return (strategies) ->
        strategies.encoder(new BrokerFrameEncoder()).decoder(new BrokerFrameDecoder());
  }
}
