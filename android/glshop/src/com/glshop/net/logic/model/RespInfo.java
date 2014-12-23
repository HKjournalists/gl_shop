package com.glshop.net.logic.model;

import java.io.Serializable;

import android.util.Pair;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.net.common.GlobalConstants.ReqSendType;

/**
 * @Description : Logic层返回请求结果信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 下午4:06:18
 */
public class RespInfo implements Serializable {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 863702193044529185L;

	/**
	 * 调用Invoker
	 */
	public Object invoker;

	/**
	 * 数据请求类型
	 */
	public DataReqType reqDataType;

	/**
	 * 数据请求方式
	 */
	public ReqSendType reqSendType;

	/**
	 * 返回数据
	 */
	public Object data;

	/**
	 * 错误编码
	 */
	public String errorCode;

	/**
	 * 错误详细信息
	 */
	public String errorMsg;

	/**
	 * Integer类型参数1
	 */
	public int intArg1;

	/**
	 * Integer类型参数2
	 */
	public int intArg2;

	/**
	 * String类型参数1
	 */
	public String strArg1;

	/**
	 * String类型参数2
	 */
	public String strArg2;

	/**
	 * 扩展数据列表
	 */
	public Pair<Object, Object> extendData;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RespInfo[");
		sb.append("invoker=" + invoker);
		sb.append(", data=" + data);
		sb.append(", errorCode=" + errorCode);
		sb.append(", errorMsg=" + errorMsg);
		sb.append(", intArg1=" + intArg1);
		sb.append(", intArg2=" + intArg2);
		sb.append(", strArg1=" + strArg1);
		sb.append(", strArg2=" + strArg2);
		sb.append(", extendData=" + extendData);
		sb.append("]");
		return sb.toString();
	}

}
