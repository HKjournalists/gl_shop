package com.glshop.platform.api.syscfg.data.model;

import java.util.List;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 系统地域信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class AreaInfoModel extends ResultItem implements Cloneable {

	public String type;

	public String id;

	public String name;

	public String code;

	public String pCode;

	public String orderNo;

	public AreaInfoModel parent;

	public List<AreaInfoModel> childList;

	public boolean isSelectedForUI;
	public boolean isSelectedForDB;

	@Override
	public Object clone() {
		AreaInfoModel o = null;
		try {
			o = (AreaInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof AreaInfoModel) {
			AreaInfoModel other = (AreaInfoModel) o;
			if (this.code == null || other.code == null) {
				return false;
			} else {
				return this.code.equals(other.code);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AreaInfoModel[");
		sb.append("type=" + type);
		sb.append(", id=" + id);
		sb.append(", name=" + name);
		sb.append(", code=" + code);
		sb.append(", pCode=" + pCode);
		sb.append(", orderNo=" + orderNo);
		sb.append(", parent=" + (parent != null ? parent.name : null));
		sb.append(", isSelectedForUI=" + isSelectedForUI);
		sb.append(", isSelectedForDB=" + isSelectedForDB);
		sb.append("]");
		return sb.toString();
	}
}
