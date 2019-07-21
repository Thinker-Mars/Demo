package com.cone.websocket2.controller;
/**
*@author Cone
*@Version 2019年7月21日 下午4:26:43
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cone.websocket2.pojo.Message;
import com.cone.websocket2.service.serviceImpl.WebSocketServiceImpl;

@Controller
public class WebSocketController {
	
	@Autowired
	private WebSocketServiceImpl ws;
	
	@MessageMapping("/Welcome")
	@ResponseBody
	public void say(Message message) throws Exception {
		
		ws.sendMesage(message);
		
	}

}
