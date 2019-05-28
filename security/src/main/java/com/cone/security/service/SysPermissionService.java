package com.cone.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.security.entity.SysPermission;
import com.cone.security.mapper.SysPermissionMapper;

@Service
public class SysPermissionService {
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	public List<SysPermission> listByRoleId(Integer roleId) {
		return sysPermissionMapper.listByRoleId(roleId);
	}

}
