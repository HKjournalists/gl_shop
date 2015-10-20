package com.glshop.net.logic.pay.framework.impl.alipay;

import com.glshop.net.logic.pay.framework.BasePayInfo;
import com.glshop.net.logic.pay.framework.OrderInfo;
import com.glshop.net.logic.pay.framework.impl.alipay.utils.Keys;
import com.glshop.net.logic.pay.framework.impl.alipay.utils.Rsa;

import java.net.URLEncoder;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public class AliPayInfo extends BasePayInfo {

	private static final String TAG = "AliPayInfo";

	public AliPayInfo(OrderInfo info) {
		super(info);
	}

	@Override
	public String createOrder() {
		String order = createOrderInfo();
		String sign = Rsa.sign(order, Keys.PRIVATE);
		sign = URLEncoder.encode(sign);
		order += "&sign=\"" + sign + "\"&" + getSignType();
		return order;
	}

	private String createOrderInfo() {
		StringBuilder order = new StringBuilder();
		order.append("partner=\"");
		order.append(Keys.DEFAULT_PARTNER);
		order.append("\"&out_trade_no=\"");
		order.append(/*getOutTradeNo()*/this.orderInfo.getTradeNo());
		order.append("\"&subject=\"");
		order.append(this.orderInfo.getSubject());
		order.append("\"&body=\"");
		order.append(this.orderInfo.getDescription());
		order.append("\"&total_fee=\"");
		order.append(this.orderInfo.getTotalPrice());

		order.append("\"&notify_url=\"");
		// ��ַ��Ҫ��URL����
		order.append(URLEncoder.encode(/*"http://notify.java.jpxx.org/index.jsp"*/this.orderInfo.getNotifyUrl()));
		order.append("\"&service=\"mobile.securitypay.pay");
		order.append("\"&_input_charset=\"UTF-8");
		order.append("\"&return_url=\"");
		order.append(URLEncoder.encode("http://m.alipay.com"));
		order.append("\"&payment_type=\"1");
		order.append("\"&seller_id=\"");
		order.append(Keys.DEFAULT_SELLER);
		order.append("\"&it_b_pay=\"1m");
		order.append("\"");
		return order.toString();
	}

	/*private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		Log.d(TAG, "outTradeNo: " + key);
		return key;
	}*/


	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
