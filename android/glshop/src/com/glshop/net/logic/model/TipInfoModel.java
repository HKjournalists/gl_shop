package com.glshop.net.logic.model;

import java.io.Serializable;

import android.os.Bundle;

import com.glshop.net.common.GlobalConstants.TipActionBackType;

/**
 * @Description : 操作提示信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 下午4:06:18
 */
public class TipInfoModel implements Serializable {

	private static final long serialVersionUID = -2531118186258508950L;

	/**
	 * 操作提示标题
	 */
	public String operatorTipTitle;

	/**
	 * 操作提示类型标题
	 */
	public String operatorTipTypeTitle;

	/**
	 * 操作提示内容
	 */
	public String operatorTipContent;

	/**
	 * 操作提示Action1文本
	 */
	public String operatorTipActionText1;

	/**
	 * 操作提示Action2文本
	 */
	public String operatorTipActionText2;

	/**
	 * 操作提示Action1
	 */
	public String operatorTipAction1;

	/**
	 * 操作提示Action Class 1
	 */
	public Class<?> operatorTipActionClass1;

	/**
	 * 操作提示Action2
	 */
	public String operatorTipAction2;

	/**
	 * 操作提示Action Class 2
	 */
	public Class<?> operatorTipActionClass2;

	/**
	 * 操作提示Warning
	 */
	public String operatorTipWarning;

	/**
	 * 操作结果页面Back类型
	 */
	public TipActionBackType backType = TipActionBackType.FINISH;

	/**
	 * 操作结果Tag2
	 */
	public Bundle actionData1;

	/**
	 * 操作结果Tag1
	 */
	public Bundle actionData2;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TipInfo[");
		sb.append("operatorTipTitle=" + operatorTipTitle);
		sb.append(", operatorTipTypeTitle=" + operatorTipTypeTitle);
		sb.append(", operatorTipContent=" + operatorTipContent);
		sb.append(", operatorTipActionText1=" + operatorTipActionText1);
		sb.append(", operatorTipActionText2=" + operatorTipActionText2);
		sb.append(", operatorTipAction1=" + operatorTipAction1);
		sb.append(", operatorTipActionClass1=" + operatorTipActionClass1 == null ? null : operatorTipActionClass1.getName());
		sb.append(", operatorTipAction2=" + operatorTipAction2);
		sb.append(", operatorTipActionClass2=" + operatorTipActionClass2 == null ? null : operatorTipActionClass2.getName());
		sb.append(", operatorTipWarning=" + operatorTipWarning);
		sb.append("]");
		return sb.toString();
	}
}
