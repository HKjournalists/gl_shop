package com.appabc.bean.pvo;

import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchOpTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderFindMatchStatusEnum;
import com.appabc.common.base.bean.BaseBean;

public class TOrderFindMatch extends BaseBean {
    /**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1972395393374287243L;

	/**
     * 记录ID
     */
    //private Integer rid;

    /**
     * 所有者
     */
    private String owner;

    /**
     * 目标者
     */
    private String target;

    /**
     * 保证金
     */
    private Double guaranty;

    /**
     * 操作类型
     */
    private OrderFindMatchOpTypeEnum opType;

    /**
     * 记录状态
     */
    private OrderFindMatchStatusEnum status;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 所有者的父询单ID
     */
    private String opfid;

    /**
     * 所有者的子询单ID
     */
    private String ocfid;

    /**
     * 目标者的询单ID
     */
    private String tfid;

    /**
     * 备注
     */
    private String remark;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public Double getGuaranty() {
        return guaranty;
    }

    public void setGuaranty(Double guaranty) {
        this.guaranty = guaranty;
    }

    public OrderFindMatchOpTypeEnum getOpType() {
        return opType;
    }

    public void setOpType(OrderFindMatchOpTypeEnum opType) {
        this.opType = opType;
    }

    public OrderFindMatchStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderFindMatchStatusEnum status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getOpfid() {
        return opfid;
    }

    public void setOpfid(String opfid) {
        this.opfid = opfid == null ? null : opfid.trim();
    }

    public String getOcfid() {
        return ocfid;
    }

    public void setOcfid(String ocfid) {
        this.ocfid = ocfid == null ? null : ocfid.trim();
    }

    public String getTfid() {
        return tfid;
    }

    public void setTfid(String tfid) {
        this.tfid = tfid == null ? null : tfid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}