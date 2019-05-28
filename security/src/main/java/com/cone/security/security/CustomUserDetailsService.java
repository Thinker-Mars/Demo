package com.cone.security.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cone.security.entity.SysRole;
import com.cone.security.entity.SysUser;
import com.cone.security.entity.SysUserRole;
import com.cone.security.service.SysRoleService;
import com.cone.security.service.SysUserRoleService;
import com.cone.security.service.SysUserService;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		//获取用户信息
		SysUser user = sysUserService.selectByName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在.");
		}
		//添加权限
		List<SysUserRole> userRoles = sysUserRoleService.listByUserId(user.getId());
		for (SysUserRole userRole : userRoles) {
			SysRole role = sysRoleService.selectById(userRole.getRoleId());
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		//返回UserDetails实现类
		return new User(user.getName(), user.getPassword(), authorities);
	}

}
