package com.appabc.http.purse;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年10月17日 下午3:37:49
 */

public class PurseTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)  
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		this.testInitializePurseAccount();
	}
	
	public void testGetPurseList(){
		request.setRequestURI("/purse/getPurseList");
        request.setMethod(HttpMethod.POST.name());
        //request.addParameter("status", "1");
        //request.addParameter("pageIndex", "-1");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}
	
	public void testInitializePurseAccount(){
		request.setRequestURI("/purse/initializePurseAccount");
        request.setMethod(HttpMethod.POST.name());
        request.addParameter("type", "1");
        //request.addParameter("pageIndex", "-1");
        try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.debug(e.getMessage(), e);
		}
	}

}
