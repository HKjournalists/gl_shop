package com.appabc.cms.web.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appabc.bean.bo.AuthCmpInfo;
import com.appabc.bean.enums.AuthRecordInfo;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo;
import com.appabc.bean.enums.CompanyInfo.PersonalAuthSex;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyAuth;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TCompanyPersonal;
import com.appabc.bean.pvo.TCompanyShipping;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductPrice;
import com.appabc.bean.pvo.TProductPriceHope;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.bean.pvo.TUser;
import com.appabc.cms.web.AbstractListBaseController;
import com.appabc.cms.web.ActionResult;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.IAuthRecordService;
import com.appabc.datas.service.company.ICompanyAuthService;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.company.ICompanyPersonalService;
import com.appabc.datas.service.company.ICompanyShippingService;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.product.IProductPriceHopeService;
import com.appabc.datas.service.product.IProductPriceService;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.tools.service.codes.IPublicCodesService;
import com.appabc.tools.utils.MessageSendManager;

/**
 * Created by zouxifeng on 12/11/14.
 */
@Controller
@RequestMapping("/tasks/editor")
public class EditorController extends AbstractListBaseController {

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
    private IPublicCodesService publicCodesService;

    @Autowired
    private IProductPriceService productPriceService;

    @Autowired
    private IProductPriceHopeService productPriceHopeService;

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private TaskService taskService;

    @RequestMapping("/verify_info_audit/{page}/")
    public String verifyInfoList(@PathVariable int page, ModelMap model) {
        if (page < 1) {
            page = 1;
        }

        QueryContext<TAuthRecord> qc = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        pm.setPageIndex(page);
        qc.setPage(pm);
        authRecordService.queryListForPaginationByTypeAndAuthstatus(qc,
                AuthRecordInfo.AuthRecordType.AUTH_RECORD_TYPE_COMPANY,
                AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_ING);

        model.addAttribute("result", qc.getQueryResult().getResult());
        calculatePagingData(model, "/tasks/editor/verify_info_audit/{}/", page, qc.getPage().getTotalSize());
        return "tasks/editor_verify_info_audit";
    }

    @RequestMapping("/verify_info_audit/detail/{id}/")
    public String verifyInfoAudit(@PathVariable String id, ModelMap model) {
        TAuthRecord ar = authRecordService.query(id);
        TCompanyInfo company = companyInfoService.query(ar.getCid());
        TUser user = userService.getUserByCid(company.getId());

        List<TUploadImages> images = uploadImagesService.getListByOidAndOtype(ar.getId(), FileInfo.FileOType.FILE_OTYPE_COMPANY_AUTH.getVal());
        model.addAttribute("auth", ar);
        model.addAttribute("auth_images", images);
        model.addAttribute("company", company);
        model.addAttribute("user", user);
        String infoFragmentName = null;
        switch (company.getCtype()) {
            case COMPANY_TYPE_PERSONAL:
                infoFragmentName = "person";
                break;
            case COMPANY_TYPE_ENTERPRISE:
                infoFragmentName = "company";
                break;
            case COMPANY_TYPE_SHIP:
                infoFragmentName = "ship";
                break;
        }
        model.addAttribute("info_frag_name", infoFragmentName);
        return "tasks/editor_verify_info_audit_detail";
    }
    
    /**
     * 已审核信息查看
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/verify_info_audit/show/{authId}/")
    public String verifyInfoShow(@PathVariable String authId, ModelMap model) {
    	TAuthRecord ar = authRecordService.query(authId);
    	TCompanyInfo company = companyInfoService.queryAuthCmpInfo(ar.getCid());
    	TUser user = userService.getUserByCid(company.getId());
    	
    	List<TUploadImages> images = uploadImagesService.getListByOidAndOtype(ar.getId(), FileInfo.FileOType.FILE_OTYPE_COMPANY_AUTH.getVal());
    	model.addAttribute("auth", ar);
    	model.addAttribute("auth_images", images);
    	model.addAttribute("company", company);
    	model.addAttribute("user", user);
    	
    	TCompanyPersonal personBean = null;
    	TCompanyAuth companyBean = null;
    	TCompanyShipping shipBean = null;
    	
    	switch (company.getCtype()) {
    	case COMPANY_TYPE_PERSONAL:
    		personBean = companyPersonalService.queryByAuthid(Integer.parseInt(authId));
    		break;
    	case COMPANY_TYPE_ENTERPRISE:
    		companyBean = companyAuthService.queryByAuthid(Integer.parseInt(authId));
    		break;
    	case COMPANY_TYPE_SHIP:
    		shipBean = companyShippingService.queryByAuthid(Integer.parseInt(authId));
    		break;
    	}
    	if(personBean == null) {personBean = new TCompanyPersonal();personBean.setSex(PersonalAuthSex.PERSONAL_SEX_M);}
    	if(companyBean == null) {companyBean = new TCompanyAuth();}
    	if(shipBean == null) {shipBean = new TCompanyShipping();}
    	
    	model.addAttribute("personBean", personBean);
    	model.addAttribute("companyBean", companyBean);
    	model.addAttribute("shipBean", shipBean);
    	return "tasks/editor_verify_info_show";
    }

    @RequestMapping(value="/verify_info_audit/detail/{id}/{action}/", method = RequestMethod.POST)
    public String verifyInfoAuditAction(HttpServletRequest request,
                                        @PathVariable String id,
                                        @PathVariable String action,
                                        String remark) throws ServletRequestBindingException, ServiceException {
        // If action not equal "pass" or "fail", IllegalArgumentException will be raised.
        ActionResult actionResult = ActionResult.valueOf(action.toUpperCase());

        AuthCmpInfo authInfo = new AuthCmpInfo();
        authInfo.setAuthid(id);
        authInfo.setAuthor(getCurrentUser().getUserName());
        authInfo.setRemark(remark);

        TAuthRecord ar = authRecordService.query(id);
        TCompanyInfo company = companyInfoService.query(ar.getCid());

        switch (actionResult) {
            case FAIL:
                auditFail(authInfo);
                break;
            case PASS:
                auditPass(authInfo, company.getCtype(), request);
                break;
        }

        authInfo.setAuthresult(authInfo.getAuthStatus().getText());
        authRecordService.authCmp(authInfo);
        
        /****已处理列表**************/
        Date now = new Date();
        
