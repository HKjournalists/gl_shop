package com.appabc.tools.bean;

/**
 * @Description : IOS推送配置信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月29日 下午2:53:20
 */
public class IosConnectionConfigBean {
	
	/**
	 * 苹果推送服务器地址
	 * 测试服务器：gateway.sandbox.push.apple.com
	 * 正式服务器：
	 */
	private String host;
	
	/**
	 * 服务器端口
	 */
	private int port = 2195;
	
	/**
	 * MAC系统下导出的证书路径
	 */
	private String certificatePath;
	
	/**
	 * 密码
	 */
	private String certificatePassword;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public String getCertificatePassword() {
		return certificatePassword;
	}

	public void setCertificatePassword(String certificatePassword) {
		this.certificatePassword = certificatePassword;
	}

}
