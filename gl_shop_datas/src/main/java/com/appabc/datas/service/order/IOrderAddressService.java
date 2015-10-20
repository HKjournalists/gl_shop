/**
 *
 */
package com.appabc.datas.service.order;

import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 询单或合同卸货地址service接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午3:49:08
 */
public interface IOrderAddressService extends IBaseService<TOrderAddress> {
	
	/**
	 * 复制一份询单的卸货地址，并将新的址与新的询单关联(包括卸货地址基本信息和卸货地址图片)
	 * @param newFid 新的询单ID
	 * @param oa
	 * @return 新的卸货地址ID
	 */
	public int copyTOrderAddressRelationshipsFid(String newFid, TOrderAddress oa);
	
	/**
	 * 根据询单ID删除卸货地址
	 * @param fid
	 */
	public void delByFid(String fid);
	
	/**
	 * 根据询单ID获取卸货地址信息（包括卸货地址图片信息）
	 * @param fid
	 * @return
	 */
	public TOrderAddress queryByFid(String fid);

}
