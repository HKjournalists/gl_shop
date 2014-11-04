package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TSystemMessage extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2229648798709014992L;

    /**
     * 企业编号
     */
    private String qyid;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 业务ID
     */
    private String businessid;

    /**
     * 业务类型
     */
    private String businesstype;

    /**
     * 消息状态:已读，未读
     */
    private Integer status;

    /**
     * 消息创建时间
     */
    private Date createtime;

    /**
     * 读取时间
     */
    private Date readtime;

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid == null ? null : qyid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid == null ? null : businessid.trim();
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype == null ? null : businesstype.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }
}