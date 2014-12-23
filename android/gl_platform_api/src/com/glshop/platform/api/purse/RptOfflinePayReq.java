package com.glshop.platform.api.purse;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 上报离线支付(银行转账、线下支付)成功请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class RptOfflinePayReq extends BaseRequest<CommonResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	/**
	 * 支付类型(银行转账、线下支付)
	 */
	public String type;

	public RptOfflinePayReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.addParam("type", type);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/purse/paymentAccountOffline";
	}

}
