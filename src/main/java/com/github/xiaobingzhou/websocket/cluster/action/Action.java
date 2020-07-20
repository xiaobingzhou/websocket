package com.github.xiaobingzhou.websocket.cluster.action;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.WebSocketManager;

/**
 * 定义集群操作接口
 * @author xiaobingzhou
 * @date 2020/7/17 16:04
 * @since 1.0.0
 */
@FunctionalInterface
public interface Action {
    String IDENTIFIER = "identifier";
    String MESSAGE    = "message";
    String ACTION     = "action";

    /**
     * 根据消息做自己的事情
     * @param manager webSocket管理器
     * @param jsonObject 消息体转化的JSON
     */
    void handle(WebSocketManager manager, JSONObject jsonObject);
}
