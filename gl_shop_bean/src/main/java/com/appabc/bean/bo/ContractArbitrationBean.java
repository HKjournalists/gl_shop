package com.appabc.bean.bo;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TOrderArbitration;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月25日 下午3:30:15
 */

public class ContractArbitrationBean extends TOrderArbitration {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 合同ID
     */
	private String oid;
	
	/**
     * 企业名称
     */
    private String cname;

    /**
     * 企业类型（区分企业、船舶、个人）
     */
    private CompanyType ctype;

    /**
     * 合同标题
     */
    private String coTitle;

	/**  
	 * oid  
	 *  
	 * @return  the oid  
	 * @since   1.0.0  
	 */
	
	public String getOid() {
		return oid;
	}

	/**  
	 * @param oid the oid to set  
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

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

	/**  
	 * ctype  
	 *  
	 * @return  the ctype  
	 * @since   1.0.0  
	 */
	
	public CompanyType getCtype() {
		return ctype;
	}

	/**  
	 * @param ctype the ctype to set  
	 */
	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
	}

	/**  
	 * coTitle  
	 *  
	 * @return  the coTitle  
	 * @since   1.0.0  
	 */
	
	public String getCoTitle() {
		return coTitle;
	}

	/**  
	 * @param coTitle the coTitle to set  
	 */
	public void setCoTitle(String coTitle) {
		this.coTitle = coTitle;
	}
    
}
