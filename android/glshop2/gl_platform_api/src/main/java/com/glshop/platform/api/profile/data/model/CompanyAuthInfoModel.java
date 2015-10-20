package com.glshop.platform.api.profile.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 企业认证信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class CompanyAuthInfoModel extends ResultItem implements Cloneable {

	/**
	 * 认证ID
	 */
	public String authId;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 公司名称
	 */
	public String companyName;

	/**
	 * 注册地址
	 */
	public String registeAddr;

	/**
	 * 成立时间
	 */
	public String registeDatetime;

	/**
	 * 注册号
	 */
	public String registeNo;

	/**
	 * 法定代表人姓名
	 */
	public String lawPeople;

	/**
	 * 登记机关
	 */
	public String registerOrg;

	/**
	 * 企业类型
	 */
	public String companyType;

	@Override
	public Object clone() {
		CompanyAuthInfoModel o = null;
		try {
			o = (CompanyAuthInfoModel) super.clone();
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
		sb.append(", companyName=" + companyName);
		sb.append(", registeAddr=" + registeAddr);
		sb.append(", registeDatetime=" + registeDatetime);
		sb.append(", registeNo=" + registeNo);
		sb.append(", lawPeople=" + lawPeople);
		sb.append(", registerOrg=" + registerOrg);
		sb.append("]");
		return sb.toString();
	}

}
