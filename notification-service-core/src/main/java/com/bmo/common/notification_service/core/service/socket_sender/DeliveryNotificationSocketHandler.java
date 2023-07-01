package com.bmo.common.notification_service.core.service.socket_sender;

import com.bmo.common.gateway.header.GatewayHeader;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryNotificationSocketHandler extends TextWebSocketHandler {

    private final UserSocketService userSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Server connection opened");

        session.getHandshakeHeaders()
            .getOrEmpty(GatewayHeader.USER_ID)
            .stream()
            .findFirst()
            .map(UUID::fromString)
            .ifPresent(userId -> userSocketService.saveUsersSession(userId, session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("Server connection closed: {}", status);

        session.getHandshakeHeaders()
            .getOrEmpty(GatewayHeader.USER_ID)
            .stream()
            .findFirst()
            .map(UUID::fromString)
            .ifPresent(userSocketService::deleteUsersSession);
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();
        log.info("Server received: {}", request);
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("Server transport error: {}", exception.getMessage());
    }
}
