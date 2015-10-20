/**
 *
 */
package com.appabc.bean.bo;

import java.util.List;

import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年8月18日 下午3:30:37
 */
public class ContractAllInfoBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 合同基本信息
	 */
	private TOrderInfo orderInfo;

	/**
	 * 状态其它修改
	 */
	private String statusRemark;
	
	/**
	 * 该合同的操作记录
	 */
	private List<TOrderOperations> opList;

	public TOrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(TOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public List<TOrderOperations> getOpList() {
		return opList;
	}

	public void setOpList(List<TOrderOperations> opList) {
		this.opList = opList;
	}
	

}
