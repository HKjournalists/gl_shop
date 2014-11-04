package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TOrderCancel extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1017190498190410088L;

    /**
     * 操作编号
     */
    private String lid;

    /**
     * 取消人（企业、客服、系统）
     */
    private String canceler;

    /**
     * 取消类型（单方取消、协商取消、系统取消）
     */
    private String canceltype;

    /**
     * 取消时间
     */
    private Date canceltime;

    /**
     * 原因
     */
    private String reason;

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

    public String getCanceler() {
        return canceler;
    }

    public void setCanceler(String canceler) {
        this.canceler = canceler == null ? null : canceler.trim();
    }

    public String getCanceltype() {
        return canceltype;
    }

    public void setCanceltype(String canceltype) {
        this.canceltype = canceltype == null ? null : canceltype.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}