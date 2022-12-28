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
package org.doodle.broker.frame;

import com.google.protobuf.Message;
import java.util.Map;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.AbstractEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;

public class BrokerFrameEncoder extends AbstractEncoder<Message> {
  private final ProtobufEncoder encoder;

  public BrokerFrameEncoder() {
    super(BrokerFrameMimeTypes.BROKER_FRAME_MIME_TYPE);
    this.encoder = new ProtobufEncoder();
  }

  @Override
  public Flux<DataBuffer> encode(
      Publisher<? extends Message> inputStream,
      DataBufferFactory bufferFactory,
      ResolvableType elementType,
      MimeType mimeType,
      Map<String, Object> hints) {
    return this.encoder.encode(inputStream, bufferFactory, elementType, mimeType, hints);
  }

  @Override
  public DataBuffer encodeValue(
      Message value,
      DataBufferFactory bufferFactory,
      ResolvableType valueType,
      MimeType mimeType,
      Map<String, Object> hints) {
    return this.encoder.encodeValue(value, bufferFactory, valueType, mimeType, hints);
  }
}
