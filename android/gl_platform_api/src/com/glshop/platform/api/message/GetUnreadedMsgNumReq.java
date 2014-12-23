package com.glshop.platform.api.message;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.message.data.GetUnreadedMsgResult;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * @Description : 获取消息列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetUnreadedMsgNumReq extends BaseRequest<GetUnreadedMsgResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	public GetUnreadedMsgNumReq(Object invoker, IReturnCallback<GetUnreadedMsgResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetUnreadedMsgResult getResultObj() {
		return new GetUnreadedMsgResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("cid", companyId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetUnreadedMsgResult result, ResultItem item) {
		ResultItem itemResult = (ResultItem) item.get("DATA");
		if (itemResult != null) {
			result.totalSize = itemResult.getInt("total");
		}
	}

	@Override
	protected String getTypeURL() {
		return "/msg/newTotal";
	}

}
