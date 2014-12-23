package com.appabc.common.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.utils.LogUtil;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年11月5日 下午5:45:11
 */

public class MultiTypeBeanPropertySqlParameterSource extends
		BeanPropertySqlParameterSource {
	
	
	protected LogUtil log = LogUtil.getLogUtil(this.getClass());
	/**  
	 * 创建一个新的实例 SupperEnumBeanPropertySqlParameterSource.  
	 *  
	 * @param object  
	 */
	public MultiTypeBeanPropertySqlParameterSource(Object object) {
		super(object);
	}

	/* (non-Javadoc)  
	 * @see org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource#getValue(java.lang.String)  
	 */
	@Override
	public Object getValue(String paramName) throws IllegalArgumentException {
		Object value = super.getValue(paramName);
		if(value instanceof Enum){
			try {
				Method method = value.getClass().getMethod("getVal");
				return method.invoke(value);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				log.error(e.getMessage());
				try {
					Method method = value.getClass().getMethod("getValue");
					return method.invoke(value);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e1) {
					log.error(e.getMessage());
				}
				return value;
			}
		}else if(value instanceof BaseBean){
			BaseBean bb = (BaseBean)value;
			return bb.getId();
		}else{
			return value;
		}
		
	}
	
}
