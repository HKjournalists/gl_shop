package com.glshop.platform.api.setting.data.model;

import com.glshop.platform.net.base.ResultItem;

/**
 * @Description : 升级信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-8-20 上午11:12:23
 */
public class UpgradeInfoModel extends ResultItem {

	/**
	 * 版本名称
	 */
	public String versionName;
	
	/**
	 * 版本编号
	 */
	public String versionCode;

	/**
	 * 大小
	 */
	public String size;

	/**
	 * 下载地址
	 */
	public String url;

	/**
	 * 文件MD5
	 */
	public String md5;

	/**
	 * 是否强制升级
	 */
	public boolean isForceUpgrade;

	/**
	 * 升级描述
	 */
	public String description;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof UpgradeInfoModel) {
			UpgradeInfoModel other = (UpgradeInfoModel) o;
			if (this.md5 == null || other.md5 == null) {
				return false;
			} else {
				return this.md5.equals(md5);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UpgradeInfoModel[");
		sb.append("versionName=" + versionName);
		sb.append(", versionCode=" + versionCode);
		sb.append(", size=" + size);
		sb.append(", url=" + url);
		sb.append(", md5=" + md5);
		sb.append(", isForceUpgrade=" + isForceUpgrade);
		sb.append(", description=" + description);
		sb.append("]");
		return sb.toString();
	}

}
