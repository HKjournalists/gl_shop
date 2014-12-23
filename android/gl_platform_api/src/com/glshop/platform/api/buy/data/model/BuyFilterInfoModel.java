package com.glshop.platform.api.buy.data.model;

import com.glshop.platform.api.DataConstants.BuyOrderType;
import com.glshop.platform.api.DataConstants.OrderStatus;

/**
 * @Description : 买卖信息过滤实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 下午4:16:39
 */
public class BuyFilterInfoModel {

	/**
	 * 排序类别
	 */
	public BuyOrderType orderType = BuyOrderType.EXPIRY;

	/**
	 * 排序类型
	 */
	public OrderStatus orderStatus = OrderStatus.DESC;

	/**
	 * 地域编号
	 */
	public String areaCode = "";

	/**
	 * 交易时间：年份
	 */
	public String year = "";

	/**
	 * 交易时间：月份
	 */
	public String month = "";

	/**
	 * 交易时间：天数
	 */
	public String day = "";

	/**
	 * 货物类型
	 */
	public String productType = "";

	/**
	 * 货物编号
	 */
	public String productID = "";

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof BuyFilterInfoModel) {
			BuyFilterInfoModel other = (BuyFilterInfoModel) o;
			return this.orderType == other.orderType && this.orderStatus == other.orderStatus && this.areaCode.equals(other.areaCode) && this.year.equals(other.year)
					&& this.month.equals(other.month) && this.day.equals(other.day) && this.productType.equals(other.productType) && this.productID.equals(other.productID);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BuyInfoModel[");
		sb.append("orderType=" + orderType);
		sb.append(", areaCode=" + areaCode);
		sb.append(", year=" + year);
		sb.append(", month=" + month);
		sb.append(", day=" + day);
		sb.append(", productType=" + productType);
		sb.append(", productID=" + productID);
		sb.append("]");
		return sb.toString();
	}

}
