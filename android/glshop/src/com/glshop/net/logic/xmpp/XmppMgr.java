package com.glshop.net.logic.xmpp;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.notification.DefineNotification;
import com.glshop.net.ui.basic.notification.NotificationConstants.PushNotifyID;
import com.glshop.net.ui.basic.notification.NotificationHelper;
import com.glshop.net.ui.basic.notification.NotificationHelper.PendingIntentType;
import com.glshop.net.ui.mycontract.ContractInfoActivity;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.utils.Logger;
import com.glshop.platform.utils.StringUtils;

/**
 * @Description : XMPP管理类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-10-27 上午10:33:22
 */
public class XmppMgr {

	private static final String TAG = "XmppMgr";

	private static XmppMgr mInstance;

	private Context mContext;

	private XmppMgr() {

	}

	public static synchronized XmppMgr getInstance() {
		if (mInstance == null) {
			mInstance = new XmppMgr();
		}
		return mInstance;
	}

	public void initialize(Context context) {
		mContext = context;
	}

	public void parseAction(XmppMsgInfo info) {
		if (info != null) {
			Logger.e(TAG, "XmppInfo = " + info);
			switch (info.businessType) {
			case TYPE_REGISTER:

				break;
			case TYPE_COMPANY_AUTH:
				if (checkUser() && isCurUser(info.owner)) {
					handleAuthResult(info);
				}
				break;
			case TYPE_CONTRACT_SIGN:
			case TYPE_CONTRACT_ING:
			case TYPE_CONTRACT_EVALUATION:
				if (checkUser() && isCurUser(info.owner)) {
					handleContractInfo(info);
				}
				break;
			case TYPE_USER_LOGIN_OTHER_DEVICE: // 用户下线
				if (checkUser() && isCurUser(info.owner)) {
					handleLogoutInfo(info);
				}
				break;
			case TYPE_MONEY_CHANG_GUARANTY: // 保证金金额变动
				if (checkUser() && isCurUser(info.owner)) {
					handleDepositInfo(info);
				}
				break;
			case TYPE_MONEY_CHANG_DEPOSIT: // 货款金额变动
				if (checkUser() && isCurUser(info.owner)) {
					handlePaymentInfo(info);
				}
				break;
			case TYPE_MONEY_CHANG: // 钱包金额变动
				if (checkUser() && isCurUser(info.owner)) {
					handlePurseInfo(info);
				}
				break;
			}
		}
	}

