package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;

public class TOrderProductInfo extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6410876863194809595L;

    /**
     * 询单ID
     */
    private String fid;

    /**
     * 询单实体ID
     */
    private String sid;

    /**
     * 商品ID
     */
    private String pid;

    /**
     * 商品名称
     */
    private String pname;

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
     * 产地（必填）
     */
    private String paddress;
    
    /**
     * 单位
     */
    private String unit;

    /**
     * 备注
     */
    private String premark;
    
    private String productImgIds; // 商品图片，多ID用逗号间隔
    
    private String productPropertys; // 商品属性

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
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

    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPremark() {
		return premark;
	}

	public void setPremark(String premark) {
		this.premark = premark;
	}

	public String getProductPropertys() {
		return productPropertys;
	}

	public void setProductPropertys(String productPropertys) {
		this.productPropertys = productPropertys;
	}

	public String getProductImgIds() {
		return productImgIds;
	}

	public void setProductImgIds(String productImgIds) {
		this.productImgIds = productImgIds;
	}
	
    
}