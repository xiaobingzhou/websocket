package com.github.xiaobingzhou.websocket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;

@Slf4j
public class WebSocketManagerChecker {

    public static void checkSession(WebSocketManager webSocketManager){
        log.info("[WebSocketManagerChecker] 开始检测websocket管理器的session是否关闭");
        webSocketManager.localWebSocketMap().values().stream().forEach(webSocketEntity ->
            webSocketEntity.getSessions().stream()
                    .filter(session -> !((Session) session).isOpen())
                    .forEach(session -> {
                        log.warn("[WebSocketManagerChecker] 检测到session({})已关闭, 自动移除, from={}, identifier={}",
                                ((Session) session).getId(), webSocketEntity.getFrom(), webSocketEntity.getIdentifier());

                        webSocketManager.remove(webSocketEntity.getIdentifier(), (Session) session);
                    })
        );
    }

}
