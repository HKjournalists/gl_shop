package com.appabc.bean.pvo;

import com.appabc.bean.enums.FileInfo.FileCommitServer;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.FileInfo.FileStyle;
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
    private FileOType otype;

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
    private FileCommitServer commitserver;

    /**
     * 提交时间
     */
    private Date createdate;
    
    /**
     * 文件父ID
     */
    private Integer pid;
    
    /**
     * 图片规格（原图\大\中\小）
     */
    private FileStyle fstyle;

    public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid == null ? null : oid.trim();
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

	/**  
	 * otype  
	 *  
	 * @return  the otype  
	 * @since   1.0.0  
	*/  
	
	public FileOType getOtype() {
		return otype;
	}

	/**  
	 * @param otype the otype to set  
	 */
	public void setOtype(FileOType otype) {
		this.otype = otype;
	}

	/**  
	 * fstyle  
	 *  
	 * @return  the fstyle  
	 * @since   1.0.0  
	*/  
	
	public FileStyle getFstyle() {
		return fstyle;
	}

	/**  
	 * @param fstyle the fstyle to set  
	 */
	public void setFstyle(FileStyle fstyle) {
		this.fstyle = fstyle;
	}

	/**  
	 * commitserver  
	 *  
	 * @return  the commitserver  
	 * @since   1.0.0  
	*/  
	
	public FileCommitServer getCommitserver() {
		return commitserver;
	}

	/**  
	 * @param commitserver the commitserver to set  
	 */
	public void setCommitserver(FileCommitServer commitserver) {
		this.commitserver = commitserver;
	}


}