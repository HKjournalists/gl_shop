package com.glshop.platform.api.user.data;

import android.graphics.Bitmap;

import com.glshop.platform.api.base.CommonResult;

/**
 * @Description : 获取图形验证码结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class GetImgVerifyCodeResult extends CommonResult {

	/**
	 * 图形验证码
	 */
	public Bitmap imgVerifyCode;

	@Override
	public String toString() {
		return super.toString() + " imgVerifyCode = " + imgVerifyCode;
	}
}
