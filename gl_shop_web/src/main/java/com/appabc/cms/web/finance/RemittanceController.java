package com.appabc.cms.web.finance;

import com.appabc.bean.enums.AuthRecordInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.PurseInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyContact;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TUser;
import com.appabc.cms.web.AbstractBaseController;
import com.appabc.cms.web.AbstractListBaseController;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.dao.ServiceLogDao;
import com.appabc.datas.cms.service.FinanceService;
import com.appabc.datas.cms.vo.Context;
import com.appabc.datas.cms.vo.Remittance;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyContactService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.pay.bean.TAcceptBank;
import com.appabc.pay.bean.TOfflinePay;
import com.appabc.pay.bean.TPassbookInfo;
import com.appabc.pay.service.IPassPayService;
import com.appabc.pay.service.local.IOfflinePayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zouxifeng on 12/4/14.
 */
@Controller
@RequestMapping("/finance/remittance")
public class RemittanceController extends FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private ICompanyInfoService companyInfoService;
    @Autowired
    private IAcceptBankService acceptBankService;
    @Autowired
    private ICompanyContactService contactService;
    @Autowired
    private ICompanyPersonalService companyPersonalService;
    @Autowired
    private IAuthRecordService authRecordService;

    @Autowired
    private IContractInfoService contractInfoService;
    @Autowired
    private IOfflinePayService offlinePayService;
    @Autowired
    private IPassPayService passPayService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ServiceLogDao serviceLogDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
    }

    @RequestMapping(value="/input/", method = RequestMethod.GET)
    public String input() {
        return "finance/remittance_input";
    }

    @RequestMapping(value="/input/", method=RequestMethod.POST)
    public String doInput(@ModelAttribute("remittance") Remittance remittance,
                          BindingResult result, ModelMap model) {
        // TODO: need to validate input
        Remittance r = new Remittance();
        BeanUtils.copyProperties(remittance, r);
        r.setStatus(Remittance.Status.PROCESSING);
        r.setCreator(AbstractBaseController.getCurrentUser());
        r.setCreateTime(new Date());
        financeService.inputRemittance(r);


        return "redirect:/finance/remittance/input/";
    }

    @RequestMapping("/process/{page}/")
    public String waitingProcessList(@PathVariable int page, ModelMap model) {
        remittanceList(page, Remittance.Status.PROCESSING, "/finance/remittance/process/{}/", model);
        return "finance/remittance_process_list";
    }

    @RequestMapping("/process/detail/{id}/")
    public String process(@PathVariable int id, ModelMap model) {
        Remittance r = financeService.getRemittance(id);
        model.addAttribute("remittance", r);
        showMoreInfoOfRemit(model, r);
        return "finance/remittance_detail";
    }

    @RequestMapping("/process/detail/{id}/find_targets/")
    public String findPossibleTarget(@PathVariable int id, ModelMap model) {
        Remittance r = financeService.getRemittance(id);
        model.addAttribute("remittance", r);
        showMoreInfoOfRemit(model, r);

        Set<TCompanyInfo> possibleTargets = new HashSet<>();

        // Find by remitter bank account
        if (StringUtils.hasText(r.getRemitterAccount())) {
            TAcceptBank qab = new TAcceptBank();
            qab.setBankcard(r.getRemitterAccount());
            findCompanyByAcceptBank(qab, possibleTargets);
        }

        // Find by target mobile
        if (StringUtils.hasText(r.getMobile())) {
            String mobile = r.getMobile();
            TUser qu = new TUser();
            qu.setPhone(mobile);
            TUser u = userService.query(qu);
            if (u != null) {
                TCompanyInfo q = new TCompanyInfo();
                q.setId(u.getCid());
                findCompany(q, possibleTargets);
            }

            TCompanyInfo q = new TCompanyInfo();
            q.setCphone(mobile);
            findCompany(q, possibleTargets);

            TCompanyContact qc = new TCompanyContact();
            qc.setCphone(mobile);
            findCompanyByContact(qc, possibleTargets);
        }

        if (StringUtils.hasText(r.getCompany())) {
            TCompanyInfo q = new TCompanyInfo();
            q.setCname(r.getCompany().trim());
            findCompany(q, possibleTargets);
        }

        // Find by remitter name.
        if (StringUtils.hasText(r.getRemitter())) {
            String remitter = r.getRemitter().trim();
            TCompanyContact qContact = new TCompanyContact();
            qContact.setCname(remitter);
            findCompanyByContact(qContact, possibleTargets);

            TCompanyPersonal qCompanyPerson = new TCompanyPersonal();
            qCompanyPerson.setCpname(remitter);
            List<TCompanyPersonal> companyPersonals = companyPersonalService.queryForList(qCompanyPerson);
            if (!CollectionUtils.isEmpty(companyPersonals)) {
                for (TCompanyPersonal person : companyPersonals) {
                    TAuthRecord authRecord = authRecordService.query(person.getAuthid());
                    if (authRecord.getAuthstatus() == AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_YES) {
                        TCompanyInfo qCompany = new TCompanyInfo();
                        qCompany.setId(authRecord.getCid());
                        findCompany(qCompany, possibleTargets);
                    }
                }
            }

            TAcceptBank qAcceptBank = new TAcceptBank();
            qAcceptBank.setCarduser(remitter);
            findCompanyByAcceptBank(qAcceptBank, possibleTargets);
        }

        if (CollectionUtils.isEmpty(possibleTargets)) {
            financeService.unableSolveRemittance(r);
            return "finance/remittance_unsolvable";
        }

        List<RemittanceDTO> targets = new LinkedList<>();
        Set<String> targetIds = new HashSet<>();
        for (TCompanyInfo c : possibleTargets) {
            if (targetIds.contains(c.getId())) {
                continue;
            }
            targetIds.add(c.getId());
            TPassbookInfo balance = passPayService.getPurseAccountInfo(c.getId(), PurseInfo.PurseType.DEPOSIT);
            TPassbookInfo guaranty = passPayService.getPurseAccountInfo(c.getId(), PurseInfo.PurseType.GUARANTY);
            Map<String, Object> params = new HashMap<>();
            params.put("creater", c.getId());
            QueryContext<TOfflinePay> ctx = new QueryContext<>();
            ctx.setParameters(params);
            ctx.setOrderColumn("createtime");
            ctx.setOrder("desc");
            PageModel pageModel = new PageModel();
            pageModel.setPageIndex(1);
            pageModel.setPageIndex(1);
            ctx.setPage(pageModel);
            QueryContext<TOfflinePay> result = offlinePayService.queryListForPagination(ctx);
            Date requestRemitTime = null;
            List<TOfflinePay> requestRemits = result.getQueryResult().getResult();
            if (!CollectionUtils.isEmpty(requestRemits)) {
                requestRemitTime = requestRemits.get(0).getCreatetime();
            }
            targets.add(new RemittanceDTO(r, c, balance, guaranty, requestRemitTime));
        }

        model.addAttribute("possibleTargest", targets);

        return "finance/remittance_process";
    }

    @RequestMapping(value = "/process/detail/{id}/", method = RequestMethod.POST)
    public String doProcess(@PathVariable int id, String mode, String cid, String walletType, ModelMap model) {
        Remittance.Status status = Remittance.Status.valueOf(mode);
        Remittance r = financeService.getRemittance(id);
        switch (status) {
            case PROCESSING:
                TCompanyInfo c = companyInfoService.query(cid);
                if (c == null) {
                    throw new IllegalArgumentException("Invalid company id");
                }
                r.setTargetCompanyId(cid);
                r.setTargetWalletType(PurseInfo.PurseType.valueOf(walletType));
                financeService.finishProcess(r, getCurrentUser());
                break;
            case UNSOLVABLE:
                r.setProcessor(getCurrentUser());
                financeService.unableSolveRemittance(r);
                break;
        }

        return "redirect:/finance/remittance/process/1/";
    }

    @RequestMapping("/audit/{page}/")
    public String waitingAuditList(@PathVariable int page, ModelMap model) {
        remittanceList(page, Remittance.Status.AUDITING, "/finance/remittance/audit/{}/", model);
        return "finance/remittance_audit_list";
    }

    @RequestMapping("/audit/detail/{id}/")
    public String audit(@PathVariable int id, ModelMap model) {
        Remittance r = financeService.getRemittance(id);
        model.addAttribute("remittance", r);
        model.addAttribute("target", companyInfoService.query(r.getTargetCompanyId()));
        showMoreInfoOfRemit(model, r);
        return "finance/remittance_audit";
    }

    @RequestMapping(value = "/audit/detail/{id}/", method = RequestMethod.POST)
    public String doAudit(@PathVariable int id, String mode, ModelMap model) {
        Remittance.Status status = Remittance.Status.valueOf(mode);
        Remittance r = financeService.getRemittance(id);
        switch (status) {
            case FINISH:
                financeService.finishAudit(r, getCurrentUser());
                break;
            case AUDIT_FAIL:
                financeService.auditFail(r, getCurrentUser());
                break;
        }
        return "redirect:/finance/remittance/audit/1/";
    }

    private void remittanceList(int page, Remittance.Status status,
                                 String pagingUrl, ModelMap model) {
        if (page < 1) {
            page = 1;
        }
        Context<Remittance> ctx = new Context<>();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("status", status.getValue());
        ctx.setStart(calculateStartRow(page, AbstractListBaseController.DEFAULT_PAGE_SIZE));
        ctx.setPageSize(AbstractListBaseController.DEFAULT_PAGE_SIZE);
        ctx.setParameters(parameters);
        financeService.queryForRemittance(ctx);
        model.addAttribute("remittances", ctx.getResult());
        calculatePagingData(model, pagingUrl, page, ctx.getTotalRows());
    }

    private void showMoreInfoOfRemit(ModelMap model, Remittance r) {
        if (StringUtils.hasText(r.getMobile()) || StringUtils.hasText(r.getCompany())
            || StringUtils.hasText(r.getRemitter()) || StringUtils.hasText(r.getRemark())) {
            model.addAttribute("show_more", true);
        }
    }

    private void findCompanyByAcceptBank(TAcceptBank qab, Set<TCompanyInfo> possibleTargets) {
        qab.setAuthstatus(AuthRecordStatus.AUTH_STATUS_CHECK_YES);
        List<TAcceptBank> acceptBanks = acceptBankService.queryForList(qab);
        if (!CollectionUtils.isEmpty(acceptBanks)) {
            for (TAcceptBank acceptBank : acceptBanks) {
                if (acceptBank.getAuthstatus() == AuthRecordStatus.AUTH_STATUS_CHECK_YES) {
                    possibleTargets.add(acceptBank.getCompany());
                }
            }
        }
    }

    private void findCompanyByContact(TCompanyContact qc, Set<TCompanyInfo> possibleTargets) {
        List<TCompanyContact> contacts = contactService.queryForList(qc);
        if (!CollectionUtils.isEmpty(contacts)) {
            for (TCompanyContact contact : contacts) {
                TCompanyInfo qCompany = new TCompanyInfo();
                qCompany.setId(contact.getCid());
                findCompany(qCompany, possibleTargets);
            }
        }
    }

    private void findCompany(TCompanyInfo q, Set<TCompanyInfo> possibleTargets) {
        List<TCompanyInfo> companies = companyInfoService.queryForList(q);
        if (!CollectionUtils.isEmpty(companies)) {
            for (TCompanyInfo company : companies) {
                if (company.getAuthstatus() == AuthRecordStatus.AUTH_STATUS_CHECK_YES) {
                    possibleTargets.add(company);
                }
            }
        }
    }

    private static class RemittanceDTO {
        private Remittance remittance;
        private TCompanyInfo company;
        private TPassbookInfo balance;
        private TPassbookInfo guaranty;
        private Date requestRemitTime;

        private RemittanceDTO(Remittance remittance, TCompanyInfo company, TPassbookInfo wallet,
                              TPassbookInfo guaranty, Date requestRemitTime) {
            this.remittance = remittance;
            this.company = company;
            this.balance = wallet;
            this.guaranty = guaranty;
            this.requestRemitTime = requestRemitTime;
        }

        public Remittance getRemittance() {
            return remittance;
        }

        public TCompanyInfo getCompany() {
            return company;
        }

        public TPassbookInfo getBalance() {
            return balance;
        }

        public TPassbookInfo getGuaranty() {
            return guaranty;
        }

        public Date getRequestRemitTime() {
            return requestRemitTime;
        }
    }

}
