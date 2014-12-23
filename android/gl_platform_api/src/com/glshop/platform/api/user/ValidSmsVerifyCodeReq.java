package com.glshop.platform.api.user;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 验证短信验证码请求,旧需求接口，暂保留.
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:08:51
 */
@Deprecated
public class ValidSmsVerifyCodeReq extends BaseRequest<CommonResult> {

	/**
	 * 用户名
	 */
	public String account;

	/**
	 * 短信验证码
	 */
	public String verifyCode;

	public ValidSmsVerifyCodeReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("phone", account);
		request.addParam("vldcode", verifyCode);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {
	}

	@Override
	protected String getTypeURL() {
		return "/vldcode/check";
	}

}
