package com.appabc.datas.dao.contract;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TOrderMine;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月24日 下午5:29:52
 */

public interface IContractMineDAO extends IBaseDao<TOrderMine> {
	
	/**
	 * description 根据合同ID和企业ID查询我的合同信息
	 * @param oid,cid
	 * @return TOrderMine
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	TOrderMine queryOrderMineWithCidOid(String oid,String cid);
	
	/**
	 * @Description : 根据合同ID,企业ID,状态,生命周期,操作人
	 * @param String oid,String cid,
	 * 		  ContractStatus status,
	 *        ContractLifeCycle lifeCycle,
	 *        String operator
	 * @return boolean
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	boolean saveOrUpdateMineContractWithCidOid(String oid,String cid,ContractStatus status,ContractLifeCycle lifeCycle,String operator);
	
}
