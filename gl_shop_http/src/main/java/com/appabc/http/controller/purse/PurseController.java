/**
 * com.appabc.pay.controller.PurseController.java
 *
 * 2014年9月16日 上午10:55:03
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.http.controller.purse;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ObjectUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.http.utils.HttpApplicationErrorCode.HttpAppSystemConstant;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.bean.TPayThirdOrgInfo;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IPayThirdInfoService;
import com.appabc.pay.service.local.IPayThirdRecordService;
import com.appabc.pay.util.CodeConstant;
import com.appabc.tools.utils.GuarantStatusCheck;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description :
 * @Copyright   : JiangSu Guoli Digital Animation Co., Ltd.
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date : 2014年9月16日 上午10:55:03
 */

@Controller
@RequestMapping(value="/purse")
public class PurseController extends BaseController<TPassbookInfo> {

	@Autowired
	private IPassPayService iPassPayService;

	@Autowired
	private IContractInfoService iContractInfoService;

	@Autowired
	private IPayThirdInfoService iPayThirdInfoService;

	@Autowired
	private IPayThirdRecordService iPayThirdRecordService;

	@Autowired
	private ValidateCodeManager validateCodeManager;

	@Autowired
	private IUserService iUserService;

	@Autowired
	protected GuarantStatusCheck gsCheck;

	private String getReturnMsgWithParamNull(String parameter){
		if(StringUtils.isEmpty(parameter)){
			return StringUtils.EMPTY;
		}
		return getMessage(HttpAppSystemConstant.PURSEACCOUNT_PARAMETER_ISNULL).replaceAll("{param}", parameter);
	}

	private String getReturnMsgWithParamMatch(String parameter){
		if(StringUtils.isEmpty(parameter)){
			return StringUtils.EMPTY;
		}
		return getMessage(HttpAppSystemConstant.PURSEACCOUNT_PARAMETER_NOTMATCH).replaceAll("{param}", parameter);

	}

