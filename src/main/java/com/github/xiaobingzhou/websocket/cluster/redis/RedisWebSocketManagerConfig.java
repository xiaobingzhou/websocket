package com.github.xiaobingzhou.websocket.cluster.redis;

import com.github.xiaobingzhou.websocket.cluster.Receiver;
import com.github.xiaobingzhou.websocket.cluster.action.ActionConfig;
import com.github.xiaobingzhou.websocket.WebSocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 配置redis消息订阅功能
 * @author xiaobingzhou
 * @date 2020/7/17 11:02
 * @since 1.0.0
 */
@Configuration
@Import(ActionConfig.class)
public class RedisWebSocketManagerConfig {

    @Bean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public RedisWebSocketManager webSocketManager(StringRedisTemplate stringRedisTemplate) {
        System.out.println("RedisWebSocketManager");
        return new RedisWebSocketManager(stringRedisTemplate);
    }

    @Bean(name = "receiver")
    public Receiver receiver() {
        return new RedisReceiver();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(@Qualifier("receiver") RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, RedisReceiver.RECEIVER_METHOD_NAME);
    }

    @Bean(name = "redisMessageListenerContainer")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisWebSocketManager.CHANNEL));
        return container;
    }

}
