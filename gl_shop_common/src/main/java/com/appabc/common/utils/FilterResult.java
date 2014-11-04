package com.appabc.common.utils;

import java.util.Collection;

/**
 * @Description : 支持过滤对象不需要的属性返回结果集
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年9月1日 下午2:30:26
 */

public class FilterResult {

	private Collection<?> collection;
	
	private String[] filterPropertyNames;

	public FilterResult(){}
	
	public FilterResult(Collection<?> collection,String...filterPropertyNames){
		this.collection = collection;
		this.filterPropertyNames = filterPropertyNames;
	}
	
	/**  
	 * collection  
	 *  
	 * @return  the collection  
	 * @since   1.0.0  
	 */
	
	public Collection<?> getCollection() {
		return collection;
	}

	/**  
	 * @param collection the collection to set  
	 */
	public void setCollection(Collection<?> collection) {
		this.collection = collection;
	}

	/**  
	 * filterPropertyNames  
	 *  
	 * @return  the filterPropertyNames  
	 * @since   1.0.0  
	 */
	
	public String[] getFilterPropertyNames() {
		return filterPropertyNames;
	}

	/**  
	 * @param filterPropertyNames the filterPropertyNames to set  
	 */
	public void setFilterPropertyNames(String[] filterPropertyNames) {
		this.filterPropertyNames = filterPropertyNames;
	}
	
}
