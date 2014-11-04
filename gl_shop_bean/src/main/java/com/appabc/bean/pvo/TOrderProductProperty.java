package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;

public class TOrderProductProperty extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5299686024177768959L;

    /**
     * 编号
     */
    private Integer ppid;

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
    private String status;
    
    /**
     * 序号
     */
    private Integer orderno; 

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
}