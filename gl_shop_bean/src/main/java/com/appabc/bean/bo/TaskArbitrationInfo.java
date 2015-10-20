package com.appabc.bean.bo;

import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.pvo.TOrderArbitration;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月3日 上午11:32:29
 */

public class TaskArbitrationInfo extends TOrderArbitration {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 7382827849589922381L;
	
	private String oid;
	
	private String sellerid;
	
	private String buyerid;
	
	private String title;
	
	private String susername;
	
	private String srealname;
	
	private String cid;
	
	private String tusername;
	
	private String tphone;
	
	private CompanyType tctype;

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
	 * sellerid  
	 *  
	 * @return  the sellerid  
	 * @since   1.0.0  
	 */
	
	public String getSellerid() {
		return sellerid;
	}

	/**  
	 * @param sellerid the sellerid to set  
	 */
	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	/**  
	 * buyerid  
	 *  
	 * @return  the buyerid  
	 * @since   1.0.0  
	 */
	
	public String getBuyerid() {
		return buyerid;
	}

	/**  
	 * @param buyerid the buyerid to set  
	 */
	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	/**  
	 * title  
	 *  
	 * @return  the title  
	 * @since   1.0.0  
	 */
	
	public String getTitle() {
		return title;
	}

	/**  
	 * @param title the title to set  
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**  
	 * susername  
	 *  
	 * @return  the susername  
	 * @since   1.0.0  
	 */
	
	public String getSusername() {
		return susername;
	}

	/**  
	 * @param susername the susername to set  
	 */
	public void setSusername(String susername) {
		this.susername = susername;
	}

	/**  
	 * srealname  
	 *  
	 * @return  the srealname  
	 * @since   1.0.0  
	 */
	
	public String getSrealname() {
		return srealname;
	}

	/**  
	 * @param srealname the srealname to set  
	 */
	public void setSrealname(String srealname) {
		this.srealname = srealname;
	}

	/**  
	 * cid  
	 *  
	 * @return  the cid  
	 * @since   1.0.0  
	 */
	
	public String getCid() {
		return cid;
	}

	/**  
	 * @param cid the cid to set  
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**  
	 * tusername  
	 *  
	 * @return  the tusername  
	 * @since   1.0.0  
	 */
	
	public String getTusername() {
		return tusername;
	}

	/**  
	 * @param tusername the tusername to set  
	 */
	public void setTusername(String tusername) {
		this.tusername = tusername;
	}

	/**  
	 * tphone  
	 *  
	 * @return  the tphone  
	 * @since   1.0.0  
	 */
	
	public String getTphone() {
		return tphone;
	}

	/**  
	 * @param tphone the tphone to set  
	 */
	public void setTphone(String tphone) {
		this.tphone = tphone;
	}

	/**  
	 * tctype  
	 *  
	 * @return  the tctype  
	 * @since   1.0.0  
	 */
	
	public CompanyType getTctype() {
		return tctype;
	}

	/**  
	 * @param tctype the tctype to set  
	 */
	public void setTctype(CompanyType tctype) {
		this.tctype = tctype;
	}
	
}
