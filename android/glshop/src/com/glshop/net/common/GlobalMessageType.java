package com.glshop.net.common;

/**
 * @Description : 全局消息类型定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:57:11
 */
public interface GlobalMessageType {

	/**
	 * 通用模块消息类型
	 */
	public interface CommonMessageType {

		int BASE = 0x10000000;

		/** 开始倒计时 */
		int MSG_TIMER_START = BASE + 1;

		/** 倒计时进行中 */
		int MSG_TIMER_PROGRESS = BASE + 2;

		/** 倒计时结束 */
		int MSG_TIMER_STOP = BASE + 3;

		/** 大图浏览时显示下一张 */
		int MSG_IMAGE_BROWSE_NEXT = BASE + 4;

		/** 大图浏览时单击 */
		int MSG_IMAGE_BROWSE_SINGLE_CLICK = BASE + 5;

		/** 文件上传成功 */
		int MSG_FILE_UPLOAD_SUCCESS = BASE + 6;

		/** 文件上传失败 */
		int MSG_FILE_UPLOAD_FAILED = BASE + 7;

		/** 文件下载成功 */
		int MSG_FILE_DOWNLOAD_SUCCESS = BASE + 8;

		/** 文件下载失败 */
		int MSG_FILE_DOWNLOAD_FAILED = BASE + 9;

	}

	/**
	 * 用户模块消息类型
	 */
	public interface UserMessageType {

		int BASE = 0x20000000;

		/** 启动完成 */
		int MSG_BOOT_COMPLETE = BASE + 1;

		/** 登录成功 */
		int MSG_LOGIN_SUCCESS = BASE + 2;

		/** 登录失败 */
		int MSG_LOGIN_FAILED = BASE + 3;

		/** 注销成功 */
		int MSG_LOGOUT_SUCCESS = BASE + 4;

		/** 注销失败 */
		int MSG_LOGOUT_FAILED = BASE + 5;

		/** 注册用户成功 */
		int MSG_REG_USER_SUCCESS = BASE + 6;

		/** 注册用户失败 */
		int MSG_REG_USER_FAILED = BASE + 7;

		/** 获取图形验证码成功 */
		int MSG_GET_IMG_VERFIYCODE_SUCCESS = BASE + 8;

		/** 获取图形验证码失败 */
		int MSG_GET_IMG_VERFIYCODE_FAILED = BASE + 9;

		/** 获取短信验证码成功 */
		int MSG_GET_SMS_VERFIYCODE_SUCCESS = BASE + 10;

		/** 获取短信验证码失败 */
		int MSG_GET_SMS_VERFIYCODE_FAILED = BASE + 11;

		/** 检验短信验证码成功 */
		int MSG_VALID_SMS_VERFIYCODE_SUCCESS = BASE + 12;

		/** 检验短信验证码失败 */
		int MSG_VALID_SMS_VERFIYCODE_FAILED = BASE + 13;

		/** 检验图形验证码成功 */
		int MSG_VALID_IMG_VERFIYCODE_SUCCESS = BASE + 14;

		/** 检验图形验证码失败 */
		int MSG_VALID_IMG_VERFIYCODE_FAILED = BASE + 15;

		/** 找回密码成功 */
		int MSG_RESET_PASSWORD_SUCCESS = BASE + 16;

		/** 找回密码失败 */
		int MSG_RESET_PASSWORD_FAILED = BASE + 17;

		/** 修改密码成功 */
		int MSG_MODIFY_PASSWORD_SUCCESS = BASE + 18;

		/** 修改密码失败 */
		int MSG_MODIFY_PASSWORD_FAILED = BASE + 19;

		/** 刷新Token成功 */
		int MSG_REFRESH_TOKEN_SUCCESS = BASE + 20;

		/** 刷新Token失败 */
		int MSG_REFRESH_TOKEN_FAILED = BASE + 21;

		/** 检验注册用户成功 */
		int MSG_CHECK_REG_USER_SUCCESS = BASE + 22;

		/** 检验注册用户失败 */
		int MSG_CHECK_REG_USER_FAILED = BASE + 23;

	}

	/**
	 * 找买找卖、我的供求模块消息类型
	 */
	public interface BuyMessageType {

