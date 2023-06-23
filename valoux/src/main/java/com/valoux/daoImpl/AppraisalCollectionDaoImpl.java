package com.valoux.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AppraisalCollectionDao;

public class AppraisalCollectionDaoImpl implements AppraisalCollectionDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(AppraisalCollectionDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	/**
	 * This method check Existing Appraisal for CollectionList.
	 */
	public AppraisalCollectionBean checkExistingAppraisalCollectionList(
			List<AppraisalCollectionBean> appraisalCollectionList, String requestType) throws Exception {
		LOGGER.debug("Enter Method checkExistingAppraisalItemList of AppraisalDaoImpl");
		AppraisalCollectionBean appCollectionBean = null;
		for (AppraisalCollectionBean appraisalCollection : appraisalCollectionList) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", (appraisalCollection.getAppraisalBean().getAppraisalId())));
			criteria.add(Restrictions.eq("valouxCollectionBean.vcid", (appraisalCollection.getValouxCollectionBean().getVcid())));

			appCollectionBean = (AppraisalCollectionBean) criteria.uniqueResult();

		}
		LOGGER.debug("Exit Method checkExistingAppraisalItemList of AppraisalDaoImpl");
		return appCollectionBean;
	}
	
	/**
	 * This method get all the collection associatedwith appraisal
	 */
	public List<AppraisalCollectionBean> getCollectionAssociatedWithAppraisal(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter Method getCollectionAssociatedWithAppraisal of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		List<AppraisalCollectionBean> appraisalCollectionList = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("Exit Method getCollectionAssociatedWithAppraisal of AppraisalDaoImpl");
		return appraisalCollectionList;
	}
	

	/**
	 * This method performs get appraisal collection Bean By appraisalId.
	 */
	public List<AppraisalCollectionBean> getAppraisalCollectionDataByAppraisalId(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter method getAppraisalCollectionDataByAppraisalId of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		@SuppressWarnings("unchecked")
		List<AppraisalCollectionBean> appraisalCollectionBeanList = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("Exit method getAppraisalCollectionDataByAppraisalId of AppraisalDaoImpl");
		return appraisalCollectionBeanList;
	}
	
	/**
	 * This method get lis of Appraisal of collection.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCollectionBean> getAppraisalCollectionBeansByCollectionId(Integer collectionId)
			throws Exception {

		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalCollectionBeansByCollectionId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		List<AppraisalCollectionBean> appraisalList = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalCollectionBeansByCollectionId");
		return appraisalList;
	}

	/**
	 * This method get list of Appraisal.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCollectionBean> getAppraisalBeansByAppraisalId(Integer appraisalId) throws Exception {

		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalBeansByAppraisalId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		List<AppraisalCollectionBean> appraisalList = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalBeansByAppraisalId");
		return appraisalList;
	}
	

	/**
	 * This method performs get All collections item by id and collection id.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCollectionBean> getAppraisalByAppraisalIdAndCollectionId(JSONArray collectionId,
			Integer appraisalId) throws Exception {
		int collectionIdi = 0;
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalByAppraisalIdAndCollectionId");
		List<AppraisalCollectionBean> collectionBeans = null;
		List<AppraisalCollectionBean> collectionList = new ArrayList<AppraisalCollectionBean>();
		for (int i = 0; i < collectionId.length(); i++) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
			collectionIdi = Integer.parseInt(collectionId.getString(i));
			criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionIdi));
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
			collectionBeans = (List<AppraisalCollectionBean>) criteria.list();
			collectionList.addAll(collectionBeans);
		}

		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalByAppraisalIdAndCollectionId");
		return collectionList;
	}
	
	/**
	 * This method will delete all the collection items.
	 */
	public void deleteCollectionList(List<AppraisalCollectionBean> collectionBeans) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method deleteCollectionList");
		for (AppraisalCollectionBean valouxCollectionItemBean : collectionBeans) {
			thirdDBSessionFactory.getCurrentSession().delete(valouxCollectionItemBean);
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method deleteCollectionList");
	}

	/**
	 * This method will get Valoux Appraisal CollectionBean By Appraisal And
	 * collectionId.
	 */
	private AppraisalCollectionBean getValouxAppraisalCollectionBeanByAppraisalAndCollectionId(Integer appraisalId,
			Integer collectionId) {

		LOGGER.debug("AppraisalDaoImpl : Enter method getValouxAppraisalCollectionBeanByAppraisalAndCollectionId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		AppraisalCollectionBean collectionBean = (AppraisalCollectionBean) criteria.uniqueResult();

		LOGGER.debug("AppraisalDaoImpl : Exit method getValouxAppraisalCollectionBeanByAppraisalAndCollectionId");
		return collectionBean;
	}
	

	/**
	 * This method will delete all the Appraisal collection.
	 */
	public void deletedCollectionFromAppraisal(List<Integer> deleteCollectionList, Integer appraisalId)
			throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method deletedCollectionFromAppraisal");
		for (Integer collectionId : deleteCollectionList) {
			AppraisalCollectionBean valouxAppraisalCollectionBean = getValouxAppraisalCollectionBeanByAppraisalAndCollectionId(
					appraisalId, collectionId);

			if (valouxAppraisalCollectionBean != null) {
				thirdDBSessionFactory.getCurrentSession().delete(valouxAppraisalCollectionBean);
			}
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method deletedCollectionFromAppraisal");
	}
	
	/**
	 * This method get collection appraisal
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCollectionBean> getCollectionAppraisalByCollectionIdAndAppraisalId(Integer collectionId,
			Integer appraisalId) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionAppraisalByCollectionIdAndAppraisalId");
		List<AppraisalCollectionBean> collectionBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		collectionBeans = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionAppraisalByCollectionIdAndAppraisalId");
		return collectionBeans;

	}

	/**
	 * This method get collection appraisal
	 */
	@SuppressWarnings("unchecked")
	public AppraisalCollectionBean getCollectionAppraisalByCollectionAndAppraisalId(Integer collectionId,
			Integer appraisalId) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionAppraisalByCollectionIdAndAppraisalId");
		AppraisalCollectionBean collectionBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		collectionBeans = (AppraisalCollectionBean) criteria.uniqueResult();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionAppraisalByCollectionIdAndAppraisalId");
		return collectionBeans;

	}

	/**
	 * This method delete collection appraisal
	 */
	public void deleteUserCollectionAppraisalList(List<AppraisalCollectionBean> collectionBeans) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method deleteUserCollectionAppraisalList");

		for (AppraisalCollectionBean collectionBean : collectionBeans) {
			thirdDBSessionFactory.getCurrentSession().delete(collectionBean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deleteUserCollectionAppraisalList");

	}

	/**
	 * This method get collection appraisal
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCollectionBean> getCollectionAppraisalsById(Integer collectionId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionAppraisalsById");
		List<AppraisalCollectionBean> collectionBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		collectionBeans = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionAppraisalsById");
		return collectionBeans;
	}
	
	/**
	 * This method delete deleted collection appraisal in collection
	 */
	public void deletedAppraisalsFromCollection(List<Integer> deleteListAppraisals, Integer appraisalCollectionId)
			throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method deletedAppraisalsFromCollection");

		for (Integer appraisalId : deleteListAppraisals) {

			AppraisalCollectionBean appraisalCollectionBean = getCollectionAppraisalByCollectionAndAppraisalId(
					appraisalCollectionId, appraisalId);

			if (appraisalCollectionBean != null) {
				thirdDBSessionFactory.getCurrentSession().delete(appraisalCollectionBean);
			}
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deletedAppraisalsFromCollection");

	}

	/**
	 * This method add selected collection appraisal in collection
	 */
	public void addAppraisalsInCollection(List<AppraisalCollectionBean> appraisalsList) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method addAppraisalsInCollection");

		for (AppraisalCollectionBean valouxCollectionBean : appraisalsList) {
			thirdDBSessionFactory.getCurrentSession().save(valouxCollectionBean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method addAppraisalsInCollection");

	}

	/**
	 * This method delete list of collection appraisals from collection
	 */
	public void deletedAllAppraisalsFromCollection(List<AppraisalCollectionBean> beanList) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method deletedAllAppraisalsFromCollection");

		for (AppraisalCollectionBean bean : beanList) {
			thirdDBSessionFactory.getCurrentSession().delete(bean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deletedAllAppraisalsFromCollection");

	}
	
	/**
	 * This method creates Appraisal for Collection.
	 */
	public AppraisalCollectionBean saveAppraisalCollectionList(List<AppraisalCollectionBean> appraisalCollectionList,
			String requestType) throws Exception {
		AppraisalCollectionBean appCollectionBean = null;
		LOGGER.debug("Enter Method saveAppraisalCollectionList of AppraisalDaoImpl");
		for (AppraisalCollectionBean appraisalCollection : appraisalCollectionList) {

			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", (appraisalCollection.getAppraisalBean().getAppraisalId())));
			criteria.add(Restrictions.eq("valouxCollectionBean.vcid", (appraisalCollection.getValouxCollectionBean().getVcid())));

			appCollectionBean = (AppraisalCollectionBean) criteria.uniqueResult();
			if (appCollectionBean == null) {
				appCollectionBean = new AppraisalCollectionBean();
				appCollectionBean.setId(appraisalCollection.getId());
//				appCollectionBean.setAppraisalId(appraisalCollection.getAppraisalBean().getAppraisalId());
				AppraisalBean appraisalBean = new AppraisalBean();
				appraisalBean.setAppraisalId(appraisalCollection.getAppraisalBean().getAppraisalId());
				appCollectionBean.setAppraisalBean(appraisalBean);
				
//				appCollectionBean.setCollectionId(appraisalCollection.getValouxCollectionBean().getVcid());
				ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
				valouxCollectionBean.setVcid(appraisalCollection.getValouxCollectionBean().getVcid());
				appCollectionBean.setValouxCollectionBean(valouxCollectionBean);
				
				appCollectionBean.setStatus(appraisalCollection.getStatus());
				appCollectionBean.setModifiedBy(appraisalCollection.getCreatedBy());
				appCollectionBean.setModifiedOn(appraisalCollection.getCreatedOn());
				if (requestType != null && requestType.equalsIgnoreCase("Update")) {
					thirdDBSessionFactory.getCurrentSession().saveOrUpdate(appCollectionBean);
				} else {
					appCollectionBean.setCreatedBy(appraisalCollection.getCreatedBy());
					appCollectionBean.setCreatedOn(appraisalCollection.getCreatedOn());

					thirdDBSessionFactory.getCurrentSession().save(appCollectionBean);
				}
			}
			LOGGER.debug("Exit Method saveAppraisalCollectionList of AppraisalDaoImpl");
		}
		return appCollectionBean;
	}

	@SuppressWarnings("unchecked")
	public List<AppraisalCollectionBean> getAppraisalCollectionssByCollectionAndNotThisAppraisalId(
			Integer vcid, Integer appraisalId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionAppraisalsById");
		List<AppraisalCollectionBean> collectionBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", vcid));
		criteria.add(Restrictions.ne("appraisalBean.appraisalId", appraisalId));
		criteria.add(Restrictions.eq("status", CommonConstants.APPRAISAL_STATUS_APPROVED));
		collectionBeans = (List<AppraisalCollectionBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionAppraisalsById");
		return collectionBeans;
	}

	public AppraisalCollectionBean getAppraisalCollectionByCollectionAndAppraisalId(
			Integer vcid, Integer appraisalId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getAppraisalCollectionByCollectionAndAppraisalId");
		AppraisalCollectionBean collectionBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalCollectionBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", vcid));
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		collectionBeans = (AppraisalCollectionBean) criteria.uniqueResult();
		LOGGER.debug("CollectionDaoImpl : Exit Method getAppraisalCollectionByCollectionAndAppraisalId");
		return collectionBeans;
	}

	public void updateAppraisalCollectionDetails(
			AppraisalCollectionBean appraisalCollectionBean) throws Exception {

		LOGGER.debug("Enter Method updateAppraisalCollectionDetails of AppraisalDaoImpl");
		if(appraisalCollectionBean != null) {
			thirdDBSessionFactory.getCurrentSession().update(appraisalCollectionBean);
		}
		LOGGER.debug("Exit Method updateAppraisalCollectionDetails of AppraisalDaoImpl");
	}

}
