package com.glshop.platform.api.pay.impl;

import android.content.Context;

import com.glshop.platform.api.pay.BasePay;
import com.glshop.platform.api.pay.IPayCallback;
import com.glshop.platform.api.pay.IProduct;

/**
 * @Description : 银联支付
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-22 下午5:01:00
 */
public class UnionPay extends BasePay {

	public UnionPay(Context context) {
		this.mContext = context;
	}

	@Override
	public String pay(IProduct product, IPayCallback callback) {
		String result = "";
		//TODO
		return result;
	}

}
