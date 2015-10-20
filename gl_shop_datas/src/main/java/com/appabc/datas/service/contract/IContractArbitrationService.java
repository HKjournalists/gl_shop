package com.appabc.datas.service.contract;

import com.appabc.bean.bo.ContractArbitrationBean;
import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.exception.ServiceException;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月3日 下午3:04:06
 */

public interface IContractArbitrationService extends IContractBaseService<TOrderArbitration> {

	/**
	 * @Description 合同仲裁操作
	 * @param contractId,operator,operatorName
	 * @return TOrderArbitration
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderArbitration toContractArbitration(String oid,String cid,String cname,String flid) throws ServiceException;
	
	/**
	 * @Description 合同咨询客服操作
	 * @param contractId,operator,operatorName
	 * @return TOrderArbitration
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderArbitration toConsultingService(String oid,String cid,String cname) throws ServiceException;
	
	/**
	 * @Description 合同仲裁处理操作
	 * @param isTrade,aid,cid,totalnum,price
	 * @return void
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	void contractArbitractionProcess(boolean isTrade,String aid,String cid,String cname,double num,double price) throws ServiceException;
	
	/**
	 * @Description 合同仲裁处理操作
	 * @param isTrade,aid,cid,totalnum,price,result
	 * @return void
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	void contractArbitractionProcess(boolean isTrade,String aid,String cid,String cname,double num,double price,String result) throws ServiceException;
	
	/**
	 * @Description 合同仲裁处理操作
	 * @param isTrade,aid,cid,totalnum,price,result
	 * @return void
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	void contractArbitractionProcess(boolean isTrade,String aid,String cid,String cname,double num,double price,double amount,String result) throws ServiceException;
	
	/**
	 * @Description 获取合同仲裁历史
	 * @param contractId
	 * @return List<?>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<?> getContractArbitrationHistroy(String oid);
	
	/**
	 * @Description 获取合同仲裁记录
	 * @param ContractArbitrationStatus
	 * @return List<ContractArbitrationBean>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<ContractArbitrationBean> getContractArbitrationInfoForList(ContractArbitrationStatus status);
	
	/**
	 * @Description 分页查询获取合同仲裁记录
	 * @param qContext
	 * @return QueryContext<ContractArbitrationBean>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	QueryContext<ContractArbitrationBean> getContractArbitrationInfoListForPagination(QueryContext<ContractArbitrationBean> qContext);
}
