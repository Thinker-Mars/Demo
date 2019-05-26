package com.cone.sunny;

import org.activiti.spring.boot.SecurityAutoConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.env.Environment;

//import javax.sql.DataSource;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SunnyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SunnyApplication.class, args);
	}

}