	private void handleAuthResult(XmppMsgInfo info) {
		MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.ProfileMessageType.MSG_REFRESH_MY_PROFILE);
		String title = "长江电商平台认证成功";
		String context = mContext.getString(R.string.notify_profile_content_auth_success);
		Notification notification = DefineNotification.getDefaultNotification(mContext, title, context, title);
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_MY_PROFILE);
		notification.contentIntent = NotificationHelper.getDefaultPendingIntent(mContext, PendingIntentType.ACTIVITY, PushNotifyID.BUSINESS_TYPE_COMPANY_AUTH, intent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(mContext, PushNotifyID.BUSINESS_TYPE_COMPANY_AUTH, notification);
	}

	/**
	 * 处理合同相关推送
	 * @param info
	 */
	private void handleContractInfo(XmppMsgInfo info) {
		String title = "合同状态更新";
		String context = /*mContext.getString(R.string.notify_contract_content_receive_unf_contract)*/info.content;
		Notification notification = DefineNotification.getDefaultNotification(mContext, title, context, title);
		Intent intent = null;
		if (info.businessType == MsgBusinessType.TYPE_CONTRACT_SIGN) {
			intent = new Intent(mContext, /*UfmContractInfoActivity.class*/ContractInfoActivity.class);
		} else {
			intent = new Intent(mContext, ContractInfoActivity.class);
		}
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, info.businessId);
		notification.contentIntent = NotificationHelper.getDefaultPendingIntent(mContext, PendingIntentType.ACTIVITY, PushNotifyID.BUSINESS_TYPE_CONTRACT_SIGN, intent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(mContext, PushNotifyID.BUSINESS_TYPE_CONTRACT_SIGN, notification);
	}

	/**
	 * 处理保证金相关推送
	 * @param info
	 */
	private void handleDepositInfo(XmppMsgInfo info) {
		String title = "用户保证金金额变动";
		String context = info.content;
		Notification notification = DefineNotification.getDefaultNotification(mContext, title, context, title);
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE);
		notification.contentIntent = NotificationHelper.getDefaultPendingIntent(mContext, PendingIntentType.ACTIVITY, PushNotifyID.BUSINESS_TYPE_MONEY_CHANG_GUARANTY, intent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(mContext, PushNotifyID.BUSINESS_TYPE_MONEY_CHANG_GUARANTY, notification);

		if (info.params != null) {
			GlobalConfig.getInstance().setDepositStatus(info.params.getBoolean("isGuarantyEnough"));
			GlobalConfig.getInstance().setDepositBalance(info.params.getFloat("balance"));
			MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO);
		}
	}

	/**
	 * 处理货款相关推送
	 * @param info
	 */
	private void handlePaymentInfo(XmppMsgInfo info) {
		String title = "用户货款金额变动";
		String context = info.content;
		Notification notification = DefineNotification.getDefaultNotification(mContext, title, context, title);
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE);
		notification.contentIntent = NotificationHelper.getDefaultPendingIntent(mContext, PendingIntentType.ACTIVITY, PushNotifyID.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT, intent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(mContext, PushNotifyID.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT, notification);

		if (info.params != null) {
			GlobalConfig.getInstance().setPaymentStatus(info.params.getBoolean("isGuarantyEnough"));
			GlobalConfig.getInstance().setPaymentBalance(info.params.getFloat("balance"));
			MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO);
		}
	}

	/**
	 * 处理钱包相关推送
	 * @param info
	 */
	private void handlePurseInfo(XmppMsgInfo info) {
		String title = "用户钱包金额变动";
		String context = info.content;
		Notification notification = DefineNotification.getDefaultNotification(mContext, title, context, title);
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE);
		notification.contentIntent = NotificationHelper.getDefaultPendingIntent(mContext, PendingIntentType.ACTIVITY, PushNotifyID.BUSINESS_TYPE_MONEY_CHANG, intent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(mContext, PushNotifyID.BUSINESS_TYPE_MONEY_CHANG, notification);

		if (info.params != null) {
			GlobalConfig.getInstance().setDepositBalance(info.params.getFloat("guarantybalance"));
			GlobalConfig.getInstance().setPaymentBalance(info.params.getFloat("depositbalance"));
			GlobalConfig.getInstance().setDepositStatus(info.params.getBoolean("isGuarantyEnough"));
			//GlobalConfig.getInstance().setPaymentStatus(info.params.getBoolean("isGuarantyEnough"));
			MessageCenter.getInstance().sendEmptyMesage(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO);
		}
	}

	/**
	 * 处理离线推送
	 * @param info
	 */
	private void handleLogoutInfo(XmppMsgInfo info) {
		String title = "用户下线通知";
		String context = info.content;
		Notification notification = DefineNotification.getDefaultNotification(mContext, title, context, title);
		//notification.contentIntent = NotificationHelper.getDefaultPendingIntent(mContext, PendingIntentType.ACTIVITY, NotifyID.BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE, null);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		DefineNotification.notifyCommon(mContext, PushNotifyID.BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE, notification);

		MessageCenter.getInstance().sendEmptyMesage(DataConstants.GlobalMessageType.MSG_USER_OFFLINE);
	}

	/**
	 * 检查用户状态
	 * @return
	 */
	private boolean checkUser() {
		return GlobalConfig.getInstance().isLogined();
	}

	/**
	 * 是否为同一个用户
	 * @param owner
	 * @return
	 */
	private boolean isCurUser(String owner) {
		Logger.i(TAG, "XmppOwner = " + owner + ", CurUser = " + GlobalConfig.getInstance().getCompanyId());
		return StringUtils.isNotEmpty(owner) && owner.equals(GlobalConfig.getInstance().getCompanyId());
	}
}
