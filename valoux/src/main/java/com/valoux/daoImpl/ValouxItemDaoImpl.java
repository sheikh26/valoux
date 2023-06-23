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

import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxItemDao;

public class ValouxItemDaoImpl implements ValouxItemDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxItemDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}
	/**
	 * This method get items Details.
	 */
	public ValouxItemBean getItemsBeanByItemId(Integer itemId) {
		LOGGER.debug("Enter Method getItemsBeanByItemId of AppraisalDaoImpl");
		ValouxItemBean itemsBean = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("itemId", itemId));
		itemsBean = (ValouxItemBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getItemsBeanByItemId of AppraisalDaoImpl");
		return itemsBean;
	}

	/**
	 * This method provides items details.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getItemsListNotInAppraisal(String publicKey, Object[] objects) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method getItemsListNotInAppraisal");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", publicKey));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("itemId", objects)));
		}
		List<ValouxItemBean> itemsBeanList = (List<ValouxItemBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit method getItemsListNotInAppraisal");
		return itemsBeanList;
	}
	
	/**
	 * This method performs add Item.
	 */
	public ValouxItemBean addItem(ValouxItemBean itemBean) throws Exception {
		LOGGER.debug("Enter Method addItem of ItemDaoImpl");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		thirdDBSessionFactory.getCurrentSession().save(itemBean);
		trans.commit();
		LOGGER.debug("Exit Method addItem of ItemDaoImpl");
		return itemBean;
	}
	
	/**
	 * This method performs get All Item List.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getAllItemList(String publicKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("Enter method getAllItemList of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", publicKey));

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
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("Exit method getAllItemList of ItemDaoImpl");
		return itemBeanList;
	}
	
	/**
	 * This method performs get All Item List with name like keyword.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getItemListForGlobalSearchByName(String publicKey,String keyword,Object[] objects,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("Enter method getItemListForGlobalSearchByName of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		if(publicKey!=null){
		criteria.add(Restrictions.eq("rKey", publicKey));
		}
		//criteria.add(Restrictions.ilike("name", "%"+keyword+"%"));
		Disjunction orConditions = Restrictions.disjunction();
		orConditions.add(Restrictions.ilike("name", "%"+keyword+"%"));
		orConditions.add(Restrictions.ilike("sDescription", "%"+keyword+"%"));
		criteria.add(orConditions); 
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.in("itemId", objects));
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
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("Exit method getItemListForGlobalSearchByName of ItemDaoImpl");
		return itemBeanList;
	}
	
	/**
	 * This method performs get All Item List with name like keyword.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getItemListForGlobalSearchBySDescription(String publicKey,String keyword,Object[] objects) throws Exception {
		LOGGER.debug("Enter method getItemListForGlobalSearchBySDescription of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", publicKey));
		criteria.add(Restrictions.ilike("sDescription", keyword));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.in("itemId", objects));
		}
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("Exit method getItemListForGlobalSearchBySDescription of ItemDaoImpl");
		return itemBeanList;
	}
	
	/**
	 * This method get the item detail
	 */
	public ValouxItemBean getItemDetailByItemIdAndRkey(Integer itemId, String rKey) throws Exception {
		LOGGER.debug("Enter Method getItemDetailByItemIdAndRkey of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", rKey));
		criteria.add(Restrictions.eq("itemId", itemId));
		ValouxItemBean itemBean = (ValouxItemBean) criteria.uniqueResult();
		LOGGER.debug("Exit Method getItemDetailByItemIdAndRkey of ItemDaoImpl");
		return itemBean;
	}

	/**
	 * This method update the item detail
	 */
	public ValouxItemBean updateItemdetail(ValouxItemBean itemBean) throws Exception {
		LOGGER.debug("Enter Method updateItemdetail of ItemDaoImpl");
		thirdDBSessionFactory.getCurrentSession().update(itemBean);
		LOGGER.debug("Exit Method updateItemdetail of ItemDaoImpl");
		return itemBean;
	}
	
	/**
	 * This method provides item details.
	 */
	public ValouxItemBean getValouxItemDetailsById(Integer itemId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getValouxItemDetailsById");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("itemId", itemId));
		ValouxItemBean itemBean = (ValouxItemBean) criteria.uniqueResult();
		LOGGER.debug("ItemDaoImpl : Exit method getValouxItemDetailsById");
		return itemBean;
	}

	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getConsumerItemsNotInCollection(String publicKey, Object[] objects) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getConsumerItemsNotInCollection");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", publicKey));
		if (objects != null && objects.length > 0) {
			criteria.add(Restrictions.not(Restrictions.in("itemId", objects)));
		}

		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();

		LOGGER.debug("ItemDaoImpl : Exit method getConsumerItemsNotInCollection");
		return itemBeanList;
	}
	
	/**
	 * This method get the detail by storeId
	 */
	public List<ValouxItemBean> getItemDetailByStoreId(Integer storeId) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method getItemDetailByStoreId");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("storeId", storeId));
		@SuppressWarnings("unchecked")
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Exit method getItemDetailByStoreId");
		return itemBeanList;
	}

	/**
	 * This method check the item name exist for user
	 */
	public List<ValouxItemBean> checkItemNameExistForUser(String publicKey, String itemName) throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter method checkItemNameExistForUser");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", publicKey));
		criteria.add(Restrictions.eq("name", itemName));
		@SuppressWarnings("unchecked")
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("ItemDaoImpl : Enter method checkItemNameExistForUser");
		return itemBeanList;
	}
	

	/**
	 * This method delete item component object
	 */
	public void deleteAnyBeanByObject(Object objectBean)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method deleteAnyBeanByObject");

		thirdDBSessionFactory.getCurrentSession().delete(objectBean);

		LOGGER.debug("ItemDaoImpl : Exit Method deleteAnyBeanByObject");
		
	}

	/**
	 * This method add update item component object
	 */
	public void addUpdateItemComponent(ValouxItemComponentBean componentBean)
			throws Exception {
		LOGGER.debug("ItemDaoImpl : Enter Method addUpdateItemComponent");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();

		if (componentBean != null) {
			thirdDBSessionFactory.getCurrentSession().persist(componentBean);
		}
		trans.commit();
		LOGGER.debug("ItemDaoImpl : Exit Method addUpdateItemComponent");
		
	}
	
	/**
	 * This method merge store
	 */
	public void mergestore(Integer primaryStoreId, Integer storeIdToBeMerged) throws Exception{
		LOGGER.debug("Enter Method mergestore of ValouxItemDaoImpl");
//		thirdDBSessionFactory.openSession();
//		Transaction tx = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		String hqlUpdate = "update valoux_item s set s.store_id=:primaryStoreId where s.store_id=:storeIdToBeMerged";
		// or String hqlUpdate = "update Customer set name = :newName where name = :oldName";
		int updatedEntities = thirdDBSessionFactory.getCurrentSession().createSQLQuery(hqlUpdate).setInteger("primaryStoreId", primaryStoreId).setInteger("storeIdToBeMerged", storeIdToBeMerged).executeUpdate();
		
//		if (!tx.wasCommitted())
//		    tx.commit();
//		thirdDBSessionFactory.close();
		LOGGER.debug("Enter Method mergestore of ValouxItemDaoImpl");
	}
	
	/**
	 *  Method for item price properties
	 */
	public void updateItemPriceProperties(ValouxItemBean itemBean)
			throws Exception {
		LOGGER.debug("Enter Method updateItemPriceProperties of ItemDaoImpl");
		Transaction trans = thirdDBSessionFactory.getCurrentSession().beginTransaction();
		thirdDBSessionFactory.getCurrentSession().persist(itemBean);
		trans.commit();
		LOGGER.debug("Exit Method updateItemPriceProperties of ItemDaoImpl");
	}
	
	/**
	 * This method get the item detail
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getItemDetailByStoreIdAndRkey(Integer storeId, String rKey) throws Exception {
		LOGGER.debug("Enter Method getItemDetailByStoreIdAndRkey of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", rKey));
		criteria.add(Restrictions.eq("storeId", storeId));
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("Exit Method getItemDetailByStoreIdAndRkey of ItemDaoImpl");
		return itemBeanList;
	}

	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getAllListOfItems() throws Exception {
		LOGGER.debug("Enter Method getAllListOfItems of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("Exit Method getAllListOfItems of ItemDaoImpl");
		return itemBeanList;
	}

	@SuppressWarnings("unchecked")
	public List<ValouxItemBean> getTopItemsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception {
		LOGGER.debug("Enter Method getAllListOfItems of ItemDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxItemBean.class);
		criteria.add(Restrictions.eq("rKey", userPublicKey));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(limit);
		List<ValouxItemBean> itemBeanList = (List<ValouxItemBean>) criteria.list();
		LOGGER.debug("Exit Method getAllListOfItems of ItemDaoImpl");
		return itemBeanList;
	}

}
