package com.bmo.common.market_service.core.configs.properties;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class KafkaClusterProperties {

  @NotEmpty
  private List<String> bootstrapServers;
}



