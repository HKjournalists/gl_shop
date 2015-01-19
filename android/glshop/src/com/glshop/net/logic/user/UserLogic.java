package com.glshop.net.logic.user;

import android.content.Context;
import android.os.Message;

import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalMessageType.UserMessageType;
import com.glshop.net.logic.basic.BasicLogic;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.platform.api.IReturnCallback;
import com.glshop.platform.api.base.CommonResult;
import com.glshop.platform.api.user.GetImgVerifyCodeReq;
import com.glshop.platform.api.user.GetSmsVerifyCodeExReq;
import com.glshop.platform.api.user.GetSmsVerifyCodeReq;
import com.glshop.platform.api.user.ModifyPwdReq;
import com.glshop.platform.api.user.RefreshTokenReq;
import com.glshop.platform.api.user.RegUserReq;
import com.glshop.platform.api.user.ResetPwdReq;
import com.glshop.platform.api.user.UserLoginReq;
import com.glshop.platform.api.user.UserLogoutReq;
import com.glshop.platform.api.user.data.GetImgVerifyCodeResult;
import com.glshop.platform.api.user.data.LoginResult;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.net.base.ProtocolType.ResponseEvent;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : 用户账户业务接口实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午5:26:30
 */
public class UserLogic extends BasicLogic implements IUserLogic {

	private static final String TAG = "UserLogic";

	public UserLogic(Context context) {
		super(context);
	}

