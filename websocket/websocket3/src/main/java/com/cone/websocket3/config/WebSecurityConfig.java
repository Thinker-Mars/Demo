package com.cone.websocket3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.cone.websocket3.util.MyPasswordEncoder;

/**
*@author Cone
*@Version 2019年7月21日 下午10:24:14
*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	/**
	 * 在内存中配置两个用户，角色为user
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		.inMemoryAuthentication().passwordEncoder(myPasswordEncoder)
		.withUser("yang").password("yang").roles("USER")
		.and()
		.withUser("wang").password("wang").roles("USER");
	}
	
	/**
	 * 忽略对静态资源的拦截
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/resources/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
		.antMatchers("/","/login").permitAll()//根路径和login路径不拦截
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")//登陆页面路径为/login
		.defaultSuccessUrl("/chat")//登陆成功转向chat页面
		.permitAll()
		.and()
		.logout()
		.permitAll();
	}

}
