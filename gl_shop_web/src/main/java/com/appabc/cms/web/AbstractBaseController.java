package com.appabc.cms.web;

import com.appabc.cms.web.security.UserDetailsImpl;
import com.appabc.datas.cms.dao.ServiceLogDao;
import com.appabc.datas.cms.vo.ServiceLog;
import com.appabc.datas.cms.vo.ServiceLogType;
import com.appabc.datas.cms.vo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zouxifeng on 1/23/15.
 */
public class AbstractBaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ServiceLogDao serviceLogDao;

    protected final static User getCurrentUser() {
        try {
			if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			    if (auth instanceof AnonymousAuthenticationToken) {
			        return null;
			    }
			    return ((UserDetailsImpl) auth.getPrincipal()).getRawUser();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        return null;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @ModelAttribute
    protected void currentUser(ModelMap model) {
        model.addAttribute("currentUser", getCurrentUser());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(SecurityException ex) {
        logger.error("Request failed.", ex);
        return "redirect:/system/access_denied/";
    }

    protected final void putServiceLogs(String objectId,
                                        ServiceLogType type,
                                        ModelMap model) {
        model.addAttribute("service_logs", serviceLogDao.query(objectId, type));
    }

    protected final void createServiceLog(String objectId,
                                          String content,
                                          ServiceLogType type) {

        createServiceLog("", objectId, content, type);
    }

    /**
     *
     * @param companyId should not be null, can put in with empty string
     * @param objectId
     * @param content
     * @param type
     */
    protected final void createServiceLog(String companyId,
                                          String objectId,
                                          String content,
                                          ServiceLogType type) {

        ServiceLog log = new ServiceLog();
        log.setCompanyId(companyId);
        log.setCreateTime(new Date());
        log.setObjectId(objectId);
        log.setType(type);
        log.setOperator(getCurrentUser());
        log.setContent(content);
        serviceLogDao.create(log);
    }
}
