package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TAuthRecord extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1892428591555465142L;

    /**
     * 企业编号
     */
    private String cid;

    /**
     * 图片表关联ID
     */
    private Integer imgid;

    /**
     * 认证状态（审核中，审核通过，审核不通过）
     */
    private String authstatus;

    /**
     * 是否有人处理（主要由于后续可能存在的任务分配）
     */
    private Integer dealstatus;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 认证人
     */
    private String author;

    /**
     * 认证通过或不通过
     */
    private String authresult;

    /**
     * 认证时间
     */
    private Date authdate;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 认证类型：企业、提款账户
     */
    private Integer type;
    
    /**
     * 提款人ID
     */
    private String abid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public Integer getImgid() {
        return imgid;
    }

    public void setImgid(Integer imgid) {
        this.imgid = imgid;
    }

    public String getAuthstatus() {
        return authstatus;
    }

    public void setAuthstatus(String authstatus) {
        this.authstatus = authstatus == null ? null : authstatus.trim();
    }

    public Integer getDealstatus() {
        return dealstatus;
    }

    public void setDealstatus(Integer dealstatus) {
        this.dealstatus = dealstatus;
    }

    public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getAuthresult() {
        return authresult;
    }

    public void setAuthresult(String authresult) {
        this.authresult = authresult == null ? null : authresult.trim();
    }

    public Date getAuthdate() {
        return authdate;
    }

    public void setAuthdate(Date authdate) {
        this.authdate = authdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAbid() {
		return abid;
	}

	public void setAbid(String abid) {
		this.abid = abid;
	}
    
}