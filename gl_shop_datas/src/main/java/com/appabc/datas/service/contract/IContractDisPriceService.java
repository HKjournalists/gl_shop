package com.appabc.datas.service.contract;

import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午2:58:52
 */

public interface IContractDisPriceService extends IContractBaseService<TOrderDisPrice> {

	/**
	 * description 查询当前合同下所有的议价信息，并返回结果按照议价时间的DESC排列
	 * @param contractId
	 * @return List<TOrderDisPrice>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderDisPrice> queryForList(String contractId);
	
	/**
	 * @Description 查询当前合同下所有的议价信息，并返回结果按照议价时间的DESC排列
	 * @param lid
	 * @return List<TOrderDisPrice>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderDisPrice> getOrderDisPriceListByOperId(String lid);
	
	/**
	 * @Description 合同议价信息
	 * @param oid,cname,bean
	 * @return void
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	void validateGoodsDisPrice(String oid,String cname,TOrderDisPrice bean) throws ServiceException;
	
	/**
	 * @Description 获取合同历史议价记录，包括操作信息和议价信息
	 * @param oid,lid,disPriceId,disPriceType
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> getGoodsDisPriceHisList(String oid,String lid, String disPriceId,String disPriceType);
	
	/**
	 * @Description 获取合同抽样验收议价历史记录:抽样中/抽样通过： 返回第一次议价列表
	 * @param oid
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> getGoodsDisPriceHistroyListWithSampleCheck(String oid);
	
	/**
	 * @Description 获取合同全量验收议价历史记录:全量抽样中/全量抽样通过：返回第一次议价的最后一条，和 第二议价的列表
	 * @param oid
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> getGoodsDisPriceHistroyListWithFullTakeover(String oid);
	
	/**
	 * @Description 买家确认合同货款和货物申请结算
	 * @param oid,cid,cname,totalNum,price
	 * @return void
	 * @since 1.0 
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void applyContractFinalEstimate(String oid,String cid,String cname,double price,double num) throws ServiceException;
	
	/**
	 * @Description 买家确认合同货款和货物申请结算
	 * @param oid,cid,cname,totalNum,price,finalAmount
	 * @return void
	 * @since 1.0 
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void applyContractFinalEstimate(String oid,String cid,String cname,double price,double num,double finalAmount) throws ServiceException;
	
	/**
	 * @Description 卖家同意确认合同货款和货物申请结算
	 * @param oid,cid,cname,flid
	 * @return void
	 * @since 1.0 
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void agreeContractFinalEstimate(String oid,String cid,String cname,String flid) throws ServiceException;
	
	/**
	 * @Description JOB自动同意卖家确认合同货款和货物申请结算
	 * @param oid,cid,cname,flid
	 * @return void
	 * @since 1.0 
	 * @throws ServiceException
	 * @author Bill Huang
	 * */
	void jobAutoAgreeContractFinalEstimate(TOrderInfo bean,String cid,String cname,String flid) throws ServiceException;
	
}
