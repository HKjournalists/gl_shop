package com.appabc.bean.bo;

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

	private String cname;

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
	
}
