package com.github.xiaobingzhou.websocket.cluster.action;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.WebSocketSender;
import com.github.xiaobingzhou.websocket.WebSocketManager;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;

/**
 * 广播消息，注意不要出现循环执行
 * @author xiaobingzhou
 * @date 2020/7/16 21:59
 * @since 1.0.0
 */
@Slf4j
public class BroadCastAction implements Action {
    @Override
    public void handle(WebSocketManager manager, JSONObject jsonObject) {
        log.info(jsonObject.toString());
        String message = jsonObject.getString(MESSAGE);
        if (message == null) {
            return;
        }

        manager.localWebSocketMap().values()
                .forEach(webSocket -> webSocket.getSessions().stream()
                        .forEach(session -> WebSocketSender.sendMessage((Session) session, message)));
    }
}
