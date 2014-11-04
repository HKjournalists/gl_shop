package com.appabc.common.base;

/**
 * @Description : SQL Expression Enum
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月9日 下午8:25:05
 */

public enum SQLExpressionEnum {
	//=, >=, <=, <>, !=, like
	EQ(" = "),GT(" >= "),LT(" <= "),NOEQ(" <> "),NOTEQ(" != "),LIKE(" like ");
	
	private String text;
	
	private SQLExpressionEnum(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
}
