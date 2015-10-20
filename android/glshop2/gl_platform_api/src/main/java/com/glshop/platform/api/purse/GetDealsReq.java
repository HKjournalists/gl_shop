package com.glshop.platform.api.purse;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.DataConstants.PurseDealFilterType;
import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.purse.data.GetDealsResult;
import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取钱包流水列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetDealsReq extends BaseRequest<GetDealsResult> {

	/**
	 * 钱包类型
	 */
	public PurseType type;

	/**
	 * 流水过滤类型
	 */
	public PurseDealFilterType filterType = PurseDealFilterType.ALL;

	/**
	 * 当前页数
	 */
	public int pageIndex;

	/**
	 * 每页条数
	 */
	public int pageSize;

	public GetDealsReq(Object invoker, IReturnCallback<GetDealsResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetDealsResult getResultObj() {
		return new GetDealsResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("type", type.toValue());
		if (filterType != PurseDealFilterType.ALL) {
			request.addParam("direction", filterType.toValue());
		}
		request.addParam("pageIndex", pageIndex);
		request.addParam("pageSize", pageSize);
	}

	@Override
	protected void parseData(GetDealsResult result, ResultItem item) {
		List<ResultItem> items = (List<ResultItem>) item.get("DATA");
		result.datas = new ArrayList<DealSummaryInfoModel>();
		for (ResultItem infoItem : items) {
			DealSummaryInfoModel model = new DealSummaryInfoModel();
			model.id = infoItem.getString("id");
			model.dealTime = infoItem.getString("paytime");
			model.dealMoney = infoItem.getDouble("needamount");
			model.balance = infoItem.getDouble("balance");
			model.directionType = DealDirectionType.convert(infoItem.getEnumValue("direction"));
			model.oprType = DealOprType.convert(infoItem.getEnumValue("otype"));
			result.datas.add(model);
		}
	}

	@Override
	protected String getTypeURL() {
		return "/purse/getPayRecordList";
	}

}
