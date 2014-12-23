package com.glshop.platform.net.base;

import com.glshop.platform.net.RequestDispatcher;

/**
 * FileName    : BaseRequestItem.java
 * Description : 网络请求项的基类
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 下午2:30:10
 **/
public abstract class BaseRequestItem implements IRequestItem {

	/**请求的状态*/
	private RequestStatus status = RequestStatus.NONE;
	/**请求的编号*/
	protected String requestId;
	/**请求对应的callback*/
	protected IRequestCallBack callBack;

	public BaseRequestItem(IRequestCallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	public String getRequestId() {
		return requestId;
	}

	@Override
	public RequestStatus getStatus() {
		return status;
	}

	@Override
	public void setRequestStatus(RequestStatus status) {
		this.status = status;
	}

	@Override
	public IRequestCallBack getCallback() {
		return callBack;
	}

	@Override
	public void send() {
		RequestDispatcher.getInstance().addRequest(this);
	}

	@Override
	public void cancel() {
		RequestDispatcher.getInstance().cancelRequest(this);
	}

}
