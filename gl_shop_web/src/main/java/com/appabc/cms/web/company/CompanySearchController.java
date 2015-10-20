package com.appabc.cms.web.company;

import com.appabc.bean.pvo.TUser;
import com.appabc.datas.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zouxifeng on 2/3/15.
 */
@Controller
@RequestMapping("/company/search")
public class CompanySearchController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/mobile/{mobile}/")
    public @ResponseBody Map<String, String> searchByMobile(@PathVariable String mobile) {
        TUser qc = new TUser();
        qc.setPhone(mobile);
        TUser u = userService.query(qc);
        Map<String, String> result = new HashMap<>();
        if (u != null) {
            result.put("cid", u.getCid());
        }
        return result;
    }
}
