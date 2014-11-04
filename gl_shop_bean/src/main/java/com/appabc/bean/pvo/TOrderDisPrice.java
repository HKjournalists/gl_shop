package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TOrderDisPrice extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6308562170620493224L;

    /**
     * 操作编号
     */
    private String lid;

    /**
     * 抽样验收、全部验收
     */
    private Integer type;

    /**
     * 变更人
     */
    private String canceler;

    /**
     * 变更时间
     */
    private Date canceltime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 操作金额起始
     */
    private Float beginamount;

    /**
     * 操作金额结束
     */
    private Float endamount;

    /**
     * 操作数量起始
     */
    private Float beginnum;

    /**
     * 操作数量结束
     */
    private Float endnum;

    /**
     * 处罚原因
     */
    private String punreason;

    /**
     * 处罚天数
     */
    private Integer punday;

    /**
     * 备注
     */
    private String remark;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid == null ? null : lid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCanceler() {
        return canceler;
    }

    public void setCanceler(String canceler) {
        this.canceler = canceler == null ? null : canceler.trim();
    }

    public Date getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(Date canceltime) {
        this.canceltime = canceltime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Float getBeginamount() {
        return beginamount;
    }

    public void setBeginamount(Float beginamount) {
        this.beginamount = beginamount;
    }

    public Float getEndamount() {
        return endamount;
    }

    public void setEndamount(Float endamount) {
        this.endamount = endamount;
    }

    public float getBeginnum() {
        return beginnum;
    }

    public void setBeginnum(float beginnum) {
        this.beginnum = beginnum;
    }

    public float getEndnum() {
        return endnum;
    }

    public void setEndnum(float endnum) {
        this.endnum = endnum;
    }

    public String getPunreason() {
        return punreason;
    }

    public void setPunreason(String punreason) {
        this.punreason = punreason == null ? null : punreason.trim();
    }

    public Integer getPunday() {
        return punday;
    }

    public void setPunday(Integer punday) {
        this.punday = punday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}