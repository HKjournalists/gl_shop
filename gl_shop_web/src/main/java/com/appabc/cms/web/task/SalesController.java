package com.appabc.cms.web.task;

import com.appabc.bean.bo.OrderFindInsteadBean;
import com.appabc.bean.bo.UrgeVerifyInfo;
import com.appabc.bean.enums.OrderFindInfo;
import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.enums.UrgeInfo.UrgeStatus;
import com.appabc.bean.enums.UrgeInfo.UrgeType;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TUrgeVerify;
import com.appabc.cms.web.ProductUtils;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.urge.IUrgeDepositService;
import com.appabc.datas.service.urge.IUrgeVerifyService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 2:33:07 PM
 */
@Controller
@RequestMapping("/tasks/sales")
public class SalesController extends TaskController {

    @Autowired
    private IOrderFindService orderFindService;
    @Autowired
    private ICompanyInfoService companyInfoService;
    @Autowired
    private IUrgeVerifyService urgeVerifyService;
    @Autowired
    private IUrgeDepositService urgeDepositService;
    
	@RequestMapping("/verify/need/{page}/")
	public String salesPromptVerify(@PathVariable Integer page, ModelMap model,String registtimeorder,String verifytimeorder) {
		if (page == null || page < 1) {
            return "redirect:/tasks/sales/verify/need/1/";
        }
		Map<String, Object> params = new HashMap<>();
		StringBuilder psb = new StringBuilder();
		if(StringUtils.isNotEmpty(registtimeorder)){
			params.put("registtimeorder", registtimeorder);
			psb.append("&registtimeorder=").append(registtimeorder);
			model.addAttribute("registtimeorder",registtimeorder);
		}
		if(StringUtils.isNotEmpty(verifytimeorder)){
			params.put("verifytimeorder", verifytimeorder);
			psb.append("&verifytimeorder=").append(verifytimeorder);
			 model.addAttribute("verifytimeorder",verifytimeorder);
		}
		
        QueryContext<UrgeVerifyInfo> qContext = new QueryContext<UrgeVerifyInfo>();
        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        qContext.setPage(paging);
        qContext.setParameters(params);
        qContext = this.urgeVerifyService.getVerifyList(qContext);
        calculatePagingData(model, "/tasks/sales/verify/need/{}/?1=1"+psb, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        model.addAttribute("resList", qContext.getQueryResult().getResult());
		return "tasks/sales_prompt_verify";
	}
	
	@RequestMapping("/verify/no/{page}/")
	public String salesPromptNoVerify(@PathVariable Integer page, ModelMap model,String registtimeorder,String verifytimeorder) {
		if (page == null || page < 1) {
            return "redirect:/tasks/sales/verify/no/1/";
        }
		Map<String, Object> params = new HashMap<>();
		StringBuilder psb = new StringBuilder();
		if(StringUtils.isNotEmpty(registtimeorder)){
			params.put("registtimeorder", registtimeorder);
			psb.append("&registtimeorder=").append(registtimeorder);
			model.addAttribute("registtimeorder",registtimeorder);
		}
		if(StringUtils.isNotEmpty(verifytimeorder)){
			params.put("verifytimeorder", verifytimeorder);
			psb.append("&verifytimeorder=").append(verifytimeorder);
			 model.addAttribute("verifytimeorder",verifytimeorder);
		}
        QueryContext<UrgeVerifyInfo> qContext = new QueryContext<UrgeVerifyInfo>();
        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        qContext.setPage(paging);
        qContext.setParameters(params);
        qContext = this.urgeVerifyService.getVerifyNoList(qContext);
        calculatePagingData(model, "/tasks/sales/verify/no/{}/?1=1"+psb, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        model.addAttribute("resList", qContext.getQueryResult().getResult());
		return "tasks/sales_prompt_verify";
	}
	
	@RequestMapping("/verify/detail/{id}/{type}")
	public String salesPromptVerifyDetail(@PathVariable String id,@PathVariable String type,  ModelMap model) {
		if(type.equals("need"))
		{ 
			Task<UrgeVerifyInfo> t = taskService.getUrgeVerifyTask(String.valueOf(id));
	        if (t.getOwner() == null) {
	            claimTask(String.valueOf(id), TaskType.UrgeVerify);
	            t.setOwner(getCurrentUser());
	         }        
		}		
		UrgeVerifyInfo uv=this.urgeVerifyService.queryVerifyInfoByTaskId(id);
		List<TUrgeVerify> urgeVerify=this.urgeVerifyService.queryRecordByCompanyId(uv.getUserid());
		uv.setUrgeVerifyList(urgeVerify);
		model.addAttribute("uv", uv);	
		return "tasks/sales_prompt_verify_detail_"+type;
	}
	
	@RequestMapping(value="/verify/detail/add/{companyId}/{taskid}/{action}/", method = RequestMethod.POST)
	public String salesPromptVerifyResult(HttpServletRequest request,
            @PathVariable String companyId,
            @PathVariable String action,
            @PathVariable String taskid,
            TUrgeVerify tUrgeVerify,
            ModelMap model) throws ServletRequestBindingException, ServiceException{
				
		tUrgeVerify.setUtype(UrgeType.URGE_VERIFY);
		tUrgeVerify.setUrgestatus(UrgeStatus.enumOf(action));
		tUrgeVerify.setCreater(String.valueOf(getCurrentUser().getId()));
		tUrgeVerify.setCreatetime(new Date());
		tUrgeVerify.setCid(companyId);
		this.urgeVerifyService.add(tUrgeVerify);
		if(action.equals("1"))
		{ 
			completeTask(taskid, TaskType.UrgeVerify);	 
			return "redirect:/tasks/sales/verify/no/1/";
		}
		return "redirect:/tasks/sales/verify/need/1/";
	}
	

	@RequestMapping("/deposit/need/{page}/")
	public String salesPromptDeposit(@PathVariable Integer page, ModelMap model,String registtime,String verifytime) {
		if (page == null || page < 1) {
            return "redirect:/tasks/sales/deposit/need/1/";
        }
			Map<String, Object> params = new HashMap<>();
	        String strParams = "";
	    	if(StringUtils.isNotEmpty(registtime)){
	        	strParams += "?registtime="+registtime;
	        	model.addAttribute("registtime",registtime);
	        	params.put("registtime",registtime);
	        }
	    	if(StringUtils.isNotEmpty(verifytime)){
	        	strParams += "?verifytime="+verifytime;
	        	model.addAttribute("verifytime",verifytime);
	        	params.put("verifytime",verifytime);
	        }
		 	QueryContext<UrgeVerifyInfo> qContext = new QueryContext<UrgeVerifyInfo>();
	        PageModel paging = new PageModel();
	        paging.setPageSize(DEFAULT_PAGE_SIZE);
	        paging.setPageIndex(page);
	        qContext.setPage(paging);
	        qContext.setParameters(params);
	        qContext = this.urgeDepositService.getDepositList(qContext);
	        calculatePagingData(model, "/tasks/sales/deposit/need/{}/"+strParams, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
	        model.addAttribute("resList", qContext.getQueryResult().getResult());
		return "tasks/sales_prompt_deposit";
	}
	@RequestMapping("/deposit/no/{page}/")
	public String salesNoPromptDeposit(@PathVariable Integer page, ModelMap model,String registtime,String verifytime) {
		if (page == null || page < 1) {
            return "redirect:/tasks/sales/deposit/no/1/";
        }
		Map<String, Object> params = new HashMap<>();
        String strParams = "";
    	if(StringUtils.isNotEmpty(registtime)){
        	strParams += "?registtime="+registtime;
        	model.addAttribute("registtime",registtime);
        	params.put("registtime",registtime);
        }
    	if(StringUtils.isNotEmpty(verifytime)){
        	strParams += "?verifytime="+verifytime;
        	model.addAttribute("verifytime",verifytime);
        	params.put("verifytime",verifytime);
        }
		 QueryContext<UrgeVerifyInfo> qContext = new QueryContext<UrgeVerifyInfo>();
	        PageModel paging = new PageModel();
	        paging.setPageSize(DEFAULT_PAGE_SIZE);
	        paging.setPageIndex(page);
	        qContext.setPage(paging);
	        qContext.setParameters(params);
	        qContext = this.urgeDepositService.getNoDepositList(qContext);
	        calculatePagingData(model, "/tasks/sales/deposit/no/{}/", page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
	        model.addAttribute("resList", qContext.getQueryResult().getResult());
		return "tasks/sales_prompt_deposit";
	}
	@RequestMapping("/deposit/need/detail/{taskid}/")
	public String salesPromptDepositDetail(@PathVariable String taskid,  ModelMap model) {
		
		Task<UrgeVerifyInfo> t = taskService.getUrgeDepositTask(String.valueOf(taskid));
		if (t.getOwner() == null) {
		        claimTask(String.valueOf(taskid), TaskType.UrgeDeposit);
		        t.setOwner(getCurrentUser());
		    }      
				
		UrgeVerifyInfo uv=this.urgeDepositService.queryDepositInfoByTaskId(taskid);
		List<TUrgeVerify> urgeDeposit=this.urgeDepositService.queryRecordByTypeAndId(UrgeType.URGE_DEPOSIT.getVal(), uv.getId());
		uv.setUrgeVerifyList(urgeDeposit);
		model.addAttribute("uv", uv);	
		return "tasks/sales_prompt_deposit_detail";
	}
	@RequestMapping("/deposit/no/detail/{taskid}/")
	public String salesPromptNoDepositDetail(@PathVariable String taskid,  ModelMap model) {		
		UrgeVerifyInfo uv=this.urgeDepositService.queryDepositInfoByTaskId(taskid);
		List<TUrgeVerify> urgeDeposit=this.urgeDepositService.queryRecordByTypeAndId(UrgeType.URGE_DEPOSIT.getVal(), uv.getId());
		uv.setUrgeVerifyList(urgeDeposit);
		model.addAttribute("uv", uv);	
		return "tasks/sales_prompt_deposit_detail";
	}
	
	@RequestMapping(value="/deposit/need/detail/add/{passid}/", method = RequestMethod.POST)
	public String salesPromptDepositResult(HttpServletRequest request,
            @PathVariable String passid,        
            TUrgeVerify tUrgeVerify,
            ModelMap model) throws ServletRequestBindingException, ServiceException{				
		tUrgeVerify.setUtype(UrgeType.URGE_DEPOSIT);
		tUrgeVerify.setUrgestatus(UrgeStatus.NEED_URGE);
		tUrgeVerify.setCreater(String.valueOf(getCurrentUser().getId()));
		tUrgeVerify.setCreatetime(new Date());
		tUrgeVerify.setCid(passid);
		this.urgeDepositService.add(tUrgeVerify);
		return "redirect:/tasks/sales/deposit/need/1/";
	}
	@RequestMapping(value="/deposit/no/detail/add/{passid}/{taskid}/", method = RequestMethod.POST)
	public String salesPromptNoDepositResult(HttpServletRequest request,
            @PathVariable String passid,
            @PathVariable String taskid,
            TUrgeVerify tUrgeVerify,
            ModelMap model) throws ServletRequestBindingException, ServiceException{
				
		tUrgeVerify.setUtype(UrgeType.URGE_DEPOSIT);
		tUrgeVerify.setUrgestatus(UrgeStatus.NO_URGE);
		tUrgeVerify.setCreater(String.valueOf(getCurrentUser().getId()));
		tUrgeVerify.setCreatetime(new Date());
		tUrgeVerify.setCid(passid);
		this.urgeDepositService.add(tUrgeVerify);		 		
		completeTask(taskid, TaskType.UrgeDeposit);	 		
		return "redirect:/tasks/sales/deposit/no/1/";
	}
	

	@RequestMapping("/payment/")
	public String salesPromptPayment() {
		return "task/sales_prompt_payment";
	}

	@RequestMapping("/realpay/")
	public String salesPromptRealPay() {
		return "task/sales_prompt_real_pay";
	}

    @RequestMapping("/pubinfo/")
    public String publishTradeInfo(ModelMap model) {
        return "tasks/sales_pub_trade_info";
    }

    @SuppressWarnings("static-access")
	@RequestMapping("/pubinfo/finished/{page}/")
    public String queryPublishFinishedList(@PathVariable Integer page, Integer operator, ModelMap model) {

    	if (page == null || page < 1) {
            return "redirect:/tasks/sales/pubinfo/finished/1/";
        }

    	String strParams = "";
        Map<String, Object> params = new HashMap<>();
        if(operator != null && operator == 1){
        	params.put("operatorId", this.getCurrentUser().getId());
        	strParams += "?operator="+operator;
        }

        QueryContext<OrderFindInsteadBean> qContext = new QueryContext<OrderFindInsteadBean>();
        qContext.setParameters(params);

        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        qContext.setPage(paging);
        qContext = this.orderFindService.queryParentOrderFindOfInsteadListForPagination(qContext);
        calculatePagingData(model, "/tasks/sales/pubinfo/finished/{}/"+strParams, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        model.addAttribute("resList", qContext.getQueryResult().getResult());
        model.addAttribute("operatorVal",operator);

    	return "tasks/sales_pub_finished_list";
    }

    @RequestMapping(value="/pubinfo/", method = RequestMethod.POST)
    public String doPublishTradeInfo(TOrderFind orderFind, BindingResult result,
                                     TOrderProductInfo orderProductInfo, BindingResult result1,
                                     String addressid, HttpServletRequest req) {
        try {
            Integer orderType = ServletRequestUtils.getIntParameter(req, "type");
            orderFind.setType(OrderFindInfo.OrderTypeEnum.enumOf(orderType));
            orderFind.setCreater(getCurrentUser().getUserName());

            Integer addressType = ServletRequestUtils.getIntParameter(req, "addresstype");
            orderFind.setAddresstype(OrderFindInfo.OrderAddressTypeEnum.enumOf(addressType));

            String unitType = ServletRequestUtils.getStringParameter(req, "unit");
            ProductInfo.UnitEnum unit = ProductInfo.UnitEnum.enumOf(unitType);
            orderFind.setUnit(unit);
            orderProductInfo.setUnit(unit);

            orderFind.setCreater(getCurrentUser().getRealName());

            orderFindService.orderPublishSubstitute(orderFind, orderProductInfo, ProductUtils.collectProductProperties(req), addressid);

            /****已处理列表**************/
            TCompanyInfo ci = this.companyInfoService.query(orderFind.getCid());
            Date now = new Date();

            Task<?> taskBean = new Task<Object>();
            taskBean.setClaimTime(now);
            taskBean.setCompany(ci);
            taskBean.setCreateTime(now);
            taskBean.setFinished(true);
            taskBean.setFinishTime(now);
            taskBean.setObjectId(orderFind.getId());
            taskBean.setOwner(getCurrentUser());
            taskBean.setType(TaskType.OrderFindPublish);

            taskService.createTask(taskBean);

        } catch (ServiceException e) {
            logger.error("Something error", e);
            throw new RuntimeException(e);
        } catch (ServletRequestBindingException e) {
            logger.error("Required value not found.", e);
        }
        return "redirect:/tasks/sales/pubinfo/finished/1/";
    }
}
