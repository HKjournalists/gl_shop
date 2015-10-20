package com.appabc.datas.cms.dao;

import com.appabc.bean.bo.TaskArbitrationInfo;
import com.appabc.bean.bo.TaskContractInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月1日 上午10:06:01
 */

public interface IContractDAO extends IBaseDao<TaskContractInfo>{
	
	QueryContext<TaskContractInfo> getConfirmContractOrderList(QueryContext<TaskContractInfo> qContext);
	
	QueryContext<TaskArbitrationInfo> getContractArbitrationList(QueryContext<TaskArbitrationInfo> qContext);
	
}
