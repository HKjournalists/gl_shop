package com.glshop.platform.api.buy.data.model;

import java.util.List;

import com.glshop.platform.api.DataConstants.BuyStatus;

/**
 * @Description : 我的供应信息概要实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-14 上午11:34:38
 */
public class MyBuySummaryInfoModel extends BuySummaryInfoModel {

	/**
	 * 信息状态
	 */
	public BuyStatus buyStatus = BuyStatus.VALID;

	/**
	 * 是否多地域发布
	 */
	public boolean isMoreArea;
	
	/**
	 * 多地域发布信息列表
	 */
	public List<AreaPriceInfoModel> areaInfoList;

	/**
	 * 感兴趣的人数
	 */
	public int interestedNumber;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof MyBuySummaryInfoModel) {
			MyBuySummaryInfoModel other = (MyBuySummaryInfoModel) o;
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
		sb.append("MyBuySummaryInfoModel[");
		sb.append("publishBuyId=" + publishBuyId);
		sb.append(", companyId=" + companyId);
		sb.append(", buyType=" + buyType.toValue());
		sb.append(", buyStatus=" + buyStatus.toValue());
		sb.append(", productName=" + productName);
		sb.append(", productSubCode=" + productSubCode);
		sb.append(", unitPrice=" + unitPrice);
		sb.append(", tradeAmount=" + tradeAmount);
		sb.append(", tradeArea=" + tradeArea);
		sb.append(", tradePubDate=" + tradePubDate);
		sb.append(", tradeBeginDate=" + tradeBeginDate);
		sb.append(", tradeEndDate=" + tradeEndDate);
		sb.append(", interestedNumber=" + interestedNumber);
		sb.append("]");
		return sb.toString();
	}

}