		int BASE = 0x30000000;

		/** 获取找买找卖信息列表成功 */
		int MSG_GET_BUYS_SUCCESS = BASE + 1;

		/** 获取找买找卖信息列表失败 */
		int MSG_GET_BUYS_FAILED = BASE + 2;

		/** 获取买卖信息详情成功 */
		int MSG_GET_BUY_INFO_SUCCESS = BASE + 3;

		/** 获取买卖信息详情失败 */
		int MSG_GET_BUY_INFO_FAILED = BASE + 4;

		/** 获取我的供求信息列表成功 */
		int MSG_GET_MYBUYS_SUCCESS = BASE + 5;

		/** 获取我的供求信息列表失败 */
		int MSG_GET_MYBUYS_FAILED = BASE + 6;

		/** 发布供求信息成功 */
		int MSG_PUB_BUY_INFO_SUCCESS = BASE + 7;

		/** 发布供求信息失败 */
		int MSG_PUB_BUY_INFO_FAILED = BASE + 8;

		/** 重新发布供求信息成功 */
		int MSG_REPUB_BUY_INFO_SUCCESS = BASE + 9;

		/** 重新发布供求信息失败 */
		int MSG_REPUB_BUY_INFO_FAILED = BASE + 10;

		/** 取消供求信息发布成功 */
		int MSG_UNDO_PUB_BUY_INFO_SUCCESS = BASE + 11;

		/** 取消供求信息发布失败 */
		int MSG_UNDO_PUB_BUY_INFO_FAILED = BASE + 12;

		/** 删除供求信息发布成功 */
		int MSG_DELETE_PUB_BUY_INFO_SUCCESS = BASE + 13;

		/** 删除供求信息发布失败 */
		int MSG_DELETE_PUB_BUY_INFO_FAILED = BASE + 14;

		/** 更新供求信息成功 */
		int MSG_UPDATE_PUB_BUY_INFO_SUCCESS = BASE + 15;

		/** 更新供求信息失败 */
		int MSG_UPDATE_PUB_BUY_INFO_FAILED = BASE + 16;

		/** 提交想交易请求成功 */
		int MSG_WANT_TO_DEAL_SUCCESS = BASE + 17;

		/** 提交想交易请求失败 */
		int MSG_WANT_TO_DEAL_FAILED = BASE + 18;

		/** 获取今日报价列表成功 */
		int MSG_GET_TODAY_PRICE_SUCCESS = BASE + 19;

		/** 获取今日报价列表失败 */
		int MSG_GET_TODAY_PRICE_FAILED = BASE + 20;

		/** 获取价格趋势列表成功 */
		int MSG_GET_PRICE_FORECAST_SUCCESS = BASE + 21;

		/** 获取价格趋势列表失败 */
		int MSG_GET_PRICE_FORECAST_FAILED = BASE + 22;

		/** 刷新我的供求倒计时时间 */
		int MSG_REFRESH_BUY_WAIT_TIME = BASE + 23;

		/** 刷新找买找卖和我的供求列表 */
		int MSG_REFRESH_BUY_LIST = BASE + 24;
		
		/** 刷新找买找卖和我的供求列表并重置过滤条件 */
		int MSG_REFRESH_BUY_LIST_WITH_RESET_FILTER = BASE + 25;
		
		/** 刷新供求详情信息 */
		int MSG_REFRESH_MY_BUY_INFO = BASE + 26;

	}

	/**
	 * 我的合同模块消息类型
	 */
	public interface ContractMessageType {

		int BASE = 0x40000000;

		/** 获取我的合同信息列表成功 */
		int MSG_GET_CONTRACTS_SUCCESS = BASE + 1;

		/** 获取我的合同信息列表失败 */
		int MSG_GET_CONTRACTS_FAILED = BASE + 2;

		/** 刷新我的合同倒计时时间 */
		int MSG_REFRESH_CONTRACT_WAIT_TIME = BASE + 3;

		/** 获取合同模板详情成功 */
		int MSG_GET_CONTRACT_MODEL_SUCCESS = BASE + 4;

		/** 获取合同模板详情失败 */
		int MSG_GET_CONTRACT_MODEL_FAILED = BASE + 5;

