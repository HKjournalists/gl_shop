package com.glshop.net.logic.xmpp;

import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : XMPP信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 下午4:06:18
 */
public class XmppMsgInfo {

	/**
	 * 业务ID
	 */
	public String businessId;

	/**
	 * 消息所有者
	 */
	public String owner;

	/**
	 * 业务类型
	 */
	public MsgBusinessType businessType = MsgBusinessType.TYPE_DEFAULT;

	/**
	 * 业务内容
	 */
	public String content;

	/**
	 * 消息参数
	 */
	public ResultItem params;

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("XmppMsgInfo[");
		sb.append("businessId=" + businessId);
		sb.append(", owner=" + owner);
		sb.append(", businessType=" + businessType.toValue());
		sb.append(", content=" + content);
		sb.append(", params=" + params);
		sb.append("]");
		return sb.toString();
	}

}
