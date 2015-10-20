package com.glshop.platform.net.base;

import com.glshop.platform.net.base.ProtocolType.ResponseEvent;

/**
 * FileName    : IRequestCallBack.java
 * Description : 对外统一的callback回调
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午4:56:38
 **/
public interface IRequestCallBack {

	/**
	 * 事件回调
	 * @param event
	 * @param response
	 */
	public void onResponseEvent(ResponseEvent event, IResponseItem response);
}
