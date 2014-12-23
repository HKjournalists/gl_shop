package com.glshop.net.logic.transfer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.logic.transfer.mgr.ITask;
import com.glshop.net.logic.transfer.mgr.ITaskCallback;
import com.glshop.net.logic.transfer.mgr.ITaskResponse;
import com.glshop.net.logic.transfer.mgr.TransferMgr;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.net.logic.transfer.mgr.file.download.FileDownloadTask;
import com.glshop.net.logic.transfer.mgr.file.upload.FileUploadTask;
import com.glshop.platform.utils.Logger;

/**
 * @Description : 传输管理业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午3:40:32
 */
public class TransferLogic extends BasicLogic implements ITransferLogic {

	public TransferLogic(Context context) {
		super(context);
	}

	@Override
	public String uploadFile(String invoker, FileInfo info) {
		List<FileInfo> list = new ArrayList<FileInfo>();
		list.add(info);
		return uploadFile(invoker, list);
	}

	@Override
	public String uploadFile(final String invoker, final List<FileInfo> infos) {
		ITask task = new FileUploadTask(infos);
		task.addCallback(new ITaskCallback() {

			@Override
			public void onResult(TaskEvent event, ITaskResponse resp) {
				if (TaskEvent.isFinished(event)) {
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(invoker, null);
					message.obj = respInfo;
					if (event == TaskEvent.SUCCESS) {
						message.what = GlobalMessageType.CommonMessageType.MSG_FILE_UPLOAD_SUCCESS;
						ArrayList<FileInfo> respData = resp.getResponseData();
						Logger.e(TAG, "UploadResult = " + respData);
						respInfo.data = respData;
					} else {
						message.what = GlobalMessageType.CommonMessageType.MSG_FILE_UPLOAD_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		TransferMgr.getInstance().addTask(task);
		return task.getTaskId();
	}

	@Override
	public String downloadFile(final String invoker, FileInfo file) {
		ITask task = new FileDownloadTask(file);
		task.addCallback(new ITaskCallback() {

			@Override
			public void onResult(TaskEvent event, ITaskResponse resp) {
				if (TaskEvent.isFinished(event)) {
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(invoker, null);
					message.obj = respInfo;
					if (event == TaskEvent.SUCCESS) {
						message.what = GlobalMessageType.CommonMessageType.MSG_FILE_DOWNLOAD_SUCCESS;
						FileInfo respData = resp.getResponseData();
						Logger.e(TAG, "DownloadResult = " + respData);
						respInfo.data = respData;
					} else {
						message.what = GlobalMessageType.CommonMessageType.MSG_FILE_DOWNLOAD_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		TransferMgr.getInstance().addTask(task);
		return task.getTaskId();
	}

	@Override
	public void cancelFileUpload(String taskId) {
		TransferMgr.getInstance().cancelTask(taskId);
	}

}
