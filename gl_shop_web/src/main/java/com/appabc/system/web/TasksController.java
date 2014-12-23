package com.appabc.system.web;

import com.appabc.bean.enums.AuthRecordInfo;
import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.enums.MsgInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyShippingService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.system.Context;
import com.appabc.datas.system.Task;
import com.appabc.datas.system.TaskService;
import com.appabc.datas.system.TaskType;
import com.appabc.datas.system.User;
import com.appabc.tools.bean.MessageInfoBean;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.SystemMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zouxifeng on 11/24/14.
 */
@Controller
@RequestMapping("/tasks")
public class TasksController extends AbstractListBaseController {

    @Autowired
    private IUploadImagesService uploadImagesService;

    @Autowired
    private IAuthRecordService authRecordService;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private ICompanyAuthService companyAuthService;

    @Autowired
    private ICompanyShippingService companyShippingService;

    @Autowired
    private ICompanyPersonalService companyPersonalService;

    @Autowired
    private MessageSendManager messageSendManager;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/tasks/editor/verify_info_audit/";
    }

    @RequestMapping("/editor/verify_info_audit/{page}/")
    public String verifyInfoList(@PathVariable int page, ModelMap model) {
        if (page < 1) {
            page = 1;
        }

        Context<Task> ctx = new Context<>();
        ctx.setStart(calculateStartRow(page, DEFAULT_PAGE_SIZE));
        ctx.setPageSize(DEFAULT_PAGE_SIZE);
        ctx = taskService.queryForUnfinished(ctx, TaskType.VerifyInfo);
        model.addAttribute("tasks", ctx.getResult());
        calculatePagingData(model, "/tasks/editor/verify_info_audit/{}/", page, ctx.getTotalRows());
        return "tasks/editor_verify_info_audit";
    }

    @RequestMapping("/editor/verify_info_audit/detail/{id}/")
    public String verifyInfoAudit(@PathVariable int id, ModelMap model) {
        Task task = taskService.getVerifyInfoTask(id);
        TAuthRecord ar = (TAuthRecord) task.getTaskObject();
        List<TUploadImages> images = uploadImagesService.getListByOidAndOtype(ar.getId(), String.valueOf(AuthRecordInfo.AuthRecordType.AUTH_RECORD_TYPE_COMPANY.getVal()));
        model.addAttribute("task", task);
        model.addAttribute("auth_images", images);
        return "tasks/editor_verify_info_audit_detail";
    }

    @RequestMapping(value="/editor/verify_info_audit/detail/{id}/{action}/", method = RequestMethod.POST)
    public String verifyInfoAuditAction(HttpServletRequest request, @PathVariable int id, @PathVariable String action, ModelMap model) {
        // If action not equal "pass" or "fail", IllegalArgumentException will be raised.
        ActionResult actionResult = ActionResult.valueOf(action.toUpperCase());
        User user = getCurrentUser();
        Task t = taskService.getVerifyInfoTask(id);
        t.setOwner(user);

        switch (actionResult) {
            case FAIL:
                auditFail(t, request);
                break;
            case PASS:
                auditPass(t, request);
                break;
        }

        TAuthRecord authResult = (TAuthRecord) t.getTaskObject();
        authResult.setAuthresult(authResult.getAuthstatus().getText());
        authResult.setAuthor(user.getUserName());
        authResult.setAuthdate(new Date());
        authRecordService.modify(authResult);

        taskService.completeTask(t);

        return "redirect:/tasks/editor/verify_info_audit/1/";
    }

    private void auditPass(Task t, HttpServletRequest request) {
        TAuthRecord ar = (TAuthRecord) t.getTaskObject();
        ar.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_YES);

        switch (t.getCompany().getCtype()) {
            case COMPANY_TYPE_ENTERPRISE:
                updateCompany(t, request);
                break;
            case COMPANY_TYPE_SHIP:
                updateShip(t, request);
                break;
            case COMPANY_TYPE_PERSONAL:
                updatePerson(t, request);
                break;
        }

       String user = getCurrentUser().getUserName();

       TCompanyInfo c = t.getCompany();
       c.setAuthstatus(CompanyInfo.CompanyAuthStatus.AUTH_STATUS_YES);
       c.setUpdatedate(new Date());
       c.setUpdater(user);
       this.companyInfoService.modify(c);

        MessageInfoBean mi = new  MessageInfoBean(MsgInfo.MsgBusinessType.BUSINESS_TYPE_COMPANY_AUTH,
                t.getObjectId(), c.getId(), SystemMessageContent.getMsgContentOfCompanyAuthYes());
        mi.setSendPushMsg(true);
        mi.setSendSystemMsg(true);
        messageSendManager.msgSend(mi);
    }

    private void auditFail(Task t, HttpServletRequest request) {
        TAuthRecord ar = (TAuthRecord) t.getTaskObject();
        ar.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_NO);
    }

    private void updateCompany(Task t, HttpServletRequest request) {
        TCompanyAuth ca = new TCompanyAuth();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(ca);
        binder.bind(request);

        // TODO: need to fill the content with the form.
        ca.setAuthid(Integer.valueOf(t.getObjectId()));
        ca.setAddress("深圳南山");
        ca.setCname("XX公司"+ Calendar.getInstance().getTime().toLocaleString());
        ca.setCratedate(Calendar.getInstance().getTime());
        ca.setLperson("法人李四");
        ca.setOrgid("oggogg111");
        ca.setRdate("2005-2-2");

        this.companyAuthService.add(ca);

        TCompanyInfo c = t.getCompany();
        c.setCname(ca.getCname());
    }

    private void updateShip(Task t, HttpServletRequest request) {
        TCompanyShipping cs = new TCompanyShipping();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(cs);
        binder.bind(request);

        // TODO: need to fill the content with the form.
        cs.setAuthid(Integer.valueOf(t.getObjectId()));
        cs.setPregistry("船籍港001");
        cs.setSbusinesser("船老大");
        cs.setScreatetime("2009-01-13");
        cs.setSdeep(5.3f);
        cs.setSlength(22.3f);
        cs.setSload(5000f);
        cs.setSmateriall(3.3f);
        cs.setSname("货轮"+Calendar.getInstance().getTime().toLocaleString());
        cs.setSno("DJ00334");
        cs.setSorg("深圳南山素菜批发市场检");
        cs.setSover(6.6f);
        cs.setSowner("船老大02");
        cs.setStotal(5020f);
        cs.setSwidth(6.4f);
        cs.setStype("s_0012");

        this.companyShippingService.add(cs);

        TCompanyInfo c = t.getCompany();
        c.setCname(cs.getSname());
    }

    private void updatePerson(Task t, HttpServletRequest request) {
        TCompanyPersonal cp = new TCompanyPersonal();

        ServletRequestDataBinder binder = new ServletRequestDataBinder(cp);
        binder.bind(request);

        // TODO: need to fill the content with the form.
        cp.setAuthid(Integer.valueOf(t.getObjectId()));
        cp.setCpname("孙六"+Calendar.getInstance().getTime().toLocaleString());
        cp.setCratedate(Calendar.getInstance().getTime());
        cp.setIdentification("111222333444555666");
        cp.setOrigo("xx公安局");
        cp.setRemark("aabb");
        cp.setSex(1);
        this.companyPersonalService.add(cp);

        TCompanyInfo c = t.getCompany();
        c.setCname(cp.getCpname());
    }
}
