package com.github.xiaobingzhou.websocket.event;

import com.github.xiaobingzhou.websocket.WebSocketEntity;

import javax.websocket.Session;

/**
 * WebSocket 连接关闭事件
 * @author bell.zhouxiaobing
 * @date 2020/7/16 13:32
 * @since
 */
public class WebSocketCloseEvent extends WebSocketEvent {
    private Session session;
    public WebSocketCloseEvent(WebSocketEntity webSocketEntity, Session session){
        super(webSocketEntity);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
