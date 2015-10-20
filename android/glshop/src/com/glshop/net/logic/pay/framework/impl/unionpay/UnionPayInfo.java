package com.glshop.net.logic.pay.framework.impl.unionpay;

import com.glshop.net.logic.pay.framework.BasePayInfo;
import com.glshop.net.logic.pay.framework.OrderInfo;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public class UnionPayInfo extends BasePayInfo {

	private static final String TAG = "UnionPayInfo";

	public UnionPayInfo(OrderInfo info) {
		super(info);
	}

	@Override
	public String createOrder() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.orderInfo.getTradeNo());
		return sb.toString();
	}

}
