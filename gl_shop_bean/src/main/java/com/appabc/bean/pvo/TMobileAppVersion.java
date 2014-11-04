package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TMobileAppVersion extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8915460958991273896L;

    /**
     * 反馈设备(安卓、苹果)
     */
    private String devices;

    /**
     * 最新版本名称
     */
    private String lastname;

    /**
     * 最新版本号
     */
    private String lastest;

    /**
     * 最新版本序号
     */
    private Integer lastno;

    /**
     * 最新版本描述
     */
    private String mark;

    /**
     * 文件大小
     */
    private String filesize;

    /**
     * 下载地址
     */
    private String downurl;

    /**
     * 是否强制更新
     */
    private String isforce;

    /**
     * 更新频率
     */
    private Integer fequency;

    /**
     * 更新（天、星期）
     */
    private String unit;

    /**
     * 修改人
     */
    private String updater;

    /**
     * 修改时间
     */
    private Date updatetime;

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices == null ? null : devices.trim();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname == null ? null : lastname.trim();
    }

    public String getLastest() {
        return lastest;
    }

    public void setLastest(String lastest) {
        this.lastest = lastest == null ? null : lastest.trim();
    }

    public Integer getLastno() {
        return lastno;
    }

    public void setLastno(Integer lastno) {
        this.lastno = lastno;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize == null ? null : filesize.trim();
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl == null ? null : downurl.trim();
    }

    public String getIsforce() {
        return isforce;
    }

    public void setIsforce(String isforce) {
        this.isforce = isforce == null ? null : isforce.trim();
    }

    public Integer getFequency() {
        return fequency;
    }

    public void setFequency(Integer fequency) {
        this.fequency = fequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}