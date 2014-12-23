package com.glshop.net.logic.transfer.mgr.file;

import java.io.File;
import java.io.Serializable;

/**
 * @Description : 文件信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 下午8:03:56
 */
public class FileInfo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5771996483830325079L;

	/**
	 * 文件信息
	 */
	public File file;

	/**
	 * 文件云端ID
	 */
	public String cloudId;

	/**
	 * 云端文件路径
	 */
	public String cloudUrl;

	/**
	 * 云端文件缩略图路径
	 */
	public String cloudThumbnailUrl;

	/**
	 * 文件大小
	 */
	public long size;

	/**
	 * 文件上传状态
	 */
	public FileStatus uploadStatus = FileStatus.NONE;

	/**
	 * 文件下载状态
	 */
	public FileStatus downloadStatus = FileStatus.NONE;

	/**
	 * 文件状态(上传和下载)
	 */
	public enum FileStatus {

		NONE,

		RUNNINIG,

		SUCCESS,

		ERROR,

		CANCELED

	}
}
