package com.glshop.platform.api.profile;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.GetContactListResult;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取联系人列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetContactListReq extends BaseRequest<GetContactListResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	public GetContactListReq(Object invoker, IReturnCallback<GetContactListResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetContactListResult getResultObj() {
		return new GetContactListResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("cid", companyId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetContactListResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<ContactInfoModel> itemList = new ArrayList<ContactInfoModel>();
		if (BeanUtils.isNotEmpty(items)) {
			ResultItem contactItem = items.get(0);
			ContactInfoModel info = new ContactInfoModel();
			info.id = contactItem.getString("id");
			info.name = contactItem.getString("cname");
			info.telephone = contactItem.getString("cphone");
			info.fixPhone = contactItem.getString("tel");
			info.isDefault = "1".equals(contactItem.getString("status"));
			itemList.add(info);
		}
		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/copn/contact/getList";
	}

}
