package com.github.xiaobingzhou.websocket.cluster.redis;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.WebSocketManager;
import com.github.xiaobingzhou.websocket.cluster.Receiver;
import com.github.xiaobingzhou.websocket.cluster.action.Action;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class RedisReceiver implements Receiver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void receiveMessage(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        if(!jsonObject.containsKey(Action.ACTION)){
            return;
        }
        String actionName = jsonObject.getString(Action.ACTION);
        Action action = getAction(actionName);
        action.handle(getWebSocketManager() , jsonObject);
    }

    protected Action getAction(String actionName) {
        return this.applicationContext.getBean(actionName, Action.class);
    }

    protected WebSocketManager getWebSocketManager() {
        return this.applicationContext.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
