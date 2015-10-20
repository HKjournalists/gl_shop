package com.glshop.platform.api.profile.data.model;

/**
 * @Description : 对方资料信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class OtherProfileInfoModel extends MyProfileInfoModel {

	/**
	 * 成功交易笔数
	 */
	public int tradeNumber;

	/**
	 * 交易成功率
	 */
	public float turnoverRate;

	/**
	 * 交易满意度(1-5)
	 */
	public int satisfactionPer;

	/**
	 * 交易诚信度(1-5)
	 */
	public int sincerityPer;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("OtherProfileInfoModel[");
		sb.append("companyId=" + companyId);
		sb.append(", companyName=" + companyName);
		sb.append(", authStatusType=" + authStatusType.toValue());
		sb.append(", depositStatusType=" + depositStatusType.toValue());
		sb.append(", tradeNumber=" + tradeNumber);
		sb.append(", satisfactionPer=" + satisfactionPer);
		sb.append(", sincerityPer=" + sincerityPer);
		sb.append("]");
		return sb.toString();
	}

}
