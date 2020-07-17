package com.github.xiaobingzhou.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

public abstract class AbstractWebSocketEndpoint {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractWebSocketEndpoint.class);

    private WebSocketManager webSocketManager;

    public AbstractWebSocketEndpoint(WebSocketManager webSocketManager) {
        this.webSocketManager = webSocketManager;
    }

    /**
     * 连接来源
     * @see WebSocketFrom
     */
    protected static final String FROM = "from";

    /**
     * 路径标识：目前使用token来代表
     */
    protected static final String IDENTIFIER = "identifier";

    public void connect(Session session, String from, String identifier) {
        try {
            if(null == identifier || "".equals(identifier)){
                return;
            }
            WebSocketManager websocketManager = this.webSocketManager;
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
        this.webSocketManager.remove(identifier, session);
    }

    public void receiveMessage(String message, Session session, String from, String identifier) {
        WebSocketManager webSocketManager = this.webSocketManager;
        // 心跳监测
        if(webSocketManager.isPing(identifier, message, session)){
            String pong = webSocketManager.pong(identifier, message, session);
            WebSocketSender.sendMessage(session, pong);
            return;
        }
        // 收到其他消息的时候
        webSocketManager.onMessage(identifier, message, session);
    }

}
