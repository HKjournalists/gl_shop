/**  
 * com.appabc.tools.mail.javamail.JavaEmailConfig.java  
 *   
 * 2015年9月15日 下午2:28:45  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.mail.javamail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年9月15日 下午2:28:45
 */

@Component
public class JavaEmailConfig {

	//**邮件服务器*//*  
    @Value("${mail.host}")
    private String email_host;
    
    //**邮件端口*//*
    @Value("${mail.port}")
    private String email_port;
      
    //**用户名*//*  
    @Value("${mail.username}")
    private String email_username;  
      
    //**用户密码*//*  
    @Value("${mail.password}")
    private  String email_password;     
      
    //**接收人*//*  
    @Value("${mail.to}")
    private  String email_to;  
      
    //**发送人*//*  
    @Value("${mail.from}")
    private String email_from;     
      
    //**服务器进行认证,认证用户名和密码是否正确*//*  
    @Value("${mail.smtp.auth}")
    private  String email_smtp_auth;  
      
    //**超时时间设定*//*  
    @Value("${mail.smtp.timeout}")
    private  String email_smtp_timeout;  
      
    //**编码设置*//*  
    @Value("${mail.encoding}")
    private  String email_encoding;  
      
    @Value("${mail.smtp.starttls.enable}")
    private  String email_smtp_starttls;  
      
    @Value("${mail.smtp.socketFactory.class}")
    private  String email_smtp_socketfactory_class;

	/**  
	 * email_host  
	 *  
	 * @return  the email_host  
	 * @since   1.0.0  
	 */
	
	public String getEmail_host() {
		return StringUtils.isNotEmpty(email_host) ? email_host.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_host the email_host to set  
	 */
	public void setEmail_host(String email_host) {
		this.email_host = StringUtils.isNotEmpty(email_host) ? email_host.trim() : StringUtils.EMPTY;
	}

	/**  
	 * eamil_port  
	 *  
	 * @return  the eamil_port  
	 * @since   1.0.0  
	 */
	
	public String getEamil_port() {
		return StringUtils.isNotEmpty(email_port) ? email_port.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param eamil_port the eamil_port to set  
	 */
	public void setEamil_port(String email_port) {
		this.email_port = StringUtils.isNotEmpty(email_port) ? email_port.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_username  
	 *  
	 * @return  the email_username  
	 * @since   1.0.0  
	 */
	
	public String getEmail_username() {
		return StringUtils.isNotEmpty(email_username) ? email_username.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_username the email_username to set  
	 */
	public void setEmail_username(String email_username) {
		this.email_username = StringUtils.isNotEmpty(email_username) ? email_username.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_password  
	 *  
	 * @return  the email_password  
	 * @since   1.0.0  
	 */
	
	public String getEmail_password() {
		return StringUtils.isNotEmpty(email_password) ? email_password.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_password the email_password to set  
	 */
	public void setEmail_password(String email_password) {
		this.email_password = StringUtils.isNotEmpty(email_password) ? email_password.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_to  
	 *  
	 * @return  the email_to  
	 * @since   1.0.0  
	 */
	
	public String getEmail_to() {
		return StringUtils.isNotEmpty(email_to) ? email_to.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_to the email_to to set  
	 */
	public void setEmail_to(String email_to) {
		this.email_to = StringUtils.isNotEmpty(email_to) ? email_to.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_from  
	 *  
	 * @return  the email_from  
	 * @since   1.0.0  
	 */
	
	public String getEmail_from() {
		return StringUtils.isNotEmpty(email_from) ? email_from.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_from the email_from to set  
	 */
	public void setEmail_from(String email_from) {
		this.email_from = StringUtils.isNotEmpty(email_from) ? email_from.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_smtp_auth  
	 *  
	 * @return  the email_smtp_auth  
	 * @since   1.0.0  
	 */
	
	public String getEmail_smtp_auth() {
		return StringUtils.isNotEmpty(email_smtp_auth) ? email_smtp_auth.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_smtp_auth the email_smtp_auth to set  
	 */
	public void setEmail_smtp_auth(String email_smtp_auth) {
		this.email_smtp_auth = StringUtils.isNotEmpty(email_smtp_auth) ? email_smtp_auth.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_smtp_timeout  
	 *  
	 * @return  the email_smtp_timeout  
	 * @since   1.0.0  
	 */
	
	public String getEmail_smtp_timeout() {
		return StringUtils.isNotEmpty(email_smtp_timeout) ? email_smtp_timeout.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_smtp_timeout the email_smtp_timeout to set  
	 */
	public void setEmail_smtp_timeout(String email_smtp_timeout) {
		this.email_smtp_timeout = StringUtils.isNotEmpty(email_smtp_timeout) ? email_smtp_timeout.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_encoding  
	 *  
	 * @return  the email_encoding  
	 * @since   1.0.0  
	 */
	
	public String getEmail_encoding() {
		return StringUtils.isNotEmpty(email_encoding) ? email_encoding.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_encoding the email_encoding to set  
	 */
	public void setEmail_encoding(String email_encoding) {
		this.email_encoding = StringUtils.isNotEmpty(email_encoding) ? email_encoding.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_smtp_starttls  
	 *  
	 * @return  the email_smtp_starttls  
	 * @since   1.0.0  
	 */
	
	public String getEmail_smtp_starttls() {
		return StringUtils.isNotEmpty(email_smtp_starttls) ? email_smtp_starttls.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_smtp_starttls the email_smtp_starttls to set  
	 */
	public void setEmail_smtp_starttls(String email_smtp_starttls) {
		this.email_smtp_starttls = StringUtils.isNotEmpty(email_smtp_starttls) ? email_smtp_starttls.trim() : StringUtils.EMPTY;
	}

	/**  
	 * email_smtp_socketfactory_class  
	 *  
	 * @return  the email_smtp_socketfactory_class  
	 * @since   1.0.0  
	 */
	
	public String getEmail_smtp_socketfactory_class() {
		return StringUtils.isNotEmpty(email_smtp_socketfactory_class) ? email_smtp_socketfactory_class.trim() : StringUtils.EMPTY;
	}

	/**  
	 * @param email_smtp_socketfactory_class the email_smtp_socketfactory_class to set  
	 */
	public void setEmail_smtp_socketfactory_class(
			String email_smtp_socketfactory_class) {
		this.email_smtp_socketfactory_class = StringUtils.isNotEmpty(email_smtp_socketfactory_class) ? email_smtp_socketfactory_class.trim() : StringUtils.EMPTY;
	}

}
