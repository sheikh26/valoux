package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxComponentsImageBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.bean.ValouxStoreAdvertisementBean;
import com.valoux.dao.ValouxStoreAdvertisementDao;
import com.valoux.dao.ValouxStoreDao;

public class ValouxStoreAdvertisementDaoImpl implements ValouxStoreAdvertisementDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxStoreAdvertisementDaoImpl.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * This method creates Store.
	 */

	public ValouxStoreAdvertisementBean createStoreAdvertisement(ValouxStoreAdvertisementBean storeAddBean) throws Exception {
		LOGGER.debug("Enter Method createStoreAdvertisement of ValouxStoreAdvertisementDaoImpl");
		sessionFactory.getCurrentSession().save(storeAddBean);
		LOGGER.debug("Exit Method createStoreAdvertisement of ValouxStoreAdvertisementDaoImpl");
		return storeAddBean;
	}
	
	/**
	 * This method Update Store.
	 */

	public ValouxStoreAdvertisementBean updateStoreAdvertisement(ValouxStoreAdvertisementBean storeAddBean) throws Exception {
		LOGGER.debug("Enter Method updateStoreAdvertisement of ValouxStoreAdvertisementDaoImpl");
		sessionFactory.getCurrentSession().update(storeAddBean);
		LOGGER.debug("Exit Method updateStoreAdvertisement of ValouxStoreAdvertisementDaoImpl");
		return storeAddBean;
	}
	
	/**
	 * This method gets the maximum index of the table
	 */
	public Integer getMaximumIndexOfStoreAdvTable() throws Exception {
		LOGGER.debug("Enter Method getMaximumIndexOfStoreAdvTable of ValouxStoreAdvertisementDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreAdvertisementBean.class)
				.setProjection(Projections.max("storeAdvertisementId"));
		Integer maxValue = (Integer) criteria.uniqueResult();
		LOGGER.debug("Enter Method getMaximumIndexOfStoreAdvTable of ValouxStoreAdvertisementDaoImpl");
		return maxValue;
	}
	
	/**
	 * This method get store advertisement list by storeId
	 */
	public List<ValouxStoreAdvertisementBean> getStoreAdvertisementListByStoreId(Integer storeId) throws Exception{
		LOGGER.debug("Enter Method getStoreAdvertisementListByStoreId of ValouxStoreAdvertisementDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreAdvertisementBean.class);
		criteria.add(Restrictions.eq("storeId", storeId));
		List<ValouxStoreAdvertisementBean> storeAdvertisementBean =(List<ValouxStoreAdvertisementBean>)criteria.list();
		LOGGER.debug("Enter Method getStoreAdvertisementListByStoreId of ValouxStoreAdvertisementDaoImpl");
		return storeAdvertisementBean;
	}
	
	/**
	 * This method delete store adv image
	 */
	public void deleteStoreAdvImages(ValouxStoreAdvertisementBean storeAdvBean) throws Exception {
		LOGGER.debug("Enter Method deleteStoreAdvImages of ValouxStoreAdvertisementDaoImpl");
		sessionFactory.getCurrentSession().delete(storeAdvBean);
		LOGGER.debug("Enter Method deleteStoreAdvImages of ValouxStoreAdvertisementDaoImpl");
	}
	
	/**
	 * This method update store adv image
	 */
	public void updateStoreAdvImages(ValouxStoreAdvertisementBean storeAdvBean) throws Exception {
		LOGGER.debug("Enter Method deleteStoreAdvImages of updateStoreAdvImages");
		sessionFactory.getCurrentSession().update(storeAdvBean);
		LOGGER.debug("Enter Method deleteStoreAdvImages of updateStoreAdvImages");
	}
	
	/**
	 * This method get store advertisement list
	 */
	public List<ValouxStoreAdvertisementBean> getStoreAdvertisementList() throws Exception{
		LOGGER.debug("Enter Method getStoreAdvertisementList of ValouxStoreAdvertisementDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreAdvertisementBean.class);
		List<ValouxStoreAdvertisementBean> storeAdvertisementBean =(List<ValouxStoreAdvertisementBean>)criteria.list();
		LOGGER.debug("Enter Method getStoreAdvertisementList of ValouxStoreAdvertisementDaoImpl");
		return storeAdvertisementBean;
	}
	
	/**
	 * This method get store advertisement list
	 */
	public List<ValouxStoreAdvertisementBean> getStoreAdvertisementListByDistinctStoreId() throws Exception{
		LOGGER.debug("Enter Method getStoreAdvertisementList of ValouxStoreAdvertisementDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreAdvertisementBean.class);
		criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("storeId"),
				"storeId")));
		criteria.setResultTransformer(Transformers.aliasToBean(ValouxStoreAdvertisementBean.class));
		List<ValouxStoreAdvertisementBean> storeAdvertisementBean =(List<ValouxStoreAdvertisementBean>)criteria.list();
		LOGGER.debug("Enter Method getStoreAdvertisementList of ValouxStoreAdvertisementDaoImpl");
		return storeAdvertisementBean;
	}
	
	/**
	 * This method get store advertisement list by storeAdvertisementId
	 */
	public List<ValouxStoreAdvertisementBean> getStoreAdvertisementListByStoreAdvertisementId(Integer storeAdvertisementId) throws Exception{
		LOGGER.debug("Enter Method getStoreAdvertisementListByStoreAdvertisementId of ValouxStoreAdvertisementDaoImpl");
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ValouxStoreAdvertisementBean.class);
		criteria.add(Restrictions.eq("storeAdvertisementId", storeAdvertisementId));
		List<ValouxStoreAdvertisementBean> storeAdvertisementBean =(List<ValouxStoreAdvertisementBean>)criteria.list();
		LOGGER.debug("Enter Method getStoreAdvertisementListByStoreAdvertisementId of ValouxStoreAdvertisementDaoImpl");
		return storeAdvertisementBean;
	}
	
	/**
	 * This method merge store
	 */
	public void mergestore(Integer primaryStoreId, Integer storeIdToBeMerged) throws Exception{
		LOGGER.debug("Enter Method mergestore of ValouxStoreAdvertisementDaoImpl");
//		sessionFactory.openSession();
//		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		String hqlUpdate = "update valoux_store_advertisement s set s.store_id=:primaryStoreId where s.store_id=:storeIdToBeMerged";
		// or String hqlUpdate = "update Customer set name = :newName where name = :oldName";
		int updatedEntities = sessionFactory.getCurrentSession().createSQLQuery(hqlUpdate).setInteger("primaryStoreId", primaryStoreId).setInteger("storeIdToBeMerged", storeIdToBeMerged).executeUpdate();
		
//		if (!tx.wasCommitted())
//		    tx.commit();
//		sessionFactory.close();
		LOGGER.debug("Enter Method mergestore of ValouxStoreAdvertisementDaoImpl");
	}

}
