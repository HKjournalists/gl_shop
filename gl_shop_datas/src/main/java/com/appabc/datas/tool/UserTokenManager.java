/**
 *
 */
package com.appabc.datas.tool;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.enums.TokenEnum;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.TokenBean;
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
		
		UserInfoBean ut = getBeanByUsernameDoNotCheckTime(user.getUsername());
		if(ut == null || CollectionUtils.isEmpty(ut.getTokenList())) ut = new UserInfoBean();
		
		TokenBean tokenBean = createUserToken(user.getUsername());
		if (tokenBean != null){
			ut.getTokenList().addFirst(tokenBean);
			
			ut.setId(user.getId());
			ut.setCid(user.getCid());
			ut.setUserName(user.getUsername());
			ut.setPhone(user.getPhone());
			ut.setNick(user.getNick());
			ut.setLogo(ut.getLogo());
			
			ut.setCname(ci.getCname());
			ut.setCtype(ci.getCtype() != null ? ci.getCtype().getVal() : null);
			ut.setAuthstatus(ci.getAuthstatus() != null ? ci.getAuthstatus().getVal() : null);
			
			try {
				Map<byte[],byte[]> map = this.redisHelper.hgetAll(USERTOKEN_MAP_KEY.getBytes("UTF-8"));
				if(map==null){
					map = new HashMap<byte[],byte[]>();
				}
				byte[] bytes = SerializeUtil.serialize(ut);
				map.put(user.getUsername().getBytes("UTF-8"), bytes);
				map.put(tokenBean.getToken().getBytes("UTF-8"), bytes);
				
				this.redisHelper.hmset(USERTOKEN_MAP_KEY.getBytes("UTF-8"), map);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return ut;
		}else{
			return null;
		}
	}
	
	/**
	 * 更新USERTOKEN信息
	 * @param ut
	 */
	public void updateUserInfo(UserInfoBean ut) {
		if(ut == null){
			logger.error("UserToken is null");
			return ;
		}
		
		TokenBean tokenBean = ut.getTokenList().getFirst();
		
		if(tokenBean != null && StringUtils.isNotEmpty(tokenBean.getToken())){
			try {
				Map<byte[],byte[]> map = this.redisHelper.hgetAll(USERTOKEN_MAP_KEY.getBytes("UTF-8"));
				if(map==null){
					map = new HashMap<byte[],byte[]>();
				}
				byte[] bytes = SerializeUtil.serialize(ut);
				map.put(ut.getUserName().getBytes("UTF-8"), bytes);
				map.put(tokenBean.getToken().getBytes("UTF-8"), bytes);
				
				this.redisHelper.hmset(USERTOKEN_MAP_KEY.getBytes("UTF-8"), map);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			logger.error("UserToken is not exist");
		}
		
	}

	/**
	 * 根据用户名获取UserInfoBean(过期将返回空)
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
						if(u.getTokenList().getFirst().getExpTime().before(Calendar.getInstance().getTime())){ // 过期时间小于当前时间时，USERTOKEN过期
							return null;
						}
						return u;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return null;
	}

	/**
	 * 根据TOKEN值获取UserInfoBean
	 * @param userToken
	 * @return
	 */
	public UserInfoBean getBeanByToken(String token){
		try {
			if(token !=  null && this.redisHelper.hexists(USERTOKEN_MAP_KEY.getBytes("UTF-8"), token.getBytes("UTF-8"))){
				byte[] bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), token.getBytes("UTF-8"));
				if(bytes != null){
					UserInfoBean u = (UserInfoBean) SerializeUtil.unserialize(bytes);
					if(u != null){
						if(u.getTokenList().getFirst().getExpTime().before(Calendar.getInstance().getTime())){ // 过期时间小于当前时间时，删除USERTOKEN
							return null;
						}
						return u;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return null;
	}

	/**
	 * 根据用户名删除userInfo（删除userName对应的Token 以及 tokenList中所有TOKEN）
	 * @param userName
	 */
	public void delUserInfoByUser(String userName){
		UserInfoBean ut = getBeanByUsernameDoNotCheckTime(userName);
		if(ut != null){
			LinkedList<TokenBean> tokenList = ut.getTokenList();
			try {
				delTokenList(tokenList);
				this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userName.getBytes("UTF-8"));
				logger.info("DELETE ALL USERTOKEN ; userName="+userName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除一组TOKEN
	 * @param tokenList
	 */
	private void delTokenList(LinkedList<TokenBean> tokenList){
		if(CollectionUtils.isNotEmpty(tokenList)){
			for (int i = tokenList.size()-1; i > -1 ; i--) {
				try {
					this.redisHelper.hdel(USERTOKEN_MAP_KEY.getBytes("UTF-8"), tokenList.get(i).getToken().getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				logger.info("DELETE TOKEN LIST ; token="+tokenList.get(i).getToken());
			}
		}
		
	}
	
	/**
	 * 根据TOKEN值删除userInfo（删除userName对应的Token 以及 tokenList中所有TOKEN）
	 * @param userName
	 */
	public void delUserInfoByToken(String token) {
		UserInfoBean ut = getBeanByTokenDoNotCheckTime(token);
		if(ut != null && ut.getUserName() != null){
			delUserInfoByUser(ut.getUserName());
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
	private TokenBean createUserToken(String userName){
		TokenBean tokenBean = null;
		try {
			tokenBean = new TokenBean();
			tokenBean.setUserName(userName);
			tokenBean.setToken(BaseCoder.encryptMD5(userName + Calendar.getInstance()));
			tokenBean.setExpTime(getExpTime());
			tokenBean.setEffTimeLength(systemParamsManager.getInt(SystemConstant.USERTOKEN_EFF_TIME_LENGTH));
		} catch (Exception e) {
			logger.error("createUserToken is error."+ e.getMessage());
			e.printStackTrace();
		}
		
		return tokenBean;
	}
	
	/**
	 * 根据token获取username
	 * @param token
	 * @return
	 */
	public String getUsernameByToken(String token){
		UserInfoBean u = getBeanByToken(token);
		if(u != null){
			return u.getUserName();
		}else{
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
						Date expTime = u.getTokenList().getFirst().getExpTime();
						if(expTime == null || expTime.before(Calendar.getInstance().getTime())) { // 过期时间小于当前时间时，USERTOKEN过期
							logger.info("u.getExpTime()="+expTime);
							logger.info(TokenEnum.TOKEN_STATUS_EXPIRED);
							logger.info(u);
							return TokenEnum.TOKEN_STATUS_EXPIRED;
						}else{
							
							// 如果该token为最新的token删除其它token
							try {
								UserInfoBean userNameGetInfo = getBeanByUsernameDoNotCheckTime(u.getUserName());
								if(userNameGetInfo != null && userToken.equals(userNameGetInfo.getTokenList().getFirst().getToken())){
									LinkedList<TokenBean> tokenList = userNameGetInfo.getTokenList();
									if(tokenList != null && tokenList.size()>1){
										TokenBean firstToke = tokenList.getFirst();
										
										tokenList.removeFirst(); // 保留第一个，第一个为最新token
										delTokenList(tokenList);
										logger.info("The latest token is :"+tokenList.getFirst().getToken()+"\t ,username is :"+tokenList.getFirst().getUserName());
										
										// 更新 userName 对应的token list size is 1, and the first token is new token
										LinkedList<TokenBean> newTokenList = new LinkedList<TokenBean>();
										newTokenList.add(firstToke);
										userNameGetInfo.setTokenList(newTokenList);
										updateUserInfo(userNameGetInfo);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							return TokenEnum.TOKEN_STATUS_EXIST;
						}
						
					}else{
						logger.info("UserInfoBean 反序列化为空；");
					}
				}else{
					logger.info("USERTOKEN 不存在 ；userToken="+userToken+"; bytes is null");
				}
			}else{
				logger.info("USERTOKEN 不存在 ；userToken="+userToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return TokenEnum.TOKEN_STATUS_NOTEXIST;
	}

}
