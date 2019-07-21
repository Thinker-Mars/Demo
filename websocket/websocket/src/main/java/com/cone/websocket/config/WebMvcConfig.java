package com.cone.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
*@author Cone
*@Version 2019年7月21日 下午12:12:38
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//为ws.html提供路径映射
		registry.addViewController("/ws").setViewName("/ws");
	}

}
