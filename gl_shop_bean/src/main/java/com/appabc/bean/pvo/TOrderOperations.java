package com.appabc.bean.pvo;

import com.appabc.bean.enums.ContractInfo.ContractLifeCycle;
import com.appabc.bean.enums.ContractInfo.ContractOperateType;
import com.appabc.common.base.bean.BaseBean;

import java.util.Date;

public class TOrderOperations extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5172993826180203359L;

    /**
     * 订单编号
     */
    private String oid;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作时间
     */
    private Date operationtime;

    /**
     * 操作的具体内容
     */
    private ContractOperateType type;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 操作时候的合同当前状态
     * */
    private ContractLifeCycle orderstatus;
    
	/**
     * 父操作ID
     */
    private String plid;

    /**
     * 备注
     */
    private String remark;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(Date operationtime) {
        this.operationtime = operationtime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getPlid() {
        return plid;
    }

    public void setPlid(String plid) {
        this.plid = plid == null ? null : plid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	/**  
	 * type  
	 *  
	 * @return  the type  
	 * @since   1.0.0  
	*/  
	
	public ContractOperateType getType() {
		return type;
	}

	/**  
	 * @param type the type to set  
	 */
	public void setType(ContractOperateType type) {
		this.type = type;
	}

	/**  
	 * orderstatus  
	 *  
	 * @return  the orderstatus  
	 * @since   1.0.0  
	*/  
	
	public ContractLifeCycle getOrderstatus() {
		return orderstatus;
	}

	/**  
	 * @param orderstatus the orderstatus to set  
	 */
	public void setOrderstatus(ContractLifeCycle orderstatus) {
		this.orderstatus = orderstatus;
	}
    
}