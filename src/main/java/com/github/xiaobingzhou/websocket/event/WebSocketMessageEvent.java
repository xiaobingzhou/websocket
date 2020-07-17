package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;

import javax.websocket.Session;

/**
 * websocket 消息事件
 * @author xiaobingzhou
 * @date 2020/7/17 14:02
 * @since 1.0.0
 */
public class WebSocketMessageEvent extends WebSocketEvent {
    private Session session;
    private String message;
    public WebSocketMessageEvent(WebSocketEntity webSocketEntity, Session session, String message){
        super(webSocketEntity);
        this.session = session;
        this.message = message;
    }

    public Session getSession() {
        return session;
    }

    public String getMessage() {
        return message;
    }
}
