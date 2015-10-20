package com.appabc.datas.service.system;

import com.appabc.bean.pvo.TActivityJoin;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月22日 下午5:21:22
 */

public interface ISystemActivityService extends IBaseService<TActivityJoin> {

	boolean checkPhoneNumIsJoined(String phone);
	
}
