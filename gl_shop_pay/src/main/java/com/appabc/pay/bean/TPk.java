package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TPk extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7586358574939864134L;

	/**
     * 业务编码
     */
    private String bid;

    /**
     * 最大值
     */
    private Integer maxval;

    /**
     * 最小值
     */
    private Integer minval;

    /**
     * 当前值
     */
    private Integer curval;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 业务前缀(关键字YEAR,DAY,MONTH)
     */
    private String bprefix;

    /**
     * 业务后缀
     */
    private String bsuffix;

    /**
     * 消息创建时间
     */
    private Date createtime;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid == null ? null : bid.trim();
    }

    public Integer getMaxval() {
        return maxval;
    }

    public void setMaxval(Integer maxval) {
        this.maxval = maxval;
    }

    public Integer getMinval() {
        return minval;
    }

    public void setMinval(Integer minval) {
        this.minval = minval;
    }

    public Integer getCurval() {
        return curval;
    }

    public void setCurval(Integer curval) {
        this.curval = curval;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getBprefix() {
        return bprefix;
    }

    public void setBprefix(String bprefix) {
        this.bprefix = bprefix == null ? null : bprefix.trim();
    }

    public String getBsuffix() {
        return bsuffix;
    }

    public void setBsuffix(String bsuffix) {
        this.bsuffix = bsuffix == null ? null : bsuffix.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}