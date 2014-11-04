package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TCompanyContact extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 755116813739894683L;

    /**
     * 企业编号
     */
    private String cid;

    /**
     * 姓名
     */
    private String cname;

    /**
     * 电话
     */
    private String cphone;
    
    /**
     * 固定电话
     */
    private String tel;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建人
     */
    private String creater;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone == null ? null : cphone.trim();
    }

    public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}