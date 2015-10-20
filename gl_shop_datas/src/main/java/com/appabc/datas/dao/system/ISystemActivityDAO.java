package com.appabc.datas.dao.system;

import java.util.List;

import com.appabc.bean.pvo.TActivityJoin;
import com.appabc.common.base.dao.IBaseDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月22日 下午5:49:43
 */

public interface ISystemActivityDAO extends IBaseDao<TActivityJoin> {
	
	List<TActivityJoin> querySystemActivityByPhoneNum(String phone);
	
}
