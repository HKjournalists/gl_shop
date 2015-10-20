package com.glshop.platform.api.contract;

import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 同意议价和验收通过接口(包括第一次议价和第二次议价)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class ContractAcceptanceReq extends BaseRequest<CommonResult> {

	/**
	 * 合同编号
	 */
	public String contractId;

	/**
	 * 操作类型
	 */
	public int type;

	/**
	 * 父ID
	 */
	public String pId;

	public ContractAcceptanceReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.addParam("operate", /*DataConstants.ContractOprType.VALIDATE_PASS.toValue()*/type);
		request.addParam("fid", pId);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/applyOrPassGoodsInfo";
	}

}
