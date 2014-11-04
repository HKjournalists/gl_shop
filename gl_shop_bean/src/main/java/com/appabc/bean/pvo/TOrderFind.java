package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月19日 下午10:00:44
 */
public class TOrderFind extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8652475511208134764L;

    /**
     * 企业编号
     */
    private String cid;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型（买和卖）
     */
    private Integer type;

    /**
     * 地址由谁来指定
     */
    private Integer addresstype;

    /**
     * 价格
     */
    private Float price;

    /**
     * 数量
     */
    private Float totalnum;

    /**
     * 当前数量
     */
    private Float num;

    /**
     * 开始日期
     */
    private Date starttime;

    /**
     * 结束日期
     */
    private Date endtime;

    /**
     * 是否发布多个地区（卖家的需求）
     */
    private String morearea;

    /**
     * 区域
     */
    private String area; 

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date creatime;

    /**
     * 有效期
     */
    private Date limitime;

    /**
     * 状态（有效、失效）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 标识询单的层级
     */
    private String parentid;
    
    /**
     * 更新人
     */
    private String updater;
    
    /**
     *  更新时间
     */
    private Date updatetime;
    
    /**
     * 状态(已发布，审核不通过，已取消，已过期，销售量为0，已产生订单)
     */
    private Integer overallstatus;
    
    private String pname; //产品名称
    private String ptype; //产品类型
    private String unit; // 单位
    /**
     * 多地域发布信息，内容为JSON格式，包含地域和单价
     * 例：[{\"price\":\"12\",\"area\":\"A_003\"},{\"price\":\"14\",\"area\":\"A_006\"}]
     */
    private String moreAreaInfos; 
    
    public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(Integer addresstype) {
        this.addresstype = addresstype;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Float totalnum) {
		this.totalnum = totalnum;
	}

	public Float getNum() {
		return num;
	}

	public void setNum(Float num) {
		this.num = num;
	}

	public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getMorearea() {
        return morearea;
    }

    public void setMorearea(String morearea) {
        this.morearea = morearea == null ? null : morearea.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Date getLimitime() {
        return limitime;
    }

    public void setLimitime(Date limitime) {
        this.limitime = limitime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getMoreAreaInfos() {
		return moreAreaInfos;
	}

	public void setMoreAreaInfos(String moreAreaInfos) {
		this.moreAreaInfos = moreAreaInfos;
	}

	public Integer getOverallstatus() {
		return overallstatus;
	}

	public void setOverallstatus(Integer overallstatus) {
		this.overallstatus = overallstatus;
	}
    

}