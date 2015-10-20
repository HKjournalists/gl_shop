package com.appabc.cms.web.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.CompanyAllInfo;
import com.appabc.bean.bo.CompanyEvaluationInfo;
import com.appabc.bean.bo.ContractAllInfoBean;
import com.appabc.bean.enums.ContractInfo;
import com.appabc.bean.enums.OrderFindInfo;
import com.appabc.bean.enums.PurseInfo;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAddress;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TUser;
import com.appabc.cms.web.AbstractListBaseController;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.dao.ServiceLogDao;
import com.appabc.datas.cms.vo.ServiceLogType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAddressService;
import com.appabc.datas.service.company.ICompanyContactService;
import com.appabc.datas.service.company.ICompanyEvaluationService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.system.ISystemLogService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.bean.TPassbookPay;
import com.appabc.pay.service.IPassPayService;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Nov 3, 2014 7:50:43 PM
 */
@Controller
@RequestMapping("/company/{cid}")
public class CompanyInfoController extends AbstractListBaseController {

	@Autowired
	private ICompanyInfoService companyInfoService;

	@Autowired
	private ICompanyContactService companyContactService;

    @Autowired
    private ICompanyAddressService companyAddressService;

	@Autowired
	private IPassPayService passPayService;

    @Autowired
    private IAuthRecordService authRecordService;

    @Autowired
    private IUploadImagesService uploadImagesService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICompanyEvaluationService companyEvaluationService;
    
    @Autowired
    private IContractInfoService iContractInfoService;

    @Autowired
    private ServiceLogDao serviceLogDao;

    @Autowired
    private IContractInfoService contractInfoService;

    @Autowired
    private IOrderFindService orderFindService;

    @Autowired
    private IUserService customerService;
    
    @Autowired
    private IContractOperationService contractOperationService;
    
    @Autowired
    private ISystemLogService systemLogService;

	@ModelAttribute
	private void loadCompanyInfo(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		CompanyAllInfo companyInfo = companyInfoService.queryAuthCompanyInfo(cid, null);
		model.addAttribute("company", companyInfo);
        TUser u = userService.getUserByCid(cid);
        model.addAttribute("user", u);
        
        // 用户最后一次登录时间
        model.addAttribute("lastLoginTime", systemLogService.queryLastLoginTimeByUser(u.getUsername()));
        
    }

    @RequestMapping("/info/load/")
    public @ResponseBody Map<String, Object> ajaxCompanyInfo(ModelMap model) {
        CompanyAllInfo c = (CompanyAllInfo) model.get("company");
        double guarantyAmount = getAvailableGuaranty(c.getId());
        Map<String, Object> company = new HashMap<>();
        company.put("id", c.getId());
        company.put("cname", c.getCname());
        company.put("ctype", c.getCtype());
        company.put("guaranty", guarantyAmount);
        company.put("isGuaranty", iContractInfoService.checkCashGuarantyEnough(c.getId()));
        TUser user = customerService.getUserByCid(c.getId());
        company.put("phone", user.getPhone());
        return company;
    }

    @RequestMapping("/addresses/load/")
    public @ResponseBody List<TCompanyAddress> ajaxCompanyAddresses(@PathVariable String cid) {
        TCompanyAddress qc = new TCompanyAddress();
        qc.setCid(cid);
        return companyAddressService.queryForListHaveImgs(qc);
    }


	@RequestMapping("/info/")
	public String info(@PathVariable String cid, Model model) {
        TCompanyContact qContact = new TCompanyContact();
        qContact.setCid(cid);
		List<TCompanyContact> contacts = companyContactService.queryForList(qContact);
		model.addAttribute("contacts", contacts);
		CompanyEvaluationInfo cei = new CompanyEvaluationInfo();
		cei.setCid(cid);
        model.addAttribute("company_evaluations", companyEvaluationService.queryEvaluationListByCompany(cei));
		return "company/basic_info";
	}

