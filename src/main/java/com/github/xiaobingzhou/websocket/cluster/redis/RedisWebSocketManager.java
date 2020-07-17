package com.github.xiaobingzhou.websocket.cluster.redis;

import com.alibaba.fastjson.JSON;
import com.github.xiaobingzhou.websocket.MemoryWebSocketManager;
import com.github.xiaobingzhou.websocket.cluster.action.Action;
import com.github.xiaobingzhou.websocket.cluster.action.BroadCastAction;
import com.github.xiaobingzhou.websocket.cluster.action.RemoveAction;
import com.github.xiaobingzhou.websocket.cluster.action.SendMessageAction;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class RedisWebSocketManager extends MemoryWebSocketManager {
    public static final String CHANNEL = "websocket";
    protected StringRedisTemplate stringRedisTemplate;

    public RedisWebSocketManager(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private String getChannel() {
        return CHANNEL;
    }

    @Override
    public void remove(String identifier, Session session) {
        boolean containsKey = localWebSocketMap().containsKey(identifier);
        // 本地找到直接移除
        if(containsKey){
            super.doRemove(identifier, session);
        }else {
            Map<String , Object> map = new HashMap<>(2);
            map.put(Action.ACTION , RemoveAction.class.getName());
            map.put(Action.IDENTIFIER , identifier);
            map.put(Action.SESSIONID , session.getId());
            //在websocket频道上发布发送消息的消息
            stringRedisTemplate.convertAndSend(getChannel(), JSON.toJSONString(map));
        }
    }


    @Override
    public void sendMessage(String identifier, String message) {
        boolean containsKey = localWebSocketMap().containsKey(identifier);
        //  本地能找到就直接发
        if(containsKey){
            super.doSendMessage(identifier, message);
        } else {
            Map<String , Object> map = new HashMap<>(3);
            map.put(Action.ACTION , SendMessageAction.class.getName());
            map.put(Action.IDENTIFIER , identifier);
            map.put(Action.MESSAGE , message);
            //在websocket频道上发布发送消息的消息
            stringRedisTemplate.convertAndSend(getChannel(), JSON.toJSONString(map));
        }

    }

    @Override
    public void broadcast(String message) {
        Map<String , Object> map = new HashMap<>(2);
        map.put(Action.ACTION , BroadCastAction.class.getName());
        map.put(Action.MESSAGE , message);
        //在websocket频道上发布广播的消息
        stringRedisTemplate.convertAndSend(getChannel(), JSON.toJSONString(map));
    }
}
