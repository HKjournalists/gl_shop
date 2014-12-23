package com.glshop.net.logic.transfer.mgr;

/**
 * @Description : 传输任务接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午7:59:57
 */
public interface ITask {

	/**
	 * 任务初始化
	 */
	public void init();

	/**
	 * 任务执行
	 */
	public void exec();

	/**
	 * 任务取消
	 */
	public void cancel();

	/**
	 * 获取任务ID
	 */
	public String getTaskId();

	/**
	 * 添加任务回调接口
	 */
	public void addCallback(ITaskCallback callback);

	/**
	 * 任务是否结束
	 */
	public boolean isFinished();

	/**
	 * 获取任务执行结果
	 */
	public ITaskResponse getTaskResponse();

	/**
	 * 任务执行状态枚举
	 */
	public enum TaskStatus {

		NONE,

		RUNNING,

		SUCCESS,

		ERROR,

		CANCELED

	}

}
