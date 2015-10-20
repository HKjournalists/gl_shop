package com.glshop.net.logic.xmpp;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.glshop.net.R;
import com.glshop.net.common.GlobalAction;
import com.glshop.net.common.GlobalConfig;
import com.glshop.net.common.GlobalConstants;
import com.glshop.net.common.GlobalMessageType;
import com.glshop.net.logic.model.RespInfo;
import com.glshop.net.ui.MainActivity;
import com.glshop.net.ui.basic.notification.NotificationConstants.PushNotifyID;
import com.glshop.net.ui.basic.notification.NotificationHelper;
import com.glshop.net.ui.basic.notification.NotificationHelper.PendingIntentType;
import com.glshop.net.ui.mycontract.ContractInfoActivityV2;
import com.glshop.platform.api.DataConstants;
import com.glshop.platform.api.DataConstants.AuthStatusType;
import com.glshop.platform.api.DataConstants.ContractType;
import com.glshop.platform.api.DataConstants.MsgBusinessType;
import com.glshop.platform.api.DataConstants.ProfileType;
import com.glshop.platform.base.manager.MessageCenter;
import com.glshop.platform.net.base.ResultItem;
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

	/**
	 * 处理推送业务
	 * @param info
	 */
	public void parseAction(XmppMsgInfo info) {
		if (info != null) {
			Logger.e(TAG, "XmppInfo = " + info);
			switch (info.businessType) {
			case TYPE_REGISTER: // 用户注册
				// 暂无处理
				break;
			case TYPE_COMPANY_AUTH: // 企业认证
				if (checkUser() && isCurUser(info.owner)) {
					handleAuthResult(info);
				}
				break;
			case TYPE_CONTRACT_SIGN:
			case TYPE_CONTRACT_ING:
			case TYPE_CONTRACT_EVALUATION:
			case TYPE_CONTRACT_CANCEL:
			case TYPE_CONTRACT_MAKE_MATCH:
			case TYPE_CONTRACT_SINGLE_DAF_CONFIRM:
			case TYPE_CONTRACT_DAF_CANCEL:
			case TYPE_CONTRACT_DAF_TIMEOUT:
			case TYPE_CONTRACT_BUYER_PAYFUNDS:
			case TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE:
			case TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE:
			case TYPE_CONTRACT_APPLY_ARBITRATION:
			case TYPE_CONTRACT_ARBITRATED_FINALESTIMATE:
			case TYPE_CONTRACT_PAYFUNDS_TIMEOUT:
			case TYPE_CONTRACT_OTHERS: // 合同模块
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

	/**
	 * 处理认证相关推送
	 * @param info
	 */
	private void handleAuthResult(XmppMsgInfo info) {
		String title = mContext.getString(R.string.notify_auth_title);
		String context = info.content;
		showNotification(PushNotifyID.BUSINESS_TYPE_COMPANY_AUTH, title, context, getMainPageIntent(GlobalAction.TipsAction.ACTION_VIEW_MY_PROFILE));

		// 更新认证结果
		ResultItem result = info.params;
		if (result != null) {
			// 更新认证状态
			ResultItem authItem = (ResultItem) result.get("authstatus");
			if (authItem != null) {
				boolean isAuth = authItem.getInt("val") == AuthStatusType.AUTH_SUCCESS.toValue();
				GlobalConfig.getInstance().setAuth(isAuth);
			}
			// 更新身份类型
			ResultItem profileItem = (ResultItem) result.get("ctype");
			if (profileItem != null) {
				ProfileType profileType = ProfileType.convert(result.getEnumValue("ctype", ProfileType.COMPANY.toValue()));
				GlobalConfig.getInstance().setProfleType(profileType);
			}
		}

		MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.ProfileMessageType.MSG_REFRESH_MY_PROFILE, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
	}

	/**
	 * 处理合同相关推送
	 * @param info
	 */
	private void handleContractInfo(XmppMsgInfo info) {
		String title = mContext.getString(R.string.notify_contract_title);
		String context = info.content;
		Intent intent = new Intent(mContext, ContractInfoActivityV2.class);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_CONTRACT_ID, info.businessId);
		intent.putExtra(GlobalAction.ContractAction.EXTRA_KEY_IS_GET_CONTRACT_MODEL, true);
		showNotification(PushNotifyID.BUSINESS_TYPE_CONTRACT_SIGN, title, context, intent);

		refreshBuyList(info); // 刷新找买找卖及供求列表
		refreshBuyInfo(info); // 刷新我的供求详情
		refreshContractInfo(info); // 刷新合同详情信息
		refreshContractList(info); // 刷新合同列表信息
	}

	/**
	 * 处理保证金相关推送
	 * @param info
	 */
	private void handleDepositInfo(XmppMsgInfo info) {
		String title = mContext.getString(R.string.notify_deposit_change_title);
		String context = info.content;
		showNotification(PushNotifyID.BUSINESS_TYPE_MONEY_CHANG_GUARANTY, title, context, getMainPageIntent(GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE));

		if (info.params != null) {
			GlobalConfig.getInstance().setDepositStatus(info.params.getBoolean("isGuarantyEnough"));
			GlobalConfig.getInstance().setDepositBalance(info.params.getDouble("balance"));
			//MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
			MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
		}
	}

	/**
	 * 处理货款相关推送
	 * @param info
	 */
	private void handlePaymentInfo(XmppMsgInfo info) {
		String title = mContext.getString(R.string.notify_payment_change_title);
		String context = info.content;
		showNotification(PushNotifyID.BUSINESS_TYPE_MONEY_CHANG_DEPOSIT, title, context, getMainPageIntent(GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE));

		if (info.params != null) {
			GlobalConfig.getInstance().setPaymentStatus(info.params.getBoolean("isGuarantyEnough"));
			GlobalConfig.getInstance().setPaymentBalance(info.params.getDouble("balance"));
			//MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
			MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
		}
	}

	/**
	 * 处理钱包相关推送
	 * @param info
	 */
	private void handlePurseInfo(XmppMsgInfo info) {
		String title = mContext.getString(R.string.notify_purse_change_title);
		String context = info.content;
		showNotification(PushNotifyID.BUSINESS_TYPE_MONEY_CHANG, title, context, getMainPageIntent(GlobalAction.TipsAction.ACTION_VIEW_MY_PURSE));

		if (info.params != null) {
			GlobalConfig.getInstance().setDepositBalance(info.params.getDouble("guarantybalance"));
			GlobalConfig.getInstance().setPaymentBalance(info.params.getDouble("depositbalance"));
			GlobalConfig.getInstance().setDepositStatus(info.params.getBoolean("isGuarantyEnough"));
			//GlobalConfig.getInstance().setPaymentStatus(info.params.getBoolean("isGuarantyEnough"));
			//MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_BALANCE_INFO, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
			MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.PurseMessageType.MSG_REFRESH_PURSE_INFO, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
		}
	}

	/**
	 * 处理离线推送
	 * @param info
	 */
	private void handleLogoutInfo(XmppMsgInfo info) {
		String title = mContext.getString(R.string.notify_offline_title);
		String context = info.content;
		showNotification(PushNotifyID.BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE, title, context, new Intent());

		MessageCenter.getInstance().sendEmptyMesage(DataConstants.GlobalMessageType.MSG_USER_OFFLINE);
	}

	/**
	 * 刷新找买找卖及供求列表
	 * @param info
	 */
	private void refreshBuyList(XmppMsgInfo info) {
		if (info.businessType == MsgBusinessType.TYPE_CONTRACT_MAKE_MATCH || info.businessType == MsgBusinessType.TYPE_CONTRACT_DAF_CANCEL
				|| info.businessType == MsgBusinessType.TYPE_CONTRACT_DAF_TIMEOUT) {
			MessageCenter.getInstance().sendEmptyMesageDelay(GlobalMessageType.BuyMessageType.MSG_REFRESH_BUY_LIST, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
		}
	}

	/**
	 * 刷新供求详情
	 * @param info
	 */
	private void refreshBuyInfo(XmppMsgInfo info) {
		if (info.businessType == MsgBusinessType.TYPE_CONTRACT_MAKE_MATCH || info.businessType == MsgBusinessType.TYPE_CONTRACT_DAF_CANCEL
				|| info.businessType == MsgBusinessType.TYPE_CONTRACT_DAF_TIMEOUT) {
			if (info.params != null) {
				String buyId = info.params.getString("fid");
				if (StringUtils.isNotEmpty(buyId)) {
					Logger.e(TAG, String.format("Buy id is %s, send refresh action!", buyId));
					Message refreshMsg = new Message();
					refreshMsg.what = GlobalMessageType.BuyMessageType.MSG_REFRESH_MY_BUY_INFO;
					RespInfo respInfo = new RespInfo();
					respInfo.strArg1 = buyId;
					refreshMsg.obj = respInfo;
					MessageCenter.getInstance().sendMessageDelay(refreshMsg, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
				} else {
					Logger.e(TAG, "Buy id is null, so ignore!");
				}
			} else {
				Logger.e(TAG, "Buy id is null, so ignore!");
			}
		}
	}

	/**
	 * 刷新合同详情
	 * @param info
	 */
	private void refreshContractInfo(XmppMsgInfo info) {
		Message refreshMsg = new Message();
		refreshMsg.what = GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_INFO;
		RespInfo respInfo = new RespInfo();
		respInfo.strArg1 = info.businessId;
		Logger.e(TAG, String.format("Contract id is %s, send refresh action!", info.businessId));
		refreshMsg.obj = respInfo;
		MessageCenter.getInstance().sendMessageDelay(refreshMsg, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
	}

	/**
	 * 刷新合同列表
	 * @param info
	 */
	private void refreshContractList(XmppMsgInfo info) {
		switch (info.businessType) {
		case TYPE_CONTRACT_MAKE_MATCH: // 撮合成功
		case TYPE_CONTRACT_SINGLE_DAF_CONFIRM: // 单方起草确认
		case TYPE_CONTRACT_DAF_TIMEOUT: // 起草超时
		case TYPE_CONTRACT_DAF_CANCEL: // 起草取消
			refreshContractList(ContractType.UNCONFIRMED);
			break;
		case TYPE_CONTRACT_SIGN: // 双方起草确认
			refreshContractList(ContractType.UNCONFIRMED, ContractType.ONGOING);
			break;
		case TYPE_CONTRACT_BUYER_PAYFUNDS: // 买方付款
		case TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE: // 申请结算
		case TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE: // 同意结算
		case TYPE_CONTRACT_EVALUATION: // 合同评价
		case TYPE_CONTRACT_APPLY_ARBITRATION: // 申请仲裁
		case TYPE_CONTRACT_ARBITRATED_FINALESTIMATE: // 仲裁结算
		case TYPE_CONTRACT_PAYFUNDS_TIMEOUT: // 预期未付款
			refreshContractList(ContractType.ONGOING);
			break;
		case TYPE_CONTRACT_ING: // 进行中
		case TYPE_CONTRACT_CANCEL: // 取消合同
		case TYPE_CONTRACT_OTHERS: // 其他
			refreshContractList(ContractType.ONGOING, ContractType.ENDED);
			break;
		default:
			refreshContractList(ContractType.UNCONFIRMED, ContractType.ONGOING, ContractType.ENDED);
			break;
		}
	}

	/**
	 * 更新合同列表
	 */
	private void refreshContractList(ContractType... typeList) {
		for (ContractType type : typeList) {
			Message refreshMsg = new Message();
			refreshMsg.what = GlobalMessageType.ContractMessageType.MSG_REFRESH_CONTRACT_LIST;
			refreshMsg.arg2 = type.toValue();
			MessageCenter.getInstance().sendMessageDelay(refreshMsg, GlobalConstants.CfgConstants.MESSAGE_REFRESH_DELAY_TIME);
		}
	}

	private Intent getMainPageIntent(String action) {
		Intent intent = new Intent(mContext, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalAction.TipsAction.EXTRA_DO_ACTION, action);
		return intent;
	}

	/**
	 * 显示推送通知栏信息
	 * @param id 通知栏id
	 * @param title 通知栏标题
	 * @param content 通知栏内容
	 * @param intent 通知栏点击事件
	 */
	private void showNotification(int id, String title, String content, Intent intent) {
		NotificationHelper.notifyEx(mContext, id, title, content, title, title, content, "", intent, Notification.FLAG_AUTO_CANCEL, PendingIntentType.ACTIVITY);
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
