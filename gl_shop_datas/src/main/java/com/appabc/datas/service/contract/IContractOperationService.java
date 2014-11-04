package com.appabc.datas.service.contract;

import java.util.List;

import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午2:57:38
 */

public interface IContractOperationService extends IBaseService<TOrderOperations> {
	
	/*确认合同信息*/
	TOrderOperations applyOrNotGoodsInfo(String contractId,String type,String operator,String result,String pid);
	
	/*验收通过和同意的接口*/
	TOrderOperations applyOrPassGoodsInfo(String contractId,String operType,String pid,String operator,String operatorName);
	
	/*确认卸货信息*/
	void confirmUninstallGoods(String contractId,String confirmer,String confirmerName) throws ServiceException;
	
	List<?> getContractChangeHistory(String contractId);
	
	List<TOrderOperations> queryForList(String contractId, String type);
	
	List<TOrderOperations> queryForListWithOIDAndOper(String contractId,String operator);
	
	List<TOrderOperations> queryForList(String contractId);
	
}
