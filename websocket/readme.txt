socket链接广播式通信规则
1、注册一个Stomp 协议的endpoint,并指定 SockJS协议

2、广播式应配置一个/topic 消息代理

3、浏览器发送请求通过@messageMapping 映射到某个方法（和@RequestMapping类似，url请求转为方法调用）

4、服务器端有消息时，会向所有订阅了消息代理的浏览器发送信息