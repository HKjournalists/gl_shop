package com.appabc.pay.bean;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

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
    private String otype;

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
    private Integer direction;

    /**
     * 支付方式
     */
    private String paytype;

    /**
     * 支付时间
     */
    private Date patytime;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 指通过什么设备支付（手机、电脑）
     */
    private String devices;

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

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype == null ? null : otype.trim();
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

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public Date getPatytime() {
        return patytime;
    }

    public void setPatytime(Date patytime) {
        this.patytime = patytime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices == null ? null : devices.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}