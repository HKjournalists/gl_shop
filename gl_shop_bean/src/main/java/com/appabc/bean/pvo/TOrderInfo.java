package com.appabc.bean.pvo;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
import com.appabc.bean.enums.ContractInfo.ContractType;
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
    private Double price;

    /**
     * 数量
     */
    private double totalnum;

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
    private Double totalamount;

    /**
     * 支付金额
     */
    private Double amount;
    
    /**
     * 结算价格
     * */
    private Double settleamount;

    /**
     * 订单状态(订单的进行中的状态)
     */
    private ContractStatus status;

    /**
     * 订单的类型（未签订，已经签订）
     */
    private ContractType otype;

    /**
     * 订单生命周期
     */
    private ContractLifeCycle lifecycle;
    
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(double totalnum) {
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

    public Double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Double totalamount) {
        this.totalamount = totalamount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public ContractStatus getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(ContractStatus status) {
		this.status = status;
	}

	/**  
	 * lifecycle  
	 *  
	 * @return  the lifecycle  
	 * @since   1.0.0  
	*/  
	
	public ContractLifeCycle getLifecycle() {
		return lifecycle;
	}

	/**  
	 * @param lifecycle the lifecycle to set  
	 */
	public void setLifecycle(ContractLifeCycle lifecycle) {
		this.lifecycle = lifecycle;
	}

	/**  
	 * otype  
	 *  
	 * @return  the otype  
	 * @since   1.0.0  
	*/  
	
	public ContractType getOtype() {
		return otype;
	}

	/**  
	 * @param otype the otype to set  
	 */
	public void setOtype(ContractType otype) {
		this.otype = otype;
	}

	/**  
	 * settleamount  
	 *  
	 * @return  the settleamount  
	 * @since   1.0.0  
	*/  
	
	public Double getSettleamount() {
		return settleamount;
	}

	/**  
	 * @param settleamount the settleamount to set  
	 */
	public void setSettleamount(Double settleamount) {
		this.settleamount = settleamount;
	}
}