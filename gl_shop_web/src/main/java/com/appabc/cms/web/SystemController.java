package com.appabc.cms.web;

import com.appabc.datas.cms.service.UserService;
import com.appabc.datas.cms.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : Oct 13, 2014 2:17:38 PM
 */
@Controller
@RequestMapping("/system")
public class SystemController extends AbstractBaseController {

	@Autowired
	private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@RequestMapping(value="/signin/", method=RequestMethod.GET)
	public String signin(@RequestParam(required=false) String error, ModelMap model) {
		model.addAttribute("error", !StringUtils.isEmpty(error));
		return "system/signin";
	}

	@RequestMapping(value="/signin/error/", method=RequestMethod.GET)
	public String signinError(ModelMap model) {
		model.addAttribute("error", true);
		return "system/signin";
	}

    @RequestMapping("/change_password/")
    public String changePassword(String error, ModelMap model) {
        model.addAttribute("error", StringUtils.isNotEmpty(error));
        return "system/change_password";
    }

    @RequestMapping(value="/change_password/", method=RequestMethod.POST)
    public String doChangePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmNewPassword)) {
            return "redirect:/system/change_password/?error=1";
        }
        if (!newPassword.equals(confirmNewPassword)) {
            return "redirect:/system/change_password/?error=2";
        }
        if (newPassword.length() < 8) {
            return "redirect:/system/change_password/?error=3";
        }
        User user = userService.getUser(getCurrentUser().getId());
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/system/change_password/?error=4";
        }
        userService.changePassword(user.getId(), newPassword);
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/system/signin/";
    }

    @RequestMapping("/access_denied/")
    public String accessDenied(ModelMap model) {
        return "error/403";
    }

}
