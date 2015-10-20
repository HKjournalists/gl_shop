package com.appabc.common.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.LogUtil;

/**
 * @Description : String To Enum in SpringMVC
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月22日 下午4:05:10
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
class EnumConverterFactory implements ConverterFactory<String, Enum> {

	protected LogUtil log = LogUtil.getLogUtil(this.getClass());
	
	@Override
	public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
		Class<?> enumType = targetType;
		while (enumType != null && !enumType.isEnum()) {
			enumType = enumType.getSuperclass();
		}
		if (enumType == null) {
			throw new IllegalArgumentException("The target type " + targetType.getName() + " does not refer to an enum");
		}
		return new StringToEnum(enumType);
	}


	private class StringToEnum<T extends Enum> implements Converter<String, T> {

		private final Class<T> enumType;
		
		private final Class<?>[] clzs = new Class<?>[] { String.class,
				boolean.class, Boolean.class, byte.class, Byte.class,
				char.class, Character.class, short.class, Short.class,
				int.class, Integer.class, long.class, Long.class, float.class,
				Float.class, double.class, Double.class, Object.class,
				Date.class };

		private Method getEnumOfMethodInEnum(String methodName,Class<?> cls){
			if(StringUtils.isEmpty(methodName) || cls == null){
				return null;
			}
			try {
				Method method = this.enumType.getMethod(methodName,cls);
				return method;
			} catch (NoSuchMethodException | SecurityException e) {
				log.error(e);
				return null;
			}
		}
		
		private Object convertObjectValue(String str,Class<?> type){
			if(StringUtils.isEmpty(str)){
				return null;
			}
			if(type == null){
				return str;
			}
			if(type == int.class || type == Integer.class){
				return Integer.valueOf(str);
			}else if(type == boolean.class || type == Boolean.class){
				return Boolean.valueOf(str);
			}else if(type == byte.class || type == Byte.class){
				return Byte.valueOf(str);
			}else if(type == char.class || type == Character.class){
				return str.toCharArray();
			}else if(type == short.class || type == Short.class){
				return Short.valueOf(str);
			}else if(type == long.class || type == Long.class){
				return Long.valueOf(str);
			}else if(type == float.class || type == Float.class){
				return Float.valueOf(str);
			}else if(type == double.class || type == Double.class){
				return Double.valueOf(str);
			}else if(type == Date.class){
				return DateUtil.strToDate(DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS, str);
			}else if(type == String.class){
				return str;
			}else{				
				return type.cast(str);
			}
		}
		
		public StringToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}
		
		@Override
		public T convert(String source) {
			if (source.length() == 0) {
				// It's an empty enum identifier: reset the enum value to null.
				return null;
			}
			//to support our project get the enum values 
			//can uses the enum the enumOf method to get 
			//the value object.
			Class<?> paramType = null;
			Method method = null;
			for(int i = 0; i < clzs.length; i ++){
				method = this.getEnumOfMethodInEnum("enumOf", clzs[i]);
				if(method != null){
					paramType = clzs[i];
					break;
				}
			}
			if(method != null){
				T[] ts = this.enumType.getEnumConstants();
				Object value = null;
				for(T t : ts){
					try {
						value = method.invoke(t, convertObjectValue(source.trim(), paramType));
						if(value != null){
							return (T)value;
						}
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						log.error(e);
					}
				}
				return null;
			}
			return (T) Enum.valueOf(this.enumType, source.trim());
		}
	}

}