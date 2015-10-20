package com.glshop.net.common;

import android.os.Environment;

/**
 * @Description : 全局常量定义
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-16 下午4:56:43
 */
public final class GlobalConstants {

	/**
	 * Debug模式
	 */
	public static final boolean DEBUG_MODE = false;

	/**
	 * 全局服务器地址常量定义
	 */
	public static class Common {

		/**
		 * 常用业务请求服务器地址
		 */
		public static String SERVER_URL = "";

		/**
		 * 图片业务服务器地址
		 */
		public static String SERVER_URL_IMAGE = "";

		static {
			if (DEBUG_MODE) {
//				SERVER_URL = "http://192.168.1.197:8080/gl_shop_http/"; // 内网开发库
//				SERVER_URL_IMAGE = "http://192.168.1.223:8080/http/"; // 内网开发库
//				SERVER_URL="http://192.168.1.224:8080/gl_shop_http";
//				SERVER_URL="http://192.168.1.224:8080/gl_shop_http";
				SERVER_URL = "http://www.916816.com:9090/gl_shop_http_test"; // 内网测试库
				SERVER_URL_IMAGE = "http://www.916816.com/gl_shop_http_test"; // 内网测试库
			} else {
				//SERVER_URL = "http://121.40.91.147:8080/gl_shop_http"; // 现网老库(已废弃)
				//SERVER_URL_IMAGE = "http://121.40.91.147:8080/gl_shop_http"; // 现网老库(已废弃)
				//SERVER_URL = "http://www.916816.com/gl_shop_http"; // 现网老库(已废弃)
				SERVER_URL = "http://www.916816.com/gl_shop_http_new"; // 现网新库
				SERVER_URL_IMAGE = "http://www.916816.com/gl_shop_http_new"; // 现网新库
				//SERVER_URL = "http://www.916816.com:8081/gl_play"; // 现网银联新库
				//SERVER_URL_IMAGE = "http://www.916816.com:8081/gl_play"; // 现网银联新库
			}
		}
	}

	/**
	 * 常见通用配置常量
	 */
	public interface CfgConstants {

		/**
		 * 请求默认分页大小
		 */
		public static final int PAGE_SIZE = 15;

		/**
		 * 最大交易单价
		 */
		public static final double MAX_UNIT_PRICE = 5000;

		/**
		 * 最大交易数量
		 */
		public static final double MAX_AMOUNT = 50000;

		/**
		 * 默认消息刷新延时间隔
		 */
		public static final long MESSAGE_REFRESH_DELAY_TIME = 1000;

		/**
		 * 客户端是否检验保证金余额
		 */
		public static final boolean VALIDATE_DEPOSIT_BALANCE = false;

		/**
		 * 客户端是否检验货款余额
		 */
		public static final boolean VALIDATE_PAYMENT_BALANCE = true;

		/**
		 * 客户端是否检验保证金足够
		 */
		public static final boolean VALIDATE_DEPOSIT_ENOUGH = false;

		/**
		 * 平台客服电话(便于前期上线，暂时硬编码)
		 */
		public static final String PLATFORM_CUSTOM_SERVICE_PHONE = "4009616816";

	}

	/**
	 * 路径常量定义
	 */
	public interface AppDirConstants {

		/**
		 * 根路径
		 */
		public static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gl_shop";

		/**
		 * 日志路径
		 */
		public static final String LOG = ROOT + "/log/glshop.log";

		/**
		 * 临时文件路径
		 */
		public static final String TEMP = ROOT + "/temp/";

		/**
		 * 缓存路径
		 */
		public static final String CACHE = ROOT + "/cache/";

		/**
		 * 图片缓存路径
		 */
		public static final String IMAGE_CACHE = ROOT + "/cache/image/";

		/**
		 * 下载目录(文件及图片下载管理)
		 */
		public static final String DOWNLOAD = ROOT + "/download/";

		/**
		 * 升级目录(升级文件管理)
		 */
		public static final String UPGRADE = ROOT + "/upgrade/";

		/**
		 * 图形验证码下载全路径
		 */
		public static final String IMG_VERIFY_CODE_PATH = TEMP + "img_code.jpg";

	}

