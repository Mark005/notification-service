package com.bmo.common.notification_service.core.service;

import com.bmo.common.delivery_service.model.kafka.DeliveryStatusUpdateEvent;
import com.bmo.common.notification_service.core.service.notification.NotificationProcessor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryStatusUpdateEventHandlerImpl implements DeliveryStatusUpdateEventHandler {

  private final List<NotificationProcessor<DeliveryStatusUpdateEvent>> notificationProcessors;

  @Override
  public void handleUpdateEvent(DeliveryStatusUpdateEvent updateEvent) {
    notificationProcessors.stream()
        .filter(notificationProcessor -> notificationProcessor.filterNotification(updateEvent))
        .forEach(notificationProcessor -> notificationProcessor.processNotification(updateEvent));
  }
}
