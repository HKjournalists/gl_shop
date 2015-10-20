package com.glshop.platform.api.purse.data.model;

import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 钱包交易流水信息概要
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class DealSummaryInfoModel extends ResultItem {

	/**
	 * 流水号
	 */
	public String id;

	/**
	 * 钱包类型
	 */
	public PurseType purseType = PurseType.DEPOSIT;

	/**
	 * 交易流水类型
	 */
	public DealDirectionType directionType = DealDirectionType.IN;

	/**
	 * 交易流水操作类型
	 */
	public DealOprType oprType;

	/**
	 * 交易流水时间
	 */
	public String dealTime;

	/**
	 * 交易金额
	 */
	public double dealMoney;

	/**
	 * 余额
	 */
	public double balance;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof DealSummaryInfoModel) {
			DealSummaryInfoModel other = (DealSummaryInfoModel) o;
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
		sb.append("DealSummaryInfoModel[");
		sb.append("id=" + id);
		sb.append(", purseType=" + purseType.toValue());
		sb.append(", directionType=" + directionType);
		sb.append(", oprType=" + oprType);
		sb.append(", dealTime=" + dealTime);
		sb.append(", dealMoney=" + dealMoney);
		sb.append(", balance=" + balance);
		sb.append("]");
		return sb.toString();
	}

}
