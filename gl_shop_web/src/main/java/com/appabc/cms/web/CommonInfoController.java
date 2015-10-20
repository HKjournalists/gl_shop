package com.appabc.cms.web;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.tools.service.codes.IPublicCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zouxifeng on 3/4/15.
 */
@Controller
@RequestMapping("/common/")
public class CommonInfoController {

    @Autowired
    private IPublicCodesService publicCodesService;

    @RequestMapping("/areas/")
    public @ResponseBody List<TPublicCodes> loadArea() {
        TPublicCodes qc = new TPublicCodes();
        qc.setCode("AREA");
        qc.setIshidden(0);
        return publicCodesService.queryForList(qc);
    }
    
    /**
     * 港口
     * @return
     */
    @RequestMapping("/reverPortDock/")
    public @ResponseBody List<TPublicCodes> loadReverPortDock() {
    	TPublicCodes qc = new TPublicCodes();
    	qc.setCode("RIVER_PORT_DOCK");
    	qc.setIshidden(0);
    	return publicCodesService.queryForList(qc);
    }

}
