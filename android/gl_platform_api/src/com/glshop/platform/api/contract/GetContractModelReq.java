package com.glshop.platform.api.contract;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractStatusType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetContractModelResult;
import com.glshop.platform.api.contract.data.model.ContractBuyStatusInfo;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * @Description : 获取合同模板详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetContractModelReq extends BaseRequest<GetContractModelResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	public GetContractModelReq(Object invoker, IReturnCallback<GetContractModelResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetContractModelResult getResultObj() {
		return new GetContractModelResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetContractModelResult result, ResultItem item) {
		ResultItem modelItem = (ResultItem) item.get("DATA|bean");
		ContractModelInfo info = new ContractModelInfo();
		if (modelItem != null) {
			info.contractId = modelItem.getString("id");
			info.contractType = ContractType.convert(modelItem.getInt("status|val"));
			info.lifeCycle = ContractLifeCycle.convert(modelItem.getInt("lifecycle|val"));
			info.statusType = ContractStatusType.convert(modelItem.getInt("otype|val"));
			info.productName = modelItem.getString("productName");
			info.amount = modelItem.getString("totalamount");
			info.buyType = BuyType.convert(modelItem.getInt("saleType|val"));
			info.buyCompanyId = modelItem.getString("buyerid");
			info.buyCompanyName = modelItem.getString("buyerName");
			info.sellCompanyId = modelItem.getString("sellerid");
			info.sellCompanyName = modelItem.getString("sellerName");
			info.createTime = modelItem.getString("creatime");
			info.expireTime = modelItem.getString("limittime");

			// 解析买家状态
			ResultItem buyerStatusItem = (ResultItem) modelItem.get("buyerStatus");
			if (buyerStatusItem != null) {
				ContractBuyStatusInfo buyStatus = parseStatusInfo(buyerStatusItem);
				info.buyerStatus = buyStatus;
			}

			// 解析卖家状态
			ResultItem sellerStatusItem = (ResultItem) modelItem.get("sellerStatus");
			if (sellerStatusItem != null) {
				ContractBuyStatusInfo sellerStatus = parseStatusInfo(sellerStatusItem);
				info.sellerStatus = sellerStatus;
			}

		}
		info.content = item.getString("DATA|template");
		result.data = info;
	}

	private ContractBuyStatusInfo parseStatusInfo(ResultItem statusItem) {
		ContractBuyStatusInfo statusInfo = new ContractBuyStatusInfo();
		statusInfo.id = statusItem.getString("id");
		statusInfo.contractId = statusItem.getString("oid");
		statusInfo.oprId = statusItem.getString("operator");
		statusInfo.oprDatetime = statusItem.getString("operationtime");
		statusInfo.lifeCycle = ContractLifeCycle.convert(statusItem.getInt("orderstatus|val"));
		statusInfo.oprType = ContractOprType.convert(statusItem.getInt("type|val"));
		statusInfo.remarks = statusItem.getString("remark");
		return statusInfo;
	}

	@Override
	protected String getTypeURL() {
		return "/contract/getContractDetailTemplate";
	}

}
