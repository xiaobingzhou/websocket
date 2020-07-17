package com.github.xiaobingzhou.websocket.cluster.redis;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.websocket.MemoryWebSocketManager;
import com.github.xiaobingzhou.websocket.WebSocketEntity;
import com.github.xiaobingzhou.websocket.cluster.action.Action;
import com.github.xiaobingzhou.websocket.cluster.action.BroadCastAction;
import com.github.xiaobingzhou.websocket.cluster.action.RemoveAction;
import com.github.xiaobingzhou.websocket.cluster.action.SendMessageAction;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.websocket.Session;
import java.util.Optional;

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
    public void remove(String identifier, Session session) {
        WebSocketEntity webSocketEntity = localWebSocketMap().get(identifier);
        // 根据标识查看本地是否有连接
        if (webSocketEntity != null) {
            Optional match = webSocketEntity.getSessions().stream().filter(s -> s == session).findFirst();
            // 只有当session匹配到了，才确认这个session在本地
            if (match.isPresent()) {
                super.doRemove(identifier, session);
                return;
            }
        }

        // 根据identifier本地未找到，或者session没有匹配到，在websocket频道发布消息
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put(Action.ACTION, RemoveAction.class.getName());
        jsonObject.put(Action.IDENTIFIER, identifier);
        jsonObject.put(Action.SESSIONID, session.getId());

        // 在websocket频道上发布发送消息的消息
        publish(jsonObject.toJSONString());
    }

    @Override
    public void sendMessage(String identifier, String message) {
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put(Action.ACTION, SendMessageAction.class.getName());
        jsonObject.put(Action.IDENTIFIER, identifier);
        jsonObject.put(Action.MESSAGE, message);

        // 在websocket频道上发布发送消息的消息
        publish(jsonObject.toJSONString());

    }

    @Override
    public void broadcast(String message) {
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put(Action.ACTION, BroadCastAction.class.getName());
        jsonObject.put(Action.MESSAGE, message);

        // 在websocket频道上发布广播的消息
        publish(jsonObject.toJSONString());
    }

    protected void publish(String s) {
        // 在websocket频道上发布发送消息的消息
        stringRedisTemplate.convertAndSend(getChannel(), s);
    }

    private String getChannel() {
        return CHANNEL;
    }
}
