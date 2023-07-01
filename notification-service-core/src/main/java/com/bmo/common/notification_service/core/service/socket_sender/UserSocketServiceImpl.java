package com.bmo.common.notification_service.core.service.socket_sender;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.notification_service.core.exception.HeaderIsMissingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocketUserServiceImpl implements SocketUserService {

  private final Map<UUID, WebSocketSession> userIdToSession = new HashMap<>();

  private final ObjectMapper objectMapper;

  @Override
  public void saveUsersSession(UUID userId, WebSocketSession session) {
    userIdToSession.put(userId, session);
  }

  @Override
  public void deleteUsersSession(UUID userId) {
    userIdToSession.remove(userId);
  }

  @Override
  public <T> boolean sendMessageToUser(UUID userId, T message) {
    WebSocketSession session = userIdToSession.get(userId);
    if (session == null) {
      return false;
    }

    try {
      String messageJson = objectMapper.writeValueAsString(message);
      session.sendMessage(new TextMessage(messageJson));
      log.debug("Message [{}] has been sent to user [{}]", message, userId);
      return true;
    } catch (IOException e) {
      log.error("Exception occurred during message sending, message has not been sent", e);
      return false;
    }
  }
}
