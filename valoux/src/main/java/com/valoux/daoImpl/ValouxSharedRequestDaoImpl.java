package com.valoux.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.beans.factory.annotation.Qualifier;

import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxSharedRequestDao;

public class ValouxSharedRequestDaoImpl implements ValouxSharedRequestDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private static final Logger LOGGER = Logger.getLogger(ValouxSharedRequestDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<ValouxSharedRequestBean> getShareItemListNotInItemList(String sharedTo, Object[] objects,
			String sharedBy) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getShareItemListNotInCollection");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		criteria.add(Restrictions.eq("sharedItemType", 1));
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.eq("approveStatus", 2));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("sharedItemId", objects)));
		}

		List<ValouxSharedRequestBean> shareRequestBeanList = (List<ValouxSharedRequestBean>) criteria.list();

		LOGGER.debug("ItemDaoImpl : Exit method getShareItemListNotInCollection");
		return shareRequestBeanList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ValouxSharedRequestBean> getShareAppraisalListNotInItemList(String sharedTo, Object[] objects,String sharedBy) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getShareAppraisalListNotInItemList");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		criteria.add(Restrictions.eq("sharedItemType", 3));
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.eq("approveStatus", 2));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("sharedItemId", objects)));
		}

		List<ValouxSharedRequestBean> shareRequestBeanList = (List<ValouxSharedRequestBean>) criteria.list();

		LOGGER.debug("ItemDaoImpl : Exit method getShareAppraisalListNotInItemList");
		return shareRequestBeanList;
	}
	
	/**
	 * This method get shared request of collection except the collection id
	 * passed in objects array
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxSharedRequestBean> getShareCollectionListNotInItemList(String sharedTo, Object[] objects,
			String sharedBy) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getShareCollectionListNotInCollectionList");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("sharedItemType", 2));
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.eq("approveStatus", 2));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("sharedItemId", objects)));
		}

		List<ValouxSharedRequestBean> shareRequestBeanList = (List<ValouxSharedRequestBean>) criteria.list();

		LOGGER.debug("ItemDaoImpl : Exit method getShareCollectionListNotInCollectionList");
		return shareRequestBeanList;
	}
	
	/**
	 * * This method will save sharedRequest object
	 */
	public ValouxSharedRequestBean saveSharedRequest(ValouxSharedRequestBean sharedRequestBean) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method saveSharedRequest");
		thirdDBSessionFactory.getCurrentSession().save(sharedRequestBean);
		LOGGER.debug("ItemDaoImpl : Enter Method saveSharedRequest");
		return sharedRequestBean;
	}
	
	/**
	 * This method get list of shared item shared by particular user
	 */
	public List<ValouxSharedRequestBean> getSharedRequestListBySharedByAndItemType(String sharedBy, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		if (itemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", itemType));
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
		criteria.add(Restrictions.eq("status", 1));
		criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("sharedTo"),
				"sharedTo")));
		criteria.setResultTransformer(Transformers.aliasToBean(ValouxSharedRequestBean.class));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared item shared To user
	 */
	public List<ValouxSharedRequestBean> getSharedRequestListBySharedTo(String sharedTo,Integer sharedItemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedTo");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.eq("approveStatus", CommonConstants.STATUS_ACCEPTED));
		if (sharedItemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", sharedItemType));
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
		criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("sharedBy"),
				"sharedBy")));
		criteria.setResultTransformer(Transformers.aliasToBean(ValouxSharedRequestBean.class));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedTo");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared item shared by particular user
	 */
	public List<ValouxSharedRequestBean> getSharedRequestListBySharedByAndItemType(String sharedBy, Integer itemType,
			Integer itemId, String sharedTo,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.ne("approveStatus", CommonConstants.STATUS_REJECTED));
		if (itemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", itemType));
		}
		if (itemId != null) {
			criteria.add(Restrictions.eq("sharedItemId", itemId));
		}
		if (sharedTo != null) {
			criteria.add(Restrictions.eq("sharedTo", sharedTo));
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
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		return sharedBeanList;
	}

	/**
	 * This method get shared request on the basis of shared request id
	 */
	public ValouxSharedRequestBean getSaredRequestBySharedRequestId(Integer sharedRequestId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSaredRequestBySharedRequestId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedRequestId", sharedRequestId));
		ValouxSharedRequestBean sharedBean = (ValouxSharedRequestBean) criteria.uniqueResult();
		LOGGER.debug("ItemDaoImpl : Enter Method getSaredRequestBySharedRequestId");
		return sharedBean;
	}

	/**
	 * This method get list of shared item shared by particular user having
	 * distinct item id
	 */
	public List<ValouxSharedRequestBean> getSharedRequestListBySharedByAndItemTypeDistinctItemId(String sharedBy,
			Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemTypeDistinctItemId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		if (itemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", itemType));
		}
		criteria.add(Restrictions.eq("status", 1));
		ProjectionList projectionList = Projections.projectionList();
		ProjectionList projectionList2 = Projections.projectionList();

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
		projectionList2.add(Projections.distinct(projectionList.add(Projections.property("sharedItemId"),
				"sharedItemId")));
		projectionList2.add(Projections.property("sharedItemType"), "sharedItemType");
		// projectionList2.add(Projections.property("sharedRequestId"),"sharedRequestId");
		criteria.setProjection(projectionList2);
		criteria.setResultTransformer(Transformers.aliasToBean(ValouxSharedRequestBean.class));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemTypeDistinctItemId");
		return sharedBeanList;
	}

	/**
	 * This method get list of item shared to user
	 */
	public List<ValouxSharedRequestBean> getSharedRequestItemListSharedToUser(String sharedTo, Integer itemType,
			Integer approvedStatus,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestItemListSharedToUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		if (itemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", itemType));
		}
		criteria.add(Restrictions.eq("status", 1));
		if (approvedStatus != null) {
			criteria.add(Restrictions.eq("approveStatus", approvedStatus));
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
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestItemListSharedToUser");
		return sharedBeanList;
	}
	
	/**
	 * This method get list of item shared to user
	 */
	public List<ValouxSharedRequestBean> getSharedRequestItemListSharedToUserByConsumer(String sharedTo, Integer itemType,
			Integer approvedStatus,String sharedBy,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestItemListSharedToUserByConsumer");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		if (itemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", itemType));
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
		criteria.add(Restrictions.eq("status", 1));
		if (approvedStatus != null) {
			criteria.add(Restrictions.eq("approveStatus", approvedStatus));
			}
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestItemListSharedToUserByConsumer");
		return sharedBeanList;
	}

	/**
	 * This method get list of item shared to user in requested state
	 */
	public List<ValouxSharedRequestBean> getRequetedItemListSharedToUser(String sharedTo, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getRequetedItemListSharedToUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		if (itemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", itemType));
		}
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.eq("approveStatus", 1));
		
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
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getRequetedItemListSharedToUser");
		return sharedBeanList;
	}
	
	/**
	 * This method get list of shared item shared item id
	 */
	public List<ValouxSharedRequestBean> getSharedRequestListByItemId(Integer itemId,Integer sharedItemType) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListByItemId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedItemId", itemId));
		criteria.add(Restrictions.eq("sharedItemType", sharedItemType));
		criteria.add(Restrictions.eq("status", 1));
		criteria.add(Restrictions.ne("approveStatus", CommonConstants.STATUS_REJECTED));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListByItemId");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared item shared shared to email
	 */
	public List<ValouxSharedRequestBean> getSharedRequestListBySharedToEmail(String sharedToEmail) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedToEmail");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedToEmail", sharedToEmail));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedToEmail");
		return sharedBeanList;
	}

	/**
	 * This method update shared request object
	 */
	public ValouxSharedRequestBean updateSharedRequestBean(ValouxSharedRequestBean sharedRequestBean) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method updateSharedRequestBean");
		thirdDBSessionFactory.getCurrentSession().update(sharedRequestBean);
		LOGGER.debug("ItemDaoImpl : Exit Method updateSharedRequestBean");
		return sharedRequestBean;
	}

	/**
	 * This method get shared request bean by item id and shared o email and
	 * shared to
	 */
	public ValouxSharedRequestBean getSharedRequestBeanByItemIdSharedToEmailAndSharedTo(Integer itemId,
			String sharedToEmail, String sharedTo, Integer sharedItemType) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestBeanByItemIdSharedToEmailAndSharedTo");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedItemId", itemId));
		if (sharedToEmail != null) {
			criteria.add(Restrictions.eq("sharedToEmail", sharedToEmail));
		}
		if (sharedTo != null) {
			criteria.add(Restrictions.eq("sharedTo", sharedTo));
		}
		if (sharedItemType != null) {
			criteria.add(Restrictions.eq("sharedItemType", sharedItemType));
		}
		ValouxSharedRequestBean sharedBean = (ValouxSharedRequestBean) criteria.uniqueResult();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestBeanByItemIdSharedToEmailAndSharedTo");
		return sharedBean;
	}
	
	/**
	 * This method delete item shared
	 */
	public void deleteSharedItem(ValouxSharedRequestBean sharedRequestBean) throws Exception {
		LOGGER.debug("Enter Method deleteSharedItem of ItemDaoImpl");
		thirdDBSessionFactory.getCurrentSession().delete(sharedRequestBean);
		LOGGER.debug("Enter Method deleteSharedItem of ItemDaoImpl");
	}
	

	/**
	 * This method get list of item shared to agent in requested state
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxSharedRequestBean> getRequetedItemListSharedToAgent(String relationKey)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getRequetedItemListSharedToAgent");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", relationKey));
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS_ACTIVE));
		criteria.add(Restrictions.eq("approveStatus", CommonConstants.STATUS_REQUESTED));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getRequetedItemListSharedToAgent");
		return sharedBeanList;
	}
	
	/**
	 * This method get list of item shared to agent in requested state
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxSharedRequestBean> getListOfRequestedAndAcceptedSharedItemsToAgent(String relationKey)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getListOfRequestedAndAcceptedSharedItemsToAgent");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", relationKey));
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS_ACTIVE));
		criteria.add(Restrictions.ne("approveStatus", CommonConstants.STATUS_REJECTED));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getListOfRequestedAndAcceptedSharedItemsToAgent");
		return sharedBeanList;
	}

	/**
	 * This method will fetch user shared with agent
	 */
	public List<ValouxSharedRequestBean> getUserListSharedToAgent(String sharedTo) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS_ACTIVE));
		criteria.add(Restrictions.eq("approveStatus", CommonConstants.STATUS_ACCEPTED));
		criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("sharedBy"),"sharedBy")));
		criteria.setResultTransformer(Transformers.aliasToBean(ValouxSharedRequestBean.class));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		return sharedBeanList;
	}

	/**
	 * This method will fetch user shared with agent
	 */
	public List<ValouxSharedRequestBean> getUserListSharedToAgentSharedByUser(
			String sharedBy, String sharedTo) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		criteria.add(Restrictions.eq("status", CommonConstants.STATUS_ACTIVE));
		criteria.add(Restrictions.eq("approveStatus", CommonConstants.STATUS_ACCEPTED));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getSharedRequestListBySharedByAndItemType");
		return sharedBeanList;
	}
	
	/**
	 * This method will fetch user shared with agent
	 */
	public List<ValouxSharedRequestBean> getAllBeanSharedByUser(
			String sharedBy) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getAllBeanSharedByUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedBy", sharedBy));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getAllBeanSharedByUser");
		return sharedBeanList;
	}
	
	/**
	 * This method will fetch user shared with agent
	 */
	public List<ValouxSharedRequestBean> getAllBeanSharedToUser(String sharedTo) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method getAllBeanSharedToUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxSharedRequestBean.class);
		criteria.add(Restrictions.eq("sharedTo", sharedTo));
		List<ValouxSharedRequestBean> sharedBeanList = (List<ValouxSharedRequestBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter Method getAllBeanSharedToUser");
		return sharedBeanList;
	}

}
