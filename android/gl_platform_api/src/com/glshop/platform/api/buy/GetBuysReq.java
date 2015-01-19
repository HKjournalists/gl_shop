package com.glshop.platform.api.buy;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.OrderStatus;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.data.GetBuysResult;
import com.glshop.platform.api.buy.data.model.BuyFilterInfoModelV2;
import com.glshop.platform.api.buy.data.model.BuySummaryInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

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
	public BuyFilterInfoModelV2 filterInfo;

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
			request.addParam("startTime", filterInfo.tradeStartDate);
			request.addParam("endTime", filterInfo.tradeEndDate);
			request.addParam("pids", packFilterValue(filterInfo.productIdList));
			request.addParam("areaCodeProvince", packFilterValue(filterInfo.provinceCodeList));
			request.addParam("areaCodeArea", packFilterValue(filterInfo.districtCodeList));
		}
	}

	private String packFilterValue(List<String> list) {
		StringBuffer sb = new StringBuffer();
		if (BeanUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i) + (i == list.size() - 1 ? "" : ","));
			}
		}
		Logger.e(TAG, "Value = " + sb.toString());
		return sb.toString();
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
				info.productSpecId = buyItem.getString("pid");

				ResultItem typeItem = (ResultItem) buyItem.get("type");
				if (typeItem != null) {
					info.buyType = BuyType.convert(typeItem.getInt("val"));
				}

				// 单价及数量
				info.unitPrice = buyItem.getFloat("price");
				info.tradeAmount = buyItem.getFloat("totalnum");
				// 单位
				ResultItem unitTypeItem = (ResultItem) buyItem.get("unit");
				if (unitTypeItem != null) {
					info.unitType = ProductUnitType.convert(unitTypeItem.getString("val"));
				}
				info.tradePubDate = buyItem.getString("creatime");
				info.tradeBeginDate = buyItem.getString("starttime");
				info.tradeEndDate = buyItem.getString("endtime");
				info.tradeAreaCode = buyItem.getString("area");
				info.tradeAreaName = buyItem.getString("areaFullName");

				// 诚信度
				info.publisherCredit = (int) buyItem.getFloat("credit");

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
