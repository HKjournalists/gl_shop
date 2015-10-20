package com.glshop.platform.api.profile;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 修改企业介绍信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class UpdateCompanyIntroReq extends BaseRequest<CommonResult> {

	/**
	 * 企业介绍信息
	 */
	public CompanyIntroInfoModel info;

	public UpdateCompanyIntroReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("cid", info.companyId);
		request.addParam("mark", info.introduction);
		if (info.imgList != null && info.imgList.size() > 0) {
			StringBuffer imgId = new StringBuffer();
			for (int i = 0; i < info.imgList.size(); i++) {
				imgId.append(info.imgList.get(i).cloudId + (i == info.imgList.size() - 1 ? "" : ","));
			}
			request.addParam("companyImgIds", imgId.toString());
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/copn/updateIntroduction";
	}

}
