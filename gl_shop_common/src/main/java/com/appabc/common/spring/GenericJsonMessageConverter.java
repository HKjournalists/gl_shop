package com.appabc.common.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.appabc.common.base.bean.EmptyResult;
import com.appabc.common.utils.CheckResult;
import com.appabc.common.utils.FilterResult;
import com.appabc.common.utils.LogUtil;
import com.appabc.common.utils.SystemConstant;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * @Description : 公用的json数据转换器
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月25日 下午8:48:22
 */

public class GenericJsonMessageConverter extends
		MappingJackson2HttpMessageConverter{

	private static final LogUtil log = LogUtil.getLogUtil(GenericJsonMessageConverter.class);
	
	/* (non-Javadoc)  
	 * @see org.springframework.http.converter.AbstractHttpMessageConverter#readInternal(java.lang.Class, org.springframework.http.HttpInputMessage)  
	 */
	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return super.readInternal(clazz, inputMessage);
	}
	
	/* (non-Javadoc)  
	 * @see org.springframework.http.converter.json.MappingJacksonHttpMessageConverter#writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage)  
	 */
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		Map<String,Object> message = new HashMap<String,Object>();
		if(object == null){
			message.put(SystemConstant.RESULT, true);
			message.put(SystemConstant.MESSAGE, StringUtils.EMPTY);
		}else{
			//here is out put the message according jackson json Util 
			boolean isCheckResult = object instanceof CheckResult;
			boolean isModelMapCheckResult = object instanceof ModelMap && ((ModelMap)object).get(SystemConstant.ERRORCODE)!=null ;
			isModelMapCheckResult = isModelMapCheckResult && ((ModelMap)object).get(SystemConstant.ERRORCODE) instanceof CheckResult;
			isCheckResult = isCheckResult || isModelMapCheckResult;
			if(isCheckResult){
				//checkResult format is error or succes message and object
				CheckResult result = null;
				if(isModelMapCheckResult){
					result = (CheckResult)((ModelMap)object).get(SystemConstant.ERRORCODE);
				}else{
					result = (CheckResult)object;
				}
				if(result.isError()){
					message.put(SystemConstant.ERRORCODE, result.getErrorCode());
					//message.put(SystemConstant.MESSAGE, result.getMessage());
				}
				message.put(SystemConstant.RESULT, !result.isError());
				message.put(SystemConstant.MESSAGE, result.getMessage());
				if(!ObjectUtils.isEmpty(result.getData())){
					message.put(SystemConstant.DATA, result.getData().length == 1 ? result.getData()[0] : result.getData());
				}
			} else if (object instanceof FilterResult){
				//the filter result can filter the collection to not output some properties
				FilterResult result = (FilterResult)object;
				if(result.getFilterPropertyNames()==null || result.getFilterPropertyNames().length<=0){
					message.put(SystemConstant.RESULT, true);
					message.put(SystemConstant.DATA, CollectionUtils.isEmpty(result.getCollection()) ? StringUtils.EMPTY : result.getCollection());
				}else{
					Iterator<?> it = result.getCollection().iterator();
					Class<?> clz = it.next().getClass();
					CustomObjectMapper com = CustomObjectMapper.getFilterObjectMapperInstance(clz,result.getFilterPropertyNames());
					message.put(SystemConstant.RESULT, true);
					message.put(SystemConstant.DATA, com.readValue(com.writeValueAsString(result.getCollection()),TypeFactory.defaultInstance().constructCollectionType(result.getCollection().getClass(), clz)));
					log.debug(com);
				}
				message.put(SystemConstant.MESSAGE, "operator success");
			} else if(object instanceof EmptyResult){
				message.put(SystemConstant.RESULT, true);
				message.put(SystemConstant.MESSAGE, "operator success");
				EmptyResult er = (EmptyResult)object;
				message.put(SystemConstant.DATA, StringUtils.isNotEmpty(er.getMesg()) ? er.getMesg() : StringUtils.EMPTY);
			}else{
				//here is normal output the object by json
				message.put(SystemConstant.RESULT, true);
				if(object!=null){
					message.put(SystemConstant.DATA, object);
				}
				message.put(SystemConstant.MESSAGE, "operator success");
			}
		}
		log.debug(message);
		super.writeInternal(message, outputMessage);
	}
	
}
