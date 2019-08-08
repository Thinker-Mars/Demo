package com.cone.thu;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
*@author Cone
*@Version 2019年8月2日 上午10:15:58
*/
@RunWith(SpringJUnit4ClassRunner.class)
//声明配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"
	,"classpath:spring/spring-service.xml"})
public class BaseTest {

}
