/**
 *
 */
package com.appabc.datas.service.order;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 询单SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月10日 下午4:07:39
 */
public interface IOrderFindService extends IBaseService<TOrderFind>{
	
	/**
	 * 发布询单
	 * @param ofBean
	 * @param opiBean
	 * @param addressid 企业卸货地址ID
	 */
	public void orderPublish(TOrderFind ofBean, TOrderProductInfo opiBean, String addressid);
	
	/**
	 * 根据询单ID获取详情信息，包括商品信息和卸货地址
	 * @param fid
	 * @return
	 */
	public OrderAllInfor queryInfoById(String fid);
	
	/**
	 * @param fid
	 * @param userid 操作人ID
	 * @return
	 */
	public String cancel(String fid, String userid);
	
	/**
	 * 更新询单
	 * @param ofBean
	 * @param oaBean
	 * @param opiBean
	 */
	public void updateOrderAllInfo(TOrderFind ofBean, TOrderAddress oaBean, TOrderProductInfo opiBean);

}
