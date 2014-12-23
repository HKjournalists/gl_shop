package com.glshop.platform.api.purse.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 钱包基本信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class PurseInfoModel extends ResultItem {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 企业名称
	 */
	public String companyName;

	/**
	 * 保证金账户余额
	 */
	public float depositBalance;

	/**
	 * 保证金账户ID
	 */
	public String depositAccountId;

	/**
	 * 货款账户余额
	 */
	public float paymentBalance;

	/**
	 * 货款账户ID
	 */
	public String paymentAccountID;

	/**
	 * 待付货款数目
	 */
	public int toPayCount;

	/**
	 * 保证金是否足够
	 */
	public boolean isDepositEnough;

	/**
	 * 货款是否足够
	 */
	public boolean isPaymentEnough;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("PurseInfoModel[");
		sb.append("companyId=" + companyId);
		sb.append(", companyName=" + companyName);
		sb.append(", depositBalance=" + depositBalance);
		sb.append(", paymentBalance=" + paymentBalance);
		sb.append(", depositAccountId=" + depositAccountId);
		sb.append(", paymentAccountID=" + paymentAccountID);
		sb.append(", toPayCount=" + toPayCount);
		sb.append(", isDepositEnough=" + isDepositEnough);
		sb.append(", isPaymentEnough=" + isPaymentEnough);
		sb.append("]");
		return sb.toString();
	}

}
