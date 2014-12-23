package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.bean.BaseBean;

public class TPassbookThirdCheck extends BaseBean {
    /**  
	 * serialVersionUID  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;

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
     * 金额
     */
    private Float amount;

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
    private Date patytime;

    /**
     * 交易状态
     */
    private TradeStatus status;

    /**
     * 指通过什么设备支付（手机、电脑）
     */
    private DeviceType devices;

    /**
     * 备注
     */
    private String remark;

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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getPatytime() {
        return patytime;
    }

    public void setPatytime(Date patytime) {
        this.patytime = patytime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}