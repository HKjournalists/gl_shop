package com.appabc.cms.web.finance;

import com.appabc.cms.web.AbstractListBaseController;
import com.appabc.cms.web.security.UserPermissionAuthority;
import com.appabc.datas.cms.vo.GroupPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 2:16:15 PM
 */
public class FinanceController extends AbstractListBaseController {

    @ModelAttribute
    private void populateModel(ModelMap model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isProcessRole = false;
        boolean isAuditRole = false;

        for (GrantedAuthority auth : userDetails.getAuthorities()) {
            UserPermissionAuthority perm = (UserPermissionAuthority) auth;
            if (GroupPermission.FinanceProcess.isGroupMember(perm.getPermission())) {
                isProcessRole = true;
                break;
            }
        }

        for (GrantedAuthority auth : userDetails.getAuthorities()) {
            UserPermissionAuthority perm = (UserPermissionAuthority) auth;
            if (GroupPermission.FinanceAudit.isGroupMember(perm.getPermission())) {
                isAuditRole = true;
                break;
            }
        }

        model.addAttribute("isRoleProcess", isProcessRole);
        model.addAttribute("isRoleAudit", isAuditRole);
    }

}
