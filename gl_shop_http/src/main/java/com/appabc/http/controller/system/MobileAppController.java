package com.appabc.http.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TMobileAppVersion;
import com.appabc.common.base.controller.BaseController;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.system.IMobileAppVersionService;
import com.appabc.http.utils.HttpApplicationErrorCode;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年12月2日 下午8:01:13
 */

@Controller
@RequestMapping(value="/app")
public class MobileAppController extends BaseController<TMobileAppVersion> {
	
	@Autowired
	private IMobileAppVersionService imavService;

	@ResponseBody
	@RequestMapping(value = "/checkVersion")
	public Object checkVersion(HttpServletRequest request,HttpServletResponse response) {
		String devices = request.getParameter("devices");
		String lastNo = request.getParameter("lastNo");
		if(StringUtils.isEmpty(devices)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"devices is not allow null.");
		}
		if(StringUtils.isEmpty(lastNo)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"lastNo is not allow null.");
		}
		try {
			TMobileAppVersion obj = imavService.getMobileAppUpdateInfo(devices, lastNo);
			if(obj == null){
				//return buildEmptyResult();//"You version is the newest version !"
				return new TMobileAppVersion();
			}else{
				obj.setId(StringUtils.EMPTY);
				obj.setUpdatetime(null);
				return obj;
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			return this.buildFailResult(HttpApplicationErrorCode.MOBILE_APP_VERSION_ERROR, e.getMessage());
		}
	}
	
}
