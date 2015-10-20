package com.glshop.platform.api.purse.data.model;

import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 支付记录信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class PayInfoModel extends ResultItem {

	/**
	 * 订单ID
	 */
	public String orderId;

	/**
	 * 第三方支付ID
	 */
	public String thirdPayId;

	/**
	 * 用户ID
	 */
	public String userId;

	/**
	 * 支付主题
	 */
	public String subject;

	/**
	 * 支付内容描述
	 */
	public String description;

	/**
	 * 支付总价
	 */
	public String totalPrice;

	/**
	 * 支付结果
	 */
	public String result;

	/**
	 * 支付类型
	 */
	public PurseType type = PurseType.DEPOSIT;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("PayInfoModel[");
		sb.append("orderId=" + orderId);
		sb.append(", thirdPayId=" + thirdPayId);
		sb.append(", userId=" + userId);
		sb.append(", subject=" + subject);
		sb.append(", description=" + description);
		sb.append(", totalPrice=" + totalPrice);
		sb.append(", result=" + result);
		sb.append(", type=" + type.toValue());
		sb.append("]");
		return sb.toString();
	}

}
