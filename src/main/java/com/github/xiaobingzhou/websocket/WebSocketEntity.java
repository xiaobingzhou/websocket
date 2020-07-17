package com.github.xiaobingzhou.websocket;

import lombok.Data;

import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class WebSocketEntity<T> {
    /**
     * 连接集合
     * 线程安全list，防止遍历时ConcurrentModificationException
     */
    private List<Session> sessions = new CopyOnWriteArrayList<>();

    /**
     * 连接来源
     */
    private WebSocketFrom from;

    /**
     * 唯一标识，可以是用户会话id或是设备id
     */
    private String identifier;

    /**
     * 其他信息
     */
    private T data;


    public boolean addSession(Session session) {
        return sessions.add(session);
    }

    public boolean removeSession(Session session) {
        return sessions.remove(session);
    }


}
