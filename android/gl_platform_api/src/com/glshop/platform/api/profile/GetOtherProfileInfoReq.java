package com.glshop.platform.api.profile;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.DataConstants.DepositStatusType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.GetOtherProfileInfoResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.profile.data.model.OtherProfileInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取对方的资料信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetOtherProfileInfoReq extends BaseRequest<GetOtherProfileInfoResult> {

	public String companyId;

	public GetOtherProfileInfoReq(Object invoker, IReturnCallback<GetOtherProfileInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetOtherProfileInfoResult getResultObj() {
		return new GetOtherProfileInfoResult();
	}

	@Override
	protected void buildParams() {
		request.setMethod(HttpMethod.GET);
		request.addParam("cid", companyId);
	}

	@Override
	protected void parseData(GetOtherProfileInfoResult result, ResultItem item) {
		List<ResultItem> items = (List<ResultItem>) item.get("DATA");
		OtherProfileInfoModel info = new OtherProfileInfoModel();
		ResultItem itemProfile = items.get(0);
		info.companyId = itemProfile.getString("id");
		info.companyName = itemProfile.getString("cname");

		// 解析身份类型
		ResultItem profileTypeItem = (ResultItem) itemProfile.get("ctype");
		if (profileTypeItem != null) {
			info.profileType = ProfileType.convert(profileTypeItem.getInt("val"));
		}

		// 解析认证状态
		ResultItem statusItem = (ResultItem) itemProfile.get("authstatus");
		if (statusItem != null) {
			info.authStatusType = AuthStatusType.convert(statusItem.getInt("val"));
		}

		// 保证金状态
		ResultItem depositStatusItem = (ResultItem) itemProfile.get("bailstatus");
		if (depositStatusItem != null) {
			info.depositStatusType = DepositStatusType.convert(depositStatusItem.getInt("val"));
		}

		// 解析认证图片信息
		ImageInfoModel authImgInfo = new ImageInfoModel();
		List<ResultItem> authImgItems = (List<ResultItem>) itemProfile.get("authImgList");
		if (BeanUtils.isNotEmpty(authImgItems)) {
			ResultItem authImgItem = authImgItems.get(0);
			authImgInfo.cloudId = authImgItem.getString("id");
			authImgInfo.cloudUrl = authImgItem.getString("url");
			authImgInfo.cloudThumbnailUrl = authImgItem.getString("thumbnailSmall");
		}
		info.authImgInfo = authImgInfo;

		// 解析默认联系人信息
		ContactInfoModel contact = new ContactInfoModel();
		contact.id = itemProfile.getString("contactId");
		contact.name = itemProfile.getString("contact");
		contact.telephone = itemProfile.getString("cphone");
		contact.fixPhone = itemProfile.getString("tel");

		// 解析默认卸货地址信息
		AddrInfoModel addr = new AddrInfoModel();
		addr.deliveryAddrDetail = itemProfile.getString("address");
		addr.uploadPortWaterDepth = itemProfile.getFloat("deep");
		addr.uploadPortShippingWaterDepth = itemProfile.getFloat("realdeep");

		// 解析卸货地址图片信息
		List<ImageInfoModel> imageList = new ArrayList<ImageInfoModel>();
		List<ResultItem> addrImageItems = (ArrayList<ResultItem>) itemProfile.get("addressImgList");
		if (addrImageItems != null) {
			for (ResultItem imageItem : addrImageItems) {
				ImageInfoModel image = new ImageInfoModel();
				image.cloudId = imageItem.getString("id");
				image.cloudUrl = imageItem.getString("url");
				image.cloudThumbnailUrl = imageItem.getString("thumbnailSmall");
				imageList.add(image);
			}
		}
		addr.addrImageList = imageList;

		// 解析企业介绍信息
		CompanyIntroInfoModel intro = new CompanyIntroInfoModel();
		intro.companyId = itemProfile.getString("id");
		intro.introduction = itemProfile.getString("mark");
		// 解析企业介绍图片信息
		List<ImageInfoModel> companyImageList = new ArrayList<ImageInfoModel>();
		List<ResultItem> companyImageItems = (ArrayList<ResultItem>) itemProfile.get("companyImgList");
		if (companyImageItems != null) {
			for (ResultItem imageItem : companyImageItems) {
				ImageInfoModel image = new ImageInfoModel();
				image.cloudId = imageItem.getString("id");
				image.cloudUrl = imageItem.getString("url");
				image.cloudThumbnailUrl = imageItem.getString("thumbnailSmall");
				companyImageList.add(image);
			}
		}
		intro.imgList = companyImageList;

		// 企业评价信息
		ResultItem evaItem = (ResultItem) itemProfile.get("evaluationInfo");
		if (evaItem != null) {
			info.tradeNumber = evaItem.getInt("transactionSuccessNum");
			info.turnoverRate = evaItem.getFloat("transactionSuccessRate");
			info.satisfactionPer = (int) evaItem.getFloat("averageEvaluation");
			info.sincerityPer = (int) evaItem.getFloat("averageCredit");
		}

		info.defaultContact = contact;
		info.defaultAddr = addr;
		info.defaultCompanyIntro = intro;
		result.data = info;
	}

	@Override
	protected String getTypeURL() {
		return "/copn/getCompanyInfo";
	}

}
