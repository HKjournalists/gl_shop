package com.appabc.common.spring;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;



/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午8:19:25
 */

public class CustomObjectMapper extends ObjectMapper {
	
	/**  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public CustomObjectMapper(){
		//according to the Serializer factory to register the date format pattern
		this.setDateFormat(new SimpleDateFormat(pattern));
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

	/**  
	 * @param pattern the pattern to set  
	 */
	public void setPattern(String patternE) {
		pattern = patternE;
	}
	
	//here method set the filter the property form bean 
	//so the SimpleBeanPropertyFilter will be create and just create one instance for the simple filter
	public static CustomObjectMapper getFilterObjectMapperInstance(Class<?> clz,String[] filterPropertyNames){
		FilterProvider filters = new SimpleFilterProvider().addFilter(clz.getName(), SimpleBeanPropertyFilter.serializeAllExcept(filterPropertyNames));
		CustomObjectMapper om = new CustomObjectMapper();
		om.setFilters(filters);
		om.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
			private static final long serialVersionUID = 944241454906882230L;
			@Override
			public Object findFilterId(Annotated ac) {
				Object id = super.findFilterId(ac);
				// but use simple class name if not
			    if (id == null) {
			       id = ac.getName();
			    }
			    return id;
			}
		});
		return om;
	}
	
}
