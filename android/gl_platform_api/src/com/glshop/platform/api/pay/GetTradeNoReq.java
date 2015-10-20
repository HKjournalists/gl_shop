package com.glshop.platform.api.pay;

import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.pay.data.GetTradeNoInfoResult;
import com.glshop.platform.api.pay.data.model.TradeNoInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取第三方支付平台交易流水号请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetTradeNoReq extends BaseRequest<GetTradeNoInfoResult> {

	/**
	 * 钱包类型
	 */
	public PurseType purseType;

	/**
	 * 支付金额
	 */
	public double money;

	public GetTradeNoReq(Object invoker, IReturnCallback<GetTradeNoInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetTradeNoInfoResult getResultObj() {
		return new GetTradeNoInfoResult();
	}

	@Override
	protected void buildParams() {
		request.setTimeout(90000); // 设置连接超时90s
		request.setReadtime(90000); // 设置读写超时90s
		request.addParam("type", purseType.toValue());
		request.addParam("balance", money);
		//request.addParam("cid", "");
	}

	@Override
	protected void parseData(GetTradeNoInfoResult result, ResultItem item) {
		ResultItem dataItem = (ResultItem) item.get("DATA");
		TradeNoInfoModel tnInfo = new TradeNoInfoModel();
		tnInfo.tn = dataItem.getString("tn");
		tnInfo.oid = dataItem.getString("oid");
		tnInfo.datetime = dataItem.getString("tnTime");
		result.data = tnInfo;
	}

	@Override
	protected String getTypeURL() {
		return "/purse/getUnionPayTnOrderId";
	}

}
