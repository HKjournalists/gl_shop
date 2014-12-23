package com.appabc.datas.service.system;

import com.appabc.bean.pvo.TMobileAppVersion;
import com.appabc.common.base.service.IBaseService;
import com.appabc.datas.exception.ServiceException;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月22日 上午10:53:18
 */

public interface IMobileAppVersionService extends IBaseService<TMobileAppVersion> {

	TMobileAppVersion getMobileAppUpdateInfo(String devices,String lastNo) throws ServiceException;
	
}
