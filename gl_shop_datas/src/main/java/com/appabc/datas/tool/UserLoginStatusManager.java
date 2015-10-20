/**
 *
 */
package com.appabc.datas.tool;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.common.utils.RedisHelper;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2015年9月10日 下午3:47:26
 */
@Repository(value="userLoginStatusManager")
public class UserLoginStatusManager {
	
	private Logger logger  = Logger.getLogger(this.getClass());
	private final static String USER_lOGIN_STATUS_MAP_KEY = "U_L_S_MAP";
	
	@Autowired
	private RedisHelper redisHelper;
	
	/**
	 * 记录用户登录状态，如果已存在则不记录
	 * @param username
	 */
	public void recordingUserStatus (String username) {
		
		if(StringUtils.isEmpty(username)){
			logger.warn("username is null");
			return ;
		}
		
		Map<byte[],byte[]> allUserMap = getAllUsersLogin();
		if(allUserMap == null) allUserMap = new HashMap<byte[],byte[]>();
		
		try {
			if(allUserMap.get(username.getBytes("UTF-8")) == null){
				allUserMap.put(username.getBytes("UTF-8"), Calendar.getInstance().toString().getBytes("UTF-8"));
			}
			
			redisHelper.hmset(USER_lOGIN_STATUS_MAP_KEY.getBytes("UTF-8"), allUserMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取所有
	 * @return
	 */
	public Map<byte[],byte[]> getAllUsersLogin() {
		
		try {
			return redisHelper.hgetAll(USER_lOGIN_STATUS_MAP_KEY.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 删除所有
	 */
	public void delAll(){
		try {
			redisHelper.del(USER_lOGIN_STATUS_MAP_KEY.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}
