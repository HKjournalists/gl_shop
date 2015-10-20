package com.glshop.net.ui.basic.notification;

/**
 * @Description : XMPP常量定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:56:43
 */
public final class NotificationConstants {

	/**
	 * 升级通知栏ID定义
	 */
	public interface UpgradeNotifyID {

		public static final int BASE = 0x1000;

		/** 升级包下载  */
		public static final int ID_UPGRADE_PKG_DOWNLOAD = BASE + 1;
	}

	/**
	 * 推送通知栏ID定义
	 */
	public interface PushNotifyID {

		public static final int BASE = 0x2000;

		/**
		 * 用户注册
		 */
		int BUSINESS_TYPE_REGISTER = BASE + 1;

		/**
		 * 企业资料认证
		 */
		int BUSINESS_TYPE_COMPANY_AUTH = BASE + 2;

		/**
		 * 企业收款人认证
		 */
		int BUSINESS_TYPE_COMPANY_PAYEE_AUTH = BASE + 3;

		/**
		 * 询单，供求信息
		 */
		int BUSINESS_TYPE_ORDER_FIND = BASE + 4;

		/**
		 * 合同签订
		 */
		int BUSINESS_TYPE_CONTRACT_SIGN = BASE + 5;

		/**
		 * 合同进行中
		 */
		int BUSINESS_TYPE_CONTRACT_ING = BASE + 6;

		/**
		 * 合同评价
		 */
		int BUSINESS_TYPE_CONTRACT_EVALUATION = BASE + 7;

		/**
		 * 保证金金额变动
		 */
		int BUSINESS_TYPE_MONEY_CHANG_GUARANTY = BASE + 8;

		/**
		 * 货款金额变动
		 */
		int BUSINESS_TYPE_MONEY_CHANG_DEPOSIT = BASE + 9;

		/**
		 * 钱包金额变动
		 */
		int BUSINESS_TYPE_MONEY_CHANG = BASE + 10;

		/**
		 * 业务类型：APP下载URL
		 */
		int BUSINESS_TYPE_APP_DOWNLOAD = BASE + 11;

		/**
		 * 业务类型：用户下线
		 */
		int BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE = BASE + 12;

	}

}
