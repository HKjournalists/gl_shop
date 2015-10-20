package com.glshop.platform.api.pay.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 第三方交易号实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class TradeNoInfoModel extends ResultItem {

	/**
	 * 第三方交易询单号
	 */
	public String tn;

	/**
	 * 服务器端交易询单号
	 */
	public String oid;

	/**
	 * 询单时间
	 */
	public String datetime;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof TradeNoInfoModel) {
			TradeNoInfoModel other = (TradeNoInfoModel) o;
			if (this.tn == null || other.tn == null) {
				return false;
			} else {
				return this.tn.equals(other.tn);
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
		sb.append("TradeNoInfoModel[");
		sb.append("tn=" + tn);
		sb.append(", oid=" + oid);
		sb.append(", datetime=" + datetime);
		sb.append("]");
		return sb.toString();
	}

}
