package com.github.xiaobingzhou.websocket.cluster.action;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.WebSocketManager;
import com.github.xiaobingzhou.websocket.WebSocketSender;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.Optional;

/**
 * 发送信息，注意不要出现循环执行
 * @author bell.zhouxiaobing
 * @date 2020/7/16 22:00
 * @since
 */
@Slf4j
public class SendMessageAction implements Action {
    @Override
    public void handle(WebSocketManager manager, JSONObject jsonObject) {
        log.info(jsonObject.toString());
        String identifier = jsonObject.getString(IDENTIFIER);
        if (identifier == null) {
            return;
        }

        String message = jsonObject.getString(MESSAGE);
        if (message == null) {
            return;
        }

        Optional.ofNullable(manager.get(identifier))
                .ifPresent(webSocketEntity -> {
                    // 发送消息
                    webSocketEntity.getSessions().stream()
                        .forEach((session) -> WebSocketSender.sendMessage((Session) session, message));
                });

    }
}
