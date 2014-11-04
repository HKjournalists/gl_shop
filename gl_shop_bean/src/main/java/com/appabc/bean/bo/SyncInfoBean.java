/**
 *
 */
package com.appabc.bean.bo;

import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 同步接口信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月29日 下午2:18:38
 */
public class SyncInfoBean extends BaseBean{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 商品品类CODE
	 */
	private SyncDataBean goods;
	/**
	 * 港口分段
	 */
	private SyncDataBean riverSection;
	/**
	 * 交易地域
	 */
	private SyncDataBean area;
	/**
	 * 平台支持银行
	 */
	private SyncDataBean banks;
	
	/**  
	 *  商品子类
	 */  
	private SyncDataBean  goodChild;
	
	/**  
	 * 系统配置参数
	 */  
	private SyncDataBean  sysParam;

	public SyncDataBean getGoods() {
		return goods;
	}

	public void setGoods(SyncDataBean goods) {
		this.goods = goods;
	}

	public SyncDataBean getRiverSection() {
		return riverSection;
	}

	public void setRiverSection(SyncDataBean riverSection) {
		this.riverSection = riverSection;
	}

	public SyncDataBean getArea() {
		return area;
	}

	public void setArea(SyncDataBean area) {
		this.area = area;
	}

	public SyncDataBean getBanks() {
		return banks;
	}

	public void setBanks(SyncDataBean banks) {
		this.banks = banks;
	}

	public SyncDataBean getGoodChild() {
		return goodChild;
	}

	public void setGoodChild(SyncDataBean goodChild) {
		this.goodChild = goodChild;
	}

	public SyncDataBean getSysParam() {
		return sysParam;
	}

	public void setSysParam(SyncDataBean sysParam) {
		this.sysParam = sysParam;
	}

}
