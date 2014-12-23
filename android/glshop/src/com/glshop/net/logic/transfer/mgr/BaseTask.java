package com.glshop.net.logic.transfer.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : 任务基类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午8:02:34
 */
public abstract class BaseTask implements ITask {

	/** 任务ID生成器 */
	private static AtomicInteger taskIDs = new AtomicInteger(1);

	/** 任务ID */
	protected String taskId;

	/** 任务执行状态 */
	protected TaskStatus mTaskStatus;

	/** 任务执行结果 */
	protected ITaskResponse mTaskResponse;

	/** 回调接口列表 */
	protected List<ITaskCallback> mCallbackList = new ArrayList<ITaskCallback>();

	public BaseTask() {
		init();
	}

	@Override
	public void init() {
		taskId = String.valueOf(taskIDs.getAndIncrement());
	}

	@Override
	public String getTaskId() {
		return taskId;
	}

	@Override
	public boolean isFinished() {
		return mTaskStatus == TaskStatus.SUCCESS;
	}

	@Override
	public void addCallback(ITaskCallback callback) {
		if (!mCallbackList.contains(callback)) {
			mCallbackList.add(callback);
		}
	}

	@Override
	public ITaskResponse getTaskResponse() {
		return mTaskResponse;
	}

}
