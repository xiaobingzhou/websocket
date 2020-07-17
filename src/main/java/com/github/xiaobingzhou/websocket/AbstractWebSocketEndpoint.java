package com.github.xiaobingzhou.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

/**
 * websocket 连接endpoint
 * @author xiaobingzhou
 * @date 2020/7/17 14:03
 * @since 1.0.0
 * @see WebSocketManager
 */
public abstract class AbstractWebSocketEndpoint {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractWebSocketEndpoint.class);

    public void connect(Session session, String from, String identifier) {
        try {
            if(null == identifier || "".equals(identifier)){
                return;
            }

            WebSocketManager websocketManager = this.getWebSocketManager();
            WebSocketEntity webSocketEntity = websocketManager.get(identifier);
            if (webSocketEntity == null) {
                webSocketEntity = new WebSocketEntity();
                webSocketEntity.setFrom(WebSocketFrom.getFrom(from));
                webSocketEntity.setIdentifier(identifier);
            }

            webSocketEntity.addSession(session);
            websocketManager.put(identifier , webSocketEntity, session);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void disconnect(Session session, String from, String identifier) {
        this.getWebSocketManager().remove(identifier, session);
    }

    public void receiveMessage(String message, Session session, String from, String identifier) {
        WebSocketManager webSocketManager = this.getWebSocketManager();
        // 心跳监测
        if(webSocketManager.isPing(identifier, message, session)){
            String pong = webSocketManager.pong(identifier, message, session);
            WebSocketSender.sendMessage(session, pong);
            return;
        }
        // 收到其他消息的时候
        webSocketManager.onMessage(identifier, message, session);
    }

    public abstract WebSocketManager getWebSocketManager();

}
