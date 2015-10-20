/**
 *
 */
package com.appabc.datas.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.user.IUserSettingService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月11日 上午10:57:00
 */
public class UserSettingTest extends AbstractDatasTest{
	
	@Autowired
	private IUserSettingService userSettingService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		testGetRemind();
	}

	
	public void testGetRemind(){
		int a = userSettingService.getRemind("201503300000026");
		
		System.out.println(a);
	}
	
}