	/**
	 * SharedPreference Key 定义
	 */
	public interface SPKey {
		/**
		 * 设置versioncode
		 */
		public static final String VERSION_CODE = "version_code";
		/**
		 * 是否显示引导页
		 */
		public static final String BOOT_PAGE = "boot_page";

		/**
		 * 用户更新Token时间
		 */
		public static final String USER_TOKEN_UPDATE_TIME = "user_token_update_time";

		/**
		 * 当前登录账户名
		 */
		public static final String USER_ACCOUNT = "user_account";

		/**
		 * 当前登录账户手机号
		 */
		public static final String USER_PHONE = "user_phone";

		/**
		 * 当前登录账户ID
		 */
		public static final String USER_ID = "user_id";

		/**
		 * 当前登录企业ID
		 */
		public static final String COMPANY_ID = "company_id";

		/**
		 * 当前登录企业名称
		 */
		public static final String COMPANY_NAME = "company_name";

		/**
		 * 当前登录用户认证状态
		 */
		public static final String USER_AUTH_STATUS = "user_auth_status";

		/**
		 * 当前登录用户保证金状态
		 */
		public static final String USER_DEPOSIT_STATUS = "user_deposit_status";

		/**
		 * 当前登录用户货款状态
		 */
		public static final String USER_PAYMENT_STATUS = "user_payment_status";

		/**
		 * 当前登录用户保证金余额
		 */
		public static final String USER_DEPOSIT_BALANCE = "user_deposit_balance";

		/**
		 * 当前登录用户货款余额
		 */
		public static final String USER_PAYMENT_BALANCE = "user_payment_balance";

		/**
		 * 当前登录身份
		 */
		public static final String USER_PROFILE_TYPE = "user_profile_type";

		/**
		 * 当前登录用户密码
		 */
		public static final String USER_PASSWORD = "user_password";

		/**
		 * 是否记住密码
		 */
		public static final String IS_REMEMBER_USER_PWD = "is_remember_user_pwd";

		/**
		 * 用户是否注销
		 */
		public static final String IS_USER_LOGOUT = "is_user_logout";

		/**
		 * 应用是否已打开过
		 */
		public static final String IS_APP_OPENED = "is_app_opened";

		/**
		 * 当前版本名称
		 */
		public static final String CUR_VERSION_NAME = "cur_version_name";

		/**
		 * 当前版本编号
		 */
		public static final String CUR_VERSION_CODE = "cur_version_code";

		/**
		 * 当前忽略升级版本编号
		 */
		public static final String CUR_IGNORE_VERSION_CODE = "cur_ignore_version_code";

		/**
		 * 是否已导入本地省市区信息
		 */
		public static final String IS_IMPORTED_AREA_CFG = "is_imported_area_cfg";

		/**
		 * 是否已导入港口市区信息
		 */
		public static final String IS_IMPORTED_PORT_CFG = "is_imported_port_cfg";

	}

	/**
	 * 协议常量定义
	 */
	public interface ProtocolConstants {

		/**
		 * 协议根路径
		 */
		public static final String PROTOCOL_ROOT = "file:///android_asset/html";

		/**
		 * 用户服务协议路径
		 */
		public static final String USER_SERVICE_PROTOCOL_URL = PROTOCOL_ROOT + "/user_service_protocol_v150303.html";

		/**
		 * 用户认证协议路径
		 */
		public static final String USER_AUTH_PROTOCOL_URL = PROTOCOL_ROOT + "/user_auth_protocol_v150303.html";

		/**
		 * 充值协议路径
		 */
		public static final String RECHARGE_PROTOCOL_URL = PROTOCOL_ROOT + "/recharge_protocol_v150303.html";
		
		public static final String PAY_PROTOCOL_URL = PROTOCOL_ROOT + "/pay_protocl_v141009.html";

	}

	/**
	 * 主页Tab状态枚举
	 */
	public enum TabStatus {

		/**系统首页*/
		SHOP,
		/**找买找卖页面*/
		FIND_BUY,
		/**我的供求页面*/
		MY_BUY,
		/**我的合同页面*/
		MY_CONTRACT,
		/**我的钱包*/
		MY_PURSE,
		/**我的资料*/
		MY_PROFILE;

