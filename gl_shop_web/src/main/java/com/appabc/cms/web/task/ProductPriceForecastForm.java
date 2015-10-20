package com.appabc.cms.web.task;

import java.util.Date;

/**
 * Created by zouxifeng on 2/11/15.
 */
public class ProductPriceForecastForm {

    private String area;
    private Date datepoint;
    private ProductPriceForecast[] priceForecasts;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getDatepoint() {
        return datepoint;
    }

    public void setDatepoint(Date datepoint) {
        this.datepoint = datepoint;
    }

    public ProductPriceForecast[] getPriceForecasts() {
        return priceForecasts;
    }

    public void setPriceForecasts(ProductPriceForecast[] priceForecasts) {
        this.priceForecasts = priceForecasts;
    }

    public static class ProductPriceForecast {
        private String pid;
        private Float week1price;
        private String week1oid;
        private Float week2price;
        private String week2oid;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Float getWeek1price() {
            return week1price;
        }

        public void setWeek1price(Float week1price) {
            this.week1price = week1price;
        }

        public String getWeek1oid() {
            return week1oid;
        }

        public void setWeek1oid(String week1oid) {
            this.week1oid = week1oid;
        }

        public Float getWeek2price() {
            return week2price;
        }

        public void setWeek2price(Float week2price) {
            this.week2price = week2price;
        }

        public String getWeek2oid() {
            return week2oid;
        }

        public void setWeek2oid(String week2oid) {
            this.week2oid = week2oid;
        }
    }
}
