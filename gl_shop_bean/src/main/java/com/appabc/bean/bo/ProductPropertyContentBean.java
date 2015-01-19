/**
 *
 */
package com.appabc.bean.bo;

import com.appabc.common.base.bean.BaseBean;


/**
 * @Description : 用户传递商品属性值与商品属性ID的对应关系
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月31日 下午3:06:35
 */
public class ProductPropertyContentBean extends BaseBean{
	
	/**
	 * ppid对应 T_PRODUCT_PROPERTY中的ID或T_ORDER_PRODUCT_PROPERTY中PPROID
	 */
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4249991407325609611L;
	/**
     * 商品属性ID
     */
    private String ppid;
    /**
     * 商品属性值
     */
    private String content;

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
