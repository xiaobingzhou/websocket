package com.github.xiaobingzhou.websocket;

import com.github.xiaobingzhou.websocket.event.WebSocketEvent;
import com.github.xiaobingzhou.websocket.event.WebSocketCloseEvent;
import com.github.xiaobingzhou.websocket.event.WebSocketConnectEvent;
import com.github.xiaobingzhou.websocket.event.WebSocketMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MemoryWebSocketManager implements WebSocketManager, ApplicationContextAware {
    /**
     * 因为全局只有一个 WebSocketManager ，所以才敢定义为非static
     */
    private final Map<String, WebSocketEntity> connections = new ConcurrentHashMap<>(100);

    private ApplicationContext applicationContext;

    @Override
    public WebSocketEntity get(String identifier) {
        return connections.get(identifier);
    }

    @Override
    public void put(String identifier, WebSocketEntity webSocketEntity, Session session) {
        connections.put(identifier, webSocketEntity);
        publishEvent(new WebSocketConnectEvent(webSocketEntity, session));
    }

    protected void publishEvent(WebSocketEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("发布事件, {}", event);
        }

        // 发布连接事件
        this.applicationContext.publishEvent(event);
    }

    @Override
    public void remove(String identifier, Session session) {
        doRemove(identifier, session);
    }

    protected void doRemove(String identifier, Session session) {
        WebSocketEntity webSocketEntity = connections.get(identifier);
        if (webSocketEntity == null) {
            return;
        }

        webSocketEntity.removeSession(session);
        if (webSocketEntity.getSessions().isEmpty()) {
            connections.remove(identifier);
        }

        publishEvent(new WebSocketCloseEvent(webSocketEntity, session));
    }

    @Override
    public Map<String, WebSocketEntity> localWebSocketMap() {
        return connections;
    }

    @Override
    public void sendMessage(String identifier, String message) {
        doSendMessage(identifier, message);

    }

    protected void doSendMessage(String identifier, String message) {
        WebSocketEntity webSocketEntity = connections.get(identifier);
        if (webSocketEntity == null) {
            return;
        }

        // 发送消息
        webSocketEntity.getSessions().stream()
                .forEach((session) -> WebSocketSender.sendMessage((Session) session, message));
    }

    @Override
    public void broadcast(String message) {
        // 广播
        localWebSocketMap().values()
                .forEach(webSocket -> webSocket.getSessions().stream()
                        .forEach(session -> WebSocketSender.sendMessage((Session) session, message)));
    }

    @Override
    public void onMessage(String identifier, String message, Session session) {
        WebSocketEntity webSocketEntity = connections.get(identifier);
        if (webSocketEntity == null) {
            return;
        }

        // 发布信息事件
        publishEvent(new WebSocketMessageEvent(webSocketEntity, session, message));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
