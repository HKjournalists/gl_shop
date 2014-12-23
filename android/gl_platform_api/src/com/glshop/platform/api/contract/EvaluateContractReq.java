package com.glshop.platform.api.contract;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 评价合同请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class EvaluateContractReq extends BaseRequest<CommonResult> {

	/**
	 * 评价信息
	 */
	public EvaluationInfoModel info;

	public EvaluateContractReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
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
			request.addParam("CID", info.beEvaluaterCID);
			request.addParam("satisfaction", info.satisfactionPer);
			request.addParam("credit", info.sincerityPer);
			request.addParam("evaluation", info.content);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/toEvaluateContract";
	}

}
