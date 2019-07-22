package com.cone.websocket3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
*@author Cone
*@Version 2019年7月21日 下午10:01:43
*/
@Configuration
@EnableWebSocketMessageBroker
//开启使用STOMP协议来传输基于代理(message broker)的消息
//此时浏览器支持使用@MessageMapping  就像支持@RequestMapping一样
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	/**
	 * 注册协议节点
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		//注册一个Stomp协议的endpoint,并指定 SockJS协议
		registry.addEndpoint("/endpointWisely").withSockJS();
	}
	
	/**
	 * 配置消息代理
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		//点对点式增加一个/queue消息代理
		registry.enableSimpleBroker("/queue");
	}

}
