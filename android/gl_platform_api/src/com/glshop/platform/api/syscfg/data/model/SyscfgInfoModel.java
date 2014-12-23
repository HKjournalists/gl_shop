package com.glshop.platform.api.syscfg.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 系统配置信息详情
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class SyscfgInfoModel extends ResultItem {

	/**
	 * 参数类型
	 */
	public String type;

	/**
	 * 参数编码
	 */
	public String code;

	/**
	 * 参数值
	 */
	public String value;

	/**
	 * 参数父编码
	 */
	public String pCode;
	
	/**
	 * 排序编号
	 */
	public String orderNo;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SyscfgInfoModel[");
		sb.append("type=" + type);
		sb.append(", code=" + code);
		sb.append(", value=" + value);
		sb.append(", pCode=" + pCode);
		sb.append(", orderNo=" + orderNo);
		sb.append("]");
		return sb.toString();
	}

}
