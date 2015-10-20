package com.appabc.datas.dao.urge;

import java.util.List;
import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 催促认证Dao接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月27日 下午2:48:02
 */

public interface IUrgeVerifyDao extends IBaseDao<TUrgeVerify> {
	UrgeVerifyInfo queryVerifyInfoByTaskId(String taskid);
	QueryContext<UrgeVerifyInfo> getVerifyList(QueryContext<UrgeVerifyInfo> qContext);
	QueryContext<UrgeVerifyInfo> getVerifyNoList(QueryContext<UrgeVerifyInfo> qContext);
	List<TUrgeVerify> queryRecordByCompanyId(String  companyId);
}
