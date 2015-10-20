/**
 *
 */
package com.appabc.tools.service.user;

import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TClient;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月11日 下午6:08:34
 */
public interface IClientService extends IBaseService<TClient>{

	/**
	 * 获取下一个IOS上小红圈数
	 * @param clientid
	 * @param clienttype
	 * @param username
	 * @return
	 */
	int getNextBadge(String clientid, ClientTypeEnum clienttype, String username);
	
	/**
	 * 获取下一个IOS上小红圈数
	 * @param clientid
	 * @param clienttype
	 * @param username
	 * @return
	 */
	TClient getNextBadgeBean(String clientid, ClientTypeEnum clienttype, String username);

	/**
	 * 清除IOS推送的小红圈标识数
	 * @param clientid
	 */
	void rmBadge(String clientid);

}
