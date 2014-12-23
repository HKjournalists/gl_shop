package com.glshop.platform.api.contract.data.model;

import java.util.List;

/**
 * @Description : 合同详情(包括进行中的和已介绍的合同详情)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-18 下午3:31:15
 */
public class ContractInfoModel extends ContractSummaryInfoModel {

	/**
	 * 合同原始单价
	 */
	public float unitPrice;

	/**
	 * 合同原始总体数量
	 */
	public float tradeAmount;

	/**
	 * 第一次议价后新的单价
	 */
	public float firstNegotiateUnitPrice;

	/**
	 * 第一次议价原因
	 */
	public String firstNegotiateReason;

	/**
	 * 第一次议价历史记录
	 */
	public List<NegotiateInfoModel> firstNegotiateList;

	/**
	 * 是否同意对方的第一次议价
	 */
	public boolean isAgreeFirstNegotiate;

	/**
	 * 第二次议价后新的单价
	 */
	public float secondNegotiateUnitPrice;

	/**
	 * 第二次议价原因
	 */
	public String secondNegotiateReason;

	/**
	 * 第二次议价历史记录
	 */
	public List<NegotiateInfoModel> secondNegotiateList;

	/**
	 * 是否同意对方的第二次议价
	 */
	public boolean isAgreeSecondNegotiate;

	/**
	 * 最终实际单价
	 */
	public float fianlTradeUnitPrice;

	/**
	 * 最终实际卸货数量
	 */
	public float finalTradedAmount;
	
	/**
	 * 最终支付总价
	 */
	public float finalPayMoney;

	/**
	 * 合同结束时间
	 */
	public String endedTime;

	/**
	 * 结束原因类型
	 */
	public int endedReasonType;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ContractInfoModel[");
		sb.append("contractId=" + contractId);
		sb.append(", buyCompanyId=" + buyCompanyId);
		sb.append(", sellCompanyId=" + sellCompanyId);
		sb.append(", buyType=" + buyType.toValue());
		sb.append(", summary=" + summary);
		sb.append(", amount=" + amount);
		sb.append(", createTime=" + createTime);
		sb.append(", expireTime=" + expireTime);
		sb.append(", updateTime=" + updateTime);
		sb.append(", endedTime=" + endedTime);
		sb.append(", statusType=" + statusType.toValue());
		sb.append(", contractType=" + contractType.toValue());
		sb.append(", unitPrice=" + unitPrice);
		sb.append(", fianlTradeUnitPrice=" + fianlTradeUnitPrice);
		sb.append(", finalTradedAmount=" + finalTradedAmount);
		sb.append("]");
		return sb.toString();
	}

}
