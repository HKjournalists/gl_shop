/**
 *
 */
package com.appabc.http.system;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月16日 下午4:31:33
 */
public class SystemMessageTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		getCount();
//		getList();
//		getInfo();
//		read();
		
	}
	
	public void getCount(){
		request.setRequestURI("/msg/newTotal");
		request.setMethod("GET");
		
		request.setParameter("cid", "000000915102014");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getList(){
		request.setRequestURI("/msg/getList");
		request.setMethod("GET");
		
		request.setParameter("cid", "000000915102014");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getInfo(){
		request.setRequestURI("/msg/getInfo");
		request.setMethod("GET");
		
		request.setParameter("id", "1");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void read(){
		request.setRequestURI("/msg/read");
		request.setMethod("GET");
		
		request.setParameter("id", "1");
		
		try {
			final ModelAndView mav = handle(request, response);
			this.print(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
