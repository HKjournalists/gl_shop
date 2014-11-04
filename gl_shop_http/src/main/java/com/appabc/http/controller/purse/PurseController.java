/**  
 * com.appabc.pay.controller.PurseController.java  
 *   
 * 2014年9月16日 上午10:55:03  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.http.controller.purse;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.base.controller.BaseController;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.pay.bean.OInfo;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.enums.PurseInfo.PurseType;
import com.appabc.pay.exception.ServiceException;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.util.CodeConstant;

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
		boolean flag = iPassPayService.initializePurseAccount(cid, type);
		if(flag){
			return buildSuccessResult("initialize purse account success.");
		}else{
			return buildFailResult(CodeConstant.RETURNERRORCODE, "initialize purse account failure.");
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
		if(StringUtils.isEmpty(type)){
			TPassbookInfo depositAccount = iPassPayService.getPurseAccountInfo(cid, PurseType.DEPOSIT.getValue());
			TPassbookInfo guarantyAccount = iPassPayService.getPurseAccountInfo(cid, PurseType.GUARANTY.getValue());
			result.getResult().add(depositAccount);
			result.getResult().add(guarantyAccount);
		}else{
			TPassbookInfo tpbi = iPassPayService.getPurseAccountInfo(cid, type);
			result.getResult().add(tpbi);
		}
		QueryContext<TOrderInfo> qContext = new QueryContext<TOrderInfo>();
		qContext.getPage().setPageIndex(-1);
		qContext.addParameter("lifecycle", ContractLifeCycle.SINGED.getValue());
		qContext.addParameter("buyerid", this.getCurrentUserCid(request));
		qContext = iContractInfoService.queryListForPagination(qContext);
		result.setTotalSize(qContext.getQueryResult().getResult().size());
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		String cid = this.getCurrentUserCid(request);
		return iPassPayService.getPurseAccountInfo(cid, type);
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
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		String cid = getCurrentUserCid(request);
		return iPassPayService.payRecordList(cid, type);
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "PId paramer is not allow null.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "balance paramer is not allow null.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "bankcard paramer is not allow null.");
		}
		String carduser = request.getParameter("carduser");
		if(StringUtils.isEmpty(carduser)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "carduser paramer is not allow null.");
		}
		String bankaccount = request.getParameter("bankaccount");
		if(StringUtils.isEmpty(bankaccount)){
			bankaccount = carduser;
		}
		String bankname = request.getParameter("bankname");
		if(StringUtils.isEmpty(bankname)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "bankname paramer is not allow null.");
		}
		String banktype = request.getParameter("banktype");
		if(StringUtils.isEmpty(banktype)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "blanktype paramer is not allow null.");
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
			return buildFailResult(CodeConstant.RETURNERRORCODE, e.getMessage());
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
		String cid = getCurrentUserCid(request);
		if(StringUtils.isEmpty(acceptId)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "acceptId paramer is not allow null.");
		}
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "balance paramer is not allow null.");
		}
		if(StringUtils.isEmpty(type)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		TPassbookDraw tpd = iPassPayService.extractCashRequest(cid, type, NumberUtils.toFloat(balance), acceptId);
		if(tpd == null){
			return buildFailResult(CodeConstant.RETURNERRORCODE, "extract cash request failure.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "tid paramer is not allow null.");
		}
		String result = request.getParameter("result");
		if(StringUtils.isEmpty(result)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "result paramer is not allow null.");
		}
		String reson = request.getParameter("reson");
		String cid = getCurrentUserCid(request);
		boolean flag = iPassPayService.extractCashAudit(tid, BooleanUtils.toBoolean(result), reson, cid);
		if(flag){
			return buildSuccessResult("validate the extract cash success.");
		}else{
			return buildFailResult(CodeConstant.RETURNERRORCODE, "validate the extract cash failure.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		return iPassPayService.payRecordList(cid, type);
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "balance paramer is not allow null.");
		}
		String payNo = request.getParameter("payNo");
		if(StringUtils.isEmpty(payNo)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "payNo paramer is not allow null.");
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		boolean flag = iPassPayService.depositAccountOnline(cid, type, NumberUtils.toFloat(balance), payNo);
		if(flag){
			return buildSuccessResult("deposit account online success.");
		}else{
			return buildFailResult(CodeConstant.RETURNERRORCODE, "deposit account online failure.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		String balance = request.getParameter("balance");
		if(StringUtils.isEmpty(balance)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "balance paramer is not allow null.");
		}
		String payNo = request.getParameter("payNo");
		if(StringUtils.isEmpty(payNo)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "payNo paramer is not allow null.");
		}
		String cid = request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			cid = getCurrentUserCid(request);
		}
		boolean flag = iPassPayService.depositThirdOrgRecord(cid, type, NumberUtils.toFloat(balance), payNo);
		if(flag){
			return buildSuccessResult("deposit account third part success.");
		}else{
			return buildFailResult(CodeConstant.RETURNERRORCODE, "deposit account third part failure.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		String cid = this.getCurrentUserCid(request);
		boolean flag = iPassPayService.depositAccountOffline(cid, type);
		if(flag){
			return buildSuccessResult("deposit account offline success.");
		}else{
			return buildFailResult(CodeConstant.RETURNERRORCODE, "deposit account offline failure.");
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
			return buildFailResult(CodeConstant.PARAMETERISNULL, "type paramer is not allow null.");
		}
		String oid = request.getParameter("OID");
		if(StringUtils.isEmpty(oid)){
			return buildFailResult(CodeConstant.PARAMETERISNULL, "OID paramer is not allow null.");
		}
		String cid = this.getCurrentUserCid(request);
		OInfo o = new OInfo();
		o.setOid(oid);
		boolean flag = iPassPayService.payAccountOffline(cid, PurseType.DEPOSIT.getValue(), o);
		if(flag){
			return buildSuccessResult("pay account offline success.");
		}else{
			return buildFailResult(CodeConstant.RETURNERRORCODE, "pay account offline failure.");
		}
	}
	
}
