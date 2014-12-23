package com.glshop.platform.api.buy.data;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;

/**
 * @Description : 获取买卖信息详情结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetBuyInfoResult extends CommonResult {

	/**
	 * 发布信息详情
	 */
	public BuyInfoModel data;

	@Override
	public String toString() {
		return super.toString() + ", " + data;
	}

}
