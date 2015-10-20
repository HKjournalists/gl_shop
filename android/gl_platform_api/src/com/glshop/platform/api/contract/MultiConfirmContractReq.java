package com.glshop.platform.api.contract;

import com.glshop.platform.api.DataConstants.ContractConfirmType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 合同多功能确认请求(货款确认申请、同意货款申请、申请仲裁)
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class MultiConfirmContractReq extends BaseRequest<CommonResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	/**
	 * 确认类型
	 */
	public ContractConfirmType confirmType = ContractConfirmType.PAYMENT_APPLY;

	/**
	 * 议价后单价
	 */
	public String disUnitPrice;

	/**
	 * 议价后数量
	 */
	public String disAmount;

	public MultiConfirmContractReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.addParam("operateType", confirmType.toValue());
		if (confirmType == ContractConfirmType.PAYMENT_APPLY) {
			request.addParam("disPrice", disUnitPrice);
			request.addParam("disNum", disAmount);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/contract/applyOrAgreeOrArbitrateFinalEstimate";
	}

}
