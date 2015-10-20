package com.appabc.datas.dao.banner;


import java.util.List;
import java.util.Map;

import com.appabc.bean.bo.ClientBannerInfo;
import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.bean.pvo.TClientBanner;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年8月4日 上午10:47:59
 */

public interface IClientBannerDao  extends IBaseDao<TClientBanner>{
	 int getMaxOrderno(ClientType osType);
	 List<ClientBannerInfo> queryClientBannerInfoList(Map<String, Object> args);
	 List<ClientBannerInfo> queryBannerLittleInfoList(Integer btype);	
}
