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
package org.doodle.broker.server.context;

import java.util.Objects;
import lombok.Setter;
import org.doodle.broker.server.BrokerServer;
import org.doodle.broker.server.BrokerServerAcceptor;
import org.doodle.broker.server.BrokerServerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;

public class BrokerServerBootstrap implements ApplicationEventPublisherAware, SmartLifecycle {

  @Setter private ApplicationEventPublisher applicationEventPublisher;

  private final BrokerServer brokerServer;

  public BrokerServerBootstrap(
      BrokerServerFactory serverFactory, BrokerServerAcceptor serverAcceptor) {
    Objects.requireNonNull(serverFactory);
    this.brokerServer = Objects.requireNonNull(serverFactory.create(serverAcceptor));
  }

  @Override
  public void start() {
    this.brokerServer.start();
    this.applicationEventPublisher.publishEvent(
        new BrokerServerInitializedEvent(this.brokerServer));
  }

  @Override
  public void stop() {
    this.brokerServer.stop();
  }

  @Override
  public boolean isRunning() {
    BrokerServer brokerServer = this.brokerServer;
    if (brokerServer != null) {
      return brokerServer.address() != null;
    }
    return false;
  }
}
