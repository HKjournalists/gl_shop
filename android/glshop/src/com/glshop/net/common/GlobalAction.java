package com.glshop.net.common;

/**
 * @Description : 全局Action定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:54:57
 */
public interface GlobalAction {

	/**
	 * 通用Action定义
	 */
	public interface CommonAction {

		/**
		 * 选中的Tab
		 */
		public static final String EXTRA_KEY_SELECTED_TAB = "extra_key_selected_tab";

		/**
		 * 请求标识Invoker
		 */
		public static final String EXTRA_KEY_REQ_INVOKER = "extra_key_req_invoker";

		/**
		 * 请求返回错误码
		 */
		public static final String EXTRA_KEY_RESP_ERROR_CODE = "extra_key_resp_error_code";

		/**
		 * 请求返回错误信息
		 */
		public static final String EXTRA_KEY_RESP_ERROR_MESSAGE = "extra_key_resp_error_message";

		/**
		 * 请求返回数据信息
		 */
		public static final String EXTRA_KEY_RESP_DATA = "extra_key_resp_data";
	}

	/**
	 * 用户Action定义
	 */
	public interface UserAction {

		/**
		 * 注册手机号
		 */
		public static final String EXTRA_REG_ACCOUNT = "extra_reg_account";

		/**
		 * 用户手机号
		 */
		public static final String EXTRA_USER_ACCOUNT = "extra_user_account";

		/**
		 * 修改密码
		 */
		public static final String EXTRA_IS_MODIFY_PWD = "extra_is_modify_pwd";

		/**
		 * 用户注销
		 */
		public static final String EXTRA_IS_USER_LOGOUT = "extra_is_user_logout";
		
		/**
		 * 跳转个人资料页面
		 */
		public static final String EXTRA_GOTO_MYPROFILE = "extra_is_goto_myprofile";

	}

	/**
	 * 买卖Action定义
	 */
	public interface BuyAction {

		/**
		 * 选中的Tab
		 */
		public static final String EXTRA_KEY_SELECTED_TAB = "extra_key_selected_tab";

		/**
		 * 今日报价列表
		 */
		public static final String EXTRA_KEY_PRICE_FORECAST_DATA = "extra_key_price_forcecast_data";

		/**
		 * 价格预测列表
		 */
		public static final String EXTRA_KEY_TODAY_PRICE_DATA = "extra_key_today_price_data";

		/**
		 * 买卖信息列表
		 */
		public static final String EXTRA_KEY_BUYS_DATA = "extra_key_buys_data";

		/**
		 * 买卖信息类型
		 */
		public static final String EXTRA_KEY_BUY_INFO_TYPE = "extra_key_buy_info_type";

		/**
		 * 查看买卖信息详情类型
		 */
		public static final String EXTRA_KEY_VIEW_BUY_INFO_TYPE = "extra_key_view_buy_info_type";

		/**
		 * 发布信息ID
		 */
		public static final String EXTRA_KEY_BUY_ID = "extra_key_buy_id";

		/**
		 * 是否是修改发布信息
		 */
		public static final String EXTRA_KEY_IS_MODIFY_BUY_INFO = "extra_key_is_modify_buy_info";

		/**
		 * 修改的发布信息
		 */
		public static final String EXTRA_KEY_MODIFY_BUY_INFO = "extra_key_modify_buy_info";

		/**
		 * 预览的发布信息
		 */
		public static final String EXTRA_KEY_PREV_BUY_INFO = "extra_key_prev_buy_info";

		/**
		 * 货物信息类型
		 */
		public static final String EXTRA_KEY_PRODUCT_TYPE = "extra_key_product_type";

		/**
		 * 货物规格信息
		 */
		public static final String EXTRA_KEY_PRODUCT_INFO = "extra_key_product_info";

		/**
		 * Fagment Layout ID
		 */
		public static final String EXTRA_KEY_FRAGMENT_LAYOUT_ID = "extra_key_fragment_layout_id";

	}

