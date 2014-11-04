package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;

import java.util.Date;

public class TProductPriceHope extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1506477267418964770L;

	/**
     * 商品ID
     */
    private String pid;
    
    /**
     * 价格
     */
    private Float baseprice;

    /**
     * 测试价格最小
     */
    private Float pricemin;

    /**
     * 测试价格最大
     */
    private Float pricemax;

    /**
     * 单位
     */
    private String unit;

    /**
     * 开始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 1周、2周
     */
    private String timetype;

    /**
     * 地区
     */
    private String area;

    /**
     * 预测人
     */
    private String updater;

    /**
     * 预测时间
     */
    private Date updatetime;

    public Float getBaseprice() {
        return baseprice;
    }

    public void setBaseprice(Float baseprice) {
        this.baseprice = baseprice;
    }

    public Float getPricemin() {
        return pricemin;
    }

    public void setPricemin(Float pricemin) {
        this.pricemin = pricemin;
    }

    public Float getPricemax() {
        return pricemax;
    }

    public void setPricemax(Float pricemax) {
        this.pricemax = pricemax;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype == null ? null : timetype.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
    
    
}