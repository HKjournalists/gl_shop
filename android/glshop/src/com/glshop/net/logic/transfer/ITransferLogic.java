package com.glshop.net.logic.transfer;

import java.util.List;

import com.glshop.net.logic.transfer.mgr.file.FileInfo;
import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 传输管理接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:15
 */
public interface ITransferLogic extends ILogic {

	/**
	 * 文件上传
	 * @param invoker
	 * @param info 文件信息
	 * @return 任务ID
	 */
	String uploadFile(String invoker, FileInfo info);

	/**
	 * 文件上传(批量上传)
	 * @param invoker
	 * @param infos 批量文件信息
	 * @return 任务ID
	 */
	String uploadFile(String invoker, List<FileInfo> infos);

	/**
	 * 文件下载
	 * @param invoker
	 * @param file 下载文件信息
	 * @return 任务ID
	 */
	String downloadFile(String invoker, FileInfo file);

	/**
	 * 取消文件上传
	 * @param taskId 任务ID
	 */
	void cancelFileUpload(String taskId);

}
