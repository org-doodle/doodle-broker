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
package org.doodle.broker.client;

import io.rsocket.loadbalance.LoadbalanceStrategy;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import reactor.core.publisher.Mono;

final class DefaultBrokerRSocketRequesterBuilder implements BrokerRSocketRequesterBuilder {

  private final RSocketRequester.Builder builder;

  public DefaultBrokerRSocketRequesterBuilder(RSocketRequester.Builder builder) {
    this.builder = Objects.requireNonNull(builder);
  }

  @Override
  public RSocketRequester.Builder dataMimeType(MimeType mimeType) {
    return this.builder.dataMimeType(mimeType);
  }

  @Override
  public RSocketRequester.Builder metadataMimeType(MimeType mimeType) {
    return this.builder.metadataMimeType(mimeType);
  }

  @Override
  public RSocketRequester.Builder setupData(Object data) {
    return this.builder.setupData(data);
  }

  @Override
  public RSocketRequester.Builder setupRoute(String route, Object... routeVars) {
    return this.builder.setupRoute(route, routeVars);
  }

  @Override
  public RSocketRequester.Builder setupMetadata(Object value, MimeType mimeType) {
    return this.builder.setupMetadata(value, mimeType);
  }

  @Override
  public RSocketRequester.Builder rsocketStrategies(RSocketStrategies strategies) {
    return this.builder.rsocketStrategies(strategies);
  }

  @Override
  public RSocketRequester.Builder rsocketStrategies(
      Consumer<RSocketStrategies.Builder> configurer) {
    return this.builder.rsocketStrategies(configurer);
  }

  @Override
  public RSocketRequester.Builder rsocketConnector(RSocketConnectorConfigurer configurer) {
    return this.builder.rsocketConnector(configurer);
  }

  @Override
  public RSocketRequester.Builder apply(Consumer<RSocketRequester.Builder> configurer) {
    return this.builder.apply(configurer);
  }

  @Override
  public RSocketRequester tcp(String host, int port) {
    return this.builder.tcp(host, port);
  }

  @Override
  public RSocketRequester websocket(URI uri) {
    return this.builder.websocket(uri);
  }

  @Override
  public RSocketRequester transport(ClientTransport transport) {
    return this.builder.transport(transport);
  }

  @Override
  public RSocketRequester transports(
      Publisher<List<LoadbalanceTarget>> targetPublisher, LoadbalanceStrategy loadbalanceStrategy) {
    return this.builder.transports(targetPublisher, loadbalanceStrategy);
  }

  @Deprecated
  @Override
  public Mono<RSocketRequester> connectTcp(String host, int port) {
    return this.builder.connectTcp(host, port);
  }

  @Deprecated
  @Override
  public Mono<RSocketRequester> connectWebSocket(URI uri) {
    return this.builder.connectWebSocket(uri);
  }

  @Deprecated
  @Override
  public Mono<RSocketRequester> connect(ClientTransport transport) {
    return this.builder.connect(transport);
  }
}
