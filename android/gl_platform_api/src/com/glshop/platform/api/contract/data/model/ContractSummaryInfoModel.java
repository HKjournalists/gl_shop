package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractStatusType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 我的合同信息概要实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午2:35:06
 */
public class ContractSummaryInfoModel extends ResultItem {

	/**
	 * 合同编号
	 */
	public String contractId;

	/**
	 * 货物名称
	 */
	public String productName;

	/**
	 * 合同甲方ID(企业ID)
	 */
	public String buyCompanyId;

	/**
	 * 合同甲方名称
	 */
	public String buyCompanyName;

	/**
	 * 合同乙方ID(企业ID)
	 */
	public String sellCompanyId;

	/**
	 * 合同乙方
	 */
	public String sellCompanyName;

	/**
	 * 买卖类型
	 */
	public BuyType buyType = BuyType.BUYER;

	/**
	 * 合同概要信息
	 */
	public String summary;

	/**
	 * 合同数量
	 */
	public String amount;

	/**
	 * 合同成立时间
	 */
	public String createTime;

	/**
	 * 合同有效时间
	 */
	public String expireTime;

	/**
	 * 合同更新时间
	 */
	public String updateTime;

	/**
	 * 合同类型：待确认的合同、进行中的合同、已结束的合同
	 */
	public ContractType contractType = ContractType.UNCONFIRMED;

	/**
	 * 合同生命周期
	 */
	public ContractLifeCycle lifeCycle = ContractLifeCycle.DRAFTING;

	/**
	 * 合同状态类型
	 */
	public ContractStatusType statusType = ContractStatusType.DRAFTING;

	/**
	 * 合同操作类型
	 */
	public ContractOprType oprType = ContractOprType.CONFRIM_CONTRACT;

	/**
	 * 买家操作状态
	 */
	public ContractBuyStatusInfo buyerStatus;

	/**
	 * 卖家操作状态
	 */
	public ContractBuyStatusInfo sellerStatus;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof ContractSummaryInfoModel) {
			ContractSummaryInfoModel other = (ContractSummaryInfoModel) o;
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
		sb.append(", productName=" + productName);
		sb.append(", buyCompanyId=" + buyCompanyId);
		sb.append(", buyCompanyName=" + buyCompanyName);
		sb.append(", sellCompanyId=" + sellCompanyId);
		sb.append(", sellCompanyName=" + sellCompanyName);
		sb.append(", buyType=" + buyType.toValue());
		sb.append(", summary=" + summary);
		sb.append(", amount=" + amount);
		sb.append(", createTime=" + createTime);
		sb.append(", expireTime=" + expireTime);
		sb.append(", updateTime=" + updateTime);
		sb.append(", statusType=" + statusType.toValue());
		sb.append(", contractType=" + contractType.toValue());
		sb.append(", lifeCycle=" + lifeCycle.toValue());
		sb.append(", oprType=" + oprType.toValue());
		sb.append("]");
		return sb.toString();
	}

}
