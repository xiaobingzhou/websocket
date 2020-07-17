package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;
import org.springframework.context.ApplicationEvent;

/**
 * websocket 事件
 * @author xiaobingzhou
 * @date 2020/7/16 21:46
 * @since 1.0.0
 */
public class WebSocketEvent extends ApplicationEvent {
    public WebSocketEvent(WebSocketEntity webSocketEntity){
        super(webSocketEntity);
    }
}
