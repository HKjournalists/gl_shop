/**
 *
 */
package com.appabc.bean.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appabc.bean.enums.OrderFindInfo.OrderAddressTypeEnum;
import com.appabc.bean.enums.ProductInfo.UnitEnum;
import com.appabc.bean.pvo.TOrderProductProperty;
import com.appabc.common.base.bean.BaseBean;

/**
 * @Description : 询单详细信息BEAN
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月11日 下午5:15:17
 */
public class OrderAllInfor extends BaseBean {
	
	private static final long serialVersionUID = -8954640684427560438L;
	
	private String fid; // 询单ID,使用的是basebean id ，这里没用到
	private String cid; // 企业ID
	private String cname; // 企业名称
	private String title; // 标题
	private Integer type;// 类型:0买/1卖
	private Float price;// 单价
	private Float totalnum;// 当前数量
	private Float num;//总量
	private Date creatime;//创建时间
	private Date updatetime;//更新时间
	private Date starttime;//有效开始时间
	private Date endtime;//询单结束时间
	private String morearea;//是否多地发布,1单地，2多地
	private Date limitime;//到期时间
	private String area;// 地区
	private String remark; // 询单备注
	private Integer matchingnum;// 撮合合同次数
	
	private Integer opiid;//交易中的商品属性ID
	private String pname;//商品名
	private String pcode; // 商品类型
	private String pid; // 配置表中的商品ID
	private String ptype;//商品种类
	private String pcolor;//商品颜色
	private String paddress;//商品产地
	private UnitEnum unit;//数量单位(吨、立方)
	private String premark;//商品备注
	private Integer status; // 询单状态
	private OrderAddressTypeEnum addresstype; //卸货地址指定方
	private TOrderProductProperty psize; // 商品规格 
	private String psizeid; // 商品规格关联ID(T_ORDER_PRODUCT_PROPERTY ID)
	private EvaluationInfoBean evaluationInfo; // 企业评价信息
    private List<MoreAreaInfos> moreAreaInfos = new ArrayList<MoreAreaInfos>();// 询单多地域发布信息
	private List<TOrderProductProperty> oppList = new ArrayList<TOrderProductProperty>(); // 商品属性
	/****卸货地址 信息*****/
	private String address; // 卸货地址
    private String areacode; // 卸货地址地区编码（最后一级编码）
    private Float deep; // 水深
    private Float realdeep; // 实际吃水深度
    private List<ViewImgsBean> addressImgList = new ArrayList<ViewImgsBean>();// 企业卸货地址图片
    private List<ViewImgsBean> productImgList = new ArrayList<ViewImgsBean>();// 货物照片
    private int isApply; // 是否申请过(1已申请，0未申请)
	
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getMatchingnum() {
		return matchingnum;
	}

	public void setMatchingnum(Integer matchingnum) {
		this.matchingnum = matchingnum;
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
		this.morearea = morearea;
	}

	public Date getLimitime() {
		return limitime;
	}

	public void setLimitime(Date limitime) {
		this.limitime = limitime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOpiid() {
		return opiid;
	}

	public void setOpiid(Integer opiid) {
		this.opiid = opiid;
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

	public TOrderProductProperty getPsize() {
		return psize;
	}

	public void setPsize(TOrderProductProperty psize) {
		this.psize = psize;
	}

	public String getPcolor() {
		return pcolor;
	}

	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}

	public String getPaddress() {
		return paddress;
	}

	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}

	public UnitEnum getUnit() {
		return unit;
	}

	public void setUnit(UnitEnum unit) {
		this.unit = unit;
	}

	public String getPremark() {
		return premark;
	}

	public void setPremark(String premark) {
		this.premark = premark;
	}

	public List<TOrderProductProperty> getOppList() {
		return oppList;
	}

	public void setOppList(List<TOrderProductProperty> oppList) {
		this.oppList = oppList;
	}

	public List<MoreAreaInfos> getMoreAreaInfos() {
		return moreAreaInfos;
	}

	public void setMoreAreaInfos(List<MoreAreaInfos> moreAreaInfos) {
		this.moreAreaInfos = moreAreaInfos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public EvaluationInfoBean getEvaluationInfo() {
		return evaluationInfo;
	}

	public void setEvaluationInfo(EvaluationInfoBean evaluationInfo) {
		this.evaluationInfo = evaluationInfo;
	}

	public String getPsizeid() {
		return psizeid;
	}

	public void setPsizeid(String psizeid) {
		this.psizeid = psizeid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public OrderAddressTypeEnum getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(OrderAddressTypeEnum addresstype) {
		this.addresstype = addresstype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public Float getDeep() {
		return deep;
	}

	public void setDeep(Float deep) {
		this.deep = deep;
	}

	public Float getRealdeep() {
		return realdeep;
	}

	public void setRealdeep(Float realdeep) {
		this.realdeep = realdeep;
	}

	public List<ViewImgsBean> getAddressImgList() {
		return addressImgList;
	}

	public void setAddressImgList(List<ViewImgsBean> addressImgList) {
		this.addressImgList = addressImgList;
	}

	public List<ViewImgsBean> getProductImgList() {
		return productImgList;
	}

	public void setProductImgList(List<ViewImgsBean> productImgList) {
		this.productImgList = productImgList;
	}

	public Date getCreatime() {
		return creatime;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getIsApply() {
		return isApply;
	}

	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}

}
