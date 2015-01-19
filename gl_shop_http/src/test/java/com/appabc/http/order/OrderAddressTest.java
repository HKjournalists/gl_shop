/**
 *
 */
package com.appabc.http.order;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月13日 上午11:14:33
 */
public class OrderAddressTest extends AbstractHttpControllerTest {
	/* (non-Javadoc)
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		testGetAddressByFid();
	}
	
	public void testGetAddressByFid(){
		request.setRequestURI("/order/address/getAddress");
		request.setMethod("GET");
		
		request.addParameter("fid", "201501120000140");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
