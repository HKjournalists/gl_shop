package com.glshop.platform.api.buy.data;

import java.util.List;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.buy.data.model.MyBuySummaryInfoModel;

/**
 * @Description : 获取我的供求列表结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetMyBuysResult extends CommonResult {

	/**
	 * 我的供求信息概要列表
	 */
	public List<MyBuySummaryInfoModel> datas;

	/**
	 * 我的找卖找卖总数
	 */
	public int totalCount;

	@Override
	public String toString() {
		return super.toString() + ", TotalCount = " + totalCount + ", " + datas;
	}

}
