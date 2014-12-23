package com.glshop.platform.api.purse;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.PayeeStatus;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.purse.data.GetPayeeListResult;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取收款人列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetPayeeListReq extends BaseRequest<GetPayeeListResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 审核状态
	 */
	public PayeeStatus status;

	public GetPayeeListReq(Object invoker, IReturnCallback<GetPayeeListResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetPayeeListResult getResultObj() {
		return new GetPayeeListResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("cid", companyId);
		if (status != null) {
			request.addParam("authStatus", status.toValue());
		}
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetPayeeListResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<PayeeInfoModel> itemList = new ArrayList<PayeeInfoModel>();
		for (ResultItem payeeItem : items) {
			PayeeInfoModel info = new PayeeInfoModel();
			info.id = payeeItem.getString("id");
			info.status = PayeeStatus.convert(payeeItem.getEnumValue("authstatus"));
			info.companyId = payeeItem.getString("company|id");
			info.name = payeeItem.getString("carduser");
			info.bankCode = payeeItem.getString("banktype");
			info.subBank = payeeItem.getString("bankname");
			info.card = payeeItem.getString("bankcard");

			List<ResultItem> imgItems = (ArrayList<ResultItem>) payeeItem.get("vImgList");
			if (BeanUtils.isNotEmpty(imgItems)) {
				ResultItem imgItem = imgItems.get(0);
				ImageInfoModel image = new ImageInfoModel();
				image.cloudId = imgItem.getString("id");
				image.cloudUrl = imgItem.getString("url");
				image.cloudThumbnailUrl = imgItem.getString("thumbnailSmall");
				info.certImgInfo = image;
			}

			info.isDefault = 1 == payeeItem.getEnumValue("status");
			itemList.add(info);
		}
		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/copn/accept/getList";
	}

}
