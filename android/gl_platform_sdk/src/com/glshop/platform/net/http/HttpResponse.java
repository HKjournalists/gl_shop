package com.glshop.platform.net.http;

import java.util.List;

import com.glshop.platform.net.base.BaseResponseItem;

/**
 * FileName    : HttpResponse.java
 * Description : http的数据响应对象
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-9 下午1:04:34
 **/
public class HttpResponse extends BaseResponseItem {

	public HttpResponse() {
		super();
	}

	public HttpResponse(String error, String errorMessage) {
		super();
		setError(error, errorMessage);
	}

	public HttpResponse(String errorMessage) {
		setError("", errorMessage);
	}

	@Override
	public <T> T getResultItem(Class<T> T) {
		return (T) resultData;
	}

	@Override
	public <T> List<T> getResultArray(Class<T> T) {
		return null;
	}
}
