package com.glshop.platform.api.purse.data.model;

import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 钱包提现信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class RolloutInfoModel extends ResultItem {

	/**
	 * 提现类型
	 */
	public PurseType type = PurseType.PAYMENT;

	/**
	 * 提现金额
	 */
	public String money;

	/**
	 * 收款人
	 */
	public String payeeId;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RolloutInfoModel[");
		sb.append("type=" + type.toValue());
		sb.append(", money=" + money);
		sb.append(", payeeId=" + payeeId);
		sb.append("]");
		return sb.toString();
	}
}
