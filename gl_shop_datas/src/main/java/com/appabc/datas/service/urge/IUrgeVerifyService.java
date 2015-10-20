package com.appabc.datas.service.urge;

import java.util.List;

import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 催促认证service接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄木俊
 * @version     : 1.0
 * @Create_Date  : 2015年8月27日 下午3:00:44
 */

public interface IUrgeVerifyService extends IBaseService<TUrgeVerify>{

	UrgeVerifyInfo queryVerifyInfoByTaskId(String  taskid);
	QueryContext<UrgeVerifyInfo> getVerifyList(QueryContext<UrgeVerifyInfo> qContext);
	QueryContext<UrgeVerifyInfo> getVerifyNoList(QueryContext<UrgeVerifyInfo> qContext);
	List<TUrgeVerify> queryRecordByCompanyId(String  companyId);
}
