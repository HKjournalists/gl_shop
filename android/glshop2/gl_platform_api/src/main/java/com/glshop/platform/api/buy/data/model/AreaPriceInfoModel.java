package com.glshop.platform.api.buy.data.model;

import java.io.Serializable;

import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;

/**
 * @Description : 区域价格信息，用于多地域发布
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-15 下午4:13:13
 */
public class AreaPriceInfoModel implements Serializable, Cloneable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = -4550072687791787597L;

	/**
	 * 地域信息
	 */
	public AreaInfoModel areaInfo;

	/**
	 * 地域单价
	 */
	public double unitPrice;

	@Override
	public Object clone() {
		AreaPriceInfoModel o = null;
		try {
			o = (AreaPriceInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AreaPriceInfoModel[");
		sb.append("areaInfo=" + areaInfo);
		sb.append(", unitPrice=" + unitPrice);
		sb.append("]");
		return sb.toString();
	}

}
