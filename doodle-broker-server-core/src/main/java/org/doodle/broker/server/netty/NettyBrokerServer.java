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
package org.doodle.broker.server.netty;

import java.net.InetSocketAddress;
import java.util.Objects;
import org.doodle.broker.server.BrokerServer;
import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.boot.rsocket.server.RSocketServerException;

final class NettyBrokerServer implements BrokerServer {

  private final RSocketServer server;

  NettyBrokerServer(RSocketServer server) {
    this.server = Objects.requireNonNull(server);
  }

  @Override
  public void start() throws RSocketServerException {
    this.server.start();
  }

  @Override
  public void stop() throws RSocketServerException {
    this.server.stop();
  }

  @Override
  public InetSocketAddress address() {
    return this.server.address();
  }
}