		public int toValue() {
			if (this == SHOP) {
				return 0;
			} else if (this == FIND_BUY) {
				return 1;
			} else if (this == MY_BUY) {
				return 2;
			} else if (this == MY_CONTRACT) {
				return 3;
			} else if (this == MY_PURSE) {
				return 4;
			} else if (this == MY_PROFILE) {
				return 5;
			} else {
				return 0;
			}
		}

		public static TabStatus convert(int status) {
			if (status == 0) {
				return SHOP;
			} else if (status == 1) {
				return FIND_BUY;
			} else if (status == 2) {
				return MY_BUY;
			} else if (status == 3) {
				return MY_CONTRACT;
			} else if (status == 4) {
				return MY_PURSE;
			} else if (status == 5) {
				return MY_PROFILE;
			} else {
				return SHOP;
			}
		}

	}

	/**
	 * 查看买卖信息详情类型
	 */
	public enum ViewBuyInfoType {

		/**查看找买找卖信息详情*/
		FINDBUY,
		/**查看我的供求信息详情**/
		MYBUY,
		/**查看我合同中供求信息详情**/
		MYCONTRACT;

		public int toValue() {
			if (this == FINDBUY) {
				return 1;
			} else if (this == MYBUY) {
				return 2;
			} else if (this == MYCONTRACT) {
				return 3;
			} else {
				return 1;
			}
		}

		public static ViewBuyInfoType convert(int type) {
			if (type == 1) {
				return FINDBUY;
			} else if (type == 2) {
				return MYBUY;
			} else if (type == 3) {
				return MYCONTRACT;
			} else {
				return FINDBUY;
			}
		}

	}

	/**
	 * 发布流程步骤枚举
	 */
	public enum PubBuyIndicatorType {

		/**货物信息*/
		PRODUCT,
		/**交易信息**/
		TRADE,
		/**发布预览**/
		PREVIEW;

		public int toValue() {
			if (this == PRODUCT) {
				return 1;
			} else if (this == TRADE) {
				return 2;
			} else if (this == PREVIEW) {
				return 3;
			} else {
				return 1;
			}
		}

		public static PubBuyIndicatorType convert(int type) {
			if (type == 1) {
				return PRODUCT;
			} else if (type == 2) {
				return TRADE;
			} else if (type == 3) {
				return PREVIEW;
			} else {
				return PRODUCT;
			}
		}

	}

	/**
	 * 找买找卖筛选类型
	 */
	public enum BuyFilterType {

		/**按交易地域筛选*/
		TRADE_AREA,
		/**按交易时间筛选**/
		TRADE_DATE,
		/**按货物种类筛选**/
		TRADE_PRODUCT;

		public int toValue() {
			if (this == TRADE_AREA) {
				return 1;
			} else if (this == TRADE_DATE) {
				return 2;
			} else if (this == TRADE_PRODUCT) {
				return 3;
			} else {
				return 1;
			}
		}

		public static BuyFilterType convert(int type) {
			if (type == 1) {
				return TRADE_AREA;
			} else if (type == 2) {
				return TRADE_DATE;
			} else if (type == 3) {
				return TRADE_PRODUCT;
			} else {
				return TRADE_AREA;
			}
		}

	}

	/**
	 * 合同详情Tab状态
	 */
	public enum ContractInfoType {

		/**合同待处理操作*/
		OPERATION,
		/**合同状态*/
		STATUS;

		public int toValue() {
			if (this == OPERATION) {
				return 0;
			} else if (this == STATUS) {
				return 1;
			} else {
				return 0;
			}
		}

		public static ContractInfoType convert(int status) {
			if (status == 0) {
				return OPERATION;
			} else if (status == 1) {
				return STATUS;
			} else {
				return OPERATION;
			}
		}

	}

	/**
	 * 合同模板详情Tab状态
	 */
	public enum TabContractInfoStatus {

		/**合同模板*/
		MODEL,
		/**操作历史记录*/
		HISTORY;

