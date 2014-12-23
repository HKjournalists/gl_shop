package com.glshop.platform.api.buy.data.model;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 找买找卖信息概要实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 下午4:16:39
 */
public class BuySummaryInfoModel extends ResultItem {

	/**
	 * 发布信息ID
	 */
	public String publishBuyId;

	/**
	 * 发布者企业ID
	 */
	public String companyId;

	/**
	 * 买卖类型
	 */
	public BuyType buyType;

	/**
	 * 货物大类型编码
	 */
	public String productCode;

	/**
	 * 货物名称
	 */
	public String productName;

	/**
	 * 货物子类型编码
	 */
	public String productSubCode;

	/**
	 * 单价
	 */
	public float unitPrice;

	/**
	 * 数量
	 */
	public float tradeAmount;

	/**
	 * 交易地域
	 */
	public String tradeArea;

	/**
	 * 交易发布时间
	 */
	public String tradePubDate;

	/**
	 * 交易起始时间
	 */
	public String tradeBeginDate;

	/**
	 * 交易结束时间
	 */
	public String tradeEndDate;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof BuySummaryInfoModel) {
			BuySummaryInfoModel other = (BuySummaryInfoModel) o;
			if (this.publishBuyId == null || other.publishBuyId == null) {
				return false;
			} else {
				return this.publishBuyId.equals(other.publishBuyId);
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
		sb.append("BuySummaryInfoModel[");
		sb.append("publishBuyId=" + publishBuyId);
		sb.append(", companyId=" + companyId);
		sb.append(", buyType=" + buyType.toValue());
		sb.append(", productName=" + productName);
		sb.append(", productSubCode=" + productSubCode);
		sb.append(", unitPrice=" + unitPrice);
		sb.append(", tradeAmount=" + tradeAmount);
		sb.append(", tradeArea=" + tradeArea);
		sb.append(", tradePubDate=" + tradePubDate);
		sb.append(", tradeBeginDate=" + tradeBeginDate);
		sb.append(", tradeEndDate=" + tradeEndDate);
		sb.append("]");
		return sb.toString();
	}

}
