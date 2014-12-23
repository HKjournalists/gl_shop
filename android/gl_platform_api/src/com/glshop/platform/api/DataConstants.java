package com.glshop.platform.api;

/**
 * FileName    : DataConstants.java
 * Description : 数据常量 和 枚举
 * @Copyright  : GL. All Rights Reserved
 * @Company    : 深圳市国立数码动画有限公司
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014-4-14 下午3:50:50
 **/
public class DataConstants {

	public static final String YES = "yes";

	public static final String NO = "no";

	/**api操作成功的接口*/
	public static final int MSG_OK = 200;
	/**api token键值*/
	public static final String USER_TOKEN = "USER_TOKEN";

	/**
	 * 全局统一ErrorCode
	 */
	public static interface GlobalErrorCode {

		/** 用户未登陆 */
		public static final String USER_NOT_LOGIN = "300001";

		/** 用户Token过期 */
		public static final String USER_TOKEN_EXPIRE = "300002";
	}

	/**
	 * 全局统一MessageType
	 */
	public static interface GlobalMessageType {

		int BASE = 0x11000000;

		/** 登录未登陆 */
		int MSG_USER_NOT_LOGINED = BASE + 1;

		/** 用户TOKEN失效 */
		int MSG_USER_TOKEN_EXPIRE = BASE + 2;

		/** 登录已离线 */
		int MSG_USER_OFFLINE = BASE + 3;
	}

	/**
	 * 系统同步参数常量
	 */
	public static interface SysCfgCode {

		/** 货物总类型编号 */
		public static final String TYPE_PRODUCT_CATEGORY = "1";

		/** 货物子类型编号 */
		public static final String TYPE_PRODUCT_SUB_CATEGORY = "2";

		/** 港口类型编号 */
		public static final String TYPE_PORT_SECTION = "3";

		/** 交易区域类型编号 */
		public static final String TYPE_AREA = "4";

		/** 银行类型编号 */
		public static final String TYPE_BANK = "5";

		/** 系统参数类型编号 */
		public static final String TYPE_SYSPARAM = "6";

		/** 沙子类型编号 */
		public static final String TYPE_PRODUCT_SAND = "G002";

		/** 石子类型编号 */
		public static final String TYPE_PRODUCT_STONE = "G001";

		/** 货物规格属性(含泥量) */
		public static final String TYPE_PROP_SEDIMENT = "MUD_CONTENT";

		/** 货物规格属性(泥块含量) */
		public static final String TYPE_PROP_SEDIMENT_BLOCK = "CLAY_CONTENT";

		/** 货物规格属性(含水率) */
		public static final String TYPE_PROP_WATER = "MOISTURE_CONTENT";

		/** 货物规格属性(表观密度) */
		public static final String TYPE_PROP_APPEARANCE = "APPARENT_DENSITY";

		/** 货物规格属性(堆积密度) */
		public static final String TYPE_PROP_STACKING = "BULK_DENSITY";

		/** 货物规格属性(压碎值指标) */
		public static final String TYPE_PROP_CRUNCH = "CRUSHING_VALUE_INDEX";

		/** 货物规格属性(针片状颗粒含量) */
		public static final String TYPE_PROP_NEEDLE_PLATE = "ELONGATED_PARTICLES";

		/** 货物规格属性(坚固性指标) */
		public static final String TYPE_PROP_STURDINESS = "CONSISTENCY_INDEX";

		/** 省份类型编号 */
		public static final String TYPE_AREA_PROVINCE = "province";

		/** 城市类型编号 */
		public static final String TYPE_AREA_CITY = "city";

		/** 市区类型编号 */
		public static final String TYPE_AREA_DISTRICT = "district";

		/** 企业保证金编号 */
		public static final String TYPE_BOND_ENTERPRISE = "BOND_ENTERPRISE";

		/** 个人保证金编号 */
		public static final String TYPE_BOND_PERSONAL = "BOND_PERSONAL";

		/** 船舶保证金编号 */
		public static final String TYPE_BOND_SHIP = "BOND_SHIP_";

	}

	/**
	 * 系统参数类型
	 */
	public enum SysCfgType {

		/**货物类型**/
		PRODUCT,
		/**交易地域**/
		AREA,
		/**银行类型**/
		BANK,
		/**平台参数**/
		SYSPARAM;

