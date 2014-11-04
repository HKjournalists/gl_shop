/**
 *
 */
package com.appabc.http.copn;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月28日 下午3:13:12
 */
public class AddressTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		loginSimulation("", "", "");
//		queryList();
		
	}
	
	public void queryList(){

		request.setRequestURI("/copn/address/getList");
		request.setMethod("GET");
		
		/***企业信息*********************/
		request.addParameter("cid", "CompanyInfoId000000811102014END"); // 企业ID
		
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
