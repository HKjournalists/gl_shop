package com.glshop.platform.api.buy.data.model;

/**
 * @Description : 未来趋势价格实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午10:40:14
 */
public class ForecastPriceModel extends TodayPriceModel {

	/**
	 * 一周预测价格
	 */
	public String oneWeekForecastPrice;

	/**
	 * 两周预测价格
	 */
	public String twoWeekForecastPrice;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof ForecastPriceModel) {
			ForecastPriceModel other = (ForecastPriceModel) o;
			if (this.productSepcId != null && other.productSepcId != null) {
				return this.productSepcId.equals(other.productSepcId);
			} else if (this.productSepcType != null && other.productSepcType != null) {
				return this.productSepcType.equals(other.productSepcType);
			}
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
		sb.append("ForecastPriceModel[");
		sb.append("productSepcType=" + productSepcType);
		sb.append(", productSepcId=" + productSepcId);
		sb.append(", todayPrice=" + todayPrice);
		sb.append(", yesterdayPrice=" + yesterdayPrice);
		sb.append(", oneWeekForecastPrice=" + oneWeekForecastPrice);
		sb.append(", twoWeekForecastPrice=" + twoWeekForecastPrice);
		sb.append(", publishArea=" + publishArea);
		sb.append("]");
		return sb.toString();
	}

}
