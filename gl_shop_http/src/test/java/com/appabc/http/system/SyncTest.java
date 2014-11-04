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
 * @Create_Date  : 2014年10月9日 下午9:19:02
 */

public class SyncTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)  
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
//		syncGetInfos();
		
	}
	
	public void syncGetInfos(){
		request.setRequestURI("/sync/getInfo");
		request.setMethod("GET");
		
//		request.addParameter("typeInfo", "[{\"type\":1,\"timeStamp\":\"2014-5-6\"},{\"type\":2,\"timeStamp\":\"2014-5-6\"},{\"type\":3,\"timeStamp\":\"2014-5-6\"},{\"type\":4,\"timeStamp\":\"2014-5-6\"}]");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
