package com.appabc.system;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Zou Xifeng
 * @version     : 1.0
 * Create Date  : Oct 21, 2014 3:05:02 PM
 */
@WebAppConfiguration
@ActiveProfiles("develop")
@ContextConfiguration(locations={"classpath*:test-context.xml",
		"classpath*:applicationContext-redis.xml",
		"classpath*:applicationContext-pay.xml",
		"classpath*:applicationContext-datas.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemUserServiceTest {
	
	@BeforeClass
	public static void setSystemProperties() {
		System.setProperty("local.config", "classpath*:/local-config.properties");
	}
	
	@Autowired
	private UserService userService;
	
	@Test
	public void setupSysUsers() {
		final String userNamePrefix = "SysUser";
		final int userCount = 332;
		final String basePassword = "12309812";
		for (int i = 0; i < userCount; i++) {
			User user = new User();
			user.setUserName(userNamePrefix + i);
			user.setRealName(userNamePrefix + i);
			user.setPassword(basePassword);
			user.setCreateTime(new Date());
			userService.createUser(user);
		}
	}
	
}
