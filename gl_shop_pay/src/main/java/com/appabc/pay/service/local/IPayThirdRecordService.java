/**  
 * com.appabc.pay.service.local.IPayThirdRecordService.java  
 *   
 * 2015年3月3日 上午10:29:19  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.service.local;

import com.appabc.bean.enums.PurseInfo.RequestType;
import com.appabc.common.base.service.IBaseService;
import com.appabc.pay.bean.TPayThirdOrgRecord;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月3日 上午10:29:19
 */

public interface IPayThirdRecordService extends IBaseService<TPayThirdOrgRecord> {
	
	TPayThirdOrgRecord savePayThirdOrgRecord(String oid, String paramsContent, RequestType rt);
	
}
