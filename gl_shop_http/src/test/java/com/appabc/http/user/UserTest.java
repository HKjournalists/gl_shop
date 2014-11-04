package com.appabc.http.user;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午3:19:43
 */
public class UserTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)  
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		//testGetUser();
		//testGetSingleUser();
		//testGetSingleUserEx();
		//testSaveUser();
	}

	void testSaveUser(){
		request.setRequestURI("/user/saveUser");
        request.setMethod("POST");
        //request.addParameter("cid", "");
        request.addParameter("username", "myusername咏珊");
        request.addParameter("password", "597845");
        request.addParameter("nick", "my nick");
        request.addParameter("phone", "13845612589");
        request.addParameter("logo", "llooggoo");
        request.addParameter("status", "0");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void testGetUser(){
		request.setRequestURI("/user/getUser");
        request.setMethod("GET");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void testGetSingleUser(){
		request.setRequestURI("/user/getSingleUser");
        request.setMethod("POST");
        request.addParameter("userId", "59");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void testGetSingleUserEx(){
		request.setRequestURI("/user/getSingleUserEx");
        request.setMethod("POST");
        request.addParameter("userId", "59");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
