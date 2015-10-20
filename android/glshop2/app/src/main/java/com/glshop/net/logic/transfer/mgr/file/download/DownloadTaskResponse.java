package com.glshop.net.logic.transfer.mgr.file.download;

import com.glshop.net.logic.transfer.mgr.BaseTaskResponse;
import com.glshop.net.logic.transfer.mgr.file.FileInfo;

/**
 * @Description : 下载返回结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-28 上午10:25:07
 */
public class DownloadTaskResponse extends BaseTaskResponse {

	/**
	 * 下载云端文件信息
	 */
	private FileInfo mFile;

	public DownloadTaskResponse() {

	}

	@Override
	public <T> void setResponseData(T object) {
		mFile = (FileInfo) object;
	}

	@Override
	public <T> T getResponseData() {
		return (T) mFile;
	}

}
