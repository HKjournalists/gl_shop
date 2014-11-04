/**
 *
 */
package com.appabc.http.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.user.IUserService;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description : 图片验证码接口
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 上午10:35:37
 */
@Controller
@RequestMapping(value = "/imgcode")
public class ImgValidateCodeController extends BaseController<TUser> {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ValidateCodeManager vcm;
	
	/**
	 * 获取图片验证码
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/getCode")
	public void getImgVldCode(HttpServletRequest request,
			HttpServletResponse response){
		String deviceId = request.getParameter("deviceId");
		
		/********用户验证************************/
		if(StringUtils.isNotEmpty(deviceId)){
			this.vcm.outputImgCode(deviceId, request, response);
		}
		
	}
	
	/**
	 * 验证码验证
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check")
	public Object vldCode(HttpServletRequest request,
			HttpServletResponse response){
		
		String deviceId = request.getParameter("deviceId");
		String imgCode = request.getParameter("imgCode");
		
		if(StringUtils.isNotEmpty(imgCode) && StringUtils.isNotEmpty(deviceId)){
			String code = vcm.getImgCode(deviceId);
			if(code != null && code.equals(imgCode)){
				vcm.delImgCode(deviceId);
				return this.buildSuccessResult("验证码正确", "");
			}else{
				return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码不存在或已过期");
			}
		}
		
		return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码错误");
	}
	
	

}
