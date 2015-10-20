package com.appabc.cms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 3:39:14 PM
 */
@Controller
@RequestMapping("/customer")
public class CustomerServiceController {

	@RequestMapping("/dialin/")
	public String dialIn() {
		return "task/customer_dial_in";
	}

	@RequestMapping("/dialout/")
	public String dialOut() {
		return "task/customer_dial_out";
	}
}
