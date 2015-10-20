package com.appabc.cms.web;

import com.appabc.bean.enums.ProductInfo;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.datas.service.order.IOrderProductPropertyService;
import com.appabc.datas.service.product.IProductInfoService;
import com.appabc.datas.service.product.IProductPropertyService;
import com.appabc.tools.service.codes.IPublicCodesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zouxifeng on 1/15/15.
 */
@Controller
@RequestMapping("/product")
public class ProductInfoController {

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IProductPropertyService productPropertyService;

    @Autowired
    private IPublicCodesService publicCodesService;

    @Autowired
    private IOrderProductPropertyService orderProductPropertyService;

    @RequestMapping("/code/G001/")
    public @ResponseBody List<Map<String, Object>> loadG001Spec() {
        TProductInfo qc = new TProductInfo();
        qc.setPcode("G001");
        List<TProductInfo> products = productInfoService.queryForList(qc);
        return recomposeProducts(products);
    }

    @RequestMapping("/code/G002/")
    public @ResponseBody List<TPublicCodes> loadG002SubCategories() {
        TPublicCodes qc = new TPublicCodes();
        qc.setPcode("G002");
        List<TPublicCodes> productCodes = publicCodesService.queryForList(qc);
        return productCodes;
    }

    @RequestMapping("/spec/{productType}/")
    public @ResponseBody List<Map<String, Object>> loadProductSpec(@PathVariable String productType) {
        TProductInfo qc = new TProductInfo();
        qc.setPtype(productType);
        List<TProductInfo> products = productInfoService.queryForList(qc);
        return recomposeProducts(products);
    }

    @RequestMapping("/props/{pid}/")
    public @ResponseBody List<TProductProperty> loadProductProperties(@PathVariable String pid) {
        TProductProperty qc = new TProductProperty();
        qc.setPid(pid);
        return productPropertyService.queryForList(qc);
    }

    @RequestMapping("/props/order/{ppid}/")
    public @ResponseBody List<TOrderProductProperty> loadOrderProductProperties(@PathVariable Integer ppid) {
        TOrderProductProperty qc = new TOrderProductProperty();
        qc.setPpid(ppid);
        return orderProductPropertyService.queryForList(qc);
    }

    private List<Map<String, Object>> recomposeProducts(List<TProductInfo> products) {
        TProductProperty qc = new TProductProperty();
        qc.setStatus(ProductInfo.PropertyStatusEnum.PROPERTY_STATUS_1);
        List<TProductProperty> prodProps = productPropertyService.queryForList(qc);
        Map<String, TProductProperty> allBaseProps = new HashMap<>();
        for (TProductProperty pp : prodProps) {
            allBaseProps.put(pp.getPid(), pp);
        }

        List<Map<String, Object>> recomposedProduct = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (TProductInfo pi : products) {
            Map<String, Object> props = mapper.convertValue(pi, Map.class);
            TProductProperty baseProp = allBaseProps.get(pi.getId());
            props.put("minv", baseProp.getMinv());
            props.put("maxv", baseProp.getMaxv());
            recomposedProduct.add(props);
        }
        return recomposedProduct;
    }
}