		/** 获取合同操作历史记录成功 */
		int MSG_GET_CONTRACT_OPR_HISTORY_SUCCESS = BASE + 6;

		/** 获取合同操作历史记录失败 */
		int MSG_GET_CONTRACT_OPR_HISTORY_FAILED = BASE + 7;

		/** 获取我的待付货款合同信息列表成功 */
		int MSG_GET_TO_PAY_CONTRACTS_SUCCESS = BASE + 8;

		/** 获取我的待付货款合同信息列表失败 */
		int MSG_GET_TO_PAY_CONTRACTS_FAILED = BASE + 9;

		/** 获取合同评价信息列表成功 */
		int MSG_GET_CONTRACTS_EVALUATION_SUCCESS = BASE + 10;

		/** 获取合同评价信息列表失败 */
		int MSG_GET_CONTRACTS_EVALUATION_FAILED = BASE + 11;

		/** 获取企业评价信息列表成功 */
		int MSG_GET_COMPANY_EVALUATION_SUCCESS = BASE + 12;

		/** 获取企业评价信息列表失败 */
		int MSG_GET_COMPANY_EVALUATION_FAILED = BASE + 13;

		/** 获取进行中和已结束合同详情信息成功 */
		int MSG_GET_CONTRACT_INFO_SUCCESS = BASE + 14;

		/** 获取进行中和已结束合同详情信息失败 */
		int MSG_GET_CONTRACT_INFO_FAILED = BASE + 15;

		/** 待确认合同签订成功 */
		int MSG_AGREE_CONTRACT_SIGN_SUCCESS = BASE + 16;

		/** 待确认合同签订失败 */
		int MSG_AGREE_CONTRACT_SIGN_FAILED = BASE + 17;

		/** 刷新合同列表 */
		int MSG_REFRESH_CONTRACT_LIST = BASE + 18;

		/** 合同线上支付成功 */
		int MSG_CONTRACT_PAY_ONLINE_SUCCESS = BASE + 19;

		/** 合同线上支付失败 */
		int MSG_CONTRACT_PAY_ONLINE_FAILED = BASE + 20;

		/** 合同线下支付成功 */
		int MSG_CONTRACT_PAY_OFFLINE_SUCCESS = BASE + 21;

		/** 合同线下支付失败 */
		int MSG_CONTRACT_PAY_OFFLINE_FAILED = BASE + 22;

		/** 合同验收或同意议价成功 */
		int MSG_CONTRACT_ACCEPTANCE_SUCCESS = BASE + 23;

		/** 合同验收或同意议价失败 */
		int MSG_CONTRACT_ACCEPTANCE_FAILED = BASE + 24;

		/** 提交议价成功 */
		int MSG_CONTRACT_NEGOTIATE_SUCCESS = BASE + 25;

		/** 提交议价失败 */
		int MSG_CONTRACT_NEGOTIATE_FAILED = BASE + 26;

		/** 取消合同成功 */
		int MSG_CONTRACT_CANCEL_SUCCESS = BASE + 27;

		/** 取消合同失败 */
		int MSG_CONTRACT_CANCEL_FAILED = BASE + 28;

		/** 同意取消合同成功 */
		int MSG_AGREE_CONTRACT_CANCEL_SUCCESS = BASE + 29;

		/** 同意取消合同失败 */
		int MSG_AGREE_CONTRACT_CANCEL_FAILED = BASE + 30;

		/** 联系客服成功 */
		int MSG_CONTACT_CUSTOM_SERVICE_SUCCESS = BASE + 31;

		/** 联系客服失败 */
		int MSG_CONTACT_CUSTOM_SERVICE_FAILED = BASE + 32;

		/** 确认卸货成功 */
		int MSG_COMFIRM_DISCHARGE_SUCCESS = BASE + 33;

		/** 确认卸货失败 */
		int MSG_COMFIRM_DISCHARGE_FAILED = BASE + 34;

		/** 确认收货成功 */
		int MSG_COMFIRM_RECEIPT_SUCCESS = BASE + 35;

		/** 确认收货失败 */
		int MSG_COMFIRM_RECEIPT_FAILED = BASE + 36;

