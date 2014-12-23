package com.glshop.platform.api.contract.data.model;

import java.io.Serializable;

/**
 * @Description : 议价信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-30 下午4:34:09
 */
public class NegotiateInfoModel implements Serializable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 1941357291481147727L;

	/**
	 * 议价记录ID
	 */
	public String id;

	/**
	 * 议价记录父ID
	 */
	public String pid;

	/**
	 * 合同ID
	 */
	public String contractId;
	
	/**
	 * 合同原始单价
	 */
	public float unitPrice;
	
	/**
	 * 合同原始数量
	 */
	public float tradeAmount;

	/**
	 * 合同上次议价单价
	 */
	public float preNegUnitPrice;

	/**
	 * 本次议价后的单价
	 */
	public float negUnitPrice;

	/**
	 * 合同上次议价数量
	 */
	public float preNegAmount;

	/**
	 * 本次议价后的数量
	 */
	public float negAmount;

	/**
	 * 议价时间
	 */
	public String oprTime;

	/**
	 * 议价发起者
	 */
	public String operator;

	/**
	 * 议价原因
	 */
	public String reason;

	/**
	 * 议价备注
	 */
	public String remarks;

	/**
	 * 是否是我的议价
	 */
	public boolean isMine;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof NegotiateInfoModel) {
			NegotiateInfoModel other = (NegotiateInfoModel) o;
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
		sb.append("NegotiateInfoModel[");
		sb.append("id=" + id);
		sb.append(", pid=" + pid);
		sb.append(", contractId=" + contractId);
		sb.append(", preNegUnitPrice=" + preNegUnitPrice);
		sb.append(", negUnitPrice=" + negUnitPrice);
		sb.append(", preNegAmount=" + preNegAmount);
		sb.append(", negAmount=" + negAmount);
		sb.append(", oprTime=" + oprTime);
		sb.append(", operator=" + operator);
		sb.append(", reason=" + reason);
		sb.append(", isMine=" + isMine);
		sb.append("]");
		return sb.toString();
	}
}
