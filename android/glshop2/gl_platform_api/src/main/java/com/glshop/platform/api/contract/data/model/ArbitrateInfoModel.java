package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 仲裁信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class ArbitrateInfoModel extends ResultItem {

	/**
	 * 评价ID
	 */
	public String id;

	/**
	 * 合同ID
	 */
	public String contractId;

	/**
	 * 申请者企业ID
	 */
	public String creatorID;

	/**
	 * 处理者
	 */
	public String dealOperator;

	/**
	 * 处理时间
	 */
	public String dealTime;

	/**
	 * 仲裁单价
	 */
	public double unitPrice;

	/**
	 * 仲裁数量
	 */
	public double amount;

	/**
	 * 仲裁备注
	 */
	public String remarks;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof ArbitrateInfoModel) {
			ArbitrateInfoModel other = (ArbitrateInfoModel) o;
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
		sb.append("ArbitrateInfoModel[");
		sb.append("id=" + id);
		sb.append(", contractId=" + contractId);
		sb.append(", unitPrice=" + unitPrice);
		sb.append(", amount=" + amount);
		sb.append(", dateTime=" + dealTime);
		sb.append(", remarks=" + remarks);
		sb.append("]");
		return sb.toString();
	}

}
