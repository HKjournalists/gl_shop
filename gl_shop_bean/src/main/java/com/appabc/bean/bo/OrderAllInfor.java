/**
 *
 */
package com.appabc.bean.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appabc.bean.enums.AuthRecordInfo.AuthRecordStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyBailStatus;
import com.appabc.bean.enums.CompanyInfo.CompanyType;
import com.appabc.bean.enums.ContractInfo.ContractStatus;
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
	private String title; // 标题
	private Integer type;// 类型:0买/1卖
	private Float price;// 单价
	private Float totalnum;// 总量
	private Float num;//当前剩余数量
	private Date creatime;//创建时间
	private Date updatetime;//更新时间
	private Date starttime;//有效开始时间
	private Date endtime;//有效结束时间
	private String morearea;//是否多地发布,1单地，2多地
	private Date limitime;//到期时间
	private String area;// 地区
	private String areaFullName;// 地区全名称（顶级到）
	private String remark; // 询单备注
	private Integer matchingnum;// 撮合合同次数
	
	private Integer opiid;//交易中的商品ID
	private String pname;//商品名
	private String pcode; // 商品类型
	private String pid; // 配置表中的商品ID
	private String ptype;//商品种类
	private String pcolor;//商品颜色
	private String paddress;//商品产地
	private UnitEnum unit;//数量单位(吨、立方)
	private String premark;//商品备注
	private String pTypeName; //商品种类名称
	
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
    private String addrAreaFullName; // 卸货地址地区全名
    private Float deep; // 水深
    private Float realdeep; // 实际吃水深度
    private Float shippington; // 可泊船吨位
    private List<ViewImgsBean> addressImgList = new ArrayList<ViewImgsBean>();// 企业卸货地址图片
    private List<ViewImgsBean> productImgList = new ArrayList<ViewImgsBean>();// 货物照片
    
    private int isApply; // 是否申请过(1已申请，0未申请)
    private Integer applyNum; // 交易盘询次数
    private Date applyDate; // 最后一次交易询盘时间
    private String contractid; // 合同ID（该询单生成的合同ID）
    private ContractStatus contractStatus; // 合同状态
    private Date contractendtime; // 合同结束时间
    
    /***企业信息*************/
    private String cid; // 企业ID
	private String cname; // 企业名称
    private CompanyType ctype;//企业类型
    private AuthRecordStatus authstatus; // 认证状态(是否认证)
    private CompanyBailStatus bailstatus; // 保证金缴纳状态（是否缴纳足额）
    private double guaranty;
    
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

	@SuppressWarnings("deprecation")
	public void setEndtime(Date endtime) {
		if(endtime != null){
    		endtime.setHours(23);
    		endtime.setMinutes(59);
    		endtime.setSeconds(59);
    	}
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

	public String getpTypeName() {
		return pTypeName;
	}

	public void setpTypeName(String pTypeName) {
		this.pTypeName = pTypeName;
	}

	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getAreaFullName() {
		return areaFullName;
	}

	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}

	public String getAddrAreaFullName() {
		return addrAreaFullName;
	}

	public void setAddrAreaFullName(String addrAreaFullName) {
		this.addrAreaFullName = addrAreaFullName;
	}

	public CompanyType getCtype() {
		return ctype;
	}

	public void setCtype(CompanyType ctype) {
		this.ctype = ctype;
	}

	public AuthRecordStatus getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(AuthRecordStatus authstatus) {
		this.authstatus = authstatus;
	}

	public CompanyBailStatus getBailstatus() {
		return bailstatus;
	}

	public void setBailstatus(CompanyBailStatus bailstatus) {
		this.bailstatus = bailstatus;
	}

	public Float getShippington() {
		return shippington;
	}

	public void setShippington(Float shippington) {
		this.shippington = shippington;
	}


	public Date getContractendtime() {
		return contractendtime;
	}

	public void setContractendtime(Date contractendtime) {
		this.contractendtime = contractendtime;
	}

	public Integer getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}

	/**  
	 * guaranty  
	 *  
	 * @return  the guaranty  
	 * @since   1.0.0  
	*/  
	
	public double getGuaranty() {
		return guaranty;
	}

	/**  
	 * @param guaranty the guaranty to set  
	 */
	public void setGuaranty(double guaranty) {
		this.guaranty = guaranty;
	}

	/**  
	 * applyDate  
	 *  
	 * @return  the applyDate  
	 * @since   1.0.0  
	*/  
	
	public Date getApplyDate() {
		return applyDate;
	}

	/**  
	 * @param applyDate the applyDate to set  
	 */
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	

}
