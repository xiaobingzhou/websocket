package com.github.xiaobingzhou.websocket.cluster;

@FunctionalInterface
public interface Receiver {
    String RECEIVER_METHOD_NAME = "receiveMessage";
    /**
     * 回调方法
     * @param message 接收到的消息
     */
    void receiveMessage(String message);
}
