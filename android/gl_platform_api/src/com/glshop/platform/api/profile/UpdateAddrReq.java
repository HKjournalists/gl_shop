package com.glshop.platform.api.profile;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 更新卸货地址请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class UpdateAddrReq extends BaseRequest<CommonResult> {

	/**
	 * 卸货地址信息
	 */
	public AddrInfoModel info;

	public UpdateAddrReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("id", info.addrId);
		request.addParam("areacode", info.areaCode);
		request.addParam("address", info.deliveryAddrDetail);
		request.addParam("deep", info.uploadPortWaterDepth);
		//request.addParam("realdeep", info.uploadPortShippingWaterDepth);
		request.addParam("shippington", info.shippingTon);

		if (info.addrImageList != null) {
			StringBuffer imgId = new StringBuffer();
			for (int i = 0; i < info.addrImageList.size(); i++) {
				imgId.append(info.addrImageList.get(i).cloudId + (i == info.addrImageList.size() - 1 ? "" : ","));
			}
			request.addParam("addressImgIds", imgId.toString());
		}

	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/copn/address/mdy";
	}

}
