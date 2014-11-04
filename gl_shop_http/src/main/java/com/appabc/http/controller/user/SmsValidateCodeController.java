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
import com.appabc.datas.enums.SmsInfo;
import com.appabc.datas.service.user.IUserService;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description : 短信验证码发送和验证
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月22日 下午1:55:53
 */
@Controller
@RequestMapping(value = "/smscode")
public class SmsValidateCodeController extends BaseController<TUser> {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ValidateCodeManager vcm;	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/send")
	public Object send(HttpServletRequest request,
			HttpServletResponse response) {
		String phone = request.getParameter("phone");
		String userName = request.getParameter("userName");
		String sendType = request.getParameter("sendType"); // 发送类型
		if(StringUtils.isNotEmpty(sendType) && sendType.equals(SmsInfo.SmsSendTypeEnum.SEND_TYPE_REGISTER.getVal())){ // 注册类型
			if(this.userService.isExistUsername(userName)){
				return this.buildFailResult(ErrorCode.GENERICERRORCODE, "用户已被注册");
			}
		}
		
		if(StringUtils.isNotEmpty(phone)) {
			if(vcm.sendSmsCode(phone, userName)) {
				return this.buildSuccessResult("发送成功", "");
			} else {
				return this.buildFailResult(ErrorCode.SMS_SEND_FAIL, "验证码发送失败");
			}
		}
		
		return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "手机号不能为空");
	}
	
	/**
	 * 短信验证码发送，并校验图片验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendAndCheckImgCode")
	public Object sendAndCheckImgCode(HttpServletRequest request,
			HttpServletResponse response){
		String userName = request.getParameter("userName");
		String deviceId = request.getParameter("deviceId"); // 设备ID
		String code = request.getParameter("code");
		
		/********必填数据验证**********************/
		if(StringUtils.isEmpty(code)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "验证码不能为空");
		}else if(StringUtils.isEmpty(userName)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "用户名不能为空");
		}
		/*********图形验证码验证*******************/
		String imgCode = vcm.getImgCode(deviceId);
		if(imgCode == null){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码不存在或已过期");
		}else if(!code.equals(imgCode)){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码错误");
		}else{ // 验证码正确，本次使用后删除
			vcm.delImgCode(userName);;
		}
		/********用户验证************************/
		TUser user = new TUser();
		user.setUsername(userName);
		user = this.userService.query(user);
		if(user == null){
			return this.buildFailResult(ErrorCode.GENERICERRORCODE, "该用户不存在");
		}else if(StringUtils.isEmpty(user.getPhone())) {
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "该用户手机号为空");
		}
		
		/*******短信发送************************/
		if(vcm.sendSmsCode(user.getPhone(), user.getNick())) {
			return this.buildSuccessResult("发送成功", "");
		} else {
			return this.buildFailResult(ErrorCode.SMS_SEND_FAIL, "验证码发送失败");
		}
		
	}
	
	
}
