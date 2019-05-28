package com.cone.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.security.entity.SysUserRole;
import com.cone.security.mapper.SysUserRoleMapper;

@Service
public class SysUserRoleService {
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	public List<SysUserRole> listByUserId(Integer userId) {
		return sysUserRoleMapper.listByUserId(userId);
	}

}
