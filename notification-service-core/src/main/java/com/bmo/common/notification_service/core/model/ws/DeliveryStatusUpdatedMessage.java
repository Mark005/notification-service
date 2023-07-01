package com.bmo.common.notification_service.core.model.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeliveryStatusUpdatedMessage {

  private String newDeliveryStatus;
  private String message;
}
