package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCalRuleUse extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2626057879202050534L;

    /**
     * 规则定义编号
     */
    private String ruleid;

    /**
     * 使用开始时间
     */
    private Date startdate;

    /**
     * 使用结束时间
     */
    private Date enddate;

    /**
     * 排序
     */
    private String orderby;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 备注
     */
    private String remark;

    public String getRuleid() {
        return ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid == null ? null : ruleid.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby == null ? null : orderby.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}