package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TSystemLog extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5129956150150980303L;

    /**
     * 业务ID
     */
    private String businessid;

    /**
     * 业务类型
     */
    private String businesstype;

    /**
     * 日志内容
     */
    private String logcontent;

    /**
     * 日志类型
     */
    private Integer logtype;

    /**
     * 日志级别
     */
    private Integer loglevel;

    /**
     * 日志状态
     */
    private Integer logstatus;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建人
     */
    private String creater;

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

    public String getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent == null ? null : logcontent.trim();
    }

    public Integer getLogtype() {
        return logtype;
    }

    public void setLogtype(Integer logtype) {
        this.logtype = logtype;
    }

    public Integer getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(Integer loglevel) {
        this.loglevel = loglevel;
    }

    public Integer getLogstatus() {
        return logstatus;
    }

    public void setLogstatus(Integer logstatus) {
        this.logstatus = logstatus;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}