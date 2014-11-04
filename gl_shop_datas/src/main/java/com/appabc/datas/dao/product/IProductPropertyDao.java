/**
 *
 */
package com.appabc.datas.dao.product;

import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 商品其它属性DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午3:34:20
 */
public interface IProductPropertyDao extends IBaseDao<TProductProperty>{
	
	/**
	 * 根据商品ID删除其属性
	 * @param pid 商品ID
	 */
	public void delByPid(String pid);

}
