package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TAcceptBank extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1397338775999167001L;

	/**
	 * 企业编号
	 * */
	private String cid;
	
    /**
     * 认证记录ID
     */
    private Integer authid;

    /**
     * 银行卡号
     */
    private String bankcard;

    /**
     * 银行账户
     */
    private String bankaccount;

    /**
     * 持卡人姓名
     */
    private String carduser;

    /**
     * 持卡人证件号
     */
    private String carduserid;

    /**
     * 招商、建行的类型
     */
    private String banktype;

    /**
     * 开户行
     */
    private String bankname;

    /**
     * 地址
     */
    private String addr;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 认证图片
     */
    private String imgurl;
    /**
     * 认证图片
     */
    private String imgid;
    
    /**
     * 状态（默认1，普通0）
     */
    private Integer status;

    /**
     * 认证状态（默认1，普通0）
     */
    private Integer authstatus;
    
    /**  
	 * cid  
	 *  
	 * @return  the cid  
	 * @since   1.0.0  
	 */
	
	public String getCid() {
		return cid;
	}

	/**  
	 * @param cid the cid to set  
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getAuthid() {
        return authid;
    }

    public void setAuthid(Integer authid) {
        this.authid = authid;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard == null ? null : bankcard.trim();
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount == null ? null : bankaccount.trim();
    }

    public String getCarduser() {
        return carduser;
    }

    public void setCarduser(String carduser) {
        this.carduser = carduser == null ? null : carduser.trim();
    }

    public String getCarduserid() {
        return carduserid;
    }

    public void setCarduserid(String carduserid) {
        this.carduserid = carduserid == null ? null : carduserid.trim();
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype == null ? null : banktype.trim();
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(Integer authstatus) {
		this.authstatus = authstatus;
	}
}