		/** 合同评价成功 */
		int MSG_CONTRACT_EVALUATE_SUCCESS = BASE + 37;

		/** 合同评价失败 */
		int MSG_CONTRACT_EVALUATE_FAILED = BASE + 38;

		/** 合同确认成功 */
		int MSG_MULTI_COMFIRM_CONTRACT_SUCCESS = BASE + 39;

		/** 合同确认失败 */
		int MSG_MULTI_COMFIRM_CONTRACT_FAILED = BASE + 40;

		/** 合同申请仲裁成功 */
		int MSG_APPLY_ARBITARTE_SUCCESS = BASE + 41;

		/** 合同申请仲裁失败 */
		int MSG_APPLY_ARBITARTE_FAILED = BASE + 42;

		/** 取消合同成功 */
		int MSG_CONTRACT_MULTI_CANCEL_SUCCESS = BASE + 43;

		/** 取消合同失败 */
		int MSG_CONTRACT_MULTI_CANCEL_FAILED = BASE + 44;

		/** 刷新支付倒计时时间 */
		int MSG_REFRESH_PAY_WAIT_TIME = BASE + 45;

		/** 刷新合同信息 */
		int MSG_REFRESH_CONTRACT_INFO = BASE + 46;

	}

	/**
	 * 我的钱包模块消息类型
	 */
	public interface PurseMessageType {

		int BASE = 0x50000000;

		/** 获取钱包基本信息成功 */
		int MSG_GET_PURSE_INFO_SUCCESS = BASE + 1;

		/** 获取钱包基本信息失败 */
		int MSG_GET_PURSE_INFO_FAILED = BASE + 2;

		/** 获取钱包交易流水列表成功 */
		int MSG_GET_DEALS_SUCCESS = BASE + 3;

		/** 获取钱包交易流水列表失败 */
		int MSG_GET_DEALS_FAILED = BASE + 4;

		/** 获取钱包交易流水详情成功 */
		int MSG_GET_DEAL_INFO_SUCCESS = BASE + 5;

		/** 获取钱包交易流水详情失败 */
		int MSG_GET_DEAL_INFO_FAILED = BASE + 6;

		/** 钱包取现成功 */
		int MSG_ROLL_OUT_SUCCESS = BASE + 7;

		/** 钱包取现失败 */
		int MSG_ROLL_OUT_FAILED = BASE + 8;

		/** 获取选择收款人列表成功 */
		int MSG_GET_SELECT_PAYEE_LIST_SUCCESS = BASE + 9;

		/** 获取选择收款人列表失败 */
		int MSG_GET_SELECT_PAYEE_LIST_FAILED = BASE + 10;

		/** 获取管理收款人列表成功 */
		int MSG_GET_MGR_PAYEE_LIST_SUCCESS = BASE + 11;

		/** 获取管理收款人列表失败 */
		int MSG_GET_MGR_PAYEE_LIST_FAILED = BASE + 12;

		/** 新增收款人成功 */
		int MSG_ADD_PAYEE_INFO_SUCCESS = BASE + 13;

		/** 新增收款人失败 */
		int MSG_ADD_PAYEE_INFO_FAILED = BASE + 14;

		/** 修改收款人成功 */
		int MSG_UPDATE_PAYEE_INFO_SUCCESS = BASE + 15;

		/** 修改收款人失败 */
		int MSG_UPDATE_PAYEE_INFO_FAILED = BASE + 16;

		/** 删除收款人成功 */
		int MSG_DELETE_PAYEE_INFO_SUCCESS = BASE + 17;

		/** 删除收款人失败 */
		int MSG_DELETE_PAYEE_INFO_FAILED = BASE + 18;

		/** 设置默认收款人成功 */
		int MSG_SET_DEFAULT_PAYEE_INFO_SUCCESS = BASE + 19;

		/** 设置默认收款人失败 */
		int MSG_SET_DEFAULT_PAYEE_INFO_FAILED = BASE + 20;

		/** 线下付款或充值成功 */
		int MSG_RECHARGE_OFFLINE_SUCCESS = BASE + 21;

		/** 线下付款或充值失败 */
		int MSG_RECHARGE_OFFLINE_FAILED = BASE + 22;

