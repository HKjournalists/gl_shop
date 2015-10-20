package com.glshop.platform.api.purse;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 新增收款人请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class AddPayeeReq extends BaseRequest<CommonResult> {

	/**
	 * 收款人信息
	 */
	public PayeeInfoModel info;

	/**
	 * 短信验证码
	 */
	public String smsCode;

	public AddPayeeReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		if (info != null) {
			request.addParam("cid", info.companyId);
			request.addParam("carduser", info.name);
			request.addParam("bankcard", info.card);
			request.addParam("banktype", info.bankCode);
			request.addParam("bankname", info.subBank);
			if (info.certImgInfo != null) {
				request.addParam("imgid", info.certImgInfo.cloudId);
			}
			request.addParam("code", smsCode);
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/copn/accept/authApply";
	}

}
