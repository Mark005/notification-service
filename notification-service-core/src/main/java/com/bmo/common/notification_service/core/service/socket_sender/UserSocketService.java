package com.bmo.common.notification_service.core.service.socket_sender;

import java.util.UUID;
import org.springframework.web.socket.WebSocketSession;

public interface UserSocketService {

  void saveUsersSession(UUID userId, WebSocketSession session);

  void deleteUsersSession(UUID userId);

  <T> boolean sendMessageToUser(UUID userId, T message);
}
