package com.github.xiaobingzhou.websocket.cluster.action;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.WebSocketEntity;
import com.github.xiaobingzhou.websocket.WebSocketManager;
import com.github.xiaobingzhou.websocket.event.WebSocketCloseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;

/**
 * 移除连接，注意不要出现循环执行
 * @author xiaobingzhou
 * @date 2020/7/16 22:00
 * @since 1.0.0
 */
@Slf4j
public class RemoveAction implements Action {

    private ApplicationContext applicationContext;

    public RemoveAction(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle(WebSocketManager manager, JSONObject jsonObject) {
        if (log.isDebugEnabled()) {
            log.debug("[cluster]RemoveAction={}", jsonObject.toString());
        }

        String identifier = jsonObject.getString(Action.IDENTIFIER);
        if (identifier == null) {
            return;
        }

        String sessionId = jsonObject.getString(Action.SESSIONID);
        if (sessionId == null) {
            return;
        }

        Map<String, WebSocketEntity> connections = manager.localWebSocketMap();
        WebSocketEntity webSocketEntity = connections.get(identifier);
        if (webSocketEntity == null) {
            return;
        }

        List<Session> sessions = webSocketEntity.getSessions();
        sessions.stream()
                .filter(session -> session.getId().equals(sessionId))
                .findFirst()
                .ifPresent(session -> {
                    if (log.isDebugEnabled()) {
                        log.debug("[local] remove session, webSocketEntity={}, sessionId={}", webSocketEntity, sessionId);
                    }

                    sessions.remove(session);
                    if (sessions.isEmpty()) {
                        connections.remove(identifier);
                    }

                    // 发布事件
                    publishEvent(new WebSocketCloseEvent(webSocketEntity, session));
        });

    }

    private void publishEvent(WebSocketCloseEvent webSocketCloseEvent) {
        applicationContext.publishEvent(webSocketCloseEvent);
    }

}
