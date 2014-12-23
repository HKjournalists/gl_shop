package com.glshop.platform.api.contract;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetEndedContractInfoResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取已结束合同详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
@Deprecated
public class GetEndedContractInfoReq extends BaseRequest<GetEndedContractInfoResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	public GetEndedContractInfoReq(Object invoker, IReturnCallback<GetEndedContractInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetEndedContractInfoResult getResultObj() {
		return new GetEndedContractInfoResult();
	}

	@Override
	protected void buildParams() {

	}

	@Override
	protected void parseData(GetEndedContractInfoResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return null;
	}

}
