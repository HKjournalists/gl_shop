package com.appabc.datas.cms.vo.task;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.common.base.bean.BaseBean;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.cms.vo.User;

import java.util.Date;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 4:10:34 PM
 */
public class Task<T> extends BaseBean{

	/**  
	 * serialVersionUID:（用一句话描述这个变量表示什么）  
	 *  
	 * @since 1.0.0  
	 */  
	
	private static final long serialVersionUID = 1L;
	
	private TUser customer;
    private TCompanyInfo company;
	private TaskType type;
	private User owner;
    private String objectId;
    private T taskObject;
    private Date claimTime;
	private boolean finished = false;
	private Date finishTime;
    private Date createTime;
    private Date updateTime;

	public TUser getCustomer() {
		return customer;
	}

	public void setCustomer(TUser customer) {
		this.customer = customer;
	}

	public TCompanyInfo getCompany() {
        return company;
    }

    public void setCompany(TCompanyInfo company) {
        this.company = company;
    }

    public TaskType getType() {
		return type;
	}
	public void setType(TaskType type) {
		this.type = type;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public T getTaskObject() {
        return taskObject;
    }

    public void setTaskObject(T taskObject) {
        this.taskObject = taskObject;
    }
	public Date getClaimTime() {
		return claimTime;
	}
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	/**  
	 * updateTime  
	 *  
	 * @return  the updateTime  
	 * @since   1.0.0  
	*/  
	
	public Date getUpdateTime() {
		return updateTime;
	}

	/**  
	 * @param updateTime the updateTime to set  
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
