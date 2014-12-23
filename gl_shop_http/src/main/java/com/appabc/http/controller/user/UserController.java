package com.appabc.http.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.user.IUserService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.http.utils.UserAuthUtil;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 
 * @Create Date : 2014年8月25日 下午1:57:20
 */

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController<TUser> {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ValidateCodeManager vcm;

	/**
	 * 用户注册
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public Object addUser(HttpServletRequest request,
			HttpServletResponse response, TUser user) {
		if(StringUtils.isEmpty(user.getPhone())){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "手机号不能为空");
		}
		String code = request.getParameter("code"); // 短信验证码
		if(StringUtils.isEmpty(code)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "验证码不能为空");
		}
		String smsCode = vcm.getSmsCode(user.getPhone());
		if(smsCode == null){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码不存在或已过期");
		}else if(!code.equalsIgnoreCase(smsCode)){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "验证码错误");
		}
		
		if(StringUtils.isEmpty(user.getUsername())){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "用户名不能为空");
		}else if(user.getUsername().length() > 50){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "用户最长为50");
		}
		if(StringUtils.isEmpty(user.getPassword())){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "密码不能为空");
		}
		
		if(this.userService.isExistUsername(user.getUsername())){
			return this.buildFailResult(ErrorCode.GENERIC_ERROR_CODE, "用户已被注册");
		}
		
		try {
			this.vcm.delSmsCode(user.getPhone());
			this.userService.register(user);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		return buildSuccessResult("用户注册成功", "");
	}


	/**
	 * 用户密码修改
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mdypwd")
	public Object mdypwd(HttpServletRequest request,
			HttpServletResponse response) {
		
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		String userName = request.getParameter("userName");
		String code = request.getParameter("code"); // 短信验证码
		// 数据完整性检查
		if(StringUtils.isEmpty(code)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "短信验证码不能为空");
		}else if(StringUtils.isEmpty(userName)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "用户名不能为空");
		}else if(StringUtils.isEmpty(oldPassword)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "原始密码不能为空");
		}else if(StringUtils.isEmpty(newPassword)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "新密码不能为空");
		}
		
		TUser user = new TUser();
		user.setUsername(userName);
		user = this.userService.query(user);
		if(user == null){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "该用户不存在");
		}
		// 短信验证码检查
		String smsCode = vcm.getSmsCode(user.getPhone());
		if(smsCode == null){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "短信验证码不存在或已过期");
		}else if(!smsCode.equalsIgnoreCase(code)){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "短信验证码错误");
		}
		// 用户状态和原始密码检查
		Map<String, Object> map = UserAuthUtil.checkStatus(user.getStatus().getVal());
		if(map.get("code").equals(1)) { // 用户状态异常
			return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, map.get("errStr").toString());
		}else if(!user.getPassword().equalsIgnoreCase(oldPassword)) {
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "原始密码错误");
		}else if(newPassword.equalsIgnoreCase(user.getPassword())){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "新密码不能和原始密码相同");
		}
		
		user.setPassword(newPassword);
		try {
			this.userService.modify(user);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return this.buildSuccessRetJson("新密码已修改成功", "");
	}
	
	/**
	 * 用户密码找回
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findpwd")
	public Object findpwd(HttpServletRequest request,
			HttpServletResponse response) {
		
		String newPassword = request.getParameter("newPassword");
		String userName = request.getParameter("userName");
		String code = request.getParameter("code"); // 短信验证码
		// 数据完整性检查
		if(StringUtils.isEmpty(code)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "短信验证码不能为空");
		}else if(StringUtils.isEmpty(userName)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "用户名不能为空");
		}else if(StringUtils.isEmpty(newPassword)){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "密码不能为空");
		}
		
		TUser user = new TUser();
		user.setUsername(userName);
		user = this.userService.query(user);
		if(user == null){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "该用户不存在");
		}
		// 短信验证码检查
		String smsCode = vcm.getSmsCode(user.getPhone());
		if(smsCode == null){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "短信验证码不存在或已过期");
		}else if(!smsCode.equalsIgnoreCase(code)){
			return this.buildFailResult(ErrorCode.ERROR_VLD_CODE, "短信验证码错误");
		}
		// 用户状态和原始密码检查
		Map<String, Object> map = UserAuthUtil.checkStatus(user.getStatus().getVal());
		if(map.get("code").equals(1)) { // 用户状态异常
			return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, map.get("errStr").toString());
		}else if(newPassword.equalsIgnoreCase(user.getPassword())){
			return this.buildFailResult(ErrorCode.DATA_IS_NOT_COMPLETE, "新密码不能和旧密码相同");
		}
		
		user.setPassword(newPassword);
		try {
			this.userService.modify(user);
		} catch (Exception e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
		
		return this.buildSuccessRetJson("新密码已设置成功", "");
	}

}
