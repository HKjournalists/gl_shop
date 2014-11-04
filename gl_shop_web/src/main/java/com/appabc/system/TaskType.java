package com.appabc.system;

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
	Editor(4);
	
	private int type;
	
	private TaskType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public static TaskType valueOf(int type) {
		if (type < Sales.type || type > Editor.type) {
			return Unknown;
		} 
		
		TaskType tt = Unknown;

		for (TaskType taskType : TaskType.values()) {
			if (taskType.getType() == type) {
				tt = taskType;
				break;
			}
		}
		
		return tt;
	}

}
