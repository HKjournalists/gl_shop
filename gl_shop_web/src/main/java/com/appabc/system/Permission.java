package com.appabc.system;

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
	PHONE_DIAL_IN(1, "PHONE_DIAL_IN", "呼入电话"),
	PHONE_DIAL_OUT(2, "PHONE_DIAL_OUT", "呼出电话"),
	
	// Site Sales
	PROMPT_VERIFY(21, "PROMPT_VERIFY", "催促认证"),
	PROMPT_DEPOSIT(22, "PROMPT_DEPOSIT", "催缴保证金"),
	PUBLISH_INFO(23, "PUBLISH_INFO", "代发信息"),
	PROMPT_PAYMENT(24, "PROMPT_PAYMENT", "催促缴纳货款"),
	PROMPT_REAL_PAY(25, "PROMPT_REAL_PAY", "催促实际支付"),
	
	// Contract Manager
	CONTRACT_MATCH(41, "CONTRACT_MATCH", "撮合交流"),
	CONTRACT_GENERATE_CONFIRM(42, "CONTRACT_GENERATE_CONFIRM", "生成/确认合同"),
	
	// Editor
	MARKET_INFO_EDIT(61, "MARKET_INFO_EDIT", "市场行情编辑"),
	VERIFY_INFO_AUDIT(62, "VERIFY_INFO_AUDIT", "认证信息审核"),
	
	// Financial Process
	REMITTANCE_INPUT(81, "REMITTANCE_INPUT", "汇入款项录入"),
	ACCOUNT_AUDIT(82, "ACCOUNT_AUDIT", "账户审核"),
	REMITTANCE_PROCESS(83, "REMITTANCE_PROCESS", "汇入款项处理"),
	PAY_PROCESS(84, "PAY_PROCESS", "支出款项处理"),
	
	// Financial Audit
	REMITTANCE_AUDIT(101, "REMITTANCE_AUDIT", "汇入款项审核"),
	PAY_AUDIT(102, "PAY_AUDIT", "支出款项审核"),
	SPECIAL_FUND_PROCESS(103, "SPECIAL_FUND_PROCESS", "特殊款项处理"),
	
	// System management
	ACCOUNT_MANAGER(10001, "ACCOUNT_MANAGER", "人员帐号管理"),
	SPECIAL_CONDITION_PROCESS(10002, "SPECIAL_CONDITION_PROCESS", "特殊情况处理");
	
	private static Map<Integer, Permission> lookupTable = null;
	
	static {
		lookupTable = new HashMap<Integer, Permission>();
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
