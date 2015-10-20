package com.appabc.datas.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.data.IDataAllUserService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年9月15日 下午7:45:57
 */

public class DataAllUserTest extends AbstractDatasTest {

	@Autowired
	private IDataAllUserService dataAllUserService;
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		/*try {
			dataAllUserService.jobAutoSendAllUserDataWithEmail();
		} catch (ServiceException e) {
			e.printStackTrace();
		}*/
	}

}
