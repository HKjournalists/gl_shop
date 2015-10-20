/**
 *
 */
package com.appabc.datas.token;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
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
//		testGetUserByToken();
//		updateUserToken();
//		delUserToken();
//		testGetTokenByUser();
	}
	
	// b7843af55ba83801f7af9ee1f70f8f5f
	// 8bdd5afc7d279f926097b52c73ebfb96
	// 411a16976b5021fb1d4e6c347c2af446

	public void delUserToken(){
		this.userTokenManager.delUserInfoByUser("测试企业");
	}
	
	public void testSave(){
		TUser user = new TUser();
		user.setId("u0001");
		user.setUsername("zhangsan110");

		UserInfoBean ui = this.userTokenManager.saveUserToken(user, new TCompanyInfo());
		for (int i = 0; i < ui.getTokenList().size(); i++) {
			System.out.println(ui.getTokenList().get(i).getToken());
		}
	}

	@SuppressWarnings("deprecation")
	public void testGetTokenByUser(){
		UserInfoBean ui = this.userTokenManager.getBeanByUsername("15811822330");
		for (int i = 0; i < ui.getTokenList().size(); i++) {
			System.out.println(ui.getTokenList().get(i).getToken());
			System.out.println(ui.getTokenList().get(i).getExpTime().toLocaleString());
		}
	}

	public void testGetUserByToken(){
		UserInfoBean ui = this.userTokenManager.getBeanByToken("dc4b0ce51a4b681efd92da4f26de87ce");
		for (int i = 0; i < ui.getTokenList().size(); i++) {
			System.out.println(ui.getTokenList().get(i).getToken());
		}
		System.out.println(ui.getUserName());
//		System.out.println(this.userTokenManager.isExists("411a16976b5021fb1d4e6c347c2af446").getText());
	}
	
	public void updateUserToken(){
		
		UserInfoBean ut = this.userTokenManager.getBeanByUsername("zhangsan110");
		ut.setCname("测试企业");
		
		this.userTokenManager.updateUserInfo(ut);
	}

}
