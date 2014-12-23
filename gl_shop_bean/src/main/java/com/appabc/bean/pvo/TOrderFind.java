package com.appabc.bean.pvo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appabc.bean.bo.MatchingBean;
import com.appabc.bean.bo.MoreAreaInfos;
import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderMoreAreaEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderOverallStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderStatusEnum;
import com.appabc.bean.enums.OrderFindInfo.OrderTypeEnum;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月19日 下午10:00:44
 */
public class TOrderFind extends BaseBean implements Cloneable{
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
    private OrderTypeEnum type;

    /**
     * 地址由谁来指定
     */
    private OrderAddressTypeEnum addresstype;

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
    private OrderMoreAreaEnum morearea;

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
    private OrderStatusEnum status;

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
    private OrderOverallStatusEnum overallstatus;
    
    /**
     * 撮合合同次数
     */
    private Integer matchingnum; 
    
    private String pname; //产品名称
    private String pcode; //商品类型（大类）
    private String ptype; //产品种类（二级分类CODE）
    private String unit; // 单位
    /**
     * 多地域发布信息，内容为JSON格式，包含地域和单价
     * 例：[{\"price\":\"12\",\"area\":\"A_003\"},{\"price\":\"14\",\"area\":\"A_006\"}]
     */
    private String moreAreaInfos; 
    private List<MoreAreaInfos> moreAreaList = new ArrayList<MoreAreaInfos>(); // 我的供求列表页面的多地域信息
    
    private MatchingBean matchingBean; // 询单自动匹配列表
    
    public MatchingBean getMatchingBean() {
		return matchingBean;
	}

	public void setMatchingBean(MatchingBean matchingBean) {
		this.matchingBean = matchingBean;
	}

	public List<MoreAreaInfos> getMoreAreaList() {
		return moreAreaList;
	}

	public void setMoreAreaList(List<MoreAreaInfos> moreAreaList) {
		this.moreAreaList = moreAreaList;
	}

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

    public OrderAddressTypeEnum getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(OrderAddressTypeEnum addresstype) {
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

	public OrderMoreAreaEnum getMorearea() {
		return morearea;
	}

	public void setMorearea(OrderMoreAreaEnum morearea) {
		this.morearea = morearea;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public OrderOverallStatusEnum getOverallstatus() {
		return overallstatus;
	}

	public void setOverallstatus(OrderOverallStatusEnum overallstatus) {
		this.overallstatus = overallstatus;
	}

	public OrderTypeEnum getType() {
		return type;
	}

	public void setType(OrderTypeEnum type) {
		this.type = type;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public Integer getMatchingnum() {
		return matchingnum;
	}

	public void setMatchingnum(Integer matchingnum) {
		this.matchingnum = matchingnum;
	}


}