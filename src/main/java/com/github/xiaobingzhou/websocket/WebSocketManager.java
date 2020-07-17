package com.github.xiaobingzhou.websocket;

import javax.websocket.Session;
import java.util.Map;

public interface WebSocketManager {
    /**
     * 在容器中的名字
     */
    String WEBSOCKET_MANAGER_NAME  = "webSocketManager";

    /**
     * 根据标识获取websocket session
     * @param identifier 标识
     * @return WebSocket
     */
    WebSocketEntity get(String identifier);

    /**
     * 放入一个 websocket session
     * @param identifier 标识
     * @param webSocketEntity WebSocketEntity
     * @param session 会话
     */
    void put(String identifier, WebSocketEntity webSocketEntity, Session session);

    /**
     * 删除
     * @param identifier 标识
     */
    void remove(String identifier, Session session);

    /**
     * 获取当前机器上的保存的 WebSocketEntity
     * @return WebSocketEntity Map
     */
    Map<String , WebSocketEntity> localWebSocketMap();

    /**
     * 统计所有在线人数
     * @return 所有在线人数
     */
    default int size(){
        return localWebSocketMap().size();
    }

    /**
     * 给某人发送消息
     * @param identifier 标识
     * @param message 消息
     */
    void sendMessage(String identifier, String message);

    /**
     * 广播
     * @param message 消息
     */
    void broadcast(String message);

    /**
     * WebSocket接收到消息的函数调用
     * @param identifier 标识
     * @param session
     * @param message 消息内容
     */
    void onMessage(String identifier, String message, Session session);

    /**
     * 在OnMessage中判断是否是心跳,
     * 从客户端的消息判断是否是ping消息
     * @param identifier 标识
     * @param message 消息
     * @return 是否是ping消息
     */
    default boolean isPing(String identifier, String message, Session session){
        return "ping".equalsIgnoreCase(message);
    }

    /**
     * 返回心跳信息
     * @param identifier 标识
     * @param message 消息
     * @return 返回的pong消息
     */
    default String pong(String identifier, String message, Session session){
        return "pong";
    }
}
