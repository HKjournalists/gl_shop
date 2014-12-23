package com.glshop.net.common;

import android.content.Context;

import com.glshop.net.logic.upgrade.IUpgradeLogic;
import com.glshop.net.ui.basic.notification.DefineNotification;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.api.user.data.model.LoginRespInfoModel;
import com.glshop.platform.base.config.PlatformConfig;
import com.glshop.platform.base.manager.LogicFactory;
import com.igexin.sdk.PushManager;

/**
 * @Description : 全局配置 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:55:51
 */
public class GlobalConfig {

	private static GlobalConfig mInstance;

	/** 上下文  */
	private Context mContext;

	/** 当前用户登录状态  */
	private boolean isLogined = false;

	/** 当前用户发布供求数  */
	private int mMyBuyCount = 0;

	/** 当前用户合同数  */
	private int mContractCount = 0;

	/** 保证金是否足够 */
	private boolean isDepositEnough;

	/** 货款是否足够 */
	private boolean isPaymentEnough;

	/** 保证金余额 */
	private float depositBalance;

	/** 货款余额 */
	private float paymentBalance;

	/** 未读消息数目 */
	private int unreadMsgNum;

	private GlobalConfig() {

	}

	public static synchronized GlobalConfig getInstance() {
		if (mInstance == null) {
			mInstance = new GlobalConfig();
		}
		return mInstance;
	}

	public void init(Context context) {
		mContext = context;
	}

	/**
	 * 设置用户登录状态
	 * @param isLogined
	 */
	public void setLoginStatus(boolean isLogined) {
		this.isLogined = isLogined;
	}

	/**
	 * 当前用户是否登录
	 * @return
	 */
	public boolean isLogined() {
		return isLogined;
	}

	/**
	 * 设置当前登录账户名
	 * @return
	 */
	public void setUserAccount(String account) {
		setSPValue(GlobalConstants.SPKey.USER_ACCOUNT, account);
	}

	/**
	 * 获取当前登录账户名
	 * @return
	 */
	public String getUserAccount() {
		return (String) getSPValue(GlobalConstants.SPKey.USER_ACCOUNT);
	}

	/**
	 * 设置当前登录账户手机号
	 * @return
	 */
	public void setUserPhone(String phone) {
		setSPValue(GlobalConstants.SPKey.USER_PHONE, phone);
	}

	/**
	 * 获取当前登录账户手机号
	 * @return
	 */
	public String getUserPhone() {
		return (String) getSPValue(GlobalConstants.SPKey.USER_PHONE);
	}

	/**
	 * 设置当前登录账户ID
	 * @return
	 */
	public void setUserID(String id) {
		setSPValue(GlobalConstants.SPKey.USER_ID, id);
	}

	/**
	 * 获取当前登录账户ID
	 * @return
	 */
	public String getUserID() {
		return (String) getSPValue(GlobalConstants.SPKey.USER_ID);
	}

	/**
	 * 设置用户身份
	 * @param type
	 */
	public void setProfleType(ProfileType type) {
		if (type != null) {
			setSPValue(GlobalConstants.SPKey.USER_PROFILE_TYPE, type.toValue());
		} else {
			removeSPValue(GlobalConstants.SPKey.USER_PROFILE_TYPE);
		}
	}

	/**
	 * 获取用户身份
	 * @return
	 */
	public ProfileType getProfileType() {
		int value = PlatformConfig.getInt(GlobalConstants.SPKey.USER_PROFILE_TYPE, -1);
		if (value != -1) {
			return ProfileType.convert(PlatformConfig.getInt(GlobalConstants.SPKey.USER_PROFILE_TYPE, ProfileType.COMPANY.toValue()));
		} else {
			return null;
		}
	}

	/**
	 * 设置用户发布供求数
	 * @param count
	 */
	public void setMyBuyCount(int count) {
		this.mMyBuyCount = count;
	}

	/**
	 * 获取用户发布供求数
	 * @return
	 */
	public int getMyBuyCount() {
		return mMyBuyCount;
	}

	/**
	 * 设置用户合同数
	 * @param count
	 */
	public void setContractCount(int count) {
		this.mContractCount = count;
	}

	/**
	 * 获取用户合同数
	 * @return
	 */
	public int getContractCount() {
		return mContractCount;
	}

	/**
	 * 设置企业ID
	 * @param id
	 */
	public void setCompanyId(String id) {
		setSPValue(GlobalConstants.SPKey.COMPANY_ID, id);
	}

	/**
	 * 获取企业ID
	 * @return
	 */
	public String getCompanyId() {
		return (String) getSPValue(GlobalConstants.SPKey.COMPANY_ID);
	}

	/**
	 * 设置企业名称
	 * @param name
	 */
	public void setCompanyName(String name) {
		setSPValue(GlobalConstants.SPKey.COMPANY_NAME, name);
	}

	/**
	 * 获取企业名称
	 * @return
	 */
	public String getCompanyName() {
		return (String) getSPValue(GlobalConstants.SPKey.COMPANY_NAME);
	}

	/**
	 * 设置用户保证金状态
	 * @param enough
	 */
	public void setDepositStatus(boolean enough) {
		isDepositEnough = enough;
	}

	/**
	 * 获取用户保证金状态
	 * @return
	 */
	public boolean isDepositEnough() {
		return isDepositEnough;
	}

