/**  
 * com.appabc.tools.mail.javamail.MailInfo.java  
 *   
 * 2015年9月14日 下午7:40:27  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.mail.javamail;

import com.appabc.common.base.bean.IBaseEnum;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年9月14日 下午7:40:27
 */

public interface MailInfo extends IBaseEnum{

	public enum MailType implements MailInfo {
		
		/** 
	     * 图片类型 
	     * */  
	    IMG,  
	    /** 
	     * 文件类型 
	     * */  
	    FILE;  
	  
	    @Override  
	    public String toString() {  
	        String message = null;  
	        switch (this) {       
	        case IMG :  
	            message = ",包含图片";  
	        default:  
	            message = ",包含附件";  
	            break;  
	        }  
	        return message;  
	    }
		
	}
	
	public enum SendMailType implements MailInfo {
		TEXT, HTML;

		@Override
		public String toString() {
			String message = null;
			switch (this) {
			case TEXT:
				message = "发送文本消息";
				break;
			case HTML:
				message = "发送html消息";
				break;
			}
			return message;
		}

	}
	
}