		public int toValue() {
			if (this == PRODUCT) {
				return 1;
			} else if (this == AREA) {
				return 2;
			} else if (this == BANK) {
				return 3;
			} else if (this == SYSPARAM) {
				return 4;
			} else {
				return 1;
			}
		}

		public static SysCfgType convert(int type) {
			if (type == 1) {
				return PRODUCT;
			} else if (type == 2) {
				return AREA;
			} else if (type == 3) {
				return BANK;
			} else if (type == 4) {
				return SYSPARAM;
			} else {
				return PRODUCT;
			}
		}

	}

	/**
	 * 买卖类型
	 */
	public enum BuyType {

		/**求购**/
		BUYER,
		/**出售**/
		SELLER;

		public int toValue() {
			if (this == BUYER) {
				return 1;
			} else {
				return 2;
			}
		}

		public static BuyType convert(int type) {
			if (type == 1) {
				return BUYER;
			} else {
				return SELLER;
			}
		}

	}

	/**
	 * 我的供求过滤类型
	 */
	public enum MyBuyFilterType {

		/**全部**/
		ALL,
		/**求购**/
		BUY,
		/**出售**/
		SELL;

		public int toValue() {
			if (this == ALL) {
				return 0;
			} else if (this == BUY) {
				return 1;
			} else if (this == SELL) {
				return 2;
			} else {
				return 0;
			}
		}

		public static MyBuyFilterType convert(int type) {
			if (type == 0) {
				return ALL;
			} else if (type == 1) {
				return BUY;
			} else if (type == 2) {
				return SELL;
			} else {
				return ALL;
			}
		}

	}

	/**
	 * 买卖信息状态
	 */
	public enum BuyStatus {

		/**有效**/
		VALID,
		/**审核未通过**/
		INVALID_UNPASS_REVIEW,
		/**用户取消**/
		INVALID_CREATED_CONTRACT,
		/**过期**/
		INVALID_EMPTY_AMOUNT,
		/**过期**/
		INVALID_EXPIRE,
		/**过期**/
		INVALID_CANCELED,
		/**审核未通过**/
		INVALID_OTHER;

		public int toValue() {
			if (this == VALID) {
				return 0;
			} else if (this == INVALID_UNPASS_REVIEW) {
				return 1;
			} else if (this == INVALID_CREATED_CONTRACT) {
				return 2;
			} else if (this == INVALID_EMPTY_AMOUNT) {
				return 3;
			} else if (this == INVALID_EXPIRE) {
				return 4;
			} else if (this == INVALID_CANCELED) {
				return 5;
			} else if (this == INVALID_OTHER) {
				return 6;
			} else {
				return 0;
			}
		}

		public static BuyStatus convert(int status) {
			if (status == 0) {
				return VALID;
			} else if (status == 1) {
				return INVALID_UNPASS_REVIEW;
			} else if (status == 2) {
				return INVALID_CREATED_CONTRACT;
			} else if (status == 3) {
				return INVALID_EMPTY_AMOUNT;
			} else if (status == 4) {
				return INVALID_EXPIRE;
			} else if (status == 5) {
				return INVALID_CANCELED;
			} else if (status == 6) {
				return INVALID_OTHER;
			} else {
				return VALID;
			}
		}

	}

	/**
	 * 货物类型
	 */
	public enum ProductType {

		/**黄沙**/
		SAND,
		/**石子**/
		STONE;

		public int toValue() {
			if (this == SAND) {
				return 1;
			} else {
				return 2;
			}
		}

		public static ProductType convert(int type) {
			if (type == 1) {
				return SAND;
			} else {
				return STONE;
			}
		}

	}

	/**
	 * 交货地址指定方式
	 */
	public enum DeliveryAddrType {

		/**己方指定**/
		ME_DECIDE,
		/**对方指定**/
		ANOTHER_DECIDE;

		public int toValue() {
			if (this == ME_DECIDE) {
				return 1;
			} else {
				return 2;
			}
		}

		public static DeliveryAddrType convert(int type) {
			if (type == 1) {
				return ME_DECIDE;
			} else {
				return ANOTHER_DECIDE;
			}
		}

	}

	/**
	 * 合同类型
	 */
	public enum ContractType {

		/**待确认的合同**/
		UNCONFIRMED,
		/**进行中的合同**/
		ONGOING,
		/**暂停中的合同**/
		PAUSEING,
		/**已结束的合同**/
		ENDED;

