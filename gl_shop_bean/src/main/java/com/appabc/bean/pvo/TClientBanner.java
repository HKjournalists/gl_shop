package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年8月4日 下午2:33:22
 */

public class TClientBanner extends BaseBean {

	/**  
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 2597397308906827282L;

	private String bid;
	private ClientType btype;
	private String bname;
	private String sortimgid;
	private String thumedid;
	private Integer orderno;
	private String creater;
	private Date createtime;
	private String updater;
	private Date updatetime;
	private String targeturl;
	private String remark;
	 
	/**  
	 * bid  
	 *  
	 * @return  the bid  
	 * @since   1.0.0  
	 */
	
	public String getBid() {
		return bid;
	}
	/**  
	 * @param bid the bid to set  
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}
	/**  
	 * btype  
	 *  
	 * @return  the btype  
	 * @since   1.0.0  
	 */
	
	public ClientType getBtype() {
		return btype;
	}
	/**  
	 * @param btype the btype to set  
	 */
	public void setBtype(ClientType btype) {
		this.btype = btype;
	}
	/**  
	 * bname  
	 *  
	 * @return  the bname  
	 * @since   1.0.0  
	 */
	
	public String getBname() {
		return bname;
	}
	/**  
	 * @param bname the bname to set  
	 */
	public void setBname(String bname) {
		this.bname =bname;
	}
	/**  
	 * sortingid  
	 *  
	 * @return  the sortingid  
	 * @since   1.0.0  
	 */
	
	public String getSortimgid() {
		return sortimgid;
	}
	/**  
	 * @param sortingid the sortingid to set  
	 */
	public void setSortimgid(String sortimgid) {
		this.sortimgid = sortimgid;
	}
	/**  
	 * thumedid  
	 *  
	 * @return  the thumedid  
	 * @since   1.0.0  
	 */
	
	public String getThumedid() {
		return thumedid;
	}
	/**  
	 * @param thumedid the thumedid to set  
	 */
	public void setThumedid(String thumedid) {
		this.thumedid = thumedid;
	}
	/**  
	 * orderno  
	 *  
	 * @return  the orderno  
	 * @since   1.0.0  
	 */
	
	public Integer getOrderno() {
		return orderno;
	}
	/**  
	 * @param order the order to set  
	 */
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	/**  
	 * creater  
	 *  
	 * @return  the creater  
	 * @since   1.0.0  
	 */
	
	public String getCreater() {
		return creater;
	}
	/**  
	 * @param creater the creater to set  
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
	/**  
	 * createtime  
	 *  
	 * @return  the createtime  
	 * @since   1.0.0  
	 */
	
	public Date getCreatetime() {
		return createtime;
	}
	/**  
	 * @param createtime the createtime to set  
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**  
	 * updater  
	 *  
	 * @return  the updater  
	 * @since   1.0.0  
	 */
	
	public String getUpdater() {
		return updater;
	}
	/**  
	 * @param updater the updater to set  
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	/**  
	 * updatetime  
	 *  
	 * @return  the updatetime  
	 * @since   1.0.0  
	 */
	
	public Date getUpdatetime() {
		return updatetime;
	}
	/**  
	 * @param updatetime the updatetime to set  
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	/**  
	 * remark  
	 *  
	 * @return  the remark  
	 * @since   1.0.0  
	 */
	
	public String getRemark() {
		return remark;
	}
	/**  
	 * @param remark the remark to set  
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**  
	 * targeturl  
	 *  
	 * @return  the targeturl  
	 * @since   1.0.0  
	 */
	
	public String getTargeturl() {
		return targeturl;
	}
	/**  
	 * @param targeturl the targeturl to set  
	 */
	public void setTargeturl(String targeturl) {
		this.targeturl = targeturl;
	}

	
}
