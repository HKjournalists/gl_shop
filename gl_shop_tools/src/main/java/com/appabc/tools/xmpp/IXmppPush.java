/**
 *
 */
package com.appabc.tools.xmpp;

import java.util.List;

import com.appabc.bean.pvo.TUser;
import com.appabc.tools.bean.PushInfoBean;

/**
 * @Description : 消息推送接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月29日 下午9:17:04
 */
public interface IXmppPush {
	
	/**
	 * 单个用户推送
	 * @param piBean 推送内容
	 * @param user 用户
	 * @return true/false
	 */
	public boolean pushToSingle(PushInfoBean piBean, TUser user);
	
	/**
	 * 批量推送
	 * @param piBean
	 * @param userList
	 * @return
	 */
	public boolean pushToList(PushInfoBean piBean, List<TUser> userList);

}
