package com.glshop.platform.api.contract;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.ContractLifeCycle;
import com.glshop.platform.api.DataConstants.ContractOprType;
import com.glshop.platform.api.DataConstants.ContractStatusType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.DataConstants.DealDirectionType;
import com.glshop.platform.api.DataConstants.DealOprType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.contract.data.GetContractInfoResult;
import com.glshop.platform.api.contract.data.model.ArbitrateInfoModel;
import com.glshop.platform.api.contract.data.model.ContractBuyStatusInfo;
import com.glshop.platform.api.contract.data.model.ContractInfoModel;
import com.glshop.platform.api.contract.data.model.ContractModelInfo;
import com.glshop.platform.api.contract.data.model.NegotiateInfoModel;
import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 是否获取合同模板信息
	 */
	public boolean isGetModel;

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
		if (isGetModel) {
			request.addParam("getTemplate", "1");
		}
	}

	@Override
	protected void parseData(GetContractInfoResult result, ResultItem item) {
		ResultItem modelItem = (ResultItem) item.get("DATA");
		if (modelItem != null) {
			ContractInfoModel info = new ContractInfoModel();
			info.contractId = modelItem.getString("id");
			info.buyType = BuyType.convert(modelItem.getInt("saleType|val"));
			info.contractType = ContractType.convert(modelItem.getEnumValue("status"));
			info.myContractType = ContractType.convert(modelItem.getEnumValue("myContractType"));
			info.lifeCycle = ContractLifeCycle.convert(modelItem.getInt("lifecycle|val"));
			info.statusType = ContractStatusType.convert(modelItem.getInt("otype|val"));
			info.operator = modelItem.getString("operator");
			info.operatorTime = modelItem.getString("operationTime");
			info.buyCompanyId = modelItem.getString("buyerid");
			info.buyCompanyName = modelItem.getString("buyerName");
			info.sellCompanyId = modelItem.getString("sellerid");
			info.sellCompanyName = modelItem.getString("sellerName");
			info.productName = modelItem.getString("productName");
			info.productCode = modelItem.getString("productCode");
			info.productSubCode = modelItem.getString("productType");
			info.productSpecId = modelItem.getString("productId");
			info.unitPrice = modelItem.getDouble("price");
			info.tradeAmount = modelItem.getDouble("totalnum");
			info.finalPayMoney = modelItem.getDouble("totalamount");
			info.finalyAmount=modelItem.getDouble("settleamount");
			info.payedMoney = modelItem.getDouble("payFundsAmount");
			info.receivedMoney = modelItem.getDouble("amount");
			info.updateTime = modelItem.getString("updatetime");
			info.expireTime = modelItem.getString("limittime");
			info.payExpireTime = modelItem.getString("payGoodsLimitTime");
			info.isBuyerEva = modelItem.getEnumValue("buyerEvaluation") == 1;
			info.isSellerEva = modelItem.getEnumValue("sellerEvaluation") == 1;

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

			// 货款确认信息
			List<ResultItem> finalNegItemList = (ArrayList<ResultItem>) item.get("DATA|fundGoodsDisPriceList");
			info.finalNegotiateInfo = parseFinalNegotiateInfo(finalNegItemList, modelItem);

			// 平台结算账单信息
			List<ResultItem> dealItemList = (ArrayList<ResultItem>) item.get("DATA|finalEstimateList");
			info.dealList = parseDealInfo(dealItemList);

			// 平台仲裁信息
			List<ResultItem> arbitrationItemList = (ArrayList<ResultItem>) item.get("DATA|arbitrationDisPriceList");
			ResultItem arbitrationInfo = (ResultItem) item.get("DATA|arbitrationProcessInfo");
			if (arbitrationInfo != null && BeanUtils.isNotEmpty(arbitrationItemList)) {
				info.arbitrateInfo = parseArbitrateInfo(arbitrationItemList, arbitrationInfo);
			}

			// 解析合同模板信息
			if (isGetModel) {
				info.modelInfo = parseModelInfo(modelItem);
			}

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
		statusInfo.preLifeCycle = ContractLifeCycle.convert(statusItem.getInt("oldstatus|val"));
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
				negInfo.unitPrice = contractItem.getDouble("price");
				negInfo.tradeAmount = contractItem.getDouble("totalnum");
				negInfo.preNegUnitPrice = itemDis.getDouble("beginamount");
				negInfo.negUnitPrice = itemDis.getDouble("endamount");
				negInfo.preNegAmount = itemDis.getDouble("beginnum");
				negInfo.negAmount = itemDis.getDouble("endnum");
				negInfo.reason = itemDis.getString("reason");
				negInfoList.add(negInfo);
			}
		}
		return negInfoList;
	}

	/**
	 * 解析货款确认信息
	 * @param items
	 * @return
	 */
	private NegotiateInfoModel parseFinalNegotiateInfo(List<ResultItem> items, ResultItem contractItem) {
		NegotiateInfoModel negInfo = null;
		if (BeanUtils.isNotEmpty(items)) {
			ResultItem itemDis = items.get(0);
			negInfo = new NegotiateInfoModel();
			negInfo.id = itemDis.getString("id");
			negInfo.pid = itemDis.getString("dlid");
			negInfo.contractId = itemDis.getString("oid");
			negInfo.operator = itemDis.getString("operator");
			negInfo.oprTime = itemDis.getString("operationtime");
			negInfo.unitPrice = contractItem.getDouble("beginamount");
			negInfo.tradeAmount = contractItem.getDouble("beginnum");
			negInfo.negUnitPrice = itemDis.getDouble("endamount");
			negInfo.negAmount = itemDis.getDouble("endnum");
		}
		return negInfo;
	}

	/**
	 * 解析账单信息
	 * @param items
	 * @return
	 */
	private List<DealSummaryInfoModel> parseDealInfo(List<ResultItem> items) {
		List<DealSummaryInfoModel> dealInfoList = new ArrayList<DealSummaryInfoModel>();
		if (BeanUtils.isNotEmpty(items)) {
			for (ResultItem infoItem : items) {
				DealSummaryInfoModel model = new DealSummaryInfoModel();
				model.id = infoItem.getString("id");
				model.dealTime = infoItem.getString("paytime");
				model.dealMoney = infoItem.getDouble("amount");
				model.balance = infoItem.getDouble("balance");
				model.directionType = DealDirectionType.convert(infoItem.getEnumValue("direction"));
				model.oprType = DealOprType.convert(infoItem.getEnumValue("otype"));
				dealInfoList.add(model);
			}
		}
		return dealInfoList;
	}

	/**
	 * 解析仲裁信息
	 * @param items
	 * @return
	 */
	private ArbitrateInfoModel parseArbitrateInfo(List<ResultItem> items, ResultItem abrInfo) {
		ArbitrateInfoModel arbInfo = new ArbitrateInfoModel();
		if (BeanUtils.isNotEmpty(items)) {
			arbInfo.id = abrInfo.getString("id");
			arbInfo.creatorID = abrInfo.getString("creater");
			arbInfo.remarks = abrInfo.getString("dealresult");
			arbInfo.dealTime = abrInfo.getString("dealTime");
			// 仲裁单价及数量
			ResultItem disItem = items.get(0);
			if (disItem != null) {
				arbInfo.unitPrice = disItem.getDouble("endamount");
				arbInfo.amount = disItem.getDouble("endnum");
			}
		}
		return arbInfo;
	}

	/**
	 * 解析合同模板信息
	 */
	private ContractModelInfo parseModelInfo(ResultItem modelItem) {
		ContractModelInfo info = null;
		if (modelItem != null) {
			String template = modelItem.getString("contractTemplate");
			if (StringUtils.isNotEmpty(template)) {
				info = new ContractModelInfo();
				info.contractId = modelItem.getString("id");
				info.buyId = modelItem.getString("fid");
				info.contractType = ContractType.convert(modelItem.getEnumValue("status"));
				info.myContractType = ContractType.convert(modelItem.getEnumValue("myContractType"));
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

				// 合同模板信息
				info.content = template;
			}
		}

		return info;
	}

	@Override
	protected String getTypeURL() {
		return "/contract/getContractDetailInfoEx";
	}

}
