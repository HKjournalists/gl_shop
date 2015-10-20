package com.appabc.datas.service.system.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.bo.ViewImgsBean;
import com.appabc.bean.enums.FileInfo.FileOType;
import com.appabc.bean.enums.FileInfo.FileStyle;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.dao.system.IUploadImagesDAO;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.tools.utils.SystemParamsManager;

/**
 * @Description :
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0
 * @Create_Date : 2014年9月22日 上午11:09:13
 */

@Service(value = "IUploadImagesService")
public class UploadImagesServiceImpl extends BaseService<TUploadImages> implements IUploadImagesService {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IUploadImagesDAO iUploadImagesDAO;
	@Autowired
	private SystemParamsManager spm;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */

	public void add(TUploadImages entity) {
		iUploadImagesDAO.save(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#modify(com.appabc.common.
	 * base.bean.BaseBean)
	 */

	public void modify(TUploadImages entity) {
		iUploadImagesDAO.update(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(com.appabc.common.
	 * base.bean.BaseBean)
	 */

	public void delete(TUploadImages entity) {
		if(entity == null) return;
		iUploadImagesDAO.delete(entity);
		if(StringUtils.isNotEmpty(entity.getFpath())){
			// 企业卸货地址和询单卸货地址图片删除需要判断图片的物理引用关系，存在多于1份引用关系不能删除
			if(entity.getOtype() != null && (FileOType.FILE_OTYPE_ADDRESS_ORDER.equals(entity.getOtype()) || FileOType.FILE_OTYPE_ADDRESS.equals(entity.getOtype()))){ 
				int count = this.iUploadImagesDAO.getCountByFpath(entity.getFpath());
				if(count > 1) return;
			}
			delFile(spm.getString(SystemConstant.UPLOADFILE_DIR) + entity.getFpath());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */

	public void delete(Serializable id) {
		iUploadImagesDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(com.appabc.common.base
	 * .bean.BaseBean)
	 */

	public TUploadImages query(TUploadImages entity) {
		return iUploadImagesDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */

	public TUploadImages query(Serializable id) {
		return iUploadImagesDAO.query(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(com.appabc.common
	 * .base.bean.BaseBean)
	 */

	public List<TUploadImages> queryForList(TUploadImages entity) {
		return iUploadImagesDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */

	public List<TUploadImages> queryForList(Map<String, ?> args) {
		return iUploadImagesDAO.queryForList(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryListForPagination(com
	 * .appabc.common.base.QueryContext)
	 */

	public QueryContext<TUploadImages> queryListForPagination(
			QueryContext<TUploadImages> qContext) {
		return iUploadImagesDAO.queryListForPagination(qContext);
	}

	/* (non-Javadoc)根据ID获取图片的显示URL
	 * @see com.appabc.datas.service.file.IUploadImagesService#getUrlByid(int)
	 */
	public String getUrlByid(int id) {
		TUploadImages ui = iUploadImagesDAO.query(id);
		if(ui != null){
			return ui.getFullpath();
		}
		return null;
	}

	/* (non-Javadoc)获取一组URL，多URL用逗号间隔，例：URL1,UR2,URL3
	 * @see com.appabc.datas.service.system.IUploadImagesService#getUrlsByEntity(com.appabc.bean.pvo.TUploadImages)
	 */
	public String getUrlsByEntity(TUploadImages entity) {
		List<TUploadImages> uiList = iUploadImagesDAO.queryForList(entity);
		StringBuffer imgUrls = new StringBuffer();
		for(int i=0; i<uiList.size(); i++){
			if(i>0){
				imgUrls.append(",");
			}
			imgUrls.append(uiList.get(i).getFullpath());
		}

		return imgUrls.toString();
	}

	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.IUploadImagesService#getUrlsByOidAndOtype(java.lang.String, java.lang.String)
	 */
	public String getUrlsByOidAndOtype(String oid, String otype) {
		TUploadImages entity = new TUploadImages();
		entity.setOid(oid);
		entity.setOtype(FileOType.enumOf(otype));

		return getUrlsByEntity(entity);
	}

	/* (non-Javadoc)根据业务ID和业务类型删除文件记录
	 * @see com.appabc.datas.service.system.IUploadImagesService#delByOidAndOtype(java.lang.String, java.lang.String)
	 */
	public void delByOidAndOtype(String oid, FileOType otype) {
		if(StringUtils.isNotEmpty(oid) && otype != null){
			TUploadImages entity = new TUploadImages();
			entity.setOid(oid);
			entity.setOtype(otype);
			
			delete(entity);
		}else{
			logger.error("文件删除错误，oid="+oid+",otype="+otype);
		}
	}

	/* (non-Javadoc)获取List,根据业务ID和业务类型获取一组文件信息
	 * @see com.appabc.datas.service.system.IUploadImagesService#getListByOidAndOtype(java.lang.String, java.lang.String)
	 */
	public List<TUploadImages> getListByOidAndOtype(String oid, String otype) {
		TUploadImages entity = new TUploadImages();
		entity.setOid(oid);
		entity.setOtype(FileOType.enumOf(otype));
		return iUploadImagesDAO.queryForList(entity);
	}

	/* (non-Javadoc)获取图片信息
	 * @see com.appabc.datas.service.system.IUploadImagesService#getViewImgsByOidAndOtype(java.lang.String, java.lang.String)
	 */
	public List<ViewImgsBean> getViewImgsByOidAndOtype(String oid, String otype) {

		List<ViewImgsBean> vImgList = new ArrayList<ViewImgsBean>();

		ViewImgsBean vi = null;

		TUploadImages entity = new TUploadImages();
		entity.setOid(oid);
		entity.setOtype(FileOType.enumOf(otype));
		List<TUploadImages> uiList = iUploadImagesDAO.queryForList(entity);

		for(TUploadImages ui : uiList) {
			if(ui.getFstyle() == null || ui.getFstyle().equals(FileStyle.FILE_STYLE_ORIGINAL)) { // 先将原图信息放入BEAN
				vi = new ViewImgsBean();
				vi.setId(ui.getId());
				vi.setUrl(ui.getFullpath());
				vImgList.add(vi);
			}
		}

		for(TUploadImages ui : uiList) { // 缩略图信息放入BEAN
			if(ui.getFstyle() != null && ui.getFstyle().equals(FileStyle.FILE_STYLE_SMALL)) {
				for(ViewImgsBean vib : vImgList) {
					if(ui.getPid().equals(Integer.parseInt(vib.getId())) ){
						vib.setThumbnailSmall(ui.getFullpath());
						continue;
					}
				}
			}
		}

		return vImgList;
	}

	/* (non-Javadoc)将图片与业务关联
	 * @see com.appabc.datas.service.system.IUploadImagesService#updateOtypeAndOid(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateOtypeAndOid(String oid, FileOType otype, String fileid) {
		
		if(StringUtils.isEmpty(oid) || otype ==  null || StringUtils.isEmpty(fileid)){
			logger.warn("数据不完整，oid="+oid+",otype="+otype+",fileid="+fileid);
		}

		TUploadImages ui = this.iUploadImagesDAO.query(fileid);
		if(ui != null){
			ui.setOid(oid);
			ui.setOtype(otype);
			this.iUploadImagesDAO.update(ui); // 更新原文件关联

			TUploadImages qEntity = new TUploadImages();
			qEntity.setPid(Integer.parseInt(fileid));
			List<TUploadImages> uiList = this.iUploadImagesDAO.queryForList(qEntity); // 查询子文件（略图之类）

			for(TUploadImages uimg : uiList){
				uimg.setOid(oid);
				uimg.setOtype(otype);
				this.iUploadImagesDAO.update(uimg); // 更新子文件关联
			}
		}


	}

	/* (non-Javadoc)将一批图片与业务关联，并删除不需要关联的旧图片
	 * @see com.appabc.datas.service.system.IUploadImagesService#updateOtypeAndOidBatch(java.lang.String, com.appabc.bean.enums.FileInfo.FileOType, java.lang.String[])
	 */
	public void updateOtypeAndOidBatch(String oid, FileOType otype, String[] fileids) {
		TUploadImages uiBean = new TUploadImages();
		uiBean.setOid(oid);
		uiBean.setOtype(otype);
		List<TUploadImages> imgList = iUploadImagesDAO.queryForList(uiBean);

		if(fileids != null){
			for(String imgid : fileids) {
				boolean isUpdate = true;
				for(int i=imgList.size()-1; i>=0; i--) { // 过滤不需要更新的文件
					if(imgList.get(i).getId().equals(imgid) || (imgList.get(i).getPid() != null && imgList.get(i).getPid().equals(Integer.valueOf(imgid)))){
						imgList.remove(i);
						isUpdate = false;
					}
				}
				if(isUpdate == true){
					updateOtypeAndOid(oid, otype, imgid);
				}
			}
		}

		for(int i=0; i<imgList.size(); i++){ // 删除不需要关联的旧图片
			this.iUploadImagesDAO.delete(imgList.get(i).getId());
		}

	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.IUploadImagesService#getViewImgsById(java.lang.String)
	 */
	@Override
	public ViewImgsBean getViewImgsById(String imgid) {
		
		ViewImgsBean vi = new ViewImgsBean();
		if(StringUtils.isNotEmpty(imgid)){
			TUploadImages ui = this.iUploadImagesDAO.query(imgid);
			if(ui != null){
				vi.setId(ui.getId());
				vi.setUrl(ui.getFullpath());
				
				// 略图URL
				TUploadImages uiBean = new TUploadImages();
				uiBean.setPid(Integer.parseInt(imgid));
				List<TUploadImages> imgList = iUploadImagesDAO.queryForList(uiBean);
				if(imgList != null && imgList.size()>0 && imgList.get(0)!=null){
					vi.setThumbnailSmall(imgList.get(0).getFullpath());
				}
			}
			
		}
		
		return vi;
	}
	
	/**
	 * 文件物理删除
	 * @param filePath
	 */
	private void delFile(String filePath) {
		logger.debug("del File path is:" + filePath);
		try {
			File file = new File(filePath);
			if(file.isFile()){
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appabc.datas.service.system.IUploadImagesService#copyUploadImagesRelationshipsObject(java.lang.String, com.appabc.bean.enums.FileInfo.FileOType, java.util.List)
	 */
	@Override
	public void copyUploadImagesRelationshipsObject(String oid,
			FileOType otype, List<TUploadImages> uiList) {
		logger.debug("图片信息复制，oid="+oid+",otype="+otype);
		String newParentId = null ;
		for(TUploadImages ui : uiList){
			try {
				TUploadImages uiNew = (TUploadImages) ui.clone();
				uiNew.setOid(oid);
				uiNew.setOtype(otype);
				uiNew.setId(null);
				uiNew.setCreatedate(Calendar.getInstance().getTime());
				if(FileStyle.FILE_STYLE_SMALL.equals(uiNew.getFstyle())) { // 略图
					uiNew.setPid(Integer.parseInt(newParentId));  // 父ID为上一条原始图片ID
				}
				
				this.iUploadImagesDAO.save(uiNew);
				if(FileStyle.FILE_STYLE_ORIGINAL.equals(uiNew.getFstyle())){ // 原始图片
					newParentId = uiNew.getId();
				}else{
					newParentId = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
