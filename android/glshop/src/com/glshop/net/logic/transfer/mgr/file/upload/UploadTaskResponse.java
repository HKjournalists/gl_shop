package com.glshop.net.logic.transfer.mgr.file.upload;

import java.util.List;

import com.glshop.net.logic.transfer.mgr.BaseTaskResponse;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;

/**
 * @Description : 上传返回结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-28 上午10:25:07
 */
public class UploadTaskResponse extends BaseTaskResponse {

	/**
	 * 上传云端文件信息
	 */
	private List<FileInfo> mFileList;

	public UploadTaskResponse() {

	}

	@Override
	public <T> void setResponseData(T object) {
		mFileList = (List<FileInfo>) object;
	}

	@Override
	public <T> T getResponseData() {
		return (T) mFileList;
	}

}
