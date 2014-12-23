package com.appabc.datas.service.system;

import com.appabc.bean.bo.ViewImgsBean;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.service.IBaseService;

import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2014年9月22日 上午10:53:57
 */

public interface IUploadImagesService extends IBaseService<TUploadImages> {

	/**
	 * 根据ID获取文件的显示URL
	 * @param id
	 * @return
	 */
	public String getUrlByid(int id);

	/**
	 * 获取一组URL，多URL用逗号间隔，例：URL1,UR2,URL3
	 * @param entity
	 * @return
	 */
	public String getUrlsByEntity(TUploadImages entity);

	/**
	 * 获取List,根据业务ID和业务类型获取一组文件信息
	 * @param oid 业务ID
	 * @param otype 业务类型
	 * @return
	 */
	public List<TUploadImages> getListByOidAndOtype(String oid, String otype);

	/**
	 * 根据业务ID和业务类型删除文件记录
	 * @param oid 业务ID
	 * @param otype 业务类型
	 * @return
	 */
	public void delByOidAndOtype(String oid, String otype);

	/**
	 * 获取图片信息
	 * @param oid
	 * @param otype
	 * @return
	 */
	public List<ViewImgsBean> getViewImgsByOidAndOtype(String oid, String otype);
	
	/**
	 * 根据图片ID，获取图片信息对象
	 * @param oid
	 * @param otype
	 * @return
	 */
	public ViewImgsBean getViewImgsById(String imgid);

	/**
	 * 将图片与业务关联
	 * @param oid
	 * @param otype
	 * @param fileid
	 */
	public void updateOtypeAndOid(String oid, FileOType otype, String fileid);

	/**
	 * 将一批图片与业务关联，并删除不需要关联的旧图片
	 * @param oid
	 * @param otype
	 * @param fileids 文件ID数组
	 */
	public void updateOtypeAndOidBatch(String oid, FileOType otype, String[] fileids);

}
