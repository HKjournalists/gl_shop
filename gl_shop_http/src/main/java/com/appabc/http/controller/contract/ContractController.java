package com.appabc.http.controller.contract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.base.exception.BaseException;
import com.appabc.common.utils.MessagesUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.enums.ContractInfo.ContractDisPriceType;
import com.appabc.datas.enums.ContractInfo.ContractLifeCycle;
import com.appabc.datas.enums.ContractInfo.ContractOperateType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.tools.utils.ValidateCodeManager;

/**
 * @Description  : 合同相关的接口信息
 * @Copyright    : GL. All Rights Reserved
 * @Company      : 江苏国立网络技术有限公司
 * @author       : 黄建华
 * @version      : 1.0
 * @Create_Date  : 2014年9月1日 下午8:38:27
 */

@Controller
@RequestMapping(value="/contract")
public class ContractController extends BaseController<TOrderInfo> {
	
	@Autowired
	private IContractInfoService iContractInfoService;
	
	@Autowired
	private IContractCancelService iContractCancelService;
	
	@Autowired
	private IContractOperationService iContractOperationService;
	
	@Autowired
	private IContractDisPriceService iContractDisPriceService;
	
	@Autowired
	private IContractArbitrationService iContractArbitrationService;
	
	@Autowired
	private ICompanyEvaluationService iCompanyEvaluationService;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private ValidateCodeManager validateCodeManager;
	/**
	 * getContractListForPage(获取合同信息列表，根据分页信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return QueryResult<TOrderInfo>
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractListWitPage",method={RequestMethod.GET,RequestMethod.POST})
	public Object getContractListForPage(HttpServletRequest request,
			HttpServletResponse response) {
		//status is ContractStatus.DRAFT,ContractStatus.DOING,ContractStatus.FINISHED.
		//buyerid is current login user id;
		QueryContext<ContractInfoBean> qContext = new QueryContext<ContractInfoBean>();
		PageModel page = initilizePageParams(qContext.getPage(), request);
		qContext.setPage(page);
		qContext.setParameters(buildParametersToMap(qContext.getParameters(), request));
		qContext.addParameter("cid", this.getCurrentUserCid(request));
		qContext = iContractInfoService.queryContractInfoListForPagination(qContext);
		return qContext.getQueryResult().getResult();
	}
	 
	/**
	 * getContractListForPage(获取合同信息列表，不分页)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return QueryResult<TOrderInfo>
	 * @exception
	 * @since 1.0.0
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/getContractListNotPage",method={RequestMethod.GET,RequestMethod.POST})
	public Object getContractListNotPage(HttpServletRequest request,HttpServletResponse response){
		//status is ContractStatus.DRAFT,ContractStatus.DOING,ContractStatus.FINISHED.
		//buyerid is current login user id;
		QueryContext<TOrderInfo> qContext = initializeQueryContext(request);
		qContext.getParameters().put("buyerid", this.getCurrentUserCid(request));
		qContext.getPage().setPageIndex(-1);
		qContext = iContractInfoService.queryListForPagination(qContext);
		return qContext.getQueryResult().getResult();
	}
	
	/**
	 * getContractDetailInfo(获取合同详细信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractDetailInfo",method={RequestMethod.GET,RequestMethod.POST})
	public Object getContractDetailInfo(HttpServletRequest request,HttpServletResponse response){
		/* here parameter need get from request */
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		//TOrderInfo bean = iContractInfoService.getOrderDetailInfo(id);
		ContractInfoBean CNBean = iContractInfoService.getContractInfoById(this.getCurrentUserCid(request),id);
		List<TOrderOperations> sellerOperatorList = iContractOperationService.queryForListWithOIDAndOper(id, CNBean.getSellerid());
		CNBean.setSellerStatus(sellerOperatorList != null&&sellerOperatorList.size()>0 ? sellerOperatorList.get(0) : null);
		List<TOrderOperations> buyerOperatorList = iContractOperationService.queryForListWithOIDAndOper(id, CNBean.getBuyerid());
		CNBean.setBuyerStatus(buyerOperatorList != null&&buyerOperatorList.size()>0 ? buyerOperatorList.get(0) : null);
		if (StringUtils.equalsIgnoreCase(CNBean.getLifecycle(),
				ContractLifeCycle.SIMPLE_CHECKING.getValue())
				|| StringUtils.equalsIgnoreCase(CNBean.getLifecycle(),
						ContractLifeCycle.SIMPLE_CHECKED.getValue())){
			//抽样中/抽样通过： 返回第一次议价列表
			List<?> sample_check_list = iContractDisPriceService.getGoodsDisPriceHisList(id, StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.SAMPLE_CHECK.getValue());
			CNBean.setDisPriceList(sample_check_list);
		} else if (StringUtils.equalsIgnoreCase(CNBean.getLifecycle(),
				ContractLifeCycle.FULL_TAKEOVERING.getValue())
				|| StringUtils.equalsIgnoreCase(CNBean.getLifecycle(),
						ContractLifeCycle.FULL_TAKEOVERED.getValue())){
			//全量抽样中/全量抽样通过：返回第一次议价的最后一条，和 第二议价的列表
			List<TContractDisPriceOperation> sample_check_list = iContractDisPriceService.getGoodsDisPriceHisList(id, StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.SAMPLE_CHECK.getValue());
			List<TContractDisPriceOperation> full_takeover_list = iContractDisPriceService.getGoodsDisPriceHisList(id, StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.FULL_TAKEOVER.getValue());
			List<TContractDisPriceOperation> list = new ArrayList<TContractDisPriceOperation>();
			list.add(sample_check_list.get(sample_check_list.size()-1));
			list.addAll(full_takeover_list);
			CNBean.setDisPriceList(list);
		}
		return CNBean;
	}
	
	/**
	 * finalConfirmContract(合同双方确认合同,形成交易中的合同)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/toConfirmContract",method=RequestMethod.POST)
	public Object toConfirmContract(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		UserInfoBean uib = this.getCurrentUser(request);
		String updater = uib.getCid();
		String updaterName = uib.getCname();
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		try {//这里需要去扣除保证金操作
			int i = iContractInfoService.toConfirmContract(id, updater,updaterName);
			return buildSuccessResult(MessagesUtil.getMessage("CONTRACTCONFIRMSUCCESSTIPS"),i);
		} catch (ServiceException e) {
			e.printStackTrace();
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_CONFIRM_CONTRACT_ERROR, e.getMessage());
		}
	}
	
	/**
	 * payContractFundsOffline(买家已经线下付款)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/payContractFundsOffline",method=RequestMethod.POST)
	public Object payContractFundsOffline(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		TOrderInfo bean = this.iContractInfoService.query(id);
		String buyer = getCurrentUserCid(request);
		if(!bean.getBuyerid().equalsIgnoreCase(buyer)){
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_PAY_ERROR, "you are not buyer, you can not to pay the contract,please contact the admin.");
		}
		try {
			//here need to invoke the pay service to pay the offline pay service
			//order operation and change the contract status and life cycle
			iContractInfoService.payContractFundsOffline(id, buyer, getCurrentUser(request).getCname());
			return buildSuccessResult(MessagesUtil.getMessage("PAYCONTRACTFUNDSTIPS"));
		} catch (Exception e) {
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_PAY_ERROR, e.getMessage());
		}
	}
	
	/**
	 * payContractFundsOnline(支付合同款项)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/payContractFundsOnline",method=RequestMethod.POST)
	public Object payContractFundsOnline(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		String validateCode = request.getParameter("validateCode");
		if (StringUtils.isEmpty(validateCode)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"验证码不能为空.");
		}
		UserInfoBean userInfoBean = this.getCurrentUser(request);
		String smsCode = validateCodeManager.getSmsCode(userInfoBean.getPhone());
		if(!StringUtils.equalsIgnoreCase(validateCode, smsCode)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"验证码过期.");
		}
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(password)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"用户密码为空.");
		}
		String username = userInfoBean.getUserName();
		TUser user = iUserService.queryByNameAndPass(username, password);
		if(user==null){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"用户密码不对.");
		}
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		try {
			iContractInfoService.payContractFunds(id, userInfoBean.getCid(), userInfoBean.getCname());
			return buildSuccessResult(MessagesUtil.getMessage("PAYCONTRACTFUNDSTIPS"));
		} catch (BaseException e) {
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_PAY_ERROR, e.getMessage());
		}
	}
	
	/**
	 * unPayFundsContractList(获取未付款的合同列表)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/unPayFundsContractList",method={RequestMethod.GET,RequestMethod.POST})
	public Object unPayFundsContractList(HttpServletRequest request,HttpServletResponse response){
		QueryContext<ContractInfoBean> qContext = new QueryContext<ContractInfoBean>();
		PageModel page = initilizePageParams(qContext.getPage(), request);
		qContext.setPage(page);
		qContext.setParameters(buildParametersToMap(qContext.getParameters(), request));
		qContext.addParameter("lifecycle", ContractLifeCycle.SINGED.getValue());
		qContext.addParameter("cid", this.getCurrentUserCid(request));
		//qContext = iContractInfoService.queryListForPagination(qContext);
		qContext = iContractInfoService.queryContractInfoListForPagination(qContext);
		return qContext.getQueryResult().getResult();
	}
	
	/**
	 * getContractDetailTemplate(获取合同模板信息接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractDetailTemplate",method={RequestMethod.GET,RequestMethod.POST})
	public Object getContractDetailTemplate(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		ContractInfoBean contractInfo = iContractInfoService.getContractInfoById(getCurrentUserCid(request),id);
		@SuppressWarnings("deprecation")
		String rourse = request.getRealPath(MessagesUtil.getMessage("CONTRACTTEMPLATEPATH"));
		try {
			String content = FileUtils.readFileToString(new File(rourse));
			//here need to format the contract info.
			String base64Str = Base64Utils.encodeToString(content.getBytes()); 
			Map<String,Object> retVal = new HashMap<String,Object>();
			retVal.put("template", base64Str);
			retVal.put("bean", contractInfo);
			return retVal;
		} catch (IOException e) {
			log.debug(e.getMessage(), e);
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_DETAIL_TEMPLATE_ISNULL, e.getMessage());
		}
	}
	
	/**
	 * noticeShippingGoods(通知发货信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/noticeShippingGoods",method=RequestMethod.POST)
	public Object noticeShippingGoods(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		String updater = this.getCurrentUserCid(request);
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		iContractInfoService.noticeShippingGoods(id, updater,getCurrentUser(request).getCname());
		return buildSuccessResult(MessagesUtil.getMessage("CONTRACTNOTICESHIPPINGGOODS"));
	}
	
	/**
	 * singleCancelContract(单方取消合同接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/singleCancelContract",method=RequestMethod.POST)
	public Object singleCancelContract(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		String canceler = uib.getCid();
		try {
			iContractCancelService.singleCancelContract(id,canceler,uib.getCname());
			return buildSuccessResult(MessagesUtil.getMessage("CONTRACTCANCELSUCCESSTIPS"));
		} catch (BaseException e) {
			return buildFailResult(HttpApplicationErrorCode.CONTACT_SINGLE_CANCEL_ERROR, e.getMessage());
		}
	}
	
	/**
	 * multiCancelContract(多次取消合同接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/multiCancelContract",method=RequestMethod.POST)
	public Object multiCancelContract(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		String canceler = uib.getCid();
		int i;
		try {
			i = iContractCancelService.multiCancelContract(id, canceler,uib.getCname());
			return buildSuccessResult(MessagesUtil.getMessage("CONTRACTCANCELSUCCESSTIPS"),i);
		} catch (BaseException e) {
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * getCancelContractListForPage(获取取消合同记录列表根据分页信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getCancelContractListForPage",method=RequestMethod.GET)
	public Object getCancelContractListForPage(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		return iContractCancelService.getCancelContractListByOID(contractId);
	}
	
	/**
	 * getCancelContractListForPage(获取取消合同记录列表不分页)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/getCancelContractListNotPage",method=RequestMethod.GET)
	public Object getCancelContractListNotPage(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		return iContractCancelService.getCancelContractListByOID(contractId);
	}
	
	/**
	 * applyOrNotGoodsInfo(是否同意的接口,包括抽样验收和全量验收)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/applyOrPassGoodsInfo",method=RequestMethod.POST)
	public Object applyOrPassGoodsInfo(HttpServletRequest request,HttpServletResponse response){
		//contract id
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		//the result is apply this discuss Price and validate pass 
		String operate = request.getParameter("operate");
		if (StringUtils.isEmpty(operate)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_APPLY_RESULT_ISNULL"));
		}
		if (!StringUtils.equalsIgnoreCase(operate,
				ContractOperateType.APPLY_DISPRICE.getValue())
				&& !StringUtils.equalsIgnoreCase(operate,
						ContractOperateType.VALIDATE_PASS.getValue())) {
			return buildFailResult(
					HttpApplicationErrorCode.PARAMETER_IS_NULL,
					MessagesUtil.getMessage("CONTRACT_INVALIDATION_TYPE"));
		}
		//if the fid is null, so the operator interface is first time.
		String fid = request.getParameter("fid");
		UserInfoBean uib = getCurrentUser(request);
		TOrderOperations too = iContractOperationService.applyOrPassGoodsInfo(contractId, operate, fid, uib.getCid(),uib.getCname());
		return buildSuccessResult(MessagesUtil.getMessage("OPERATESUCCESSTIPS"),too);
	}
	
	/**
	 * validateGoodsDisPrice(验收货物有异议,议价过程)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/validateGoodsDisPrice",method=RequestMethod.POST)
	public Object validateGoodsDisPrice(HttpServletRequest request,HttpServletResponse response){
		//contract id
		String contractId = request.getParameter("OID");
		//operation table id
		String operateId = request.getParameter("LID");
		String disPrice = request.getParameter("disPrice");
		String disNum = request.getParameter("disNum");
		String reason = request.getParameter("reason");
		String punday = request.getParameter("punday");
		String punReason = request.getParameter("punReason");
		String remark = request.getParameter("remark");
		if(StringUtils.isEmpty(contractId)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		if(StringUtils.isEmpty(disPrice) || !NumberUtils.isNumber(disPrice)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_DISPRICE_ISNULL"));
		}
		if(StringUtils.isEmpty(disNum) || !NumberUtils.isNumber(disNum)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_DISNUM_ISNULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		String operator = uib.getCid();
		TOrderDisPrice dPrice = new TOrderDisPrice();
		dPrice.setCanceler(operator);
		dPrice.setCanceltime(new Date());
		dPrice.setLid(operateId);
		dPrice.setReason(reason);
		dPrice.setPunreason(punReason);
		if(StringUtils.isNotEmpty(punday)){
			dPrice.setPunday(RandomUtil.str2int(punday));
		}
		dPrice.setRemark(remark);
		dPrice.setEndamount(RandomUtil.str2float(disPrice));
		dPrice.setEndnum(RandomUtil.str2float(disNum));
		iContractDisPriceService.validateGoodsDisPrice(contractId,uib.getCname(),dPrice);
		return buildSuccessResult(MessagesUtil.getMessage("OPERATESUCCESSTIPS"),dPrice);
	}
	
	/**
	 * getGoodsDisPriceInfo(获取货物议价记录信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getGoodsDisPriceHisList",method={RequestMethod.GET,RequestMethod.POST})
	public Object getGoodsDisPriceHisList(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		String operateId = request.getParameter("LID");
		String disPriceId = request.getParameter("CID");
		if(StringUtils.isEmpty(contractId)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		List<?> sample_check_list = iContractDisPriceService.getGoodsDisPriceHisList(contractId, operateId, disPriceId, ContractDisPriceType.SAMPLE_CHECK.getValue());
		List<?> full_takeover_list = iContractDisPriceService.getGoodsDisPriceHisList(contractId, operateId, disPriceId, ContractDisPriceType.FULL_TAKEOVER.getValue());
		if(CollectionUtils.isEmpty(sample_check_list)&&CollectionUtils.isEmpty(full_takeover_list)){
			return new Object[]{};
		}else{			
			return new Object[]{sample_check_list,full_takeover_list};
		}
	}
	
	/**
	 * confirmUninstallGoods(确认卸货信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmUninstallGoods",method=RequestMethod.POST)
	public Object confirmUninstallGoods(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		try {
			this.iContractOperationService.confirmUninstallGoods(contractId, uib.getCid(),uib.getCname());
			return buildSuccessResult(MessagesUtil.getMessage("CONTRACTCONFIRMUNINSTALLGOODS"));
		} catch (ServiceException e) {
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_CONFIRM_UNINSTALLGOODS_ERROR, e.getMessage());
		}
	}
	
	/**
	 * validateGoodsInfo(确认验收货物信息)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/validateGoodsInfo",method=RequestMethod.POST)
	public Object validateGoodsInfo(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		try {
			iContractInfoService.validateGoodsInfo(contractId, uib.getCid(),uib.getCname());
			return buildSuccessResult(MessagesUtil.getMessage("CONTRACTVALIDATEGOODSINFO"));
		} catch (ServiceException e) {
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_CONFIRM_RECEIVEGOODS_ERROR, e.getMessage());
		}
		
	}
	
	/**
	 * doContractArbitration(合同仲裁操作)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/toContractArbitration",method=RequestMethod.POST)
	public Object toContractArbitration(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		iContractArbitrationService.toContractArbitration(contractId, uib.getCid(),uib.getCname());
		return buildSuccessResult(MessagesUtil.getMessage("OPERATESUCCESSTIPS"));
	}
	
	/**
	 * getContractArbitrationHistroy(获取合同仲裁记录)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractArbitrationHistroy",method=RequestMethod.GET)
	public Object getContractArbitrationHistroy(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		return iContractArbitrationService.getContractArbitrationHistroy(contractId);
	}
	
	/**
	 * getContractChangeHistory(获取合同变更历史 改成 操作记录 什么时候付款)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractChangeHistory",method=RequestMethod.GET)
	public Object getContractChangeHistory(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		return iContractOperationService.getContractChangeHistory(contractId);
	}
	
	/**
	 * toEvaluateContract(评价合同接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/toEvaluateContract",method=RequestMethod.POST)
	public Object toEvaluateContract(HttpServletRequest request,HttpServletResponse response){
		String cid = request.getParameter("CID");
		if (StringUtils.isEmpty(cid)) {
			//cid = this.getCurrentUserCid(request);
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"被评价的企业ID为空");
		}
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		String evaluation = request.getParameter("evaluation");
		String credit = request.getParameter("credit");
		if (StringUtils.isEmpty(credit) || !NumberUtils.isNumber(credit)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_CREDIT_ISNULL"));
		}
		String satisfaction = request.getParameter("satisfaction");
		if (StringUtils.isEmpty(satisfaction) || !NumberUtils.isNumber(satisfaction)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_SATISFACTION_ISNULL"));
		}
		UserInfoBean uib = getCurrentUser(request);
		String creater = uib.getCid();
		TCompanyEvaluation bean = new TCompanyEvaluation();
		bean.setEvaluation(evaluation);
		bean.setOid(contractId);
		bean.setCid(cid);
		bean.setCredit(RandomUtil.str2int(credit));
		bean.setSatisfaction(RandomUtil.str2int(satisfaction));
		bean.setCreater(creater);
		try {
			iCompanyEvaluationService.toEvaluateContract(creater,uib.getCname(),bean);
			return buildSuccessResult(MessagesUtil.getMessage("CONTRACTEVALUATESUCCESSTIPS"));
		} catch (ServiceException e) {
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,e.getMessage());
		}
	}
	
	/**
	 * getEvaluationContractList(评价合同接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getEvaluationContractList",method={RequestMethod.POST,RequestMethod.GET})
	public Object getEvaluationContractList(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("ID");
		if (StringUtils.isEmpty(id)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		String type = request.getParameter("type");
		boolean f = !StringUtils.equalsIgnoreCase("0", type) && !StringUtils.equalsIgnoreCase("1", type);
		if (StringUtils.isEmpty(type) || f) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,"类型无效.");
		}
		//type is 0 is company, 1 is contract
		TCompanyEvaluation entity = new TCompanyEvaluation();
		if(StringUtils.equalsIgnoreCase("0", type)){
			entity.setCid(id);
			return iCompanyEvaluationService.queryForList(entity);
		}else if(StringUtils.equalsIgnoreCase("1", type)){
			entity.setOid(id);
			return iCompanyEvaluationService.queryForList(entity);
		}else{
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,"没有数据.");
		}
	}
	
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/saveContractDetail")
	public Object saveContractDetail(TOrderInfo orderInfo,HttpServletRequest request,HttpServletResponse response){
		Date now = new Date();
		
		orderInfo.setCreatime(now);
		iContractInfoService.add(orderInfo);
		// return new CheckResult("tianjiacheng");
		return orderInfo;
	}
	
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/updateContractDetail")
	public Object updateContractDetail(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		TOrderInfo bean = new TOrderInfo();
		bean.setId(id);
		iContractInfoService.modify(bean);
		return this.buildSuccessResult("update contract success", bean);
	}
	
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/deleteContractDetail")
	public Object deleteContractDetail(HttpServletRequest request,HttpServletResponse response){
		/* here parameter need to get from request */
		String id = request.getParameter("userId");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,MessagesUtil.getMessage("CONTRACT_ID_IS_NULL"));
		}
		iContractInfoService.delete(id);
		return this.buildSuccessResult("delete contract success Id is " + id);
	}
	
}
