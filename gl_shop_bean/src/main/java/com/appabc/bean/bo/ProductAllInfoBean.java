/**
 *
 */
package com.appabc.bean.bo;

import java.util.ArrayList;
import java.util.List;

import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月6日 下午4:47:20
 */
/**
 * @Description : 商品全部信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月6日 下午4:49:57
 */
public class ProductAllInfoBean extends BaseBean{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5653034136118317551L;

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
     * 规格
     */
    private TProductProperty psize;
    
    /**
     * 排序
     */
    private int orderno;
    
    /**
     * 商品参数
     */
    List<TProductProperty> propertyList = new ArrayList<TProductProperty>(); 

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

	public TProductProperty getPsize() {
		return psize;
	}

	public void setPsize(TProductProperty psize) {
		this.psize = psize;
	}

	public List<TProductProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<TProductProperty> propertyList) {
		this.propertyList = propertyList;
	}

	public int getOrderno() {
		return orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
    
}
