package com.cone.security.entity;

import java.io.Serializable;

public class SysUserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6531369879901942735L;
	
	private Integer userId;
	
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
