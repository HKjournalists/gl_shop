package com.glshop.net.logic.upgrade.mgr;

import android.content.Context;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 升级模块工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午6:13:15
 */
public class UpgradeUtils {

	public static void saveIgnoreVersion(Context context, String versionCode) {
		GlobalConfig.setSPValue(GlobalConstants.SPKey.CUR_IGNORE_VERSION_CODE, versionCode);
	}

	public static String getIgnoreVersion() {
		return GlobalConfig.getStringSPValue(GlobalConstants.SPKey.CUR_IGNORE_VERSION_CODE);
	}

	public static boolean isIgnoreVersion(Context context, String versionCode) {
		return StringUtils.isNotEmpty(versionCode) && versionCode.equalsIgnoreCase(getIgnoreVersion());
	}
}
