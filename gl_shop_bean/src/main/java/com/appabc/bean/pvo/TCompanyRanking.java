package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCompanyRanking extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6222612019103297545L;

    /**
     * 企业编号
     */
    private String cid;

    /**
     * 成功交易笔数
     */
    private Integer ordersnum;

    /**
     * 交易成功率
     */
    private Float orderspersent;

    /**
     * 交易满意度
     */
    private Float degress;

    /**
     * 企业交易评价
     */
    private Float evaluation;

    /**
     * 统计日期
     */
    private Date statdate;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public Integer getOrdersnum() {
        return ordersnum;
    }

    public void setOrdersnum(Integer ordersnum) {
        this.ordersnum = ordersnum;
    }

    public Float getOrderspersent() {
        return orderspersent;
    }

    public void setOrderspersent(Float orderspersent) {
        this.orderspersent = orderspersent;
    }

    public Float getDegress() {
        return degress;
    }

    public void setDegress(Float degress) {
        this.degress = degress;
    }

    public Float getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Float evaluation) {
        this.evaluation = evaluation;
    }

    public Date getStatdate() {
        return statdate;
    }

    public void setStatdate(Date statdate) {
        this.statdate = statdate;
    }
}