/**
 *
 */
package com.appabc.bean.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private String title; // 标题
	private Integer type;// 类型:0买/1卖
	private Float price;// 单价
	private Integer totalnum;// 当前数量
	private Integer num;//总量
	private Date starttime;//有效开始时间
	private Date endtime;//询单结束时间
	private String morearea;//是否多地发布,1单地，2多地
	private Date limitime;//到期时间
	private String area;// 地区
	private String remark; // 询单备注
	private Integer opiid;//商品ID
	private String pname;//商品名
	private String ptype;//商品种类
	private String psize;//商品规格
	private String pcolor;//商品颜色
	private String paddress;//商品产地
	private String unit;//数量单位(吨、立方)
	private String premark;//商品备注
	private Integer status; // 询单状态
	private EvaluationInfoBean evaluationInfo; // 企业评价信息
    private List<MoreAreaInfos> moreAreaInfos = new ArrayList<MoreAreaInfos>();// 询单多地域发布信息
	private List<TOrderProductProperty> oppList = new ArrayList<TOrderProductProperty>(); // 商品属性

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

	public Integer getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
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

	public String getPsize() {
		return psize;
	}

	public void setPsize(String psize) {
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

}
