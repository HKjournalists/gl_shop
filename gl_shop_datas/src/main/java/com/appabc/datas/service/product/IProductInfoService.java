/**
 *
 */
package com.appabc.datas.service.product;

import java.util.List;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 商品基本信息SERVICE接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午2:44:11
 */
public interface IProductInfoService extends IBaseService<TProductInfo>{
	
	/**
	 * 添加商品和商品属性
	 * @param productInfo
	 * @param productPropertyList
	 */
	public void add(TProductInfo productInfo, List<TProductProperty> productPropertyList);
	
	/**
	 * 修改商品及属性
	 * @param productInfo
	 * @param productPropertyList
	 */
	public void modify(TProductInfo productInfo, List<TProductProperty> productPropertyList);
	
	/**
	 * 根据商品大类查找商品
	 * @param pCode
	 */
	public List<TProductInfo> queryByPcode(String pCode);

}