		public int toValue() {
			if (this == MODEL) {
				return 0;
			} else if (this == HISTORY) {
				return 1;
			} else {
				return 0;
			}
		}

		public static TabContractInfoStatus convert(int status) {
			if (status == 0) {
				return MODEL;
			} else if (status == 1) {
				return HISTORY;
			} else {
				return MODEL;
			}
		}

	}

	/**
	 * 钱包支付方式(包括充值、支付)
	 */
	public enum PursePayType {

		/**货款支付*/
		PAYMENT,
		/**钱包充值*/
		RECHARGE;

		public int toValue() {
			if (this == PAYMENT) {
				return 0;
			} else if (this == RECHARGE) {
				return 1;
			} else {
				return 0;
			}
		}

		public static PursePayType convert(int status) {
			if (status == 0) {
				return PAYMENT;
			} else if (status == 1) {
				return RECHARGE;
			} else {
				return PAYMENT;
			}
		}

	}

	/**
	 * 通用操作结果页面返回类型
	 */
	public enum TipActionBackType {

		/**默认仅关闭当前页*/
		FINISH,
		/**回到主界面*/
		TO_MAINPAGE,
		/**执行Action1*/
		DO_ACTION1,
		/**执行Action2*/
		DO_ACTION2;

		public int toValue() {
			if (this == FINISH) {
				return 0;
			} else if (this == TO_MAINPAGE) {
				return 1;
			} else if (this == DO_ACTION1) {
				return 2;
			} else if (this == DO_ACTION2) {
				return 3;
			} else {
				return 0;
			}
		}

		public static TipActionBackType convert(int type) {
			if (type == 0) {
				return FINISH;
			} else if (type == 1) {
				return TO_MAINPAGE;
			} else if (type == 2) {
				return DO_ACTION1;
			} else if (type == 3) {
				return DO_ACTION2;
			} else {
				return FINISH;
			}
		}

	}

	/**
	 * 数据状态类型
	 */
	public enum DataStatus {

		/**正在加载*/
		LOADING,
		/**加载完成*/
		NORMAL,
		/**数据为空*/
		EMPTY,
		/**加载失败*/
		ERROR;

		public int toValue() {
			if (this == LOADING) {
				return 0;
			} else if (this == NORMAL) {
				return 1;
			} else if (this == EMPTY) {
				return 2;
			} else if (this == ERROR) {
				return 3;
			} else {
				return 0;
			}
		}

		public static DataStatus convert(int type) {
			if (type == 0) {
				return LOADING;
			} else if (type == 1) {
				return NORMAL;
			} else if (type == 2) {
				return EMPTY;
			} else if (type == 3) {
				return ERROR;
			} else {
				return LOADING;
			}
		}

	}

	/**
	 * 数据请求类型
	 */
	public enum DataReqType {

		/**初始数据加载*/
		INIT,
		/**更多数据加载*/
		MORE,
		/**刷新数据加载*/
		REFRESH;

		public int toValue() {
			if (this == INIT) {
				return 0;
			} else if (this == MORE) {
				return 1;
			} else if (this == REFRESH) {
				return 2;
			} else {
				return 0;
			}
		}

		public static DataReqType convert(int type) {
			if (type == 0) {
				return INIT;
			} else if (type == 1) {
				return MORE;
			} else if (type == 2) {
				return REFRESH;
			} else {
				return INIT;
			}
		}

	}

	/**
	 * 数据请求方式
	 */
	public enum ReqSendType {

		/**前台请求*/
		FOREGROUND,
		/**后台请求*/
		BACKGROUND;

		public int toValue() {
			if (this == FOREGROUND) {
				return 0;
			} else if (this == BACKGROUND) {
				return 1;
			} else {
				return 0;
			}
		}

		public static ReqSendType convert(int type) {
			if (type == 0) {
				return FOREGROUND;
			} else if (type == 1) {
				return BACKGROUND;
			} else {
				return FOREGROUND;
			}
		}

	}


	/**微信app_id*/
	public static final String WEIXIN_APP_ID="wx3a65e76c66faaad2";

	/**客户端类型*/
	public static final String CLIENT_TYPE="Android";

}
