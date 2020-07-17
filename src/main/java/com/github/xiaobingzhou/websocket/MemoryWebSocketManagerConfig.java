package com.github.xiaobingzhou.websocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内存websocket管理器配置类
 * @author xiaobingzhou
 * @date 2020/7/17 16:06
 * @since 1.0.0
 */
@Configuration
public class MemoryWebSocketManagerConfig {

    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public WebSocketManager webSocketManager() {
        return new MemoryWebSocketManager();
    }

}
