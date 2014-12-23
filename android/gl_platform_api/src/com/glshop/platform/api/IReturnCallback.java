package com.glshop.platform.api;

import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;

/**
 * FileName    : IReturnCallback.java
 * Description : 业务层的数据返回
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-14 下午3:03:59
 **/
public interface IReturnCallback<T extends CommonResult> {

	public void onReturn(Object invoker, ResponseEvent event, T result);
}
