package com.glshop.platform.api.profile;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 删除卸货地址请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class DeleteAddrReq extends BaseRequest<CommonResult> {

	/**
	 * 卸货地址ID
	 */
	public String addrId;

	public DeleteAddrReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("id", addrId);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/copn/address/del";
	}

}
