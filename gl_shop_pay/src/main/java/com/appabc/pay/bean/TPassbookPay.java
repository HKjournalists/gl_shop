package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.bean.BaseBean;

public class TPassbookPay extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -810793784356305903L;

    /**
     * 钱包编号
     */
    private String passid;

    /**
     * 交易编号（订单号、支付纪录）
     */
    private String oid;

    /**
     * 交易类型（充值、提取、交易缴款、交易收款、交易违约金扣款、交易服务费）
     */
    private TradeType otype;

    /**
     * 银行流水号
     */
    private String payno;

    /**
     * 交易方名称
     */
    private String name;

    /**
     * 实际金额
     */
    private Double amount;

    /**
     * 应收金额
     */
    private Double needamount;

    /**
     * 余额
     */
    private Double balance;
    
    /**
     * 流入流出
     */
    private PayDirection direction;

    /**
     * 支付方式
     */
    private PayWay paytype;

    /**
     * 支付时间
     */
    private Date paytime;

    /**
     * 交易状态
     */
    private String status;

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
     * 指通过什么设备支付（手机、电脑）
     */
    private DeviceType devices;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 父操作ID
     */
    private String ppid;
    
    /**
     * 合同名称(此属性非数据库中字段)
     */
    private String contractname;

    public String getPassid() {
        return passid;
    }

    public void setPassid(String passid) {
        this.passid = passid == null ? null : passid.trim();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getPayno() {
        return payno;
    }

    public void setPayno(String payno) {
        this.payno = payno == null ? null : payno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getNeedamount() {
        return needamount;
    }

    public void setNeedamount(Double needamount) {
        this.needamount = needamount;
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

	/**  
	 * otype  
	 *  
	 * @return  the otype  
	 * @since   1.0.0  
	*/  
	
	public TradeType getOtype() {
		return otype;
	}

	/**  
	 * @param otype the otype to set  
	 */
	public void setOtype(TradeType otype) {
		this.otype = otype;
	}

	/**  
	 * direction  
	 *  
	 * @return  the direction  
	 * @since   1.0.0  
	*/  
	
	public PayDirection getDirection() {
		return direction;
	}

	/**  
	 * @param direction the direction to set  
	 */
	public void setDirection(PayDirection direction) {
		this.direction = direction;
	}

	/**  
	 * paytype  
	 *  
	 * @return  the paytype  
	 * @since   1.0.0  
	*/  
	
	public PayWay getPaytype() {
		return paytype;
	}

	/**  
	 * @param paytype the paytype to set  
	 */
	public void setPaytype(PayWay paytype) {
		this.paytype = paytype;
	}

	/**  
	 * devices  
	 *  
	 * @return  the devices  
	 * @since   1.0.0  
	*/  
	
	public DeviceType getDevices() {
		return devices;
	}

	/**  
	 * @param devices the devices to set  
	 */
	public void setDevices(DeviceType devices) {
		this.devices = devices;
	}

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public String getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 * paytime  
	 *  
	 * @return  the paytime  
	 * @since   1.0.0  
	*/  
	
	public Date getPaytime() {
		return paytime;
	}

	/**  
	 * @param paytime the paytime to set  
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	/**  
	 * balance  
	 *  
	 * @return  the balance  
	 * @since   1.0.0  
	*/  
	
	public Double getBalance() {
		return balance;
	}

	/**  
	 * @param balance the balance to set  
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**  
	 * ppid  
	 *  
	 * @return  the ppid  
	 * @since   1.0.0  
	*/  
	
	public String getPpid() {
		return ppid;
	}

	/**  
	 * @param ppid the ppid to set  
	 */
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}

}