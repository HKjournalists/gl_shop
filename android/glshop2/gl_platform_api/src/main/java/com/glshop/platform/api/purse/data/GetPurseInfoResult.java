package com.glshop.platform.api.purse.data;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.purse.data.model.PurseInfoModel;

/**
 * @Description : 获取我的钱包详情信息结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetPurseInfoResult extends CommonResult {

	/**
	 * 钱包基本信息
	 */
	public PurseInfoModel data;

	@Override
	public String toString() {
		return super.toString() + ", " + data;
	}

}
