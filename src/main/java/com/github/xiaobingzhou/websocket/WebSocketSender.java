package com.github.xiaobingzhou.websocket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 本地websocket连接消息发送器
 * @author xiaobingzhou
 * @date 2020/7/17 16:07
 * @since 1.0.0
 */
@Slf4j
public class WebSocketSender {

    static final int size = 1 << 3; // 8
    static ExecutorService[] executors = new ExecutorService[size];

    static {
        for (int i = 0; i < size; i++) {
            executors[i] = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1 << 12),// 4096
                    new SenderThreadFactory("ws-sender"),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }
    }

    /**
     * 根据session的id进行hash映射到线程池上的线程
     * @param session
     * @param message
     */
    /**
     * 根据session的id进行hash映射到线程池上的线程
     *
     * <p>getBasicRemote 和 getAsyncRemote 当同一个session并发操作时会出现异常:
     * 		java.lang.IllegalStateException: The remote endpoint was in state [TEXT_FULL_WRITING] which is an invalid state for called method</p>
     * <p></p>
     * <p>getBasicRemote方法需要在session上加锁，同步操作</p>
     * <p></p>
     * <p>getAsyncRemote方法在session上加锁，也可能会出现并发操作session导致发生异常：
     * 		java.lang.IllegalStateException: The remote endpoint was in state [TEXT_FULL_WRITING] which is an invalid state for called method</p>
     * <p></p>
     * <p>解决方案：使用getBasicRemote方法同时session加锁; 可以使用多线程优化群发功能，同一个session使用同一个线程发送</p>
     * @see <code>https://blog.csdn.net/wy_xing/article/details/82744559</code>
     * @param message
     */
    public static void sendMessage(final Session session, final String message) {
        if (session == null)
            return;

        int idx = (executors.length - 1) & session.getId().hashCode();
        executors[idx].execute(() -> {
            synchronized (session) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("websocket 同步发送, sessionId={}, message={}", session.getId(), message);
                    }
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("websocket 同步发送异常" + e);
                }
            }
        });
    }


    /**
     * 自定义线程工厂
     */
    static class SenderThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final String namePrefix;

        SenderThreadFactory(String pool) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = pool + "-" + poolNumber.getAndIncrement() + "-one";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix,
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