		/** 钱包转款至保证金成功 */
		int MSG_ROLLOUT_DEPOSIT_SUCCESS = BASE + 23;

		/** 钱包转款至保证金失败 */
		int MSG_ROLLOUT_DEPOSIT_FAILED = BASE + 24;

		/** 刷新钱包页面 */
		int MSG_REFRESH_PURSE_INFO = BASE + 25;

		/** 刷新钱包金额消息 */
		int MSG_REFRESH_PURSE_BALANCE_INFO = BASE + 26;
		
		/** 获取第三方支付流水号成功 */
		int MSG_GET_PAY_TN_SUCCESS = BASE + 27;
		
		/** 获取第三方支付流水号失败 */
		int MSG_GET_PAY_TN_FAILED = BASE + 28;
		
		/** 上报支付结果成功 */
		int MSG_RPT_PAY_RESULT_SUCCESS = BASE + 29;
		
		/** 上报支付结果失败 */
		int MSG_RPT_PAY_RESULT_FAILED = BASE + 30;

	}

	/**
	 * 资料模块消息类型
	 */
	public interface ProfileMessageType {

		int BASE = 0x60000000;

		/** 获取我的资料信息成功 */
		int MSG_GET_MY_PROFILE_INFO_SUCCESS = BASE + 1;

		/** 获取我的资料信息成功 */
		int MSG_GET_MY_PROFILE_INFO_FAILED = BASE + 2;

		/** 获取对方的资料信息成功 */
		int MSG_GET_OTHER_PROFILE_INFO_SUCCESS = BASE + 3;

		/** 获取对方的资料信息成功 */
		int MSG_GET_OTHER_PROFILE_INFO_FAILED = BASE + 4;

		/** 获取卸货地址列表成功 */
		int MSG_GET_DISCHARGE_ADDR_LIST_SUCCESS = BASE + 5;

		/** 获取卸货地址列表失败 */
		int MSG_GET_DISCHARGE_ADDR_LIST_FAILED = BASE + 6;

		/** 添加卸货地址成功 */
		int MSG_ADD_ADDR_SUCCESS = BASE + 7;

		/** 添加卸货地址失败 */
		int MSG_ADD_ADDR_FAILED = BASE + 8;

		/** 更新卸货地址成功 */
		int MSG_UPDATE_ADDR_SUCCESS = BASE + 9;

		/** 更新卸货地址失败 */
		int MSG_UPDATE_ADDR_FAILED = BASE + 10;

		/** 删除卸货地址成功 */
		int MSG_DELETE_ADDR_SUCCESS = BASE + 11;

		/** 删除卸货地址失败 */
		int MSG_DELETE_ADDR_FAILED = BASE + 12;

		/** 设置默认卸货地址成功 */
		int MSG_SET_DEFAULT_ADDR_SUCCESS = BASE + 13;

		/** 设置默认卸货地址失败 */
		int MSG_SET_DEFAULT_ADDR_FAILED = BASE + 14;

		/** 获取联系人列表成功 */
		int MSG_GET_CONTACT_LIST_SUCCESS = BASE + 15;

		/** 获取联系人列表失败 */
		int MSG_GET_CONTACT_LIST_FAILED = BASE + 16;

		/** 更新联系人信息成功 */
		int MSG_UPDATE_CONTACT_INFO_SUCCESS = BASE + 17;

		/** 更新联系人信息失败 */
		int MSG_UPDATE_CONTACT_INFO_FAILED = BASE + 18;

		/** 更新企业介绍信息成功 */
		int MSG_UPDATE_COMPANY_INTRO_INFO_SUCCESS = BASE + 19;

		/** 更新企业介绍信息失败 */
		int MSG_UPDATE_COMPANY_INTRO_INFO_FAILED = BASE + 20;

		/** 提交认证信息成功 */
		int MSG_SUBMIT_AUTH_INFO_SUCCESS = BASE + 21;

		/** 提交认证信息失败*/
		int MSG_SUBMIT_AUTH_INFO_FAILED = BASE + 22;

		/** 刷新个人资料信息*/
		int MSG_REFRESH_MY_PROFILE = BASE + 23;

		/** 获取交易地域地址列表成功 */
		int MSG_GET_TRADE_ADDR_LIST_SUCCESS = BASE + 24;

