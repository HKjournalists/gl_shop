package com.glshop.net.logic.user;

import com.glshop.platform.base.logic.ILogic;

/**
 * @Description : 用户账户接口定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午5:14:40
 */
public interface IUserLogic extends ILogic {

	/**
	 * 普通登录
	 * @param account
	 * @param password
	 */
	public void login(String account, String password, String clientID);

	/**
	 * 带验证码方式登录(前期无此需求，后续实现)
	 * @param account
	 * @param password
	 * @param verifyCode
	 */
	@Deprecated
	public void login(String account, String password, String verifyCode, String clientID);

	/**
	 * 注销
	 * @param account
	 */
	public void logout(String account);

	/**
	 * 获取图形验证码
	 * @param invoker
	 * @param file
	 * @param deviceId
	 */
	public void getImgVerifyCode(String invoker, String file, String deviceId);

	/**
	 * 获取短信验证码
	 * @param invoker
	 * @param account
	 */
	public void getSmsVerifyCode(String invoker, String account);

	/**
	 * 获取短信验证码
	 * @param invoker
	 * @param account
	 */
	public void getSmsVerifyCode(String invoker, String account, String sendType);

	/**
	 * 获取短信验证码、同时验证图像验证码
	 * @param invoker
	 * @param deviceId
	 * @param account
	 * @param imgVerifyCode
	 */
	@Deprecated
	public void getSmsVerifyCode(String invoker, String deviceId, String account, String imgVerifyCode);

	/**
	 * 验证短信验证码是否准确
	 * @param account
	 * @param verifyCode
	 */
	public void validSmsVerfiyCode(String account, String verifyCode);

	/**
	 * 验证图形验证码是否准确
	 * @param account
	 * @param verifyCode
	 */
	public void validImgVerfiyCode(String account, String deviceId, String verifyCode);

	/**
	 * 检验注册用户
	 * @param account
	 */
	public void checkUser(String account);

	/**
	 * 用户注册
	 * @param account
	 * @param password
	 * @param smsCode
	 */
	public void registerUser(String account, String password, String smsCode);

	/**
	 * 找回密码
	 * @param account
	 * @param password
	 * @param smsCode
	 */
	public void resetPassword(String account, String newPassword, String smsCode);

	/**
	 * 修改密码
	 * @param account
	 * @param oldPassword
	 * @param newPassword
	 * @param smsCode
	 */
	public void modifyPassword(String account, String oldPassword, String newPassword, String smsCode);

	/**
	 * 刷新Token
	 * @param token
	 */
	public void refreshToken(String account, String clientID, String token, boolean isTokenExpired);

	/**
	 * 判断自动登录
	 * @return
	 */
	public boolean autoLogin(boolean isRefreshToken);

}
