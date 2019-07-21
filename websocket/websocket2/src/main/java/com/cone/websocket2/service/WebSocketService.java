package com.cone.websocket2.service;

import com.cone.websocket2.pojo.Message;

/**
*@author Cone
*@Version 2019年7月21日 下午4:15:37
*/
public interface WebSocketService {
	
	public void sendMesage(Message message) throws Exception;

}
