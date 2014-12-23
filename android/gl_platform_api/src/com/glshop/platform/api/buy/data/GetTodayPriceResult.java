package com.glshop.platform.api.buy.data;

import java.util.List;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;

/**
 * @Description : 获取今日报价列表结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetTodayPriceResult extends CommonResult {

	/**
	 * 今日价格列表
	 */
	public List<TodayPriceModel> datas;

	@Override
	public String toString() {
		return super.toString() + ", datas = " + datas;
	}
}
