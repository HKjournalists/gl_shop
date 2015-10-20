package com.glshop.platform.api.purse.data.model;

import com.glshop.platform.api.DataConstants.PayType;

/**
 * @Description : 钱包交易流水信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class DealInfoModel extends DealSummaryInfoModel {

	/**
	 * 支付方式
	 */
	public PayType payType = PayType.NETBANK_PAY;
	
	/**
	 * 合同ID
	 */
	public String contractId;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof DealInfoModel) {
			DealInfoModel other = (DealInfoModel) o;
			if (this.id == null || other.id == null) {
				return false;
			} else {
				return this.id.equals(other.id);
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
		sb.append("DealInfoModel[");
		sb.append("id=" + id);
		sb.append(", purseType=" + purseType.toValue());
		sb.append(", payType=" + payType.toValue());
		sb.append(", directionType=" + directionType);
		sb.append(", oprType=" + oprType);
		sb.append(", dealTime=" + dealTime);
		sb.append(", dealMoney=" + dealMoney);
		sb.append(", balance=" + balance);
		sb.append(", contractId=" + contractId);
		sb.append("]");
		return sb.toString();
	}

}
