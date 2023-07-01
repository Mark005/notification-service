package com.bmo.common.market_service.core.service;

import com.bmo.common.delivery_service.model.kafka.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.kafka.DeliveryStatusUpdateEvent;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryStatusUpdateEventHandlerImpl implements DeliveryStatusUpdateEventHandler {

  private final UsersOrderService usersOrderService;

  @Override
  public void handleUpdateEvent(DeliveryStatusUpdateEvent updateEvent) {
    if (updateEvent.getStatus() == DeliveryStatusDto.DELIVERED) {
      UsersOrder usersOrder = usersOrderService.getOrderById(updateEvent.getOrderId());

      usersOrder.setStatus(OrderStatus.ORDERED);

      usersOrderService.saveAndUpdateHistory(usersOrder);
    }
  }
}
