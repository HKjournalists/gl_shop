package com.glshop.platform.api.profile;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.GetAddrListResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;

/**
 * @Description : 获取卸货地址列表请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetAddrListReq extends BaseRequest<GetAddrListResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	public GetAddrListReq(Object invoker, IReturnCallback<GetAddrListResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetAddrListResult getResultObj() {
		return new GetAddrListResult();
	}

	@Override
	protected void buildParams() {
		request.addParam("cid", companyId);
		request.setMethod(HttpMethod.GET);
	}

	@Override
	protected void parseData(GetAddrListResult result, ResultItem item) {
		List<ResultItem> items = (ArrayList<ResultItem>) item.get("DATA");
		List<AddrInfoModel> itemList = new ArrayList<AddrInfoModel>();
		for (ResultItem addrItem : items) {
			AddrInfoModel info = new AddrInfoModel();
			info.addrId = addrItem.getString("id");
			info.companyId = addrItem.getString("cid");
			info.deliveryAddrDetail = addrItem.getString("address");
			info.uploadPortWaterDepth = addrItem.getFloat("deep");
			info.uploadPortShippingWaterDepth = addrItem.getFloat("realdeep");
			info.isDefaultAddr = "1".equals(addrItem.getString("status|val"));

			// 解析图片信息
			List<ImageInfoModel> imageList = new ArrayList<ImageInfoModel>();
			List<ResultItem> imageItems = (ArrayList<ResultItem>) addrItem.get("vImgList");
			if (imageItems != null) {
				for (ResultItem imageItem : imageItems) {
					ImageInfoModel image = new ImageInfoModel();
					image.cloudId = imageItem.getString("id");
					image.cloudUrl = imageItem.getString("url");
					image.cloudThumbnailUrl = imageItem.getString("thumbnailSmall");
					imageList.add(image);
				}
			}
			info.addrImageList = imageList;

			itemList.add(info);
		}
		result.datas = itemList;
	}

	@Override
	protected String getTypeURL() {
		return "/copn/address/getList";
	}

}
