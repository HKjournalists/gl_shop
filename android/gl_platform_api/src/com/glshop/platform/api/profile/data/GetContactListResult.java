package com.glshop.platform.api.profile.data;

import java.util.List;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;

/**
 * @Description : 获取联系人列表结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetContactListResult extends CommonResult {

	/**
	 * 联系人信息列表
	 */
	public List<ContactInfoModel> datas;

	@Override
	public String toString() {
		return super.toString() + ", " + datas;
	}

}
