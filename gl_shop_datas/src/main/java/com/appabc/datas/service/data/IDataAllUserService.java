/**
 *
 */
package com.appabc.datas.service.data;

import java.util.List;

import com.appabc.bean.bo.DataUserSingleCount;
import com.appabc.bean.pvo.TDataAllUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年7月17日 下午3:03:10
 */
public interface IDataAllUserService extends IBaseService<TDataAllUser> {
	
	/**
	 * 单个用户信息统计
	 * @param qContext
	 * @return
	 */
	public QueryContext<DataUserSingleCount> queryListForPaginationOfDataUserSingleCount(
			QueryContext<DataUserSingleCount> qContext);
	
	/**
	 * 单个用户信息统计（查询所有）
	 * @return
	 */
	List<DataUserSingleCount> queryAllUserSingleCountForList();
	
	void jobAutoSendAllUserDataWithEmail() throws ServiceException;

}