	@RequestMapping("/auth/")
	public String auth(@PathVariable String cid, Model model) {
        TPassbookInfo pbi = passPayService.getPurseAccountInfo(cid,
                PurseInfo.PurseType.GUARANTY);
        CompanyAllInfo company = companyInfoService.queryAuthCompanyInfo(cid, null);
        model.addAttribute("company_full_info", company);

        TAuthRecord ar = new TAuthRecord();
        if(StringUtils.isNotEmpty(company.getAuthid())){
        	ar = authRecordService.query(company.getAuthid());
        }
        model.addAttribute("auth", ar);
        
        model.addAttribute("guaranty", pbi.getAmount());
        model.addAttribute("service_logs",
        		serviceLogDao.queryByCompanyIdAndType(cid, ServiceLogType.VerifyAuthInfo));
        model.addAttribute("authLogs", authRecordService.queryAuthLogListByCid(cid));
		return "company/auth";
	}

	private void walletBalance(String cid, ModelMap model) {
		TPassbookInfo balance = passPayService.getPurseAccountInfo(cid, PurseType.DEPOSIT);
		TPassbookInfo guaranty = passPayService.getPurseAccountInfo(cid, PurseType.GUARANTY);
		model.addAttribute("balance", balance);
		model.addAttribute("guaranty", guaranty);
	}

