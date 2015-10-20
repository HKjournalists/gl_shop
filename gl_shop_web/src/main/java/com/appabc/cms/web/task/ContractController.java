package com.appabc.cms.web.task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.ContractArbitrationBean;
import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.bo.OrderFindAllBean;
import com.appabc.bean.bo.TaskArbitrationInfo;
import com.appabc.bean.bo.TaskContractInfo;
import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.enums.ContractInfo;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.OrderFindInfo;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.enums.PurseInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderAddress;
import com.appabc.bean.pvo.TOrderArbitration;
import com.appabc.bean.pvo.TOrderDisPrice;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderFindMatch;
import com.appabc.bean.pvo.TOrderFindMatchEx;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TOrderProductInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.cms.web.ProductUtils;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.service.IContractService;
import com.appabc.datas.cms.service.UserService;
import com.appabc.datas.cms.vo.Context;
import com.appabc.datas.cms.vo.ServiceLogType;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractDisPriceService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.service.order.IOrderFindMatchService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.order.IOrderProductPropertyService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.service.IPassPayService;

/**
 * Created by zouxifeng on 12/11/14.
 */
@Controller
@RequestMapping("/tasks/contract")
public class ContractController extends TaskController {

    @Autowired
    private IOrderFindService orderFindSerivce;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private IContractInfoService contractInfoService;

    @Autowired
    private IOrderProductPropertyService orderProductPropertyService;

    @Autowired
    private IContractArbitrationService contractArbitrationService;

    @Autowired
    private IContractOperationService contractOperationService;

    @Autowired
    private IContractDisPriceService contractDisPriceService;

    @Autowired
    private IPassPayService passPayService;

    @Autowired
	private UserService sUserService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IContractService iContractService;

    @Autowired
    private IOrderFindMatchService orderFindMatchService;


    @ModelAttribute
    private ContractForm setupContractForm(ContractForm form, ModelMap model) {
        if (form.getTask() != null) {
            Task<OrderAllInfor> t = taskService.getOrderRequestTask(form.getTask().getId());
            OrderAllInfor orderReq = orderFindSerivce.queryInfoById(t.getObjectId(), t.getCompany().getId());
            t.setTaskObject(orderReq);
            form.setTask(t);
            form.setOrderRequest(orderReq);
            form.setProductCode(orderReq.getPcode());
        }

        if (form.getBuyer() != null) {
            TCompanyInfo buyer = companyInfoService.query(form.getBuyer().getId());
            form.setBuyer(buyer);
        }

        if (form.getSeller() != null) {
            TCompanyInfo seller = companyInfoService.query(form.getSeller().getId());
            form.setSeller(seller);
        }

        return form;
    }

    @RequestMapping("/order_requests/{page}/")
    public String contractRequests(@PathVariable int page, ModelMap model,String applyDate,String creatime) {
        if (page < 1) {
            page = 1;
        }
        Map<String, Object> params = new HashMap<>();
        String strParams = "";
    	if(StringUtils.isNotEmpty(applyDate)){
        	strParams += "?applyDate="+applyDate;
        	model.addAttribute("applyDate",applyDate);
        	params.put("applyDate",applyDate);
        }
    	if(StringUtils.isNotEmpty(creatime)){
        	strParams += "?creatime="+creatime;
        	model.addAttribute("creatime",creatime);
        	params.put("creatime",creatime);
        }
    	Context<Task> ctx = new Context<>();
        ctx.setStart(calculateStartRow(page, DEFAULT_PAGE_SIZE));
        ctx.setPageSize(DEFAULT_PAGE_SIZE);
        ctx.setParameters(params);
        ctx = taskService.queryTaskListForUnFinished(ctx, TaskType.MatchOrderRequest);
        model.addAttribute("tasks", ctx.getResult());
        calculatePagingData(model, "/tasks/contract/order_requests/{}/"+strParams, page, ctx.getTotalRows());
        return "tasks/contract/order_requests";
    }
    
