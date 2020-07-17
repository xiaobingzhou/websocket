# message-frame
### 1.0.0 版本支持功能 
> websocket集群支持

---
```java
/**
* 使用例子
*/
@ServerEndpoint(value="/webSocket/{identifier}")
public class WebSocketEndpoint extends AbstractWebSocketEndpoint {

    public WebSocketEndpoint(WebSocketManager webSocketManager) {
        super(webSocketManager);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        if (logger.isInfoEnabled()) {
            logger.info("*** WebSocket opened sessionId: {}, identifier: {}", session.getId(), identifier);
        }

        connect(session, identifier);
    }

    //
    @OnMessage
    public void onMessage(String message, Session session, @PathParam(IDENTIFIER) String identifier) {
        if (logger.isInfoEnabled()) {
            logger.info("*** WebSocket 接收到的数据为: {}, sessionId: {}, identifier: {}", message, session.getId(), identifier);
        }

        receiveMessage(message, session, identifier);
    }

    //
    @OnClose
    public void onClose(Session session, @PathParam(IDENTIFIER) String identifier) {
        if (logger.isInfoEnabled()) {
            logger.info("*** WebSocket closed sessionId: {}, identifier: {}", session.getId(), identifier);
        }

        disconnect(session, identifier);
    }

    //
    @OnError
    public void onError(Throwable t, Session session, @PathParam(IDENTIFIER) String identifier){
        if (logger.isInfoEnabled()) {
            logger.info("*** WebSocket 发生异常: {}, sessionId: {}, identifier: {}", t.getMessage(), session.getId(), identifier);
        }

        if (logger.isErrorEnabled()) {
            logger.error(t.getMessage(), t);
        }

        disconnect(session, identifier);
    }
}

```

## 使用方式

### 1、下载源码
```java
git clone https://github.com/xiaobingzhou/websocket.git
```
### 2、使用maven安装到本地仓库(需要跳过gpg检查)
```java
mvn clean install -Dgpg.skip
```
### 3、获取jar包
```java
cd target
```
