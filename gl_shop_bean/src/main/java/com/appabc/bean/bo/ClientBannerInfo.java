package com.appabc.bean.bo;

import java.util.Date;

import com.appabc.bean.pvo.TClientBanner;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年8月19日 下午2:01:57
 */

public class ClientBannerInfo extends TClientBanner {

	 /**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 3798367569781986270L;

	/**
     * 文件名
     */
    private String fname;

    /**
     * 文件类型
     */
    private String ftype;

    /**
     * 文件大小
     */
    private Long fsize;

    /**
     * 文件目录
     */
    private String fpath;

    /**  
	 * fname  
	 *  
	 * @return  the fname  
	 * @since   1.0.0  
	 */
	
    /**
     * 全路径（直接外网访问路径）
     */
    private String fullpath;
    /**
     * 提交时间
     */
    private Date createdate;
	public String getFname() {
		return fname;
	}
	/**  
	 * @param fname the fname to set  
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**  
	 * ftype  
	 *  
	 * @return  the ftype  
	 * @since   1.0.0  
	 */
	
	public String getFtype() {
		return ftype;
	}
	/**  
	 * @param ftype the ftype to set  
	 */
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	/**  
	 * fsize  
	 *  
	 * @return  the fsize  
	 * @since   1.0.0  
	 */
	
	public Long getFsize() {
		return fsize;
	}
	/**  
	 * @param fsize the fsize to set  
	 */
	public void setFsize(Long fsize) {
		this.fsize = fsize;
	}
	/**  
	 * fpath  
	 *  
	 * @return  the fpath  
	 * @since   1.0.0  
	 */
	
	public String getFpath() {
		return fpath;
	}
	/**  
	 * @param fpath the fpath to set  
	 */
	public void setFpath(String fpath) {
		this.fpath = fpath;
	}
	/**  
	 * fullpath  
	 *  
	 * @return  the fullpath  
	 * @since   1.0.0  
	 */
	
	public String getFullpath() {
		return fullpath;
	}
	/**  
	 * @param fullpath the fullpath to set  
	 */
	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
	/**  
	 * createdate  
	 *  
	 * @return  the createdate  
	 * @since   1.0.0  
	 */
	
	public Date getCreatedate() {
		return createdate;
	}
	/**  
	 * @param createdate the createdate to set  
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
