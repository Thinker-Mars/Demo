package com.cone.sunny.api;

import com.alibaba.druid.support.json.JSONParser;

public class AjaxResult {

	// 返回状态码   (默认1:成功,0:失败)
	private int code = 0;

	// 返回的中文消息
	private String message;

	// 成功时携带的数据
	private Object data;
	
	// 用于存放返回结果为websocket点对点用户信息
	private String socketUser;

	public String getSocketUser() {
		return socketUser;
	}

	public void setSocketUser(String socketUser) {
		this.socketUser = socketUser;
	}

	public int getCode() {
		return code;
	}

	public AjaxResult setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}
	
	public AjaxResult setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public AjaxResult setData(Object data) {
		this.data = data;
		return this;
	}

	public AjaxResult addSuccess(String message) {
		this.message = message;
		this.code = 1;
		this.data = null;
		return this;
	}

	public AjaxResult addError(String message) {
		this.message = message;
		this.code = 0;
		this.data = null;
		return this;
	}

	public AjaxResult addFail(String message) {
		this.message = message;
		this.code = 999;
		this.data = null;
		return this;
	}

	public AjaxResult addWarn(String message) {
		this.message = message;
		this.code = 2;
		this.data = null;
		return this;
	}

	public AjaxResult success(Object data) {
		this.message = "success";
		this.data = data;
		this.code = 1;
		return this;
	}

	public boolean isSuccess() {
		return getCode() == 1;
	}

	@Override
	public String toString() {
		return "{\"code\":"+this.getCode()+",\"message\":"+this.getMessage()+",\"data\":"+this.getData()+",\"socketUser\":"+this.getSocketUser()+"}";
	}
	
	
}

