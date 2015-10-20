/**
 *
 */
package com.appabc.bean.bo;

import java.util.Date;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description :单个用户信息统计
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 杨跃红
 * @version : 1.0 Create Date : 2015年7月22日 下午5:19:59
 */
public class DataUserSingleCount extends BaseBean {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 注册日期
	 */
	private Date regdate;
	/**
	 * 企业名称
	 */
	private String cname;
	/**
	 * 企业类型
	 */
	private CompanyType ctype;
	/**
	 * 企业ID
	 */
	private String cid;
	/**
	 * 联系人名称
	 */
	private String contactname;
	/**
	 * 联系人手机
	 */
	private String phone;
	/**
	 * 联系人座机
	 */
	private String tel;
	/**
	 * 保证金余额
	 */
	private double guaranty;
	/**
	 * 货款余额
	 */
	private double deposit;
	/**
	 * 认证状态
	 */
	private String authstatus;
	/**
	 * 认证结果
	 */
	private String authresult;
	/**
	 * 有效询单数-购买
	 */
	private int buynum;
	/**
	 * 有效询单数-出售
	 */
	private int sellnum;
	/**
	 * 已结束的合同数
	 */
	private int contractnumend;
	/**
	 * 进行中的合同数
	 */
	private int contractnuming;
	
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
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public double getGuaranty() {
		return guaranty;
	}
	public void setGuaranty(double guaranty) {
		this.guaranty = guaranty;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public String getAuthstatus() {
		return authstatus;
	}
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	public String getAuthresult() {
		return authresult;
	}
	public void setAuthresult(String authresult) {
		this.authresult = authresult;
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
	public int getContractnumend() {
		return contractnumend;
	}
	public void setContractnumend(int contractnumend) {
		this.contractnumend = contractnumend;
	}
	public int getContractnuming() {
		return contractnuming;
	}
	public void setContractnuming(int contractnuming) {
		this.contractnuming = contractnuming;
	}
	
}
