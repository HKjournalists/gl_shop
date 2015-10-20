package com.appabc.datas.dao.contract;

import com.appabc.bean.bo.ContractArbitrationBean;
import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午2:59:54
 */

public interface IContractArbitrationDAO extends IBaseDao<TOrderArbitration> {
	
	/**
	 * @description 通过合同编号获取仲裁列表信息
	 * @param contractId
	 * @return List<TOrderArbitration>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderArbitration> queryArbitrationForList(String contractId);
	
	/**
	 * @description 获取合同仲裁信息记录
	 * @param ContractArbitrationStatus
	 * @return List<ContractArbitrationBean>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<ContractArbitrationBean> queryContractArbitrationInfoForList(ContractArbitrationStatus status);
	
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
