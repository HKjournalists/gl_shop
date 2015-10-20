package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.common.base.bean.BaseBean;

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
    private PurseType passtype;

    /**
     * 总额
     */
    private Double amount;

    /**
     * 保证金是否充足
     */
    private boolean isGuarantyEnough;
    
    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 备注
     */
    private String remark;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

	/**  
	 * passtype  
	 *  
	 * @return  the passtype  
	 * @since   1.0.0  
	*/  
	
	public PurseType getPasstype() {
		return passtype;
	}

	/**  
	 * @param passtype the passtype to set  
	 */
	public void setPasstype(PurseType passtype) {
		this.passtype = passtype;
	}

	/**  
	 * isGuarantyEnough  
	 *  
	 * @return  the isGuarantyEnough  
	 * @since   1.0.0  
	*/  
	
	public boolean isGuarantyEnough() {
		return isGuarantyEnough;
	}

	/**  
	 * @param isGuarantyEnough the isGuarantyEnough to set  
	 */
	public void setGuarantyEnough(boolean isGuarantyEnough) {
		this.isGuarantyEnough = isGuarantyEnough;
	}
    
}