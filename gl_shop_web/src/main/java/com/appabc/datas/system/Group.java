package com.appabc.datas.system;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 30, 2014 11:55:31 AM
 */
public enum Group {
	
	SuperUser(0, "SU", "超级管理员"),
	CustomerService(1, "CS", "客服"),
	ContractManager(2, "CM", "合同管理员"),
	Editor(3, "ED", "编辑"),
	SiteSales(4, "SS", "站内销售"),
	FinancialProcessor(5, "FP", "财务处理"),
	FinancialAudit(6, "FA", "财务审核");

	private static Map<Integer, Group> lookupTable = null;
	
	static {
		lookupTable = new HashMap<Integer, Group>();
		for (Group g : Group.values()) {
			lookupTable.put(g.getId(), g);
		}
	}	

	private int id;
	private String name;
	private String displayName;
	
	private Group(int id, String name, String displayName) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
	}

	public static Group valueOf(int id) {
		return lookupTable.get(id);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

}
