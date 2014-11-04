package com.appabc.datas.service.contract;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 合同信息列表
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @since  : 2014年9月1日 下午6:29:18
 */

public interface IContractInfoService extends IBaseService<TOrderInfo>{
	
	/*获取合同详情信息*/
	TOrderInfo getOrderDetailInfo(String id);
	/**
	 * description : 获取合同详情信息
	 * @param contractId
	 * @return ContractInfoBean
	 * @since 
	 * @throws ServiceException
	 * @author Administrator
	 * */
	ContractInfoBean getContractInfoById(String cid,String contractId);
	/*买家支付合同货款*/
	TOrderInfo payContractFunds(String contractId,String userId,String userName) throws ServiceException;
	/*买家支付合同货款(线上)*/
	TOrderInfo payContractFundsOnline(String contractId,String userId,String userName) throws ServiceException;
	/*买家支付合同货款(线下)*/
	TOrderInfo payContractFundsOffline(String contractId,String userId,String userName) throws ServiceException;
	
	/**
	 * description : 确认合同信息
	 * @param contractId,userId,userName
	 * @return 0是一方确认合同, 1是双方确认合同
	 * @since 
	 * @throws ServiceException
	 * @author Administrator
	 * */
	int toConfirmContract(String contractId,String userId,String updaterName) throws ServiceException;
	
	/*通知卖家发货*/
	void noticeShippingGoods(String contractId,String userId,String updaterName);
	
	/*验证货物信息*/
	void validateGoodsInfo(String contractId,String userId,String updaterName) throws ServiceException;
	
	QueryContext<ContractInfoBean> queryContractInfoListForPagination(QueryContext<ContractInfoBean> qContext);
	
	/**
	 * 获取企业的交易成功率
	 * @param cid
	 * @return
	 */
	EvaluationInfoBean getTransactionSuccessRate(String cid);
	
	/**
	 * 判断2个企业是否交易过
	 * @param cid1
	 * @param cid2
	 * @return
	 */
	boolean isOldCustomer(String cid1, String cid2);
	
	/**
	 * 获取企业的合同数(所有状态)
	 * @param cid
	 * @return
	 */
	int getTotalByCid(String cid);
	
}
