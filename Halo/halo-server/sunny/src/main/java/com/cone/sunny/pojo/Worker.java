package com.cone.sunny.pojo;

import java.io.Serializable;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//员工信息实体类
@SuppressWarnings("serial")
public class Worker implements Serializable {
	private Integer id;
	private String workerName;
	private String sex;
	private String password;
	private String role;
	private String username;
	
	public Worker() {
		
	}
	
	public Worker(Integer id, String workerName, String sex, String password, String role, String username) {
		this.id = id;
		this.workerName = workerName;
		this.sex = sex;
		this.password = password;
		this.role = role;
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWorkerName() {
		return workerName;
	}
	
	public void setName(String workerName) {
		this.workerName = workerName;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	

}
