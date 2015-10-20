package com.glshop.platform.net.http;

import com.glshop.platform.net.base.IRequestItem;

/**
 * FileName    : IRequestCallBack.java
 * Description : 网络请求的callBack
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-8 上午10:49:51
 **/
public interface IHttpRequestCallBack {
	/**
	 * 任务开始运行
	 * @param item
	 */
	public void onStart(IRequestItem item);

	/**
	 * 任务上报进度
	 * @param item
	 */
	public void onProgress(IRequestItem item);

	/**
	 * 任务出错
	 * @param item
	 */
	public void onError(IRequestItem item);

	/**
	 * 任务完成
	 * @param item
	 */
	public void onFinish(IRequestItem item);

	/**
	 * 任务被取消
	 * @param item
	 */
	public void onCancel(IRequestItem item);
}
