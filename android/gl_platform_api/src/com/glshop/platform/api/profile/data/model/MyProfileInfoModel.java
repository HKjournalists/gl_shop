package com.glshop.platform.api.profile.data.model;

import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.DataConstants.DepositStatusType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 我的资料信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class MyProfileInfoModel extends ResultItem {

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 企业名称
	 */
	public String companyName;

	/**
	 * 身份状态类型
	 */
	public ProfileType profileType;

	/**
	 * 认证状态类型
	 */
	public AuthStatusType authStatusType = AuthStatusType.UN_AUTH;

	/**
	 * 保证金状态类型
	 */
	public DepositStatusType depositStatusType = DepositStatusType.UN_RECHARGE;

	/**
	 * 认证图片信息
	 */
	public ImageInfoModel authImgInfo;

	/**
	 * 企业默认联系人信息
	 */
	public ContactInfoModel defaultContact;

	/**
	 * 企业默认卸货地址信息
	 */
	public AddrInfoModel defaultAddr;

	/**
	 * 企业介绍信息
	 */
	public CompanyIntroInfoModel defaultCompanyIntro;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MyProfileInfoModel[");
		sb.append("companyId=" + companyId);
		sb.append(", companyName=" + companyName);
		//sb.append(", profileType=" + profileType.toValue());
		sb.append(", authStatusType=" + authStatusType.toValue());
		sb.append(", depositStatusType=" + depositStatusType.toValue());
		sb.append(", authImgInfo=" + authImgInfo);
		sb.append(", defaultContact=" + defaultContact);
		sb.append(", defaultAddr=" + defaultAddr);
		sb.append(", defaultCompanyIntro=" + defaultCompanyIntro);
		sb.append("]");
		return sb.toString();
	}
}