		public int toValue() {
			if (this == UNCONFIRMED) {
				return 0;
			} else if (this == ONGOING) {
				return 1;
			} else if (this == PAUSEING) {
				return 2;
			} else if (this == ENDED) {
				return 3;
			} else {
				return 0;
			}
		}

		public static ContractType convert(int type) {
			if (type == 0) {
				return UNCONFIRMED;
			} else if (type == 1) {
				return ONGOING;
			} else if (type == 2) {
				return PAUSEING;
			} else if (type == 3) {
				return ENDED;
			} else {
				return UNCONFIRMED;
			}
		}

	}

	/**
	 * 合同状态类型
	 */
	public enum ContractStatusType {

		/**起草中**/
		DRAFTING,
		/**已签约**/
		SIGNED;

		public int toValue() {
			if (this == DRAFTING) {
				return 0;
			} else if (this == SIGNED) {
				return 1;
			} else {
				return 0;
			}
		}

		public static ContractStatusType convert(int type) {
			if (type == 0) {
				return DRAFTING;
			} else if (type == 1) {
				return SIGNED;
			} else {
				return DRAFTING;
			}
		}

	}

	/**
	 * 合同生命周期
	 */
	public enum ContractLifeCycle {

		/** 起草中*/
		DRAFTING,

		/** 起草超时结束 */
		TIMEOUT_FINISHED,

		/** 起草手动作废（由客服） */
		MANUAL_RESTORE,

		/** 已签订 */
		SINGED,

		/** 付款中 */
		IN_THE_PAYMENT,

		/** 已付款 */
		PAYED_FUNDS,

		/** 已发货 */
		SENT_GOODS,

		/** 抽样验收中 */
		SIMPLE_CHECKING,

		/** 抽样验收通过 */
		SIMPLE_CHECKED,

		/** 全量验收中 */
		FULL_TAKEOVERING,

		/** 全量验收通过 */
		FULL_TAKEOVERED,

		/** 已经卸货 */
		UNINSTALLED_GOODS,

		/** 已经收货 */
		RECEIVED_GOODS,

		/** 取消中 */
		CANCELING,

		/** 结算中 */
		FINALESTIMATEING,

		/** 结算完成 */
		FINALESTIMATE_FINISHED,

		/** 正常结束 */
		NORMAL_FINISHED,

		/** 单方取消结束 */
		SINGLECANCEL_FINISHED,

		/** 双方取消结束 */
		DUPLEXCANCEL_FINISHED,

		/** 终止结束 */
		EXPIRATION_FINISHED,

		/** 已经仲裁 */
		ARBITRATION;

		public int toValue() {
			if (this == DRAFTING) {
				return 0;
			} else if (this == TIMEOUT_FINISHED) {
				return 1;
			} else if (this == MANUAL_RESTORE) {
				return 2;
			} else if (this == SINGED) {
				return 3;
			} else if (this == IN_THE_PAYMENT) {
				return 4;
			} else if (this == PAYED_FUNDS) {
				return 5;
			} else if (this == SENT_GOODS) {
				return 6;
			} else if (this == SIMPLE_CHECKING) {
				return 7;
			} else if (this == SIMPLE_CHECKED) {
				return 8;
			} else if (this == FULL_TAKEOVERING) {
				return 9;
			} else if (this == FULL_TAKEOVERED) {
				return 10;
			} else if (this == UNINSTALLED_GOODS) {
				return 11;
			} else if (this == RECEIVED_GOODS) {
				return 12;
			} else if (this == CANCELING) {
				return 13;
			} else if (this == FINALESTIMATEING) {
				return 14;
			} else if (this == FINALESTIMATE_FINISHED) {
				return 15;
			} else if (this == NORMAL_FINISHED) {
				return 16;
			} else if (this == SINGLECANCEL_FINISHED) {
				return 17;
			} else if (this == DUPLEXCANCEL_FINISHED) {
				return 18;
			} else if (this == EXPIRATION_FINISHED) {
				return 19;
			} else if (this == ARBITRATION) {
				return 20;
			} else {
				return 0;
			}
		}