    @RequestMapping("/order_requests/export/")
    public void orderRequestsExport(HttpServletResponse response,String applyDate,String creatime) throws IOException {
        final String[] csvHeader = new String[] {"用户手机号码", "用户名称", "企业类型",
                "撮合交易内容标题", "交易保证金", "被交易询盘次数", "交易询盘时间" , "撮合次数" , 
                "发布时间"};
        
        Map<String, Object> params = new HashMap<>();
    	if(StringUtils.isNotEmpty(applyDate)){
        	params.put("applyDate",applyDate);
        }
    	if(StringUtils.isNotEmpty(creatime)){
        	params.put("creatime",creatime);
        }
        Context<Task> ctx = new Context<>();
        ctx.setStart(0);
        ctx.setPageSize(-1);
        ctx.setParameters(params);
        ctx = taskService.queryTaskListForUnFinished(ctx, TaskType.MatchOrderRequest);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=export_order.csv");
        CSVPrinter out = CSVFormat.EXCEL.withHeader(csvHeader).print(response.getWriter());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Task t : ctx.getResult()) {
            List<Object> record = new ArrayList<>(csvHeader.length);
            TOrderFind order = (TOrderFind) t.getTaskObject();
            record.add(t.getCustomer().getPhone());
            if (t.getCompany().getCtype() != null) {
                record.add(t.getCompany().getCname());
                record.add(t.getCompany().getCtype().getText());
            } else {
                record.add("未知");
                record.add("未知");
            }
            record.add(order.getTitle());
            record.add(order.getGuaranty());
            record.add(order.getApplyNum());
            String applyDateStr = order.getApplyDate() != null ? df.format(order.getApplyDate()) : "";
            record.add(applyDateStr);
            record.add(order.getMatchingnum());
            String creatimeStr = order.getCreatime() != null ? df.format(order.getCreatime()) : "";
            record.add(creatimeStr);
            out.printRecord(record);
        }
        out.flush();
        out.close();
    }
    
    @RequestMapping("/order_requests/finished/{page}/")
    public String orderRequestToContractFinishedList(@PathVariable int page, Integer operator,String createTime, ModelMap model) {
    	if (page < 1) {
    		page = 1;
    	}
    	
    	String strParams = "";
    	Map<String, Object> params = new HashMap<>();
    	if(operator != null && operator == 1){
        	params.put("task.owner", this.getCurrentUser().getId());
        	strParams += "?operator="+operator;
        }
    	if(StringUtils.isNotEmpty(createTime)){
        	strParams += "?createTime="+createTime;
        	params.put("createTime",createTime);
        }
    	
    	Context<Task> ctx = new Context<>();
    	ctx.setStart(calculateStartRow(page, DEFAULT_PAGE_SIZE));
    	ctx.setPageSize(DEFAULT_PAGE_SIZE);
    	ctx.setParameters(params);
    	ctx = taskService.queryTaskListForFinished(ctx, TaskType.MatchOrderRequest);
    	model.addAttribute("tasks", ctx.getResult());
    	model.addAttribute("operatorVal",operator);
    	model.addAttribute("createTime",createTime);
    	calculatePagingData(model, "/tasks/contract/order_requests/finished/{}/"+strParams, page, ctx.getTotalRows());
    	return "tasks/contract/order_requests_finished_list";
    }
    
    @RequestMapping("/order_requests/finished/export/")
    public void orderRequestsFinishedExport(HttpServletResponse response,Integer operator,String createTime) throws IOException {
    	final String[] csvHeader = new String[] {"用户手机号码", "用户名称", "企业类型",
                "撮合交易内容标题", "交易保证金", "被交易询盘次数", "撮合次数", "发布时间", "完成处理人" , "处理时间"};
    	
    	Map<String, Object> params = new HashMap<>();
    	if(operator != null && operator == 1){
        	params.put("task.owner", this.getCurrentUser().getId());
        }
    	if(StringUtils.isNotEmpty(createTime)){
        	params.put("createTime",createTime);
        }
    	Context<Task> ctx = new Context<>();
    	ctx.setStart(0);
        ctx.setPageSize(-1);
    	ctx.setParameters(params);
    	ctx = taskService.queryTaskListForFinished(ctx, TaskType.MatchOrderRequest);
    	response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=export_order_finished.csv");
        CSVPrinter out = CSVFormat.EXCEL.withHeader(csvHeader).print(response.getWriter());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Task t : ctx.getResult()) {
            List<Object> record = new ArrayList<>(csvHeader.length);
            TOrderFind order = (TOrderFind) t.getTaskObject();
            record.add(t.getCustomer().getPhone());
            if (t.getCompany().getCtype() != null) {
                record.add(t.getCompany().getCname());
                record.add(t.getCompany().getCtype().getText());
            } else {
                record.add("未知");
                record.add("未知");
            }
            record.add(order.getTitle());
            record.add(order.getGuaranty());
            record.add(order.getApplyNum());
            record.add(order.getMatchingnum());
            String createTimeStr = t.getCreateTime() != null ? df.format(t.getCreateTime()) : "";
            record.add(createTimeStr);
            String realName = t.getOwner() != null ? t.getOwner().getRealName() : "";
            record.add(realName);
            String creatimeStr = t.getFinishTime() != null ? df.format(t.getFinishTime()) : "";
            record.add(creatimeStr);
            out.printRecord(record);
        }
        out.flush();
        out.close();
    }

    @RequestMapping("/order_requests/detail/{id}/")
    public String contractRequestDetail(@PathVariable int id, ModelMap model) {
        Task<OrderAllInfor> t = claimTask(String.valueOf(id), TaskType.MatchOrderRequest);
        OrderAllInfor req = orderFindSerivce.queryInfoById(t.getObjectId(), t.getCompany().getId());
        t.setTaskObject(req);
        model.addAttribute("task", t);
        model.addAttribute("guaranty", getAvailableGuaranty(t.getCompany().getId()));
        putServiceLogs(req.getId(), ServiceLogType.MatchOrder, model);
        return "tasks/contract/order_request_summary";
    }

    @RequestMapping("/order_requests/detail/{id}/{type}/{page}/")
    public String contractRequestCandidates(@PathVariable int id,
                                            @PathVariable Integer page,
                                            @PathVariable String type,
                                            String matchType,
                                            String showType,
                                            ModelMap model) {
        if (page == null || page < 1) {
            page = 1;
        }

        try {
            Task<OrderAllInfor> t = taskService.getOrderRequestTask(String.valueOf(id));
            if (t.getOwner() == null) {
                claimTask(String.valueOf(id), TaskType.MatchOrderRequest);
                t.setOwner(getCurrentUser());
            }
            model.addAttribute("guaranty", getAvailableGuaranty(t.getCompany().getId()));
            t.setTaskObject(orderFindSerivce.queryInfoById(t.getObjectId(), t.getCompany().getId()));

            model.addAttribute("task", t);

            model.addAttribute("listType", type);
            model.addAttribute("matchType", matchType);
            model.addAttribute("showType", showType);

            switch (type) {
                case "possible":
                    orderRequestMatchingPossibleTargets(t, page, matchType, model);
                    break;
                case "saved":
                    contractDraftList(t, page, matchType, type,
                            OrderFindInfo.OrderFindMatchStatusEnum.SAVE, model);
                    break;
                case "canceled":
                    contractDraftList(t, page, matchType, type,
                            OrderFindInfo.OrderFindMatchStatusEnum.CANCEL, model);
                    break;
                case "success":
                	contractDraftList(t, page, matchType, type, OrderFindMatchStatusEnum.SUCCESS, model);
                	break;
            }
        } catch (ServiceException e) {
            logger.error("request id is not provided.");
        }

        return "tasks/contract/order_request_match";
    }

    @RequestMapping(value="/order_requests/match/{taskId}/fail/", method = RequestMethod.POST)
    public @ResponseBody String orderRequestMatchFail(@PathVariable int taskId,
                                                      String candidateId,
                                                      String log) {
        Task<OrderAllInfor> t = taskService.getOrderRequestTask(String.valueOf(taskId));
        OrderAllInfor orderInfo = orderFindSerivce.queryInfoById(t.getObjectId(), t.getCompany().getId());
        orderFindSerivce.addMatchingNum(orderInfo.getId());
        TCompanyInfo company = companyInfoService.query(candidateId);
        StringBuilder logContent = new StringBuilder(100);
        logContent.append("撮合" + company.getCname() + "(" + company.getId() +")失败。");
        logContent.append(log);
        createServiceLog(orderInfo.getCid(), orderInfo.getId(), logContent.toString(), ServiceLogType.MatchOrder);
        return "ok";
    }
        
    private void orderRequestMatchingPossibleTargets(Task<OrderAllInfor> task,
                                                     Integer page,
                                                     String matchType,
                                                     ModelMap model) throws ServiceException {
        boolean isApply = false, isOrderFid = false, isIdentity = false;

        if ("ALL".equals(matchType)) {
            isApply = true;
            isOrderFid = true;
        } else if ("INTR".equals(matchType)) {
            isApply = true;
        } else if ("MATCH".equals(matchType)) {
            isOrderFid = true;
        }

        QueryContext<MatchingBean> ctx = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageIndex(page);
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        ctx.setPage(pm);
        ctx = orderFindSerivce.queryMatchingObjectByFidForPagination(ctx,
                task.getTaskObject().getId(), isApply, isOrderFid, isIdentity);

        List<MatchCandidate> candidates = new LinkedList<>();
        for (MatchingBean target : ctx.getQueryResult().getResult()) {
            candidates.add(new MatchCandidate(target,
                    getAvailableGuaranty(target.getCid()), target.getCtype(),
                    target.getPhone()));
        }

        model.addAttribute("candidates", candidates);
        calculatePagingData(model,
                "/tasks/contract/order_requests/detail/" + task.getId()
                        + "/possible/{}/?matchType=" + matchType,
                page, pm.getTotalPage() * pm.getPageSize());
        model.addAttribute("list_fragment_name", "match_candidates");
    }

    @RequestMapping(value = "/order_requests/match/{taskId}/", method = RequestMethod.POST)
    public String orderRequestMatch(@PathVariable int taskId, String candidateId, ModelMap model) {
        Task<OrderAllInfor> t = taskService.getOrderRequestTask(String.valueOf(taskId));
        OrderAllInfor orderInfo = orderFindSerivce.queryInfoById(t.getObjectId(), t.getCompany().getId());
        model.addAttribute("task", t);
        model.addAttribute("orderRequest", orderInfo);
        TCompanyInfo candidate = companyInfoService.query(candidateId);
        return "";
    }
        
    private void contractDraftList(Task<OrderAllInfor> task,
                                   int page, String matchType, String listType,
                                   OrderFindInfo.OrderFindMatchStatusEnum status,
                                   ModelMap model) {
        OrderFindInfo.OrderFindMatchOpTypeEnum opType = null;
        if ("INTR".equals(matchType)) {
            opType = OrderFindInfo.OrderFindMatchOpTypeEnum.TRADEINQUIRY;
        } else if ("MATCH".equals(matchType)) {
            opType = OrderFindInfo.OrderFindMatchOpTypeEnum.MATCHCONTRACT;
        }

        String contractId = task.getTaskObject().getContractid();
        String targetId = StringUtils.EMPTY;
        if(StringUtils.isNotEmpty(contractId)){
        	OrderTypeEnum orderType = OrderTypeEnum.enumOf(task.getTaskObject().getType());
        	TOrderInfo toi = this.contractInfoService.query(contractId);
        	if(toi != null && orderType == OrderTypeEnum.ORDER_TYPE_BUY){
        		targetId = toi.getSellerid();
        	}else if(toi != null && orderType == OrderTypeEnum.ORDER_TYPE_SELL){
        		targetId = toi.getBuyerid();
        	}
        }
        
        QueryContext<TOrderFindMatchEx> qc = new QueryContext<>();
        qc.addParameter("owner", task.getCompany().getId());
        qc.addParameter("target", targetId);
        qc.addParameter("status", status);
        qc.addParameter("opType", opType);
        qc.addParameter("opfid", task.getTaskObject().getFid());
        PageModel p = qc.getPage();
        p.setPageSize(DEFAULT_PAGE_SIZE);
        p.setPageIndex(page);
        qc = orderFindMatchService.findOrderFindMatchExInfoForPagination(qc);
        List<MatchCandidate> candidates = new LinkedList<>();
        if(status == OrderFindMatchStatusEnum.SUCCESS){
        	List<TOrderFindMatchEx> l = qc.getQueryResult().getResult();
        	if(CollectionUtils.isNotEmpty(l)){
        		TOrderFindMatchEx target = l.get(0);
        		candidates.add(new MatchCandidate(target, getAvailableGuaranty(target.getTarget()), target.gettCtype(), target.gettPhone()));
        	}
        }else{
//          for (TOrderFindMatchEx target : orderFindMatchService.findOrderFindMatchExInfo(task.getCompany().getId(), status)) {
            for (TOrderFindMatchEx target : qc.getQueryResult().getResult()) {
                candidates.add(new MatchCandidate(target, getAvailableGuaranty(target.getTarget()),
                        target.gettCtype(), target.gettPhone()));
            }
        }

        calculatePagingData(model,
                "/tasks/contract/order_requests/detail/" + task.getId()
                        + "/" + listType + "/{}/?matchType=" + matchType,
                page, p.getTotalPage() * p.getPageSize());
        model.addAttribute("candidates", candidates);
        model.addAttribute("is_saved_draft", true);
        model.addAttribute("list_fragment_name", "saved_contracts");
    }

    @RequestMapping(value="/order_requests/cancel_draft/", method=RequestMethod.POST)
    public @ResponseBody TOrderFindMatch cancelDraft(ContractForm form, int matchType,
                    String draftId, String serviceLog,
                    TOrderFind orderFind, BindingResult orderFindResult,
                    TOrderProductInfo orderProduct, BindingResult orderProductResult,
                    TOrderAddress address, BindingResult addressResult,
                    HttpServletRequest request, ModelMap model) throws ServletRequestBindingException, ServiceException {
        TOrderFindMatch result = saveContractDraft(form, matchType, draftId, orderFind,
                orderProduct, OrderFindInfo.OrderFindMatchStatusEnum.CANCEL,
                address, serviceLog, request);
        return result;
    }

    @RequestMapping(value="/order_requests/save_draft/",
                    method=RequestMethod.POST)
    public @ResponseBody TOrderFindMatch saveContractDraft(ContractForm form, int matchType,
                          String draftId, String serviceLog,
                          TOrderFind orderFind, BindingResult orderFindResult,
                          TOrderProductInfo orderProduct, BindingResult orderProductResult,
                          TOrderAddress address, BindingResult addressResult,
                          HttpServletRequest request, ModelMap model) throws ServletRequestBindingException, ServiceException {
        TOrderFindMatch result = saveContractDraft(form, matchType, draftId, orderFind, orderProduct,
                OrderFindInfo.OrderFindMatchStatusEnum.SAVE,
                address, serviceLog, request);
        return result;
    }

    @RequestMapping(value="/order_requests/generate_contract/",
                    method=RequestMethod.POST)
    public @ResponseBody TOrderFindMatch matchAndGenerateContract(ContractForm form, int matchType,
                          String draftId, String serviceLog,
                          TOrderFind orderFind, BindingResult orderFindResult,
                          TOrderProductInfo orderProduct, BindingResult orderProductResult,
                          TOrderAddress address, BindingResult addressResult,
                          HttpServletRequest request, ModelMap model) throws ServletRequestBindingException {
        OrderFindAllBean draft = composeOrderObject(form, orderFind, orderProduct, address, request);
        String candidate = form.getTask().getTaskObject().getType() == 1 ? form.getSeller().getId() : form.getBuyer().getId();
        TOrderFindMatch result = null;
    	try {
			contractInfoService.makeAndMatchATOrderInfo(draft, candidate, getCurrentUser().getRealName());
			completeTask(form.getTask().getId(), TaskType.MatchOrderRequest);
			//
			result = saveContractDraft(form, matchType, draftId, draft,OrderFindMatchStatusEnum.SUCCESS, serviceLog);
		} catch (ServiceException e) {
			this.logger.debug(e.getMessage());
			try {
				result = saveContractDraft(form, matchType, draftId, draft,
				        OrderFindInfo.OrderFindMatchStatusEnum.SAVE, serviceLog);
			} catch (ServiceException e1) {
				this.logger.debug(e1.getMessage());
			}
		}
    	return result;
    }

    private TOrderFindMatch saveContractDraft(ContractForm form, int matchType,
                                              String draftId, OrderFindAllBean draft,
                                              OrderFindInfo.OrderFindMatchStatusEnum draftStatus,
                                              String serviceLog)
         throws ServletRequestBindingException, ServiceException {
        TOrderFindMatch match = null;
        if (StringUtils.isNotEmpty(draftId)) {
            TOrderFindMatch q = new TOrderFindMatch();
            q.setOcfid(draftId);
            match = orderFindMatchService.query(q);
            draft.getOfBean().setId(match.getOcfid());
            if (form.getTask().getTaskObject().getType() == 1) {
                form.getSeller().setId(match.getTarget());
            } else {
                form.getBuyer().setId(match.getTarget());
            }
        }
        String candidate = form.getTask().getTaskObject().getType() == 1 ? form.getSeller().getId() : form.getBuyer().getId();

        if (match != null) {
            match.setStatus(draftStatus);
            orderFindMatchService.updateFindMatchInfo(draft, match);
        } else {
            match = orderFindMatchService.saveOrderFindMatchInfo(draft, candidate,
                    OrderFindInfo.OrderFindMatchOpTypeEnum.enumOf(matchType),
                    draftStatus,
                    getCurrentUser().getRealName(),
                    form.getTask().getTaskObject().getTitle(),
                    form.getTask().getTaskObject().getFid());
            orderFindSerivce.addMatchingNum(match.getOpfid());
        }
        createServiceLog(match.getTarget(), match.getOcfid(), serviceLog, ServiceLogType.MatchOrder);
        return match;
    }

    private TOrderFindMatch saveContractDraft(ContractForm form, int matchType, String draftId,
                                   TOrderFind orderFind,
                                   TOrderProductInfo orderProduct,
                                   OrderFindInfo.OrderFindMatchStatusEnum draftStatus,
                                   TOrderAddress address,
                                   String serviceLog, HttpServletRequest request) throws ServletRequestBindingException, ServiceException {
        OrderFindAllBean draft = composeOrderObject(form, orderFind, orderProduct,
                address, request);
        return saveContractDraft(form, matchType, draftId, draft,
                draftStatus, serviceLog);
    }


    @RequestMapping("/generate/step1/")
    public String generateContract(@ModelAttribute ContractForm form, ModelMap model) {
        if (form.getTask() != null) {
        }
        return "tasks/contract/contract_generate_step_1";
    }

    @RequestMapping(value = "/generate/step2/", method = RequestMethod.POST)
    public String generateContractStep2(ContractForm form, ModelMap model) {
        model.addAttribute("contractForm", form);
        model.addAttribute("sellerGuaranty", getAvailableGuaranty(form.getSeller().getId()));
        model.addAttribute("buyerGuaranty", getAvailableGuaranty(form.getBuyer().getId()));
        return "tasks/contract/contract_generate_step_2";
    }

    @RequestMapping(value = "/generate/complete/", method = RequestMethod.POST)
    public @ResponseBody String doGenerateContract(ContractForm contractForm, TOrderFind orderFind, BindingResult orderFindResult,
                                     TOrderProductInfo orderProduct, BindingResult orderProductResult,
                                     TOrderAddress address, BindingResult addressResult,
                                     HttpServletRequest request, ModelMap model) throws ServiceException, ServletRequestBindingException {
        boolean sellerHasEnoughGuaranty = hasEnoughGuaranty(contractForm.getSeller().getId());
        boolean buyerHasEnoughGuaranty = hasEnoughGuaranty(contractForm.getBuyer().getId());
        model.addAttribute("sellerHasEnoughGuaranty", sellerHasEnoughGuaranty);
        model.addAttribute("buyerHasEnoughGuaranty", buyerHasEnoughGuaranty);
        if (!sellerHasEnoughGuaranty || !buyerHasEnoughGuaranty) {
            model.addAttribute("contractForm", contractForm);
            return "not enough guaranty";
        }

        if (contractForm.getTask() != null) {
        	OrderFindAllBean order = composeOrderObject(contractForm, orderFind, orderProduct, address, request);
            String cid = order.getOfBean().getCid().equals(contractForm.getSeller().getId()) ? contractForm.getBuyer().getId() : contractForm.getSeller().getId();
            iContractService.customerMakeAndMatchTOrderHaveTask(order, cid, getCurrentUser(),contractForm.getTask());
            
            return "ok";
        } else {
        	OrderFindAllBean order = composeOrderObject(contractForm, orderFind, orderProduct, address, request);
			iContractService.customerMakeAndMatchTOrderNoTask(order,contractForm.getSeller().getId(),contractForm.getBuyer().getId(),getCurrentUser());
            return "ok";
        }
    }

    @RequestMapping(value = "/pending/{page}/", method = {RequestMethod.POST,RequestMethod.GET})
    public String contractConfirmPending(@PathVariable int page,ModelMap model, HttpServletRequest request) {
    	String status = request.getParameter("status");
    	String contractType = request.getParameter("contractType");
    	if(!StringUtils.isEmpty(status)){
    		model.addAttribute("status", status);
    	}
    	QueryContext<TaskContractInfo> ctx = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageIndex(page);
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        ctx.setPage(pm);
        ctx.addParameter("status", ContractStatus.DRAFT);
        ctx.addParameter("taskType", TaskType.ContractConfirm);
        if(StringUtils.equalsIgnoreCase("2", contractType)){
        	ctx.addParameter("generator", getCurrentUser().getId());
        }
        ctx = iContractService.getConfirmContractOrderList(ctx);
        model.addAttribute("confirmOrderInfo", ctx.getQueryResult().getResult());
        calculatePagingData(model, "/tasks/contract/pending/{}/"+(StringUtils.isNotEmpty(contractType) ? "?contractType="+contractType : ""), page, pm.getTotalPage() * pm.getPageSize());
        
        return "tasks/contract/contract_confirm_pending";
    }
    
    @RequestMapping(value = "/finished/{page}/", method = {RequestMethod.POST,RequestMethod.GET})
    public String contractFinished(@PathVariable int page, ModelMap model, HttpServletRequest request) {
    	String contractType = request.getParameter("contractType");
    	QueryContext<TaskContractInfo> ctx = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageIndex(page);
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        ctx.setPage(pm);
        ctx.addParameter("status", ContractStatus.FINISHED);
        ctx.addParameter("taskType", TaskType.ContractConfirm);
        if(StringUtils.equalsIgnoreCase("2", contractType)){
        	ctx.addParameter("generator", getCurrentUser().getId());
        }
        ctx = iContractService.getConfirmContractOrderList(ctx);
        model.addAttribute("confirmOrderInfo", ctx.getQueryResult().getResult());
        calculatePagingData(model, "/tasks/contract/finished/{}/"+(StringUtils.isNotEmpty(contractType) ? "?contractType="+contractType : ""), page, pm.getTotalPage() * pm.getPageSize());
    	
    	return "tasks/contract/contract_finished_list";
    }

    @RequestMapping("/arbitrations/{status}/{page}/")
    public String contractArbitrate(@PathVariable Integer page,
                                    @PathVariable String status,
                                    ModelMap model,
                                    HttpServletRequest request) {
    	String retStatus = request.getParameter("status");
    	if(!StringUtils.isEmpty(retStatus)){
    		model.addAttribute("status", retStatus);
    	}
        if (page < 1) {
            page = 1;
        }

        ContractInfo.ContractArbitrationStatus s;
        if ("pending".equals(status)) {
            s = ContractInfo.ContractArbitrationStatus.REQUEST;
        } else if ("success".equals(status)) {
            s = ContractInfo.ContractArbitrationStatus.SUCCESS;
        } else if ("fail".equals(status)) {
            s = ContractInfo.ContractArbitrationStatus.FAILURE;
        } else {
            throw new SecurityException("Invalid request url.");
        }

        putContractArbitration(s, page, model);

        return "tasks/contract/arbitrations_pending";
    }

    @RequestMapping("/arbitrations/pending/{id}/detail/")
    public String arbitrationDetail(@PathVariable String id, ModelMap model) {
        TOrderArbitration ar = contractArbitrationService.query(id);
        if (ar.getStatus() != ContractInfo.ContractArbitrationStatus.REQUEST) {
            //throw new SecurityException("Invalid status, should reach here.");
            //return "tasks/contract/arbitration_detail";
        	if(!StringUtils.isEmpty(ar.getDealer())){
        		User u = sUserService.getUser(NumberUtils.toInt(ar.getDealer()));
        		ar.setDealer(u.getRealName());
        	}
        }

        TOrderOperations op = contractOperationService.query(ar.getLid());
        TOrderInfo order = contractInfoService.query(op.getOid());
        OrderAllInfor request = orderFindSerivce.queryInfoById(order.getFid(), null);
        
        List<TOrderDisPrice> res = contractDisPriceService.queryForList(op.getOid());
        TOrderDisPrice price = CollectionUtils.isEmpty(res) ? null : res.get(0);
        TOrderDisPrice arbitrationPrice = null;
        if(ar.getStatus() != ContractInfo.ContractArbitrationStatus.REQUEST){
        	arbitrationPrice = res.get(res.size()-1);
        }
        
        TCompanyInfo buyerCompany = companyInfoService.queryAuthCmpInfo(order.getBuyerid());
        TCompanyInfo sellerCompany = companyInfoService.queryAuthCmpInfo(order.getSellerid());
        TUser q = new TUser();
        q.setCid(buyerCompany.getId());
        TUser buyer = userService.query(q);
        q.setCid(sellerCompany.getId());
        TUser seller = userService.query(q);

        TPassbookPay buyerGuaranty = passPayService.getGuarantyToGelationRecord(
                order.getId(), buyerCompany.getId());
        TPassbookPay sellerGuaranty = passPayService.getGuarantyToGelationRecord(
                order.getId(), sellerCompany.getId());

        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("buyerCompany", buyerCompany);
        model.addAttribute("sellerCompany", sellerCompany);
        model.addAttribute("arbitration", ar);
        model.addAttribute("contract", order);
        model.addAttribute("order_request", request);
        model.addAttribute("price", price);
        model.addAttribute("arPrice", arbitrationPrice);
        model.addAttribute("buyerGuaranty", buyerGuaranty);
        model.addAttribute("sellerGuaranty", sellerGuaranty);

        if (ar.getStatus() == ContractInfo.ContractArbitrationStatus.REQUEST) {
            return "tasks/contract/arbitration_form";
        } else {
            return "tasks/contract/arbitration_detail";
        }
    }


    @RequestMapping(value="/arbitrations/pending/complete/", method=RequestMethod.POST)
    public String arbitrationComplete(String id, double arbitrationAmount,
                                      double arbitrationPrice, String note, ModelMap model) throws ServiceException {
        //boolean isTrade = (arbitrationAmount * arbitrationPrice) != 0;
        //iContractService.contractArbitractionProcess(isTrade, id, getCurrentUser(), arbitrationAmount, arbitrationPrice, note);
        //createServiceLog(id, note, ServiceLogType.ContractArbitration);
        //return "redirect:/tasks/contract/arbitrations/pending/1/?status=success";
        return arbitrationComplete(id, arbitrationAmount, arbitrationPrice, 0, note, model);
    }
    
    @RequestMapping(value="/arbitrations/pending/completeEx/", method=RequestMethod.POST)
    public String arbitrationComplete(String id, double arbitrationAmount,
            double arbitrationPrice, double amount, String note, ModelMap model) throws ServiceException {
    	boolean isTrade = (arbitrationAmount * arbitrationPrice * amount) != 0;
        iContractService.contractArbitractionProcess(isTrade, id, getCurrentUser(), arbitrationAmount, arbitrationPrice, amount, note);
        createServiceLog(id, note, ServiceLogType.ContractArbitration);
    	return "redirect:/tasks/contract/arbitrations/pending/1/?status=success";
    }
    
    @RequestMapping(value = "/arbitrations/finished/{page}/", method = {RequestMethod.POST,RequestMethod.GET})
    public String arbitrationFinished(@PathVariable int page, ModelMap model, HttpServletRequest request) {
    	String contractType = request.getParameter("contractType");
    	QueryContext<TaskArbitrationInfo> ctx = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageIndex(page);
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        ctx.setPage(pm);
        //ctx.addParameter("status", ContractStatus.FINISHED);
        ctx.addParameter("taskType", TaskType.ContractArbitrate);
        if(StringUtils.equalsIgnoreCase("2", contractType)){
        	ctx.addParameter("generator", getCurrentUser().getId());
        }
        ctx = iContractService.getContractArbitrationList(ctx);
        model.addAttribute("arbitrationContractList", ctx.getQueryResult().getResult());
        calculatePagingData(model, "/tasks/contract/arbitrations/finished/{}/"+(StringUtils.isNotEmpty(contractType) ? "?contractType="+contractType : ""), page, pm.getTotalPage() * pm.getPageSize());
    	
    	return "tasks/contract/arbitrations_finished_list";
    }

    private OrderFindAllBean composeOrderObject(ContractForm form, TOrderFind formOrder, TOrderProductInfo orderProduct,
                               TOrderAddress address, HttpServletRequest request) throws ServletRequestBindingException {
        OrderFindAllBean order = new OrderFindAllBean();

        TOrderFind of = null;
        if (form.getTask() != null) {
            of = orderFindSerivce.query(form.getTask().getObjectId());
            formOrder.setId(of.getId());
            BeanUtils.copyProperties(formOrder, of, "fid", "type", "cid");
        } else {
            of = formOrder;
        }

        String unitType = ServletRequestUtils.getStringParameter(request, "unit");
        ProductInfo.UnitEnum unit = ProductInfo.UnitEnum.enumOf(unitType);
        of.setUnit(unit);
        orderProduct.setUnit(unit);

        address.setCreatime(new Date());
        address.setCrater(getCurrentUser().getUserName());

        of.setArea(address.getAreacode());

        order.setOfBean(of);
        order.setOaBean(address);
        order.setOpiBean(orderProduct);

        order.setPpcList(ProductUtils.collectProductProperties(request));
        return order;
    }

    private double getAvailableGuaranty(String companyId) {
        TPassbookInfo i = passPayService.getPurseAccountInfo(companyId, PurseInfo.PurseType.GUARANTY);
        return i.getAmount();
    }

    private boolean hasEnoughGuaranty(String cid) {
        return contractInfoService.checkCashGuarantyEnough(cid);
    }

    private void putContractArbitration(
            ContractInfo.ContractArbitrationStatus status, int page, ModelMap model) {

        QueryContext<ContractArbitrationBean> qc = new QueryContext<>();
        ContractArbitrationBean q = new ContractArbitrationBean();
        q.setStatus(status);
        qc.setBeanParameter(q);
        PageModel pm = new PageModel();
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        pm.setPageIndex(page);
        qc.setPage(pm);
        contractArbitrationService.getContractArbitrationInfoListForPagination(qc);
        model.addAttribute("arbitrations", qc.getQueryResult().getResult());
        calculatePagingData(model, "/tasks/contract/arbitrations/{}/", page, qc.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
    }

    public static class MatchCandidate {

        private Object company;
        private double guaranty;
        private CompanyInfo.CompanyType ctype;
        private String phone;


        public MatchCandidate(Object company, double guaranty, CompanyInfo.CompanyType type, String phone) {
            this.company = company;
            this.guaranty = guaranty;
            this.phone = phone;
            this.ctype = type;
        }

        public Object getCompany() {
            return company;
        }

        public String getPhone() {
            return phone;
        }

        public double getGuaranty() {
            return guaranty;
        }

        public CompanyInfo.CompanyType getCtype() {
            return ctype;
        }
    }

}
