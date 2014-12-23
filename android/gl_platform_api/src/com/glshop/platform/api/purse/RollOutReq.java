package com.glshop.platform.api.purse;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.purse.data.model.RolloutInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 钱包提现请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class RollOutReq extends BaseRequest<CommonResult> {

	/**
	 * 提现信息
	 */
	public RolloutInfoModel info;

	/**
	 * 短信验证码
	 */
	public String smsCode;

	/**
	 * 密码
	 */
	public String password;

	public RollOutReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		if (info != null) {
			request.addParam("balance", info.money);
			request.addParam("acceptId", info.payeeId);
			request.addParam("type", info.type.toValue());
			request.addParam("validateCode", smsCode);
			request.addParam("password", password);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/purse/extractCashRequest";
	}

}
