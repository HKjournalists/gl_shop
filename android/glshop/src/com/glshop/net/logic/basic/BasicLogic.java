package com.glshop.net.logic.basic;

import android.content.Context;

import com.glshop.net.logic.model.RespInfo;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.base.logic.BaseLogic;

/**
 * @Description : Logic基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-12-15 下午4:25:38
 */
public abstract class BasicLogic extends BaseLogic {

	public BasicLogic(Context context) {
		super(context);
	}

	protected RespInfo getOprRespInfo(CommonResult result) {
		RespInfo respInfo = new RespInfo();
		if (result != null && result.error != null) {
			respInfo.errorCode = result.error.getErrorCode();
			respInfo.errorMsg = result.error.getErrorMessage();
		}
		return respInfo;
	}

	protected RespInfo getOprRespInfo(Object invoker, CommonResult result) {
		RespInfo respInfo = getOprRespInfo(result);
		respInfo.invoker = invoker;
		return respInfo;
	}

	protected RespInfo getOprRespInfo(Object invoker, Object data, CommonResult result) {
		RespInfo respInfo = getOprRespInfo(result);
		respInfo.invoker = invoker;
		respInfo.data = data;
		return respInfo;
	}
}
