package com.glshop.platform.api.pay;

import android.content.Context;

import com.glshop.platform.api.pay.IPay.PayType;
import com.glshop.platform.api.pay.impl.AliPayProduct;
import com.glshop.platform.api.pay.impl.UnionPayProduct;

/**
 * @Description : 支付实体工厂类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-22 下午5:01:00
 */
public class ProductFactory {

	public static IProduct newInstance(Context context, PayInfo product) {
		// 默认为支付宝支付
		IProduct instance = new AliPayProduct();
		instance.initProduct(product);
		return instance;
	}

	public static IProduct newInstance(Context context, PayInfo product, PayType payType) {
		IProduct instance = null;
		switch (payType) {
		case ALI_PAY:
			instance = new AliPayProduct();
			break;
		case UNION_PAY:
			instance = new UnionPayProduct();
			break;
		default:
			instance = new AliPayProduct();
			break;
		}
		instance.initProduct(product);
		return instance;
	}

}
