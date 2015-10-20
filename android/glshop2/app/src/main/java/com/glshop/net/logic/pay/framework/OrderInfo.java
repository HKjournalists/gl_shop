package com.glshop.net.logic.pay.framework;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2015-3-9 上午10:17:12
 */
public class OrderInfo {

	/**
	 * 商品主题信息
	 */
	private String subject;

	/**
	 * 商品描述信息
	 */
	private String description;

	/**
	 * 商品总价信息
	 */
	private String totalPrice;

	/**
	 * 第三方支付工具回调地址
	 */
	private String notifyUrl;

	/**
	 * 第三方支付流水号
	 */
	private String tradeNo;

	/**
	 * 服务器支付流水号
	 */
	private String orderNo;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
