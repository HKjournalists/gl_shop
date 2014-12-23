package com.glshop.platform.api.buy;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.ProductType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.buy.data.GetPriceForecastResult;
import com.glshop.platform.api.buy.data.model.ForecastPriceModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取价格趋势信息列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetPriceForecastReq extends BaseRequest<GetPriceForecastResult> {

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

	/**
	 * 当前页数
	 */
	public int pageIndex;

	/**
	 * 每页条数
	 */
	public int pageSize;

	public GetPriceForecastReq(Object invoker, IReturnCallback<GetPriceForecastResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetPriceForecastResult getResultObj() {
		return new GetPriceForecastResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("pcode", productCode);
		request.addParam("area", areaCode);
	}

	@Override
	protected void parseData(GetPriceForecastResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		result.datas = new ArrayList<ForecastPriceModel>();
		for (ResultItem itemPrice : items) {
			ForecastPriceModel model = new ForecastPriceModel();
			model.productName = itemPrice.getString("pname");
			model.productSepcType = itemPrice.getString("ptype");
			model.productSepcId = itemPrice.getString("pid");
			model.todayPrice = itemPrice.getString("todayPrice");
			model.oneWeekForecastPrice = itemPrice.getString("basePrice1");
			model.twoWeekForecastPrice = itemPrice.getString("basePrice2");
			result.datas.add(model);
		}
	}

	@Override
	protected String getTypeURL() {
		return "/product/price/getHope";
	}

}
