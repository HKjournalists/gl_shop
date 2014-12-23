package com.glshop.platform.api.buy;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.buy.data.model.BuyInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 重新发布信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-31 下午3:19:29
 */
public class RepubBuyInfoReq extends BaseRequest<CommonResult> {

	/**
	 * 重新发布的发布信息
	 */
	public BuyInfoModel buyInfo;

	public RepubBuyInfoReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {

	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return null;
	}

}
