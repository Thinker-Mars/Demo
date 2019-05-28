package com.cone.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.security.entity.SysRole;
import com.cone.security.mapper.SysRoleMapper;

@Service
public class SysRoleService {
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	public SysRole selectById(Integer id) {
		return sysRoleMapper.selectById(id);
	}
	
	public SysRole selectByName(String name) {
		return sysRoleMapper.selectByName(name);
	}

}
