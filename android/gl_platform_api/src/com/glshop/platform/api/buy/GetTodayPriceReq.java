package com.glshop.platform.api.buy;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.data.GetTodayPriceResult;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取今日报价信息列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetTodayPriceReq extends BaseRequest<GetTodayPriceResult> {

	/**
	 * 区域编号
	 */
	public String areaCode;

	/**
	 * 商品类型编号
	 */
	public String productCode;

	/**
	 * 货物类型
	 */
	public ProductType productType;

	public GetTodayPriceReq(Object invoker, IReturnCallback<GetTodayPriceResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetTodayPriceResult getResultObj() {
		return new GetTodayPriceResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("pcode", productCode);
		request.addParam("area", areaCode);
	}

	@Override
	protected void parseData(GetTodayPriceResult result, ResultItem item) {
		List<ResultItem> items = (List<ResultItem>) item.get("data");
		result.datas = new ArrayList<TodayPriceModel>();
		for (ResultItem itemPrice : items) {
			TodayPriceModel model = new TodayPriceModel();
			model.productName = itemPrice.getString("pname");
			model.productSepcType = itemPrice.getString("ptype");
			model.productSepcId = itemPrice.getString("pid");
			model.todayPrice = itemPrice.getString("todayPrice");
			//model.todayPrice = model.todayPrice.substring(0, model.todayPrice.length() >= 4 ? 4 : model.todayPrice.length());
			model.yesterdayPrice = itemPrice.getString("yesterdayPrice");
			result.datas.add(model);
		}
	}

	@Override
	protected String getTypeURL() {
		return "/product/price/getToday";
	}

}
