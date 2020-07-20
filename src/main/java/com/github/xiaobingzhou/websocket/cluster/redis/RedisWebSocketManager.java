package com.github.xiaobingzhou.websocket.cluster.redis;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.MemoryWebSocketManager;
import com.github.xiaobingzhou.websocket.cluster.action.Action;
import com.github.xiaobingzhou.websocket.cluster.action.BroadCastAction;
import com.github.xiaobingzhou.websocket.cluster.action.SendMessageAction;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 使用redis实现websocket管理器
 * @author xiaobingzhou
 * @date 2020/7/17 13:57
 * @since 1.0.0
 */
public class RedisWebSocketManager extends MemoryWebSocketManager {
    public static final String CHANNEL = "websocket";
    protected StringRedisTemplate stringRedisTemplate;

    public RedisWebSocketManager(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void sendMessage(String identifier, String message) {
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put(Action.ACTION, SendMessageAction.class.getName());
        jsonObject.put(Action.IDENTIFIER, identifier);
        jsonObject.put(Action.MESSAGE, message);

        // 在websocket频道上发布sendMessage消息
        publish(jsonObject.toJSONString());

    }

    @Override
    public void broadcast(String message) {
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put(Action.ACTION, BroadCastAction.class.getName());
        jsonObject.put(Action.MESSAGE, message);

        // 在websocket频道上发布广播消息
        publish(jsonObject.toJSONString());
    }

    protected void publish(String s) {
        // 在websocket频道上发布消息
        stringRedisTemplate.convertAndSend(getChannel(s), s);
    }

    protected String getChannel(String s) {
        return CHANNEL;
    }
}
