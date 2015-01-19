package com.glshop.platform.api.contract;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractStatusType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetContractInfoResult;
import com.glshop.platform.api.contract.data.model.ContractBuyStatusInfo;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取合同详情(包括进行中和已结束)请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetContractInfoReq extends BaseRequest<GetContractInfoResult> {

	/**
	 * 合同ID
	 */
	public String contractId;

	public GetContractInfoReq(Object invoker, IReturnCallback<GetContractInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetContractInfoResult getResultObj() {
		return new GetContractInfoResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("OID", contractId);
	}

	@Override
	protected void parseData(GetContractInfoResult result, ResultItem item) {
		ResultItem modelItem = (ResultItem) item.get("DATA");
		if (modelItem != null) {
			ContractInfoModel info = new ContractInfoModel();
			info.contractId = modelItem.getString("id");
			info.buyType = BuyType.convert(modelItem.getInt("saleType|val"));
			info.contractType = ContractType.convert(modelItem.getInt("status|val"));
			info.lifeCycle = ContractLifeCycle.convert(modelItem.getInt("lifecycle|val"));
			info.statusType = ContractStatusType.convert(modelItem.getInt("otype|val"));
			info.buyCompanyId = modelItem.getString("buyerid");
			info.buyCompanyName = modelItem.getString("buyerName");
			info.sellCompanyId = modelItem.getString("sellerid");
			info.sellCompanyName = modelItem.getString("sellerName");
			info.productName = modelItem.getString("productName");
			info.productCode = modelItem.getString("productCode");
			info.productSubCode = modelItem.getString("productType");
			info.productSpecId = modelItem.getString("productId");
			info.unitPrice = modelItem.getFloat("price");
			info.tradeAmount = modelItem.getFloat("totalnum");
			info.finalPayMoney = modelItem.getFloat("totalamount");
			info.updateTime = modelItem.getString("updatetime");
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

			// 第一次议价列表信息
			List<ResultItem> firstNegItemList = (ArrayList<ResultItem>) item.get("DATA|sampleCheckDisPriceList");
			info.firstNegotiateList = parseNegotiateInfo(firstNegItemList, modelItem);

			// 第二次议价列表信息
			List<ResultItem> secondNegItemList = (ArrayList<ResultItem>) item.get("DATA|fullTakeoverDisPriceList");
			info.secondNegotiateList = parseNegotiateInfo(secondNegItemList, modelItem);

			result.data = info;
		}
	}

	/**
	 * 解析买卖状态信息
	 * @param statusItem
	 * @return
	 */
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

	/**
	 * 解析议价信息
	 * @param items
	 * @return
	 */
	private List<NegotiateInfoModel> parseNegotiateInfo(List<ResultItem> items, ResultItem contractItem) {
		List<NegotiateInfoModel> negInfoList = new ArrayList<NegotiateInfoModel>();
		if (BeanUtils.isNotEmpty(items)) {
			for (int i = 0; i < items.size(); i++) {
				ResultItem itemDis = items.get(i);
				NegotiateInfoModel negInfo = new NegotiateInfoModel();
				negInfo.id = itemDis.getString("id");
				negInfo.pid = itemDis.getString("dlid");
				negInfo.contractId = itemDis.getString("oid");
				negInfo.operator = itemDis.getString("operator");
				negInfo.oprTime = itemDis.getString("operationtime");
				negInfo.unitPrice = contractItem.getFloat("price");
				negInfo.tradeAmount = contractItem.getFloat("totalnum");
				negInfo.preNegUnitPrice = itemDis.getFloat("beginamount");
				negInfo.negUnitPrice = itemDis.getFloat("endamount");
				negInfo.preNegAmount = itemDis.getFloat("beginnum");
				negInfo.negAmount = itemDis.getFloat("endnum");
				negInfo.reason = itemDis.getString("reason");
				negInfoList.add(negInfo);
			}
		}
		return negInfoList;
	}

	@Override
	protected String getTypeURL() {
		return "/contract/getContractDetailInfo";
	}

}
