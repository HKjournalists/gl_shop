package com.glshop.net.logic.transfer.mgr.file.upload;

import java.util.List;

import com.glshop.net.logic.transfer.mgr.BaseTask;
import com.glshop.net.logic.transfer.mgr.ITask;
import com.glshop.net.logic.transfer.mgr.ITaskCallback;
import com.glshop.net.logic.transfer.mgr.ITaskCallback.TaskEvent;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.logic.transfer.mgr.file.FileInfo.FileStatus;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.file.UploadFileReq;
import com.glshop.platform.api.file.data.UploadFileResult;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.BeanUtils;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 文件上传任务
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午8:02:34
 */
public class FileUploadTask extends BaseTask {

	private static final String TAG = "FileUploadTask";

	/** 上传文件列表 */
	private List<FileInfo> mFileList;

	/** 上传请求 */
	private UploadFileReq mRequest;

	public FileUploadTask(List<FileInfo> list) {
		super();
		mFileList = list;
	}

	@Override
	public void init() {
		super.init();
		mTaskResponse = new UploadTaskResponse();
	}

	@Override
	public void exec() {
		if (BeanUtils.isNotEmpty(mFileList)) {
			doUpload();
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
	 * 上传操作
	 */
	private void doUpload() {
		FileInfo fileInfo = getNextUploadFile();
		if (fileInfo != null) {
			mRequest = new UploadFileReq(fileInfo, new IReturnCallback<UploadFileResult>() {

				@Override
				public void onReturn(Object invoker, ResponseEvent event, UploadFileResult result) {
					if (ResponseEvent.isFinish(event)) {
						FileInfo fileInfo = (FileInfo) invoker;
						if (result.isSuccess()) {
							fileInfo.uploadStatus = FileStatus.SUCCESS;
							fileInfo.cloudId = result.cloudId;
							fileInfo.cloudUrl = result.cloudFilePath;
							fileInfo.cloudThumbnailUrl = result.cloudFileThumbnailPath;
							doUpload();
						} else {
							fileInfo.uploadStatus = FileStatus.ERROR;
							onResult(TaskEvent.ERROR, FileUploadTask.this);
						}
					}
				}
			});
			mRequest.localPath = fileInfo.file.getAbsolutePath();
			mRequest.exec();
		} else {
			if (isTaskSuccess()) {
				mTaskResponse.setResponseData(mFileList);
				onResult(TaskEvent.SUCCESS, this);
			} else {
				onResult(TaskEvent.ERROR, this);
			}
		}
	}

	/**
	 * 获取下一个待上传的文件信息
	 * @return
	 */
	private FileInfo getNextUploadFile() {
		FileInfo file = null;
		Logger.e(TAG, "FileSize = " + mFileList.size());
		for (int i = 0; i < mFileList.size(); i++) {
			if (mFileList.get(i).uploadStatus == FileStatus.NONE) {
				file = mFileList.get(i);
				break;
			}
		}
		return file;
	}

	/**
	 * 当前任务是否执行成功
	 * @return
	 */
	private boolean isTaskSuccess() {
		for (FileInfo file : mFileList) {
			if (file.uploadStatus != FileStatus.SUCCESS) {
				return false;
			}
		}
		return true;
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
