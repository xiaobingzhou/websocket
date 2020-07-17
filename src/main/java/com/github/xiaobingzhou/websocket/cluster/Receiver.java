package com.github.xiaobingzhou.websocket.cluster;

/**
 * 接收到订阅消息接口
 * @author xiaobingzhou
 * @date 2020/7/17 16:05
 * @since 1.0.0
 */
@FunctionalInterface
public interface Receiver {
    String RECEIVER_METHOD_NAME = "receiveMessage";
    /**
     * 回调方法
     * @param message 接收到的消息
     */
    void receiveMessage(String message);
}
