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

import io.rsocket.ConnectionSetupPayload;
import java.util.Map;
import java.util.function.Function;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

public class BrokerFrameExtractor implements Function<ConnectionSetupPayload, BrokerFrame> {

  private final Function<ConnectionSetupPayload, BrokerFrame> frameExtractor;

  public BrokerFrameExtractor(RSocketStrategies strategies) {
    this.frameExtractor =
        (setupPayload) -> {
          MimeType mimeType = MimeType.valueOf(setupPayload.metadataMimeType());
          MetadataExtractor metadataExtractor = strategies.metadataExtractor();
          Map<String, Object> setupMetadata = metadataExtractor.extract(setupPayload, mimeType);
          if (setupMetadata.containsKey(BrokerFrameMimeTypes.BROKER_FRAME_METADATA_KEY)) {
            return (BrokerFrame) setupMetadata.get(BrokerFrameMimeTypes.BROKER_FRAME_METADATA_KEY);
          }
          return null;
        };
  }

  @Override
  public BrokerFrame apply(ConnectionSetupPayload setupPayload) {
    return this.frameExtractor.apply(setupPayload);
  }
}
