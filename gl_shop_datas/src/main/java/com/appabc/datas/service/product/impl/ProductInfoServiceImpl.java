/**
 *
 */
package com.appabc.datas.service.product.impl;

import com.appabc.bean.bo.ProductAllInfoBean;
import com.appabc.bean.enums.ProductInfo.PropertyStatusEnum;
import com.appabc.bean.pvo.TProductInfo;
import com.appabc.bean.pvo.TProductProperty;
import com.appabc.common.base.QueryContext;
import com.appabc.datas.dao.product.IProductInfoDao;
import com.appabc.datas.dao.product.IProductPropertyDao;
import com.appabc.datas.service.product.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description : 商品基本信息SERVICE实现
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午2:47:31
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProductInfoServiceImpl implements IProductInfoService{

	@Autowired
	private IProductInfoDao productInfoDao;
	
	@Autowired
	private IProductPropertyDao productPropertyDao;
	
	/* (non-Javadoc)添加产品和产品属性
	 * @see com.appabc.datas.service.product.IProductInfoService#add(com.appabc.bean.TProductInfo, java.util.List)
	 */
	public void add(TProductInfo productInfo,TProductProperty psize, List<TProductProperty> productPropertyList) {
		
		this.productInfoDao.save(productInfo);
		
		for(TProductProperty pp : productPropertyList){
			pp.setPid(productInfo.getId());
			this.productPropertyDao.save(pp);
		}
		
		psize.setPid(productInfo.getId());
		this.productPropertyDao.save(psize);
		
		productInfo.setPsize(psize.getId()); // 更新产品规格ID到产品
		this.productInfoDao.update(productInfo);
	}

	/* (non-Javadoc)删除商品及属性
	 * @see com.appabc.common.base.service.IBaseService#delete(java.lang.String)
	 */
	public void delete(Serializable id) {
		this.productPropertyDao.delByPid(id.toString()); // 删除商品属性
		this.productInfoDao.delete(id);
	}


	/* (non-Javadoc)根据ID查询商品基本信息
	 * @see com.appabc.common.base.service.IBaseService#query(java.lang.String)
	 */
	public TProductInfo query(Serializable id) {
		return this.productInfoDao.queryByid(id);
	}

	/* (non-Javadoc)根据商品code查询商品
	 * @see com.appabc.datas.service.product.IProductInfoService#queryByPcode(java.lang.String)
	 */
	public List<TProductInfo> queryByPcode(String pcode) {
		return this.productInfoDao.queryByPcode(pcode);
	}

	public void add(TProductInfo entity) {
		this.productInfoDao.save(entity);
	}

	/* (non-Javadoc)更新商品基本信息
	 * @see com.appabc.common.base.service.IBaseService#modify(com.appabc.common.base.bean.BaseBean)
	 */
	public void modify(TProductInfo entity) {
		this.productInfoDao.update(entity);
	}

	public void delete(TProductInfo entity) {
		this.productInfoDao.delete(entity);
	}

	public TProductInfo query(TProductInfo entity) {
		return this.productInfoDao.query(entity);
	}

	public List<TProductInfo> queryForList(TProductInfo entity) {
		return this.productInfoDao.queryForList(entity);
	}

	public List<TProductInfo> queryForList(Map<String, ?> args) {
		return this.productInfoDao.queryForList(args);
	}

	public QueryContext<TProductInfo> queryListForPagination(
			QueryContext<TProductInfo> qContext) {
		return this.productInfoDao.queryListForPagination(qContext);
	}

	public void modify(TProductInfo productInfo,
			List<TProductProperty> productPropertyList) {
	}

	
	/* (non-Javadoc) 获取商品所有信息
	 * @see com.appabc.datas.service.product.IProductInfoService#queryProductAllInfoForList()
	 */
	public List<ProductAllInfoBean> queryProductAllInfoForList() {
		
		List<ProductAllInfoBean> paiList = new ArrayList<ProductAllInfoBean>();
		ProductAllInfoBean pai = null;
		
		List<TProductInfo> piList = this.productInfoDao.queryForList(new TProductInfo()); // 所有商品
		for(TProductInfo pi : piList) {
			
			pai = new ProductAllInfoBean();
			pai.setId(pi.getId());
			pai.setPaddress(pi.getPaddress());
			pai.setPcode(pi.getPcode());
			pai.setPcolor(pi.getPcolor());
			pai.setPname(pi.getPname());
			pai.setPtype(pi.getPtype());
			pai.setRemark(pi.getRemark());
			pai.setUnit(pi.getUnit());
			pai.setOrderno(pi.getOrderno());
			
			pai.setPsize(this.productPropertyDao.query(pi.getPsize())); // 产品规格
			
			TProductProperty queryEntity = new TProductProperty();
			queryEntity.setPid(pi.getId());
			queryEntity.setStatus(PropertyStatusEnum.PROPERTY_STATUS_0);
			pai.setPropertyList(this.productPropertyDao.queryForList(queryEntity));
			
			paiList.add(pai);
		}
		
		return paiList;
	}
}
