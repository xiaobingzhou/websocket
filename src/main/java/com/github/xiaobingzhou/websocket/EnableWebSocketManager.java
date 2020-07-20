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

    /**
     * 是否启用集群管理器
     * @return
     */
    boolean enableCluster() default false;

    /**
     * <p>选择集群管理器的实现方式</p>
     * <p>ps: 只有当启用集群管理器({@code enableCluster=true})时，type配置才有效</p>
     * @return
     */
    ClusterType type() default ClusterType.REDIS;

}
