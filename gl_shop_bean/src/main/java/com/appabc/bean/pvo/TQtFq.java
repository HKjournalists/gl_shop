package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TQtFq extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 462684756482221865L;

    /**
     * 反馈内容
     */
    private String quetsion;

    /**
     * 反馈人
     */
    private String askid;

    /**
     * 反馈时间
     */
    private Date createtime;

    /**
     * 反馈设备(安卓、苹果)
     */
    private String devices;

    /**
     * 状态(未处理、已处理)
     */
    private Integer status;

    public String getQuetsion() {
        return quetsion;
    }

    public void setQuetsion(String quetsion) {
        this.quetsion = quetsion == null ? null : quetsion.trim();
    }

    public String getAskid() {
        return askid;
    }

    public void setAskid(String askid) {
        this.askid = askid == null ? null : askid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices == null ? null : devices.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}