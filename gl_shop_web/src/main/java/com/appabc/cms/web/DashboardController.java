package com.appabc.cms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Created by zouxifeng on 3/25/15.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @RequestMapping("/")
    public String dashboard() {
        return "dashboard";
    }
}
