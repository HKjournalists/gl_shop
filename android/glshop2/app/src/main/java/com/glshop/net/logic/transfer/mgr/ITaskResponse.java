package com.glshop.net.logic.transfer.mgr;

/**
 * @Description : 传输任务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午7:59:57
 */
public interface ITaskResponse {

	/** 设置任务大小 */
	public void setTaskSize(long size);

	/** 获取任务大小 */
	public long getTaskSize();

	/** 获取任务已完成大小 */
	public long getCompleteSize();

	/** 设置任务返回结果 */
	public <T> void setResponseData(T object);

	/** 获取任务返回结果 */
	public <T> T getResponseData();

}
