package com.github.xiaobingzhou.websocket.cluster.action;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 将所有的Action配置进容器，通过名字找到
 * @author xiaobingzhou
 * @date 2020/7/16 21:21
 * @since 1.0.0
 */
@Configuration
@Import({SendMessageAction.class , BroadCastAction.class , RemoveAction.class , NoActionAction.class})
public class ActionConfig {
}
