package com.cone.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.cone.websocket.pojo.Message;
import com.cone.websocket.pojo.Response;

/**
*@author Cone
*@Version 2019年7月20日 下午10:38:49
*/
@Controller
public class WebSocketController {
	
	@MessageMapping("/Welcome")//浏览器发送请求到此地址
	@SendTo("/topic/getResponse")//当服务端有消息时，向所有订阅了/topic/getResponse的浏览器发送消息
	public Response say(Message message) throws Exception {
		
		Thread.sleep(1000);
		return new Response("Welcome," + message.getName() + "!");
	}

}
