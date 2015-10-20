package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.ExtractStatus;
import com.appabc.common.base.bean.BaseBean;

public class TPassbookDraw extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7836431510426205954L;

    /**
     * 收款方编号
     */
    private String aid;

    /**
     * 提取金额
     */
    private Double amount;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 处理时间
     */
    private Date dealtime;

    /**
     * 处理人
     */
    private String dealer;

    /**
     * 处理结果
     */
    private String dealstatus;

    /**
     * 支付流水号
     */
    private String pid;

    /**
     * 状态
     */
    private ExtractStatus status;

    /**
     * 备注
     */
    private String mark;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getDealtime() {
        return dealtime;
    }

    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer == null ? null : dealer.trim();
    }

    public String getDealstatus() {
        return dealstatus;
    }

    public void setDealstatus(String dealstatus) {
        this.dealstatus = dealstatus == null ? null : dealstatus.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public ExtractStatus getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(ExtractStatus status) {
		this.status = status;
	}
}