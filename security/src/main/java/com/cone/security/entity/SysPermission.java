package com.cone.security.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class SysPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 135045562031438524L;
	
    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;

    @SuppressWarnings("rawtypes")
	private List permissions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@SuppressWarnings("rawtypes")
	public List getPermissions() {
		return Arrays.asList(this.permission.trim().split(","));
	}

	@SuppressWarnings("rawtypes")
	public void setPermissions(List permissions) {
		this.permissions = permissions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
