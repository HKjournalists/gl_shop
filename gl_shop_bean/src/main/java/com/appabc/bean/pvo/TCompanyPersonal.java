package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCompanyPersonal extends BaseBean {
	private static final long serialVersionUID = -7606908628185735146L;


    /**
     * 认证记录ID
     */
    private Integer authid;

    /**
     * 姓名
     */
    private String cpname;

    /**
     * 姓别
     */
    private Integer sex;

    /**
     * 身份证
     */
    private String identification;

    /**
     * 籍贯
     */
    private String origo;

    /**
     * 创建时间
     */
    private Date cratedate;

    /**
     * 更新时间
     */
    private Date updatedate;

    /**
     * 备注
     */
    private String remark;

    public Integer getAuthid() {
        return authid;
    }

    public void setAuthid(Integer authid) {
        this.authid = authid;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname == null ? null : cpname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification == null ? null : identification.trim();
    }

    public String getOrigo() {
        return origo;
    }

    public void setOrigo(String origo) {
        this.origo = origo == null ? null : origo.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}