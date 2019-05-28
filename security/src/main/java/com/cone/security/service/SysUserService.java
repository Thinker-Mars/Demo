package com.cone.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.security.entity.SysUser;
import com.cone.security.mapper.SysUserMapper;

@Service
public class SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	public SysUser selectById(Integer id) {
		return sysUserMapper.selectById(id);
	}
	
	public SysUser selectByName(String name) {
		return sysUserMapper.selectByName(name);
	}

}
