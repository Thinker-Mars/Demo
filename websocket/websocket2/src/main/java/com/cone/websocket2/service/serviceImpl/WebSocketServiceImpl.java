package com.cone.websocket2.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cone.websocket2.pojo.Message;
import com.cone.websocket2.pojo.Response;
import com.cone.websocket2.service.WebSocketService;

/**
*@author Cone
*@Version 2019年7月21日 下午4:16:53
*/
@Service
public class WebSocketServiceImpl implements WebSocketService {
	
	@Autowired
	private SimpMessagingTemplate template;//使用 spring的SimpMessagingTemplate类，实现广播式推送
	
	@Override
	public void sendMesage(Message message) throws Exception {
		
		template.convertAndSend("/topic/getResponse", new Response(message.getName()));
	}

}
