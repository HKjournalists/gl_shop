package com.appabc.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 2:16:15 PM
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

	@RequestMapping("/")
	public String index() {
		return "finance/index";
	}
}
