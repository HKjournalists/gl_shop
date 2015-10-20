package com.glshop.platform.api.contract.data.model;

import com.glshop.platform.api.buy.data.model.BuyInfoModel;

/**
 * @Description : 待确认的合同详情(使用ContractDetailInfoModel)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-18 下午3:23:11
 */
@Deprecated
public class UncfmContractInfoModel extends ContractSummaryInfoModel {

	/**
	 * 买卖信息详情
	 */
	public BuyInfoModel buyInfo;

}
