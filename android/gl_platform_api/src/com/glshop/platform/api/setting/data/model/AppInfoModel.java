package com.glshop.platform.api.setting.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 应用信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class AppInfoModel extends ResultItem {

	/**
	 * 客户端类型
	 */
	public String deviceType;

	/**
	 * 客户端设备ID
	 */
	public String deviceId;

	/**
	 * 客户端设备名称
	 */
	public String deviceName;

	/**
	 * 版本名称
	 */
	public String versionName;

	/**
	 * 版本号编码
	 */
	public String versionCode;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AppInfoModel[");
		sb.append("deviceType=" + deviceType);
		sb.append(", deviceId=" + deviceId);
		sb.append(", deviceName=" + deviceName);
		sb.append(", versionName=" + versionName);
		sb.append(", versionCode=" + versionCode);
		sb.append("]");
		return sb.toString();
	}

}
