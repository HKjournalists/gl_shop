/**  
 * com.appabc.pay.bean.TPayThirdOrgInfo.java  
 *   
 * 2015年3月2日 下午2:35:50  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.pay.bean;

import java.util.Date;

import com.appabc.bean.enums.PurseInfo.DeviceType;
import com.appabc.bean.enums.PurseInfo.PayDirection;
import com.appabc.bean.enums.PurseInfo.PayInstitution;
import com.appabc.bean.enums.PurseInfo.PayWay;
import com.appabc.bean.enums.PurseInfo.TradeStatus;
import com.appabc.bean.enums.PurseInfo.TradeType;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年3月2日 下午2:35:50
 */

public class TPayThirdOrgInfo extends BaseBean {

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = -5990569479065850777L;

	
	/*`PID` varchar(50) NOT NULL COMMENT '支付流水号',*/
	/**
	 * `PASSID` varchar(50) DEFAULT NULL COMMENT '钱包编号',
	 *
     * 钱包编号 
     */
    private String passid;

    
    /**
     * `OID` varchar(50) DEFAULT NULL COMMENT '交易编号（订单号、支付纪录）',
     * 交易编号（订单号、支付纪录）
     */
    private String oid;
    
    /**
     * `OTYPE` varchar(20) DEFAULT NULL COMMENT '交易类型（充值、提取、交易缴款、交易收款、交易违约金扣款、交易服务费）',
     * 交易类型（充值、提取、交易缴款、交易收款、交易违约金扣款、交易服务费）
     */
    private TradeType otype;

    /**
     * `PAYNO` varchar(50) NOT NULL COMMENT '银行流水号',
     * 银行流水号
     */
    private String payno;

    
    /**
     * `NAME` varchar(50) DEFAULT NULL COMMENT '交易方名称',
     * 交易方名称
     */
    private String name;

    
    /**
     * `AMOUNT` float(12,3) NOT NULL COMMENT '金额',
     * 金额
     */
    private Double amount;

    
    /**
     * `DIRECTION` int(11) NOT NULL COMMENT '流入流出',
     * 流入流出
     */
    private PayDirection direction;

    
    /**
     * `PAYTYPE` varchar(20) NOT NULL COMMENT '支付方式',
     * 支付方式
     */
    private PayWay paytype;

    
    /**
     * `PATYTIME` datetime NOT NULL COMMENT '支付时间',
     * 支付时间
     */
    private Date patytime;

    /**
     * `STATUS` varchar(20) NOT NULL COMMENT '交易状态',
     * 交易状态
     */
    private TradeStatus status;
    
    /**
     * `DEVICES` varchar(20) DEFAULT NULL COMMENT '指通过什么设备支付（手机、电脑）',
     * 指通过什么设备支付（手机、电脑） 
     */
    private DeviceType devices;

    /**
     * `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
     * 备注 
     */
    private String remark;
	
    /**
     * `TN` varchar(50) DEFAULT NULL COMMENT '交易中的TN',
     * 交易中的TN 
     */
    private String tn;
    
    /**
     * `TNTIME` varchar(50) DEFAULT NULL COMMENT '交易时间撮',
     * 交易时间撮 
     */
    private String tnTime;
    
    /**
     * `QUERYID` varchar(50) DEFAULT NULL COMMENT '查询ID',
     * 查询ID 
     */
    private String queryId;
    
    /**
     * `PAYORG` varchar(20) DEFAULT NULL COMMENT '支付机构',
     * 支付机构 
     */
    private PayInstitution payInstitution;
    
    /**
     * `ACCNO` varchar(50) DEFAULT NULL COMMENT '支付卡号',
     * 支付卡号 
     */
    private String payAccountNo;

	/**  
	 * passid  
	 *  
	 * @return  the passid  
	 * @since   1.0.0  
	 */
	
	public String getPassid() {
		return passid;
	}

	/**  
	 * @param passid the passid to set  
	 */
	public void setPassid(String passid) {
		this.passid = passid;
	}

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
	 * payno  
	 *  
	 * @return  the payno  
	 * @since   1.0.0  
	 */
	
	public String getPayno() {
		return payno;
	}

	/**  
	 * @param payno the payno to set  
	 */
	public void setPayno(String payno) {
		this.payno = payno;
	}

	/**  
	 * name  
	 *  
	 * @return  the name  
	 * @since   1.0.0  
	 */
	
	public String getName() {
		return name;
	}

	/**  
	 * @param name the name to set  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * amount  
	 *  
	 * @return  the amount  
	 * @since   1.0.0  
	 */
	
	public Double getAmount() {
		return amount;
	}

	/**  
	 * @param amount the amount to set  
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
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
	 * patytime  
	 *  
	 * @return  the patytime  
	 * @since   1.0.0  
	 */
	
	public Date getPatytime() {
		return patytime;
	}

	/**  
	 * @param patytime the patytime to set  
	 */
	public void setPatytime(Date patytime) {
		this.patytime = patytime;
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
	 * remark  
	 *  
	 * @return  the remark  
	 * @since   1.0.0  
	 */
	
	public String getRemark() {
		return remark;
	}

	/**  
	 * @param remark the remark to set  
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**  
	 * tn  
	 *  
	 * @return  the tn  
	 * @since   1.0.0  
	 */
	
	public String getTn() {
		return tn;
	}

	/**  
	 * @param tn the tn to set  
	 */
	public void setTn(String tn) {
		this.tn = tn;
	}

	/**  
	 * tnTime  
	 *  
	 * @return  the tnTime  
	 * @since   1.0.0  
	 */
	
	public String getTnTime() {
		return tnTime;
	}

	/**  
	 * @param tnTime the tnTime to set  
	 */
	public void setTnTime(String tnTime) {
		this.tnTime = tnTime;
	}

	/**  
	 * queryId  
	 *  
	 * @return  the queryId  
	 * @since   1.0.0  
	 */
	
	public String getQueryId() {
		return queryId;
	}

	/**  
	 * @param queryId the queryId to set  
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**  
	 * payInstitution  
	 *  
	 * @return  the payInstitution  
	 * @since   1.0.0  
	*/  
	
	public PayInstitution getPayInstitution() {
		return payInstitution;
	}

	/**  
	 * @param payInstitution the payInstitution to set  
	 */
	public void setPayInstitution(PayInstitution payInstitution) {
		this.payInstitution = payInstitution;
	}

	/**  
	 * payAccountNo  
	 *  
	 * @return  the payAccountNo  
	 * @since   1.0.0  
	*/  
	
	public String getPayAccountNo() {
		return payAccountNo;
	}

	/**  
	 * @param payAccountNo the payAccountNo to set  
	 */
	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}
	
}
