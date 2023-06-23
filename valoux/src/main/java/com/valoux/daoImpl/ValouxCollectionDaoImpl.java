package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxCollectionBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxCollectionDao;
import com.valoux.model.ValouxCollectionModel;

public class ValouxCollectionDaoImpl implements ValouxCollectionDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxCollectionDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}
	
	/**
	 * This method get collection Details.
	 */
	public ValouxCollectionBean getCollectionBeanByCollectionId(Integer collectionId) {
		LOGGER.debug("Enter Method getCollectionBeanByCollectionId of AppraisalDaoImpl");
		ValouxCollectionBean collectionBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("vcid", collectionId));
		collectionBean = (ValouxCollectionBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getCollectionBeanByCollectionId of AppraisalDaoImpl");
		return collectionBean;
	}

	/**
	 * This method provides item details.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionBean> getCollectionListNotInAppraisal(String publicKey, Object[] objects)
			throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method getCollectionListNotInAppraisal");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("rkey", publicKey));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("vcid", objects)));
		}

		List<ValouxCollectionBean> collectionBeanList = (List<ValouxCollectionBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit method getCollectionListNotInAppraisal");
		return collectionBeanList;
	}
	
	/**
	 * This method add update collection details.
	 */
	public ValouxCollectionBean addUpdateCollectionDetails(ValouxCollectionBean collectionBean, String requestType)
			throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method addUpdateCollectionDetails");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
			thirdDBSessionFactory.getCurrentSession().save(collectionBean);
		} else if (requestType.equalsIgnoreCase(CommonConstants.UPDATE)) {
			thirdDBSessionFactory.getCurrentSession().update(collectionBean);
		}
		trans.commit();

		LOGGER.debug("CollectionDaoImpl : Exit Method addUpdateCollectionDetails");
		return collectionBean;
	}

	/**
	 * This method provides collection details by id.
	 */
	public ValouxCollectionBean getCollectionDetailsById(int collectionId) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter method getCollectionDetailsById");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("vcid", collectionId));
		ValouxCollectionBean collectionBean = (ValouxCollectionBean) criteria.uniqueResult();
		LOGGER.debug("CollectionDaoImpl : Exit method getCollectionDetailsById");
		return collectionBean;
	}
	
	/**
	 * This method provides all collections by user id.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionBean> getCollectionDetailsByUserId(String relationKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionDetailsByUserId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("rkey", relationKey));

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
		List<ValouxCollectionBean> collectionBeans = (List<ValouxCollectionBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionDetailsByUserId");
		return collectionBeans;
	}
	
	/**
	 * This method fetch the collection bean by name
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionBean> checkCollectionNameExistForUser(String publicKey, String collectionName) {

		LOGGER.debug("CollectionDaoImpl : Enter method checkCollectionNameExistForUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("rkey", publicKey));
		criteria.add(Restrictions.eq("cname", collectionName));

		List<ValouxCollectionBean> collectionImageBeans = (List<ValouxCollectionBean>) criteria.list();

		LOGGER.debug("CollectionDaoImpl : Exit method checkCollectionNameExistForUser");

		return collectionImageBeans;
	}
	
	/**
	 * This method get collection not associated with item
	 */
	public List<ValouxCollectionBean> getCollectionListNotAssociatedWithItem(String publicKey, Object[] collectionArray)
			throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionListNotAssociatedWithItem");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("rkey", publicKey));
		criteria.add(Restrictions.not(Restrictions.eq("collectionStatus", CommonConstants.APPRAISAL_STATUS_APPROVED)));
		if (collectionArray != null && collectionArray.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("vcid", collectionArray)));
		}
		List<ValouxCollectionBean> collectionBean = (List<ValouxCollectionBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionListNotAssociatedWithItem");
		return collectionBean;
	}
	
	/**
	 * This method performs get All collection List with name like keyword.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionBean> getCollectionListForGlobalSearchByName(String publicKey,String keyword,Object[] objects,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("Enter method getCollectionListForGlobalSearchByName of CollectionDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		if(publicKey!=null){
			criteria.add(Restrictions.eq("rkey", publicKey));
		}
		Disjunction orConditions = Restrictions.disjunction();
		orConditions.add(Restrictions.ilike("cname", "%"+keyword+"%"));
		orConditions.add(Restrictions.ilike("shortDescription", "%"+keyword+"%"));
		criteria.add(orConditions); 
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.in("vcid", objects));
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
		List<ValouxCollectionBean> collectionBeanList = (List<ValouxCollectionBean>) criteria.list();
		LOGGER.debug("Exit method getCollectionListForGlobalSearchByName of CollectionDaoImplsss");
		return collectionBeanList;
	}

	@SuppressWarnings("unchecked")
	public List<ValouxCollectionBean> getTopCollectionsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionListNotAssociatedWithItem");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionBean.class);
		criteria.add(Restrictions.eq("rkey", userPublicKey));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(limit);
		List<ValouxCollectionBean> collectionBeans = (List<ValouxCollectionBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionListNotAssociatedWithItem");
		return collectionBeans;
	}
	
	/**
	 * This method delete item component object
	 */
	public void deleteAnyBeanByObject(Object objectBean)
			throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method deleteAnyBeanByObject");

		thirdDBSessionFactory.getCurrentSession().delete(objectBean);

		LOGGER.debug("CollectionDaoImpl : Exit Method deleteAnyBeanByObject");
		
	}

}
