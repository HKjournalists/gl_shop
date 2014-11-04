package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TPassbookInfo extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1010067370486492601L;

	/**
	 * 企业编号
	 * */
	private String cid;
	
    /**
     * 保证金 或者 钱包
     */
    private String passtype;

    /**
     * 总额
     */
    private Float amount;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 备注
     */
    private String remark;

    public String getPasstype() {
        return passtype;
    }

    public void setPasstype(String passtype) {
        this.passtype = passtype == null ? null : passtype.trim();
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

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
    
}