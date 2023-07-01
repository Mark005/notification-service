package com.bmo.common.notification_service.core.model.rest;

import lombok.Data;

@Data
public class EmailAccountCreateDto {

  private String email;

  private String password;

  private String host;

  private Integer port;

  private Integer connectionTimeout;

  private Integer timeout;

  private Integer fallbackPriority;
}
