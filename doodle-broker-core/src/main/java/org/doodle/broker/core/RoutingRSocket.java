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
package org.doodle.broker.core;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import java.util.Objects;
import java.util.function.Function;
import org.doodle.broker.frame.Address;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RoutingRSocket implements RSocket {
  private final RSocketLocator locator;
  private final Function<Payload, Address> addressExtractor;

  public RoutingRSocket(RSocketLocator locator, Function<Payload, Address> addressExtractor) {
    this.locator = Objects.requireNonNull(locator);
    this.addressExtractor = Objects.requireNonNull(addressExtractor);
  }

  @Override
  public Mono<Void> fireAndForget(Payload payload) {
    try {
      RSocket located = locate(payload);
      return located.fireAndForget(payload);
    } catch (Throwable cause) {
      payload.release();
      return Mono.error(cause);
    }
  }

  @Override
  public Mono<Payload> requestResponse(Payload payload) {
    try {
      RSocket located = locate(payload);
      return located.requestResponse(payload);
    } catch (Throwable cause) {
      payload.release();
      return Mono.error(cause);
    }
  }

  @Override
  public Flux<Payload> requestStream(Payload payload) {
    try {
      RSocket located = locate(payload);
      return located.requestStream(payload);
    } catch (Throwable cause) {
      payload.release();
      return Flux.error(cause);
    }
  }

  @Override
  public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
    return Flux.from(payloads)
        .switchOnFirst(
            (first, flux) -> {
              if (first.hasValue()) {
                Payload payload = first.get();
                try {
                  RSocket located = locate(payload);
                  return located.requestChannel(payloads);
                } catch (Throwable cause) {
                  if (payload != null) {
                    payload.release();
                  }
                  return Flux.error(cause);
                }
              }
              return flux;
            });
  }

  @Override
  public Mono<Void> metadataPush(Payload payload) {
    try {
      RSocket located = locate(payload);
      return located.metadataPush(payload);
    } catch (Throwable cause) {
      payload.release();
      return Mono.error(cause);
    }
  }

  private RSocket locate(Payload payload) {
    Address address = this.addressExtractor.apply(payload);
    if (!locator.supports(address.getRoutingType())) {
      throw new IllegalStateException(
          "NO RSocketLocator for RoutingType" + address.getRoutingType());
    }
    return locator.locate(address);
  }
}