        Task<?> taskBean = new Task<Object>();
        taskBean.setClaimTime(now);
        taskBean.setCompany(company);
        taskBean.setCreateTime(now);
        taskBean.setFinished(true);
        taskBean.setFinishTime(now);
        taskBean.setObjectId(ar.getId());
        taskBean.setOwner(getCurrentUser());
        taskBean.setType(TaskType.VerifyInfo);
        
        taskService.createTask(taskBean);
        

        return "redirect:/tasks/editor/verify_info_audit/1/";
    }

    @RequestMapping("/marketinfo/show/")
    public String marketInfo(ModelMap model) {
        model.addAttribute("riverSection", publicCodesService.queryListByCode("RIVER_SECTION", 0));
        return "tasks/editor_market_info";
    }

    @RequestMapping(value="/marketinfo/update/", method = RequestMethod.POST)
    public ResponseEntity<String> updateMarketInfo(ProductPriceForm form) {
        Date now = new Date();
        ProductPriceForm.ProductPrice[] prices = form.getPrices();
        for (int i = 0; i < prices.length; i++) {
            boolean isUpdate = prices[i].getOid() != null;
            TProductPrice tpp;
            if (isUpdate) {
                tpp = productPriceService.query(prices[i].getOid());
            } else {
                tpp = new TProductPrice();
                tpp.setArea(form.getArea());
                tpp.setPid(prices[i].getPid());
                TProductInfo tpi = productInfoService.query(prices[i].getPid());
                tpp.setUnit(tpi.getUnit());
            }
            tpp.setDatepoint(form.getDatepoint());
            tpp.setPrice(prices[i].getPrice());
            tpp.setUpdater(getCurrentUser().getRealName());
            tpp.setUpdatetime(now);

            if (isUpdate) {
                productPriceService.modify(tpp);
            } else {
                productPriceService.add(tpp);
            }
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping("/marketinfo/forecast/")
    public String marketForecast(ModelMap model) {
        model.addAttribute("riverSection", publicCodesService.queryListByCode("RIVER_SECTION", 0));
        return "tasks/editor_market_forecast";
    }

    @RequestMapping(value = "/marketinfo/forecast/update/", method = RequestMethod.POST)
    public ResponseEntity<String> updateMarketForecast(ProductPriceForecastForm form) {
        Date week1Monday = DateUtil.getFirstDayOfNWeek(form.getDatepoint(), 1);
        Date week1Sunday = DateUtil.getLastDayOfNWeek(form.getDatepoint(), 1);
        Date week2Monday = DateUtil.getFirstDayOfNWeek(form.getDatepoint(), 2);
        Date week2Sunday = DateUtil.getLastDayOfNWeek(form.getDatepoint(), 2);
        Date updateTime = new Date();
        String operator = getCurrentUser().getUserName();
        for (ProductPriceForecastForm.ProductPriceForecast pp : form.getPriceForecasts()) {
            TProductPriceHope pphWeek1, pphWeek2;
            TProductPriceHope pph = new TProductPriceHope();
            pph.setArea(form.getArea());
            pph.setPid(pp.getPid());
            pph.setStarttime(week1Monday);
            pph.setEndtime(week1Sunday);
            pph.setTimetype("1");

            TProductPriceHope result = productPriceHopeService.query(pph);
            if (result != null) {
                pphWeek1 = result;
            } else {
                pphWeek1 = new TProductPriceHope();
                BeanUtils.copyProperties(pph, pphWeek1);
            }

            pph.setStarttime(week2Monday);
            pph.setEndtime(week2Sunday);
            pph.setTimetype("2");
            result = productPriceHopeService.query(pph);
            if (result != null) {
                pphWeek2 = result;
            } else {
                pphWeek2 = new TProductPriceHope();
                BeanUtils.copyProperties(pph, pphWeek2);
            }

            pphWeek1.setUpdatetime(updateTime);
            pphWeek1.setUpdater(operator);
            pphWeek1.setBaseprice(pp.getWeek1price());

            pphWeek2.setUpdatetime(updateTime);
            pphWeek2.setUpdater(operator);
            pphWeek2.setBaseprice(pp.getWeek2price());

            if (pphWeek1.getId() != null) {
                productPriceHopeService.modify(pphWeek1);
            } else {
                productPriceHopeService.add(pphWeek1);
            }

            if (pphWeek2.getId() != null) {
                productPriceHopeService.modify(pphWeek2);
            } else {
                productPriceHopeService.add(pphWeek2);
            }
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping("/marketinfo/load/{area}/{date}/")
    public @ResponseBody List<TProductPrice> loadProductPrice(@PathVariable String area, @PathVariable Date date) {
        TProductPrice qc = new TProductPrice();
        qc.setArea(area);
        return productPriceService.queryListByDay(qc, date);
    }

    @RequestMapping("/marketinfo/forecast/load/{area}/{date}/")
    public @ResponseBody Map<String, Object> loadProductPriceForecast(@PathVariable String area,
                                                                          @PathVariable Date date) {
        TProductPriceHope qc = new TProductPriceHope();
        qc.setArea(area);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Monday is 2, need -1
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        cal.add(Calendar.DATE, -dayOfWeek + 1);
        Map<String, Object> forecast = new HashMap<>();
        forecast.put("day", cal.getTime());
        forecast.put("result", productPriceHopeService.queryListByDay(qc, date));
        return forecast;
    }

    private void auditPass(AuthCmpInfo authInfo, CompanyInfo.CompanyType type, HttpServletRequest request) throws ServletRequestBindingException {
        authInfo.setAuthStatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_YES);

        switch (type) {
            case COMPANY_TYPE_ENTERPRISE:
                updateCompany(authInfo, request);
                break;
            case COMPANY_TYPE_SHIP:
                updateShip(authInfo, request);
                break;
            case COMPANY_TYPE_PERSONAL:
                updatePerson(authInfo, request);
                break;
        }
    }

    private void auditFail(AuthCmpInfo authInfo) {
        authInfo.setAuthStatus(AuthRecordStatus.AUTH_STATUS_CHECK_NO);
    }

    private void updateCompany(AuthCmpInfo authInfo, HttpServletRequest request) {
        TCompanyAuth ca = new TCompanyAuth();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(ca);
        binder.bind(request);
        authInfo.setCompanyAuth(ca);
    }

    private void updateShip(AuthCmpInfo authInfo, HttpServletRequest request) {
        TCompanyShipping cs = new TCompanyShipping();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(cs);
        binder.bind(request);
        authInfo.setShippingAuth(cs);
    }

    private void updatePerson(AuthCmpInfo authInfo, HttpServletRequest request) throws ServletRequestBindingException {
        TCompanyPersonal cp = new TCompanyPersonal();

        ServletRequestDataBinder binder = new ServletRequestDataBinder(cp);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        binder.bind(request);

        cp.setCratedate(Calendar.getInstance().getTime());
        int gender = ServletRequestUtils.getIntParameter(request, "gender");
        cp.setSex(CompanyInfo.PersonalAuthSex.enumOf(gender));
        cp.setCratedate(new Date());

        authInfo.setPersonalAuth(cp);
    }

    
    @SuppressWarnings("static-access")
	@RequestMapping("/verify_info_audit/finished/{page}/")
    public String queryPublishFinishedList(@PathVariable Integer page, Integer operator, ModelMap model) {
    	
    	if (page == null || page < 1) {
            return "redirect:/tasks/editor/verify_info_audit/finished/1/";
        }
    	
    	String strParams = "";
    	
        Map<String, Object> params = new HashMap<>();
        if(operator != null && operator == 1){
        	params.put("operatorId", this.getCurrentUser().getId());
        	strParams += "?operator="+operator;
        }

        QueryContext<TAuthRecord> qContext = new QueryContext<TAuthRecord>();
        qContext.setParameters(params);

        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        qContext.setPage(paging);
        qContext = this.authRecordService.queryParentAuthRecordOfInsteadListForPagination(qContext);
        calculatePagingData(model, "/tasks/editor/verify_info_audit/finished/{}/"+strParams, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        model.addAttribute("resList", qContext.getQueryResult().getResult());
        model.addAttribute("operatorVal",operator);
    	
    	return "tasks/editor_verify_info_audit_list";
    } 


}
