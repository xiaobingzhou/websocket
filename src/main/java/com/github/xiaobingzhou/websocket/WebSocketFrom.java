package com.github.xiaobingzhou.websocket;

/**
 * websocket连接的来源
 * @author xiaobingzhou
 * @date 2020/7/17 16:07
 * @since 1.0.0
 */
public enum WebSocketFrom {

    WEB, APP, DEVICE;

    public static WebSocketFrom getFrom(String from) {
        WebSocketFrom[] values = WebSocketFrom.values();
        for (WebSocketFrom value : values) {
            if (value.toString().equalsIgnoreCase(from)){
                return value;
            }
        }
        throw new IllegalArgumentException("WebSocketFrom not support " + from);
    }
}
