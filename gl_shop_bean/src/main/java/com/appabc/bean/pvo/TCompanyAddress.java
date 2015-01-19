package com.appabc.bean.pvo;

import java.util.ArrayList;
import java.util.List;

import com.appabc.bean.bo.ViewImgsBean;
import com.appabc.bean.enums.CompanyInfo.AddressStatus;
import com.appabc.common.base.bean.BaseBean;

public class TCompanyAddress extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7597511487467043338L;

    /**
     * 企业编号
     */
    private String cid;

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
     * 状态（默认的卸货地址）
     */
    private AddressStatus status;
    
    /**
     * 实际吃水深度
     */
    private Float realdeep;
    
    /**
     * 可泊船吨位
     */
    private Float shippington;
    
    private String addressImgIds; //  缺货地点图片ID，多个ID用逗号间隔
    
    /**
     * 用于显示的图片信息
     */
    private List<ViewImgsBean> vImgList = new ArrayList<ViewImgsBean>();

    public List<ViewImgsBean> getvImgList() {
		return vImgList;
	}

	public void setvImgList(List<ViewImgsBean> vImgList) {
		this.vImgList = vImgList;
	}

	public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
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

	public Float getRealdeep() {
		return realdeep;
	}

	public void setRealdeep(Float realdeep) {
		this.realdeep = realdeep;
	}

	public String getAddressImgIds() {
		return addressImgIds;
	}

	public void setAddressImgIds(String addressImgIds) {
		this.addressImgIds = addressImgIds;
	}

	public AddressStatus getStatus() {
		return status;
	}
	
	public void setStatus(AddressStatus status) {
		this.status = status;
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

}