package com.glshop.platform.api.contract;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractStatusType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetToPayContractsResult;
import com.glshop.platform.api.contract.data.model.ToPayContractInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取待付货款列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetToPayContractsReq extends BaseRequest<GetToPayContractsResult> {

	public GetToPayContractsReq(Object invoker, IReturnCallback<GetToPayContractsResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetToPayContractsResult getResultObj() {
		return new GetToPayContractsResult();
	}

	@Override
	protected void buildParams() {

	}

	@Override
	protected void parseData(GetToPayContractsResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<ToPayContractInfoModel> itemList = new ArrayList<ToPayContractInfoModel>();
		for (ResultItem contractItem : items) {
			ToPayContractInfoModel info = new ToPayContractInfoModel();
			info.contractId = contractItem.getString("id");
			info.lifeCycle = ContractLifeCycle.convert(contractItem.getInt("lifecycle|val"));
			info.statusType = ContractStatusType.convert(contractItem.getInt("otype|val"));
			info.productName = contractItem.getString("productName");
			info.buyType = BuyType.convert(contractItem.getInt("saleType|val"));
			info.buyCompanyId = contractItem.getString("buyerid");
			info.buyCompanyName = contractItem.getString("buyerName");
			info.sellCompanyId = contractItem.getString("sellerid");
			info.sellCompanyName = contractItem.getString("sellerName");
			info.createTime = contractItem.getString("creatime");
			info.expireTime = contractItem.getString("limittime");
			info.amount = contractItem.getString("totalnum");
			info.toPayMoney = contractItem.getString("totalamount");
			itemList.add(info);
		}
		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/contract/unPayFundsContractList";
	}

}
