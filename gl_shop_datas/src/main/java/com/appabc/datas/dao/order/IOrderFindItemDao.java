/**
 *
 */
package com.appabc.datas.dao.order;

import java.util.List;

import com.appabc.bean.pvo.TOrderFindItem;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 询单交易申请记录DAO接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 上午11:54:49
 */
public interface IOrderFindItemDao extends IBaseDao<TOrderFindItem>{
	
	/**
	 * 获取询单的感兴趣数
	 * @param fid
	 * @return
	 */
	public int countByFid(String fid);

	/**
	 * 获取询单的感兴趣list
	 * @param fid
	 * @return
	 */
	public List<TOrderFindItem> queryOrderFindItemListByFid(String fid);
	
}
