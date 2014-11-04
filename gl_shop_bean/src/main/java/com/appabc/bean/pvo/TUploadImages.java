package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TUploadImages extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4033953228080324012L;

    /**
     * 对应编号
     */
    private String oid;

    /**
     * 对应类型(企业认证图片、企业图片、卸货地址图片)
     */
    private String otype;

    /**
     * 文件名
     */
    private String fname;

    /**
     * 文件类型
     */
    private String ftype;

    /**
     * 文件大小
     */
    private Long fsize;

    /**
     * 文件目录
     */
    private String fpath;

    /**
     * 全路径（直接外网访问路径）
     */
    private String fullpath;

    /**
     * 文件服务器编号（引申出文件服务器配置表）
     */
    private String fserverid;

    /**
     * 提交系统
     */
    private String commitserver;

    /**
     * 提交时间
     */
    private Date createdate;
    
    /**
     * 文件父ID
     */
    private Integer pid;
    
    /**
     * 图片格式（原图\大\中\小）
     */
    private String fstyle;
    
    /**
     * 略图
     */
    private String thumbPic;

    public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid == null ? null : oid.trim();
	}

	public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype == null ? null : otype.trim();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype == null ? null : ftype.trim();
    }

    public Long getFsize() {
        return fsize;
    }

    public void setFsize(Long fsize) {
        this.fsize = fsize;
    }

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath == null ? null : fpath.trim();
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath == null ? null : fullpath.trim();
    }

    public String getFserverid() {
        return fserverid;
    }

    public void setFserverid(String fserverid) {
        this.fserverid = fserverid == null ? null : fserverid.trim();
    }

    public String getCommitserver() {
        return commitserver;
    }

    public void setCommitserver(String commitserver) {
        this.commitserver = commitserver == null ? null : commitserver.trim();
    }

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getFstyle() {
		return fstyle;
	}

	public void setFstyle(String fstyle) {
		this.fstyle = fstyle;
	}

	public String getThumbPic() {
		return thumbPic;
	}

	public void setThumbPic(String thumbPic) {
		this.thumbPic = thumbPic;
	}

}