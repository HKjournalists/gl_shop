package com.appabc.datas.service.banner.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appabc.bean.bo.ClientBannerInfo;
import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.bean.pvo.TClientBanner;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.service.BaseService;
import com.appabc.datas.dao.banner.IClientBannerDao;
import com.appabc.datas.service.banner.IClientBannerService;
import com.appabc.datas.service.system.IUploadImagesService;


/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年8月4日 上午10:49:45
 */

@Service
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
public class ClientBannerServiceImpl extends BaseService<TClientBanner>
		implements IClientBannerService{

	@Autowired
	private IClientBannerDao iClientBannerDao;
	@Autowired
	private IUploadImagesService uploadImagesService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.READ_COMMITTED)
	public void add(TClientBanner entity) {	
		iClientBannerDao.save(entity);
	}
	
	@Override
	public void modify(TClientBanner entity) {
		iClientBannerDao.update(entity);
	}
	
	@Override
	public void delete(TClientBanner entity) {
		iClientBannerDao.delete(entity);
	}
	
	@Override
	public void delete(Serializable id) {
		iClientBannerDao.delete(id);
	}
	
	@Override
	public TClientBanner query(TClientBanner entity) {
		return iClientBannerDao.query(entity);
	}
	
	@Override
	public TClientBanner query(Serializable id) {
		return iClientBannerDao.query(id);
	}
	
	@Override
	public List<TClientBanner> queryForList(TClientBanner entity) {
		return  iClientBannerDao.queryForList(entity);		
	}
	
	@Override
	public List<TClientBanner> queryForList(Map<String, ?> args) {
		return iClientBannerDao.queryForList(args);
	}
	
	@Override
	public QueryContext<TClientBanner> queryListForPagination(
			QueryContext<TClientBanner> qContext) {
		return null;
	}

	public int getMaxOrderno(ClientType osType) {
		return iClientBannerDao.getMaxOrderno(osType);
	}
	
	public List<ClientBannerInfo> queryClientBannerInfoList(Map<String, Object> args)
	{
		return iClientBannerDao.queryClientBannerInfoList(args);
	}
	
	public List<ClientBannerInfo> queryBannerLittleInfoList(Integer btype)
	{
		return iClientBannerDao.queryBannerLittleInfoList(btype);
	}
}
