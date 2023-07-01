package com.bmo.common.notification_service.model.rest;

import lombok.Data;

@Data
public class EmailAccountUpdateDto {

  private String password;

  private String host;

  private Integer port;

  private Integer connectionTimeout;

  private Integer timeout;

  private Integer fallbackPriority;
}
