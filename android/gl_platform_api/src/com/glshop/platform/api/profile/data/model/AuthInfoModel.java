package com.glshop.platform.api.profile.data.model;

import java.util.List;

import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 认证信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class AuthInfoModel extends ResultItem {

	/**
	 * 认证身份类型
	 */
	public ProfileType profileType = ProfileType.COMPANY;

	/**
	 * 认证图片信息
	 */
	public ImageInfoModel authImgInfo;

	/**
	 * 默认联系人信息
	 */
	public ContactInfoModel contactInfo;

	/**
	 * 默认卸货地址信息
	 */
	public AddrInfoModel addrInfo;

	/**
	 * 企业介绍信息
	 */
	public CompanyIntroInfoModel introInfo;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AuthInfoModel[");
		sb.append(", profileType=" + profileType.toValue());
		sb.append(", authImgInfo=" + authImgInfo);
		sb.append(", contactInfo=" + contactInfo);
		sb.append(", addrInfo=" + addrInfo);
		sb.append(", introInfo=" + introInfo);
		sb.append("]");
		return sb.toString();
	}
}
