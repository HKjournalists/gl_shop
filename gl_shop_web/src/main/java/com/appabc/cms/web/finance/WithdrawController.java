package com.appabc.cms.web.finance;

import com.appabc.bean.enums.PurseInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.dao.ServiceLogDao;
import com.appabc.datas.cms.vo.ServiceLog;
import com.appabc.datas.cms.vo.ServiceLogType;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.bean.TPassbookDrawEx;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IPassbookDrawService;
import com.appabc.tools.service.codes.IPublicCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by zouxifeng on 5/5/15.
 */
@Controller
@RequestMapping("/finance/withdraw")
public class WithdrawController extends FinanceController {

    enum AuditAction {
        SUCCEED, FAIL
    }

    @Autowired
    private IPassbookDrawService passbookDrawService;

    @Autowired
    private IPassPayService passPayService;

    @Autowired
    private IAcceptBankService acceptBankService;

    @Autowired
    private IPublicCodesService publicCodesService;

    @Autowired
    private ServiceLogDao serviceLogDao;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @RequestMapping("/requests/{page}/")
    public String requests(@PathVariable Integer page, String acctType, ModelMap model) {
        return withdrawRequestList(page, acctType, PurseInfo.ExtractStatus.REQUEST,
                "/finance/withdraw/requests/1/", "/finance/withdraw/requests/{}/",
                "finance/withdraw_requests", model);
    }

    @RequestMapping("/cashout/{page}/")
    public String pendingCashout(@PathVariable Integer page, String acctType, ModelMap model) {
        return withdrawRequestList(page, acctType, PurseInfo.ExtractStatus.SUCCESS,
                "/finance/withdraw/cashout/1/", "/finance/withdraw/cashout/{}/",
                "finance/withdraw_cashout_pendings", model);
    }

    @RequestMapping("/requests/detail/{id}/")
    public String requestDetail(@PathVariable String id, ModelMap model) {
        withdrawRequestDetail(id, model);
        return "finance/withdraw_request_detail";
    }

    @RequestMapping(value = "/requests/detail/{id}/audit/{action}/", method = RequestMethod.POST)
    public @ResponseBody String requestAction(@PathVariable String id, @PathVariable String action, String log) {
        TPassbookDraw withdrawRequest = passbookDrawService.query(id);
        boolean result = false;
        switch (AuditAction.valueOf(action.toUpperCase())) {
            case SUCCEED:
                result = true;
                break;
        }

        passPayService.extractCashAudit(id, result, log, getCurrentUser().getUserName());

        ServiceLog svcLog = new ServiceLog();
        svcLog.setContent(log);
        svcLog.setOperator(getCurrentUser());
        svcLog.setObjectId(id);
        svcLog.setType(ServiceLogType.Withdraw);
        svcLog.setCreateTime(new Date());
        serviceLogDao.create(svcLog);

        return "ok";
    }

    private String withdrawRequestList(Integer page, String acctType,
                                       PurseInfo.ExtractStatus requestStatus,
                                       String firstPageUrl, String urlTemplate,
                                       String templateName, ModelMap model) {
       if (page == null || page < 1) {
            return firstPageUrl;
        }

        QueryContext<TPassbookDrawEx> qc = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageIndex(page);
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        qc.setPage(pm);
        Map<String, Object> params = new HashMap<>();
        params.put("status", requestStatus.getVal());
        if (acctType != null) {
            params.put("passtype", PurseInfo.PurseType.valueOf(acctType).getVal());
        }
        qc.setParameters(params);
        qc = passbookDrawService.extractCashRequestListEx(qc);
        model.addAttribute("withdraw_requests", qc.getQueryResult().getResult());
        calculatePagingData(model, urlTemplate, page,
                qc.getPage().getTotalSize());
        return templateName;
    }

    @RequestMapping("/cashout/detail/{id}/")
    public String cashoutPendingDetail(@PathVariable String id, ModelMap model) {
        withdrawRequestDetail(id, model);
        model.addAttribute("service_logs", serviceLogDao.query(id, ServiceLogType.Withdraw));
        return "finance/withdraw_cashout_detail";
    }

    @RequestMapping(value = "/cashout/detail/{id}/audit/{action}/", method = RequestMethod.POST)
    public @ResponseBody String cashoutAction(@PathVariable String id,
                                              @PathVariable String action,
                                              String log) {
        switch (AuditAction.valueOf(action.toUpperCase())) {
            case SUCCEED:
                passPayService.extractCashDeduct(id);
                break;
            case FAIL:
                passPayService.extractCashAudit(id, false, log,
                        getCurrentUser().getUserName());
                break;
        }

        ServiceLog svcLog = new ServiceLog();
        svcLog.setContent(log);
        svcLog.setOperator(getCurrentUser());
        svcLog.setObjectId(id);
        svcLog.setType(ServiceLogType.Withdraw);
        svcLog.setCreateTime(new Date());
        serviceLogDao.create(svcLog);

        return "ok";
    }

    private void withdrawRequestDetail(String id, ModelMap model) {
        QueryContext<TPassbookDrawEx> qc = new QueryContext<>();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        qc.setParameters(params);
        qc = passbookDrawService.extractCashRequestListEx(qc);
        TPassbookDrawEx request = qc.getQueryResult().getResult().get(0);
        model.addAttribute("withdraw_request", request);

        TPassbookInfo guaranty = passPayService.getPurseAccountInfo(request.getCid(),
                PurseInfo.PurseType.GUARANTY);
        TPassbookInfo deposit = passPayService.getPurseAccountInfo(request.getCid(),
                PurseInfo.PurseType.DEPOSIT);
        model.addAttribute("guaranty", guaranty);
        model.addAttribute("deposit", deposit);

        TAcceptBank bank = acceptBankService.query(request.getAid());
        model.addAttribute("bank", bank);

        TPublicCodes qcBank = new TPublicCodes();
        qcBank.setCode("BANK");
        qcBank.setVal(bank.getBanktype());
        qcBank = publicCodesService.query(qcBank);
        model.addAttribute("bank_code", qcBank);

        TCompanyInfo company = companyInfoService.query(request.getCid());
        model.addAttribute("company", company);
    }

}
