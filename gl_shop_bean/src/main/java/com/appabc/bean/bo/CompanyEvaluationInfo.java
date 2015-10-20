package com.appabc.bean.bo;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TCompanyEvaluation;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月8日 下午5:23:56
 */

public class CompanyEvaluationInfo extends TCompanyEvaluation {

	/**  
	 * serialVersionUID:用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -5736779773066261990L;

	/**
	 * 企业名称
	 */
	private String cname;
	
	/**
     * 企业类型（区分企业、船舶、个人）
     */
    private CompanyType ctype;
    
    /**
     * 用户名
     */
    private String username;

	/**  
	 * cname  
	 *  
	 * @return  the cname  
	 * @since   1.0.0  
	*/  
	
	public String getCname() {
		return cname;
	}

	/**  
	 * @param cname the cname to set  
	 */
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
	
}