	@RequestMapping("/wallet/trans/{page}/")
	public String walletTrans(@PathVariable String cid, @PathVariable Integer page,
                              String startDate, String endDate, String accountType,
                              Integer actionType, ModelMap model) {
        if (page == null || page < 0) {
            page = 1;
        }
		walletBalance(cid, model);
        Date start = null;
        if (!StringUtils.isEmpty(startDate)) {
            start = DateUtil.strToDate(startDate + " 00:00:00",
                                       DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            start = cal.getTime();
        }

        Date end = null;
        if (!StringUtils.isEmpty(endDate)) {
            end = DateUtil.strToDate(endDate + " 23:59:59",
                                     DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            end = cal.getTime();
        }

        QueryContext.DateQueryEntry dateQueryEntry =
                new QueryContext.DateQueryEntry("paytime", start, end);
        QueryContext<TPassbookPay> qc = new QueryContext<>();
        qc.addParameter("cid", cid);
        qc.addParameter(dateQueryEntry.getPropertyName(), dateQueryEntry);

        if (accountType == null) {
            accountType = "-1";
        }
        if (!"-1".equals(accountType)) {
            qc.addParameter("passType", PurseType.enumOf(accountType).getVal());
        }

        if (actionType == null) {
            actionType = -1;
        }
        if (actionType != -1) {
            qc.addParameter("direction", PurseInfo.PayDirection.enumOf(actionType).getVal());
        }
        PageModel pm = new PageModel();
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        pm.setPageIndex(page);
        qc.setPage(pm);
        qc = passPayService.payRecordList(qc);

        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        model.addAttribute("accountType", accountType);
        model.addAttribute("actionType", actionType);

        StringBuilder urlPattern = new StringBuilder(100);
        urlPattern.append("/company/" + cid + "/wallet/trans/{}/?");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        urlPattern.append("startDate=" + df.format(start));
        urlPattern.append("&endDate=" + df.format(end));
        urlPattern.append("&accountType=" + accountType);
        urlPattern.append("&actionType=" + actionType);
        calculatePagingData(model, urlPattern.toString(), page,
                qc.getQueryResult().getTotalSize());
        model.addAttribute("transactions", qc.getQueryResult().getResult());

		return "company/wallet_trans";
	}

	/**
	 * 合同历史交易
	 * @param cid
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/contracts/history/{page}/")
	public String contractHistory(@PathVariable String cid, @PathVariable Integer page,
                                  ModelMap model) {
        contractsList(cid, ContractInfo.ContractWebCmsTradeType.HISTORY, page,
                "../{}/", model);
		return "company/contracts_history";
	}

    /**
     * 合同当前交易
     * @param cid
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/contracts/progressing/{page}/")
	public String contractInProgress(@PathVariable String cid, @PathVariable Integer page,
                                     ModelMap model) {
        contractsList(cid, ContractInfo.ContractWebCmsTradeType.PRESENT, page,
                "../{}/", model);
		return "company/contracts_progressing";
	}

	@RequestMapping("/order_requests/history/{page}/")
	public String orderRequestHistory(@PathVariable String cid, @PathVariable Integer page,
                                  ModelMap model) {
        orderRequestList(cid,
                OrderFindInfo.OrderOverallStatusEnum.ORDER_OVERALL_STATUS_INVALID,
                page,
                "../{}/", model);
		return "company/order_requests_history";
	}

    @RequestMapping("/order_requests/progressing/{page}/")
	public String orderRequestProgressing(@PathVariable String cid, @PathVariable Integer page,
                                  ModelMap model) {
        orderRequestList(cid,
                OrderFindInfo.OrderOverallStatusEnum.ORDER_OVERALL_STATUS_EFFECTIVE,
                page,
                "../{}/", model);
		return "company/order_requests_progressing";
	}

    private double getAvailableGuaranty(String companyId) {
        TPassbookInfo i = passPayService.getPurseAccountInfo(companyId, PurseInfo.PurseType.GUARANTY);
        return i.getAmount();
    }

    private void contractsList(String cid, ContractInfo.ContractWebCmsTradeType listType,
                               Integer page, String pagingUrlTemplate, ModelMap model) {
        if (page == null || page < 1) {
            page = 1;
        }

        QueryContext<TOrderInfo> ctx = new QueryContext<>();
        ctx.addParameter("cid", cid);
        ctx.addParameter("type", listType.getVal());
        ctx.getPage().setPageIndex(page);
        ctx.getPage().setPageSize(DEFAULT_PAGE_SIZE);
        ctx = contractInfoService.queryContractListOfMineForPaginationToWebCms(ctx);
        calculatePagingData(model, pagingUrlTemplate, page,
                ctx.getQueryResult().getTotalSize());
        
        List<ContractAllInfoBean> caibList = new ArrayList<ContractAllInfoBean>();
        ContractAllInfoBean caib = null;
        
    	for( TOrderInfo oi : ctx.getQueryResult().getResult()){
    		caib = new ContractAllInfoBean();
    		// 计算结束时间
    		if(ContractInfo.ContractWebCmsTradeType.HISTORY == listType) { 
    			caib.setStatusRemark(getDatePoor(oi.getUpdatetime()));
    		}
    		
    		// 操作记录处理
    		TOrderOperations entity = new TOrderOperations();
    		entity.setOid(oi.getId());
    		List<TOrderOperations> opList = contractOperationService.queryForList(entity);
    		caib.setOpList(opList);
    		caib.setOrderInfo(oi);
    		
    		caibList.add(caib);
    	}
        
        model.addAttribute("caibList", caibList);
    }

    private void orderRequestList(String cid, OrderFindInfo.OrderOverallStatusEnum listType,
                               Integer page, String pagingUrlTemplate, ModelMap model) {
        if (page == null || page < 1) {
            page = 1;
        }

        QueryContext<TOrderFind> ctx = new QueryContext<>();
        ctx.addParameter("overallstatus", listType.getVal());
        ctx.getPage().setPageIndex(page);
        ctx.getPage().setPageSize(DEFAULT_PAGE_SIZE);
        try {
            ctx = orderFindService.queryMyListForPagination(ctx, cid);
        } catch (ServiceException e) {
            logger.error("Error occured.", e);
        }
        calculatePagingData(model, pagingUrlTemplate, page,
                ctx.getQueryResult().getTotalSize());
        model.addAttribute("ofList", ctx.getQueryResult().getResult());
    }
    
    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date startDate) {
    	Date nowDate = Calendar.getInstance().getTime();
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate.getTime() - startDate.getTime() ;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }
    
}
