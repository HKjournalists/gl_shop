package com.appabc.http.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TActivityJoin;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.DateUtil;
import com.appabc.datas.service.system.ISystemActivityService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.http.utils.HttpApplicationErrorCode.HttpAppSystemConstant;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年7月22日 下午5:14:47
 */

@Controller
@RequestMapping(value = "/system/activity")
public class SystemActivityController extends BaseController<TActivityJoin> {

	@Autowired
	private ISystemActivityService iSystemActivityService;
	
	@ResponseBody
	@RequestMapping(value = "/join", method=RequestMethod.POST)
	public Object joinActivity(HttpServletRequest request, HttpServletResponse response,TActivityJoin entity){
		if(entity == null){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.ENTITY_IS_NULL));
		}
		if (StringUtils.isEmpty(entity.getPhone())) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_PHONE_NULL));
		}
		if (StringUtils.isEmpty(entity.getName())) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_NAME_NULL));
		}
		if (entity.getReqnum() <= 0) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_REQNUM_NULL));
		}
		boolean isPhoneJoinAgain = iSystemActivityService.checkPhoneNumIsJoined(entity.getPhone());
		if(isPhoneJoinAgain){
			return buildFailResult(HttpApplicationErrorCode.SYSTEM_ACTIVITY_AGAIN_ERROR,getMessage(HttpAppSystemConstant.SYSTEM_ACTIVITY_REQUEST_AGAIN));
		}
		try{
			entity.setCreator(getCurrentUserId(request));
		}catch(Exception ex){
			this.log.error(ex.getMessage(), ex);
		}
		entity.setCreatedate(DateUtil.getNowDate());
		iSystemActivityService.add(entity);
		return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS));
	} 
	
}
