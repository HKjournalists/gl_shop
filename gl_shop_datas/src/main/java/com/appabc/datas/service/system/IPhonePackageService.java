package com.appabc.datas.service.system;

import java.util.List;

import com.appabc.bean.pvo.TPhonePackage;
import com.appabc.common.base.service.IBaseService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月20日 上午10:58:30
 */

public interface IPhonePackageService extends IBaseService<TPhonePackage> {
	
	List<TPhonePackage> queryPhonePackageListByPid(String pid);
	
}
