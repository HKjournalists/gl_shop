/**
 *
 */
package com.appabc.datas.tool;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.TokenEnum;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.utils.RedisHelper;
import com.appabc.common.utils.SerializeUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.common.utils.security.BaseCoder;
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

	private Logger logger  = Logger.getLogger(this.getClass());
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
		ut.setCtype(ci.getCtype() != null ? ci.getCtype().getVal() : null);
		ut.setAuthstatus(ci.getAuthstatus() != null ? ci.getAuthstatus().getVal() : null);

		ut.setExpTime(getExpTime());
		ut.setEffTimeLength(systemParamsManager.getInt(SystemConstant.USERTOKEN_EFF_TIME_LENGTH));

		delUserTokenByUser(user.getUsername()); // 删除旧数据

		try {
			Map<byte[],byte[]> map = this.redisHelper.hgetAll(USERTOKEN_MAP_KEY.getBytes("UTF-8"));
			if(map==null){
				map = new HashMap<byte[],byte[]>();
			}
			byte[] bytes = SerializeUtil.serialize(ut);
			map.put(user.getUsername().getBytes("UTF-8"), bytes);
			map.put(userToken.getBytes("UTF-8"), bytes);

			this.redisHelper.hmset(USERTOKEN_MAP_KEY.getBytes("UTF-8"), map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return ut;
	}
	
	/**
	 * 更新USERTOKEN信息
	 * @param ut
	 */
	public void updateUserToken(UserInfoBean ut) {
		if(ut == null){
			logger.error("UserToken is null");
			return ;
		}
		
		if(StringUtils.isNotEmpty(ut.getToken()) && StringUtils.isNotEmpty(ut.getToken())){
			try {
				Map<byte[],byte[]> map = this.redisHelper.hgetAll(USERTOKEN_MAP_KEY.getBytes("UTF-8"));
				if(map==null){
					map = new HashMap<byte[],byte[]>();
				}
				byte[] bytes = SerializeUtil.serialize(ut);
				map.put(ut.getUserName().getBytes("UTF-8"), bytes);
				map.put(ut.getToken().getBytes("UTF-8"), bytes);
				
				delUserTokenByUser(ut.getUserName()); // 删除旧数据
				
				this.redisHelper.hmset(USERTOKEN_MAP_KEY.getBytes("UTF-8"), map);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			logger.error("UserToken is not exist");
		}
		
	}

	/**
	 * 根据用户名获取TOKEN(过期将返回空)
	 * @param userName
	 * @return
	 */
	public UserInfoBean getBeanByUsername(String userName){
		try {
			if(userName != null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userName.getBytes("UTF-8"))){
				byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userName.getBytes("UTF-8"));
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
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 根据userName获取UserInfoBean(包含过期的TOKEN信息)
	 * @param userName
	 * @return
	 */
	public UserInfoBean getBeanByUsernameDoNotCheckTime(String userName){
		try {
			if(userName != null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userName.getBytes("UTF-8"))){
				byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userName.getBytes("UTF-8"));
				if(bytes != null){
					return (UserInfoBean) SerializeUtil.unserialize(bytes);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 根据TOKEN获取用户名
	 * @param userToken
	 * @return
	 */
	public UserInfoBean getBeanByToken(String userToken){
		try {
			if(userToken !=  null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"))){
				byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"));
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
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 根据TOKEN 获取 UserInfoBean(包含过期的TOKEN信息)
	 * @param userToken
	 * @return
	 */
	public UserInfoBean getBeanByTokenDoNotCheckTime(String userToken){
		try {
			if(userToken !=  null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"))){
				byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"));
				if(bytes != null){
					return (UserInfoBean) SerializeUtil.unserialize(bytes);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 根据用户名删除USERTOKEN，删除两对键值
	 * @param userName
	 */
	public void delUserTokenByUser(String userName){
		UserInfoBean ut = getBeanByUsernameDoNotCheckTime(userName);
		if(ut != null && ut.getToken() != null){
			try {
				this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userName.getBytes("UTF-8"));
				this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes("UTF-8"), ut.getToken().getBytes("UTF-8"));
				logger.info("DELETE USERTOKEN ; userName="+userName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据TOKEN名删除USERTOKEN，删除两对键值
	 * @param userName
	 */
	public void delUserTokenByToken(String token){
		UserInfoBean ut = getBeanByTokenDoNotCheckTime(token);
		if(ut != null && ut.getUserName() != null){
			try {
				this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes("UTF-8"), token.getBytes("UTF-8"));
				this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes("UTF-8"), ut.getUserName().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前时间N秒后的时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Date getExpTime(){
		Date date = new Date();
		date.setSeconds(date.getSeconds() + systemParamsManager.getInt(SystemConstant.USERTOKEN_EFF_TIME_LENGTH));
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
	public TokenEnum isExists(String userToken) {
		logger.info("-------------userToken isExists-------------");
		try {
			if(StringUtils.isNotEmpty(userToken) && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"))) {
				byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"));
				if(bytes != null){
					UserInfoBean u = (UserInfoBean) SerializeUtil.unserialize(bytes);
					if(u != null){
						logger.debug("UserInfoBean="+u);
						if(u.getExpTime() == null || u.getExpTime().before(Calendar.getInstance().getTime())) { // 过期时间小于当前时间时，USERTOKEN过期
							logger.info("u.getExpTime()="+u.getExpTime());
							logger.info(TokenEnum.TOKEN_STATUS_EXPIRED);
							logger.info(u);
							return TokenEnum.TOKEN_STATUS_EXPIRED;
						}else{
							return TokenEnum.TOKEN_STATUS_EXIST;
						}
						
					}else{
						logger.info("UserInfoBean 反序列化为空；bytes="+bytes);
					}
				}else{
					logger.info("USERTOKEN 不存在 ；userToken="+userToken+"; bytes is null");
				}
			}else{
				logger.info("USERTOKEN 不存在 ；userToken="+userToken);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return TokenEnum.TOKEN_STATUS_NOTEXIST;
	}

}
