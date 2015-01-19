package com.appabc.bean.pvo;

import com.appabc.bean.bo.ViewImgsBean;
import com.appabc.common.base.bean.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TOrderAddress extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4665124991897928581L;

    /**
     * 询单ID
     */
    private String fid;

    /**
     * 订单编号
     */
    private String oid;

    /**
     * 询单原始地址、询单实体产生的地址
     */
    private String type;

    /**
     * 创建时间
     */
    private Date creatime;

    /**
     * 创建人
     */
    private String crater;

    /**
     * 企业编号
     */
    private String cid;

    /**
     * 实际吃水深度
     */
    private Float realdeep;

    /**
     * 地区编码（只存最后一级编码）
     */
    private String areacode;
    
    private String areaFullName; // 区域文字全称

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 水深
     */
    private Float deep;
    
    /**
     * 可泊船吨位
     */
    private Float shippington;
    
    private String addressImgIds; //  缺货地点图片ID，多个ID用逗号间隔
    
    /**
     * 用于显示的图片信息
     */
    private List<ViewImgsBean> vImgList = new ArrayList<ViewImgsBean>();

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getCrater() {
        return crater;
    }

    public void setCrater(String crater) {
        this.crater = crater == null ? null : crater.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public Float getRealdeep() {
        return realdeep;
    }

    public void setRealdeep(Float realdeep) {
        this.realdeep = realdeep;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Float getDeep() {
        return deep;
    }

    public void setDeep(Float deep) {
        this.deep = deep;
    }

	public String getAddressImgIds() {
		return addressImgIds;
	}

	public void setAddressImgIds(String addressImgIds) {
		this.addressImgIds = addressImgIds;
	}

	public Float getShippington() {
		return shippington;
	}

	public void setShippington(Float shippington) {
		this.shippington = shippington;
	}

	public String getAreaFullName() {
		return areaFullName;
	}

	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}

	public List<ViewImgsBean> getvImgList() {
		return vImgList;
	}

	public void setvImgList(List<ViewImgsBean> vImgList) {
		this.vImgList = vImgList;
	}
	
}