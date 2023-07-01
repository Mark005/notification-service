package com.bmo.common.market_service.core.kafka.consumer;

import com.bmo.common.delivery_service.model.kafka.DeliveryStatusUpdateEvent;
import com.bmo.common.market_service.core.service.DeliveryStatusUpdateEventHandler;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(
    topics = "${kafka.consumer.delivery-status-event.topic}",
    containerFactory = "deliveryStatusEventListenerContainerFactory"
)
public class DeliveryStatusEventConsumer {

  private final DeliveryStatusUpdateEventHandler handler;

  @KafkaHandler
  public void consumeDeliveryStatusUpdateEvent(DeliveryStatusUpdateEvent updateEvent, Acknowledgment acknowledgment) {
    log.debug("Consumed DeliveryStatusUpdateEvent: {}",
        ToStringBuilder.reflectionToString(updateEvent, ToStringStyle.JSON_STYLE));
    handler.handleUpdateEvent(updateEvent);
    acknowledgment.acknowledge();
  }

  @KafkaHandler(isDefault = true)
  public void listenDefault(Object obj,
      @Header(KafkaHeaders.BATCH_CONVERTED_HEADERS) List<Map<String, Object>> headers) {
    log.warn("""
        unknown object consumed
        headers :
        {}
        object:
        {}""", headers, obj);
  }
}
