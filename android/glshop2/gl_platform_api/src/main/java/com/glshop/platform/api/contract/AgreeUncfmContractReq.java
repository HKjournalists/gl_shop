package com.glshop.platform.api.contract;

import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.AgreeContractSignResult;
import com.glshop.platform.api.contract.data.model.AgreeContractInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 待确认合同的确认请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class AgreeUncfmContractReq extends BaseRequest<AgreeContractSignResult> {

	/**
	 * 待确认合同ID
	 */
	public String contractId;

	public AgreeUncfmContractReq(Object invoker, IReturnCallback<AgreeContractSignResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected AgreeContractSignResult getResultObj() {
		return new AgreeContractSignResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
	}

	@Override
	protected void parseData(AgreeContractSignResult result, ResultItem item) {
		List<ResultItem> items = item.getItems("DATA");
		AgreeContractInfoModel info = new AgreeContractInfoModel();
		if (BeanUtils.isNotEmpty(items)) {
			info.isAnotherSigned = true;
			ResultItem ri = items.get(0);
			info.singedDatetime = ri.getString("operationtime");
		} else {
			info.isAnotherSigned = false;
		}
		result.data = info;
	}

	@Override
	protected String getTypeURL() {
		return "/contract/toConfirmContract";
	}

}
