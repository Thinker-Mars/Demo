package com.cone.websocket3.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.cone.websocket3.pojo.Message;

/**
*@author Cone
*@Version 2019年7月21日 下午10:48:01
*/
@Controller
public class WebSocketController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@MessageMapping("/chat")
	public void handleChat(Principal principal, Message message) {
		
		if (principal.getName().equals("yang")) {
			
			template.convertAndSendToUser("wang", "/queue/notifications", principal.getName()
					+ "-send:" + message.getName());
		}
		else {
			
			template.convertAndSendToUser("yang", "/queue/notifications", principal.getName()
					+ "-send:" + message.getName());
		}
	}

}
