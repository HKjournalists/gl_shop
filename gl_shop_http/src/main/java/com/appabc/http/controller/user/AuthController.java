/**
 *
 */
package com.appabc.http.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.SysLogEnum.LogBusinessType;
import com.appabc.bean.enums.SysLogEnum.LogLevel;
import com.appabc.bean.enums.SystemInfo.ServerEnvironmentEnum;
import com.appabc.bean.enums.UserInfo.ClientTypeEnum;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TSystemLog;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.bean.TokenBean;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ErrorCode;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.system.ISystemLogService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.service.user.IUserSettingService;
import com.appabc.datas.tool.UserTokenManager;
import com.appabc.http.utils.UserAuthUtil;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.GuarantStatusCheck;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import com.appabc.tools.utils.SystemParamsManager;

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
	private GuarantStatusCheck guarantStatusCheck;
	@Autowired
	private IOrderFindService orderFindService;
	@Autowired
	private ISystemLogService systemLogService;
	@Autowired
	private SystemParamsManager spm;
	@Autowired
	private IUserSettingService userSettingService;
	
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
//		if(user == null){ // 兼容没升级版本用明文密码登录
//			try {
//				user = userService.queryByNameAndPass(username, BaseCoder.encryptMD5(username+password));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		/******日志************************/
		TSystemLog log = new TSystemLog();
		log.setBusinessid(username);
		log.setBusinesstype(LogBusinessType.BUSINESS_TYPE_USER_LOGIN);
		log.setCreater(username);
		
		if(user == null){
			log.setLoglevel(LogLevel.LOG_LEVEL_ERROR);
			log.setLogcontent("用户名或密码错误,username="+username+"password="+password);
			systemLogService.addToCache(log);
			return this.buildFailResult(ErrorCode.USER_LOGIN_ERROR, "用户名或密码错误");
		}else{
			Map<String, Object> map = UserAuthUtil.checkStatus(user.getStatus().getVal());
			if(map.get("code").equals(0)){
				
				// 其它设备下线通知
				UserInfoBean beforUserToken = userTokenManager.getBeanByUsername(username);
				if(beforUserToken != null && (StringUtils.isNotEmpty(user.getClientid()) && StringUtils.isNotEmpty(clientid) && !user.getClientid().equalsIgnoreCase(clientid))){ // 用户在其它设备上登录,token还在有效期
					MessageInfoBean mi = new MessageInfoBean(MsgBusinessType.BUSINESS_TYPE_USER_LOGIN_OTHER_DEVICE, user.getCid(), user.getCid(), SystemMessageContent.getMsgContentOfUserLoginOtherDivce(user.getClienttype()));
					mi.setSendPushMsg(true);
					mi.addParam("userName", user.getUsername());
					msm.msgSend(mi);
					logger.info("==="+user.getUsername()+"被踢下线===============");
					logger.info("ip:"+request.getRemoteAddr());
				}
				TCompanyInfo ci = companyInfoService.queryAuthCmpInfo(user.getCid());
				if(ci != null){
					UserInfoBean ut = userTokenManager.saveUserToken(user, ci);
					if(ut != null && CollectionUtils.isNotEmpty(ut.getTokenList())){
						otherInfoManage(ut.getTokenList().getFirst(), user, ci, clientid, clienttype, request);
						
						log.setLoglevel(LogLevel.LOG_LEVEL_INFO);
						
						ClientTypeEnum ctype = ClientTypeEnum.enumOf(clienttype);
						String strCtype = ctype == null ? null :ctype.getText();
						log.setLogcontent("用户登录,userToken="+user.getUserToken()+",终端类型:"+strCtype+",终端ID："+clientid);
						systemLogService.addToCache(log);
						
						return buildFilterResultWithBean(user, "id","password","status","createdate","updatedate");
					}else{
						return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "userToken创建失败");
					}
				}else{
					log.setLoglevel(LogLevel.LOG_LEVEL_WARN);
					log.setLogcontent("用户登录错误，企业信息不存在,username="+username+"password="+password);
					systemLogService.addToCache(log);
					return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, map.get("errStr").toString());
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
			userTokenManager.delUserInfoByUser(ui.getUserName());
			
			
			TUser user = new TUser();
			user.setUsername(ui.getUserName());
			user = this.userService.query(user);
			
			if(user != null){
				user.setClientid(null);
				this.userService.modify(user); // 用户退出时删除clientId
			}
			
			/******日志************************/
			TSystemLog log = new TSystemLog();
			log.setBusinessid(ui.getUserName());
			log.setBusinesstype(LogBusinessType.BUSINESS_TYPE_USER_LOGOUT);
			log.setCreater(ui.getUserName());
			log.setLoglevel(LogLevel.LOG_LEVEL_INFO);
			log.setLogcontent("用户退出,user="+ui.getUserName());
			systemLogService.addToCache(log);
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
		
		String clientid = request.getParameter("clientid"); // 客户端标识 
		String clienttype = request.getParameter("clienttype"); // 客户端类型
		
		TSystemLog log = new TSystemLog();
		log.setBusinessid(oldUserToken);
		log.setBusinesstype(LogBusinessType.BUSINESS_TYPE_USER_TOKEN_UPDATE);
		
		if(oldUserToken != null && !oldUserToken.trim().isEmpty()){
			UserInfoBean ut = userTokenManager.getBeanByToken(oldUserToken);
			if(ut != null && ut.getUserName() != null){
				
				TUser user = this.userService.query(ut.getId());
				TCompanyInfo ci = companyInfoService.queryAuthCmpInfo(user.getCid());
				if(ci != null){
					ut = userTokenManager.saveUserToken(user, ci);
					if(ut != null && CollectionUtils.isNotEmpty(ut.getTokenList())){
						otherInfoManage(ut.getTokenList().getFirst(), user, ci, clientid, clienttype, request);
						
						/******日志************************/
						log.setCreater(ut.getUserName());
						log.setLoglevel(LogLevel.LOG_LEVEL_INFO);
						log.setLogcontent("oldToken="+oldUserToken+",newToken="+user.getUserToken()+",clientid="+clientid);
						systemLogService.addToCache(log);
						
						return buildFilterResultWithBean(user, "id","password","status","createdate","updatedate");
					
					}else{
						return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "new userToken创建失败");
					}
				}else{
					log.setCreater(ut.getUserName());
					log.setLoglevel(LogLevel.LOG_LEVEL_WARN);
					log.setLogcontent("用户登录错误，企业信息不存在,username="+ut.getUserName()+"oldToken="+oldUserToken);
					systemLogService.addToCache(log);
					return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "企业信息不存在,username="+ut.getUserName()+"oldToken="+oldUserToken);
				}
				
			}else{
				/******日志************************/
				log.setCreater(oldUserToken);
				log.setLoglevel(LogLevel.LOG_LEVEL_ERROR);
				log.setLogcontent("Token已过期或不存在,oldToken="+oldUserToken+",clientid="+clientid);
				systemLogService.addToCache(log);
				
				return this.buildFailResult(ErrorCode.TOKEN_IS_OUT_DATE, "userToken已过期或不存在");
			}
			
		}else{
			return this.buildFailResult(ErrorCode.USER_STATUS_ERROR, "非法请求");
		}
		
	}
	
	/**
	 * The public code is user login and oldToken update 
	 * @param ut
	 * @param user
	 * @param ci
	 * @param clientid
	 * @param clienttype
	 */
	private void otherInfoManage(TokenBean tokenBean,TUser user, TCompanyInfo ci, String clientid, String clienttype, HttpServletRequest request){
		user.setUserToken(tokenBean.getToken());
		user.setEffTimeLength(tokenBean.getEffTimeLength());
		if(StringUtils.isNotEmpty(clientid)) clientid = clientid.replace(" ", ""); // 去除空格
		
		/**从请求头中获取客户端使用的版本号*************/
		String version = request.getHeader("VERSION");
		String channelType = request.getParameter("channelType"); // 软件来源渠道(网页下载版、appstore下载版)
		if(StringUtils.isEmpty(version)) {
			version = "NoVersion";
		}
		// ClientId和ClientType与账号绑定
		this.userService.clientBinding(user.getId(),user.getUsername(), clientid, clienttype, version, channelType); // 帐号与客户端绑定
		
		user.setCname(ci.getCname());
		user.setCtype(ci.getCtype());
		user.setAuthstatus(ci.getAuthstatus());
		float monay = passPayLocalService.getGuarantyTotal(ci.getId()); // 保证金总金额
		user.setBailstatus(guarantStatusCheck.checkCashDeposit(ci.getId(), monay));
		user.setContractTotal(contractInfoService.getTotalByCid(user.getCid())); // 企业合同总数
		user.setOrderfindTotal(orderFindService.getTotalByCid(ci.getId())); // 企业询单总数
		
		TPassbookInfo pbiDeposit = passPayLocalService.getPurseAccountInfo(ci.getId(), PurseType.DEPOSIT);// 货款余额
		TPassbookInfo pbiGuaranty = passPayLocalService.getPurseAccountInfo(ci.getId(), PurseType.GUARANTY);// 保证金余额
		if(pbiDeposit != null) user.setDeposit(pbiDeposit.getAmount().doubleValue());
		if(pbiGuaranty != null) user.setGuaranty(pbiGuaranty.getAmount().doubleValue());
		
		user.setServerEnvironment(ServerEnvironmentEnum.enumOf(this.spm.getInt(SystemConstant.SERVER_ENVIRONMENT))); // 服务器环境
		
		if(user.getAuthstatus()==null || user.getAuthstatus()==AuthRecordStatus.AUTH_STATUS_CHECK_NO){
			user.setIsAuthRemind(userSettingService.getRemind(user.getCid()));
		}
		
	}

}
