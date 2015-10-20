/**
 *
 */
package com.appabc.tools.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TSystemParams;
import com.appabc.common.utils.RedisHelper;
import com.appabc.common.utils.SystemConstant;
import com.appabc.tools.service.system.ISystemParamsService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月10日 上午11:04:28
 */
@Repository(value="SystemParamsManager")
public class SystemParamsManager {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private final String PREFIX = SystemConstant.CACHE_PARAM_PROFIX;
	
	@Autowired
	private ISystemParamsService systemParamsService;
	@Autowired
	private RedisHelper redisHelper;
	
	/**
	 * 加载系统参数表中的所有值到缓存中
	 */
	public void initSystemParam(){
		List<TSystemParams> spList = this.systemParamsService.queryForList(new TSystemParams());
		for(TSystemParams sp : spList){
			logger.info(sp.getPname()+ "=" + sp.getPvalue());
			redisHelper.set(PREFIX + sp.getPname(), sp.getPvalue());
		}
		//初始化平台企业信息和平台企业信息的钱包信息.
		this.systemParamsService.initDataParameters();
	}
	
	public String getString(String paramName){
		return redisHelper.getString(PREFIX+paramName);
	}
	
	public int getInt(String paramName){
		return redisHelper.getInt(PREFIX+paramName);
	}
	
	public double getDouble(String paramName){
		return redisHelper.getDouble(PREFIX+paramName);
	}
	
	public float getFloat(String paramName){
		return redisHelper.getFloat(PREFIX+paramName);
	}
	
	public long getLong(String paramName){
		return redisHelper.getLong(PREFIX+paramName);
	}
	
	public short getShort(String paramName){
		return redisHelper.getShort(PREFIX+paramName);
	}

	public RedisHelper getRedisHelper() {
		return redisHelper;
	}

}
