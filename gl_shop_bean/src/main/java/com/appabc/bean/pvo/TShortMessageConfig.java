package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TShortMessageConfig extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8429969442727915571L;

    /**
     * 账号
     */
    private String suser;

    /**
     * 密码
     */
    private String spwd;

    /**
     * 接口地址
     */
    private String surl;

    /**
     * 状态(启用、停用)
     */
    private Integer status;

    /**
     * 类型(电信、移动、联通等)
     */
    private Integer type;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date updatetiime;
    
    private String templateid; // 短信模板ID
    
    private String templateparam; // 短信模板参数
    
    private String templatetype; // 模板类型(验证码模板，其它模板)
    
    private String tokenurl; // token获取接口地址

    public String getSuser() {
        return suser;
    }

    public void setSuser(String suser) {
        this.suser = suser == null ? null : suser.trim();
    }


    public String getSpwd() {
		return spwd;
	}

	public void setSpwd(String spwd) {
		this.spwd = spwd;
	}

	public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl == null ? null : surl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdatetiime() {
        return updatetiime;
    }

    public void setUpdatetiime(Date updatetiime) {
        this.updatetiime = updatetiime;
    }

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getTemplateparam() {
		return templateparam;
	}

	public void setTemplateparam(String templateparam) {
		this.templateparam = templateparam;
	}

	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}

	public String getTokenurl() {
		return tokenurl;
	}

	public void setTokenurl(String tokenurl) {
		this.tokenurl = tokenurl;
	}
    
}