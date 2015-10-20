package com.appabc.common.base.controller;


import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.common.base.bean.EmptyResult;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.exception.BaseException;
import com.appabc.common.base.exception.BusinessException;
import com.appabc.common.utils.*;
import com.appabc.common.utils.pagination.PageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @Description : controller 基类和一些公用的方法
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 
 * Create Date : 2014年8月22日 下午5:46:29
 */

public abstract class BaseController<T extends BaseBean> {

	protected LogUtil log = LogUtil.getLogUtil(this.getClass());

	/*google gson util tool*/
	protected Gson gson = new GsonBuilder().create();
	
	@Autowired
	private RedisHelper redisHelper;

	/**  
	 * handleLevelException (记录不同级别的日志信息)  
	 * @param Level,Exception  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	protected void handleLevelException(Level level, Exception e) {
		if (e == null) {
			return;
		}
		if (level == null) {
			level = Level.INFO;
		}
		if (Level.ERROR == level && log.isEnabledFor(Level.ERROR)) {
			log.error(e.getMessage(), e);
		} else if (Level.DEBUG == level && log.isEnabledFor(Level.DEBUG)) {
			log.debug(e.getMessage(), e);
		} else if (Level.INFO == level && log.isEnabledFor(Level.INFO)) {
			log.info(e.getMessage(), e);
		} else if (Level.WARN == level && log.isEnabledFor(Level.WARN)) {
			log.warn(e.getMessage(), e);
		} else {
			if (log.isEnabledFor(Level.INFO)) {
				log.info(e.getMessage(), e);
			}
		}
		e.printStackTrace();
	}

	/**  
	 * getCurrentUser (获取当前登录用户信息)  
	 * @param HttpServletRequest request  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	protected UserInfoBean getCurrentUser(HttpServletRequest request){
		UserInfoBean u = null;
		final String USERTOKEN_MAP_KEY = "TOKEN_MAP";
		String userToken = request.getHeader(SystemConstant.ACCESS_TOKEN);
		if(StringUtils.isEmpty(userToken)){
			throw new BusinessException(ErrorCode.USER_UNLOGINE_ERROR,"user is un login,please login first.");
		}
		byte[] bytes = null;
		try {
			bytes = this.redisHelper.hget(USERTOKEN_MAP_KEY.getBytes("UTF-8"), userToken.getBytes("UTF-8"));
			u =  (UserInfoBean) SerializeUtil.unserialize(bytes);
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException(ErrorCode.USER_UNLOGINE_ERROR,"get login user info throw the error,please login again.");
		}
		return u;
	}
	
	/**  
	 * getCurrentUserName (获取当前登录用户名字)  
	 * @param HttpServletRequest request  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	protected String getCurrentUserName(HttpServletRequest request){
		return this.getCurrentUser(request).getUserName();
	}
	
	/**  
	 * getCurrentUserId (获取当前登录用户ID)  
	 * @param HttpServletRequest request  
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	protected String getCurrentUserId(HttpServletRequest request){
		return this.getCurrentUser(request).getId();
	}
	
	/**
	 * 获取当前登录用户的企业ID
	 * @param request
	 * @return
	 */
	protected String getCurrentUserCid(HttpServletRequest request){
		return this.getCurrentUser(request).getCid();
	}
	
	/**  
	 * handleException (异常控制，这便是异常细节可控，将来可用于支持国际化（异常信息国际化）)  
	 * @author Bill huang 
	 * @param ex,request   
	 * @return ModelAndView  
	 * @exception   
	 * @since  1.0.0  
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		CheckResult result = null;
		if(ex instanceof BusinessException){
			result = new CheckResult(((BusinessException) ex).getErrorCode(), ex.getMessage());
		} else if(ex instanceof BaseException){
			result = new CheckResult(((BaseException) ex).getErrorCode(), ex.getMessage());
		} else {
			result = new CheckResult(ErrorCode.GENERIC_EXCEPTION_CODE, SystemConstant.EXCEPTIONMESSAGE);
		}
		mav.addObject(SystemConstant.ERRORCODE,result);
		ex.printStackTrace();
		log.debug(ex);
		return mav;
	}
	
	/**  
	 * buildObjectJson (将对象封装成json字符串，json字符串key)  
	 * @param obj
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public String buildObjectJson(Object obj) {
		return gson.toJson(Collections.singletonMap(SystemConstant.DATA, obj));
	}

	/**  
	 * buildRetObjJson (将对象封装成json字符串,json字符串不带key)  
	 * @param obj
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public String buildRetObjJson(Object obj) {
		return gson.toJson(obj);
	}

	/**  
	 * buildRetObjJson (将对象和成功消息封装成json字符串)  
	 * @param msg;obj
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public String buildSuccessRetJson(String msg, Object obj) {
		Map<String, Object> success = new HashMap<String, Object>();
		success.put(SystemConstant.RESULT, true);
		success.put(SystemConstant.DATA, obj);
		success.put(SystemConstant.MESSAGE, msg);
		return gson.toJson(success);
	}

	/**  
	 * buildFailRetInfo (将对象和失败消息封装成json字符串)  
	 * @param errorMsg;obj
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public String buildFailRetInfo(String errorMsg, Object obj) {
		Map<String, Object> fail = new HashMap<String, Object>();
		fail.put(SystemConstant.RESULT, false);
		fail.put(SystemConstant.DATA, obj);
		fail.put(SystemConstant.MESSAGE, errorMsg);
		return gson.toJson(fail);
	}

	/**  
	 * buildSuccessResult (将构造成功返回的结果,通过正确消息和需要返回的数据对象)  
	 * @param msg;objs
	 * @author Bill huang 
	 * @return CheckResult  
	 * @exception   
	 * @since  1.0.0  
	 */
	public CheckResult buildSuccessResult(String msg, Object... objs) {
		if (StringUtils.isEmpty(msg)) {
			return null;
		}
		CheckResult cr = new CheckResult(msg, objs);
		return cr;
	}
	
