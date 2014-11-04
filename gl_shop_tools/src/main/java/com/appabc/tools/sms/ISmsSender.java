/**
 *
 */
package com.appabc.tools.sms;

import com.appabc.tools.bean.ShortMsgInfo;

/**
 * @Description : 短信发送接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月30日 下午4:10:39
 */
public interface ISmsSender {
	
	/**
	 * 短信发送
	 * @param smi
	 * @return
	 */
	public boolean sendMsg(ShortMsgInfo smi);

}
