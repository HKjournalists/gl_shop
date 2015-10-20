/**
 *
 */
package com.appabc.datas.service.order;

import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 交易中的商品信息SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午9:49:48
 */
public interface IOrderProductInfoService extends IBaseService<TOrderProductInfo> {
	
	/**
	 * 复制一份交易中的商品信息(包括商品属性、商品规格、商品图片)
	 * @param opiid 交易中的商品ID
	 * @return newOpiid
	 */
	public int copyOrderProductAllInfo(int opiid);

}
