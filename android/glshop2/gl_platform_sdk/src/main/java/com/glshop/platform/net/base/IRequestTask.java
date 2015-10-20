package com.glshop.platform.net.base;



/**
 * FileName    : IRequestTask.java
 * Description : 真正做请求的任务
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-25 下午5:54:05
 **/
public interface IRequestTask {
	
	/**
	 * 执行请求
	 * @param request
	 */
	public void init(IRequestItem request);
	
	/**
	 * 执行
	 */
	public void exec();
	
	/**
	 * 取消
	 */
	public void cancel();
}
