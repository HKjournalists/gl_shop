package com.glshop.net.logic.transfer.mgr;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-28 上午10:25:07
 */
public abstract class BaseTaskResponse implements ITaskResponse {

	/** 任务大小 */
	protected long mTaskSize = 0;

	/** 任务已完成大小 */
	protected long mCompleteSize = 0;

	public BaseTaskResponse() {

	}

	@Override
	public void setTaskSize(long size) {
		mTaskSize = size;
	}

	@Override
	public long getTaskSize() {
		return mTaskSize;
	}

	@Override
	public long getCompleteSize() {
		return mCompleteSize;
	}

}
