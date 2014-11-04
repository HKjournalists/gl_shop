package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TShortMessageUsed extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -894841893993157649L;

    /**
     * 业务ID
     */
    private String businessid;

    /**
     * 业务类型
     */
    private String businesstype;

    /**
     * 短信内容
     */
    private String smcontent;

    /**
     * 接收手机号
     */
    private String phonenumber;

    /**
     * 短信发送状态（成功、失败）
     */
    private Integer sendstatus;

    /**
     * 待发送时间
     */
    private Date waittime;

    /**
     * 发送时间
     */
    private Date sendtime;

    /**
     * 备注
     */
    private String remark;

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype == null ? null : businesstype.trim();
    }

    public String getSmcontent() {
        return smcontent;
    }

    public void setSmcontent(String smcontent) {
        this.smcontent = smcontent == null ? null : smcontent.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Integer getSendstatus() {
        return sendstatus;
    }

    public void setSendstatus(Integer sendstatus) {
        this.sendstatus = sendstatus;
    }

    public Date getWaittime() {
        return waittime;
    }

    public void setWaittime(Date waittime) {
        this.waittime = waittime;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}