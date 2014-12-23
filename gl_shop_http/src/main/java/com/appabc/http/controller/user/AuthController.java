/**
 *
 */
package com.appabc.http.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.common.utils.security.BaseCoder;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.CompanyUtil;
import com.appabc.datas.tool.UserTokenManager;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.http.utils.UserAuthUtil;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;

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
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;
	@Autowired
	private UserTokenManager userTokenManager;
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private IContractInfoService contractInfoService;
	@Autowired
	private MessageSendManager msm;
	@Autowired
	private IPassPayService passPayLocalService;
	@Autowired
	private CompanyUtil companyUtil;
	@Autowired
	private IOrderFindService orderFindService;
	
	
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
		if(user == null){ // 兼容没升级版本用明文密码登录
			try {
				user = userService.queryByNameAndPass(username, BaseCoder.encryptMD5(username+password));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(user == null){
			return this.buildFailResult(ErrorCode.USER_LOGIN_ERROR, "用户名或密码错误");
		}else{
			Map<String, Object> map = UserAuthUtil.checkStatus(user.getStatus().getVal());
			if(map.get("code").equals(0)){
				
				// 其它设备下线通知
				UserInfoBean beforUserToken = userTokenManager.getBeanByUsername(username);
				if(beforUserToken != null && (StringUtils.isNotEmpty(user.getClientid()) && !user.getClientid().equals(clientid))){ // 用户在其它设备上登录,token还在有效期
					MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE, user.getCid(), user.getCid(), SystemMessageContent.getMsgContentOfUserLoginOtherDivce(user.getClienttype()));
					mi.setSendPushMsg(true);
					mi.addParam("userName", user.getUsername());
					msm.msgSend(mi);
					logger.info("==="+user.getUsername()+"被踢下线===============");
					logger.info("ip:"+request.getRemoteAddr());
				}
				
				TCompanyInfo ci = companyInfoService.query(user.getCid());
				UserInfoBean ut = userTokenManager.saveUserToken(user, ci);
				
				if(ut != null){
					user.setUserToken(ut.getToken());
					user.setEffTimeLength(ut.getEffTimeLength());
					
					if(clientid != null && !clientid.equals(user.getClientid())){ 
						user.setClientid(clientid);
						user.setClienttype(ClientTypeEnum.enumOf(clienttype));
						try {
							this.userService.clientBinding(user.getId(), clientid, clienttype); // 帐号与客户端绑定
						} catch (Exception e) {
							e.printStackTrace();
							return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
						}
					}
					
					if(ci != null){
						user.setCname(ci.getCname());
						user.setCtype(ci.getCtype());
						float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
						user.setBailstatus(companyUtil.checkCashDeposit(ci.getCtype(), 0, monay));
						user.setContractTotal(contractInfoService.getTotalByCid(user.getCid())); // 企业合同总数
						user.setOrderfindTotal(orderFindService.getTotalByCid(ci.getId())); // 企业询单总数
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
					user.setUserToken(ut.getToken());
					user.setEffTimeLength(ut.getEffTimeLength());
					if(ci != null){
						user.setCname(ci.getCname());
						user.setCtype(ci.getCtype());
						float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
						user.setBailstatus(companyUtil.checkCashDeposit(ci.getCtype(), 0, monay));
						user.setContractTotal(contractInfoService.getTotalByCid(user.getCid())); // 企业合同总数
						user.setOrderfindTotal(orderFindService.getTotalByCid(ci.getId())); // 企业询单总数
					}
					return buildFilterResultWithBean(user, "id","password","status","createdate","updatedate");
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
