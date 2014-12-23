package com.appabc.bean.pvo;

import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.common.base.bean.BaseBean;

public class TProductInfo extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4194599963062317463L;

    /**
     * 商品名称
     */
    private String pname;

    /**
     * 商品类型(黄沙、石子)
     */
    private String pcode;

    /**
     * 种类
     */
    private String ptype;

    /**
     * 规格
     */
    private String psize;

    /**
     * 颜色
     */
    private String pcolor;

    /**
     * 产地
     */
    private String paddress;
    
    /**
     * 单位
     */
    private UnitEnum unit;

    /**
     * 备注
     */
    private String remark;
    
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

	public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode == null ? null : pcode.trim();
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype == null ? null : ptype.trim();
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize == null ? null : psize.trim();
    }

    public String getPcolor() {
        return pcolor;
    }

    public void setPcolor(String pcolor) {
        this.pcolor = pcolor == null ? null : pcolor.trim();
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress == null ? null : paddress.trim();
    }

    public UnitEnum getUnit() {
		return unit;
	}

	public void setUnit(UnitEnum unit) {
		this.unit = unit;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}