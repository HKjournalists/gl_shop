/**
 *
 */
package com.appabc.datas.dao.product;

import com.appabc.bean.pvo.TProductInfo;
import com.appabc.common.base.dao.IBaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * @Description : 商品基本信息DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午2:34:47
 */
public interface IProductInfoDao extends IBaseDao<TProductInfo>{
	
	/**
	 * 根据商品类型code查询商品
	 * @param pcode 商品类型code
	 * @return
	 */
	public List<TProductInfo> queryByPcode(String pcode);
	
	/**
	 * 根据ID查询商品基本信息
	 * @param id
	 * @return
	 */
	public TProductInfo queryByid(Serializable id);

}
