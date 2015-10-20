package com.appabc.datas.cms.vo;

import java.util.HashMap;
import java.util.Map;

import com.appabc.datas.cms.vo.task.TaskType;


/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 30, 2014 10:34:37 AM
 */
public enum Permission {

	// Customer Service
	PhoneDialIn(TaskType.PHONE_DIAl_IN, "PHONE_DIAL_IN", "呼入电话"),
	PhoneDialOut(TaskType.PHONE_DIAl_OUT, "PHONE_DIAL_OUT", "呼出电话"),

	// Site Sales
	PromptVerify(TaskType.PROMPT_VERIFY, "PROMPT_VERIFY", "催促认证"),
	PromptDeposit(TaskType.PROMPT_DEPOSIT, "PROMPT_DEPOSIT", "催缴保证金"),
	PublishInfo(TaskType.PUBLISH_INFO, "PUBLISH_INFO", "代发信息"),
	PromptPayment(TaskType.PROMPT_PAYMENT, "PROMPT_PAYMENT", "催促缴纳货款"),
	PromptRealPay(TaskType.PROMPT_REAL_PAY, "PROMPT_REAL_PAY", "催促实际支付"),

	// Contract Manager
	ContractOperation(41, "CONTRACT_OPERATION", "合同管理"),

	// Editor
	MarketInfoEdit(TaskType.MARKET_INFO_EDIT, "MARKET_INFO_EDIT", "市场行情编辑"),
	VerifyInfoAudit(TaskType.VERIFY_INFO_AUDIT, "VERIFY_INFO_AUDIT", "认证信息审核"),

	// Financial Process
	RemittanceInput(TaskType.REMITTANCE_INPUT, "REMITTANCE_INPUT", "汇入款项录入"),
	AccountAudit(TaskType.BANK_ACCOUNT_AUDIT, "ACCOUNT_AUDIT", "账户审核"),
	RemittanceProcess(TaskType.REMITTANCE_PROCESS, "REMITTANCE_PROCESS", "汇入款项处理"),
	WithdrawProcess(TaskType.WITHDRAW_PROCESS, "WITHDRAW_PROCESS", "提现处理"),

	// Financial Audit
	RemittanceAudit(TaskType.REMITTANCE_AUDIT, "REMITTANCE_AUDIT", "汇入款项审核"),
	WithdrawCashout(TaskType.WITHDRAW_CASHOUT, "WITHDRAW_CASHOUT", "提现打款"),
	SpecialFundProcess(TaskType.SPECIAL_FUND_PROCESS, "SPECIAL_FUND_PROCESS", "特殊款项处理"),

	// System management
	AccountManager(TaskType.ACCOUNT_MANAGER, "ACCOUNT_MANAGER", "人员帐号管理"),
	SpecialConditionProcess(TaskType.SPECIAL_CONDITION_PROCESS, "SPECIAL_CONDITION_PROCESS", "特殊情况处理"),
	BannerAdvertisementConfigure(TaskType.BANNERADVERTISEMENT_CONFIGURE, "BANNERADVERTISEMENT_CONFIGURE", "Banner广告图配置"),
	CodeRiversection(TaskType.CODE_RIVERSECTION, "CODE_RIVERSECTION", "行情区域配置"),
	
	//often tool
	ShortMessageSend(TaskType.SHORTMESSAGE_SEND,"SHORTMESSAGE_SEND","短信消息发送"),
	SystemMessageSend(TaskType.SYSTEMMESSAGE_SEND,"SYSTEMMESSAGE_SEND","系统消息发送"),
	ValidateCodeManage(TaskType.VALIDATECODE_MANAGE,"VALIDATECODE_MANAGE","验证码管理"),
	// Data count
	DataUserAllCount(201, "DATA_USER_ALL_COUNT", "用户整体统计"),
	DataUserSingleCount(202, "DATA_USER_SINGLE_COUNT", "单个用户信息统计");

	private static Map<Integer, Permission> lookupTable = null;

	static {
		lookupTable = new HashMap<>();
		for (Permission p : Permission.values()) {
			lookupTable.put(p.getId(), p);
		}
	}

	private int id;
	private String permName;
	private String displayName;

	private Permission(int id, String permName, String displayName) {
		this.id = id;
		this.permName = permName;
		this.displayName = displayName;
	}

	public static Permission valueOf(int perm) {
		return lookupTable.get(perm);
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return permName;
	}

	public String getDisplayName() {
		return displayName;
	}


}
