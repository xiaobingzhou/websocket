package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;

import javax.websocket.Session;

/**
 * WebSocket 连接事件
 * @author bell.zhouxiaobing
 * @date 2020/7/16 13:31
 * @since
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
