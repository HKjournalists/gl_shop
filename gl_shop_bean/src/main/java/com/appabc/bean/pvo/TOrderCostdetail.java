package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TOrderCostdetail extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3137298852798791987L;

	/**
	 * 订单编号
	 * */
	private String oid;
    /**
     * 支付流水号
     */
    private String pid;

    /**
     * 规则发布ID
     */
    private String fid;

    /**
     * 名称
     */
    private String name;

    /**
     * 总额
     */
    private Float amount;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 更新时间
     */
    private Date updatedate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 备注
     */
    private String remark;

    /**  
	 * oid  
	 *  
	 * @return  the oid  
	 * @since   1.0.0  
	 */
	
	public String getOid() {
		return oid;
	}

	/**  
	 * @param oid the oid to set  
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}