package com.glshop.net.logic.pay.framework.impl.unionpay;

import android.app.Activity;
import android.content.Context;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.logic.pay.framework.BasePay;
import com.glshop.net.logic.pay.framework.IPayCallback;
import com.glshop.net.logic.pay.framework.IPayInfo;
import com.glshop.net.logic.pay.framework.OrderInfo;
import com.glshop.platform.api.DataConstants.PayEnvType;
import com.glshop.platform.utils.Logger;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public class UnionOnlinePay extends BasePay {

	public UnionOnlinePay(Context context) {
		super(context);
	}

	@Override
	public String pay(OrderInfo info, IPayCallback callback) {
		if (info == null) {
			throw new IllegalArgumentException("You must instantiate the OrderInfo object!");
		}

		if (mContext == null || !(mContext instanceof Activity)) {
			throw new IllegalArgumentException("You must instantiate the mContext param as Activity Context!");
		}

		IPayInfo payInfo = new UnionPayInfo(info);

		// mMode参数解释：
		// 00 - 启动银联正式环境
		// 01 - 连接银联测试环境
		// 测试环境下银联测试订单号: 201503091118440002332
		boolean isReleasePayMode = GlobalConfig.getInstance().getPayEnvType() == PayEnvType.RELEASE;
		Logger.e("UnionOnlinePay", "isReleasePayMode = " + isReleasePayMode);
		UPPayAssistEx.startPayByJAR((Activity) mContext, PayActivity.class, null, null, payInfo.createOrder(), isReleasePayMode ? "00" : "01");
		return null;
	}
}
