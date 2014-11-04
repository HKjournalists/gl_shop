/**
 *
 */
package com.appabc.http.user;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午3:31:15
 */
public class UserTest2  extends AbstractHttpControllerTest {

	@Test
	public void mainTest() {
//		msmSend();
//		register();
	}
	
	/**
	 * 验证码发送
	 */
	public void msmSend(){
		request.setRequestURI("/smscode/send");
        request.setMethod("POST");
        
        request.addParameter("phone", "15811822330");
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户注册
	 */
	public void register(){

		request.setRequestURI("/user/register");
        request.setMethod("POST");
        
        request.addParameter("phone", "15811822330");
        request.addParameter("smsCode", "6112");
        request.addParameter("username", "15811822330");
        request.addParameter("password", "e10adc3949ba59abbe56e057f20f883e");
        
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

}
