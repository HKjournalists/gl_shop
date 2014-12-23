package com.appabc.system.web.finance;

import com.appabc.bean.enums.AcceptBankInfo;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.system.web.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zouxifeng on 11/17/14.
 */
@Controller
@RequestMapping("/finance/account_audit")
public class AccountAuditController extends FinanceController {

    @Autowired
    protected IAcceptBankService acceptBankService;

    @RequestMapping("/pending/{page}/")
    public String accountAuditPending(@PathVariable Integer page, ModelMap model) {
        if (page == null || page < 1) {
            model.clear();
            return "redirect:/finance/account_audit/pending/1/";
        }

        TAcceptBank qab = new TAcceptBank();
        qab.setAuthstatus(AcceptBankInfo.AcceptAuthStatus.AUTH_STATUS_CHECK_ING);
        Map<String, Object> params = new HashMap<>();
        params.put("authstatus", AcceptBankInfo.AcceptAuthStatus.AUTH_STATUS_CHECK_ING);

        QueryContext<TAcceptBank> q = new QueryContext<>();
        q.setBeanParameter(qab);
        q.setParameters(params);
        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        q.setPage(paging);
        QueryContext<TAcceptBank> result = acceptBankService.queryListForPagination(q);
        model.addAttribute("bankAccounts", result.getQueryResult().getResult());
        calculatePagingData(model, "/finance/account_audit/pending/{}/", page, result.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        return "finance/account_audit_pending";
    }

    @RequestMapping("/finished/{page}/")
    public String accountAuditFinished(@PathVariable Integer page, ModelMap model) {
        if (page == null || page < 1) {
            model.clear();
            return "redirect:/finance/account_audit/finished/1/";
        }

        QueryContext<TAcceptBank> q = new QueryContext<>();
        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        q.setPage(paging);
        QueryContext<TAcceptBank> result = acceptBankService.queryListForAuthFinished(q);
        model.addAttribute("bankAccounts", result.getQueryResult().getResult());
        calculatePagingData(model, "/finance/account_audit/finished/{}/", page, result.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        return "finance/account_audit_finished";
    }

    @RequestMapping("/pending/{accountId}/audit/")
    public String audit(@PathVariable String accountId, ModelMap model) {
        model.addAttribute("account", acceptBankService.query(accountId));
        return "finance/account_audit";
    }

    @RequestMapping(value="/pending/{accountId}/audit/{action}", method=RequestMethod.POST)
    public String doAudit(@PathVariable String accountId, @PathVariable String action, HttpServletRequest req, ModelMap model) {
        ActionResult result = ActionResult.valueOf(action.toUpperCase());
        model.clear();
        TAcceptBank account = acceptBankService.query(accountId);
        String auditor = SecurityContextHolder.getContext().getAuthentication().getName();
        if (result == ActionResult.PASS) {
            acceptBankService.authPass(account, auditor);
            return "redirect:/finance/account_audit/pending/1/";
        } else if (result == ActionResult.FAIL) {
            return auditFail(account, req, model);
        } else {
            throw new IllegalArgumentException("Invalid action for " + action + ".");
        }
    }

    @RequestMapping("/finished/detail/{accountId}/")
    public String auditDetail(@PathVariable String accountId, ModelMap model) {
        model.addAttribute("account", acceptBankService.query(accountId));
        return "finance/account_audit_detail";
    }

    private String auditFail(TAcceptBank account, HttpServletRequest req, ModelMap model) {
        try {
            String remark = ServletRequestUtils.getRequiredStringParameter(req, "remark");
            if (StringUtils.hasText(remark)) {
                String auditor = SecurityContextHolder.getContext().getAuthentication().getName();
                account.setRemark(remark);
                acceptBankService.authFail(account, auditor);
                return "redirect:/finance/account_audit/pending/1/";
            } else {
                throw new IllegalArgumentException("Account [" + account.getId() + "] audit failed, but no remark content provided.");
            }
        } catch (ServletRequestBindingException e) {
            logger.error("Audit fail without remark content.", e);
            throw new IllegalArgumentException("Account [" + account.getId() + "] audit failed, but no remark content provided.");
        }
    }

}
