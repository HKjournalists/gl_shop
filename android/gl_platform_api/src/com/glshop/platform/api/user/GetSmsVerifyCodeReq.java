package com.glshop.platform.api.user;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取短信验证码请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:08:51
 */
public class GetSmsVerifyCodeReq extends BaseRequest<CommonResult> {

	/**
	 * 用户名
	 */
	public String account;

	/**
	 * 获取短信验证码业务类型
	 */
	public String sendType;

	public GetSmsVerifyCodeReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("phone", account);
		if (sendType != null) {
			request.addParam("sendType", sendType);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {
	}

	@Override
	protected String getTypeURL() {
		return "/smscode/send";
	}

}
