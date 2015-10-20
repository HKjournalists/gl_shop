package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.BusinessType;
import com.appabc.bean.enums.PurseInfo.OnOffLine;
import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.common.base.bean.BaseBean;

public class TOfflinePay extends BaseBean {
    /**  
	 * serialVersionUID（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

    /**
     * 支付流水号
     */
    private String pid;

    /**
     * 订单编号
     */
    private String oid;

    /**
     * 保证金、货款
     */
    private PurseType otype;

    /**
     * 应收金额
     */
    private Double total;

    /**
     * 实际收款金额
     */
    private Double amount;

    /**
     * 线上、线下（主要线下）
     */
    private OnOffLine ptype;

    /**
     * 收款人
     */
    private String creater;

    /**
     * 收款时间
     */
    private Date createtime;

    /**
     * 状态
     */
    private TradeStatus status;

    /**
     * 处理人员
     */
    private String dealer;

    /**
     * 处理时间
     */
    private Date dealtime;

    /**
     * 处理结果
     */
    private String dealresult;

    /**
     * 业务类型[支付和充值]
     */
    private BusinessType btype;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer == null ? null : dealer.trim();
    }

    public Date getDealtime() {
        return dealtime;
    }

    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }

    public String getDealresult() {
        return dealresult;
    }

    public void setDealresult(String dealresult) {
        this.dealresult = dealresult == null ? null : dealresult.trim();
    }

	/**  
	 * otype  
	 *  
	 * @return  the otype  
	 * @since   1.0.0  
	*/  
	
	public PurseType getOtype() {
		return otype;
	}

	/**  
	 * @param otype the otype to set  
	 */
	public void setOtype(PurseType otype) {
		this.otype = otype;
	}

	/**  
	 * ptype  
	 *  
	 * @return  the ptype  
	 * @since   1.0.0  
	*/  
	
	public OnOffLine getPtype() {
		return ptype;
	}

	/**  
	 * @param ptype the ptype to set  
	 */
	public void setPtype(OnOffLine ptype) {
		this.ptype = ptype;
	}

	/**  
	 * btype  
	 *  
	 * @return  the btype  
	 * @since   1.0.0  
	*/  
	
	public BusinessType getBtype() {
		return btype;
	}

	/**  
	 * @param btype the btype to set  
	 */
	public void setBtype(BusinessType btype) {
		this.btype = btype;
	}

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public TradeStatus getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(TradeStatus status) {
		this.status = status;
	}

	/**  
	 * createtime  
	 *  
	 * @return  the createtime  
	 * @since   1.0.0  
	*/  
	
	public Date getCreatetime() {
		return createtime;
	}

	/**  
	 * @param createtime the createtime to set  
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}