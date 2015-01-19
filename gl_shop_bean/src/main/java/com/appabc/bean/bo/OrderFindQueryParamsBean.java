/**
 *
 */
package com.appabc.bean.bo;

import java.util.Date;

import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 找买找卖查询参数Bean
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年1月6日 下午5:18:18
 */
public class OrderFindQueryParamsBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 地区代码-省
	 */
	private String[] areaCodeProvince;
	/**
	 * 地区代码-区
	 */
	private String[] areaCodeArea;
	/**
	 * 商品ID数组，对应T_PRODUCT_INFO表中的ID
	 */
	private String[] pids;
	
	/**
	 * 购买，出售
	 */
	private OrderTypeEnum type;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String[] getAreaCodeProvince() {
		return areaCodeProvince;
	}
	public void setAreaCodeProvince(String[] areaCodeProvince) {
		this.areaCodeProvince = areaCodeProvince;
	}
	public String[] getAreaCodeArea() {
		return areaCodeArea;
	}
	public void setAreaCodeArea(String[] areaCodeArea) {
		this.areaCodeArea = areaCodeArea;
	}
	public String[] getPids() {
		return pids;
	}
	public void setPids(String[] pids) {
		this.pids = pids;
	}
	public OrderTypeEnum getType() {
		return type;
	}
	public void setType(OrderTypeEnum type) {
		this.type = type;
	}
	
}
