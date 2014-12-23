package com.appabc.bean.pvo;

import com.appabc.bean.enums.OrderFindInfo.OrderItemEnum;
import com.appabc.common.base.bean.BaseBean;

import java.util.Date;

public class TOrderFindItem extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6812410116389930235L;

    /**
     * 询单ID
     */
    private String fid;

    /**
     * 更新企业
     */
    private String updater;

    /**
     * 创建日期
     */
    private Date createtime;

    /**
     * 处理人（客服）
     */
    private String dealer;

    /**
     * 处理结果（客服）
     */
    private String result;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 状态
     */
    private OrderItemEnum status;

    /**
     * 处理时间
     */
    private Date dealtime;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer == null ? null : dealer.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getDealtime() {
        return dealtime;
    }

    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }

	/**  
	 * status  
	 *  
	 * @return  the status  
	 * @since   1.0.0  
	*/  
	
	public OrderItemEnum getStatus() {
		return status;
	}

	/**  
	 * @param status the status to set  
	 */
	public void setStatus(OrderItemEnum status) {
		this.status = status;
	}

}