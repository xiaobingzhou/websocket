package com.github.xiaobingzhou.websocket;

import com.github.xiaobingzhou.websocket.cluster.ClusterType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(WebSocketManagerConfigurationSelector.class)
public @interface EnableWebSocketManager {

    boolean enableCluster() default true;

    ClusterType type() default ClusterType.REDIS;

}
