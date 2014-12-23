package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 我的合同双方操作状态信息概要实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class ContractBuyStatusInfo extends ResultItem {

	/**
	 * 合同编号
	 */
	public String id;

	/**
	 * 合同编号
	 */
	public String contractId;

	/**
	 * 操作者ID
	 */
	public String oprId;

	/**
	 * 操作时间
	 */
	public String oprDatetime;

	/**
	 * 合同生命周期
	 */
	public ContractLifeCycle lifeCycle = ContractLifeCycle.DRAFTING;

	/**
	 * 合同操作类型
	 */
	public ContractOprType oprType = ContractOprType.CONFRIM_CONTRACT;

	/**
	 * 备注
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

		if (o instanceof ContractBuyStatusInfo) {
			ContractBuyStatusInfo other = (ContractBuyStatusInfo) o;
			if (this.contractId == null || other.contractId == null) {
				return false;
			} else {
				return this.contractId.equals(other.contractId);
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
		sb.append("ContractSummaryInfoModel[");
		sb.append("contractId=" + contractId);
		sb.append(", lifeCycle=" + lifeCycle.toValue());
		sb.append(", oprType=" + oprType.toValue());
		sb.append("]");
		return sb.toString();
	}

}