		public static ContractLifeCycle convert(int type) {
			if (type == 0) {
				return DRAFTING;
			} else if (type == 1) {
				return TIMEOUT_FINISHED;
			} else if (type == 2) {
				return MANUAL_RESTORE;
			} else if (type == 3) {
				return SINGED;
			} else if (type == 4) {
				return IN_THE_PAYMENT;
			} else if (type == 5) {
				return PAYED_FUNDS;
			} else if (type == 6) {
				return SENT_GOODS;
			} else if (type == 7) {
				return SIMPLE_CHECKING;
			} else if (type == 8) {
				return SIMPLE_CHECKED;
			} else if (type == 9) {
				return FULL_TAKEOVERING;
			} else if (type == 10) {
				return FULL_TAKEOVERED;
			} else if (type == 11) {
				return UNINSTALLED_GOODS;
			} else if (type == 12) {
				return RECEIVED_GOODS;
			} else if (type == 13) {
				return CANCELING;
			} else if (type == 14) {
				return FINALESTIMATEING;
			} else if (type == 15) {
				return FINALESTIMATE_FINISHED;
			} else if (type == 16) {
				return NORMAL_FINISHED;
			} else if (type == 17) {
				return SINGLECANCEL_FINISHED;
			} else if (type == 18) {
				return DUPLEXCANCEL_FINISHED;
			} else if (type == 19) {
				return EXPIRATION_FINISHED;
			} else if (type == 20) {
				return ARBITRATION;
			} else {
				return DRAFTING;
			}
		}

	}

	/**
	 * 合同操作类型
	 */
	public enum ContractOprType {

		/** 确认合同  */
		CONFRIM_CONTRACT,

		/** 买家付款 */
		PAYED_FUNDS,

		/** 发货 */
		SEND_GOODS,

		/** 咨询客服 */
		CONSULTING_SERVICE,

		/** 单方取消 */
		SINGLE_CANCEL,

		/** 议价 */
		DIS_PRICE,

		/** 验收通过 */
		VALIDATE_PASS,

		/** 同意议价 */
		APPLY_DISPRICE,

		/** 确认卸货 */
		CONFIRM_UNINSTALLGOODS,

		/** 确认收货 */
		CONFIRM_RECEIVEGOODS,

		/** 评价合同 */
		EVALUATION_CONTRACT,

		/** 取消确认 */
		CANCEL_CONFIRM,

		/** 撤销取消 */
		REPEAL_CANCEL,

		/** 合同仲裁 */
		ARBITRATION_CONTRACT,

		/** 合同结算 */
		CONTRACT_ESTIMATE,

		/** 合同结束 */
		CONTRACT_FINISHED;

		public int toValue() {
			if (this == CONFRIM_CONTRACT) {
				return 0;
			} else if (this == PAYED_FUNDS) {
				return 1;
			} else if (this == SEND_GOODS) {
				return 2;
			} else if (this == CONSULTING_SERVICE) {
				return 3;
			} else if (this == SINGLE_CANCEL) {
				return 4;
			} else if (this == DIS_PRICE) {
				return 5;
			} else if (this == VALIDATE_PASS) {
				return 6;
			} else if (this == APPLY_DISPRICE) {
				return 7;
			} else if (this == CONFIRM_UNINSTALLGOODS) {
				return 8;
			} else if (this == CONFIRM_RECEIVEGOODS) {
				return 9;
			} else if (this == EVALUATION_CONTRACT) {
				return 10;
			} else if (this == CANCEL_CONFIRM) {
				return 11;
			} else if (this == REPEAL_CANCEL) {
				return 12;
			} else if (this == ARBITRATION_CONTRACT) {
				return 13;
			} else if (this == CONTRACT_ESTIMATE) {
				return 14;
			} else if (this == CONTRACT_FINISHED) {
				return 15;
			} else {
				return 0;
			}
		}

		public static ContractOprType convert(int type) {
			if (type == 0) {
				return CONFRIM_CONTRACT;
			} else if (type == 1) {
				return PAYED_FUNDS;
			} else if (type == 2) {
				return SEND_GOODS;
			} else if (type == 3) {
				return CONSULTING_SERVICE;
			} else if (type == 4) {
				return SINGLE_CANCEL;
			} else if (type == 5) {
				return DIS_PRICE;
			} else if (type == 6) {
				return VALIDATE_PASS;
			} else if (type == 7) {
				return APPLY_DISPRICE;
			} else if (type == 8) {
				return CONFIRM_UNINSTALLGOODS;
			} else if (type == 9) {
				return CONFIRM_RECEIVEGOODS;
			} else if (type == 10) {
				return EVALUATION_CONTRACT;
			} else if (type == 11) {
				return CANCEL_CONFIRM;
			} else if (type == 12) {
				return REPEAL_CANCEL;
			} else if (type == 13) {
				return ARBITRATION_CONTRACT;
			} else if (type == 14) {
				return CONTRACT_ESTIMATE;
			} else if (type == 15) {
				return CONTRACT_FINISHED;
			} else {
				return CONFRIM_CONTRACT;
			}
		}

	}

