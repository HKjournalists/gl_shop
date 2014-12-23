package com.glshop.platform.api.user.data;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.user.data.model.LoginRespInfoModel;

/**
 * @Description : 登陆结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class LoginResult extends CommonResult {

	/**
	 * 登录返回信息
	 */
	public LoginRespInfoModel data;

	@Override
	public String toString() {
		return super.toString() + data;
	}
}