	/**
	 * 合同Action定义
	 */
	public interface ContractAction {

		/**
		 * Fagment Layout ID
		 */
		public static final String EXTRA_KEY_FRAGMENT_LAYOUT_ID = "extra_key_fragment_layout_id";

		/**
		 * 选中的Tab
		 */
		public static final String EXTRA_KEY_SELECTED_TAB = "extra_key_selected_tab";

		/**
		 * 合同信息列表
		 */
		public static final String EXTRA_KEY_CONTRACTS_DATA = "extra_key_contracts_data";

		/**
		 * 合同操作记录列表
		 */
		public static final String EXTRA_KEY_CONTRACT_HISTORY_DATA = "extra_key_contract_history_data";

		/**
		 * 合同ID
		 */
		public static final String EXTRA_KEY_CONTRACT_ID = "extra_key_contract_id";

		/**
		 * 合同类型
		 */
		public static final String EXTRA_KEY_CONTRACT_TYPE = "extra_key_contract_type";

		/**
		 * 合同信息
		 */
		public static final String EXTRA_KEY_CONTRACT_INFO = "extra_key_contract_info";

		/**
		 * 账单结算信息
		 */
		public static final String EXTRA_KEY_BILL_INFO = "extra_key_bill_info";

		/**
		 * 编辑单价信息
		 */
		public static final String EXTRA_KEY_EDIT_UNIT_PRICE = "extra_key_edit_unit_price";

		/**
		 * 合同评价信息
		 */
		public static final String EXTRA_KEY_EVALUATION_DESCRIPTION = "extra_key_evaluation_description";

		/**
		 * 对方是否已确认
		 */
		public static final String EXTRA_KEY_IS_OTHER_CONFIRM = "extra_key_is_other_confirm";

		/**
		 * 对方确认时间
		 */
		public static final String EXTRA_KEY_OTHER_CONFIRM_DATETIME = "extra_key_other_confirm_datetime";

	}

	/**
	 * 钱包Action定义
	 */
	public interface PurseAction {

		/**
		 * 钱包类型
		 */
		public static final String EXTRA_KEY_PURSE_TYPE = "extra_key_purse_type";

		/**
		 * 钱包信息
		 */
		public static final String EXTRA_KEY_PURSE_INFO = "extra_key_purse_info";

		/**
		 * 流水类型
		 */
		public static final String EXTRA_KEY_DEAL_TYPE = "extra_key_deal_type";

		/**
		 * 交易流水ID
		 */
		public static final String EXTRA_KEY_DEAL_ID = "extra_key_deal_id";

		/**
		 * 钱包支付类型
		 */
		public static final String EXTRA_KEY_PAY_TYPE = "extra_key_pay_type";

		/**
		 * 收款人信息
		 */
		public static final String EXTRA_KEY_PAYEE_INFO = "extra_key_payee_info";

		/**
		 * 可转余额信息
		 */
		public static final String EXTRA_KEY_ROLLOUT_MONEY = "extra_key_rollout_money";

		/**
		 * 钱包充值或货款支付金额
		 */
		public static final String EXTRA_KEY_PAY_MONEY = "extra_key_pay_money";

	}

	/**
	 * 资料Action定义
	 */
	public interface ProfileAction {

		/**
		 * 企业ID
		 */
		public static final String EXTRA_KEY_COMPANY_ID = "extra_key_company_id";

		/**
		 * 认证信息
		 */
		public static final String EXTRA_KEY_AUTH_INFO = "extra_key_auth_info";

		/**
		 * 联系人信息
		 */
		public static final String EXTRA_KEY_CONTACT_INFO = "extra_key_contact_info";

		/**
		 * 卸货地址信息
		 */
		public static final String EXTRA_KEY_ADDR_INFO = "extra_key_addr_info";

		/**
		 * 企业介绍信息
		 */
		public static final String EXTRA_KEY_COMPANY_INTRO_INFO = "extra_key_company_intro_info";

	}

