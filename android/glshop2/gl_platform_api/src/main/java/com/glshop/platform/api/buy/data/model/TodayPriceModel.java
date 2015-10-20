package com.glshop.platform.api.buy.data.model;

import com.glshop.platform.api.syscfg.data.model.ProductCfgInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 今日价格实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午10:30:11
 */
public class TodayPriceModel extends ResultItem {

	/**
	 * 货物规格类型
	 */
	public String productSepcType;

	/**
	 * 货物规格ID
	 */
	public String productSepcId;

	/**
	 * 货物名称
	 */
	public String productName;

	/**
	 * 货物属性详情
	 */
	public ProductCfgInfoModel productInfo;

	/**
	 * 今日价格
	 */
	public String todayPrice;

	/**
	 * 昨日价格
	 */
	public String yesterdayPrice;

	/**
	 * 港口区域
	 */
	public String publishArea;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof TodayPriceModel) {
			TodayPriceModel other = (TodayPriceModel) o;
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
		sb.append("TodayPriceModel[");
		sb.append("productSepcType=" + productSepcType);
		sb.append(", productSepcId=" + productSepcId);
		sb.append(", todayPrice=" + todayPrice);
		sb.append(", yesterdayPrice=" + yesterdayPrice);
		sb.append(", publishArea=" + publishArea);
		sb.append("]");
		return sb.toString();
	}

}
