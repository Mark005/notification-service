package com.bmo.common.market_service.core.configs.properties;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class KafkaConsumerProperties {

  @NotEmpty
  private String topic;
  @NotEmpty
  private String consumerGroupId;
  @Valid
  private KafkaClusterProperties cluster;
}
