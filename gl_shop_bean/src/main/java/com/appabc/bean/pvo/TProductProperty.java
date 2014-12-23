package com.appabc.bean.pvo;

import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.common.base.bean.BaseBean;

public class TProductProperty extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3519296813209185469L;

    /**
     * 商品ID
     */
    private String pid;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值数据类型
     */
    private String types;

    /**
     * 最大值
     */
    private Float maxv;

    /**
     * 最小值
     */
    private Float minv;

    /**
     * 值
     */
    private String content;

    /**
     * 属性状态（类似分类,区分是否直接做为商品的详情展示）
     */
    private PropertyStatusEnum status;
    
    /**
     * 序号
     */
    private Integer orderno;
    
    /**
     * 属性编码
     */
    private String code;
    
    /**
     * 单位
     */
    private UnitEnum unit;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types == null ? null : types.trim();
    }

    public Float getMaxv() {
        return maxv;
    }

    public void setMaxv(Float maxv) {
        this.maxv = maxv;
    }

    public Float getMinv() {
        return minv;
    }

    public void setMinv(Float minv) {
        this.minv = minv;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public PropertyStatusEnum getStatus() {
		return status;
	}

	public void setStatus(PropertyStatusEnum status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public UnitEnum getUnit() {
		return unit;
	}

	public void setUnit(UnitEnum unit) {
		this.unit = unit;
	}
	
	
}