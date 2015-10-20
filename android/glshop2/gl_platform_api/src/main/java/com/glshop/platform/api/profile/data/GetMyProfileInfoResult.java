package com.glshop.platform.api.profile.data;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.MyProfileInfoModel;

/**
 * @Description : 获取我的个人资料详情信息结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetMyProfileInfoResult extends CommonResult {

	/**
	 * 个人资料信息实体
	 */
	public MyProfileInfoModel data;

	@Override
	public String toString() {
		return super.toString() + ", " + data;
	}

}
