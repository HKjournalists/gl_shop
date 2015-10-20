package com.appabc.bean.pvo;

import com.appabc.bean.enums.ContractInfo.ContractArbitrationStatus;
import com.appabc.common.base.bean.BaseBean;

import java.util.Date;

public class TOrderArbitration extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6897683129726038727L;

    /**
     * 操作编号
     */
    private String lid;

    /**
     * 申请企业ID
     */
    private String creater;

    /**
     * 申请时间
     */
    private Date createtime;

    /**
     * 申请备注
     */
    private String remark;

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
     * 状态
     */
    private ContractArbitrationStatus status;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid == null ? null : lid.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public ContractArbitrationStatus getStatus() {
        return status;
    }

    public void setStatus(ContractArbitrationStatus status) {
        this.status = status;
    }
}