	/**
	 * 钱包类型
	 */
	public enum PurseType {

		/**保证金**/
		DEPOSIT,
		/**货款**/
		PAYMENT;

		public int toValue() {
			if (this == DEPOSIT) {
				return 0;
			} else {
				return 1;
			}
		}

		public static PurseType convert(int type) {
			if (type == 0) {
				return DEPOSIT;
			} else {
				return PAYMENT;
			}
		}

	}

	/**
	 * 钱包流水过滤类型
	 */
	public enum PurseDealFilterType {

		/**全部**/
		ALL,
		/**收入**/
		IN,
		/**支出**/
		OUT;

		public int toValue() {
			if (this == ALL) {
				return -1;
			} else if (this == IN) {
				return 0;
			} else if (this == OUT) {
				return 1;
			} else {
				return -1;
			}
		}

		public static PurseDealFilterType convert(int type) {
			if (type == -1) {
				return ALL;
			} else if (type == 0) {
				return IN;
			} else if (type == 1) {
				return OUT;
			} else {
				return ALL;
			}
		}

	}

	/**
	 * 钱包流水大类型
	 */
	public enum DealDirectionType {

		/**收入**/
		IN,
		/**支出**/
		OUT;

		public int toValue() {
			if (this == IN) {
				return 0;
			} else if (this == OUT) {
				return 1;
			} else {
				return 0;
			}
		}

		public static DealDirectionType convert(int type) {
			if (type == 0) {
				return IN;
			} else if (type == 1) {
				return OUT;
			} else {
				return IN;
			}
		}

	}

	/**
	 * 钱包流水大类型
	 */
	public enum PayType {

		/**在线支付*/
		NETBANK_PAY,
		/**银行转账*/
		BANK_DEDUCT,
		/**长江电商钱包*/
		PLATFORM_DEDUCT,
		/**线下付款*/
		OFFLINE_PAY;

		public int toValue() {
			if (this == NETBANK_PAY) {
				return 0;
			} else if (this == BANK_DEDUCT) {
				return 1;
			} else if (this == PLATFORM_DEDUCT) {
				return 2;
			} else if (this == OFFLINE_PAY) {
				return 3;
			} else {
				return 0;
			}
		}

		public static PayType convert(int type) {
			if (type == 0) {
				return NETBANK_PAY;
			} else if (type == 1) {
				return BANK_DEDUCT;
			} else if (type == 2) {
				return PLATFORM_DEDUCT;
			} else if (type == 3) {
				return OFFLINE_PAY;
			} else {
				return NETBANK_PAY;
			}
		}

	}

	/**
	 * 流水操作类型
	 */
	public enum DealOprType {

		/**汇入*/
		DEPOSIT,
		/**提取冻结*/
		EXTRACT_CASH_GELATION,
		/**提取解冻*/
		EXTRACT_CASH_UNGELATION,
		/**提取成功*/
		EXTRACT_CASH_SUCCESS,
		/**提取失败*/
		EXTRACT_CASH_FAILURE,
		/**保证金冻结*/
		GELATION_GUARANTY,
		/**保证金解冻*/
		UNGELATION_GUARANTY,
		/**买货冻结*/
		GELATION_DEPOSIT,
		/**买货解冻*/
		UNGELATION_DEPOSIT,
		/**赔付对方付出*/
		VIOLATION_DEDUCTION,
		/**平台扣除+备注*/
		MANPOWER_DEDUCTION,
		/**对方赔付*/
		VIOLATION_REPARATION,
		/**平台汇入+备注*/
		MANPOWER_REPARATION,
		/**货款转保证金*/
		DEPOSIT_GUARANTY,
		/**支付货款*/
		PAYMENT_FOR_GOODS,
		/**交易扣除费*/
		SERVICE_CHARGE,
		/**平台返还*/
		PLATFORM_RETURN,
		/**平台支付*/
		PLATFORM_PAY,
		/**平台补贴*/
		PLATFORM_SUBSIDY,
		/**其他*/
		OTHERS_TRANSFER;