	/**
	 * 消息Action定义
	 */
	public interface MessageAction {

		/**
		 * 消息信息ID
		 */
		public static final String EXTRA_KEY_MESSAGE_ID = "extra_key_message_id";

		/**
		 * 消息信息
		 */
		public static final String EXTRA_KEY_MESSAGE_INFO = "extra_key_message_info";

	}

	/**
	 * 设置Action定义
	 */
	public interface SettingAction {

		/**
		 * 常见问题标题
		 */
		public static final String EXTRA_KEY_QA_TITLE = "extra_key_qa_title";

		/**
		 * 常见问题内容
		 */
		public static final String EXTRA_KEY_QA_CONTENT = "extra_key_qa_content";

	}

	/**
	 * 升级Action定义
	 */
	public interface UpgradeAction {

		/**
		 * 已下载大小
		 */
		public static final String EXTRA_KEY_CUR_LEN = "extra_key_cur_len";

		/**
		 * 总大小
		 */
		public static final String EXTRA_KEY_TOTAL_LEN = "extra_key_total_len";

	}

	/**
	 * 浏览图片、协议
	 */
	public interface BrowserAction {

		/**
		 * 浏览标题
		 */
		public static final String EXTRA_BROWSE_TITLE = "extra_browse_title";

		/**
		 * 浏览协议URL
		 */
		public static final String EXTRA_BROWSE_PROTOCOL_URL = "extra_browse_protocol_url";

		/**
		 * 浏览图片信息
		 */
		public static final String EXTRA_BROWSE_PICTURE_INFO = "extra_browse_picture_info";

	}

	/**
	 * 操作提示Action定义
	 */
	public interface TipsAction {

		/**
		 * 操作提示Info
		 */
		public static final String EXTRA_KEY_TIPS_INFO = "extra_key_tips_info";

		public static final String EXTRA_TIPS_ACTION = "extra_tips_action";

		public static final String EXTRA_TIPS_PAGE_TITLE = "extra_tips_page_title";

		public static final String EXTRA_TIPS_TITLE = "extra_tips_title";

		public static final String EXTRA_TIPS_CONTENT = "extra_tips_content";

		public static final String EXTRA_TIPS_ACTION_TEXT = "extra_tips_action_text";

		public static final String EXTRA_DO_ACTION = "extra_do_action";

		/**
		 * 修改密码、找回密码成功跳转到登录界面
		 */
		public static final String ACTION_USER_LOGIN = "glshop.intent.action.USER_LOGIN";

		/**
		 * 商城界面
		 */
		public static final String ACTION_VIEW_SHOP = "glshop.intent.action.VIEW_SHOP";

		/**
		 * 点击感兴趣后，跳转到找买找卖界面
		 */
		public static final String ACTION_VIEW_FIND_BUY = "glshop.intent.action.VIEW_FIND_BUY";

		/**
		 * 发布成功，跳转到我的供求界面
		 */
		public static final String ACTION_VIEW_MY_BUY = "glshop.intent.action.VIEW_MY_BUY";

		/**
		 * 跳转到我的合同界面
		 */
		public static final String ACTION_VIEW_MY_CONTRACT = "glshop.intent.action.VIEW_MY_CONTRACT";

		/**
		 * 跳转到保证金充值界面
		 */
		public static final String ACTION_RECHARGE_DEPOSIT = "glshop.intent.action.RECHARGE_DEPOSIT";

		/**
		 * 跳转到货款充值界面
		 */
		public static final String ACTION_RECHARGE_PAYMENT = "glshop.intent.action.RECHARGE_PAYMENT";

		/**
		 * 充值、提现成功，跳转到钱包界面
		 */
		public static final String ACTION_VIEW_MY_PURSE = "glshop.intent.action.VIEW_MY_PURSE";

		/**
		 * 跳转到我的资料界面
		 */
		public static final String ACTION_VIEW_MY_PROFILE = "glshop.intent.action.VIEW_MY_PROFILE";

	}

}
