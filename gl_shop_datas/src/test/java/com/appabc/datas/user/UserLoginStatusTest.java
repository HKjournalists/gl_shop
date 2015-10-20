/**
 *
 */
package com.appabc.datas.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.tool.UserLoginStatusManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年9月10日 下午6:46:50
 */
public class UserLoginStatusTest extends AbstractDatasTest {
	
	@Autowired
	private UserLoginStatusManager userLoginStatusManager;
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	public void mainTest() {
//		testUserLogionStatus();
	}
	
	public void testUserLogionStatus(){
		System.out.println(userLoginStatusManager.getAllUsersLogin() == null ? 0 : userLoginStatusManager.getAllUsersLogin().size());
		userLoginStatusManager.recordingUserStatus("15811822331");
//		userLoginStatusManager.delAll();
		System.out.println(userLoginStatusManager.getAllUsersLogin() == null ? 0 : userLoginStatusManager.getAllUsersLogin().size());
	}

}
