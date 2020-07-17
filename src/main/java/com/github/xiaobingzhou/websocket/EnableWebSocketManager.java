package com.github.xiaobingzhou.websocket;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({WebSocketManagerConfig.class})
public @interface EnableWebSocketManager {
}
