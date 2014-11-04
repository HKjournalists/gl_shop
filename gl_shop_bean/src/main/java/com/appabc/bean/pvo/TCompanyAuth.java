package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCompanyAuth extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6414960639885423564L;

    /**
     * 认证记录ID
     */
    private Integer authid;

    /**
     * 公司名称
     */
    private String cname;

    /**
     * 注册地址
     */
    private String address;

    /**
     * 成立日期
     */
    private String rdate;

    /**
     * 法定代表人
     */
    private String lperson;

    /**
     * 登记机构
     */
    private String orgid;

    /**
     * 企业类型
     */
    private String ctype;

    /**
     * 创建时间
     */
    private Date cratedate;

    /**
     * 更新时间
     */
    private Date updatedate;

    public Integer getAuthid() {
        return authid;
    }

    public void setAuthid(Integer authid) {
        this.authid = authid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate == null ? null : rdate.trim();
    }

    public String getLperson() {
        return lperson;
    }

    public void setLperson(String lperson) {
        this.lperson = lperson == null ? null : lperson.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    public Date getCratedate() {
        return cratedate;
    }

    public void setCratedate(Date cratedate) {
        this.cratedate = cratedate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}