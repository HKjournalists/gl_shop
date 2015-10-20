package com.glshop.platform.api.profile;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 修改联系人信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class UpdateContactReq extends BaseRequest<CommonResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 企业联系人列表
	 */
	public List<ContactInfoModel> infoList;

	public UpdateContactReq(Object invoker, IReturnCallback<CommonResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected CommonResult getResultObj() {
		return new CommonResult();
	}

	@Override
	protected void buildParams() {
		JSONArray array = new JSONArray();
		for (ContactInfoModel contact : infoList) {
			JSONObject object = new JSONObject();
			try {
				object.put("id", contact.id);
				object.put("cname", contact.name);
				object.put("cphone", contact.telephone);
				object.put("tel", contact.fixPhone);
				object.put("status", contact.isDefault ? 1 : 0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(object);
		}
		request.addParam("contactList", array.toString());
		request.addParam("cid", companyId);
	}

	@Override
	protected void parseData(CommonResult result, ResultItem item) {

	}

	@Override
	protected String getTypeURL() {
		return "/copn/contact/save";
	}

}
