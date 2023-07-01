package com.bmo.common.notification_service.core.service.socket_sender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

@Configuration
@EnableWebSocket
public class ServerWebSocketConfig {

    @Bean
    public WebSocketConfigurer webSocketConfigurer(
        DeliveryNotificationSocketHandler webSocketHandler) {
        return registry -> registry.addHandler(webSocketHandler, "/websocket/delivery-notifications");
    }
}
