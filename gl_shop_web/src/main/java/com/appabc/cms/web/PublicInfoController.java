package com.appabc.cms.web;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderInfo;
import com.appabc.bean.pvo.TOrderOperations;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.cms.dao.ServiceLogDao;
import com.appabc.datas.cms.vo.ServiceLog;
import com.appabc.datas.cms.vo.ServiceLogType;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.contract.IContractInfoService;
import com.appabc.datas.service.contract.IContractOperationService;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.product.IProductPropertyService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.tools.service.codes.IPublicCodesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zouxifeng on 3/16/15.
 */
@Controller
@RequestMapping("/public")
public class PublicInfoController {

    @Autowired
    private IOrderFindService orderFindService;

    @Autowired
    private IContractInfoService contractInfoService;

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IPublicCodesService publicCodesService;

    @Autowired
    private IProductPropertyService productPropertyService;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ServiceLogDao serviceLogDao;
    
    @Autowired
    private IContractOperationService contractOperationService;

    @RequestMapping("/order_request/{id}/")
    public String showOrderRequestDetail(@PathVariable String id, ModelMap model) {
        Map<String, Object> orderRequestData = populateOrderInfo(id);
        model.putAll(orderRequestData);
        return "public/order_request_detail";
    }

    @RequestMapping("/order_request/{id}/load/")
    public @ResponseBody Map<String, Object> loadOrderRequest(@PathVariable String id) {
        return populateOrderInfo(id);
    }

    @RequestMapping("/contract/{id}/")
    public String showOrderDetail(@PathVariable String id, ModelMap model) {
        TOrderInfo order = contractInfoService.query(id);
        Map<String, Object> contractData = populateOrderInfo(order.getFid());
        model.putAll(contractData);

        TCompanyInfo buyerCompany = companyInfoService.query(order.getBuyerid());
        TUser buyer = userService.getUserByCid(buyerCompany.getId());
        model.addAttribute("buyerCompany", buyerCompany);
        model.addAttribute("buyer", buyer);

        TCompanyInfo sellerCompany = companyInfoService.query(order.getSellerid());
        TUser seller = userService.getUserByCid(sellerCompany.getId());
        model.addAttribute("sellerCompany", sellerCompany);
        model.addAttribute("seller", seller);
        
        // 操作记录处理
		TOrderOperations entity = new TOrderOperations();
		entity.setOid(id);
		List<TOrderOperations> opList = contractOperationService.queryForList(entity);
		
		model.addAttribute("opList", opList);

        return "public/contract_detail";
    }

    @RequestMapping("/service_logs/{objectId}/{type}/")
    public String loadServiceLog(@PathVariable String objectId,
                                 @PathVariable int type,
                                 Boolean showPanel,
                                 ModelMap model) {
        List<ServiceLog> logs =  serviceLogDao.query(objectId, ServiceLogType.valueOf(type));
        model.addAttribute("service_logs", logs);
        if (showPanel == null) {
            showPanel = false;
        }
        model.addAttribute("showPanel", showPanel);
        return "layouts/fragments :: serviceLogs";
    }

    private Map<String, Object> populateOrderInfo(String requestId) {
        Map<String, Object> model = new HashMap<>();
        OrderAllInfor order = orderFindService.queryInfoById(requestId, null);
        model.put("order", order);
        TPublicCodes q = new TPublicCodes();
        q.setVal(order.getPcode());
        model.put("product", publicCodesService.query(q));
        if (order.getPtype() != null) {
            q = new TPublicCodes();
            q.setVal(order.getPtype());
            model.put("productType", publicCodesService.query(q));
        }
        TProductProperty qProdProp = new TProductProperty();
        qProdProp.setPid(order.getPid());
        List<TProductProperty> productProperties = productPropertyService.queryForList(qProdProp);
        Map<String, TProductProperty> propsMap = new HashMap<>();
        for (Iterator<TProductProperty> iter = productProperties.iterator(); iter.hasNext(); ) {
            TProductProperty p = iter.next();
            if (p.getStatus() == ProductInfo.PropertyStatusEnum.PROPERTY_STATUS_1) {
                iter.remove();
                continue;
            }
            propsMap.put(p.getId(), p);
            p.setContent("");
        }

        if (!CollectionUtils.isEmpty(order.getOppList())) {
            for (TOrderProductProperty prop : order.getOppList()) {
                TProductProperty p = propsMap.get(prop.getPproid());
                p.setContent(prop.getContent());
            }
        }

        model.put("productProperties", productProperties);
        return model;
    }
}