		public int toValue() {
			if (this == DEPOSIT) {
				return 0;
			} else if (this == EXTRACT_CASH_GELATION) {
				return 1;
			} else if (this == EXTRACT_CASH_UNGELATION) {
				return 2;
			} else if (this == EXTRACT_CASH_SUCCESS) {
				return 3;
			} else if (this == EXTRACT_CASH_FAILURE) {
				return 4;
			} else if (this == GELATION_GUARANTY) {
				return 5;
			} else if (this == UNGELATION_GUARANTY) {
				return 6;
			} else if (this == GELATION_DEPOSIT) {
				return 7;
			} else if (this == UNGELATION_DEPOSIT) {
				return 8;
			} else if (this == VIOLATION_DEDUCTION) {
				return 9;
			} else if (this == MANPOWER_DEDUCTION) {
				return 10;
			} else if (this == VIOLATION_REPARATION) {
				return 11;
			} else if (this == MANPOWER_REPARATION) {
				return 12;
			} else if (this == DEPOSIT_GUARANTY) {
				return 13;
			} else if (this == PAYMENT_FOR_GOODS) {
				return 14;
			} else if (this == SERVICE_CHARGE) {
				return 15;
			} else if (this == PLATFORM_RETURN) {
				return 16;
			} else if (this == PLATFORM_PAY) {
				return 17;
			} else if (this == PLATFORM_SUBSIDY) {
				return 18;
			} else if (this == OTHERS_TRANSFER) {
				return 19;
			} else {
				return 0;
			}
		}

		public static DealOprType convert(int type) {
			if (type == 0) {
				return DEPOSIT;
			} else if (type == 1) {
				return EXTRACT_CASH_GELATION;
			} else if (type == 2) {
				return EXTRACT_CASH_UNGELATION;
			} else if (type == 3) {
				return EXTRACT_CASH_SUCCESS;
			} else if (type == 4) {
				return EXTRACT_CASH_FAILURE;
			} else if (type == 5) {
				return GELATION_GUARANTY;
			} else if (type == 6) {
				return UNGELATION_GUARANTY;
			} else if (type == 7) {
				return GELATION_DEPOSIT;
			} else if (type == 8) {
				return UNGELATION_DEPOSIT;
			} else if (type == 9) {
				return VIOLATION_DEDUCTION;
			} else if (type == 10) {
				return MANPOWER_DEDUCTION;
			} else if (type == 11) {
				return VIOLATION_REPARATION;
			} else if (type == 12) {
				return MANPOWER_REPARATION;
			} else if (type == 13) {
				return DEPOSIT_GUARANTY;
			} else if (type == 14) {
				return PAYMENT_FOR_GOODS;
			} else if (type == 15) {
				return SERVICE_CHARGE;
			} else if (type == 16) {
				return PLATFORM_RETURN;
			} else if (type == 17) {
				return PLATFORM_PAY;
			} else if (type == 18) {
				return PLATFORM_SUBSIDY;
			} else if (type == 19) {
				return OTHERS_TRANSFER;
			} else {
				return DEPOSIT;
			}
		}

	}

	/**
	 * 收款人状态
	 */
	public enum PayeeStatus {

		/**审核未通过*/
		AUTH_FAILED,
		/**审核通过*/
		AUTHED,
		/**审核中*/
		AUTHING;

		public int toValue() {
			if (this == AUTH_FAILED) {
				return 0;
			} else if (this == AUTHED) {
				return 1;
			} else if (this == AUTHING) {
				return 2;
			} else {
				return 0;
			}
		}

		public static PayeeStatus convert(int status) {
			if (status == 0) {
				return AUTH_FAILED;
			} else if (status == 1) {
				return AUTHED;
			} else if (status == 2) {
				return AUTHING;
			} else {
				return AUTH_FAILED;
			}
		}

	}

	/**
	 * 用户身份类型
	 */
	public enum ProfileType {

		/**企业**/
		COMPANY,
		/**船舶**/
		SHIP,
		/**个人**/
		PEOPLE;

		public int toValue() {
			if (this == COMPANY) {
				return 0;
			} else if (this == SHIP) {
				return 1;
			} else if (this == PEOPLE) {
				return 2;
			} else {
				return 0;
			}
		}

