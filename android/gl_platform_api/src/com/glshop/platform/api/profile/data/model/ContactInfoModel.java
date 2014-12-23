package com.glshop.platform.api.profile.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 企业联系人信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class ContactInfoModel extends ResultItem implements Cloneable {

	/**
	 * 联系人ID
	 */
	public String id;

	/**
	 * 联系人姓名
	 */
	public String name;

	/**
	 * 联系人手机
	 */
	public String telephone;

	/**
	 * 联系人固定电话
	 */
	public String fixPhone;

	/**
	 * 是否为默认联系人
	 */
	public boolean isDefault;

	@Override
	public Object clone() {
		ContactInfoModel o = null;
		try {
			o = (ContactInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ContactInfoModel[");
		sb.append("id=" + id);
		sb.append(", name=" + name);
		sb.append(", telephone=" + telephone);
		sb.append(", fixPhone=" + fixPhone);
		sb.append(", isDefault=" + isDefault);
		sb.append("]");
		return sb.toString();
	}

}
