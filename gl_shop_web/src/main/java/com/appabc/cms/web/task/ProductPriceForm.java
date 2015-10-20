package com.appabc.cms.web.task;

import java.util.Date;

/**
 * Created by zouxifeng on 2/5/15.
 */
public class ProductPriceForm {

    private String area;

    public Date getDatepoint() {
        return datepoint;
    }

    public void setDatepoint(Date datepoint) {
        this.datepoint = datepoint;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private Date datepoint;

    private ProductPrice[] prices;

    public ProductPrice[] getPrices() {
        return prices;
    }

    public void setPrices(ProductPrice[] prices) {
        this.prices = prices;
    }

    public static class ProductPrice {
            private String pid;
            private String oid;
            private Float price;

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public Float getPrice() {
                return price;
            }

            public void setPrice(Float price) {
                this.price = price;
            }
    }

}
