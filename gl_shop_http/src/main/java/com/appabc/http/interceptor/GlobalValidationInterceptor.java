package com.appabc.http.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.appabc.common.base.exception.BusinessException;
import com.appabc.common.utils.LogUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.common.utils.security.BaseCoder;
import com.appabc.datas.enums.TokenEnum;
import com.appabc.datas.tool.UserTokenManager;
import com.appabc.http.utils.HttpApplicationErrorCode;

/**
 * @Description : Global Validation Interceptor
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月18日 下午3:32:53
 */
public class GlobalValidationInterceptor extends HandlerInterceptorAdapter {
	protected LogUtil log = LogUtil.getLogUtil(getClass());

	private List<String> notAuthUrlList;

	private List<String> notAuthDirList;

	private UserTokenManager userTokenManager;

	/**
	 * separateStrByMark (通过分割字符分割字符串)
	 * 
	 * @author Bill Huang
	 * @param str
	 *            ,separatorMark
	 * @return List<String>
	 * @exception null
	 * @since 1.0.0
	 * */
	private List<String> separateStrByMark(String str, String separatorMark) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(separatorMark)) {
			return null;
		}
		List<String> list = null;
		if (str.indexOf(separatorMark) != -1) {
			String[] _ex = str.split(separatorMark);
			list = Arrays.asList(_ex);
		} else {
			list = Arrays.asList(str);
		}
		return list;
	}

	/**
	 * verityRequestSign (验证签名)
	 * 
	 * @author Bill Huang
	 * @param request
	 *            ,response
	 * @return boolean
	 * @exception null
	 * @since 1.0.0
	 * */
	private boolean verityRequestSign(HttpServletRequest request,
			HttpServletResponse response) {
		// 2 进行验证加密的处理请求，如验证成功则往下走，验证失败就不处理
		Enumeration<?> e = request.getParameterNames();
		if (e != null
				&& !org.apache.commons.collections.CollectionUtils
						.sizeIsEmpty(e)) {
			// 获取客户端传上来的签名
			String clientSign = request
					.getParameter(SystemConstant.SIGN_KEY);
			if (StringUtils.isEmpty(clientSign)) {
				return false;
			}
			// 从request对象里面获取参数进行排序
			Map<String, String> treeMap = new TreeMap<String, String>();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String parameter = request.getParameter(name);
				String[] values = request.getParameterValues(name);
				if (StringUtils.isNotEmpty(name.trim())
						&& StringUtils.isNotEmpty(parameter.trim())) {
					treeMap.put(name.trim(), parameter.trim());
					log.debug("Key: " + name + ";   Value: " + parameter);
				} else if (StringUtils.isNotEmpty(name)
						&& !ObjectUtils.isEmpty(values)) {
					treeMap.put(name.trim(), Arrays.toString(values));
					log.debug("Key: " + name + ";   Value: "
							+ Arrays.toString(values));
				} else {
					treeMap.put(name.trim(), parameter.trim());
					log.debug("Key: " + name + ";   Value: " + parameter);
				}
			}
			StringBuffer input = new StringBuffer();
			input.append(SystemConstant.APP_KEY);
			Iterator<Entry<String, String>> itor = treeMap.entrySet()
					.iterator();
			while (itor.hasNext()) {
				Entry<String, String> et = itor.next();
				if(!SystemConstant.SIGN_KEY.equalsIgnoreCase(et.getKey())){
					input.append(et.getKey());
					input.append(et.getValue());
				}
			}
			// 生成签名
			String sign;
			try {
				sign = BaseCoder.encryptMD5(input.toString());
				log.debug("sign: " + sign + ";   clientSign: "	+ clientSign);
				if (!sign.equals(clientSign)) {
					return false;
				}
				log.debug("sign.equals(clientSign): "	+ sign.equals(clientSign));
			} catch (Exception e1) {
				log.debug(e1.getMessage(), e1);
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info(" execute the preHandle method . ");
		String requestUri = request.getRequestURI();
		log.info(" the request uri is : " + requestUri);
		// 1 排除不需要登录请求的目录,这是属于配置静态资源的文件的配置过滤
		if (!CollectionUtils.isEmpty(notAuthDirList)) {
			for (String dir : notAuthDirList) {
				if (requestUri.indexOf(dir) != -1) {
					return super.preHandle(request, response, handler);
				}
			}
		}
		// 2 进行验证加密的处理请求，如验证成功则往下走，验证失败就不处理
		/*boolean flag = this.verityRequestSign(request, response);
		log.info(" The verity sign is : "+flag);
		if (!flag) {
			throw new BusinessException("the sign parameter is invalid. ");
		}*/
		// 3 排除不需要登录请求的地址
		if (!CollectionUtils.isEmpty(notAuthUrlList)) {
			for (String url : notAuthUrlList) {
				if (requestUri.lastIndexOf(url) != -1) {
					return super.preHandle(request, response, handler);
				}
			}
		}
		// 4 进行验证用户是否登录信息,主要是验证token
		String token = request.getHeader(SystemConstant.ACCESS_TOKEN);
		if(StringUtils.isNotEmpty(token) && userTokenManager.isExists(token) == TokenEnum.TOKEN_STATUS_EXIST.getVal()){//normal case
			return super.preHandle(request, response, handler);
		}else if(StringUtils.isNotEmpty(token) && TokenEnum.TOKEN_STATUS_NOTEXIST.getVal() == userTokenManager.isExists(token)){//token not exits case : user not exits ,please user to login ; 
			throw new BusinessException(HttpApplicationErrorCode.USERUNLOGINERROR,"user is not login, please login . ");
		}else if(StringUtils.isNotEmpty(token) && TokenEnum.TOKEN_STATUS_EXPIRED.getVal() == userTokenManager.isExists(token)){//token is out date case ,
			throw new BusinessException(HttpApplicationErrorCode.TOKENISOUTDATE,"the user token is out date, please login again. ");
		}else{//the token is null case
			throw new BusinessException(HttpApplicationErrorCode.USERUNLOGINERROR,"user is not login, please login . ");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info(" execute the postHandle method . ");
		super.postHandle(request, response, handler, modelAndView);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * afterCompletion(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info(" execute the afterCompletion method . ");
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * @param notAuthUrlList
	 *            the notAuthUrlList to set
	 */
	public void setNotAuthUrlList(String notAuthUrlList) {
		this.notAuthUrlList = this.separateStrByMark(notAuthUrlList, ";");
	}

	/**
	 * @param notAuthDirList
	 *            the notAuthDirList to set
	 */
	public void setNotAuthDirList(String notAuthDirList) {
		this.notAuthDirList = this.separateStrByMark(notAuthDirList, ";");
	}

	/**  
	 * @param userTokenManager the userTokenManager to set  
	 */
	public void setUserTokenManager(UserTokenManager userTokenManager) {
		this.userTokenManager = userTokenManager;
	}

	public static void main(String[] args) {
		System.out.println("http://127.0.0.1:8080/user/getUser/"
				.lastIndexOf("/user"));
	}
}
