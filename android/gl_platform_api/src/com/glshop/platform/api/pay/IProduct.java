package com.glshop.platform.api.pay;

/**
 * @Description : 支付实体接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-22 下午5:01:00
 */
public interface IProduct {

	public void initProduct(PayInfo product);

	public String packOrder();

}
