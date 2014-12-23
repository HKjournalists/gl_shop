package com.glshop.platform.api.buy;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.OrderStatus;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.data.GetBuysResult;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModel;
import com.glshop.platform.api.buy.data.model.BuySummaryInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * @Description : 获取找买找卖信息列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetBuysReq extends BaseRequest<GetBuysResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 买卖类型
	 */
	public BuyType buyType;

	/**
	 * 当前页数
	 */
	public int pageIndex;

	/**
	 * 每页条数
	 */
	public int pageSize;

	/**
	 * 过滤类型
	 */
	public BuyFilterInfoModel filterInfo;

	public GetBuysReq(Object invoker, IReturnCallback<GetBuysResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetBuysResult getResultObj() {
		return new GetBuysResult();
	}

	@Override
	protected void buildParams() {
		request.setMethod(HttpMethod.GET);
		request.addParam("type", buyType.toValue());
		//request.addParam("cid", companyId);
		request.addParam("pageIndex", pageIndex);
		request.addParam("pageSize", pageSize);
		if (filterInfo != null) {
			switch (filterInfo.orderType) {
				case EXPIRY:
					request.addParam("orderEffTime", filterInfo.orderStatus == OrderStatus.DESC ? "1" : "0");
					break;
				case PRICE:
					request.addParam("orderPrice", filterInfo.orderStatus == OrderStatus.DESC ? "1" : "0");
					break;
				case CREDIT:
					request.addParam("orderCredit", filterInfo.orderStatus == OrderStatus.DESC ? "1" : "0");
					break;
			}
			if (filterInfo.areaCode != null && filterInfo.areaCode.length() > 0) {
				request.addParam("area", filterInfo.areaCode);
			}
			if (filterInfo.year != null && filterInfo.year.length() > 0) {
				request.addParam("year", filterInfo.year);
			}
			if (filterInfo.month != null && filterInfo.month.length() > 0) {
				request.addParam("month", filterInfo.month);
			}
			if (filterInfo.day != null && filterInfo.day.length() > 0) {
				request.addParam("day", filterInfo.day);
			}
			if (filterInfo.productType != null && filterInfo.productType.length() > 0) {
				request.addParam("ptype", filterInfo.productType);
			}
			if (filterInfo.productID != null && filterInfo.productID.length() > 0) {
				request.addParam("pid", filterInfo.productID);
			}
		}
	}

	@Override
	protected void parseData(GetBuysResult result, ResultItem item) {
		ResultItem dataItem = (ResultItem) item.get("DATA");

		List<ResultItem> items = (ArrayList<ResultItem>) dataItem.get("result");
		if (items != null && items.size() > 0) {
			List<BuySummaryInfoModel> itemList = new ArrayList<BuySummaryInfoModel>();
			for (ResultItem buyItem : items) {
				BuySummaryInfoModel info = new BuySummaryInfoModel();
				info.publishBuyId = buyItem.getString("id");
				info.companyId = buyItem.getString("cid");
				info.productName = buyItem.getString("pname");
				info.productCode = buyItem.getString("pcode");
				info.productSubCode = buyItem.getString("ptype");

				ResultItem typeItem = (ResultItem) buyItem.get("type");
				if (typeItem != null) {
					info.buyType = BuyType.convert(typeItem.getInt("val"));
				}

				info.unitPrice = buyItem.getFloat("price");
				info.tradeAmount = buyItem.getFloat("totalnum");
				info.tradeBeginDate = buyItem.getString("starttime");
				info.tradeEndDate = buyItem.getString("limitime");
				info.tradeArea = buyItem.getString("area");
				itemList.add(info);
			}
			result.datas = itemList;
		}

		result.totalCount = dataItem.getInt("totalSize");
	}

	@Override
	protected String getTypeURL() {
		return "/order/open/getOrderList";
	}

}
