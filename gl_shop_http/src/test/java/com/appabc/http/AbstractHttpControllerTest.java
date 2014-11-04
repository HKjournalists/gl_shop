package com.appabc.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.utils.LogUtil;
import com.appabc.datas.tool.UserTokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午3:16:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:applicationContext-redis.xml",
		"classpath*:applicationContext-tools.xml",
		"classpath*:applicationContext-datas.xml",
		"classpath*:applicationContext-pay.xml",
		"classpath*:applicationContext.xml",
		"file:src\\main\\webapp\\WEB-INF\\spring-mvc.xml",
		"classpath*:spring-mvc.xml"
		})
@ActiveProfiles(value="develop")
public abstract class AbstractHttpControllerTest {
	
	private ApplicationContext applicationContext;
	private HandlerAdapter handlerAdapter;

	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;
	protected Gson gson = new GsonBuilder().create();
	protected LogUtil logUtil = LogUtil.getLogUtil(getClass());
	@Autowired
	private UserTokenManager userTokenManager;
	
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final HandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        final HandlerExecutionChain handler = handlerMapping.getHandler(request);
        Assert.notNull(handler, "No handler found for request, check you request mapping");

        final Object controller = handler.getHandler();
        // if you want to override any injected attributes do it here

        final HandlerInterceptor[] interceptors =
            handlerMapping.getHandler(request).getInterceptors();
        for (HandlerInterceptor interceptor : interceptors) {
            final boolean carryOn = interceptor.preHandle(request, response, controller);
            if (!carryOn) {
                return null;
            }
        }

        final ModelAndView mav = handlerAdapter.handle(request, response, controller);
        return mav;
    }
	
	protected void print(ModelAndView mav){
		try{
			logUtil.info("######################Junit Test SpringMVC Controller Start###########################");
			logUtil.info("Status             : "+response.getStatus());
			logUtil.info("Cookies            : "+gson.toJson(response.getCookies()));
			logUtil.info("BufferSize         : "+response.getBufferSize());
			logUtil.info("IncludeUrl         : "+response.getIncludedUrl());
			logUtil.info("ContentType        : "+response.getContentType());
			logUtil.info("ErrorMessage       : "+response.getErrorMessage());
			logUtil.info("ForwardedUrl       : "+response.getForwardedUrl());
			logUtil.info("ModelAndView       : "+gson.toJson(mav));
			logUtil.info("RedirectedUrl      : "+response.getRedirectedUrl());
			logUtil.info("ContentLength      : "+response.getContentLength());
			logUtil.info("ContentAsString    : "+response.getContentAsString());
			logUtil.info("CharacterEncoding  : "+response.getCharacterEncoding());
			logUtil.info("######################Junit Test SpringMVC Controller End###########################");
		}catch(Exception e){}
	}
	
	public ApplicationContext getApplicationContext() {
	    return applicationContext;
	}
	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
	    this.applicationContext = applicationContext;
	}
	
	@Before
	public void setUp() throws Exception {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	    this.handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
	}
	
	/**
	 * 模拟用户登录
	 * @param userName 用户名
	 * @param userId 用户ID
	 * @param cid 企业ID
	 */
	public void loginSimulation(String userName, String userId, String cid){
		TUser user = new TUser();
		TCompanyInfo ci = new TCompanyInfo();
		if(userName == null || userName.equals("")){
			userName = "13800138000";
		}
		if(userId == null || userId.equals("")){
			userId = "100";
		}
		if(cid == null || cid.equals("")){
			cid = "CompanyInfoId000000323092014END";
		}
		
		user.setId(userId);
		user.setUsername(userName);
		user.setCid(cid);
		
		ci.setCname("abc公司");
		ci.setAuthstatus("1");
		ci.setCtype("1");
		
		
		UserInfoBean ut = this.userTokenManager.saveUserToken(user, ci);
		request.addHeader("USER_TOKEN", ut.getToken());
	}
	
	public abstract void mainTest();
	
}
