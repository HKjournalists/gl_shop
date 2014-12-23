package com.glshop.platform.api.contract;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 议价接口(包括第一次议价和第二次议价)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class ContractNegotiateReq extends BaseRequest<CommonResult> {

	/**
	 * 议价信息
	 */
	public NegotiateInfoModel info;

	public ContractNegotiateReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		if (info != null) {
			request.addParam("OID", info.contractId);
			request.addParam("LID", info.pid);
			if (info.negUnitPrice != 0) {
				request.addParam("disPrice", info.negUnitPrice);
			}
			if (info.negAmount != 0) {
				request.addParam("disNum", info.negAmount);
			}
			request.addParam("reason", info.reason);
			request.addParam("remark", info.remarks);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/validateGoodsDisPrice";
	}

}
