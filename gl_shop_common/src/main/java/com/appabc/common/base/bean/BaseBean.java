package com.appabc.common.base.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description : global base bean
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月22日 下午5:41:28
 */

public abstract class BaseBean implements Serializable, Cloneable, Comparable<BaseBean> {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 * @since 1.0.0  
	 */  
	private static final long serialVersionUID = 1L;
	
	/**主键*/
	private String id;

	/**  
	 * id  
	 *  
	 * @return  the id  
	 * @since   1.0.0  
	 */
	
	public String getId() {
		return id;
	}

	/**  
	 * @param id the id to set  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)  
	 * @see java.lang.Comparable#compareTo(java.lang.Object)  
	 */
	public int compareTo(BaseBean o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}
	
	/*
	 * global toString method
	 * 
	 * */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/* (non-Javadoc)  
	 * @see java.lang.Object#clone()  
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
