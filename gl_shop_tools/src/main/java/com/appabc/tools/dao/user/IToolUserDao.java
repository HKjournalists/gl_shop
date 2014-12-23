package com.appabc.tools.dao.user;


import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年8月23日 下午3:49:02
 */

public interface IToolUserDao extends IBaseDao<TUser> {
	
	/**
	 * 根据用户名和密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	public TUser queryByNameAndPass(String username, String password);
	
		
}
