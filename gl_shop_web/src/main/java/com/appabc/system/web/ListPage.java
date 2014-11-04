package com.appabc.system.web;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 24, 2014 2:02:57 PM
 */
public class ListPage {

	private String url;
	private String text;
	private boolean active;
	private boolean disabled;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url= url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public String toString() {
		return "[text=" + text + ", url=" + url + ", active=" + active + ", disabled=" + disabled + "]";
	}
	
}