	/**  
	 * buildSuccessResult (构造空消息返回的结果)  
	 * @param msg
	 * @author Bill huang 
	 * @return CheckResult  
	 * @exception   
	 * @since  1.0.0  
	 */
	public EmptyResult buildEmptyResult(String msg){
		EmptyResult er = new EmptyResult();
		if(StringUtils.isNotEmpty(msg)){
			er.setMesg(msg);
		}
		return er;
	}
	
	/**  
	 * buildEmptyResult (构造空消息返回的结果)  
	 * @param 
	 * @author Bill huang 
	 * @return EmptyResult  
	 * @exception   
	 * @since  1.0.0  
	 */
	public EmptyResult buildEmptyResult(){
		return this.buildEmptyResult(StringUtils.EMPTY);
	}
	
	/**
	 * buildFilterResultWithString
	 * (支持过滤不需要的属性，返回给客户端时候，将构造成功返回的结果,通过正确消息和需要返回的数据对象)
	 * 
	 * @param collection
	 *            ;filterPropertyNames
	 * @author Bill huang
	 * @return CheckResult
	 * @exception
	 * @since 1.0.0
	 */
	public FilterResult buildFilterResultWithString(Collection<?> c,
			String... filterPropertyNames) {
		if (CollectionUtils.isEmpty(c)) {
			return new FilterResult();
		}
		FilterResult filterResult = new FilterResult(c, filterPropertyNames);
		return filterResult;
	}

	/**
	 * buildFilterResultWithBean
	 * (支持过滤不需要的属性，返回给客户端时候，将构造成功返回的结果,通过正确消息和需要返回的数据对象)
	 * 
	 * @param collection
	 *            ;filterPropertyNames
	 * @author Bill huang
	 * @return CheckResult
	 * @exception
	 * @since 1.0.0
	 */
	public FilterResult buildFilterResultWithBean(Object obj,
			String... filterPropertyNames) {
		if (obj == null) {
			return null;
		}
		Collection<Object> c = new HashSet<Object>();
		c.add(obj);
		FilterResult filterResult = new FilterResult(c, filterPropertyNames);
		return filterResult;
	}

	/**
	 * buildFilterResultWithArray
	 * (支持过滤不需要的属性，返回给客户端时候，将构造成功返回的结果,通过正确消息和需要返回的数据对象)
	 * 
	 * @param collection
	 *            ;filterPropertyNames
	 * @author Bill huang
	 * @return CheckResult
	 * @exception
	 * @since 1.0.0
	 */
	public FilterResult buildFilterResultWithArray(Collection<?> c,
			String... filterPropertyNames) {
		if (CollectionUtils.isEmpty(c)) {
			return new FilterResult();
		}
		FilterResult filterResult = new FilterResult(c, filterPropertyNames);
		return filterResult;
	}
	
	/**  
	 * buildFailResult (将构造错误返回的结果,通过错误码和错误消息需要返回的数据对象)  
	 * @param errorCode;errorMessage
	 * @author Bill huang 
	 * @return CheckResult  
	 * @exception   
	 * @since  1.0.0  
	 */
	public CheckResult buildFailResult(int errorCode, String errorMessage) {
		if (errorCode == 0 || StringUtils.isEmpty(errorMessage)) {
			return null;
		}
		CheckResult cr = new CheckResult(errorCode, errorMessage);
		return cr;
	}

