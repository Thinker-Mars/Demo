package com.cone.security.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.cone.security.filter.VerifyFilter;

@Configuration//标识该类是配置类
@EnableWebSecurity//开启 Security 服务
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启全局 Securtiy注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	//自动登陆
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
	
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

			@Override
			public String encode(CharSequence charSequence) {
				
				return charSequence.toString();
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				// TODO Auto-generated method stub
				return s.equals(charSequence.toString());
			}
			
		});
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeRequests()
		.antMatchers("/getVerifyCode").permitAll()
		.anyRequest().authenticated()
		.and()
		//设置登录页
		.formLogin().loginPage("/login")
		//设置登陆成功页
		.defaultSuccessUrl("/").permitAll()
		.failureUrl("/login/error")
		.and()
		//过滤器,有两个参数,作用是在参数二之前执行参数一设置的过滤器
		.addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)
		.logout().permitAll()
		//自动登陆
		.and().rememberMe()
		.tokenRepository(persistentTokenRepository())
		//有效时间,秒
		.tokenValiditySeconds(60)
		.userDetailsService(userDetailsService);
		
		
		//关闭csrf跨域
		httpSecurity.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		//设置拦截忽略文件夹，可以对静态资源放行
		webSecurity.ignoring().antMatchers("/css/**", "/js/**");
	}

}
