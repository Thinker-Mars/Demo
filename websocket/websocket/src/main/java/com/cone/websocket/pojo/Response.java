package com.cone.websocket.pojo;
/** 服务器向浏览器发送的消息实体类
*@author Cone
*@Version 2019年7月20日 下午10:35:26
*/
public class Response {
	
	private String responseMessage;
	
	public Response(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	

}
