package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TOfflinePay extends BaseBean {
    /**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

    /**
     * 支付流水号
     */
    private String pid;

    /**
     * 订单编号
     */
    private String oid;

    /**
     * 保证金、货款
     */
    private String otype;

    /**
     * 应收金额
     */
    private Float total;

    /**
     * 实际收款金额
     */
    private Float amount;

    /**
     * 线上、线下（主要线下）
     */
    private String ptype;

    /**
     * 收款人
     */
    private String creater;

    /**
     * 收款时间
     */
    private Date creattime;

    /**
     * 状态
     */
    private String status;

    /**
     * 处理人员
     */
    private String dealer;

    /**
     * 处理时间
     */
    private Date dealtime;

    /**
     * 处理结果
     */
    private String dealresult;

    /**
     * 业务类型[支付和充值]
     */
    private String btype;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype == null ? null : otype.trim();
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype == null ? null : ptype.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer == null ? null : dealer.trim();
    }

    public Date getDealtime() {
        return dealtime;
    }

    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }

    public String getDealresult() {
        return dealresult;
    }

    public void setDealresult(String dealresult) {
        this.dealresult = dealresult == null ? null : dealresult.trim();
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype == null ? null : btype.trim();
    }
}