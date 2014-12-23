package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;

public class TPublicCodes extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8326131557166877226L;

    /**
     * 代码集
     */
    private String code;

    /**
     * 代码
     */
    private String val;

    /**
     * 名称
     */
    private String name;

    /**
     * 关联属性编号(父编号)
     */
    private String pcode;
    
    /**
     * 序号
     */
    private Integer orderno;

    public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val == null ? null : val.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode == null ? null : pcode.trim();
    }
}