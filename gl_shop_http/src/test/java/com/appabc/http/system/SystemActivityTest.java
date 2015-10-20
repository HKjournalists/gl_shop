package com.appabc.http.system;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月23日 上午9:50:02
 */

public class SystemActivityTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)  
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		//loginSimulation("18613064147", "170", "000000915102014");
		request.setRequestURI("/noAuthUrl/system/activity/join");
		request.setMethod("POST");
		
		request.setParameter("phone", "15811822329");
		request.setParameter("name", "小AA");
		request.setParameter("reqnum", "1000");
		request.setParameter("remark", "remark");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
