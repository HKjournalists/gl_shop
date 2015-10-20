/**
 *
 */
package com.appabc.datas.dao.data;

import java.util.List;

import com.appabc.bean.bo.DataUserSingleCount;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 单个用户信息统计
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月22日 下午5:18:58
 */
public interface IDataUserSingleCountDao extends IBaseDao<DataUserSingleCount> {
	
	public QueryContext<DataUserSingleCount> queryListForPaginationOfDataUserSingleCount(
			QueryContext<DataUserSingleCount> qContext);

	/**
	 * 查询所有
	 * @return
	 */
	List<DataUserSingleCount> queryAllForList();

}
