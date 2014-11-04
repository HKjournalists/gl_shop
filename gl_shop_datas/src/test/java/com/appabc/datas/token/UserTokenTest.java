/**
 *
 */
package com.appabc.datas.token;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.tool.UserTokenManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月9日 下午9:26:42
 */
public class UserTokenTest extends AbstractDatasTest{

	@Autowired
	private UserTokenManager userTokenManager;

	@Override
	@Test
	public void mainTest() {
//		testSave();
//		testGetTokenByUser();
//		testGetUserByToken();
		
	}
	
	public void testSave(){
		TUser user = new TUser();
		user.setId("u0001");
		user.setUsername("zhangsan110");
		
		this.userTokenManager.saveUserToken(user, new TCompanyInfo());
	}
	
	public void testGetTokenByUser(){
		System.out.println(this.userTokenManager.getBeanByUsername("zhangsan110"));
	}
	
	public void testGetUserByToken(){
		System.out.println(this.userTokenManager.getBeanByToken("b7ac56228255c7909e96bd6e7f413870"));
		System.out.println(this.userTokenManager.isExists("f163f9fdce8fbe7e8166694c2683e801"));
	}

}
