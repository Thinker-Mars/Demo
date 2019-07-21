package com.cone.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
*@author Cone
*@Version 2019年7月20日 下午10:14:38
*/
@Configuration
@EnableWebSocketMessageBroker
//开启使用STOMP协议来传输基于代理(message broker)的消息
//此时浏览器支持使用@MessageMapping  就像支持@RequestMapping一样
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		//注册一个Stomp协议的endpoint,并指定 SockJS协议
		registry.addEndpoint("/endpointWisely").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		//广播式应配置一个/topic消息代理
		registry.enableSimpleBroker("/topic");
	}

}
