/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.AppraisalBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AppraisalDao;

/**
 * This <java>class</java> AppraisalDaoImpl use to perform all our DB related
 * logics for the appraisal.
 * 
 * @author param Sheikh
 * 
 */

public class AppraisalDaoImpl implements AppraisalDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(AppraisalDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	/**
	 * This method creates Appraisal or update Appraisal.
	 */

	public AppraisalBean createOrUpdateAppraisal(AppraisalBean appraisalBean) throws Exception {
		LOGGER.debug("Enter Method createOrUpdateAppraisal of AppraisalDaoImpl");
		if (appraisalBean != null) {

			if (appraisalBean.getAppraisalId() != null) {
				thirdDBSessionFactory.getCurrentSession().update(appraisalBean);
			} else {
				thirdDBSessionFactory.getCurrentSession().save(appraisalBean);
			}
		}
		LOGGER.debug("Exit Method createOrUpdateAppraisal of AppraisalDaoImpl");
		return appraisalBean;
	}
	
	/**
	 * This method performs check User Exist.
	 */
	public boolean checkAppraisalAlreadyExist(String rKey, String name) throws Exception {
		LOGGER.debug("Enter Method checkAppraisalAlreadyExist of AppraisalDaoImpl");
		boolean appraisalExist = true;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		criteria.add(Restrictions.eq("name", name));
		@SuppressWarnings("unchecked")
		List<AppraisalBean> appraisalBeanList = (List<AppraisalBean>) criteria.list();
		if (!(appraisalBeanList != null && appraisalBeanList.size() > 0)) {
			appraisalExist = false;
		}

		LOGGER.debug("Exit Method checkAppraisalAlreadyExist of AppraisalDaoImpl");
		return appraisalExist;
	}

	/**
	 * This method get all the list of appraisal.
	 */
	public List<AppraisalBean> getAllAppraisalListForSupoerAdmin() throws Exception {
		LOGGER.debug("Enter Method getAllAppraisalListForSupoerAdmin of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		@SuppressWarnings("unchecked")
		List<AppraisalBean> appraisalBeanList = (List<AppraisalBean>) criteria.list();
		LOGGER.debug("Enter Method getAllAppraisalListForSupoerAdmin of AppraisalDaoImpl");
		return appraisalBeanList;
	}

	/**
	 * This method performs check Appraisal approval.
	 */
	public boolean checkAppraisalAlreadyApproved(Integer appraisalId, Integer status) throws Exception {
		LOGGER.debug("Enter Method checkAppraisalAlreadyApproved of AppraisalDaoImpl");
		boolean appraisalExist = true;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("appraisalId", appraisalId));
		criteria.add(Restrictions.eq("aStatus", status));
		@SuppressWarnings("unchecked")
		List<AppraisalBean> appraisalBeanList = (List<AppraisalBean>) criteria.list();
		if (!(appraisalBeanList != null && appraisalBeanList.size() > 0)) {
			appraisalExist = false;
		}

		LOGGER.debug("Exit Method checkAppraisalAlreadyApproved of AppraisalDaoImpl");
		return appraisalExist;
	}

	/**
	 * This method approved or disapproved Appraisal.
	 */

	public AppraisalBean approvedOrDisapprovedAppraisal(AppraisalBean appraisalBean) throws Exception {
		LOGGER.debug("Enter Method approvedOrDisapprovedAppraisal of AppraisalDaoImpl");
		thirdDBSessionFactory.getCurrentSession().update(appraisalBean);
		LOGGER.debug("Exit Method approvedOrDisapprovedAppraisal of AppraisalDaoImpl");
		return appraisalBean;
	}

	/**
	 * This method get Appraisal Details.
	 */
	public AppraisalBean getAppraisalDetails(Integer appraisalId) {
		LOGGER.debug("Enter Method getAppraisalDetails of AppraisalDaoImpl");
		AppraisalBean appraisalBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("appraisalId", appraisalId));
		appraisalBean = (AppraisalBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getAppraisalDetails of AppraisalDaoImpl");
		return appraisalBean;
	}

	/**
	 * This method provides all Appraisal by user id.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalBean> getAppraisalDetailsByUserId(String relationKey,Integer aStatus,Object[] objects) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalDetailsByUserId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		if(relationKey!=null){
		criteria.add(Restrictions.eq("relationKey", relationKey));
		}
		if(aStatus!=null){
		criteria.add(Restrictions.eq("aStatus", aStatus));
		}
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.in("appraisalId", objects));
		}
		List<AppraisalBean> appraisalBeans = (List<AppraisalBean>) criteria.list();
		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalDetailsByUserId");
		return appraisalBeans;
	}

	/**
	 * This method check the appraisal name exist for user
	 */
	public List<AppraisalBean> checkAppraisalNameExistForUser(String publicKey, String appraisalName) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method checkAppraisalNameExistForUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("relationKey", publicKey));
		criteria.add(Restrictions.eq("name", appraisalName));
		@SuppressWarnings("unchecked")
		List<AppraisalBean> itemBeanList = (List<AppraisalBean>) criteria.list();
		LOGGER.debug("AppraisalDaoImpl : Enter method checkAppraisalNameExistForUser");
		return itemBeanList;
	}

	/**
	 * This method get Appraisal not associated with item
	 */
	public List<AppraisalBean> getAppraisalListNotAssociatedWithItem(String rKey, Object[] appraisalArray)
			throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalListNotAssociatedWithItem");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("relationKey", rKey));
		criteria.add(Restrictions.not(Restrictions.eq("aStatus", CommonConstants.APPRAISAL_STATUS_APPROVED)));
		if (appraisalArray != null && appraisalArray.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("appraisalId", appraisalArray)));
		}
		List<AppraisalBean> appraisalBean = (List<AppraisalBean>) criteria.list();
		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalListNotAssociatedWithItem");
		return appraisalBean;
	}

	/**
	 * This method performs get All appraisal List with name like keyword.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalBean> getAppraisalListForGlobalSearchByName(String publicKey,String keyword,Object[] objects,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("Enter method getAppraisalListForGlobalSearchByName of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		if(publicKey!=null){
		criteria.add(Restrictions.eq("relationKey", publicKey));
		}
		Disjunction orConditions = Restrictions.disjunction();
		orConditions.add(Restrictions.ilike("name", "%"+keyword+"%"));
		orConditions.add(Restrictions.ilike("shortDescription", "%"+keyword+"%"));
		criteria.add(orConditions); 
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.in("appraisalId", objects));
		}

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
		List<AppraisalBean> appraisalBeanList = (List<AppraisalBean>) criteria.list();
		LOGGER.debug("Exit method getAppraisalListForGlobalSearchByName of AppraisalDaoImpl");
		return appraisalBeanList;
	}
	
	/**
	 * This method get collection appraisal not in collection
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalBean> getConsumerAppraisalsNotInCollection(String publicKey, Object[] objects)
			throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method getConsumerAppraisalsNotInCollection");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("relationKey", publicKey));
		criteria.add(Restrictions.not(Restrictions.eq("aStatus", CommonConstants.APPRAISAL_STATUS_APPROVED)));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("appraisalId", objects)));
		}

		List<AppraisalBean> beanList = (List<AppraisalBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit method getConsumerAppraisalsNotInCollection");
		return beanList;
	}

	@SuppressWarnings("unchecked")
	public List<AppraisalBean> getAllAppraisalsList() throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method getAllAppraisalsList");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);

		List<AppraisalBean> beanList = (List<AppraisalBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit method getAllAppraisalsList");
		return beanList;
	}

	@SuppressWarnings("unchecked")
	public List<AppraisalBean> getTopAppraisalsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method getTopAppraisalsListByUserIdAndLimit");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalBean.class);
		criteria.add(Restrictions.eq("relationKey", userPublicKey));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(limit);
		List<AppraisalBean> beanList = (List<AppraisalBean>) criteria.list();
		LOGGER.debug("AppraisalDaoImpl : Enter method getTopAppraisalsListByUserIdAndLimit");
		return beanList;
	}
	
	/**
	 * This method delete item component object
	 */
	public void deleteAnyBeanByObject(Object objectBean)
			throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method deleteAnyBeanByObject");

		thirdDBSessionFactory.getCurrentSession().delete(objectBean);

		LOGGER.debug("AppraisalDaoImpl : Exit Method deleteAnyBeanByObject");
		
	}

}
