package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TOrderInfo extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5881942262246736101L;

    /**
     * 询单ID
     */
    private String fid;

    /**
     * 卖家ID
     */
    private String sellerid;

    /**
     * 卖家ID
     */
    private String buyerid;

    /**
     * 价格
     */
    private Float price;

    /**
     * 数量
     */
    private float totalnum;

    /**
     * 生成时间
     */
    private Date creatime;

    /**
     * 生成人
     */
    private String creater;

    /**
     * 交易期限(存在一个算法)
     */
    private Date limittime;

    /**
     * 总金额（可能与单价乘以数量的总和不一样）
     */
    private Float totalamount;

    /**
     * 支付金额
     */
    private Float amount;

    /**
     * 订单状态(订单的进行中的状态)
     */
    private String status;

    /**
     * 订单的类型（未签订，已经签订）
     */
    private String otype;

    /**
     * 订单生命周期
     */
    private String lifecycle;
    
	/**
     * 备注
     */
    private String remark;
    
    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 修改人
     */
    private String updater;
    
    
    /**  
	 * updatetime  
	 *  
	 * @return  the updatetime  
	 * @since   1.0.0  
	 */
	
	public Date getUpdatetime() {
		return updatetime;
	}

	/**  
	 * @param updatetime the updatetime to set  
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/**  
	 * updater  
	 *  
	 * @return  the updater  
	 * @since   1.0.0  
	 */
	
	public String getUpdater() {
		return updater;
	}

	/**  
	 * @param updater the updater to set  
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	/**  
	 * lifecycle  
	 *  
	 * @return  the lifecycle  
	 * @since   1.0.0  
	 */
	
	public String getLifecycle() {
		return lifecycle;
	}

	/**  
	 * @param lifecycle the lifecycle to set  
	 */
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	
	public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid == null ? null : sellerid.trim();
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid == null ? null : buyerid.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public float getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(float totalnum) {
        this.totalnum = totalnum;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getLimittime() {
        return limittime;
    }

    public void setLimittime(Date limittime) {
        this.limittime = limittime;
    }

    public Float getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Float totalamount) {
        this.totalamount = totalamount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype == null ? null : otype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}