	@Override
	public void login(String account, String password, String clientID) {
		UserLoginReq req = new UserLoginReq(this, new IReturnCallback<LoginResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, LoginResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "LoginResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						// 保存当前登录用户信息
						GlobalConfig.getInstance().saveLoginInfo(result.data);
						message.what = UserMessageType.MSG_LOGIN_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_LOGIN_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.password = password;
		req.clientID = clientID;
		req.exec();
	}

	@Override
	public void login(String account, String password, String verifyCode, String clientID) {
		// TODO
	}

	@Override
	public void logout(String account) {
		UserLogoutReq req = new UserLogoutReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					GlobalConfig.getInstance().cleanLoginInfo();
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = UserMessageType.MSG_LOGOUT_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_LOGOUT_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.exec();
	}

	@Override
	public void getImgVerifyCode(String invoker, String file, String deviceId) {
		GetImgVerifyCodeReq req = new GetImgVerifyCodeReq(invoker, new IReturnCallback<GetImgVerifyCodeResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, GetImgVerifyCodeResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "GetImgVerifyCodeResult = " + result.toString());
					Message message = new Message();
					RespInfo respInfo = getOprRespInfo(invoker, result);
					message.obj = respInfo;
					if (result.isSuccess()) {
						if (result.imgVerifyCode == null || result.imgVerifyCode.getRowBytes() == 0) {
							Logger.e(TAG, "GetImgVerifyCode Error: 图片不存在或大小为0");
							message.what = UserMessageType.MSG_GET_IMG_VERFIYCODE_FAILED;
						} else {
							message.what = UserMessageType.MSG_GET_IMG_VERFIYCODE_SUCCESS;
							respInfo.data = result.imgVerifyCode;
						}
					} else {
						message.what = UserMessageType.MSG_GET_IMG_VERFIYCODE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.deviceId = deviceId;
		req.imgCodePath = file/*GlobalConstants.AppDirConstants.IMG_VERIFY_CODE_PATH*/;
		req.exec();
	}

	@Override
	public void getSmsVerifyCode(String invoker, String account, String sendType) {
		GetSmsVerifyCodeReq req = new GetSmsVerifyCodeReq(invoker, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Message message = new Message();
					message.obj = getOprRespInfo(invoker, result);
					if (result.isSuccess()) {
						message.what = UserMessageType.MSG_GET_SMS_VERFIYCODE_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.sendType = sendType;
		req.exec();
	}

	@Override
	public void getSmsVerifyCode(String invoker, String account) {
		getSmsVerifyCode(invoker, account, null);
	}

	@Override
	public void getSmsVerifyCode(String invoker, String deviceId, String account, String imgVerifyCode) {
		GetSmsVerifyCodeExReq req = new GetSmsVerifyCodeExReq(invoker, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Message message = new Message();
					message.obj = getOprRespInfo(invoker, result);
					if (result.isSuccess()) {
						message.what = UserMessageType.MSG_GET_SMS_VERFIYCODE_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_GET_SMS_VERFIYCODE_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.deviceId = deviceId;
		req.account = account;
		req.imgVerifyCode = imgVerifyCode;
		req.exec();
	}

	@Override
	public void validSmsVerfiyCode(String account, String verifyCode) {
		/*ValidSmsVerifyCodeReq req = new ValidSmsVerifyCodeReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (result.isSuccess()) {
					sendMessage(UserMessageType.MSG_VALID_SMS_VERFIYCODE_SUCCESS, null);
				} else {
					Message message = new Message();
					message.what = UserMessageType.MSG_VALID_SMS_VERFIYCODE_FAILED;
					message.arg1 = GlobalErrorMessage.getErrorMsg(result.error.getErrorCode());
					message.obj = result.error.getErrorMessage();
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.verifyCode = verifyCode;
		req.exec();*/
	}

	@Override
	public void registerUser(String account, String password, String smsCode) {
		RegUserReq req = new RegUserReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = UserMessageType.MSG_REG_USER_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_REG_USER_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.password = password;
		req.smsVerifyCode = smsCode;
		req.exec();
	}

	@Override
	public void resetPassword(String account, String newPassword, String smsCode) {
		ResetPwdReq req = new ResetPwdReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						message.what = UserMessageType.MSG_RESET_PASSWORD_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_RESET_PASSWORD_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.newPassword = newPassword;
		req.smsVerifyCode = smsCode;
		req.exec();
	}

	@Override
	public void modifyPassword(String account, String oldPassword, String newPassword, String smsCode) {
		ModifyPwdReq req = new ModifyPwdReq(this, new IReturnCallback<CommonResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, CommonResult result) {
				if (ResponseEvent.isFinish(event)) {
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						GlobalConfig.getInstance().cleanLoginInfo();
						message.what = UserMessageType.MSG_MODIFY_PASSWORD_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_MODIFY_PASSWORD_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.oldPassword = oldPassword;
		req.newPassword = newPassword;
		req.smsVerifyCode = smsCode;
		req.exec();
	}

	@Override
	public void refreshToken(String account, String token, boolean isTokenExpired) {
		RefreshTokenReq req = new RefreshTokenReq(this, new IReturnCallback<LoginResult>() {

			@Override
			public void onReturn(Object invoker, ResponseEvent event, LoginResult result) {
				if (ResponseEvent.isFinish(event)) {
					Logger.i(TAG, "RefreshTokenResult = " + result.toString());
					Message message = new Message();
					message.obj = getOprRespInfo(result);
					if (result.isSuccess()) {
						// 保存当前登录用户信息
						GlobalConfig.getInstance().saveLoginInfo(result.data);
						message.what = UserMessageType.MSG_REFRESH_TOKEN_SUCCESS;
					} else {
						message.what = UserMessageType.MSG_REFRESH_TOKEN_FAILED;
					}
					sendMessage(message);
				}
			}
		});
		req.account = account;
		req.token = token;
		req.exec();
	}

	@Override
	public boolean autoLogin(boolean isRefreshToken) {
		String token = PlatformConfig.getString(PlatformConfig.USER_TOKEN);
		Logger.i(TAG, "Token = " + token);
		if (isTokenValidate()) {
			// Token有效
			if (PlatformConfig.getBoolean(GlobalConstants.SPKey.IS_REMEMBER_USER_PWD, false)) {
				// 用户记住密码，则自动登录
				GlobalConfig.getInstance().initLoginInfo();
				GlobalConfig.getInstance().loginXmpp();
				if (isRefreshToken) {
					refreshToken(GlobalConfig.getInstance().getUserAccount(), PlatformConfig.getString(PlatformConfig.USER_TOKEN), false);
				}
				return true;
			} else {
				// 用户不记住密码，则不自动登录
				return false;
			}
		} else {
			// Token失效，刷新Token
			if (StringUtils.isNotEmpty(token) && isRefreshToken) {
				refreshToken(GlobalConfig.getInstance().getUserAccount(), token, true);
			}
			return false;
		}
	}

	/**
	 * 判断Token是否有效
	 * @return
	 */
	private boolean isTokenValidate() {
		String tokenExpire = PlatformConfig.getString(PlatformConfig.TOKEN_EXPIRY);
		String tokenUpdateTime = PlatformConfig.getString(GlobalConstants.SPKey.USER_TOKEN_UPDATE_TIME);
		if (StringUtils.isNotEmpty(tokenExpire) && StringUtils.isNotEmpty(tokenUpdateTime)) {
			long tokenTime = PlatformConfig.getLong(GlobalConstants.SPKey.USER_TOKEN_UPDATE_TIME);
			long tokenExpiry = PlatformConfig.getLong(PlatformConfig.TOKEN_EXPIRY, 0) * 1000;
			//Logger.i(TAG, "TokenTime = " + (tokenTime + tokenExpiry) + ", Now = " + System.currentTimeMillis());
			if (tokenTime + tokenExpiry >= System.currentTimeMillis()) {
				return true;
			}
		}
		return false;
	}

}
