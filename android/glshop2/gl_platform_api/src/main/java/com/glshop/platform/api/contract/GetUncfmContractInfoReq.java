package com.glshop.platform.api.contract;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetUncfmContractInfoResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取未确认合同详情请求(使用GetContractDetailReq)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
@Deprecated
public class GetUncfmContractInfoReq extends BaseRequest<GetUncfmContractInfoResult> {

	/**
	 * 待确认合同ID
	 */
	public String contractId;

	public GetUncfmContractInfoReq(Object invoker, IReturnCallback<GetUncfmContractInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetUncfmContractInfoResult getResultObj() {
		return new GetUncfmContractInfoResult();
	}

	@Override
	protected void buildParams() {

	}

	@Override
	protected void parseData(GetUncfmContractInfoResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/getContractDetailTemplate";
	}

}
