package com.glshop.platform.api.pay.impl;

import com.glshop.platform.api.pay.BaseProduct;

public class AliPayProduct extends BaseProduct {

	@Override
	public String packOrder() {
		String order = createOrderInfo();
		return order;
	}

	private String createOrderInfo() {
		StringBuilder order = new StringBuilder();
		// TODO
		return order.toString();
	}

}
