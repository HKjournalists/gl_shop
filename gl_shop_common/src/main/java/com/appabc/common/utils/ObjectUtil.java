package com.appabc.common.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年1月27日 下午2:45:20
 */

public class ObjectUtil extends ObjectUtils {
	
	/** 判断对象不为null或为空  */
	public static boolean isNotEmpty(Object obj) {
		return !isNEmpty(obj);
	}

	/** 判断对象为null或为空  */
	public static boolean isNEmpty(Object obj) {
		return obj == null ? true : isEmpty(obj);
	}

	/** 判断字符串为空，集合为空，数组为空(后续可以拓展hashSet,hashMap ...) */
	public static boolean isEmpty(Object obj) {
		boolean isEmpty = true;
		if (obj != null) {
			if (obj instanceof String) {
				// 字符串
				String tmp = obj.toString();
				isEmpty = StringUtils.isEmpty(tmp);

			} else if (obj instanceof Collection<?>) {
				// 集合
				Collection<?> collections = (Collection<?>) obj;
				isEmpty = CollectionUtils.isEmpty(collections);

			} else if (obj instanceof Map<?, ?>) {
				// Map
				Map<?, ?> map = (Map<?, ?>) obj;
				isEmpty = map.size() == 0;

			} else if (obj instanceof Object[]) {
				// 数组
				Object[] objarray = (Object[]) obj;
				isEmpty = objarray.length == 0;
				
			} else if (obj instanceof Float){
				Float f = (Float)obj;
				isEmpty = f == 0f;
			}
		}
		return isEmpty;
	}
	
}
