package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCompanyEvaluation extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2079945303515816235L;

    /**
     * 企业编号
     */
    private String cid;

    /**
     * 订单编号
     */
    private String oid;

    /**
     * 企业服务满意度（打分）
     */
    private Integer satisfaction;

    /**
     * 信用度
     */
    private Integer credit;

    /**
     * 企业服务评价
     */
    private String evaluation;

    /**
     * 评价时间
     */
    private Date cratedate;

    /**
     * 评价人
     */
    private String creater;
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation == null ? null : evaluation.trim();
    }

    public Date getCratedate() {
        return cratedate;
    }

    public void setCratedate(Date cratedate) {
        this.cratedate = cratedate;
    }
    
	/**  
	 * creater  
	 *  
	 * @return  the creater  
	 * @since   1.0.0  
	 */
	
	public String getCreater() {
		return creater;
	}

	/**  
	 * @param creater the creater to set  
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
    
}