package com.glshop.net.logic.transfer.mgr.file.download;

import com.glshop.net.logic.transfer.mgr.BaseTask;
import com.glshop.net.logic.transfer.mgr.ITask;
import com.glshop.net.logic.transfer.mgr.ITaskCallback;
import com.glshop.net.logic.transfer.mgr.ITaskCallback.TaskEvent;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.logic.transfer.mgr.file.FileInfo.FileStatus;
import com.glshop.platform.net.base.IRequestCallBack;
import com.glshop.platform.net.base.IResponseItem;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.net.http.image.DownloadRequest;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 文件下载任务
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午8:02:34
 */
public class FileDownloadTask extends BaseTask {

	private static final String TAG = "FileDownloadTask";

	/** 下载文件 */
	private FileInfo mFileInfo;

	/** 下载请求 */
	private DownloadRequest mRequest;

	public FileDownloadTask(FileInfo info) {
		super();
		mFileInfo = info;
	}

	@Override
	public void init() {
		super.init();
		mTaskResponse = new DownloadTaskResponse();
	}

	@Override
	public void exec() {
		if (!BeanUtils.isEmpty(mFileInfo)) {
			doDownload();
		} else {
			onResult(TaskEvent.ERROR, this);
		}
	}

	@Override
	public void cancel() {
		if (mRequest != null) {
			mRequest.cancel();
			onResult(TaskEvent.CANCEL, this);
		}
	}

	/**
	 * 下载操作
	 */
	private void doDownload() {
		if (mFileInfo != null) {
			mRequest = new DownloadRequest(mFileInfo.cloudUrl, mFileInfo.file.getAbsolutePath(), new IRequestCallBack() {

				@Override
				public void onResponseEvent(ResponseEvent event, IResponseItem response) {
					if (ResponseEvent.isFinish(event)) {
						if (event == ResponseEvent.SUCCESS) {
							mFileInfo.uploadStatus = FileStatus.SUCCESS;
							mTaskResponse.setResponseData(mFileInfo);
							onResult(TaskEvent.SUCCESS, FileDownloadTask.this);
						} else {
							mFileInfo.uploadStatus = FileStatus.ERROR;
							onResult(TaskEvent.ERROR, FileDownloadTask.this);
						}
					}
				}
			});
			mRequest.exec();
		}
	}

	/**
	 * 请求回调
	 * @param event
	 * @param task
	 */
	private void onResult(TaskEvent event, ITask task) {
		for (ITaskCallback callback : mCallbackList) {
			callback.onResult(event, task.getTaskResponse());
		}
	}

}
