package com.glshop.platform.api.contract.data.model;

/**
 * @Description :待付货款的合同信息概要实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class ToPayContractInfoModel extends ContractSummaryInfoModel {

	/**
	 * 待付金额
	 */
	public String toPayMoney;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof ToPayContractInfoModel) {
			ToPayContractInfoModel other = (ToPayContractInfoModel) o;
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
		sb.append("ToPayContractInfoModel[");
		sb.append("contractId=" + contractId);
		sb.append(", toPayMoney=" + toPayMoney);
		sb.append(", buyCompanyId=" + buyCompanyId);
		sb.append(", sellCompanyId=" + sellCompanyId);
		sb.append(", buyType=" + buyType.toValue());
		sb.append(", summary=" + summary);
		sb.append(", amount=" + amount);
		sb.append(", createTime=" + createTime);
		sb.append(", expireTime=" + expireTime);
		sb.append(", updateTime=" + updateTime);
		sb.append(", statusType=" + statusType.toValue());
		sb.append(", type=" + contractType.toValue());
		sb.append("]");
		return sb.toString();
	}

}
