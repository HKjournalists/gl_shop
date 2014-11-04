package com.appabc.datas.dao.contract;

import java.util.List;

import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月2日 下午2:53:51
 */

public interface IContractDisPriceDAO extends IBaseDao<TOrderDisPrice> {
	
	/*查询当前合同下所有的议价信息，并返回结果按照议价时间的DESC排列*/
	List<TOrderDisPrice> queryForList(String contractId);
	
	/*查询合同的下议价历史记录信息*/
	List<TContractDisPriceOperation> queryGoodsDisPriceHisList(String contractId, String operateId, String disPriceId,String disType);
	
}
