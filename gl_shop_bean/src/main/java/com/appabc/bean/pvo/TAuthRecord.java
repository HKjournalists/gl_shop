package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.AuthRecordInfo.AuthRecordType;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.common.base.bean.BaseBean;

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
    private AuthRecordStatus authstatus;

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
    private AuthRecordType type;
    
    /**
     * 提款人ID
     */
    private String abid;

    private String cname; // 企业名称
    
    private CompanyType ctype; // 企业类型（区分企业、船舶、个人）
    
    private String username; // 帐号登录名
    
    private Date regtime; // 帐号注册时间
    
    private String carduser; // 持卡人姓名

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

	public String getAbid() {
		return abid;
	}

	public void setAbid(String abid) {
		this.abid = abid;
	}

	public AuthRecordStatus getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(AuthRecordStatus authstatus) {
		this.authstatus = authstatus;
	}

	public AuthRecordType getType() {
		return type;
	}

	public void setType(AuthRecordType type) {
		this.type = type;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public CompanyType getCtype() {
		return ctype;
	}

	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public String getCarduser() {
		return carduser;
	}

	public void setCarduser(String carduser) {
		this.carduser = carduser;
	}
    
}