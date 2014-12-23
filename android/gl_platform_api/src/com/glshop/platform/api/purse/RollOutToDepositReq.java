package com.glshop.platform.api.purse;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 货款转款至保证金请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class RollOutToDepositReq extends BaseRequest<CommonResult> {

	/**
	 * 转款金额
	 */
	public String money;

	/**
	 * 短信验证码
	 */
	public String smsCode;

	/**
	 * 密码
	 */
	public String password;

	public RollOutToDepositReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("balance", money);
		request.addParam("smsCode", smsCode);
		request.addParam("password", password);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/purse/depositToGuaranty";
	}

}
