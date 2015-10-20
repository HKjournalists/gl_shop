package com.glshop.platform.api.file.data;

import com.glshop.platform.api.base.CommonResult;

/**
 * @Description : 上传文件请求结果
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-24 上午9:44:05
 */
public class UploadFileResult extends CommonResult {

	/**
	 * 云端文件ID
	 */
	public String cloudId;

	/**
	 * 云端文件路径
	 */
	public String cloudFilePath;

	/**
	 * 云端文件缩略图路径
	 */
	public String cloudFileThumbnailPath;

	@Override
	public String toString() {
		return super.toString() + ", cloudId = " + cloudId + ", cloudFilePath = " + cloudFilePath + ", cloudFileThumbnailPath = " + cloudFileThumbnailPath;
	}

}
