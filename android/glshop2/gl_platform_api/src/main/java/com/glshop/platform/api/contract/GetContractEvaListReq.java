package com.glshop.platform.api.contract;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetContractEvaListResult;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取合同评价列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetContractEvaListReq extends BaseRequest<GetContractEvaListResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	public GetContractEvaListReq(Object invoker, IReturnCallback<GetContractEvaListResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetContractEvaListResult getResultObj() {
		return new GetContractEvaListResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("ID", contractId);
		request.addParam("type", "1");
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetContractEvaListResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<EvaluationInfoModel> itemList = new ArrayList<EvaluationInfoModel>();
		if (BeanUtils.isNotEmpty(items)) {
			for (ResultItem evaItem : items) {
				EvaluationInfoModel info = new EvaluationInfoModel();
				info.id = evaItem.getString("id");
				info.evaluaterCID = evaItem.getString("creater");
				info.beEvaluaterCID = evaItem.getString("cid");
				info.contractId = evaItem.getString("oid");
				info.satisfactionPer = evaItem.getInt("satisfaction");
				info.sincerityPer = evaItem.getInt("credit");
				info.content = evaItem.getString("evaluation");
				itemList.add(info);
			}
		}
		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/noAuthUrl/getEvaluationContractList/";
	}

}
