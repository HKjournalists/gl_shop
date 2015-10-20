/**
 *
 */
package com.appabc.bean.bo;

import java.util.Date;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.OrderFindInfo.MatchingTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 供求自动匹配BEAN
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月18日 下午5:29:42
 */
public class MatchingBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String itemid; // 交易意向记录ID
	private String fid; // 询单ID
	private String title; // 询单标题
	private OrderTypeEnum oftype;// 询单类型
	private String cid; // 企业ID
	private String cname; // 企业名称
	private CompanyType ctype; // 企业类型
	private String username; // 用户名
	private String phone; // 用户电话
	private MatchingTypeEnum matchingtype; // 撮合对象类型
	private Date fItemTime; //交易询盘时间
	private Date fPulishTime; //询单发布时间
	
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public OrderTypeEnum getOftype() {
		return oftype;
	}
	public void setOftype(OrderTypeEnum oftype) {
		this.oftype = oftype;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public CompanyType getCtype() {
		return ctype;
	}
	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public MatchingTypeEnum getMatchingtype() {
		return matchingtype;
	}
	public void setMatchingtype(MatchingTypeEnum matchingtype) {
		this.matchingtype = matchingtype;
	}
	/**  
	 * fItemTime  
	 *  
	 * @return  the fItemTime  
	 * @since   1.0.0  
	*/  
	
	public Date getfItemTime() {
		return fItemTime;
	}
	/**  
	 * @param fItemTime the fItemTime to set  
	 */
	public void setfItemTime(Date fItemTime) {
		this.fItemTime = fItemTime;
	}
	/**  
	 * fPulishTime  
	 *  
	 * @return  the fPulishTime  
	 * @since   1.0.0  
	*/  
	
	public Date getfPulishTime() {
		return fPulishTime;
	}
	/**  
	 * @param fPulishTime the fPulishTime to set  
	 */
	public void setfPulishTime(Date fPulishTime) {
		this.fPulishTime = fPulishTime;
	}
	
}
