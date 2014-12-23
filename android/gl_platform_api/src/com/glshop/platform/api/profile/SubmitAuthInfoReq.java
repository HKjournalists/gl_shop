package com.glshop.platform.api.profile;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.AuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 提交认证请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class SubmitAuthInfoReq extends BaseRequest<CommonResult> {

	public AuthInfoModel info;

	public SubmitAuthInfoReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("ctypeValue", info.profileType.toValue());
		// 设置认证照片ID
		request.addParam("imgid", info.authImgInfo.cloudId);
		// 设置卸货地址ID
		if (info.addrInfo != null) {
			request.addParam("addressid", info.addrInfo.addrId);
		}
		// 设置企业介绍信息
		CompanyIntroInfoModel introInfo = info.introInfo;
		if (introInfo != null) {
			request.addParam("mark", info.introInfo.introduction);
			if (BeanUtils.isNotEmpty(introInfo.imgList)) {
				StringBuffer imgId = new StringBuffer();
				for (int i = 0; i < introInfo.imgList.size(); i++) {
					imgId.append(introInfo.imgList.get(i).cloudId + (i == introInfo.imgList.size() - 1 ? "" : ","));
				}
				request.addParam("companyImgIds", imgId.toString());
			}
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/copn/authApply";
	}

}
