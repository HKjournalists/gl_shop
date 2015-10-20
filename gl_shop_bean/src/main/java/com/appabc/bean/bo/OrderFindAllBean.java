/**
 *
 */
package com.appabc.bean.bo;

import java.util.List;

import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月4日 下午1:56:52
 */
public class OrderFindAllBean extends BaseBean{
	
	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -5111472913785970465L;

	/**
	 * 询单基本信息
	 */
	private TOrderFind ofBean;
	
	/**
	 * 询单卸货地址
	 */
	private TOrderAddress oaBean;
	
	/**
	 * 询单的商品基本信息
	 */
	private TOrderProductInfo opiBean;
	
	/**
	 * 询单的商品属性信息
	 */
	List<ProductPropertyContentBean> ppcList;

	public TOrderFind getOfBean() {
		return ofBean;
	}

	public void setOfBean(TOrderFind ofBean) {
		this.ofBean = ofBean;
	}

	public TOrderAddress getOaBean() {
		return oaBean;
	}

	public void setOaBean(TOrderAddress oaBean) {
		this.oaBean = oaBean;
	}

	public TOrderProductInfo getOpiBean() {
		return opiBean;
	}

	public void setOpiBean(TOrderProductInfo opiBean) {
		this.opiBean = opiBean;
	}

	public List<ProductPropertyContentBean> getPpcList() {
		return ppcList;
	}

	public void setPpcList(List<ProductPropertyContentBean> ppcList) {
		this.ppcList = ppcList;
	}

}
