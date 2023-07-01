package com.bmo.common.notification_service.core.model.rest;

import java.util.UUID;
import lombok.Data;

@Data
public class EmailAccountResponseDto {

  private UUID id;

  private String email;

  private String host;

  private Integer port;

  private Integer connectionTimeout;

  private Integer timeout;

  private Integer fallbackPriority;
}
