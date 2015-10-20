package com.glshop.platform.api.profile;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.GetAddrInfoResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * @Description : 获取卸货地址详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetAddrInfoReq extends BaseRequest<GetAddrInfoResult> {

	/**
	 * 卸货地址ID
	 */
	public String addrId;

	public GetAddrInfoReq(Object invoker, IReturnCallback<GetAddrInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetAddrInfoResult getResultObj() {
		return new GetAddrInfoResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("id", addrId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetAddrInfoResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		AddrInfoModel info = new AddrInfoModel();
		if (items.size() > 0) {
			ResultItem addrItem = items.get(0);
			info.addrId = addrItem.getString("id");
			info.companyId = addrItem.getString("cid");
			info.areaCode = addrItem.getString("areacode");
			info.areaName = addrItem.getString("areaFullName");
			info.deliveryAddrDetail = addrItem.getString("address");
			info.uploadPortWaterDepth = addrItem.getDouble("deep");
			info.uploadPortShippingWaterDepth = addrItem.getDouble("realdeep");
			info.shippingTon = addrItem.getDouble("shippington");
			info.isDefaultAddr = "1".equals(addrItem.getString("status"));
		}
		result.data = info;
	}

	@Override
	protected String getTypeURL() {
		return "/copn/address/getInfo";
	}

}