		public static ProfileType convert(int type) {
			if (type == 0) {
				return COMPANY;
			} else if (type == 1) {
				return SHIP;
			} else if (type == 2) {
				return PEOPLE;
			} else {
				return COMPANY;
			}
		}

	}

	/**
	 * 认证状态类型
	 */
	public enum AuthStatusType {

		/**未认证**/
		UN_AUTH,
		/**已认证**/
		AUTH_SUCCESS,
		/**审核中**/
		AUTHING,
		/**认证失败**/
		AUTH_FAILED;

		public int toValue() {
			if (this == UN_AUTH) {
				return 0;
			} else if (this == AUTH_SUCCESS) {
				return 1;
			} else if (this == AUTHING) {
				return 2;
			} /*else if (this == AUTH_FAILED) {
				return 0;
				} */else {
				return 0;
			}
		}

		public static AuthStatusType convert(int type) {
			if (type == 0) {
				return UN_AUTH;
			} else if (type == 1) {
				return AUTH_SUCCESS;
			} else if (type == 2) {
				return AUTHING;
			} /*else if (type == 0) {
				return AUTH_FAILED;
				}*/else {
				return UN_AUTH;
			}
		}

	}

	/**
	 * 保证金缴纳状态类型
	 */
	public enum DepositStatusType {

		/**未缴纳**/
		UN_RECHARGE,
		/**已缴纳**/
		RECHARGE_SUCCESS;

		public int toValue() {
			if (this == UN_RECHARGE) {
				return 0;
			} else if (this == RECHARGE_SUCCESS) {
				return 1;
			} else {
				return 0;
			}
		}

		public static DepositStatusType convert(int type) {
			if (type == 0) {
				return UN_RECHARGE;
			} else if (type == 1) {
				return RECHARGE_SUCCESS;
			} else {
				return UN_RECHARGE;
			}
		}

	}

	/**
	 * 买卖排序类型
	 */
	public enum BuyOrderType {

		/**有效期排序**/
		EXPIRY,
		/**价格排序**/
		PRICE,
		/**诚信度排序**/
		CREDIT;

		public int toValue() {
			if (this == EXPIRY) {
				return 0;
			} else if (this == PRICE) {
				return 1;
			} else if (this == CREDIT) {
				return 2;
			} else {
				return 0;
			}
		}

		public static BuyOrderType convert(int type) {
			if (type == 0) {
				return EXPIRY;
			} else if (type == 1) {
				return PRICE;
			} else if (type == 2) {
				return CREDIT;
			} else {
				return EXPIRY;
			}
		}

	}

	/**
	 * 排序值类型
	 */
	public enum OrderStatus {

		/**倒序排序**/
		DESC,
		/**升序排序**/
		ASC;

		public int toValue() {
			if (this == DESC) {
				return 0;
			} else if (this == ASC) {
				return 1;
			} else {
				return 0;
			}
		}

		public static OrderStatus convert(int type) {
			if (type == 0) {
				return DESC;
			} else if (type == 1) {
				return ASC;
			} else {
				return DESC;
			}
		}

	}

	/**
	 * 消息中心的消息状态
	 */
	public enum MessageStatus {

		/**未读**/
		UNREADED,
		/**已读**/
		READED;

		public int toValue() {
			if (this == UNREADED) {
				return 0;
			} else if (this == READED) {
				return 1;
			} else {
				return 0;
			}
		}

		public static MessageStatus convert(int type) {
			if (type == 0) {
				return UNREADED;
			} else if (type == 1) {
				return READED;
			} else {
				return UNREADED;
			}
		}

	}

	/**
	 * 消息中心的消息操作类型
	 */
	public enum SystemMsgOprType {

		/**无**/
		TO_DO_NOTHING,
		/**认证**/
		TO_AUTH,
		/**找买找买列表**/
		TO_FINDBUY_LIST,
		/**买卖详情**/
		TO_BUY_INFO,
		/**合同详情**/
		TO_CONTRACT_INFO;

		public int toValue() {
			if (this == TO_DO_NOTHING) {
				return 0;
			} else if (this == TO_AUTH) {
				return 1;
			} else if (this == TO_FINDBUY_LIST) {
				return 2;
			} else if (this == TO_BUY_INFO) {
				return 3;
			} else if (this == TO_CONTRACT_INFO) {
				return 4;
			} else {
				return 0;
			}
		}

