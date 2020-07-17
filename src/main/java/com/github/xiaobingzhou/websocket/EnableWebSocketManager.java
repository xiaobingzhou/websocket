package com.github.xiaobingzhou.websocket;

import com.github.xiaobingzhou.websocket.cluster.ClusterType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * websocket管理器启用注解
 * @author xiaobingzhou
 * @date 2020/7/17 16:08
 * @since 1.0.0
 * @see WebSocketManagerConfigurationSelector
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(WebSocketManagerConfigurationSelector.class)
public @interface EnableWebSocketManager {

    boolean enableCluster() default true;

    ClusterType type() default ClusterType.REDIS;

}
