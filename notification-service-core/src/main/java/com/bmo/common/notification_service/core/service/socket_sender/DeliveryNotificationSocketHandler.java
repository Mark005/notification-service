package com.bmo.common.notification_service.core.service.socket_sender;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

    private final SocketUserService socketUserService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Server connection opened");
        socketUserService.saveSession(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("Server connection closed: {}", status);
        socketUserService.deleteSession(session);
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
    
    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.demo.websocket");
    }
}