	/**
	 * getPurseList(获取钱包信息列表)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPurseList",method={RequestMethod.GET,RequestMethod.POST})
	@Deprecated
	public Object getPurseList(HttpServletRequest request,HttpServletResponse response){
		TPassbookInfo tpi = new TPassbookInfo();
		tpi.setAmount(1.0);
		return tpi;
	}

	/**
	 * getPurseList(初始化钱包信息)
	 *
	 * @param request,response
	 * @author Bill Huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/initializePurseAccount",method={RequestMethod.GET,RequestMethod.POST})
	public Object initializePurseAccount(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		String cid = this.getCurrentUserCid(request);
		//if(StringUtils.isEmpty(type)){
			//return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		//}
		boolean flag = iPassPayService.initializePurseAccount(cid, PurseType.enumOf(type));
		if(flag){
			return buildSuccessResult(getMessage(HttpAppSystemConstant.INITIALIZE_PURSEACCOUNT_SUCCESS_TIPS));
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.INITIALIZE_PURSEACCOUNT_ERROR_TIPS));
		}
	}

	/**
	 * getPurseAccountInfo(获取账户信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPurseAccountInfo",method={RequestMethod.GET,RequestMethod.POST})
	public Object getPurseAccountInfo(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		String cid = this.getCurrentUserCid(request);
		QueryResult<TPassbookInfo> result = new QueryResult<TPassbookInfo>();
		float shouldGuarantNum = gsCheck.getGuarantStatus(cid);
		float totalGuarantyUsed = iPassPayService.getGuarantyTotal(cid);
		boolean isGuarantyEnough = shouldGuarantNum != 0 && totalGuarantyUsed != 0 && shouldGuarantNum <= totalGuarantyUsed;
		if(StringUtils.isEmpty(type)){
			TPassbookInfo depositAccount = iPassPayService.getPurseAccountInfo(cid, PurseType.DEPOSIT);
			TPassbookInfo guarantyAccount = iPassPayService.getPurseAccountInfo(cid, PurseType.GUARANTY);
			guarantyAccount.setGuarantyEnough(isGuarantyEnough);
			result.getResult().add(depositAccount);
			result.getResult().add(guarantyAccount);
		}else{
			PurseType purseType = PurseType.enumOf(type);
			TPassbookInfo tpbi = iPassPayService.getPurseAccountInfo(cid, purseType);
			if(purseType == PurseType.GUARANTY){
				tpbi.setGuarantyEnough(isGuarantyEnough);
			}
			result.getResult().add(tpbi);
		}
		QueryContext<ContractInfoBean> qContext = new QueryContext<ContractInfoBean>();
		qContext.getPage().setPageIndex(-1);
		qContext.addParameter("lifecycle", ContractLifeCycle.SINGED.getVal());
		qContext.addParameter("cid", this.getCurrentUserCid(request));
		qContext.addParameter("status", ContractStatus.DOING.getVal());
		qContext.addParameter("isUnPayContractList", true);
		qContext = iContractInfoService.queryContractInfoListForPagination(qContext);
		List<ContractInfoBean> res = qContext.getQueryResult().getResult();
		result.setTotalSize(CollectionUtils.isNotEmpty(res) ? res.size() : 0);
		return result;
	}

	/**
	 * getPurseAccountAndCompanyInfo(获取账户和企业信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPurseAccountAndCompanyInfo",method={RequestMethod.POST})
	public Object getPurseAccountAndCompanyInfo(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		String cid = this.getCurrentUserCid(request);
		return iPassPayService.getPurseAccountInfo(cid, pt);
	}

	/**
	 * getPayRecordList(查询流水列表信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPayRecordList",method={RequestMethod.POST,RequestMethod.GET})
	public Object getPayRecordList(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		String direction = request.getParameter("direction");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		if(StringUtils.isNotEmpty(direction)){
			PayDirection pd = PayDirection.enumOf(RandomUtil.str2int(direction));
			if(pd == null){
				return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("direction"));
			}
		}

		QueryContext<TPassbookPay> qContext = new QueryContext<TPassbookPay>();
		PageModel page = initilizePageParams(qContext.getPage(), request);
		qContext.setPage(page);
		qContext.setParameters(buildParametersToMap(request));
		qContext.addParameter("cid", this.getCurrentUserCid(request));
		return iPassPayService.payRecordList(qContext).getQueryResult().getResult();
	}

	/**
	 * getPayRecordDetail(查询流水明细信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPayRecordDetail",method={RequestMethod.POST,RequestMethod.GET})
	public Object getPayRecordDetail(HttpServletRequest request,HttpServletResponse response){
		String detailId = request.getParameter("PID");
		if(StringUtils.isEmpty(detailId)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("PID"));
		}
		return iPassPayService.payRecordDetail(detailId);
	}

	/**
	 * depositToGuaranty(货款充值保证金)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/depositToGuaranty",method={RequestMethod.POST})
	public Object depositToGuaranty(HttpServletRequest request,HttpServletResponse response){
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("balance"));
		}
		String validateCode = request.getParameter("smsCode");
		if (StringUtils.isEmpty(validateCode)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.THE_VALIDATE_CODE_ISNULL));
		}
		UserInfoBean userInfoBean = this.getCurrentUser(request);
		String smsCode = validateCodeManager.getSmsCode(userInfoBean.getPhone());
		if(!StringUtils.equalsIgnoreCase(validateCode, smsCode)){
			return buildFailResult(HttpApplicationErrorCode.ERROR_VLD_CODE,getMessage(HttpAppSystemConstant.THE_VALIDATE_CODE_EXPIREDATE));
		}
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(password)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.USER_PASSWORD_ISNULL));
		}
		String username = userInfoBean.getUserName();
		TUser user = iUserService.queryByNameAndPass(username, password);
		if(user==null){
			return buildFailResult(HttpApplicationErrorCode.USER_LOGIN_ERROR,getMessage(HttpAppSystemConstant.USER_PASSWORD_ISWORRY));
		}
		String cid = getCurrentUserCid(request);
		String sd = RandomUtil.format(balance);
		boolean flag = iPassPayService.depositToGuaranty(cid, NumberUtils.toDouble(sd));
		//业务操作完成,删除前面的的手机验证.
		validateCodeManager.delSmsCode(userInfoBean.getPhone());
		return buildSuccessResult(String.valueOf(flag));
	}

	/**
	 * addAcceptBank(新增收款人信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addAcceptBank",method={RequestMethod.POST})
	public Object addAcceptBank(HttpServletRequest request,HttpServletResponse response){
		String cid = getCurrentUserCid(request);
		String bankcard = request.getParameter("bankcard");
		if(StringUtils.isEmpty(bankcard)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("bankcard"));
		}
		String carduser = request.getParameter("carduser");
		if(StringUtils.isEmpty(carduser)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("carduser"));
		}
		String bankaccount = request.getParameter("bankaccount");
		if(StringUtils.isEmpty(bankaccount)){
			bankaccount = carduser;
		}
		String bankname = request.getParameter("bankname");
		if(StringUtils.isEmpty(bankname)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("bankname"));
		}
		String banktype = request.getParameter("banktype");
		if(StringUtils.isEmpty(banktype)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("blanktype"));
		}
		String addr = request.getParameter("addr");
		String carduserid = request.getParameter("carduserid");

		TAcceptBank tAcceptBank = new TAcceptBank();
		tAcceptBank.setBankcard(bankcard);
		tAcceptBank.setBankaccount(bankaccount);
		tAcceptBank.setCarduser(carduser);
		tAcceptBank.setCarduserid(carduserid);
		tAcceptBank.setBankname(bankname);
		tAcceptBank.setBanktype(banktype);
		tAcceptBank.setCid(cid);
		tAcceptBank.setCreator(cid);
		tAcceptBank.setCreatetime(new Date());
		tAcceptBank.setAddr(addr);

		try {
			iPassPayService.saveAcceptBank(tAcceptBank);
			return tAcceptBank;
		} catch (ServiceException e) {
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, e.getMessage());
		}
	}

	/**
	 * getAcceptBankList(查询收款人信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getAcceptBankList",method={RequestMethod.POST,RequestMethod.GET})
	public Object getAcceptBankList(HttpServletRequest request,HttpServletResponse response){
		String cid = request.getParameter("CID");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		return iPassPayService.getAcceptBankList(cid);
	}

	/**
	 * extractCashRequest(提现申请)
	 *
	 * @param request,res提现申请ponse
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/extractCashRequest",method={RequestMethod.POST})
	public Object extractCashRequest(HttpServletRequest request,HttpServletResponse response){
		String acceptId = request.getParameter("acceptId");
		String balance = request.getParameter("balance");
		String type = request.getParameter("type");
		String validateCode = request.getParameter("validateCode");
		String password = request.getParameter("password");
		UserInfoBean userInfoBean = this.getCurrentUser(request);
		if (StringUtils.isEmpty(validateCode)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL, getMessage(HttpAppSystemConstant.THE_VALIDATE_CODE_ISNULL));
		}

		String smsCode = validateCodeManager.getSmsCode(userInfoBean.getPhone());
		if(!StringUtils.equalsIgnoreCase(validateCode, smsCode)){
			return buildFailResult(HttpApplicationErrorCode.ERROR_VLD_CODE, getMessage(HttpAppSystemConstant.THE_VALIDATE_CODE_EXPIREDATE));
		}
		if (StringUtils.isEmpty(password)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL, getMessage(HttpAppSystemConstant.USER_PASSWORD_ISNULL));
		}
		TUser user = iUserService.queryByNameAndPass(userInfoBean.getUserName(), password);
		if(user==null){
			return buildFailResult(HttpApplicationErrorCode.USER_LOGIN_ERROR, getMessage(HttpAppSystemConstant.USER_PASSWORD_ISWORRY));
		}

		if(StringUtils.isEmpty(acceptId)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("acceptId"));
		}
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("balance"));
		}
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		TPassbookDraw tpd = iPassPayService.extractCashRequest(userInfoBean.getCid(), pt, NumberUtils.toDouble(balance), acceptId);
		//业务操作完成,删除前面的的手机验证.
		validateCodeManager.delSmsCode(userInfoBean.getPhone());
		if(tpd == null){
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_EXTRACT_CASH_REQUEST_TIPS));
		}else {
			return tpd;
		}
	}

	/**
	 * extractCashRequest(提现转出验证信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/extractCashValidation",method={RequestMethod.POST})
	public Object extractCashValidation(HttpServletRequest request,HttpServletResponse response){
		String tid = request.getParameter("TID");
		if(StringUtils.isEmpty(tid)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("tid"));
		}
		String result = request.getParameter("result");
		if(StringUtils.isEmpty(result)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("result"));
		}
		String reson = request.getParameter("reson");
		String cid = getCurrentUserCid(request);
		boolean _result = BooleanUtils.toBoolean(result);
		boolean flag = iPassPayService.extractCashAudit(tid, _result, reson, cid);
		if(flag){
			if(_result){
				iPassPayService.extractCashDeduct(tid);
			}
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PURSEACCOUNT_VALIDATE_EXTRACT_CASH_SUCCESS));
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_VALIDATE_EXTRACT_CASH_FAIL));
		}
	}

	/**
	 * extractCashRequest(充值记录列表信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/depositRecordList",method={RequestMethod.POST,RequestMethod.GET})
	public Object depositRecordList(HttpServletRequest request,HttpServletResponse response){
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		return iPassPayService.payRecordList(cid, pt);
	}

	/**
	 * extractCashRequest(线上充值保证金/货款)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/depositAccountOnline",method={RequestMethod.POST})
	public Object depositAccountOnline(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("balance"));
		}
		String payNo = request.getParameter("payNo");
		if(StringUtils.isEmpty(payNo)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("payNo"));
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		boolean flag = iPassPayService.depositAccountOnline(cid, pt, NumberUtils.toFloat(balance), payNo);
		if(flag){
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_ACCOUNT_ONLINE_SUCCESS));
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_ACCOUNT_ONLINE_FAIL));
		}
	}

	/**
	 * depositThirdPartInfo(线上充值保证金/货款第三方对账信息)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/depositThirdPartInfo",method={RequestMethod.POST})
	public Object depositThirdPartInfo(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("balance"));
		}
		String payNo = request.getParameter("payNo");
		if(StringUtils.isEmpty(payNo)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("payNo"));
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		boolean flag = iPassPayService.depositThirdOrgRecord(cid, pt, NumberUtils.toFloat(balance), payNo);
		if(flag){
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_THIRD_SUCCESS));
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_THIRD_FAIL));
		}
	}

	/**
	 * getUnionPayTnOrderId(银联在线支付的获取TN编号)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnionPayTnOrderId",method={RequestMethod.POST,RequestMethod.GET})
	public Object getUnionPayTnOrderId(HttpServletRequest request,HttpServletResponse response) {
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("balance"));
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		TPayThirdOrgInfo bean = iPayThirdInfoService.getUnionPayTnOrderId(cid, NumberUtils.toDouble(balance), pt);
		if(bean != null){
			return bean;
		} else {
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_FAIL));
		}
	}

	/**
	 * getUnionPayTradeStatus(银联在线支付的查询交易状态)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnionPayTradeStatus",method={RequestMethod.POST,RequestMethod.GET})
	public Object getUnionPayTradeStatus(HttpServletRequest request,HttpServletResponse response){
		/*String tn = request.getParameter("tn");
		if(StringUtils.isEmpty(tn)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("tn"));
		}*/
		String oid = request.getParameter("oid");
		if(StringUtils.isEmpty(oid)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("oid"));
		}
		Object obj = iPayThirdInfoService.getUnionPayTradeStatus(oid);
		if(!ObjectUtil.isEmpty(obj)){
			return obj;
		} else {
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_FAIL));
		}
	}

	/**
	 * reportToUnionPayTradeResult(银联在线支付的上报交易结果)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/reportToUnionPayTradeResult",method={RequestMethod.POST,RequestMethod.GET})
	public Object reportToUnionPayTradeResult(HttpServletRequest request,HttpServletResponse response){
		/*String tn = request.getParameter("tn");
		if(StringUtils.isEmpty(tn)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("tn"));
		}*/
		String oid = request.getParameter("oid");
		if(StringUtils.isEmpty(oid)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("oid"));
		}
		try {
			this.iPassPayService.reportToUnionPayTradeResult(oid);
			//iPayThirdInfoService.reportToUnionPayTradeResult(oid);
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_THIRD_SUCCESS));
		} catch (ServiceException e) {
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_THIRD_FAIL));
		}
	}

	/**
	 * depositAccountOffline(线下充值保证金/货款)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/depositAccountOffline",method={RequestMethod.POST})
	public Object depositAccountOffline(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamMatch("type"));
		}
		String cid = this.getCurrentUserCid(request);
		boolean flag = iPassPayService.depositAccountOfflineRequest(cid, pt);
		if(flag){
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_SUCCESS));
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_DEPOSIT_ACCOUNT_OFFLINE_FAIL));
		}
	}

	/**
	 * paymentAccountOffline(线下付款)
	 *
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/paymentAccountOffline",method={RequestMethod.POST})
	public Object paymentAccountOffline(HttpServletRequest request,HttpServletResponse response){
		/*
		 * type表示银行扣款和线下转账，暂时没有存储，后续拓展可以存储起来.
		 * */
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("type"));
		}
		String oid = request.getParameter("OID");
		if(StringUtils.isEmpty(oid)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, getReturnMsgWithParamNull("OID"));
		}
		String cid = this.getCurrentUserCid(request);
		OInfo o = new OInfo();
		o.setOid(oid);
		boolean flag = iPassPayService.payAccountOfflineRequest(cid, PurseType.DEPOSIT, o);
		if(flag){
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PURSEACCOUNT_PAY_ACCOUNT_OFFLINE_SUCCESS));
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, getMessage(HttpAppSystemConstant.PURSEACCOUNT_PAY_ACCOUNT_OFFLINE_FAIL));
		}
	}

}
