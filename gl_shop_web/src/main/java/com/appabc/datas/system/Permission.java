package com.appabc.datas.system;

import java.util.HashMap;
import java.util.Map;


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
	PhoneDialIn(1, "PHONE_DIAL_IN", "呼入电话"),
	PhoneDialOUt(2, "PHONE_DIAL_OUT", "呼出电话"),

	// Site Sales
	PromptVerify(21, "PROMPT_VERIFY", "催促认证"),
	PromptDeposit(22, "PROMPT_DEPOSIT", "催缴保证金"),
	PublishInfo(23, "PUBLISH_INFO", "代发信息"),
	PromptPayment(24, "PROMPT_PAYMENT", "催促缴纳货款"),
	PromptRealPay(25, "PROMPT_REAL_PAY", "催促实际支付"),

	// Contract Manager
	ContractMatch(41, "CONTRACT_MATCH", "撮合交流"),
	ContractGenerateConfirm(42, "CONTRACT_GENERATE_CONFIRM", "生成及确认合同"),

	// Editor
	MarketInfoEdit(61, "MARKET_INFO_EDIT", "市场行情编辑"),
	VerifyInfoAudit(62, "VERIFY_INFO_AUDIT", "认证信息审核"),

	// Financial Process
	RemittanceInput(81, "REMITTANCE_INPUT", "汇入款项录入"),
	AccountAudit(82, "ACCOUNT_AUDIT", "账户审核"),
	RemittanceProcess(83, "REMITTANCE_PROCESS", "汇入款项处理"),
	PayProcess(84, "PAY_PROCESS", "支出款项处理"),

	// Financial Audit
	RemittanceAudit(101, "REMITTANCE_AUDIT", "汇入款项审核"),
	PayAudit(102, "PAY_AUDIT", "支出款项审核"),
	SpecialFundProcess(103, "SPECIAL_FUND_PROCESS", "特殊款项处理"),

	// System management
	AccountManager(10001, "ACCOUNT_MANAGER", "人员帐号管理"),
	SpecialConditionProcess(10002, "SPECIAL_CONDITION_PROCESS", "特殊情况处理");

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
