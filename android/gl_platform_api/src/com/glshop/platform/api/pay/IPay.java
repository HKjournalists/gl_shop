package com.glshop.platform.api.pay;

/**
 * @Description : 支付接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-22 下午5:01:00
 */
public interface IPay {

	public static enum PayType {
		ALI_PAY, UNION_PAY
	}

	public String pay(IProduct product, IPayCallback callback);

}
