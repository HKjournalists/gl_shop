/**
 *
 */
package com.appabc.bean.bo;

import java.util.Date;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 询单代发VO显示
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年6月30日 下午5:46:40
 */
public class OrderFindInsteadBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946706199717075415L;
	
	/**
	 * 登录账号
	 */
	private String username;
	/**
	 * 企业ID
	 */
	private String cid;
	/**
	 * 企业名称
	 */
	private String cname;
	/**
     * 企业类型（区分企业、船舶、个人）
     */
    private CompanyType ctype;
	/**
	 * 询单标题
	 */
	private String title;
	/**
	 * 询单
	 */
	private String fid;
	/**
	 * 操作人ID
	 */
	private String operatorId;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作时间
	 */
	private Date operationTime;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

}