	/**
	 * 设置用户货款状态
	 * @param enough
	 */
	public void setPaymentStatus(boolean enough) {
		isPaymentEnough = enough;
	}

	/**
	 * 获取用户货款状态
	 * @return
	 */
	public boolean isPaymentEnough() {
		return isPaymentEnough;
	}

	/**
	 * 设置用户保证金余额
	 * @param enough
	 */
	public void setDepositBalance(float balance) {
		depositBalance = balance;
	}

	/**
	 * 获取用户保证金余额
	 * @return
	 */
	public float getDepositBalance() {
		return depositBalance;
	}

	/**
	 * 设置用户货款余额
	 * @param enough
	 */
	public void setPaymentBalance(float balance) {
		paymentBalance = balance;
	}

	/**
	 * 获取用户货款余额
	 * @return
	 */
	public float getPaymentBalance() {
		return paymentBalance;
	}

	/**
	 * 获取全局未读消息数目
	 */
	public void setUnreadMsgNum(int num) {
		unreadMsgNum = num;
	}

	/**
	 * 获取全局未读消息数目
	 * @return
	 */
	public int getUnreadMsgNum() {
		return unreadMsgNum;
	}

	/**
	 * 保存Token
	 * @param token
	 */
	public void saveToken(String token) {
		setSPValue(PlatformConfig.USER_TOKEN, token);
	}

	/**
	 * 获取Token
	 * @return
	 */
	public String getToken() {
		return (String) getSPValue(PlatformConfig.USER_TOKEN);
	}

	/**
	 * 保存Token有效期
	 * @param token
	 */
	public void saveTokenExpire(long tokenExpire) {
		setSPValue(PlatformConfig.TOKEN_EXPIRY, tokenExpire);
	}

	/**
	 * 获取Token有效期
	 * @return
	 */
	public Long getTokenExpire() {
		return (Long) getSPValue(PlatformConfig.TOKEN_EXPIRY);
	}

	/**
	 * 保存Token更新时间
	 * @param token
	 */
	public void saveTokenUpdateTime(long time) {
		setSPValue(GlobalConstants.SPKey.USER_TOKEN_UPDATE_TIME, time);
	}

	/**
	 * 获取Token更新时间
	 * @return
	 */
	public Long getTokenUpdateTime() {
		return (Long) getSPValue(GlobalConstants.SPKey.USER_TOKEN_UPDATE_TIME);
	}

	/**
	 * 初始化登录信息(Token自动登录)
	 */
	public void initLoginInfo() {
		setLoginStatus(true);
	}

	/**
	 * 保存登录返回信息
	 * @param info
	 */
	public void saveLoginInfo(LoginRespInfoModel info) {
		if (info != null) {
			setLoginStatus(true);
			setUserAccount(info.account);
			setUserPhone(info.phone);
			setCompanyId(info.companyId);
			setCompanyName(info.companyName);
			setProfleType(info.profileType);
			setMyBuyCount(info.myBuyCount);
			setContractCount(info.contractCount);
			setProfleType(info.profileType);
			setDepositStatus(info.isDepositEnough);
			saveToken(info.token);
			saveTokenExpire(info.tokenExpire); // 保存Token有效期
			saveTokenUpdateTime(System.currentTimeMillis()); // 保存Token更新时间
			loginXmpp(); // 登录xmpp
		}
	}

	/**
	 * 用户注销时清除当前用户相关数据
	 */
	public void cleanLoginInfo() {
		isLogined = false;
		mContractCount = 0;
		mMyBuyCount = 0;
		isDepositEnough = false;
		removeSPValue(GlobalConstants.SPKey.COMPANY_ID);
		removeSPValue(GlobalConstants.SPKey.COMPANY_NAME);
		removeSPValue(GlobalConstants.SPKey.USER_PROFILE_TYPE);
		removeSPValue(PlatformConfig.USER_TOKEN);
		removeSPValue(PlatformConfig.TOKEN_EXPIRY);
		removeSPValue(GlobalConstants.SPKey.USER_TOKEN_UPDATE_TIME);
		logoutXmpp(); // 注销xmpp
		DefineNotification.clearAll(true); // 清除所有通知栏通知
		stopUpgradeTask(); // 取消升级操作
	}

	/**
	 * 取消升级任务
	 */
	public void stopUpgradeTask() {
		IUpgradeLogic mUpgradeLogic = LogicFactory.getLogicByClass(IUpgradeLogic.class);
		if (mUpgradeLogic != null) {
			mUpgradeLogic.cancelDownload();
		}
	}

	/**
	 * xmpp登陆
	 */
	public void loginXmpp() {
		PushManager.getInstance().turnOnPush(mContext);
	}

	/**
	 * xmpp注销
	 */
	public void logoutXmpp() {
		PushManager.getInstance().turnOffPush(mContext);
	}

	/**
	 * 获取SP值
	 * @return
	 */
	public Object getSPValue(String key) {
		return PlatformConfig.getString(key);
	}

	/**
	 * 设置SP值
	 * @param key
	 * @param value
	 */
	public void setSPValue(String key, Object value) {
		PlatformConfig.setValue(key, value);
	}

	/**
	 * 删除SP值
	 * @param key
	 */
	public void removeSPValue(String key) {
		PlatformConfig.remove(key);
	}

}
