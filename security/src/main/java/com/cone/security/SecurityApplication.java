package com.cone.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.cone.security.servlet.VerifyServlet;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	 public ServletRegistrationBean indexServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new VerifyServlet());
		registration.addUrlMappings("/getVerifyCode");
		return registration;
	}

}
