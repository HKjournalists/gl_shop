package com.glshop.platform.api.contract;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 合同在线付款请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class ContractOnlinePayReq extends BaseRequest<CommonResult> {

	/**
	 * 合同编号
	 */
	public String contractId;

	/**
	 * 短信验证码
	 */
	public String smsVerifyCode;

	/**
	 * 登录密码
	 */
	public String password;

	public ContractOnlinePayReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.addParam("validateCode", smsVerifyCode);
		request.addParam("password", password);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/payContractFundsOnline";
	}

}
