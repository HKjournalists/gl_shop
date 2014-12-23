package com.appabc.bean.pvo;

import com.appabc.bean.enums.CompanyInfo.CompanyAuthStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.base.bean.BaseBean;

import java.util.Date;

public class TCompanyInfo extends BaseBean {
    /**
	 *
	 */
	private static final long serialVersionUID = 4050498410377638848L;

	/**
     * 企业名称
     */
    private String cname;

    /**
     * 企业介绍
     */
    private String mark;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系人电话
     */
    private String cphone;

    /**
     * 企业类型（区分企业、船舶、个人）
     */
    private CompanyType ctype;

    /**
     * 认证状态(是否认证)
     */
    private CompanyAuthStatus authstatus;

    /**
     * 处理是否禁用、禁言（预留状态）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 更新时间
     */
    private Date updatedate;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 保证金缴纳状态（是否缴纳足额）
     */
    private CompanyBailStatus bailstatus;

    private String companyImgIds; // 企业相关照片ID，多个ID用逗号间隔,例: id1,id2,id3

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone == null ? null : cphone.trim();
    }

    /**
	 * ctype
	 *
	 * @return  the ctype
	 * @since   1.0.0
	 */

	public CompanyType getCtype() {
		return ctype;
	}

	/**
	 * @param ctype the ctype to set
	 */
	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCompanyImgIds() {
		return companyImgIds;
	}

	public void setCompanyImgIds(String companyImgIds) {
		this.companyImgIds = companyImgIds == null ? null : companyImgIds.trim();
	}

	/**
	 * authstatus
	 *
	 * @return  the authstatus
	 * @since   1.0.0
	*/

	public CompanyAuthStatus getAuthstatus() {
		return authstatus;
	}

	/**
	 * @param authstatus the authstatus to set
	 */
	public void setAuthstatus(CompanyAuthStatus authstatus) {
		this.authstatus = authstatus;
	}

	/**
	 * bailstatus
	 *
	 * @return  the bailstatus
	 * @since   1.0.0
	*/

	public CompanyBailStatus getBailstatus() {
		return bailstatus;
	}

	/**
	 * @param bailstatus the bailstatus to set
	 */
	public void setBailstatus(CompanyBailStatus bailstatus) {
		this.bailstatus = bailstatus;
	}

    public boolean equals(Object o) {
        if (!(o instanceof TCompanyInfo)) {
            return false;
        }

        TCompanyInfo other = (TCompanyInfo) o;
        if (this.getId() == null || other.getId() == null) {
            return false;
        }

        return this.getId().equals(other.getId());
    }
}
