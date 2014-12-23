package com.glshop.net.utils;

import com.glshop.platform.utils.StringUtils;

/**
 * 系统工具类
 */
public class SystemUtil {

	/**
	 * 生成MD5加密后的密码
	 * @param account
	 * @param password
	 * @return
	 */
	public static String getEncryptPassword(String account, String password) {
		return MD5.getMD5String(account + password);
	}

	/**
	 * 验证短信验证码是否有效
	 * @param smsCode
	 * @return
	 */
	public static boolean isSmsVerifyCode(String smsCode) {
		return StringUtils.isNotEmpty(smsCode) && smsCode.trim().length() == 6;
	}

	/**
	 * 验证图形验证码是否有效
	 * @param smsCode
	 * @return
	 */
	public static boolean isImgVerifyCode(String imgCode) {
		return StringUtils.isNotEmpty(imgCode) && imgCode.trim().length() == 4;
	}

}
