package com.appabc.system.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appabc.system.UserService;

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
public class SystemController {
	
	@Autowired
	private UserService userService;
	
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
	
}
