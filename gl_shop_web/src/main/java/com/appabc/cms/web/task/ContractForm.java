package com.appabc.cms.web.task;

import com.appabc.bean.bo.OrderAllInfor;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.datas.cms.vo.task.Task;

import java.util.List;

/**
 * Created by zouxifeng on 12/29/14.
 */
public class ContractForm {

    private Task<OrderAllInfor> task;
    private TCompanyInfo buyer;
    private String buyerPhone;
    private TCompanyInfo seller;
    private String sellerPhone;
    private OrderAllInfor orderRequest;
    private String productCode;
    private String productName;
    private String productTypeId;
    private List<TProductInfo> productTypes;

    public Task<OrderAllInfor> getTask() {
        return task;
    }

    public void setOrderRequest(OrderAllInfor orderRequest) {
        this.orderRequest = orderRequest;
    }

    public void setTask(Task<OrderAllInfor> task) {
        this.task = task;
    }

    public void setBuyer(TCompanyInfo buyer) {
        this.buyer = buyer;
    }

    public void setSeller(TCompanyInfo seller) {
        this.seller = seller;
    }

    public TCompanyInfo getBuyer() {
        return buyer;
    }

    public TCompanyInfo getSeller() {
        return seller;
    }

    public OrderAllInfor getOrderRequest() {
        return orderRequest;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<TProductInfo> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<TProductInfo> productTypes) {
        this.productTypes = productTypes;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }
}
