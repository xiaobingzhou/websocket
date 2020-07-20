# message-frame
### v1.0.0 版本支持功能
- websocket集群管理功能（当前只有redis消息订阅发布这一种实现方式，其实也可以使用消息队列来实现）
---
### 使用用法
- 启用websocket管理器功能使用注解: @EnableWebSocketManager 
- 默认启用的是websocket本地内存管理器: MemoryWebSocketManager
- 需要开启websocket集群管理器，请设置注解 @EnableWebSocketManager的enableCluster属性为true

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
