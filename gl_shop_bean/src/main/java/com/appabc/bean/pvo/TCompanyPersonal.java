package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.CompanyInfo.PersonalAuthSex;
import com.appabc.common.base.bean.BaseBean;

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
    private PersonalAuthSex sex;

    /**
     * 身份证
     */
    private String identification;

    /**
     * 地址
     */
    private String address;
    
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

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 签发机关
     */
    private String issuingauth;

    /**
     * 有效期限-开始时间
     */
    private Date effstarttime;

    /**
     * 有效期限-结束时间
     */
    private Date effendtime;


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

    public PersonalAuthSex getSex() {
		return sex;
	}

	public void setSex(PersonalAuthSex sex) {
		this.sex = sex;
	}

	public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification == null ? null : identification.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
        this.origo = this.address;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIssuingauth() {
		return issuingauth;
	}

	public void setIssuingauth(String issuingauth) {
		this.issuingauth = issuingauth;
	}

	public Date getEffstarttime() {
		return effstarttime;
	}

	public void setEffstarttime(Date effstarttime) {
		this.effstarttime = effstarttime;
	}

	public Date getEffendtime() {
		return effendtime;
	}

	public void setEffendtime(Date effendtime) {
		this.effendtime = effendtime;
	}

	public String getOrigo() {
		return origo;
	}

	public void setOrigo(String origo) {
		this.origo = origo == null ? null : origo.trim();
	}

	
}
