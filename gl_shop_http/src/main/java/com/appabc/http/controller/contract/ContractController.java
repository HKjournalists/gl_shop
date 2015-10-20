package com.appabc.http.controller.contract;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.appabc.bean.bo.ContractInfoBean;
import com.appabc.bean.bo.MoreAreaInfos;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.pvo.TCompanyEvaluation;
import com.appabc.bean.pvo.TContractDisPriceOperation;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.bean.UserInfoBean;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.utils.ObjectUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractCancelService;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.tool.ContractCostDetailUtil;
import com.appabc.datas.tool.DataSystemConstant;
import com.appabc.datas.tool.ServiceErrorCode;
import com.appabc.http.utils.EveryUtil;
import com.appabc.http.utils.HttpApplicationErrorCode;
import com.appabc.http.utils.HttpApplicationErrorCode.HttpAppSystemConstant;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.service.IPassPayService;
import com.appabc.tools.service.codes.IPublicCodesService;
import com.appabc.tools.utils.SystemParamsManager;
import com.appabc.tools.utils.ValidateCodeManager;

import freemarker.template.Template;

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
	private IOrderFindService iOrderFindService;
	
	@Autowired
	private IPublicCodesService iPublicCodesService;
	
	@Autowired
	private IPassPayService iPassPayService;
	
	@Autowired
	private ValidateCodeManager validateCodeManager;
	
	@Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private SystemParamsManager systemParamsManager;
	
	private String getContractTemplate(Map<?,?> model) throws Exception{
		try {
			//here need to format the contract info.
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate("template/ContractTemplate.html");
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			String base64Str = Base64Utils.encodeToString(content.getBytes("UTF-8")); 
			return base64Str;
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			throw e;
		}
	}
	
	private ContractInfoBean getContractInfoTemplate(String cid,String id){
		ContractInfoBean contractInfo = iContractInfoService.getContractInfoById(cid,id);
		if(StringUtils.isEmpty(contractInfo.getBuyerName())){
			TUser buyer = iUserService.getUserByCid(contractInfo.getBuyerid());
			if(buyer != null){				
				contractInfo.setBuyerName(buyer.getPhone());
			}
		}
		if(StringUtils.isEmpty(contractInfo.getSellerName())){
			TUser seller = iUserService.getUserByCid(contractInfo.getSellerid());
			if(seller != null){				
				contractInfo.setSellerName(seller.getPhone());
			}
		}
		List<TOrderOperations> sellerOperatorList = iContractOperationService.queryForListWithOIDAndOper(id, contractInfo.getSellerid());
		contractInfo.setSellerStatus(CollectionUtils.isEmpty(sellerOperatorList) ? null : sellerOperatorList.get(0));
		List<TOrderOperations> buyerOperatorList = iContractOperationService.queryForListWithOIDAndOper(id, contractInfo.getBuyerid());
		contractInfo.setBuyerStatus(CollectionUtils.isEmpty(buyerOperatorList) ? null : buyerOperatorList.get(0));
		ContractLifeCycle clc = contractInfo.getLifecycle();
		boolean isFinish = clc == ContractLifeCycle.FINALESTIMATE_FINISHED || clc == ContractLifeCycle.NORMAL_FINISHED
				|| clc == ContractLifeCycle.ARBITRATED || clc == ContractLifeCycle.BUYER_UNPAY_FINISHED || clc == ContractLifeCycle.SINGLECANCEL_FINISHED;
		if (clc == ContractLifeCycle.SIMPLE_CHECKING || clc == ContractLifeCycle.SIMPLE_CHECKED){
			//抽样中/抽样通过： 返回第一次议价列表
			List<TContractDisPriceOperation> result = iContractDisPriceService.getGoodsDisPriceHistroyListWithSampleCheck(id);
			contractInfo.setSampleCheckDisPriceList(result);
		} else if (clc == ContractLifeCycle.FULL_TAKEOVERING || clc == ContractLifeCycle.FULL_TAKEOVERED){
			//全量抽样中/全量抽样通过：返回第一次议价的最后一条，和 第二议价的列表
			List<TContractDisPriceOperation> result = iContractDisPriceService.getGoodsDisPriceHistroyListWithFullTakeover(id);
			contractInfo.setFullTakeoverDisPriceList(result);
		} 
		if (clc == ContractLifeCycle.ARBITRATED || clc == ContractLifeCycle.ARBITRATING){
			//查询当前合同下所有的仲裁议价信息
			List<TContractDisPriceOperation> arbitrationResult = iContractDisPriceService.getGoodsDisPriceHisList(id, StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.ARBITRATION_DISPRICE.getVal());
			contractInfo.setArbitrationDisPriceList(arbitrationResult);
		} 
		if (clc == ContractLifeCycle.CONFIRMING_GOODS_FUNDS || clc == ContractLifeCycle.FINALESTIMATEING || isFinish){
			//查询当前合同下所有的货物和货款议价信息
			List<TContractDisPriceOperation> fundGoodsResult = iContractDisPriceService.getGoodsDisPriceHisList(id, StringUtils.EMPTY, StringUtils.EMPTY, ContractDisPriceType.FUNDGOODS_DISPRICE.getVal());
			contractInfo.setFundGoodsDisPriceList(fundGoodsResult);
		} 
		if (isFinish){
			//查询当前合同下所有的账单信息
			List<TPassbookPay> result = iPassPayService.getPayRecordListWithOid(cid, null, id, null);
			contractInfo.setFinalEstimateList(result);
			//设置用户的支付金额
			double payFundsAmount = iPassPayService.getContractPayFundsAmount(contractInfo.getBuyerid(), contractInfo.getId());
			contractInfo.setPayFundsAmount(payFundsAmount);
		} 
		if (clc == ContractLifeCycle.ARBITRATED){
			List<?> result = iContractArbitrationService.getContractArbitrationHistroy(contractInfo.getId());
			contractInfo.setArbitrationProcessInfo(CollectionUtils.isEmpty(result) ? null : result.get(result.size()-1));
		}
		
		return contractInfo;
	}
	
	private OrderAllInfor getOrderFindTemplate(String fid){
		if(StringUtils.isNotEmpty(fid)){
			OrderAllInfor oaif = iOrderFindService.queryInfoById(fid, null);
			TOrderProductProperty tpp = oaif.getPsize();
			if(ObjectUtil.isNotEmpty(tpp.getMaxv()) && ObjectUtil.isNotEmpty(tpp.getMinv())){				
				oaif.getPsize().setContent(tpp.getMinv()+" ~ "+tpp.getMaxv());
			}else if(ObjectUtil.isNotEmpty(tpp.getMinv()) && ObjectUtil.isEmpty(tpp.getMaxv())){
				oaif.getPsize().setContent(" ≥ "+tpp.getMinv());
			}else{
				oaif.getPsize().setContent(StringUtils.EMPTY);
			}
			List<MoreAreaInfos> moreAreaInfos = oaif.getMoreAreaInfos();
			if(CollectionUtils.isEmpty(moreAreaInfos)){
				//区域
				if(oaif != null&&StringUtils.isNotEmpty(oaif.getArea())){
					TPublicCodes entity = new TPublicCodes();
					entity.setCode("RIVER_SECTION");
					entity.setVal(oaif.getArea());
					entity = iPublicCodesService.query(entity);
					if(entity != null && StringUtils.isNotEmpty(entity.getName())){
						oaif.setArea(entity.getName());
					}
				}
				//种类
				if(oaif != null&&StringUtils.isNotEmpty(oaif.getPtype())){
					TPublicCodes entity = new TPublicCodes();
					entity.setCode("GOODS");
					entity.setVal(oaif.getPtype());
					entity = iPublicCodesService.query(entity);
					if(entity != null && StringUtils.isNotEmpty(entity.getName())){
						oaif.setPtype(entity.getName());
					}
				}else{
					oaif.setPtype(null);
				}
			}
			return oaif;
		}
		return null;
	}
	
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
		qContext.setParameters(buildParametersToMap(request));
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		//TOrderInfo bean = iContractInfoService.getOrderDetailInfo(id);
		ContractInfoBean CNBean = iContractInfoService.getContractInfoById(this.getCurrentUserCid(request),id);
		List<TOrderOperations> sellerOperatorList = iContractOperationService.queryForListWithOIDAndOper(id, CNBean.getSellerid());
		CNBean.setSellerStatus(CollectionUtils.isEmpty(sellerOperatorList) ? null : sellerOperatorList.get(0));
		List<TOrderOperations> buyerOperatorList = iContractOperationService.queryForListWithOIDAndOper(id, CNBean.getBuyerid());
		CNBean.setBuyerStatus(CollectionUtils.isEmpty(buyerOperatorList) ? null :  buyerOperatorList.get(0));
		if (CNBean.getLifecycle() == ContractLifeCycle.SIMPLE_CHECKING || CNBean.getLifecycle() == ContractLifeCycle.SIMPLE_CHECKED){
			//抽样中/抽样通过： 返回第一次议价列表
			List<TContractDisPriceOperation> result = iContractDisPriceService.getGoodsDisPriceHistroyListWithSampleCheck(id);
			CNBean.setSampleCheckDisPriceList(result);
		} else if (CNBean.getLifecycle() == ContractLifeCycle.FULL_TAKEOVERING || CNBean.getLifecycle() == ContractLifeCycle.FULL_TAKEOVERED){
			//全量抽样中/全量抽样通过：返回第一次议价的最后一条，和 第二议价的列表
			List<TContractDisPriceOperation> result = iContractDisPriceService.getGoodsDisPriceHistroyListWithFullTakeover(id);
			CNBean.setFullTakeoverDisPriceList(result);
		}
		return CNBean;
	}
	
	/**
	 * getContractDetailInfoEx(获取合同详细信息:包括结算清单)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContractDetailInfoEx",method={RequestMethod.GET,RequestMethod.POST})
	public Object getContractDetailInfoEx(HttpServletRequest request,HttpServletResponse response){
		/* here parameter need get from request */
		String id = request.getParameter("OID");
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String isNeedContractTemplate = request.getParameter("getTemplate");
		ContractInfoBean CNBean = getContractInfoTemplate(this.getCurrentUserCid(request),id);
		if (StringUtils.isNotEmpty(isNeedContractTemplate)) {
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("contract", CNBean);
			String num = EveryUtil.formatDecimalNumber(systemParamsManager.getFloat(SystemConstant.SERVICE_PERCENT));
			model.put("ContractDraftConfirmLimitNum", ContractCostDetailUtil.getContractDraftConfirmLimitNum());
			model.put("ContractPayGoodsLimitNum", ContractCostDetailUtil.getContractPayGoodsLimitNum());
			model.put("service_percent", num);
			OrderAllInfor oaif = getOrderFindTemplate(CNBean.getFid());
			model.put("orderFind", oaif);
			boolean toDisplayFinalAmount = CNBean.getStatus() == ContractStatus.FINISHED && 
					(CNBean.getLifecycle() == ContractLifeCycle.ARBITRATED || 
					CNBean.getLifecycle() == ContractLifeCycle.NORMAL_FINISHED || 
					CNBean.getLifecycle() == ContractLifeCycle.FINALESTIMATE_FINISHED);
			List<?> arbitrationResult = CNBean.getArbitrationDisPriceList();
			if(!CollectionUtils.isEmpty(arbitrationResult)){
				boolean b = false;
				for(int i = 0; i < arbitrationResult.size(); i ++){
					TContractDisPriceOperation t = (TContractDisPriceOperation)arbitrationResult.get(i);
					b =  t.getEndamount() * t.getEndnum() == 0;
					if(b){
						break;
					}
				}
				toDisplayFinalAmount = toDisplayFinalAmount && !b;
			}
			model.put("toDisplayFinalAmount", toDisplayFinalAmount);
			
			try {
				//here need to format the contract info.
				String base64Str = getContractTemplate(model);
				CNBean.setContractTemplate(base64Str);
			}catch(Exception e){
				log.debug(e.getMessage(), e);
			}
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
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = this.getCurrentUser(request);
		String updater = uib.getCid();
		String updaterName = uib.getCname();
		try {//这里需要去扣除保证金操作
			//int i = iContractInfoService.toConfirmContract(id, updater,updaterName);
			List<TOrderOperations> result = iContractInfoService.toConfirmContractRetOperator(id, updater, StringUtils.isEmpty(updaterName) ? uib.getPhone() : updaterName);
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_CONFIRM_SUCCESS_TIPS),result);
		} catch (ServiceException e) {
			e.printStackTrace();
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_CONFIRM_CONTRACT_ERROR);
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		TOrderInfo bean = this.iContractInfoService.query(id);
		String buyer = getCurrentUserCid(request);
		if(!bean.getBuyerid().equalsIgnoreCase(buyer)){
			return buildFailResult(HttpApplicationErrorCode.CONTRACT_PAY_ERROR, getMessage(HttpAppSystemConstant.NOT_BUYER_NOTO_PAY_CONTRACT));
		}
		try {
			//here need to invoke the pay service to pay the offline pay service
			//order operation and change the contract status and life cycle
			iContractInfoService.payContractFundsOffline(id, buyer, getCurrentUser(request).getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PAY_CONTRACT_FUNDS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_PAY_ERROR);
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
		if (StringUtils.isEmpty(id)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String validateCode = request.getParameter("validateCode");
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
		try {
			iContractInfoService.payContractFunds(id, userInfoBean.getCid(), userInfoBean.getCname());
			//业务操作完成,删除前面的的手机验证.
			validateCodeManager.delSmsCode(userInfoBean.getPhone());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.PAY_CONTRACT_FUNDS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_PAY_ERROR);
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
		qContext.setParameters(buildParametersToMap(request));
		qContext.addParameter("lifecycle", ContractLifeCycle.SINGED.getVal());
		qContext.addParameter("cid", this.getCurrentUserCid(request));
		qContext.addParameter("status", ContractStatus.DOING.getVal());
		qContext.addParameter("isUnPayContractList", true);
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		Properties prop = System.getProperties();
		Enumeration<Object> enums =  prop.keys();
		while(enums.hasMoreElements()){
			String key = enums.nextElement().toString();
			log.debug(key+" ==K==V== "+System.getProperty(key));
		}
		String cid = this.getCurrentUserCid(request);
		ContractInfoBean contractInfo = getContractInfoTemplate(cid,id);
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("contract", contractInfo);
		String num = EveryUtil.formatDecimalNumber(systemParamsManager.getFloat(SystemConstant.SERVICE_PERCENT));
		model.put("service_percent", num);
		double wd = ContractCostDetailUtil.getContractDraftConfirmLimitNumWD();
		model.put("ContractDraftConfirmLimitNum", RandomUtil.mulRound(wd,60));
		model.put("ContractPayGoodsLimitNum", ContractCostDetailUtil.getContractPayGoodsLimitNum());
		OrderAllInfor oaif = getOrderFindTemplate(contractInfo.getFid());
		model.put("orderFind", oaif);
		boolean toDisplayFinalAmount = contractInfo.getStatus() == ContractStatus.FINISHED && 
				(contractInfo.getLifecycle() == ContractLifeCycle.ARBITRATED || 
						contractInfo.getLifecycle() == ContractLifeCycle.NORMAL_FINISHED || 
								contractInfo.getLifecycle() == ContractLifeCycle.FINALESTIMATE_FINISHED);
		List<?> arbitrationResult = contractInfo.getArbitrationDisPriceList();
		if(!CollectionUtils.isEmpty(arbitrationResult)){
			boolean b = false;
			for(int i = 0; i < arbitrationResult.size(); i ++){
				TContractDisPriceOperation t = (TContractDisPriceOperation)arbitrationResult.get(i);
				b =  t.getEndamount() * t.getEndnum() == 0;
				if(b){
					break;
				}
			}
			toDisplayFinalAmount = toDisplayFinalAmount && !b;
		}
		model.put("toDisplayFinalAmount", toDisplayFinalAmount);
		try {
			//here need to format the contract info.
			String base64Str = getContractTemplate(model);
			Map<String,Object> retVal = new HashMap<String,Object>();
			retVal.put("bean", contractInfo);
			retVal.put("template", base64Str);
			return retVal;
		} catch (Exception e) {
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		iContractInfoService.noticeShippingGoods(id, updater,getCurrentUser(request).getCname());
		return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_NOTICESHIPPING_GOODS));
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = getCurrentUser(request);
		String canceler = uib.getCid();
		TOrderInfo bean = this.iContractInfoService.query(id);
		//如果合同为空,就不能取消操作了.
		if(bean == null){
			return buildFailResult(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		//如果,操作人不是买家或者不是卖家,也不能操作合同.
		if(!StringUtils.equalsIgnoreCase(canceler, bean.getBuyerid()) && !StringUtils.equalsIgnoreCase(canceler, bean.getSellerid())){
			return buildFailResult(ServiceErrorCode.CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR));
		}
		try {
			iContractCancelService.singleCancelContract(id,canceler,uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_CANCEL_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_SINGLE_CANCEL_ERROR);
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = getCurrentUser(request);
		String canceler = uib.getCid();
		int i;
		try {
			i = iContractCancelService.multiCancelContract(id, canceler,uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_CANCEL_SUCCESS_TIPS),i);
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.RESULT_ERROR_CODE);
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
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
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		//the result is apply this discuss Price and validate pass 
		String operate = request.getParameter("operate");
		if (StringUtils.isEmpty(operate)) {
			return this.buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_APPLY_RESULT_ISNULL));
		}
		if (!StringUtils.equalsIgnoreCase(operate,
				ContractOperateType.APPLY_DISPRICE.getVal())
				&& !StringUtils.equalsIgnoreCase(operate,
						ContractOperateType.VALIDATE_PASS.getVal())) {
			return buildFailResult(
					HttpApplicationErrorCode.PARAMETER_IS_NULL,
					getMessage(HttpAppSystemConstant.CONTRACT_INVALIDATION_TYPE));
		}
		//if the fid is null, so the operator interface is first time.
		String fid = request.getParameter("fid");
		UserInfoBean uib = getCurrentUser(request);
		try {
			TOrderOperations too = iContractOperationService.applyOrPassGoodsInfo(contractId, operate, fid, uib.getCid(),uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS),too);
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_PAY_ERROR);
		}
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
		if(StringUtils.isEmpty(contractId)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		TOrderInfo contract = this.iContractInfoService.query(contractId);
		if(contract == null){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		//first dis price no need to the dis num.
		boolean isFirstDisPrice = true;
		if (ContractLifeCycle.PAYED_FUNDS == contract.getLifecycle()
				|| ContractLifeCycle.SENT_GOODS == contract.getLifecycle()
				|| ContractLifeCycle.SIMPLE_CHECKING == contract.getLifecycle()) {
			isFirstDisPrice = false;
		}
		//operation table id
		String operateId = request.getParameter("LID");
		String disPrice = request.getParameter("disPrice");
		String disNum = request.getParameter("disNum");
		String reason = request.getParameter("reason");
		String punday = request.getParameter("punday");
		String punReason = request.getParameter("punReason");
		String remark = request.getParameter("remark");
		if(StringUtils.isEmpty(contractId)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		if(StringUtils.isEmpty(disPrice) || !NumberUtils.isNumber(disPrice)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_DISPRICE_ISNULL));
		}
		//first dis price no need to the dis num.
		if(isFirstDisPrice && (StringUtils.isEmpty(disNum) || !NumberUtils.isNumber(disNum))){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_DISNUM_ISNULL));
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
		dPrice.setEndamount(RandomUtil.str2double(disPrice));
		dPrice.setEndnum(RandomUtil.str2double(disNum));
		try {
			iContractDisPriceService.validateGoodsDisPrice(contractId,uib.getCname(),dPrice);
			return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS),dPrice);
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_VALIDATE_GOODS_DIS_PRICE);
		}
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		List<?> sample_check_list = iContractDisPriceService.getGoodsDisPriceHisList(contractId, operateId, disPriceId, ContractDisPriceType.SAMPLE_CHECK.getVal());
		List<?> full_takeover_list = iContractDisPriceService.getGoodsDisPriceHisList(contractId, operateId, disPriceId, ContractDisPriceType.FULL_TAKEOVER.getVal());
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = getCurrentUser(request);
		try {
			this.iContractOperationService.confirmUninstallGoods(contractId, uib.getCid(),uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_CONFIR_MUN_INSTALLGOODS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_CONFIRM_UNINSTALLGOODS_ERROR);
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = getCurrentUser(request);
		try {
			iContractInfoService.validateGoodsInfo(contractId, uib.getCid(),uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_VALIDATE_GOODSINFO));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_CONFIRM_RECEIVEGOODS_ERROR);
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = getCurrentUser(request);
		try {
			iContractArbitrationService.toConsultingService(contractId, uib.getCid(),uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_CONSULTING_SERVICE_ERROR);
		}
	}
	
	/** contractArbitractionProcess(合同仲裁处理操作)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/contractArbitractionProcess",method=RequestMethod.POST)
	public Object contractArbitractionProcess(HttpServletRequest request,HttpServletResponse response){
		String aid = request.getParameter("aid");
		if (StringUtils.isEmpty(aid)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String isTrade = request.getParameter("isTrade");
		if (StringUtils.isEmpty(isTrade)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String disPrice = request.getParameter("disPrice");
		String disNum = request.getParameter("disNum");
		if(StringUtils.isNotEmpty(disPrice) && !NumberUtils.isNumber(disPrice)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_DISPRICE_ISNULL));
		}
		//first dis price no need to the dis num.
		if(StringUtils.isNotEmpty(disNum) && !NumberUtils.isNumber(disNum)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_DISNUM_ISNULL));
		}
		String result = request.getParameter("dealresult");
		UserInfoBean uib = getCurrentUser(request);
		try {
			iContractArbitrationService.contractArbitractionProcess(BooleanUtils.toBoolean(isTrade), aid, uib.getCid(), uib.getCname(), NumberUtils.toFloat(disNum), NumberUtils.toFloat(disPrice), result);
			//iContractArbitrationService.contractArbitractionProcess(BooleanUtils.toBoolean(isTrade), aid, uib.getCid(), uib.getCname(), NumberUtils.toFloat(disNum), NumberUtils.toFloat(disPrice));
			return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_CONSULTING_SERVICE_ERROR);
		}
	}
	
	/**
	 * toConsultingService(咨询客服操作操作)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/toConsultingService",method=RequestMethod.POST)
	public Object toConsultingService(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		UserInfoBean uib = getCurrentUser(request);
		try {
			iContractArbitrationService.toConsultingService(contractId, uib.getCid(),uib.getCname());
			return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.CONTRACT_CONSULTING_SERVICE_ERROR);
		}
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
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
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.BEEVALUATION_CID_IS_NULL));
		}
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String evaluation = request.getParameter("evaluation");
		String credit = request.getParameter("credit");
		if (StringUtils.isEmpty(credit) || !NumberUtils.isNumber(credit)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_CREDIT_ISNULL));
		}
		String satisfaction = request.getParameter("satisfaction");
		if (StringUtils.isEmpty(satisfaction) || !NumberUtils.isNumber(satisfaction)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_SATISFACTION_ISNULL));
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
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_EVALUATE_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.RESULT_ERROR_CODE);
		}
	}
	
	/**
	 * cancelDraftContract(取消起草合同接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value="/cancelDraftContract",method=RequestMethod.POST)
	public Object cancelDraftContract(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String operateType = request.getParameter("operateType");
		if(StringUtils.isEmpty(operateType)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_APPLY_TYPE_ISNULL));
		}
		ContractOperateType cot = ContractOperateType.enumOf(operateType);
		if(cot != ContractOperateType.CANCEL_CONTRACT && cot != ContractOperateType.MOVE_TO_FINISHED_CONTRACT && cot != ContractOperateType.DELETE_CONTRACT){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_INVALIDATION_TYPE));
		}
		UserInfoBean user = getCurrentUser(request);
		TOrderInfo bean = this.iContractInfoService.query(contractId);
		//如果合同为空,就不能取消操作了.
		if(bean == null){
			return buildFailResult(ServiceErrorCode.CONTRACT_IDNOTALLOW_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_IDNOTALLOW_ERROR));
		}
		//如果,操作人不是买家或者不是卖家,也不能操作合同.
		if(!StringUtils.equalsIgnoreCase(user.getCid(), bean.getBuyerid()) && !StringUtils.equalsIgnoreCase(user.getCid(), bean.getSellerid())){
			return buildFailResult(ServiceErrorCode.CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR,getMessage(DataSystemConstant.EXCEPTIONKEY_CONTRACT_NOT_BUYER_SELLER_TOOPERATE_ERROR));
		}
		try {
			iContractCancelService.cancelDraftContract(contractId, user.getCid(), StringUtils.isEmpty(user.getCname()) ? user.getPhone() : user.getCname(), cot);
			return buildSuccessResult(getMessage(HttpAppSystemConstant.CONTRACT_CANCEL_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.RESULT_ERROR_CODE);
		}
	}
	
	/**
	 * applyOrAgreeOrArbitrateFinalEstimate(申请合同确认货款和货物,同意申请合同确认货款和货物,申请仲裁接口)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value="/applyOrAgreeOrArbitrateFinalEstimate",method=RequestMethod.POST)
	public Object applyOrAgreeOrArbitrateFinalEstimate(HttpServletRequest request,HttpServletResponse response){
		String contractId = request.getParameter("OID");
		if (StringUtils.isEmpty(contractId)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String operateType = request.getParameter("operateType");
		if(StringUtils.isEmpty(operateType)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_APPLY_TYPE_ISNULL));
		}
		ContractOperateType cot = ContractOperateType.enumOf(operateType);
		if(cot != ContractOperateType.AGREE_FUND_GOODS_CONFIRM && cot != ContractOperateType.FUNDS_GOODS_CONFIRM && cot != ContractOperateType.APPLY_ARBITRATION){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL, getMessage(HttpAppSystemConstant.CONTRACT_INVALIDATION_TYPE));
		}
		//if the fid is null, so the operator interface is first time.
		//String fid = request.getParameter("fid");
		String disPrice = request.getParameter("disPrice");
		String disNum = request.getParameter("disNum");
		//final estimate amount
		String finalAmount = request.getParameter("finalAmount");
		if(StringUtils.isNotEmpty(finalAmount) && !NumberUtils.isNumber(finalAmount)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_FINALESTIMATE_AMOUNT_ISNULL));
		}
		if(StringUtils.isNotEmpty(disPrice) && !NumberUtils.isNumber(disPrice)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_DISPRICE_ISNULL));
		}
		if(StringUtils.isNotEmpty(disPrice) && NumberUtils.toDouble(disPrice) > SystemConstant.MAX_UNIT_PRICE){
			return buildFailResult(ServiceErrorCode.EXCEEDS_MAX_UNIT_PRICE,getMessage(HttpAppSystemConstant.CONTRACT_DISPRICE_ISNULL));
		}
		//first dis price no need to the dis num.
		if(StringUtils.isNotEmpty(disNum) && !NumberUtils.isNumber(disNum)){
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_DISNUM_ISNULL));
		}
		
		if(StringUtils.isNotEmpty(disNum) && NumberUtils.toDouble(disNum) > SystemConstant.MAX_TOTAL_AMOUNT){
			return buildFailResult(ServiceErrorCode.EXCEEDS_MAX_TOTAL_AMOUNT,getMessage(HttpAppSystemConstant.CONTRACT_DISNUM_ISNULL));
		}
		UserInfoBean u = this.getCurrentUser(request);
		try {
			if(cot == ContractOperateType.FUNDS_GOODS_CONFIRM){
				//this.iContractDisPriceService.applyContractFinalEstimate(contractId, u.getCid(), u.getCname(), NumberUtils.toDouble(disPrice), NumberUtils.toDouble(disNum));
				iContractDisPriceService.applyContractFinalEstimate(contractId, u.getCid(), u.getCname(), NumberUtils.toDouble(disPrice), NumberUtils.toDouble(disNum), NumberUtils.toDouble(finalAmount));
			}else if(cot == ContractOperateType.AGREE_FUND_GOODS_CONFIRM){
				this.iContractDisPriceService.agreeContractFinalEstimate(contractId, u.getCid(), u.getCname(), StringUtils.EMPTY);
			}else if(cot == ContractOperateType.APPLY_ARBITRATION){
				TOrderArbitration bean = this.iContractArbitrationService.toContractArbitration(contractId, u.getCid(), u.getCname(),StringUtils.EMPTY);
				return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS),bean);
			}else{
				return this.getBuildFailureResult(null, HttpApplicationErrorCode.RESULT_ERROR_CODE, getMessage(HttpAppSystemConstant.CONTRACT_INVALIDATION_TYPE));
			}
			return buildSuccessResult(getMessage(HttpAppSystemConstant.OPERATE_SUCCESS_TIPS));
		} catch (ServiceException e) {
			return getBuildFailureResult(e,HttpApplicationErrorCode.RESULT_ERROR_CODE);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getMyContractListWithPaginiation",method={RequestMethod.POST,RequestMethod.GET})
	public Object getMyContractListWithPaginiation(HttpServletRequest request,HttpServletResponse response){
		//status is ContractStatus.DRAFT,ContractStatus.DOING,ContractStatus.FINISHED.
		QueryContext<ContractInfoBean> qContext = new QueryContext<ContractInfoBean>();
		PageModel page = initilizePageParams(qContext.getPage(), request);
		qContext.setPage(page);
		qContext.setParameters(buildParametersToMap(request));
		qContext.addParameter("cid", this.getCurrentUserCid(request));
		qContext = iContractInfoService.queryContractListOfMineForPagination(qContext);
		return qContext.getQueryResult().getResult();
	}
	
	/**
	 * getEvaluationContractList(评价合同接口,请见NoAuthUrlController里面的方法)
	 * 
	 * @param request,response
	 * @author Bill huang
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	//@ResponseBody
	//@RequestMapping(value = "/getEvaluationContractList",method={RequestMethod.POST,RequestMethod.GET})
	@Deprecated
	public Object getEvaluationContractList(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("ID");
		if (StringUtils.isEmpty(id)) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_ID_IS_NULL));
		}
		String type = request.getParameter("type");
		boolean f = !StringUtils.equalsIgnoreCase("0", type) && !StringUtils.equalsIgnoreCase("1", type);
		if (StringUtils.isEmpty(type) || f) {
			return buildFailResult(HttpApplicationErrorCode.PARAMETER_IS_NULL,getMessage(HttpAppSystemConstant.CONTRACT_INVALIDATION_TYPE));
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
			return buildFailResult(HttpApplicationErrorCode.RESULT_ERROR_CODE,getMessage(HttpAppSystemConstant.RETURN_NO_DATA_TIPS));
		}
	}
	
}
