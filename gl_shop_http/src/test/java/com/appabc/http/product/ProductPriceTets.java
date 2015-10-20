/**
 *
 */
package com.appabc.http.product;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年2月27日 下午5:22:29
 */
public class ProductPriceTets extends AbstractHttpControllerTest {
	
	/* (non-Javadoc)
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		testGetHopePrice();
	}
	
	public void testGetHopePrice(){
		request.setRequestURI("/product/price/getHope");
		request.setMethod("GET");
		
		request.addParameter("pcode", "G001");
		request.addParameter("area", "RS001");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
