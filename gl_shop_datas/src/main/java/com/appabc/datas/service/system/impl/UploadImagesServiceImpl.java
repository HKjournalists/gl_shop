package com.appabc.datas.service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.system.IUploadImagesDAO;
import com.appabc.datas.service.system.IUploadImagesService;

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

	@Autowired
	private IUploadImagesDAO iUploadImagesDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#add(com.appabc.common.base
	 * .bean.BaseBean)
	 */
	@Override
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
	@Override
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
	@Override
	public void delete(TUploadImages entity) {
		iUploadImagesDAO.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#delete(java.io.Serializable)
	 */
	@Override
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
	@Override
	public TUploadImages query(TUploadImages entity) {
		return iUploadImagesDAO.query(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#query(java.io.Serializable)
	 */
	@Override
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
	@Override
	public List<TUploadImages> queryForList(TUploadImages entity) {
		return iUploadImagesDAO.queryForList(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appabc.common.base.service.IBaseService#queryForList(java.util.Map)
	 */
	@Override
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
	@Override
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
		entity.setOtype(otype);
		
		return getUrlsByEntity(entity);
	}

	/* (non-Javadoc)根据业务ID和业务类型删除文件记录
	 * @see com.appabc.datas.service.system.IUploadImagesService#delByOidAndOtype(java.lang.String, java.lang.String)
	 */
	public void delByOidAndOtype(String oid, String otype) {
		TUploadImages entity = new TUploadImages();
		entity.setOid(oid);
		entity.setOtype(otype);
		
		this.iUploadImagesDAO.delete(entity);
	}

	/* (non-Javadoc)获取List,根据业务ID和业务类型获取一组文件信息
	 * @see com.appabc.datas.service.system.IUploadImagesService#getListByOidAndOtype(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TUploadImages> getListByOidAndOtype(String oid, String otype) {
		TUploadImages entity = new TUploadImages();
		entity.setOid(oid);
		entity.setOtype(otype);
		return iUploadImagesDAO.queryForList(entity);
	}
	
}
