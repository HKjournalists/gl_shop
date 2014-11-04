package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;

public class TOrderArbitrationResult extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2619232671061144507L;

    /**
     * 仲裁ID
     */
    private String aid;

    /**
     * 支付流水号
     */
    private String pid;

    /**
     * 处理类型（返还、扣除、解冻）
     */
    private String rtype;

    /**
     * 处理内容（保证金、货款）
     */
    private String rcontent;

    /**
     * 买方还是卖方
     */
    private String qytype;

    /**
     * 买卖双方企业编号
     */
    private String qyid;

    /**
     * 金额
     */
    private Float amount;

    /**
     * 对应流水ID
     */
    private String payid;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype == null ? null : rtype.trim();
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent == null ? null : rcontent.trim();
    }

    public String getQytype() {
        return qytype;
    }

    public void setQytype(String qytype) {
        this.qytype = qytype == null ? null : qytype.trim();
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid == null ? null : payid.trim();
    }
}