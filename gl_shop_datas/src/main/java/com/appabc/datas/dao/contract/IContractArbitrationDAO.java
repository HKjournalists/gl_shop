package com.appabc.datas.dao.contract;

import java.util.List;

import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午2:59:54
 */

public interface IContractArbitrationDAO extends IBaseDao<TOrderArbitration> {
	
	List<TOrderArbitration> queryArbitrationForList(String contractId);
	
}
