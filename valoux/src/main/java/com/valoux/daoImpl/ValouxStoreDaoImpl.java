/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ItemImageBean;
import com.valoux.bean.ValouxAgentStoreBean;
import com.valoux.bean.ValouxStoreAdvertisementBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxStoreDao;

/**
 * This <java>interface</java> ValouxStoreDaoImpl class has all the methods
 * related to store.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class ValouxStoreDaoImpl implements ValouxStoreDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxStoreDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method creates Store.
	 */
	public ValouxStoreBean createStore(ValouxStoreBean storeBean) throws Exception {
		LOGGER.debug("Enter Method createStore of ValouxStoreDaoImpl");
		sessionFactory.getCurrentSession().save(storeBean);
		LOGGER.debug("Exit Method createStore of ValouxStoreDaoImpl");
		return storeBean;
	}

	/**
	 * This method performs get All Store Data.
	 */
	public List<ValouxStoreBean> getAllStoreData() throws Exception {
		LOGGER.debug("Enter Method getAllStoreData of ValouxStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		@SuppressWarnings("unchecked")
		List<ValouxStoreBean> valouxStoreBeanList = (List<ValouxStoreBean>) criteria.list();
		LOGGER.debug("Exit Method getAllStoreData of ValouxStoreDaoImpl");
		return valouxStoreBeanList;
	}

	/**
	 * This method performs get All Active Store Data.
	 */
	public List<ValouxStoreBean> getAllActiveStoreData() throws Exception {
		LOGGER.debug("Enter Method getAllStoreData of ValouxStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		criteria.addOrder(Order.asc("name"));
		// criteria.add(Restrictions.eq("status", 1));
		@SuppressWarnings("unchecked")
		List<ValouxStoreBean> valouxStoreBeanList = (List<ValouxStoreBean>) criteria.list();
		LOGGER.debug("Exit Method getAllStoreData of ValouxStoreDaoImpl");
		return valouxStoreBeanList;
	}
	
	/**
	 * This method performs get All Active Store Data with keyword.
	 */
	public List<ValouxStoreBean> getAllActiveStoreDataWithKeyword(String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("Enter Method getAllActiveStoreDataWithKeyword of ValouxStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		//criteria.addOrder(Order.asc("name"));
		criteria.add(Restrictions.eq("status", 1));
		Disjunction orConditions = Restrictions.disjunction();
		orConditions.add(Restrictions.ilike("name", "%"+keyword+"%"));
		criteria.add(orConditions);

		if(startRecordNo!=null){
			criteria.setFirstResult(startRecordNo);
			criteria.setMaxResults(startRecordNo+numberOfRecords-1);
		}
		 
		if(sortBy!=null && sortOrder.equals(CommonConstants.ASC)){
			criteria.addOrder(Property.forName(sortBy).asc());
		}
		if(sortBy!=null && sortOrder.equals(CommonConstants.DESC)){
			criteria.addOrder(Property.forName(sortBy).desc());
		}
		@SuppressWarnings("unchecked")
		List<ValouxStoreBean> valouxStoreBeanList = (List<ValouxStoreBean>) criteria.list();
		LOGGER.debug("Exit Method getAllActiveStoreDataWithKeyword of ValouxStoreDaoImpl");
		return valouxStoreBeanList;
	}

	/**
	 * This method performs check Duplicate Store.
	 */
	public List<ValouxStoreBean> checkDuplicateStore(ValouxStoreBean storeBean) throws Exception {
		LOGGER.debug("Enter Method checkDuplicateStore of ValouxStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		criteria.add(Restrictions.eq("name", storeBean.getName()));
		criteria.add(Restrictions.eq("addressLine1", storeBean.getAddressLine1()));
		criteria.add(Restrictions.eq("addressLine2", storeBean.getAddressLine2()));
		criteria.add(Restrictions.eq("city", storeBean.getCity()));
		criteria.add(Restrictions.eq("stateId", storeBean.getStateId()));
		criteria.add(Restrictions.eq("countryId", storeBean.getCountryId()));
		criteria.add(Restrictions.eq("zipcode", storeBean.getZipcode()));
		@SuppressWarnings("unchecked")
		List<ValouxStoreBean> valouxStoreBeanList = (List<ValouxStoreBean>) criteria.list();
		LOGGER.debug("Exit Method checkDuplicateStore of ValouxStoreDaoImpl");
		return valouxStoreBeanList;

	}

	/**
	 * This method performs get Store Data By StoreId.
	 */
	public ValouxStoreBean getStoreDataByStoreId(Integer storeId) throws Exception {
		LOGGER.debug("Enter Method getStoreDataByStoreId of ValousStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		criteria.add(Restrictions.eq("storeId", storeId));
		ValouxStoreBean storeBean = (ValouxStoreBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getStoreDataByStoreId of ValousStoreDaoImpl");
		return storeBean;
	}

	/**
	 * This method update store detail.
	 */
	public ValouxStoreBean updateStoreData(ValouxStoreBean storeBean) throws Exception {
		LOGGER.debug("Enter Method updateStoreData of ValousStoreDaoImpl");
		sessionFactory.getCurrentSession().update(storeBean);
		LOGGER.debug("Exit Method updateStoreData of ValousStoreDaoImpl");
		return storeBean;
	}
	
	/**
	 * This method get all store data not in object list
	 */
	public List<ValouxStoreBean> getStoreListNotInArray(Object[] objects) throws Exception{
		LOGGER.debug("Enter Method getStoreListNotInArray of ValousStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("storeId", objects)));
		}
		List<ValouxStoreBean> storeBeanList = (List<ValouxStoreBean>)criteria.list();
		LOGGER.debug("Exit Method getStoreListNotInArray of ValousStoreDaoImpl");
		return storeBeanList;
	}
	
	/**
	 * This method get all store data in object list
	 */
	public List<ValouxStoreBean> getStoreListInArray(Object[] objects) throws Exception{
		LOGGER.debug("Enter Method getStoreListInArray of ValousStoreDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreBean.class);
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.in("storeId", objects));
		}
		List<ValouxStoreBean> storeBeanList = (List<ValouxStoreBean>)criteria.list();
		LOGGER.debug("Exit Method getStoreListInArray of ValousStoreDaoImpl");
		return storeBeanList;
	}
	
	/**
	 * This method merge store
	 */
	public void deleteStore(ValouxStoreBean storeBean) throws Exception{
		LOGGER.debug("Enter Method deleteStore of ValousStoreDaoImpl");
		sessionFactory.getCurrentSession().delete(storeBean);
		LOGGER.debug("Enter Method deleteStore of ValousStoreDaoImpl");
	}

	public ValouxStoreBean updateStoreDetails(ValouxStoreBean storeBean) throws Exception {
		
		if(storeBean != null) {
			LOGGER.debug("Enter Method updateStoreDetails of ValouxStoreDaoImpl");
			sessionFactory.getCurrentSession().update(storeBean);
			LOGGER.debug("Exit Method updateStoreDetails of ValouxStoreDaoImpl");
		}
		return storeBean;
	}
}
