package com.glshop.net.logic.pay.framework.impl.alipay;

import android.app.Activity;
import android.content.Context;

import com.glshop.net.logic.pay.framework.BasePay;
import com.glshop.net.logic.pay.framework.IPayCallback;
import com.glshop.net.logic.pay.framework.IPayInfo;
import com.glshop.net.logic.pay.framework.OrderInfo;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public class AliOnlinePay extends BasePay {

	public AliOnlinePay(Context context) {
		super(context);
	}

	@Override
	public String pay(OrderInfo info, IPayCallback callback) {
		if (info == null) {
			throw new IllegalArgumentException("You must instantiate OrderInfo object!");
		}

		if (mContext == null || !(mContext instanceof Activity)) {
			throw new IllegalArgumentException("You must instantiate the mContext param as Activity Context!");
		}

		IPayInfo payInfo = new AliPayInfo(info);

		/*AliPay alipay = new AliPay((Activity) mContext, new Handler(mContext.getMainLooper()));
		String result = alipay.pay(payInfo.createOrder());
		if (callback != null) {
			callback.onPayRespone(0, result);
		}*/
		//return result;
		return null;
	}
}
