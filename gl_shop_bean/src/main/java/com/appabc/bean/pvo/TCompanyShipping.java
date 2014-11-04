package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCompanyShipping extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9096712518129776338L;

    /**
     * 
     */
    private Integer authid;

    /**
     * 船舶名称
     */
    private String sname;

    /**
     * 船籍港
     */
    private String pregistry;

    /**
     * 船舶登记号
     */
    private String sno;

    /**
     * 船舶检验机构
     */
    private String sorg;

    /**
     * 船舶所有人
     */
    private String sowner;

    /**
     * 船舶经营人
     */
    private String sbusinesser;

    /**
     * 船舶种类
     */
    private String stype;

    /**
     * 船舶建成日期
     */
    private String screatetime;

    /**
     * 总吨位
     */
    private Float stotal;

    /**
     * 载重
     */
    private Float sload;

    /**
     * 船长
     */
    private Float slength;

    /**
     * 船宽
     */
    private Float swidth;

    /**
     * 型深
     */
    private Float sdeep;

    /**
     * 满载吃水
     */
    private Float sover;

    /**
     * 船体材料
     */
    private Float smateriall;

    /**
     * 更新时间
     */
    private Date updatedate;

    public Integer getAuthid() {
        return authid;
    }

    public void setAuthid(Integer authid) {
        this.authid = authid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getPregistry() {
        return pregistry;
    }

    public void setPregistry(String pregistry) {
        this.pregistry = pregistry == null ? null : pregistry.trim();
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno == null ? null : sno.trim();
    }

    public String getSorg() {
        return sorg;
    }

    public void setSorg(String sorg) {
        this.sorg = sorg == null ? null : sorg.trim();
    }

    public String getSowner() {
        return sowner;
    }

    public void setSowner(String sowner) {
        this.sowner = sowner == null ? null : sowner.trim();
    }

    public String getSbusinesser() {
        return sbusinesser;
    }

    public void setSbusinesser(String sbusinesser) {
        this.sbusinesser = sbusinesser == null ? null : sbusinesser.trim();
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype == null ? null : stype.trim();
    }

    public String getScreatetime() {
        return screatetime;
    }

    public void setScreatetime(String screatetime) {
        this.screatetime = screatetime == null ? null : screatetime.trim();
    }

    public Float getStotal() {
        return stotal;
    }

    public void setStotal(Float stotal) {
        this.stotal = stotal;
    }

    public Float getSload() {
        return sload;
    }

    public void setSload(Float sload) {
        this.sload = sload;
    }

    public Float getSlength() {
        return slength;
    }

    public void setSlength(Float slength) {
        this.slength = slength;
    }

    public Float getSwidth() {
        return swidth;
    }

    public void setSwidth(Float swidth) {
        this.swidth = swidth;
    }

    public Float getSdeep() {
        return sdeep;
    }

    public void setSdeep(Float sdeep) {
        this.sdeep = sdeep;
    }

    public Float getSover() {
        return sover;
    }

    public void setSover(Float sover) {
        this.sover = sover;
    }

    public Float getSmateriall() {
        return smateriall;
    }

    public void setSmateriall(Float smateriall) {
        this.smateriall = smateriall;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}