package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;
import org.springframework.context.ApplicationEvent;

/**
 * websocket 事件
 * @author bell.zhouxiaobing
 * @date 2020/7/16 21:46
 * @since
 */
public class WebSocketEvent extends ApplicationEvent {
    public WebSocketEvent(WebSocketEntity webSocketEntity){
        super(webSocketEntity);
    }
}
