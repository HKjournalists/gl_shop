package com.appabc.pay.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appabc.bean.bo.ViewImgsBean;
import com.appabc.bean.enums.AcceptBankInfo.AcceptAuthStatus;
import com.appabc.bean.enums.AcceptBankInfo.AcceptBankStatus;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.bean.BaseBean;

public class TAcceptBank extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1397338775999167001L;

	/**
	 * 企业编号
	 * */
	private String cid;

    private TCompanyInfo company;

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
     * 用于显示的图片信息
     */
    private List<ViewImgsBean> vImgList = new ArrayList<ViewImgsBean>();
    /**
     * 状态（默认1，普通0）
     */
    private AcceptBankStatus status;

    /**
     * 认证状态（默认1，普通0）
     */
    private AcceptAuthStatus authstatus;
    
    /**
     * 关联的图片ID
     */
    private String Imgid;
    
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

    public TCompanyInfo getCompany() {
        return company;
    }

    public void setCompany(TCompanyInfo company) {
        this.company = company;
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

	public List<ViewImgsBean> getvImgList() {
		return vImgList;
	}

	public void setvImgList(List<ViewImgsBean> vImgList) {
		this.vImgList = vImgList;
	}

	public String getImgid() {
		return Imgid;
	}

	public void setImgid(String imgid) {
		Imgid = imgid;
	}

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public AcceptBankStatus getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(AcceptBankStatus status) {
		this.status = status;
	}

	/**  
	 * authstatus  
	 *  
	 * @return  the authstatus  
	 * @since   1.0.0  
	*/  
	
	public AcceptAuthStatus getAuthstatus() {
		return authstatus;
	}

	/**  
	 * @param authstatus the authstatus to set  
	 */
	public void setAuthstatus(AcceptAuthStatus authstatus) {
		this.authstatus = authstatus;
	}
	
}