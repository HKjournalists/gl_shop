package com.appabc.datas.dao.system;

import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月19日 下午5:17:27
 */

public interface IUploadImagesDAO extends IBaseDao<TUploadImages> {
	
	/**
	 * 统计引用同一个文件的记录
	 * @param fPath
	 * @return
	 */
	public int getCountByFpath(String fPath);
	
}
