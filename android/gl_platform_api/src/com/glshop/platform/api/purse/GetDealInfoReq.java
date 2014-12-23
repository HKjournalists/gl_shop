package com.glshop.platform.api.purse;

import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.DataConstants.PayType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.purse.data.GetDealInfoResult;
import com.glshop.platform.api.purse.data.model.DealInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取钱包流水详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetDealInfoReq extends BaseRequest<GetDealInfoResult> {

	/**
	 * 明细编号
	 */
	public String id;

	public GetDealInfoReq(Object invoker, IReturnCallback<GetDealInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetDealInfoResult getResultObj() {
		return new GetDealInfoResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("PID", id);
	}

	@Override
	protected void parseData(GetDealInfoResult result, ResultItem item) {
		ResultItem infoItem = (ResultItem) item.get("DATA");
		DealInfoModel model = new DealInfoModel();
		model.id = infoItem.getString("id");
		model.dealTime = infoItem.getString("paytime");
		model.dealMoney = infoItem.getFloat("needamount");
		model.balance = infoItem.getFloat("balance");
		model.directionType = DealDirectionType.convert(infoItem.getEnumValue("direction"));
		model.oprType = DealOprType.convert(infoItem.getEnumValue("otype"));
		model.payType = PayType.convert(infoItem.getEnumValue("paytype"));
		model.contractId = infoItem.getString("oid");
		result.data = model;
	}

	@Override
	protected String getTypeURL() {
		return "/purse/getPayRecordDetail";
	}

}
