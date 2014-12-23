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
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.util.CodeConstant;
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
	private ICompanyInfoService iCompanyInfoService;
	
	@Autowired
	private ValidateCodeManager validateCodeManager;
	
	@Autowired
	private IUserService iUserService;
	
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
	public Object getPurseList(HttpServletRequest request,HttpServletResponse response){
		TPassbookInfo tpi = new TPassbookInfo();
		tpi.setAmount(1.0f);
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
			return buildSuccessResult("initialize purse account success.");
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "initialize purse account failure.");
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
		float shouldGuarantNum = iCompanyInfoService.getShouldDepositAmountByCid(cid);
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not match. the value is 0 or 1.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not match. the value is 0 or 1.");
		}
		if(StringUtils.isNotEmpty(direction)){			
			PayDirection pd = PayDirection.enumOf(RandomUtil.str2int(direction));
			if(pd == null){
				return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "direction paramer is not match. the value is 0 or 1.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "PId paramer is not allow null.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "balance paramer is not allow null.");
		}
		String cid = getCurrentUserCid(request);
		return iPassPayService.depositToGuaranty(cid, NumberUtils.toFloat(balance));
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "bankcard paramer is not allow null.");
		}
		String carduser = request.getParameter("carduser");
		if(StringUtils.isEmpty(carduser)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "carduser paramer is not allow null.");
		}
		String bankaccount = request.getParameter("bankaccount");
		if(StringUtils.isEmpty(bankaccount)){
			bankaccount = carduser;
		}
		String bankname = request.getParameter("bankname");
		if(StringUtils.isEmpty(bankname)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "bankname paramer is not allow null.");
		}
		String banktype = request.getParameter("banktype");
		if(StringUtils.isEmpty(banktype)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "blanktype paramer is not allow null.");
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"验证码不能为空.");
		}
		
		String smsCode = validateCodeManager.getSmsCode(userInfoBean.getPhone());
		if(!StringUtils.equalsIgnoreCase(validateCode, smsCode)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"验证码过期.");
		}
		if (StringUtils.isEmpty(password)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"用户密码为空.");
		}
		TUser user = iUserService.queryByNameAndPass(userInfoBean.getUserName(), password);
		if(user==null){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"用户密码不对.");
		}
		
		if(StringUtils.isEmpty(acceptId)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "acceptId paramer is not allow null.");
		}
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "balance paramer is not allow null.");
		}
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type is not match. and the value will is 0 or 1.");
		}
		TPassbookDraw tpd = iPassPayService.extractCashRequest(userInfoBean.getCid(), pt, NumberUtils.toFloat(balance), acceptId);
		//业务操作完成,删除前面的的手机验证.
		validateCodeManager.delSmsCode(userInfoBean.getPhone());
		if(tpd == null){
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "extract cash request failure.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "tid paramer is not allow null.");
		}
		String result = request.getParameter("result");
		if(StringUtils.isEmpty(result)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "result paramer is not allow null.");
		}
		String reson = request.getParameter("reson");
		String cid = getCurrentUserCid(request);
		boolean flag = iPassPayService.extractCashAudit(tid, BooleanUtils.toBoolean(result), reson, cid);
		if(flag){
			return buildSuccessResult("validate the extract cash success.");
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "validate the extract cash failure.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type is not match. and the value will is 0 or 1.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "balance paramer is not allow null.");
		}
		String payNo = request.getParameter("payNo");
		if(StringUtils.isEmpty(payNo)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "payNo paramer is not allow null.");
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type is not match. and the value will is 0 or 1.");
		}
		boolean flag = iPassPayService.depositAccountOnline(cid, pt, NumberUtils.toFloat(balance), payNo);
		if(flag){
			return buildSuccessResult("deposit account online success.");
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "deposit account online failure.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "balance paramer is not allow null.");
		}
		String payNo = request.getParameter("payNo");
		if(StringUtils.isEmpty(payNo)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "payNo paramer is not allow null.");
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type is not match. and the value will is 0 or 1.");
		}
		boolean flag = iPassPayService.depositThirdOrgRecord(cid, pt, NumberUtils.toFloat(balance), payNo);
		if(flag){
			return buildSuccessResult("deposit account third part success.");
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "deposit account third part failure.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		PurseType pt = PurseType.enumOf(type);
		if(pt == null){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type is not match. and the value will is 0 or 1.");
		}
		String cid = this.getCurrentUserCid(request);
		boolean flag = iPassPayService.depositAccountOfflineRequest(cid, pt);
		if(flag){
			return buildSuccessResult("deposit account offline success.");
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "deposit account offline failure.");
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
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "type paramer is not allow null.");
		}
		String oid = request.getParameter("OID");
		if(StringUtils.isEmpty(oid)){
			return buildFailResult(CodeConstant.PARAMETER_IS_NULL, "OID paramer is not allow null.");
		}
		String cid = this.getCurrentUserCid(request);
		OInfo o = new OInfo();
		o.setOid(oid);
		boolean flag = iPassPayService.payAccountOfflineRequest(cid, PurseType.DEPOSIT, o);
		if(flag){
			return buildSuccessResult("pay account offline success.");
		}else{
			return buildFailResult(CodeConstant.RETURN_ERROR_CODE, "pay account offline failure.");
		}
	}
	
}
