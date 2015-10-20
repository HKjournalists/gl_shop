package com.appabc.datas.service.contract;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.bo.EvaluationInfoBean;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.common.base.QueryContext;
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

public interface IContractInfoService extends IContractBaseService<TOrderInfo>{

	/**
	 * @Description : 撮合生成一个合同
	 * @param bean:询单相关信息,cid:被撮合的参与者,operator:操作人员
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo makeAndMatchATOrderInfo(OrderFindAllBean bean,String cid,String operator) throws ServiceException;
	
	/**
	 * @Description : 客服人员撮合生成一个合同
	 * @param bean:询单相关信息,sellerCid:卖家CID,buyerCid:买家CID,operator:操作人员
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo makeAndMatchTOrderWithCustomService(OrderFindAllBean bean,String sellerCid,String buyerCid,String operator) throws ServiceException;
	
	/**
	 * @Description : 获取合同详情信息
	 * @param id;合同ID
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderInfo getOrderDetailInfo(String oid);

	/**
	 * @Description : 获取合同详情信息
	 * @param contractId
	 * @return ContractInfoBean
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	ContractInfoBean getContractInfoById(String cid,String oid);

	/**
	 * @Description : 买家支付合同货款
	 * @param contractId,userId,userName
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFunds(String oid,String cid,String cname) throws ServiceException;
	
	/**
	 * @Description : 买家支付合同货款[支持用户自己定义货款数量]
	 * @param contractId,userId,userName,amount
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFunds(String oid,String cid,String cname,double amount) throws ServiceException;

	/**
	 * @Description : 买家支付合同货款(线上)
	 * @param contractId,userId,userName
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFundsOnline(String oid,String cid,String cname) throws ServiceException;

	/**
	 * @Description : 买家支付合同货款(线下)
	 * @param contractId,userId,userName
	 * @return TOrderInfo
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	TOrderInfo payContractFundsOffline(String oid,String cid,String cname) throws ServiceException;

	/**
	 * description : 确认合同信息
	 * @param contractId,userId,userName
	 * @return 0是一方确认合同, 1是双方确认合同
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	int toConfirmContract(String oid,String cid,String cname) throws ServiceException;

	/**
	 * description : 确认合同信息
	 * @param contractId,userId,userName
	 * @return List<TOrderOperations>
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	List<TOrderOperations> toConfirmContractRetOperator(String oid,String cid,String cname) throws ServiceException;

	/**
	 * description : 通知卖家发货
	 * @param contractId,userId,userName
	 * @return void
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	void noticeShippingGoods(String oid,String cid,String cname);

	/**
	 * description : 验证货物信息
	 * @param contractId,userId,userName
	 * @return void
	 * @since 1.0
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void validateGoodsInfo(String oid,String cid,String cname) throws ServiceException;

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
	 * description : 分页查询我的合同详情列表
	 * @param qContext
	 * @return QueryContext<ContractInfoBean>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<ContractInfoBean> queryContractListOfMineForPagination(QueryContext<ContractInfoBean> qContext);
	
	
	/**
	 * @description : 分页查询我的合同详情列表,给后台CMS提供的接口.
	 * 	参数：cid :查询属于当前cid的合同;type:分为当前交易状态和历史交易状态.
	 * @param qContext
	 * @return QueryContext<TOrderInfo>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<TOrderInfo> queryContractListOfMineForPaginationToWebCms(QueryContext<TOrderInfo> qContext);

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
	
	public int queryCount(TOrderInfo entity);
	
	/**
	 * 统计已完成合同数（有结算的，包括正常结束+仲裁+取消+违约）
	 * @return
	 */
	public int queryCountOfFinished();

}
