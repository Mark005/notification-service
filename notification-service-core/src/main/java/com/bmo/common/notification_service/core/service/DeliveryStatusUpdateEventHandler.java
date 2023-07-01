package com.bmo.common.market_service.core.service;

import com.bmo.common.delivery_service.model.kafka.DeliveryStatusUpdateEvent;

public interface DeliveryStatusUpdateEventHandler {

  void handleUpdateEvent(DeliveryStatusUpdateEvent updateEvent);
}
