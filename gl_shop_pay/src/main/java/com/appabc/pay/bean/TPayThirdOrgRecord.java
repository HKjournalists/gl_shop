/**  
 * com.appabc.pay.bean.TPayThirdOrgRecord.java  
 *   
 * 2015年3月2日 下午2:37:04  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月2日 下午2:37:04
 */

public class TPayThirdOrgRecord extends BaseBean {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 4944439411249808784L;
	
	/**
	 * `OID` varchar(50) DEFAULT NULL COMMENT '其他业务编号',
     * 其他业务编号 
     */
    private String oid;
    
    /**
	 * `PARAMSCONTENT` text COMMENT '请求参数内容',
     * 请求参数内容 
     */
    private String paramsContent;
    
    /**
	 * `TYPE` int(3) DEFAULT NULL COMMENT '支付操作类型，支付请求，支付返回',
     * 操作类型 
     */
    private int type;
    
    /**
	 * `TRADETIME` datetime DEFAULT NULL COMMENT '交易时间',
     * 交易时间 
     */
    private Date tradeTime;
	
    /**
	 * `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
     * 创建时间 
     */
    private Date createTime;
    
    /**
	 * `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
     * 更新时间 
     */
    private Date updateTime;
    
    /**
	 * `CREATOR` varchar(50) DEFAULT NULL COMMENT '创建人',
     * 创建人 
     */
    private String creator;

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
	 * paramsContent  
	 *  
	 * @return  the paramsContent  
	 * @since   1.0.0  
	 */
	
	public String getParamsContent() {
		return paramsContent;
	}

	/**  
	 * @param paramsContent the paramsContent to set  
	 */
	public void setParamsContent(String paramsContent) {
		this.paramsContent = paramsContent;
	}

	/**  
	 * type  
	 *  
	 * @return  the type  
	 * @since   1.0.0  
	 */
	
	public int getType() {
		return type;
	}

	/**  
	 * @param type the type to set  
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**  
	 * tradeTime  
	 *  
	 * @return  the tradeTime  
	 * @since   1.0.0  
	 */
	
	public Date getTradeTime() {
		return tradeTime;
	}

	/**  
	 * @param tradeTime the tradeTime to set  
	 */
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**  
	 * createTime  
	 *  
	 * @return  the createTime  
	 * @since   1.0.0  
	 */
	
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * @param createTime the createTime to set  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**  
	 * updateTime  
	 *  
	 * @return  the updateTime  
	 * @since   1.0.0  
	 */
	
	public Date getUpdateTime() {
		return updateTime;
	}

	/**  
	 * @param updateTime the updateTime to set  
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**  
	 * creator  
	 *  
	 * @return  the creator  
	 * @since   1.0.0  
	 */
	
	public String getCreator() {
		return creator;
	}

	/**  
	 * @param creator the creator to set  
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
    
    
}
