package com.appabc.datas.system;

import com.appabc.bean.pvo.TCompanyInfo;

import java.util.Date;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 23, 2014 4:10:34 PM
 */
public class Task {

	private int id;
    private TCompanyInfo company;
	private TaskType type;
	private User owner;
    private String objectId;
    private Object taskObject;
    private Date claimTime;
	private boolean finished;
	private Date finishTime;
    private Date createTime;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
    public Object getTaskObject() {
        return taskObject;
    }

    public void setTaskObject(Object taskObject) {
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

}
