package com.appabc.datas.dao.contract;

import com.appabc.bean.pvo.TOrderCancel;
import com.appabc.common.base.dao.IBaseDao;

import java.util.List;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午2:39:46
 */

public interface IContractCancelDAO extends IBaseDao<TOrderCancel> {
	
	/**
	 * @description 通过合同编号获取取消列表信息
	 * @param oid
	 * @return List<TOrderCancel>
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	List<TOrderCancel> getCancelContractListByOID(String oid);
	
}
