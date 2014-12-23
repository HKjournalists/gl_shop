package com.glshop.platform.api.profile.data;

import java.util.List;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;

/**
 * @Description : 获取卸货地址列表结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetAddrListResult extends CommonResult {

	/**
	 * 卸货地址信息列表
	 */
	public List<AddrInfoModel> datas;

	@Override
	public String toString() {
		return super.toString() + ", " + datas;
	}

}