		/** 获取交易地域地址列表失败 */
		int MSG_GET_TRADE_ADDR_LIST_FAILED = BASE + 25;

		/** 获取卸货地址详情成功 */
		int MSG_GET_DISCHARGE_ADDR_INFO_SUCCESS = BASE + 26;

		/** 获取卸货地址详情失败 */
		int MSG_GET_DISCHARGE_ADDR_INFO_FAILED = BASE + 27;

		/** 获取合同地址详情成功 */
		int MSG_GET_CONTRACT_ADDR_INFO_SUCCESS = BASE + 28;

		/** 获取合同地址详情失败 */
		int MSG_GET_CONTRACT_ADDR_INFO_FAILED = BASE + 29;

	}

	/**
	 * 消息中心类型
	 */
	public interface MsgCenterMessageType {

		int BASE = 0x70000000;

		/** 获取本地未读消息个数成功 */
		int MSG_GET_LOCAL_UNREAD_NUM_SUCCESS = BASE + 1;

		/** 获取本地未读消息个数失败 */
		int MSG_GET_LOCAL_UNREAD_NUM_FAILED = BASE + 2;

		/** 获取云端未读消息个数成功 */
		int MSG_GET_SERVER_UNREAD_NUM_SUCCESS = BASE + 3;

		/** 获取云端未读消息个数失败 */
		int MSG_GET_SERVER_UNREAD_NUM_FAILED = BASE + 4;

		/** 获取消息列表成功 */
		int MSG_GET_MESSAGE_LIST_SUCCESS = BASE + 5;

		/** 获取消息列表失败 */
		int MSG_GET_MESSAGE_LIST_FAILED = BASE + 6;

		/** 获取消息详情成功 */
		int MSG_GET_MESSAGE_INFO_SUCCESS = BASE + 7;

		/** 获取消息详情失败 */
		int MSG_GET_MESSAGE_INFO_FAILED = BASE + 8;

		/** 上报消息已读成功 */
		int MSG_REPORT_MESSAGE_READED_SUCCESS = BASE + 9;

		/** 上报消息已读失败 */
		int MSG_REPORT_MESSAGE_READED_FAILED = BASE + 10;

		/** 刷新未读列表数目 */
		int MSG_REFRESH_UNREAD_MSG_NUM = BASE + 11;
	}

	/**
	 * 设置模块消息类型
	 */
	public interface SettingMessageType {

		int BASE = 0x80000000;

	}

	/**
	 * 升级模块消息类型
	 */
	public interface UpgradeMessageType {

		int BASE = 0x90000000;

		/** 获取升级信息成功 */
		int MSG_GET_UPGRADE_INFO_SUCCESS = BASE + 1;

		/** 获取升级信息失败 */
		int MSG_GET_UPGRADE_INFO_FAILED = BASE + 2;

		/** 开始下载 */
		int MSG_START_DOWNLOAD = BASE + 3;

		/** 更新下载进度 */
		int MSG_UPDATE_PROGRESS = BASE + 4;

		/** 下载成功 */
		int MSG_DOWNLOAD_SUCCESS = BASE + 5;

		/** 下载失败 */
		int MSG_DOWNLOAD_FAILED = BASE + 6;

		/** 下载取消 */
		int MSG_DOWNLOAD_CANCELED = BASE + 7;
		
		/** 检测升级版本 */
		int MSG_CHECK_UPGRADE_INFO = BASE + 8;
		
		/**系统退出*/
		int MSG_EXIT = BASE + 9;

	}

	/**
	 * 系统同步配置模块消息类型
	 */
	public interface SysCfgMessageType {

		int BASE = 0xA0000000;

		/** 获取系统参数信息成功 */
		int MSG_GET_SYSCFG_INFO_SUCCESS = BASE + 1;

		/** 获取系统参数信息失败 */
		int MSG_GET_SYSCFG_INFO_FAILED = BASE + 2;

		/** 获取区域列表信息成功 */
		int MSG_GET_AREA_ADDR_LIST_SUCCESS = BASE + 3;

		/** 获取区域列表信息成功 */
		int MSG_GET_AREA_ADDR_LIST_FAILED = BASE + 4;

	}

