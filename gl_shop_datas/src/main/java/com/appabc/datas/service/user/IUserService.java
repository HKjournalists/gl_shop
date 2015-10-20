package com.appabc.datas.service.user;

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

public interface IUserService extends IBaseService<TUser>{
	
	/**
	 * 根据用户名和密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	public TUser queryByNameAndPass(String username, String password);
	
	/**
	 * 检查是否存在该用户
	 * @param username
	 * @return ture:存在/false:不存在
	 */
	public boolean isExistUsername(String username);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public boolean register(TUser user);
	
	/**
	 * 根据企业ID查询USER对象
	 * @param cid
	 * @return
	 */
	public TUser getUserByCid(String cid);
	
	/**
	 * 帐号与客户端绑定
	 * @param userid
	 * @param username
	 * @param clientid
	 * @param clienttype
	 * @param version
	 * @param channel
	 */
	public void clientBinding(String userid, String username, String clientid, String clienttype, String version, String channel);
	
	/**
	 * 统计用户个数
	 * @param entity
	 * @return
	 */
	public int queryCount(TUser entity);
	
}
