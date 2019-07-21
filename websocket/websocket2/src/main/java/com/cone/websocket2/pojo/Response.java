package com.cone.websocket2.pojo;
/**服务器向浏览器发送的消息实体类
*@author Cone
*@Version 2019年7月21日 下午4:11:08
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
