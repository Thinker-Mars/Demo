package com.cone.sunny.test;

import javax.activation.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class datasourceTest {
	
	   @Autowired
	   DataSourceProperties dataSourceProperties;

	   @Autowired
	   ApplicationContext applicationContext;
	   
	   @Test
	   public void test() {
		   DataSource dataSource = applicationContext.getBean(DataSource.class);
		      System.out.println(dataSource);
		      System.out.println(dataSource.getClass().getName());
		      System.out.println(dataSourceProperties);
	   }

}
