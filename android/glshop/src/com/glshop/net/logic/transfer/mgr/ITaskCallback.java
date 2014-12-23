package com.glshop.net.logic.transfer.mgr;

/**
 * @Description : 传输接口回调
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午7:59:57
 */
public interface ITaskCallback {

	/**
	 * 任务回调
	 * @param event
	 * @param task
	 */
	public void onResult(TaskEvent event, ITaskResponse resp);

	/**
	 * 任务回调类型枚举
	 */
	public enum TaskEvent {

		START,

		PROGRESS,

		SUCCESS,

		ERROR,

		CANCEL;

		public static boolean isFinished(TaskEvent event) {
			return event == SUCCESS || event == ERROR || event == CANCEL;
		}

	}

}
