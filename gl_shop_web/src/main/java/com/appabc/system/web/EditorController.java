package com.appabc.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 3:40:52 PM
 */
@Controller
@RequestMapping("/editor")
public class EditorController {
	
	@RequestMapping("/marketinfo/")
	public String marketInfo() {
		return "task/editor_market_info";
	}

	@RequestMapping("/verifyaudit/")
	public String verifyInfoAudit() {
		return "task/editor_verify_info_audit";
	}
}