		public static SystemMsgOprType convert(int type) {
			if (type == 0) {
				return TO_DO_NOTHING;
			} else if (type == 1) {
				return TO_AUTH;
			} else if (type == 2) {
				return TO_FINDBUY_LIST;
			} else if (type == 3) {
				return TO_BUY_INFO;
			} else if (type == 4) {
				return TO_CONTRACT_INFO;
			} else {
				return TO_DO_NOTHING;
			}
		}

	}

	/**
	 * 消息中心的消息类型
	 */
	public enum SystemMsgType {

		/**系统消息**/
		SYSTEM,
		/**活动消息**/
		ACTIVE;

		public int toValue() {
			if (this == SYSTEM) {
				return 0;
			} else if (this == ACTIVE) {
				return 1;
			} else {
				return 0;
			}
		}

		public static SystemMsgType convert(int type) {
			if (type == 0) {
				return SYSTEM;
			} else if (type == 1) {
				return ACTIVE;
			} else {
				return SYSTEM;
			}
		}

	}

	/**
	 * 消息业务类型
	 */
	public enum MsgBusinessType {

		/**默认消息**/
		TYPE_DEFAULT,
		/**用户注册**/
		TYPE_REGISTER,
		/**业务类型：用户下线**/
		TYPE_USER_LOGIN_OTHER_DEVICE,
		/**企业资料认证**/
		TYPE_COMPANY_AUTH,
		/**企业收款人认证**/
		TYPE_COMPANY_PAYEE_AUTH,
		/**询单，供求信息**/
		TYPE_ORDER_FIND,
		/**合同签订**/
		TYPE_CONTRACT_SIGN,
		/**合同进行中**/
		TYPE_CONTRACT_ING,
		/**合同评价**/
		TYPE_CONTRACT_EVALUATION,
		/**保证金金额变动**/
		TYPE_MONEY_CHANG_GUARANTY,
		/**货款金额变动**/
		TYPE_MONEY_CHANG_DEPOSIT,
		/**钱包金额变动**/
		TYPE_MONEY_CHANG,
		/**业务类型：APP下载URL**/
		TYPE_APP_DOWNLOAD;

		public int toValue() {
			if (this == TYPE_DEFAULT) {
				return 0;
			} else if (this == TYPE_REGISTER) {
				return 100;
			} else if (this == TYPE_USER_LOGIN_OTHER_DEVICE) {
				return 101;
			} else if (this == TYPE_COMPANY_AUTH) {
				return 200;
			} else if (this == TYPE_COMPANY_PAYEE_AUTH) {
				return 201;
			} else if (this == TYPE_ORDER_FIND) {
				return 300;
			} else if (this == TYPE_CONTRACT_SIGN) {
				return 400;
			} else if (this == TYPE_CONTRACT_ING) {
				return 401;
			} else if (this == TYPE_CONTRACT_EVALUATION) {
				return 402;
			} else if (this == TYPE_MONEY_CHANG_GUARANTY) {
				return 500;
			} else if (this == TYPE_MONEY_CHANG_DEPOSIT) {
				return 501;
			} else if (this == TYPE_MONEY_CHANG) {
				return 502;
			} else if (this == TYPE_APP_DOWNLOAD) {
				return 900;
			} else {
				return 0;
			}
		}

		public static MsgBusinessType convert(int type) {
			if (type == 0) {
				return TYPE_DEFAULT;
			} else if (type == 100) {
				return TYPE_REGISTER;
			} else if (type == 101) {
				return TYPE_USER_LOGIN_OTHER_DEVICE;
			} else if (type == 200) {
				return TYPE_COMPANY_AUTH;
			} else if (type == 201) {
				return TYPE_COMPANY_PAYEE_AUTH;
			} else if (type == 300) {
				return TYPE_ORDER_FIND;
			} else if (type == 400) {
				return TYPE_CONTRACT_SIGN;
			} else if (type == 401) {
				return TYPE_CONTRACT_ING;
			} else if (type == 402) {
				return TYPE_CONTRACT_EVALUATION;
			} else if (type == 500) {
				return TYPE_MONEY_CHANG_GUARANTY;
			} else if (type == 501) {
				return TYPE_MONEY_CHANG_DEPOSIT;
			} else if (type == 502) {
				return TYPE_MONEY_CHANG;
			} else if (type == 900) {
				return TYPE_APP_DOWNLOAD;
			} else {
				return TYPE_DEFAULT;
			}
		}

	}

}