package com.cone.security.entity;

import java.io.Serializable;

public class SysRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8897133497937328338L;
	
	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}






}
