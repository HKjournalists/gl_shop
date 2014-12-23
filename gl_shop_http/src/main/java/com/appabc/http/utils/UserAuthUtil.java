/**
 *
 */
package com.appabc.http.utils;

import java.util.HashMap;
import java.util.Map;

import com.appabc.bean.enums.UserInfo.UserStatus;

/**
 * @Description : 用户信息处理工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月4日 上午10:20:14
 */
public class UserAuthUtil {
	
	/**
	 * 用户状态检查
	 * @param status
	 * @return
	 */
	public static Map<String,Object> checkStatus(String status){
		
		UserStatus.USER_STATUS_NORMAL.getVal();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(UserStatus.USER_STATUS_NORMAL.getVal().equals(status)){
			resultMap.put("code", 0); // 状态正常
		}else if(UserStatus.USER_STATUS_LIMITED.getVal().equals(status)){
			resultMap.put("code", 1);
			resultMap.put("errStr", "用户受限制");
		}else if(UserStatus.USER_STATUS_DISABLE.getVal().equals(status)){
			resultMap.put("code", 1);
			resultMap.put("errStr", "用户已被禁用");
		}else{
			resultMap.put("code", 1);
			resultMap.put("errStr", "用户状态异常");
		}
		
		return resultMap;
	}
	
}
