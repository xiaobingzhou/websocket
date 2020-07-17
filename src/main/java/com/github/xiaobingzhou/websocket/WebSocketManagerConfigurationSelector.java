package com.github.xiaobingzhou.websocket;

import com.github.xiaobingzhou.websocket.cluster.ClusterType;
import com.github.xiaobingzhou.websocket.cluster.redis.RedisWebSocketManagerConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 根据注解 {@code EnableWebSocketManager} 动态选择配置类
 * @author xiaobingzhou
 * @date 2020/7/17 14:19
 * @since 1.0.0
 * @see EnableWebSocketManager
 * @see MemoryWebSocketManagerConfig
 * @see RedisWebSocketManagerConfig
 */
public class WebSocketManagerConfigurationSelector implements ImportSelector {

    public static final String ENABLE_CLUSTER = "enableCluster";
    public static final String TYPE = "type";

    @Override
    public final String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableWebSocketManager.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format(
                    "@%s is not present on importing class '%s' as expected",
                    ClusterType.class.getSimpleName(), importingClassMetadata.getClassName()));
        }

        // 是否启用集群配置
        if (enableCluster(attributes)) {
            ClusterType clusterType = attributes.getEnum(getClusterType());
            String[] imports = selectImports(clusterType);
            if (imports == null) {
                throw new IllegalArgumentException("Unknown ClusterType: " + clusterType);
            }
            return imports;
        }

        return new String[]{ MemoryWebSocketManagerConfig.class.getName() };
    }

    protected String[] selectImports(ClusterType type) {
        if (type == ClusterType.REDIS) {
            return new String[]{ RedisWebSocketManagerConfig.class.getName() };
        }
        return null;
    }

    protected String getClusterType() {
        return TYPE;
    }

    protected boolean enableCluster(AnnotationAttributes attributes) {
        return attributes.getBoolean(ENABLE_CLUSTER);
    }
}
