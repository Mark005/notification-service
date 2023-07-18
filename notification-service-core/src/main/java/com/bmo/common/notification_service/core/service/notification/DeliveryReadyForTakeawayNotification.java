package com.bmo.common.notification_service.core.service.notification;

import com.bmo.common.delivery_service.model.kafka.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.kafka.DeliveryStatusUpdateEvent;
import com.bmo.common.market_service.client.MarketServiceClient;
import com.bmo.common.market_service.model.user.UserResponseDto;
import com.bmo.common.notification_service.core.model.email.EmailMessage;
import com.bmo.common.notification_service.core.model.email.EmailMessageContent;
import com.bmo.common.notification_service.core.model.ws.DeliveryStatusUpdatedMessage;
import com.bmo.common.notification_service.core.service.email_sender.EmailSenderService;
import com.bmo.common.notification_service.core.service.socket_sender.UserSocketService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryReadyForTakeawayNotification implements NotificationProcessor<DeliveryStatusUpdateEvent> {

  private final EmailSenderService emailSenderService;
  private final UserSocketService userSocketService;
  private final MarketServiceClient marketServiceClient;

  @Override
  public boolean filterNotification(DeliveryStatusUpdateEvent event) {
    return event.getStatus() == DeliveryStatusDto.READY_TO_TAKEAWAY;
  }

  @Override
  public void processNotification(DeliveryStatusUpdateEvent message) {
    UUID userId = message.getUserId();

    boolean isSentBySocket = userSocketService.sendMessageToUser(
        userId,
        DeliveryStatusUpdatedMessage.builder()
            .newDeliveryStatus(message.getStatus().name())
            .message("Your order with id: %s, is ready for takeaway".formatted(message.getOrderId()))
            .build());

    if (!isSentBySocket) {
      UserResponseDto user = marketServiceClient.getUserById(userId);

      EmailMessage emailMessage = EmailMessage.builder()
          .to(user.getEmail())
          .subject("Order ready for takeaway")
          .message(EmailMessageContent.builder()
              .plaintext("Your order with id: %s, is ready for takeaway"
                  .formatted(message.getOrderId()))
              .build())
          .build();

      emailSenderService.send(emailMessage);
    }
  }
}
