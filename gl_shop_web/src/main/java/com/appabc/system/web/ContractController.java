package com.appabc.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 2:47:23 PM
 */
@Controller
@RequestMapping("/contract")
public class ContractController {
	
	@RequestMapping("/match/")
	public String match() {
		return "task/contract_match_comm";
	}

	@RequestMapping("/genconfirm/")
	public String generateAndConfirm() {
		return "task/contract_generate_confirm";
	}
}
