/**
 *
 */
package com.appabc.tools.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.utils.RedisHelper;
import com.appabc.common.utils.SerializeUtil;
import com.appabc.tools.service.codes.IPublicCodesService;

/**
 * @Description : 区域信息管理
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年12月30日 上午9:56:23
 */
@Repository(value="AreaManager")
public class AreaManager {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private final static String AREA_MAP_KEY = "TOKEN_MAP";
	
	@Autowired
	private IPublicCodesService publicCodesService;
	@Autowired
	private RedisHelper redisHelper;
	
	/**
	 * 区域信息加载
	 */
	public void initArea(){
		
		try {
			Map<byte[],byte[]> map = this.redisHelper.hgetAll(AREA_MAP_KEY.getBytes("UTF-8"));
			if(map==null){
				map = new HashMap<byte[],byte[]>();
			}
			
			
			TPublicCodes entity = new TPublicCodes();
			entity.setCode("AREA");
			List<TPublicCodes> pcList = publicCodesService.queryForList(entity);
			logger.info("area list size:" + pcList.size());
			for(TPublicCodes pc : pcList){
				byte[] bytes = SerializeUtil.serialize(pc);
				map.put(pc.getVal().getBytes("UTF-8"), bytes);
			}
			this.redisHelper.hmset(AREA_MAP_KEY.getBytes("UTF-8"), map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 获取区域实体BEAN
	 * @param areaVal
	 * @return
	 */
	public TPublicCodes getAreaBean(String areaVal){
		try {
			if(StringUtils.isNotEmpty(areaVal) && this.redisHelper.hexists(AREA_MAP_KEY.getBytes("UTF-8"), areaVal.getBytes("UTF-8"))){
				byte[] bytes = this.redisHelper.hget(AREA_MAP_KEY.getBytes("UTF-8"), areaVal.getBytes("UTF-8"));
				if(bytes != null){
					return (TPublicCodes) SerializeUtil.unserialize(bytes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 *  获取区域名称
	 * @param areaVal
	 * @return
	 */
	public String getAreaName(String areaVal){
		TPublicCodes pc = getAreaBean(areaVal);
		if(pc != null){
			return pc.getName();
		}
		
		return null;
	}
	
	/**
	 * 获得区域从顶级到当前级别的名称
	 * @param areaVal
	 * @return
	 */
	public String getFullAreaName(String areaVal){
		StringBuilder areaName = new StringBuilder();
		spelFullAreaName(areaVal, areaName);
		return areaName.toString();
	}
	
	/**
	 * 区域拼写
	 * @param areaVal
	 * @param areaName
	 */
	private void spelFullAreaName(String areaVal, StringBuilder areaName){
		if(StringUtils.isNotEmpty(areaVal)){
			if(areaName == null) areaName = new StringBuilder();
			TPublicCodes pc = getAreaBean(areaVal);
			if(pc != null){
				areaName.insert(0, pc.getName());
				if(StringUtils.isNotEmpty(pc.getPcode()) && !"0".equalsIgnoreCase(pc.getPcode())){
					spelFullAreaName(pc.getPcode(), areaName);
				}
			}
		}
	}

}
