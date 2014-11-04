package com.appabc.http.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.common.utils.security.BaseCoder;
import com.appabc.http.AbstractHttpControllerTest;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月23日 下午6:01:45
 */

public class PayTest extends AbstractHttpControllerTest {

	/* (non-Javadoc)  
	 * @see com.appabc.http.AbstractHttpControllerTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		this.loginSimulation("", "", "");
		testVeritySign();
	}
	
	void testVeritySign(){
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("name", "value");
		treeMap.put("value", "name");
		StringBuffer input = new StringBuffer();
		Iterator<Entry<String, String>> itor = treeMap.entrySet()
				.iterator();
		while (itor.hasNext()) {
			Entry<String, String> et = itor.next();
			input.append(et.getKey());
			input.append(et.getValue());
		}
		//生成签名
		String sign = null;
		try {
			sign = BaseCoder.encryptMD5(input.toString());
			request.setRequestURI("/purse/getPurseList");
	        request.setMethod("GET");
	        request.addParameter("name", "value");
	        request.addParameter("value", "name");
	        request.addParameter("ENCRYPT_KEY", sign);
	        try {
				final ModelAndView mav = handle(request, response);
				this.print(mav);
			} catch (Exception e) {
				e.printStackTrace();
				logUtil.debug(e.getMessage(), e);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
