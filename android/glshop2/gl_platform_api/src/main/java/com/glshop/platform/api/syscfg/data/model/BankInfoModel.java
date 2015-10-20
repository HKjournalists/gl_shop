package com.glshop.platform.api.syscfg.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 系统银行信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class BankInfoModel extends ResultItem implements Cloneable {

	public String type;

	public String name;

	public String code;

	public String pCode;

	public String orderNo;

	@Override
	public Object clone() {
		BankInfoModel o = null;
		try {
			o = (BankInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BankInfoModel[");
		sb.append("type=" + type);
		sb.append(", name=" + name);
		sb.append(", code=" + code);
		sb.append(", pCode=" + pCode);
		sb.append(", orderNo=" + orderNo);
		sb.append("]");
		return sb.toString();
	}

}
