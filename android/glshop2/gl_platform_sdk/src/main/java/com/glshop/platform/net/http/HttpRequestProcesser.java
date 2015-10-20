package com.glshop.platform.net.http;

import com.glshop.platform.net.base.BaseRequestProcesser;
import com.glshop.platform.net.base.IRequestTask;

/**
 * FileName    : HttpRequestHelper.java
 * Description : 
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午6:06:36
 **/
public class HttpRequestProcesser extends BaseRequestProcesser {

	@Override
	public IRequestTask buildTask() {
		return new HttpRequestTask();
	}

}
