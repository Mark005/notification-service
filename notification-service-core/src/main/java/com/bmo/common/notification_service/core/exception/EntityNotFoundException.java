package com.bmo.common.notification_service.core.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String entityName, Object id) {
    super("Entity [%s] with id [%s] not found".formatted(entityName, id));
  }

  public EntityNotFoundException(String text) {
    super(text);
  }
}
