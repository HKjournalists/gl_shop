package com.appabc.datas.dao.urge;

import java.util.List;

import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 催促保证金Dao接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年9月14日 上午10:09:33
 */

public interface IUrgeDepositDao extends IBaseDao<TUrgeVerify>{
	QueryContext<UrgeVerifyInfo> getDepositList(QueryContext<UrgeVerifyInfo> qContext);
	QueryContext<UrgeVerifyInfo> getNoDepositList(QueryContext<UrgeVerifyInfo> qContext);
	UrgeVerifyInfo queryDepositInfoByTaskId(String  taskid);
	List<TUrgeVerify> queryRecordByTypeAndId(String utype,String id);
}
