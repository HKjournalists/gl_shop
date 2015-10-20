package com.glshop.platform.api.profile.data.model;

import com.glshop.platform.api.DataConstants.SexType;
import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 个人认证信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class PersonalAuthInfoModel extends ResultItem implements Cloneable {

	/**
	 * 认证ID
	 */
	public String authId;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 姓名
	 */
	public String personalName;

	/**
	 * 性别
	 */
	public SexType setType = SexType.MALE;

	/**
	 * 出生日期
	 */
	public String birthDate;

	/**
	 * 住址
	 */
	public String address;

	/**
	 * 身份证号
	 */
	public String idNo;

	/**
	 * 签发机关
	 */
	public String signOrg;

	/**
	 * 起始有效期限
	 */
	public String limitStartRange;

	/**
	 * 结束有效期限
	 */
	public String limitEndRange;

	@Override
	public Object clone() {
		PersonalAuthInfoModel o = null;
		try {
			o = (PersonalAuthInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CompanyAuthInfoModel[");
		sb.append("authId=" + authId);
		sb.append(", companyId=" + companyId);
		sb.append(", personalName=" + personalName);
		sb.append(", personalSex=" + setType.toValue());
		sb.append(", birthDate=" + birthDate);
		sb.append(", address=" + address);
		sb.append(", idNo=" + idNo);
		sb.append(", signOrg=" + signOrg);
		sb.append(", limitStartRange=" + limitStartRange);
		sb.append(", limitEndRange=" + limitEndRange);
		sb.append("]");
		return sb.toString();
	}

}
