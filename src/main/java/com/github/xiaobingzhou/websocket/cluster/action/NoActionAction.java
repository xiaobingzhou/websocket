package com.github.xiaobingzhou.websocket.cluster.action;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.WebSocketManager;

public class NoActionAction implements Action {

    @Override
    public void handle(WebSocketManager manager, JSONObject object) {
        // do nothing
    }
}
