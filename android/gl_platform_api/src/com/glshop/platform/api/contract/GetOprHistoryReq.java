package com.glshop.platform.api.contract;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetOprHistoryResult;
import com.glshop.platform.api.contract.data.model.ContractOprInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * @Description : 获取合同操作历史记录列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetOprHistoryReq extends BaseRequest<GetOprHistoryResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	public GetOprHistoryReq(Object invoker, IReturnCallback<GetOprHistoryResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetOprHistoryResult getResultObj() {
		return new GetOprHistoryResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetOprHistoryResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<ContractOprInfoModel> itemList = new ArrayList<ContractOprInfoModel>();
		for (ResultItem oprItem : items) {
			ContractOprInfoModel info = new ContractOprInfoModel();
			info.id = oprItem.getString("id");
			info.contractId = oprItem.getString("oid");
			info.dateTime = oprItem.getString("operationtime");
			info.summary = oprItem.getString("result");
			itemList.add(info);
		}

		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/contract/getContractChangeHistory";
	}

}
