package com.glshop.net.logic.model;

/**
 * @Description : 图片状态信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 下午4:06:18
 */
public class ImageStatusInfo {

	/**
	 * 图片本地路径
	 */
	public String filePath;

	/**
	 * 图片云端ID
	 */
	public String cloudId;

	/**
	 * 是否修改
	 */
	public boolean isModified;

	/**
	 * Obj
	 */
	public Object obj;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ImageStatusInfo[");
		sb.append("filePath=" + filePath);
		sb.append(", cloudId=" + cloudId);
		sb.append(", isModified=" + isModified);
		sb.append("]");
		return sb.toString();
	}

}
