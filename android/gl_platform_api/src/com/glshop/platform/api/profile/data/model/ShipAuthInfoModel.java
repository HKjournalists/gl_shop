package com.glshop.platform.api.profile.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 船舶认证信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class ShipAuthInfoModel extends ResultItem implements Cloneable {

	/**
	 * 认证ID
	 */
	public String authId;

	/**
	 * 企业ID
	 */
	public String companyId;

	/**
	 * 船舶名称
	 */
	public String shipName;

	/**
	 * 船籍港
	 */
	public String shipPort;

	/**
	 * 船舶登记号
	 */
	public String shipNo;

	/**
	 * 船舶检验机构
	 */
	public String shipCheckOrg;

	/**
	 * 船舶所有人
	 */
	public String shipOwner;

	/**
	 * 船舶经营者
	 */
	public String shipOperator;

	/**
	 * 船舶种类
	 */
	public String shipType;

	/**
	 * 船舶建成日期
	 */
	public String shipCreateDate;

	/**
	 * 总吨位
	 */
	public String shipTotalAmount;

	/**
	 * 载重
	 */
	public String shipLoad;

	/**
	 * 船长
	 */
	public String shipLength;

	/**
	 * 船宽
	 */
	public String shipWidth;

	/**
	 * 型深
	 */
	public String shipHeight;

	/**
	 * 满载吃水
	 */
	public String shipTotalWaterHeight;

	/**
	 * 船体材料
	 */
	public String shipMaterial;

	@Override
	public Object clone() {
		ShipAuthInfoModel o = null;
		try {
			o = (ShipAuthInfoModel) super.clone();
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
		sb.append(", shipName=" + shipName);
		sb.append(", shipPort=" + shipPort);
		sb.append("]");
		return sb.toString();
	}

}
