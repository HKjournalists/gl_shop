package com.appabc.datas.system;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 4:10:55 PM
 */
public enum TaskType {

	Unknown(0),
	Sales(1),
	Contract(2),
	Customer(3),
	VerifyInfo(Permission.VerifyInfoAudit.getId());

	private int value;

	private TaskType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static TaskType valueOf(int type) {
		TaskType tt = Unknown;

		for (TaskType taskType : TaskType.values()) {
			if (taskType.getValue() == type) {
				tt = taskType;
				break;
			}
		}

		return tt;
	}

}
