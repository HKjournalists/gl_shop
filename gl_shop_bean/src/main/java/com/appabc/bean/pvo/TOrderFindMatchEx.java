package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.CompanyInfo.CompanyType;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年6月4日 下午4:14:49
 */

public class TOrderFindMatchEx extends TOrderFindMatch {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 5347937045874580568L;
	
	private String oPhone;
	
	private String tPhone;
	
	private CompanyType oCtype;
	
	private CompanyType tCtype;
	
	private String oCname; // 企业名称
	
	private String tCname; // 企业名称
	
	private Date fItemTime; //交易询盘时间
	
	private Date fPulishTime; //询单发布时间

	/**  
	 * oPhone  
	 *  
	 * @return  the oPhone  
	 * @since   1.0.0  
	 */
	
	public String getoPhone() {
		return oPhone;
	}

	/**  
	 * @param oPhone the oPhone to set  
	 */
	public void setoPhone(String oPhone) {
		this.oPhone = oPhone;
	}

	/**  
	 * tPhone  
	 *  
	 * @return  the tPhone  
	 * @since   1.0.0  
	 */
	
	public String gettPhone() {
		return tPhone;
	}

	/**  
	 * @param tPhone the tPhone to set  
	 */
	public void settPhone(String tPhone) {
		this.tPhone = tPhone;
	}

	/**  
	 * oCtype  
	 *  
	 * @return  the oCtype  
	 * @since   1.0.0  
	 */
	
	public CompanyType getoCtype() {
		return oCtype;
	}

	/**  
	 * @param oCtype the oCtype to set  
	 */
	public void setoCtype(CompanyType oCtype) {
		this.oCtype = oCtype;
	}

	/**  
	 * tCtype  
	 *  
	 * @return  the tCtype  
	 * @since   1.0.0  
	 */
	
	public CompanyType gettCtype() {
		return tCtype;
	}

	/**  
	 * @param tCtype the tCtype to set  
	 */
	public void settCtype(CompanyType tCtype) {
		this.tCtype = tCtype;
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

	/**  
	 * oCname  
	 *  
	 * @return  the oCname  
	 * @since   1.0.0  
	*/  
	
	public String getoCname() {
		return oCname;
	}

	/**  
	 * @param oCname the oCname to set  
	 */
	public void setoCname(String oCname) {
		this.oCname = oCname;
	}

	/**  
	 * tCname  
	 *  
	 * @return  the tCname  
	 * @since   1.0.0  
	*/  
	
	public String gettCname() {
		return tCname;
	}

	/**  
	 * @param tCname the tCname to set  
	 */
	public void settCname(String tCname) {
		this.tCname = tCname;
	}

}
