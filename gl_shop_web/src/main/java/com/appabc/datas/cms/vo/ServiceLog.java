package com.appabc.datas.cms.vo;

import java.util.Date;

/**
 * Created by zouxifeng on 3/25/15.
 */
public class ServiceLog {
    private int id;
    private String companyId;
    private String objectId;
    private ServiceLogType type;
    private User operator;
    private String content;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ServiceLogType getType() {
        return type;
    }

    public void setType(ServiceLogType type) {
        this.type = type;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
