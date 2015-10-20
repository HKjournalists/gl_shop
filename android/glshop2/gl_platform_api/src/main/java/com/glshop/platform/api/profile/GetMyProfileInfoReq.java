package com.glshop.platform.api.profile;

import java.util.ArrayList;
import java.util.List;

import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.DataConstants.DepositStatusType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.DataConstants.SexType;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.BaseRequest;
import com.glshop.platform.api.profile.data.GetMyProfileInfoResult;
import com.glshop.platform.api.profile.data.model.AddrInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyAuthInfoModel;
import com.glshop.platform.api.profile.data.model.CompanyIntroInfoModel;
import com.glshop.platform.api.profile.data.model.ContactInfoModel;
import com.glshop.platform.api.profile.data.model.ImageInfoModel;
import com.glshop.platform.api.profile.data.model.MyProfileInfoModel;
import com.glshop.platform.api.profile.data.model.PersonalAuthInfoModel;
import com.glshop.platform.api.profile.data.model.ShipAuthInfoModel;
import com.glshop.platform.net.base.ResultItem;
import com.glshop.platform.net.http.ResponseDataType.HttpMethod;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 获取我的资料信息请求
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午11:21:49
 */
public class GetMyProfileInfoReq extends BaseRequest<GetMyProfileInfoResult> {

	/**
	 * 企业ID
	 */
	public String companyId;

	public GetMyProfileInfoReq(Object invoker, IReturnCallback<GetMyProfileInfoResult> callBackx) {
		super(invoker, callBackx);
	}

	@Override
	protected GetMyProfileInfoResult getResultObj() {
		return new GetMyProfileInfoResult();
	}

	@Override
	protected void buildParams() {
		request.setMethod(HttpMethod.GET);
		request.addParam("cid", companyId);
	}

	@Override
	protected void parseData(GetMyProfileInfoResult result, ResultItem item) {
		List<ResultItem> items = (List<ResultItem>) item.get("DATA");
		MyProfileInfoModel info = new MyProfileInfoModel();
		ResultItem itemProfile = items.get(0);
		info.companyId = itemProfile.getString("id");
		info.companyName = itemProfile.getString("cname");

		// 解析身份类型
		ResultItem profileTypeItem = (ResultItem) itemProfile.get("ctype");
		if (profileTypeItem != null) {
			info.profileType = ProfileType.convert(profileTypeItem.getInt("val"));
		}

		// 解析企业身份信息
		ResultItem companyInfoItem = (ResultItem) itemProfile.get("companyAuth");
		if (companyInfoItem != null) {
			CompanyAuthInfoModel authInfo = new CompanyAuthInfoModel();
			authInfo.companyId = info.companyId;
			authInfo.authId = companyInfoItem.getString("authid");
			authInfo.companyName = companyInfoItem.getString("cname");
			authInfo.registeAddr = companyInfoItem.getString("address");
			authInfo.registeDatetime = companyInfoItem.getString("rdate");
			authInfo.registeNo = companyInfoItem.getString("regno");
			authInfo.lawPeople = companyInfoItem.getString("lperson");
			authInfo.registerOrg = companyInfoItem.getString("orgid");
			authInfo.companyType = companyInfoItem.getString("ctype");
			info.companyAuthInfo = authInfo;
		}

		// 解析船舶身份信息
		ResultItem shipInfoItem = (ResultItem) itemProfile.get("shippingAuth");
		if (shipInfoItem != null) {
			ShipAuthInfoModel authInfo = new ShipAuthInfoModel();
			authInfo.companyId = info.companyId;
			authInfo.authId = shipInfoItem.getString("authid");
			authInfo.shipName = shipInfoItem.getString("sname");
			authInfo.shipPort = shipInfoItem.getString("pregistry");
			authInfo.shipNo = shipInfoItem.getString("sno");
			authInfo.shipCheckOrg = shipInfoItem.getString("sorg");
			authInfo.shipOwner = shipInfoItem.getString("sowner");
			authInfo.shipOperator = shipInfoItem.getString("sbusinesser");
			authInfo.shipType = shipInfoItem.getString("stype");
			authInfo.shipCreateDate = shipInfoItem.getString("screatetime");
			authInfo.shipTotalAmount = shipInfoItem.getString("stotal");
			authInfo.shipLoad = shipInfoItem.getString("sload");
			authInfo.shipLength = shipInfoItem.getString("slength");
			authInfo.shipWidth = shipInfoItem.getString("swidth");
			authInfo.shipHeight = shipInfoItem.getString("sdeep");
			authInfo.shipTotalWaterHeight = shipInfoItem.getString("sover");
			authInfo.shipMaterial = shipInfoItem.getString("smateriall");
			info.shipAuthInfo = authInfo;
		}

		// 解析个人身份信息
		ResultItem personalInfoItem = (ResultItem) itemProfile.get("personalAuth");
		if (personalInfoItem != null) {
			PersonalAuthInfoModel authInfo = new PersonalAuthInfoModel();
			authInfo.companyId = info.companyId;
			authInfo.authId = personalInfoItem.getString("authid");
			authInfo.personalName = personalInfoItem.getString("cpname");
			authInfo.setType = SexType.convert(personalInfoItem.getEnumValue("sex", SexType.MALE.toValue()));
			authInfo.birthDate = personalInfoItem.getString("birthday");
			authInfo.address = personalInfoItem.getString("origo");
			authInfo.idNo = personalInfoItem.getString("identification");
			authInfo.signOrg = personalInfoItem.getString("issuingauth");
			authInfo.limitStartRange = personalInfoItem.getString("effstarttime");
			authInfo.limitEndRange = personalInfoItem.getString("effendtime");
			info.personalAuthInfo = authInfo;
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
		List<ResultItem> authImgItems = (List<ResultItem>) itemProfile.get("authImgList");
		if (BeanUtils.isNotEmpty(authImgItems)) {
			List<ImageInfoModel> profileImgList = new ArrayList<ImageInfoModel>();
			for (ResultItem imageItem : authImgItems) {
				ImageInfoModel imgInfo = new ImageInfoModel();
				imgInfo.cloudId = imageItem.getString("id");
				imgInfo.cloudUrl = imageItem.getString("url");
				imgInfo.cloudThumbnailUrl = imageItem.getString("thumbnailSmall");
				profileImgList.add(imgInfo);
			}
			info.profileImgList = profileImgList;
		}

		// 解析默认联系人信息
		ContactInfoModel contact = new ContactInfoModel();
		contact.id = itemProfile.getString("contactId");
		contact.name = itemProfile.getString("contact");
		contact.telephone = itemProfile.getString("cphone");
		contact.fixPhone = itemProfile.getString("tel");

		// 解析默认卸货地址信息
		AddrInfoModel addr = new AddrInfoModel();
		addr.areaCode = itemProfile.getString("areacode");
		addr.areaName = itemProfile.getString("addrAreaFullName");
		addr.deliveryAddrDetail = itemProfile.getString("address");
		addr.uploadPortWaterDepth = itemProfile.getDouble("deep");
		addr.uploadPortShippingWaterDepth = itemProfile.getDouble("realdeep");
		addr.shippingTon = itemProfile.getDouble("shippington");

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

		info.defaultContact = contact;
		info.defaultAddr = addr;
		info.defaultCompanyIntro = intro;
		result.data = info;
	}

	@Override
	protected String getTypeURL() {
		return "/copn/getMyCompanyInfo";
	}

}
