package com.appabc.bean.bo;

import java.util.Date;
import java.util.List;
import com.appabc.bean.pvo.TUrgeVerify;


/**
 * @Description : 催促认证信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月28日 上午11:24:33
 */

public class UrgeVerifyInfo extends TUrgeVerify{
 
	private static final long serialVersionUID = 9115051444765836762L;
	/**
	 * 任务id
	 */
	private String taskid;
	/**
	 * 用户id
	 */
	private String userid;
	
	/**
	 * 用户手机号码
	 */
	private String username;
	
	/**
	 * 注册时间
	 */
	private Date regdate;
	/**
	 * 最后登录时间 
	 */
	private Date lastlogindate;
	/**
	 * 有效询单数-购买
	 */
	private int buynum;
	/**
	 * 有效询单数-出售
	 */
	private int sellnum;
	/**
	 * 提交认证次数
	 */
	private int authnum;
	/**
	 * 客服催促认证次数
	 */
	private int verifynum;
	/**
	 * 最后一次催促时间
	 */
	private Date lastverifydate;
	/**
	 * 服务记录
	 */
	private List<TUrgeVerify> urgeVerifyList;
	/**
	 * 公司类型
	 */
	private String companyType;
	/** 
	 * 认证状态
	 */
	private String authStatus;
	/** 
	 * 
	 * 保证金金额
	 */
	private String passAmount;
	
	public String getTaskid() {
		return taskid;
	}
	
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getRegdate() {
		return regdate;
	}
	
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getLastlogindate() {
		return lastlogindate;
	}
	
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	
	public int getBuynum() {
		return buynum;
	}
	
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}
	
	public int getSellnum() {
		return sellnum;
	}
	
	public void setSellnum(int sellnum) {
		this.sellnum = sellnum;
	}
	
	public int getAuthnum() {
		return authnum;
	}
	
	public void setAuthnum(int authnum) {
		this.authnum = authnum;
	}
	
	public int getVerifynum() {
		return verifynum;
	}
	
	public void setVerifynum(int verifynum) {
		this.verifynum = verifynum;
	}
	
	public Date getLastverifydate() {
		return lastverifydate;
	}
	
	public void setLastverifydate(Date lastverifydate) {
		this.lastverifydate = lastverifydate;
	}
	
	public List<TUrgeVerify> getUrgeVerifyList() {
		return urgeVerifyList;
	}
	
	public void setUrgeVerifyList(List<TUrgeVerify> urgeVerifyList) {
		this.urgeVerifyList = urgeVerifyList;
	}

	public String getCompanyType() {
		return companyType;
	}
	
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getPassAmount() {
		return passAmount;
	}

	public void setPassAmount(String passAmount) {
		this.passAmount = passAmount;
	}
	
}
