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
package org.doodle.broker.server;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import java.util.Objects;

import org.doodle.broker.frame.BrokerFrame;
import org.doodle.broker.frame.BrokerFrameExtractor;
import reactor.core.publisher.Mono;

public class BrokerServerAcceptor implements SocketAcceptor {

  private final BrokerFrameExtractor frameExtractor;

  public BrokerServerAcceptor(BrokerFrameExtractor frameExtractor) {
    this.frameExtractor = Objects.requireNonNull(frameExtractor);
  }

  @Override
  public Mono<RSocket> accept(ConnectionSetupPayload setupPayload, RSocket rSocket) {
    BrokerFrame brokerFrame = frameExtractor.apply(setupPayload);
    return Mono.empty();
  }
}
