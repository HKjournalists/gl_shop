package com.glshop.net.logic.pay.framework;

import android.content.Context;

import com.glshop.net.logic.pay.framework.IPay.PayToolType;
import com.glshop.net.logic.pay.framework.impl.alipay.AliOnlinePay;
import com.glshop.net.logic.pay.framework.impl.unionpay.UnionOnlinePay;

/**
 * @Description : 支付工具工厂类定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public class PayFactory {

	public static IPay newInstance(Context context) {
		return newInstance(context, PayConstants.DEFAULT_PAY_MODE);
	}

	public static IPay newInstance(Context context, PayToolType payType) {
		IPay instance = null;
		if (payType == null) {
			payType = PayConstants.DEFAULT_PAY_MODE;
		}
		switch (payType) {
		case ALI_PAY:
			instance = new AliOnlinePay(context);
			break;
		case UNION_PAY:
			instance = new UnionOnlinePay(context);
			break;
		default:
			instance = new AliOnlinePay(context);
			break;
		}
		return instance;
	}

}
