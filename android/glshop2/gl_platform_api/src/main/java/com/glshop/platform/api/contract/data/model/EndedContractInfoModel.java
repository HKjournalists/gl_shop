package com.glshop.platform.api.contract.data.model;

/**
 * @Description : 已结束的合同详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-18 下午3:31:15
 */
@Deprecated
public class EndedContractInfoModel extends ContractSummaryInfoModel {

	/**
	 * 合同结束时间
	 */
	public String endedTime;

	/**
	 * 结束原因类型
	 */
	public int endedReasonType;

}