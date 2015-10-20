package com.glshop.platform.api.user;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取短信验证码、同时验证码图像验证码
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:08:51
 */
public class GetSmsVerifyCodeExReq extends BaseRequest<CommonResult> {

	/**
	 * 用户设备号
	 */
	public String deviceId;
	
	/**
	 * 用户手机号
	 */
	public String account;

	/**
	 * 图形验证码
	 */
	public String imgVerifyCode;

	public GetSmsVerifyCodeExReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("deviceId", deviceId);
		request.addParam("userName", account);
		request.addParam("code", imgVerifyCode);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {
		
	}

	@Override
	protected String getTypeURL() {
		return "/smscode/sendAndCheckImgCode";
	}

}
