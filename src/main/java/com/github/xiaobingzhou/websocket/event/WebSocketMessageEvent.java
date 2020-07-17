package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;

import javax.websocket.Session;

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
