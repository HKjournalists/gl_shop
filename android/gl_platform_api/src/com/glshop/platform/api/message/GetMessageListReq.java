package com.glshop.platform.api.message;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.MessageStatus;
import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.api.DataConstants.SystemMsgType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.message.data.GetMessageListResult;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取消息列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetMessageListReq extends BaseRequest<GetMessageListResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 已读状态
	 */
	public MessageStatus status;

	/**
	 * 当前页数
	 */
	public int pageIndex;

	/**
	 * 每页条数
	 */
	public int pageSize;

	public GetMessageListReq(Object invoker, IReturnCallback<GetMessageListResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetMessageListResult getResultObj() {
		return new GetMessageListResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("cid", companyId);
		request.addParam("pageIndex", pageIndex);
		request.addParam("pageSize", pageSize);
		if (status != null) {
			request.addParam("status", status.toValue());
		}
	}

	@Override
	protected void parseData(GetMessageListResult result, ResultItem item) {
		ResultItem dataItem = (ResultItem) item.get("DATA");
		List<ResultItem> items = (ArrayList<ResultItem>) dataItem.get("result");
		List<MessageInfoModel> itemList = new ArrayList<MessageInfoModel>();
		if (BeanUtils.isNotEmpty(items)) {
			for (ResultItem msgItem : items) {
				MessageInfoModel info = new MessageInfoModel();
				info.id = msgItem.getString("id");
				info.content = msgItem.getString("content");
				info.dateTime = msgItem.getString("createtime");
				info.status = MessageStatus.convert(msgItem.getEnumValue("status"));
				info.type = SystemMsgType.convert(msgItem.getEnumValue("type"));
				info.businessType = MsgBusinessType.convert(msgItem.getEnumValue("businesstype"));
				info.businessID = msgItem.getString("businessid");
				itemList.add(info);
			}
		}

		// 未读消息数目
		ResultItem unreadItem = (ResultItem) dataItem.get("resultParam");
		if (unreadItem != null) {
			result.unreadTotalSize = unreadItem.getInt("unreadTotalSize");
		}

		result.totalSize = dataItem.getInt("totalSize");

		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/msg/getList";
	}

}
