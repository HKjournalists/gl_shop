package com.glshop.platform.api.message.data.model;

import com.glshop.platform.api.DataConstants.MessageStatus;
import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.api.DataConstants.SystemMsgType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 消息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class MessageInfoModel extends ResultItem {

	/**
	 * 用户账户
	 */
	public String account;

	/**
	 * 消息ID
	 */
	public String id;

	/**
	 * 用户ID
	 */
	public String userID;

	/**
	 * 消息类型
	 */
	public SystemMsgType type = SystemMsgType.SYSTEM;

	//	/**
	//	 * 消息操作类型
	//	 */
	//	public SystemMsgOprType oprType = SystemMsgOprType.TO_DO_NOTHING;

	/**
	 * 消息业务类型
	 */
	public MsgBusinessType businessType = MsgBusinessType.TYPE_DEFAULT;

	/**
	 * 消息业务ID
	 */
	public String businessID;

	/**
	 * 消息已读状态
	 */
	public MessageStatus status = MessageStatus.UNREADED;

	/**
	 * 消息内容
	 */
	public String content;

	/**
	 * 产生时间
	 */
	public String dateTime;

	/**
	 * 消息是否已上报
	 */
	public boolean isReported;

	public Object obj;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof MessageInfoModel) {
			MessageInfoModel other = (MessageInfoModel) o;
			if (this.id == null || other.id == null) {
				return false;
			} else {
				return this.id.equals(other.id);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MessageInfoModel[");
		sb.append("account=" + account);
		sb.append(", id=" + id);
		sb.append(", userID=" + userID);
		sb.append(", type=" + type.toValue());
		sb.append(", businessType=" + businessType.toValue());
		sb.append(", businessID=" + businessID);
		sb.append(", content=" + content);
		sb.append(", dateTime=" + dateTime);
		sb.append(", status=" + status.toValue());
		sb.append(", isReported=" + isReported);
		sb.append("]");
		return sb.toString();
	}

}
