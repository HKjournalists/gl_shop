/**
 *
 */
package com.appabc.http.sms;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月11日 上午11:46:22
 */
public class SmsSendTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		smsSend();
	}
	
	public void smsSend(){
		request.setRequestURI("/smscode/send");
        request.setMethod(HttpMethod.POST.name());
        request.addParameter("phone", "15811822330");
        request.addParameter("sendType", "");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}

}
