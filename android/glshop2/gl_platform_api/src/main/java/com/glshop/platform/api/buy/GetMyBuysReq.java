package com.glshop.platform.api.buy;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.BuyStatus;
import com.glshop.platform.api.DataConstants.BuyType;
import com.glshop.platform.api.DataConstants.MyBuyFilterType;
import com.glshop.platform.api.DataConstants.ProductUnitType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.data.GetMyBuysResult;
import com.glshop.platform.api.buy.data.model.AreaPriceInfoModel;
import com.glshop.platform.api.buy.data.model.MyBuySummaryInfoModel;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取我的供求信息列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetMyBuysReq extends BaseRequest<GetMyBuysResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 买卖过滤类型
	 */
	public MyBuyFilterType filterType = MyBuyFilterType.ALL;

	/**
	 * 当前页数
	 */
	public int pageIndex;

	/**
	 * 每页条数
	 */
	public int pageSize;

	public GetMyBuysReq(Object invoker, IReturnCallback<GetMyBuysResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetMyBuysResult getResultObj() {
		return new GetMyBuysResult();
	}

	@Override
	protected void buildParams() {
		if (filterType != MyBuyFilterType.ALL) {
			request.addParam("type", filterType.toValue());
		}
		request.addParam("cid", companyId);
		request.addParam("pageIndex", pageIndex);
		request.addParam("pageSize", pageSize);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetMyBuysResult result, ResultItem item) {
		ResultItem dataItem = (ResultItem) item.get("DATA");
		List<ResultItem> items = (ArrayList<ResultItem>) dataItem.get("result");
		List<MyBuySummaryInfoModel> itemList = new ArrayList<MyBuySummaryInfoModel>();
		if (items != null) {
			for (ResultItem buyItem : items) {
				MyBuySummaryInfoModel info = new MyBuySummaryInfoModel();
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
				info.unitPrice = buyItem.getDouble("price");
				info.tradeAmount = buyItem.getDouble("totalnum");
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

				ResultItem statusItem = (ResultItem) buyItem.get("status");
				if (typeItem != null) {
					info.buyStatus = BuyStatus.convert(statusItem.getInt("val"));
				}

				// 多地域发布
				info.isMoreArea = buyItem.getEnumValue("morearea", 1) == 2;
				if (info.isMoreArea) {
					List<ResultItem> areaItemList = (List<ResultItem>) buyItem.get("moreAreaList");
					if (BeanUtils.isNotEmpty(areaItemList)) {
						List<AreaPriceInfoModel> areaInfoList = new ArrayList<AreaPriceInfoModel>();
						for (ResultItem area : areaItemList) {
							AreaPriceInfoModel priceInfo = new AreaPriceInfoModel();
							AreaInfoModel areaInfo = new AreaInfoModel();
							areaInfo.code = area.getString("area");
							priceInfo.areaInfo = areaInfo;
							priceInfo.unitPrice = area.getDouble("price");
							areaInfoList.add(priceInfo);
						}
						info.areaInfoList = areaInfoList;
					}
				}

				itemList.add(info);
			}
		}
		result.totalCount = dataItem.getInt("totalSize");
		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/order/getMyList";
	}

}
