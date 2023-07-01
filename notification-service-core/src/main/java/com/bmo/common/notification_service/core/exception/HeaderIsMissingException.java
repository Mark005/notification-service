package com.bmo.common.notification_service.core.exception;

public class HeaderIsMissingException extends RuntimeException {

  public HeaderIsMissingException(String ... headerNames) {
    super("%s Header(s) is missing".formatted(headerNames));
  }
}
