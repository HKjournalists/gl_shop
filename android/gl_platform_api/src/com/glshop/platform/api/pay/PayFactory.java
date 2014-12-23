package com.glshop.platform.api.pay;

import android.content.Context;

import com.glshop.platform.api.pay.IPay.PayType;
import com.glshop.platform.api.pay.impl.AliPay;
import com.glshop.platform.api.pay.impl.UnionPay;

/**
 * @Description : 支付工具工厂类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-22 下午5:01:00
 */
public class PayFactory {

	public static IPay newInstance(Context context) {
		// 默认为支付宝支付
		IPay instance = new AliPay(context);
		return instance;
	}

	public static IPay newInstance(Context context, PayType payType) {
		IPay instance = null;
		switch (payType) {
		case ALI_PAY:
			instance = new AliPay(context);
			break;
		case UNION_PAY:
			instance = new UnionPay(context);
			break;
		default:
			instance = new AliPay(context);
			break;
		}
		return instance;
	}

}
