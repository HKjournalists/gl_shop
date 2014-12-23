package com.glshop.platform.api.purse;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.PurseType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.purse.data.GetPurseInfoResult;
import com.glshop.platform.api.purse.data.model.PurseInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取钱包基本信息详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetPurseInfoReq extends BaseRequest<GetPurseInfoResult> {

	/**
	 * 钱包类型
	 */
	public PurseType purseType = PurseType.DEPOSIT;

	public GetPurseInfoReq(Object invoker, IReturnCallback<GetPurseInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetPurseInfoResult getResultObj() {
		return new GetPurseInfoResult();
	}

	@Override
	protected void buildParams() {
		//request.addParam("type", purseType.toValue());
	}

	@Override
	protected void parseData(GetPurseInfoResult result, ResultItem item) {
		ResultItem dataItem = (ResultItem) item.get("DATA");
		List<ResultItem> items = (ArrayList<ResultItem>) dataItem.get("result");
		PurseInfoModel purseInfo = new PurseInfoModel();
		for (ResultItem purseItem : items) {
			ResultItem typeItem = (ResultItem) purseItem.get("passtype");
			if (typeItem != null) {
				int type = typeItem.getInt("val");
				if (type == 0) {
					purseInfo.companyId = purseItem.getString("cid");
					purseInfo.depositAccountId = purseItem.getString("id");
					purseInfo.depositBalance = purseItem.getFloat("amount");
					purseInfo.isDepositEnough = purseItem.getBoolean("guarantyEnough", false);
				} else if (type == 1) {
					purseInfo.companyId = purseItem.getString("cid");
					purseInfo.paymentAccountID = purseItem.getString("id");
					purseInfo.paymentBalance = purseItem.getFloat("amount");
					purseInfo.isPaymentEnough = purseItem.getBoolean("guarantyEnough", false);
				}
			}
		}
		purseInfo.toPayCount = dataItem.getInt("totalSize");
		result.data = purseInfo;
	}

	@Override
	protected String getTypeURL() {
		return "/purse/getPurseAccountInfo";
	}

}
