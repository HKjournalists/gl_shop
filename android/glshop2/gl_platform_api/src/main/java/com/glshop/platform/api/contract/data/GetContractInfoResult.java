package com.glshop.platform.api.contract.data;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;

/**
 * @Description : 获取合同详情结果(包括进行中和已结束)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetContractInfoResult extends CommonResult {

	/**
	 * 合同详情消息
	 */
	public ContractInfoModel data;

	@Override
	public String toString() {
		return super.toString() + ", data = " + data;
	}
}
