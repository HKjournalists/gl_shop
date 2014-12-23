package com.glshop.platform.api.contract.data;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.contract.data.model.UncfmContractInfoModel;

/**
 * @Description : 获取待确认的合同列表结果(使用GetContractModelResult)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
@Deprecated
public class GetUncfmContractInfoResult extends CommonResult {

	public UncfmContractInfoModel contractInfo;

}
