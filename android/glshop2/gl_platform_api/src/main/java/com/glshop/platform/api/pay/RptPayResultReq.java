package com.glshop.platform.api.pay;

import com.glshop.platform.api.DataConstants.PayResultType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 上报支付结果请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class RptPayResultReq extends BaseRequest<CommonResult> {

	/**
	 * 服务器交易询单号
	 */
	public String oid;

	/**
	 * 支付结果
	 */
	public PayResultType result;

	public RptPayResultReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("oid", oid);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/purse/reportToUnionPayTradeResult";
	}

}
