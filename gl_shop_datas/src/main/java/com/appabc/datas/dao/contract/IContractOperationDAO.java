package com.appabc.datas.dao.contract;

import java.util.List;

import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 合同操作记录信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午2:36:40
 */

public interface IContractOperationDAO extends IBaseDao<TOrderOperations> {
	
	List<TOrderOperations> queryForList(String contractId, String type);
	
	List<TOrderOperations> queryForList(String contractId);
	
	List<TOrderOperations> queryForListWithOIDAndOper(String contractId,String operator);
}
