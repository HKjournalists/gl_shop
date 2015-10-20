/**
 *
 */
package com.appabc.datas.service.user;

import com.appabc.bean.pvo.TUserSetting;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年5月7日 下午4:51:58
 */
public interface IUserSettingService extends IBaseService<TUserSetting>{

	/**
	 * 判断未认证用户本次登录是否需要提醒
	 * @param cid
	 * @return
	 */
	int getRemind(String cid);

}