	/**  
	 * wrapperErrorException (记录ERROR级别的日志)  
	 * @param Exception
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public void wrapperErrorException(Exception e) {
		if (e == null) {
			return;
		}
		this.handleLevelException(Level.ERROR, e);
	}

	/**  
	 * wrapperDebugException (记录DEBUG级别的日志)  
	 * @param Exception
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public void wrapperDebugException(Exception e) {
		if (e == null) {
			return;
		}
		this.handleLevelException(Level.DEBUG, e);
	}

	/**  
	 * wrapperInfoException (记录INFO级别的日志)  
	 * @param Exception
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public void wrapperInfoException(Exception e) {
		if (e == null) {
			return;
		}
		this.handleLevelException(Level.INFO, e);
	}

	/**  
	 * wrapperWarnException (记录WARN级别的日志)  
	 * @param Exception
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public void wrapperWarnException(Exception e) {
		if (e == null) {
			return;
		}
		this.handleLevelException(Level.WARN, e);
	}

	/**  
	 * wrapperException (记录所有all级别的日志)  
	 * @param Exception
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public void wrapperException(Exception e) {
		if (e == null) {
			return;
		}
		this.handleLevelException(null, e);
	}

	/**  
	 * initializeQueryContext (初始化查询上下文,填充分页信息和查询数据的参数)  
	 * @param req
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	public QueryContext<T> initializeQueryContext(
			javax.servlet.http.HttpServletRequest req) {
		QueryContext<T> qContext = new QueryContext<>();
		PageModel page = initilizePageParams(qContext.getPage(),req);
		qContext.setPage(page);
		qContext = this.buildParametersToQueryContext(qContext, req);
		return qContext;
	}
	
	/**  
	 * initilizePageParams (初始化分页信息)  
	 * @param page;req.
	 * @author Bill huang 
	 * @return PageModel  
	 * @exception   
	 * @since  1.0.0  
	 */
	public PageModel initilizePageParams(PageModel page ,HttpServletRequest req){
		String pageIndex = req.getParameter(SystemConstant.PAGEINDEX);
		int currentPage = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
		String page_Size = req.getParameter(SystemConstant.PAGESIZE);
		int pageSize = page_Size == null ? 10 : Integer.parseInt(page_Size);

		page.setPageIndex(currentPage);
		page.setPageSize(pageSize);
		return page;
	}
	
	/**  
	 * buildParametersToQueryContext (填充查询数据的参数)  
	 * @param qContext ; req
	 * @author Bill huang 
	 * @return QueryContext<T>  
	 * @exception   
	 * @since  1.0.0  
	 */
	@SuppressWarnings("unchecked")
	public QueryContext<T> buildParametersToQueryContext(QueryContext<T> qContext,
			HttpServletRequest req) {
		qContext.setParameters(this.buildParametersToMap(req));
		qContext.setBeanParameter((T) this.buildParametersToBean(req));
		return qContext;
	}

	/**  
	 * buildParametersToMap (填充查询数据的参数)  
	 * @author Bill huang
	 * @return Map<String, Object>  
	 * @exception   
	 * @since  1.0.0  
	 */
	public Map<String, Object> buildParametersToMap(HttpServletRequest req) {
		Enumeration<?> e = req.getParameterNames();
		Map<String, Object> map = new HashMap<>();
		
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String parameter = req.getParameter(name);
			if (StringUtils.isNotEmpty(parameter)) {
				map.put(name, parameter.trim());
			}
		}
		
		return map;
	}
	
	/**
	 * 将查询数据填充到泛型实体中
	 * @return
	 */
	private Object buildParametersToBean(HttpServletRequest req){
		try {
			@SuppressWarnings("rawtypes")
			Class type = getGenericType(0);
			Object obj = type.newInstance();
			ServletRequestDataBinder binder = new ServletRequestDataBinder(obj);
			binder.bind(req);
			return obj;
		} catch (Exception e) {
			log.error(e.getMessage());
		} 
		
		return null;
	}
	
	/**
	 * 获取当前泛型类型CLASS
	 * @param index
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getGenericType(int index) {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
	
	/**
	 * @Description : 获取配置消息根据CODE
	 * @param code
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getMessage(String code){
		if(StringUtils.isEmpty(code)){
			return StringUtils.EMPTY;
		}
		return MessagesUtil.getMessage(code, MessagesUtil.locale);
	}
	
	/**
	 * @Description : 获取配置消息根据CODE和local
	 * @param code;localTag
	 * @return String
	 * @since 1.0
	 * @throws null
	 * @author Bill Huang
	 * */
	public String getMessage(String code,String localTag){
		if(StringUtils.isEmpty(code)){
			return StringUtils.EMPTY;
		}
		Locale l = null;
		if(StringUtils.isNotEmpty(localTag)){
			l = Locale.forLanguageTag(localTag);
		} else {
			l = MessagesUtil.locale;
		}
		return MessagesUtil.getMessage(code, l);
	}
	
	public int getBusinessExceptionErrorCode(BaseException se,int defaultCode){
		if(se == null){
			return defaultCode;
		}
		return se.getErrorCode()!=ErrorCode.GENERIC_ERROR_CODE ? se.getErrorCode() : defaultCode;
	}
	
	public CheckResult getBuildFailureResult(BaseException se,int defaultCode,String...defaultMesg){
		int errorCode = getBusinessExceptionErrorCode(se, defaultCode);
		String _defaultMesg = defaultMesg != null && defaultMesg.length > 0 ? defaultMesg[0] : StringUtils.EMPTY;
		return buildFailResult(errorCode, se == null ? _defaultMesg : se.getMessage());
	}

}
