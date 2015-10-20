package com.appabc.bean.pvo;

import com.appabc.bean.enums.ContractInfo.ContractDisPriceType;
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
    private ContractDisPriceType type;

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
    private Double beginamount;

    /**
     * 操作金额结束
     */
    private Double endamount;

    /**
     * 操作数量起始
     */
    private Double beginnum;

    /**
     * 操作数量结束
     */
    private Double endnum;

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

    /**  
	 * type  
	 *  
	 * @return  the type  
	 * @since   1.0.0  
	 */
	
	public ContractDisPriceType getType() {
		return type;
	}

	/**  
	 * @param type the type to set  
	 */
	public void setType(ContractDisPriceType type) {
		this.type = type;
	}

	public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid == null ? null : lid.trim();
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

    public Double getBeginamount() {
        return beginamount;
    }

    public void setBeginamount(Double beginamount) {
        this.beginamount = beginamount;
    }

    public Double getEndamount() {
        return endamount;
    }

    public void setEndamount(Double endamount) {
        this.endamount = endamount;
    }

    public Double getBeginnum() {
        return beginnum;
    }

    public void setBeginnum(Double beginnum) {
        this.beginnum = beginnum;
    }

    public Double getEndnum() {
        return endnum;
    }

    public void setEndnum(Double endnum) {
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