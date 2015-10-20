package com.glshop.platform.api.contract.data.model;

import java.util.List;

import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;

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
	public double unitPrice;

	/**
	 * 合同原始总体数量
	 */
	public double tradeAmount;

	/**
	 * 第一次议价后新的单价
	 */
	public double firstNegotiateUnitPrice;

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
	public double secondNegotiateUnitPrice;

	/**
	 * 第二次议价原因
	 */
	public String secondNegotiateReason;

	/**
	 * 第二次议价历史记录
	 */
	public List<NegotiateInfoModel> secondNegotiateList;

	/**
	 * 合同货款确认记录
	 */
	public NegotiateInfoModel finalNegotiateInfo;

	/**
	 * 合同平台账单流水记录
	 */
	public List<DealSummaryInfoModel> dealList;

	/**
	 * 平台仲裁信息
	 */
	public ArbitrateInfoModel arbitrateInfo;

	/**
	 * 是否同意对方的第二次议价
	 */
	public boolean isAgreeSecondNegotiate;

	/**
	 * 最终实际单价
	 */
	public double fianlTradeUnitPrice;

	/**
	 * 最终实际卸货数量
	 */
	public double finalTradedAmount;

	/**
	 * 最终支付总价
	 */
	public double finalPayMoney;

	/**
	 * 实际已支付金额
	 */
	public double payedMoney;

	/**
	 * 实际收到金额
	 */
	public double receivedMoney;

	/**
	 * 合同结束时间
	 */
	public String endedTime;

	/**
	 * 结束原因类型
	 */
	public int endedReasonType;

	/**
	 * 合同模板信息
	 */
	public ContractModelInfo modelInfo;

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
