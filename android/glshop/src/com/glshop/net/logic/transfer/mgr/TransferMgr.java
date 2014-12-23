package com.glshop.net.logic.transfer.mgr;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : 传输管理类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午3:40:32
 */
public class TransferMgr {

	private static final String TAG = "TransferMgr";

	private static TransferMgr mInstance;

	/** 正在执行中的任务列表 */
	private ConcurrentHashMap<String, ITask> tasks = new ConcurrentHashMap<String, ITask>();

	private TransferMgr() {

	}

	public static synchronized TransferMgr getInstance() {
		if (mInstance == null) {
			mInstance = new TransferMgr();
		}
		return mInstance;
	}

	/**
	 * 添加任务
	 * @param task
	 */
	public void addTask(final ITask task) {
		if (!tasks.contains(task.getTaskId())) {
			task.addCallback(new ITaskCallback() {

				@Override
				public void onResult(TaskEvent event, ITaskResponse resp) {
					if (TaskEvent.isFinished(event)) {
						tasks.remove(task.getTaskId());
					}
				}
			});
			tasks.put(task.getTaskId(), task);
			task.exec();
		}
	}

	/**
	 * 取消任务
	 * @param taskId
	 */
	public void cancelTask(String taskId) {
		if (tasks.contains(taskId)) {
			ITask task = tasks.get(taskId);
			task.cancel();
			tasks.remove(taskId);
		}
	}

}
