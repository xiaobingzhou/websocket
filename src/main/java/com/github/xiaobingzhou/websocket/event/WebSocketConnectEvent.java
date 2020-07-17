package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;

import javax.websocket.Session;

/**
 * WebSocket 连接事件
 * @author xiaobingzhou
 * @date 2020/7/16 13:31
 * @since 1.0.0
 */
public class WebSocketConnectEvent extends WebSocketEvent {
    private Session session;
    public WebSocketConnectEvent(WebSocketEntity webSocketEntity, Session session){
        super(webSocketEntity);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
