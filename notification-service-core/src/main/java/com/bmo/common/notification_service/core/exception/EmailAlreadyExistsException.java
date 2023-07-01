package com.bmo.common.notification_service.exception;

public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException(String email) {
    super("Email account with email [%s] already exists".formatted(email));
  }
}
