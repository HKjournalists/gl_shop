package com.glshop.platform.api.message;

import com.glshop.platform.api.DataConstants.MessageStatus;
import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.api.DataConstants.SystemMsgType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.message.data.GetMessageInfoResult;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 获取消息详情请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetMessageInfoReq extends BaseRequest<GetMessageInfoResult> {

	/**
	 * 消息ID
	 */
	public String id;

	public GetMessageInfoReq(Object invoker, IReturnCallback<GetMessageInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetMessageInfoResult getResultObj() {
		return new GetMessageInfoResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("msgid", id);
	}

	@Override
	protected void parseData(GetMessageInfoResult result, ResultItem item) {
		ResultItem msgItem = (ResultItem) item.get("DATA");
		if (msgItem != null) {
			MessageInfoModel info = new MessageInfoModel();
			info.id = msgItem.getString("id");
			info.content = msgItem.getString("content");
			info.dateTime = msgItem.getString("createtime");
			info.status = MessageStatus.convert(msgItem.getEnumValue("status"));
			info.type = SystemMsgType.convert(msgItem.getEnumValue("type"));
			info.businessType = MsgBusinessType.convert(msgItem.getEnumValue("businesstype"));
			info.businessID = msgItem.getString("businessid");
			result.data = info;
		}
	}

	@Override
	protected String getTypeURL() {
		return "/msg/getInfo";
	}

}
