package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TSystemParams extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6628784004570012289L;

    /**
     * 系统参考名
     */
    private String pname;

    /**
     * 系统参数值
     */
    private String pvalue;

    /**
     * 系统参数类型(数据类型)
     */
    private String ptype;

    /**
     * 默认值
     */
    private String defaultvalue;

    /**
     * 修改人
     */
    private String updater;

    /**
     * 修改时间
     */
    private Date updatetime;
    
    /**
     * 描述
     */
    private String description;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue == null ? null : pvalue.trim();
    }

    public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue == null ? null : defaultvalue.trim();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}