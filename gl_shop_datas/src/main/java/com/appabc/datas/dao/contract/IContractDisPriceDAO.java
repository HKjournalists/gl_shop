package com.appabc.datas.dao.contract;

import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.common.base.dao.IBaseDao;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月2日 下午2:53:51
 */

public interface IContractDisPriceDAO extends IBaseDao<TOrderDisPrice> {
	
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
	 * description 查询当前合同下所有的议价信息，并返回结果按照议价时间的DESC排列
	 * @param lid
	 * @return List<TOrderDisPrice>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderDisPrice> queryOrderDisPriceListByOperId(String lid);
	
	/**
	 * description 根据参数查询合同的下议价历史记录信息
	 * @param contractId,operateId,disPriceId,disType
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> queryGoodsDisPriceHisList(String contractId, String operateId, String disPriceId,String disType);
	
	/**
	 * @Description 获取合同抽样验收议价历史记录:抽样中/抽样通过： 返回第一次议价列表
	 * @param oid
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> queryGoodsDisPriceHistroyListWithSampleCheck(String oid);
	
	/**
	 * @Description 获取合同全量验收议价历史记录:全量抽样中/全量抽样通过：返回第一次议价的最后一条，和 第二议价的列表
	 * @param oid
	 * @return List<TContractDisPriceOperation>
	 * @since 1.0 
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TContractDisPriceOperation> queryGoodsDisPriceHistroyListWithFullTakeover(String oid);
	
}
