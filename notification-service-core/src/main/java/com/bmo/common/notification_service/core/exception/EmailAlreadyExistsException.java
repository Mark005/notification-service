package com.bmo.common.notification_service.core.exception;

public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException(String email) {
    super("Email account with email [%s] already exists".formatted(email));
  }
}
