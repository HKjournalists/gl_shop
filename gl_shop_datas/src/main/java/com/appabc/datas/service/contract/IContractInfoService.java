package com.appabc.datas.service.contract;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.bo.MatchOrderInfo;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 合同信息列表
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @since  : 2014年9月1日 下午6:29:18
 */

public interface IContractInfoService extends IBaseService<TOrderInfo>{

	/**
	 * @Description : 撮合生成一个合同
	 * @param fid:询单ID,cid:被撮合的参与者,totalNum:撮合货物的总量,price:撮合货物的价格
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo makeAndMatchATOrderInfo(String fid,String cid,String operator,float totalNum,float price) throws ServiceException;
	
	/**
	 * @Description : 撮合生成一个合同
	 * @param MatchOrderInfo moi 撮合合同的相关信息
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo makeAndMatchATOrderInfo(MatchOrderInfo moi) throws ServiceException;
	
	/**
	 * @Description : 获取合同详情信息
	 * @param id;合同ID
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo getOrderDetailInfo(String id);

	/**
	 * @Description : 获取合同详情信息
	 * @param contractId
	 * @return ContractInfoBean
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	ContractInfoBean getContractInfoById(String cid,String contractId);

	/**
	 * @Description : 买家支付合同货款
	 * @param contractId,userId,userName
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFunds(String contractId,String userId,String userName) throws ServiceException;

	/**
	 * @Description : 买家支付合同货款(线上)
	 * @param contractId,userId,userName
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFundsOnline(String contractId,String userId,String userName) throws ServiceException;

	/**
	 * @Description : 买家支付合同货款(线下)
	 * @param contractId,userId,userName
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFundsOffline(String contractId,String userId,String userName) throws ServiceException;

	/**
	 * description : 确认合同信息
	 * @param contractId,userId,userName
	 * @return 0是一方确认合同, 1是双方确认合同
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	int toConfirmContract(String contractId,String userId,String updaterName) throws ServiceException;

	/**
	 * description : 确认合同信息
	 * @param contractId,userId,userName
	 * @return List<TOrderOperations>
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	List<TOrderOperations> toConfirmContractRetOperator(String contractId,String userId,String updaterName) throws ServiceException;

	/**
	 * description : 通知卖家发货
	 * @param contractId,userId,userName
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void noticeShippingGoods(String contractId,String userId,String updaterName);

	/**
	 * description : 验证货物信息
	 * @param contractId,userId,userName
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void validateGoodsInfo(String contractId,String userId,String updaterName) throws ServiceException;

	/**
	 * description : 分页查询合同详情列表
	 * @param qContext
	 * @return QueryContext<ContractInfoBean>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
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
	
	/**
	 * 获取询单被撮合的次数
	 * @param fid 询单ID
	 * @return
	 */
	public int getMatchingNumByFid(String fid);

}
