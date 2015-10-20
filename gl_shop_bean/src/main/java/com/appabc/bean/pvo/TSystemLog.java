package com.appabc.bean.pvo;

import java.util.Date;

import com.appabc.bean.enums.SysLogEnum.LogBusinessType;
import com.appabc.bean.enums.SysLogEnum.LogLevel;
import com.appabc.common.base.bean.BaseBean;

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
    private LogBusinessType businesstype;

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
    private LogLevel loglevel;

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
		this.businessid = businessid;
	}

	public LogBusinessType getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(LogBusinessType businesstype) {
		this.businesstype = businesstype;
	}

	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	public Integer getLogtype() {
		return logtype;
	}

	public void setLogtype(Integer logtype) {
		this.logtype = logtype;
	}

	public LogLevel getLoglevel() {
		return loglevel;
	}

	public void setLoglevel(LogLevel loglevel) {
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
		this.creater = creater;
	}

}