package com.glshop.platform.api.contract;

import com.glshop.platform.api.DataConstants.ContractCancelType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 合同多功能取消请求(取消、删除、移至已结束)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class MultiCancelContractReq extends BaseRequest<CommonResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	/**
	 * 取消类型
	 */
	public ContractCancelType cancelType = ContractCancelType.CANCEL;

	public MultiCancelContractReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.addParam("operateType", cancelType.toValue());
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/cancelDraftContract";
	}

}
