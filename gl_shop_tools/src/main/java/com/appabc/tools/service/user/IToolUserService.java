package com.appabc.tools.service.user;

import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月25日 上午10:29:43
 */

public interface IToolUserService extends IBaseService<TUser> {
	
	/**
	 * 根据企业ID查询用户
	 * @param cid
	 * @return
	 */
	public TUser getUserByCid(String cid);
	
}
