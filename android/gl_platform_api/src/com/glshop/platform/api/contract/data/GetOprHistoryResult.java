package com.glshop.platform.api.contract.data;

import java.util.List;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.contract.data.model.ContractOprInfoModel;

/**
 * @Description : 获取合同操作记录列表结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetOprHistoryResult extends CommonResult {

	/**
	 * 合同操作记录
	 */
	public List<ContractOprInfoModel> datas;

	@Override
	public String toString() {
		return super.toString() + ", datas = " + datas;
	}
}
