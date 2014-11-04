/**
 *
 */
package com.appabc.datas.tool;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.utils.RedisHelper;
import com.appabc.common.utils.SerializeUtil;
import com.appabc.common.utils.security.BaseCoder;
import com.appabc.datas.enums.TokenEnum;
import com.appabc.tools.utils.SystemParamsManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月9日 下午4:21:27
 */
@Repository(value="userTokenManager")
public class UserTokenManager {
	
	private final static String USERTOKEN_MAP_KEY = "TOKEN_MAP";
	
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private SystemParamsManager systemParamsManager;
	
	/**
	 * token存储
	 * @param user 用户帐号信息
	 * @param ci 用户企业信息
	 * @return
	 */
	public UserInfoBean saveUserToken(TUser user, TCompanyInfo ci){
		UserInfoBean ut = new UserInfoBean();
		String userToken = createUserToken(user.getUsername());
		
		ut.setId(user.getId());
		ut.setCid(user.getCid());
		ut.setToken(userToken);
		ut.setUserName(user.getUsername());
		ut.setPhone(user.getPhone());
		ut.setNick(user.getNick());
		ut.setLogo(ut.getLogo());
		
		ut.setCname(ci.getCname());
		ut.setCtype(ci.getCtype());
		ut.setAuthstatus(ci.getAuthstatus());
		
		ut.setExpTime(getExpTime());
		ut.setEffTimeLength(systemParamsManager.getInt("USERTOKEN_EFF_TIME_LENGTH"));
		
		delUserTokenByUser(user.getUsername()); // 删除旧数据
		
		Map<byte[],byte[]> map = this.redisHelper.hgetAll(USERTOKEN_MAP_KEY.getBytes());
		if(map==null){
			map = new HashMap<byte[],byte[]>();
		}
		byte[] bytes = SerializeUtil.serialize(ut);
		map.put(user.getUsername().getBytes(), bytes);
		map.put(userToken.getBytes(), bytes);
		
		this.redisHelper.hmset(USERTOKEN_MAP_KEY.getBytes(), map);
		
		return ut;
	}
	
	/**
	 * 根据用户名获取TOKEN
	 * @param userName
	 * @return
	 */
	public UserInfoBean getBeanByUsername(String userName){
		if(userName != null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes(), userName.getBytes())){
			byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes(), userName.getBytes());
			if(bytes != null){
				UserInfoBean u = (UserInfoBean) SerializeUtil.unserialize(bytes);
				if(u != null){
					if(u.getExpTime().before(Calendar.getInstance().getTime())){ // 过期时间小于当前时间时，USERTOKEN过期
						return null;
					}
					return u;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 根据TOKEN获取用户名
	 * @param userToken
	 * @return
	 */
	public UserInfoBean getBeanByToken(String userToken){
		if(userToken !=  null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes(), userToken.getBytes())){
			byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes(), userToken.getBytes());
			if(bytes != null){
				UserInfoBean u = (UserInfoBean) SerializeUtil.unserialize(bytes);
				if(u != null){
					if(u.getExpTime().before(Calendar.getInstance().getTime())){ // 过期时间小于当前时间时，删除USERTOKEN
						return null;
					}
					return u;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 根据用户名删除USERTOKEN，删除两对键值
	 * @param userName
	 */
	public void delUserTokenByUser(String userName){
		UserInfoBean ut = getBeanByUsername(userName);
		if(ut != null && ut.getToken() != null){
			this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes(), userName.getBytes());
			this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes(), ut.getToken().getBytes());
		}
	}
	
	/**
	 * 根据TOKEN名删除USERTOKEN，删除两对键值
	 * @param userName
	 */
	public void delUserTokenByToken(String token){
		UserInfoBean ut = getBeanByToken(token);
		if(ut != null && ut.getUserName() != null){
			this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes(), token.getBytes());
			this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes(), ut.getUserName().getBytes());
		}
	}
	
	/**
	 * 获取当前时间N秒后的时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Date getExpTime(){
		Date date = new Date();
		date.setSeconds(date.getSeconds() + systemParamsManager.getInt("USERTOKEN_EFF_TIME_LENGTH"));
		return date;
	}
	
	/**
	 * 生成userToken
	 * @param username
	 * @param date
	 * @return
	 */
	private String createUserToken(String userName){
		try {
			return BaseCoder.encryptMD5(userName + Calendar.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 判断userToken是否存在
	 * @param userToken
	 * @return TokenEnum
	 */
	public int isExists(String userToken) {
		if(userToken != null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes(), userToken.getBytes())) {
			byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes(), userToken.getBytes());
			if(bytes != null){
				UserInfoBean u = (UserInfoBean) SerializeUtil.unserialize(bytes);
				if(u != null){
					if(u.getExpTime().before(Calendar.getInstance().getTime())) { // 过期时间小于当前时间时，USERTOKEN过期
						return TokenEnum.TOKEN_STATUS_EXPIRED.getVal();
					}else{
						return TokenEnum.TOKEN_STATUS_EXIST.getVal();
					}
				}
			}
		}
		return TokenEnum.TOKEN_STATUS_NOTEXIST.getVal();
	}
	
}
