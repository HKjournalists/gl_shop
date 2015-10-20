/**
 *
 */
package com.appabc.datas.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TClient;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.service.user.IClientService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月12日 下午3:38:54
 */
public class ClientTest extends AbstractDatasTest{
	
	@Autowired
	private IClientService clientService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
//	@Rollback(value=false)
	public void mainTest() {
//		testGetBadge();
//		testRmBadge();
//		testGetBadgeBean();
	}

	public void testGetBadge(){
		int a = this.clientService.getNextBadge("", ClientTypeEnum.CLIENT_TYPE_IOS, "15811822339");
		System.out.println("============");
		System.out.println(a);
	}
	
	public void testGetBadgeBean(){
		TClient c = this.clientService.getNextBadgeBean("58e8020450d3f78b0c324c29ad2e93f4a874afcb7c319fe7e7014982b396d5ea", ClientTypeEnum.CLIENT_TYPE_IOS, "15811822330");
		System.out.println("============");
		System.out.println(c.getBadge());
	}
	
	public void testRmBadge(){
		this.clientService.rmBadge("121212121221212");
	}
}
