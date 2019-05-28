package com.cone.security.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@RequestMapping("/")
	public String showHome() {
		//获取当前登陆用户
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登陆用户:" + name);
		return "home.html";
	}
	
	@RequestMapping("/login")
	public String showLogin() {
		return "login.html";
	}
	
	@RequestMapping("/admin")
	@ResponseBody
	@PreAuthorize("hasPermission('/admin','r')")//判断用户是否拥有权限，否则不能访问
	public String printAdminR() {
		return "如果你看到这句话，说明你访问/admin具有r权限";
	}
	
	@RequestMapping("/admin/c")
	@ResponseBody
	@PreAuthorize("hasPermission('/admin','c')")//判断用户是否拥有权限，否则不能访问
	public String printAdminC() {
		return "如果你看到这句话，说明你访问/admin具有c权限";
	}
	
//	@RequestMapping("/admin")
//	@ResponseBody
//	@PreAuthorize("hasRole('ROLE_ADMIN')")//判断用户是否拥有权限，否则不能访问
//	public String printAdmin() {
//		return "如果你看到这句话，说明你有admin角色";
//	}
	
	@RequestMapping("/user")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER')")
	public String printUser() {
		return "如果你看到这句话，说明你有user角色";
	}

	@RequestMapping("/login/error")
	public void loginError(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		AuthenticationException exception = (AuthenticationException)request.getSession()
				.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		try {
			response.getWriter().write(exception.toString());
			log.info("出现异常.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
