package com.glshop.platform.api.user;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 重置密码请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:11:39
 */
public class ResetPwdReq extends BaseRequest<CommonResult> {

	/**
	 * 用户名
	 */
	public String account;

	/**
	 * 新密码
	 */
	public String newPassword;

	/**
	 * 短信验证码
	 */
	public String smsVerifyCode;

	public ResetPwdReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("userName", account);
		request.addParam("newPassword", newPassword);
		request.addParam("code", smsVerifyCode);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {
	}

	@Override
	protected String getTypeURL() {
		return "/user/findpwd";
	}

}