	/**
	 * 网络状态消息类型
	 */
	public interface NetworkMessageType {

		int BASE = 0xB0000000;

		/** 已连接 */
		int NET_STATUS_TYPE_CONNECTED = BASE + 1;

		/** 已断开 */
		int NET_STATUS_TYPE_DISCONNECTED = BASE + 2;

		/** WiFi网络 */
		int NET_STATUS_TYPE_WIFI = BASE + 3;

		/** 2G网络 */
		int NET_STATUS_TYPE_2G = BASE + 4;

		/** 3G网络 */
		int NET_STATUS_TYPE_3G = BASE + 5;

		/** 4G网络 */
		int NET_STATUS_TYPE_4G = BASE + 6;

	}

	/**
	 * 菜单类型
	 */
	public interface MenuType {

		int BASE = 0x1000;

		/** 未知 */
		int UNKNOWN = BASE + 1;

		/** 选择信息发布类型 */
		int SELECT_BUY_TYPE = BASE + 2;

		/** 选择货物信息类型 */
		int SELECT_PRODUCT_TYPE = BASE + 3;

		/** 选择货物分类 */
		int SELECT_PRODUCT_CATEGORY = BASE + 4;

		/** 选择货物规格 */
		int SELECT_PRODUCT_SPEC = BASE + 5;

		/** 选择卸货地址指定方式 */
		int SELECT_DISCHARGE_ADDR_TYPE = BASE + 6;

		/** 选择上传图片方式 */
		int SELECT_UPLOAD_PIC_TYPE = BASE + 7;

		/** 选择是否发布多地域 */
		int SELECT_PUB_MORE_AREA_TYPE = BASE + 8;

		/** 选择交易地域 */
		int SELECT_TRADE_AREA_TYPE = BASE + 9;

		/** 选择交易时间 */
		int SELECT_TRADE_DATE_TYPE = BASE + 10;

		/** 选择货物种类 */
		int SELECT_TRADE_PRODUCT_TYPE = BASE + 11;

		/** 选择认证身份类型 */
		int SELECT_AUTH_TYPE = BASE + 12;

		/** 选择银行列表 */
		int SELECT_BANK = BASE + 13;

		/** 选择港口 */
		int SELECT_PORT = BASE + 14;

		/** 选择找卖找卖排序类型 */
		int SELECT_BUY_ORDER_TYPE = BASE + 15;

		/** 选择我的供求过滤类型 */
		int SELECT_MY_BUY_FILTER_TYPE = BASE + 16;

	}

	/**
	 * Activity requestCode
	 */
	public interface ActivityReqCode {

		int BASE = 0x2000;

		/** 输入货物信息 */
		int REQ_INPUT_PRODUCT_INFO = BASE + 1;

		/** 选择交易地域信息 */
		int REQ_SELECT_TRADE_ADDRESS = BASE + 2;

		/** 选择卸货地址信息 */
		int REQ_SELECT_DISCHARGE_ADDRESS = BASE + 3;

		/** 卸货地址信息编辑 */
		int REQ_EDIT_DISCHARGE_ADDRESS = BASE + 4;

		/** 获取图片 */
		int REQ_GET_PHOTO = BASE + 5;

		/** 拍照 */
		int REQ_TAKE_PHOTO = BASE + 6;

		/** 联系人信息编辑 */
		int REQ_EDIT_CONTACT = BASE + 7;

		/** 企业介绍信息编辑 */
		int REQ_EDIT_COMPANY_INTRO = BASE + 8;

		/** 输入企业评价内容 */
		int REQ_EDIT_EVALUATION_DESCRIPTION = BASE + 9;

		/** 合同最终单价编辑 */
		int REQ_EDIT_CONTRACT_FINAL_UNIT_PRICE = BASE + 10;

		/** 收款人信息编辑 */
		int REQ_EDIT_PAYEE_INFO = BASE + 11;

		/** 查看系统消息 */
		int REQ_VIEW_SYSTEM_MESSAGE = BASE + 12;

		/** 选择找买找卖筛选条件 */
		int REQ_EDIT_BUY_FILTER_INFO = BASE + 13;

	}

}
