package com.glshop.platform.api.profile.data.model;

import java.io.File;
import java.io.Serializable;

/**
 * @Description : 图片信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-7 下午6:13:56
 */
public class ImageInfoModel implements Serializable, Cloneable {

	private static final long serialVersionUID = -5308696790668014498L;

	/**
	 * 本地图片路径
	 */
	public File localFile;

	/**
	 * 云端图片ID
	 */
	public String cloudId;

	/**
	 * 云端文件路径
	 */
	public String cloudUrl;

	/**
	 * 云端图片缩略图路径
	 */
	public String cloudThumbnailUrl;

	/**
	 * 本地资源ID
	 */
	public int resourceId;

	@Override
	public Object clone() {
		ImageInfoModel o = null;
		try {
			o = (ImageInfoModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ImageInfoModel[");
		sb.append("cloudId=" + cloudId);
		sb.append(", resourceId=" + resourceId);
		sb.append(", cloudUrl=" + cloudUrl);
		sb.append(", cloudThumbnailUrl=" + cloudThumbnailUrl);
		sb.append(", localFile=" + localFile);
		sb.append("]");
		return sb.toString();
	}

}
