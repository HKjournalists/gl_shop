package com.glshop.platform.api.message;

import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 上报消息已读请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class RptMessageReadedReq extends BaseRequest<CommonResult> {

	/**
	 * 消息ID列表
	 */
	public List<String> idList;

	public RptMessageReadedReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		if (idList != null && idList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < idList.size(); i++) {
				if (i == 0) {
					sb.append(idList.get(i));
				} else {
					sb.append("," + idList.get(i));
				}
			}
			request.addParam("msgids", sb.toString());
		}
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/msg/read";
	}

}
