package com.appabc.datas.cms.service;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.TaskArbitrationInfo;
import com.appabc.bean.bo.TaskContractInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年6月30日 下午2:12:49
 */

public interface IContractService extends IBaseService<TaskContractInfo> {
	
	void customerMakeAndMatchTOrderNoTask(OrderFindAllBean order, String sellerId, String buyerId, User operator) throws ServiceException;
	
	void customerMakeAndMatchTOrderHaveTask(OrderFindAllBean order, String beCid, User operator, Task<OrderAllInfor> task) throws ServiceException;
	
	void contractArbitractionProcess(boolean isTrade,String aid,User user,double num,double price,String note) throws ServiceException;
	
	void contractArbitractionProcess(boolean isTrade,String aid,User user,double num,double price,double finalAmount,String note) throws ServiceException;
	
	QueryContext<TaskContractInfo> getConfirmContractOrderList(QueryContext<TaskContractInfo> qContext);
	
	QueryContext<TaskArbitrationInfo> getContractArbitrationList(QueryContext<TaskArbitrationInfo> qContext);
	
}