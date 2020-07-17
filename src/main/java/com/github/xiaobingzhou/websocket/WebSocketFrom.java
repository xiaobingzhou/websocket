package com.github.xiaobingzhou.websocket;

public enum WebSocketFrom {

    WEB, APP, DEVICE;

    public static WebSocketFrom getFrom(String from) {
        WebSocketFrom[] values = WebSocketFrom.values();
        for (WebSocketFrom value : values) {
            if (value.toString().equalsIgnoreCase(from)){
                return value;
            }
        }
        throw new IllegalArgumentException("WebSocketFrom not support " +from);
    }
}
