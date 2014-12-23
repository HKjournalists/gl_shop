package com.glshop.platform.api.contract;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetCompanyEvaListResult;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取企业评价列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetCompanyEvaListReq extends BaseRequest<GetCompanyEvaListResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	public GetCompanyEvaListReq(Object invoker, IReturnCallback<GetCompanyEvaListResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetCompanyEvaListResult getResultObj() {
		return new GetCompanyEvaListResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("ID", companyId);
		request.addParam("type", "0");
	}

	@Override
	protected void parseData(GetCompanyEvaListResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<EvaluationInfoModel> itemList = new ArrayList<EvaluationInfoModel>();
		if (BeanUtils.isNotEmpty(items)) {
			for (ResultItem evaItem : items) {
				EvaluationInfoModel info = new EvaluationInfoModel();
				info.id = evaItem.getString("id");
				info.evaluaterCID = evaItem.getString("cid");
				info.user = evaItem.getString("cname");
				info.contractId = evaItem.getString("oid");
				info.satisfactionPer = evaItem.getInt("satisfaction");
				info.sincerityPer = evaItem.getInt("credit");
				info.content = evaItem.getString("evaluation");
				info.dateTime = evaItem.getString("cratedate");
				info.isSingleLine = true;
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
