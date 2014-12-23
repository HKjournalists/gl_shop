package com.glshop.platform.api.pay;

public abstract class BaseProduct implements IProduct {

	public String subject;

	public String description;

	public String price;

	@Override
	public void initProduct(PayInfo product) {
		this.subject = product.getSubject();
		this.description = product.getDescription();
		this.price = product.getTotalPrice();
	}

}
