package com.bmo.common.notification_service.core.service.notification;

public interface NotificationProcessor<E> {

  boolean filterNotification(E event);

  void processNotification(E message);

}
