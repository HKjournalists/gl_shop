package com.appabc.datas.system;

import com.appabc.bean.enums.PurseInfo;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zouxifeng on 12/4/14.
 */
@Component
public class Remittance {

    public enum Status {
        PROCESSING(0, "待处理"), AUDITING(1, "待审核"), AUDIT_FAIL(2, "审核不通过"),
        FINISH(3, "完成"), UNSOLVABLE(4, "无法处理");

        private int value;
        private String text;

        private Status(int value, String text) {
            this.value = value;
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public String getText() {
            return text;
        }

        public final static Status valueOf(int value) {
            for (Status s : Status.values()) {
                if (s.value == value) {
                    return s;
                }
            }

            throw new IllegalArgumentException("Invalid remittance status value.");
        }

    }

    private int id;
    private float amount;
    private String remitterAccount;
    private Date receiveTime;
    private String bankSerialNumber;
    private String mobile;
    private String remitter;
    private String contractId;
    private String company;
    private String remark;
    private User creator;
    private User processor;
    private User auditor;
    private Status status;
    private String targetCompanyId;
    private PurseInfo.PurseType targetWalletType;
    private Date createTime;
    private Date updateTime;
    private Date finishTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getRemitterAccount() {
        return remitterAccount;
    }

    public void setRemitterAccount(String remitterAccount) {
        this.remitterAccount = remitterAccount;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getBankSerialNumber() {
        return bankSerialNumber;
    }

    public void setBankSerialNumber(String bankSerialNumber) {
        this.bankSerialNumber = bankSerialNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemitter() {
        return remitter;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getProcessor() {
        return processor;
    }

    public void setProcessor(User processor) {
        this.processor = processor;
    }

    public User getAuditor() {
        return auditor;
    }

    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTargetCompanyId() {
        return targetCompanyId;
    }

    public void setTargetCompanyId(String targetCompanyId) {
        this.targetCompanyId = targetCompanyId;
    }

    public PurseInfo.PurseType getTargetWalletType() {
        return targetWalletType;
    }

    public void setTargetWalletType(PurseInfo.PurseType targetWalletType) {
        this.targetWalletType = targetWalletType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
