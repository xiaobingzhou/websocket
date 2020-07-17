package com.github.xiaobingzhou.websocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebSocketManagerConfig {

    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public WebSocketManager webSocketManager() {
        return new MemoryWebSocketManager();
    }

}
