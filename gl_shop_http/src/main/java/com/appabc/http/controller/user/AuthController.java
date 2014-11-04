/**
 *
 */
package com.appabc.http.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.UserTokenManager;
import com.appabc.http.utils.UserAuthUtil;
import com.appabc.tools.service.system.ISystemParamsService;

/**
 * @Description : 用户鉴权
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月27日 下午2:11:38
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController<TUser> {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ISystemParamsService systemParamsService;
	@Autowired
	private UserTokenManager userTokenManager;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private IContractInfoService contractInfoService;
	
	
	/**
	 * 客户端用户登录
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public Object login(HttpServletRequest request,HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String clientid = request.getParameter("clientid"); // 客户端标识 
		String clienttype = request.getParameter("clienttype"); // 客户端类型
		
		TUser user = userService.queryByNameAndPass(username, password);
		if(user == null){
			return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "用户名或密码错误");
		}else{
			Map<String, Object> map = UserAuthUtil.checkStatus(user.getStatus());
			if(map.get("code").equals(0)){
				TCompanyInfo ci = companyInfoService.query(user.getCid());
				UserInfoBean ut = userTokenManager.saveUserToken(user, ci);
				
				if(ut != null){
					user.setUserToken(ut.getToken());
					user.setEffTimeLength(ut.getEffTimeLength());
					user.setContractTotal(contractInfoService.getTotalByCid(user.getCid())); // 企业合同总数
					
					if(clientid != null && !clientid.equals(user.getClientid())){ 
						user.setClientid(clientid);
						user.setClienttype(clienttype);
						this.userService.modify(user); // 更新客户端ID
					}
					
					if(ci != null){
						user.setCname(ci.getCname());
					}
					
					return buildFilterResultWithBean(user, "id","password","status","createdate","updatedate");
				}else{
					return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "userToken创建失败");
				}
				
			}else{
				return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, map.get("errStr").toString());
			}
		}
		
	}
	
	/**
	 * 用户退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout" ,method={RequestMethod.GET,RequestMethod.POST})
	public Object logout(HttpServletRequest request,HttpServletResponse response) {
		
		UserInfoBean ui =  this.getCurrentUser(request);
		if(ui != null){
			userTokenManager.delUserTokenByUser(ui.getUserName());
		}
		
		return this.buildSuccessResult("用户已退出", "");
	}
	
	
	/**
	 * userToken更新
	 * @param oldUserToken
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserToken")
	public Object updateUserToken(@RequestParam(value="oldUserToken")String oldUserToken,HttpServletRequest request,HttpServletResponse response) {
		
		if(oldUserToken != null && !oldUserToken.trim().isEmpty()){
			UserInfoBean ut = userTokenManager.getBeanByToken(oldUserToken);
			if(ut != null && ut.getUserName() != null){
				
				TUser user = this.userService.query(ut.getId());
				TCompanyInfo ci = companyInfoService.query(user.getCid());
				ut = userTokenManager.saveUserToken(user, ci);
				if(ut != null){
					return buildFilterResultWithBean(ut, "id","expTime");
				}else{
					return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "userToken创建失败");
				}
				
				
			}else{
				return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "userToken已过期或不存在");
			}
			
			
		}else{
			return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "非法请求");
		}
		
	}

}
