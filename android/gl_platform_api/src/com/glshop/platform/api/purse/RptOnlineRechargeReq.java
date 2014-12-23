package com.glshop.platform.api.purse;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.purse.data.model.PayInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 上报在线充值成功请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class RptOnlineRechargeReq extends BaseRequest<CommonResult> {

	/**
	 * 充值信息
	 */
	public PayInfoModel info;

	public RptOnlineRechargeReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		if (info != null) {
			request.addParam("type", info.type.toValue());
			request.addParam("balance", info.totalPrice);
			request.addParam("payNo", info.thirdPayId);
			request.addParam("cid", info.userId);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/purse/depositAccountOnline";
	}

}
