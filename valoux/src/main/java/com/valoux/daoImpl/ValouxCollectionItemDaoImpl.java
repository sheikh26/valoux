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

import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxCollectionItemDao;

public class ValouxCollectionItemDaoImpl implements ValouxCollectionItemDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ValouxCollectionItemDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}
	
	/**
	 * This method performs get item count By appraisalId.
	 */
	public List<ValouxCollectionItemBean> getItemAssociatedWithCollection(Integer vcid) throws Exception {
		LOGGER.debug("Enter method getItemAssociatedWithCollection of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", vcid));
		@SuppressWarnings("unchecked")
		List<ValouxCollectionItemBean> appraisalBeanList = (List<ValouxCollectionItemBean>) criteria.list();
		LOGGER.debug("Exit method getItemAssociatedWithCollection of AppraisalDaoImpl");
		return appraisalBeanList;
	}
	
	/**
	 * This method will save list items in collection.
	 */
	public void addItemsInCollection(List<ValouxCollectionItemBean> itemsList) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method addItemsInCollection");

		for (ValouxCollectionItemBean valouxCollectionItemBean : itemsList) {
			thirdDBSessionFactory.getCurrentSession().save(valouxCollectionItemBean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method addItemsInCollection");
	}


	/**
	 * This method performs get All collections item by id.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionItemBean> getCollectionItemsById(Integer collectionId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionItemsById");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		List<ValouxCollectionItemBean> collectionItemBeans = (List<ValouxCollectionItemBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionItemsById");
		return collectionItemBeans;
	}

	/**
	 * This method performs get All collections item by id and collection id.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionItemBean> getCollectionItemsByCollectionIdAndItemId(Integer collectionId, Integer itemId)
			throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionItemsByCollectionIdAndItemId");
		List<ValouxCollectionItemBean> collectionItemBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		collectionItemBeans = (List<ValouxCollectionItemBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionItemsByCollectionIdAndItemId");
		return collectionItemBeans;
	}

	/**
	 * This method get the ValouxCollectionItemBean by itemid
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionItemBean> getCollectionItemsByItemId(Integer itemId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionItemsByItemId");
		List<ValouxCollectionItemBean> collectionItemBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		collectionItemBeans = (List<ValouxCollectionItemBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionItemsByItemId");
		return collectionItemBeans;
	}

	/**
	 * This method will delete all the collection items.
	 */
	public void deleteUserCollectionItemsList(List<ValouxCollectionItemBean> collectionBeans) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method deleteUserCollectionItemsList");

		for (ValouxCollectionItemBean valouxCollectionItemBean : collectionBeans) {
			thirdDBSessionFactory.getCurrentSession().delete(valouxCollectionItemBean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deleteUserCollectionItemsList");

	}
	
	/**
	 * This method will delete all the collection items.
	 */
	public void deletedItemsFromCollection(List<Integer> deleteListItems, Integer collectionId) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method deletedItemsFromCollection");

		for (Integer itemId : deleteListItems) {

			ValouxCollectionItemBean valouxCollectionItemBean = getValouxCollectionItemBeanByCollectionAndItemId(
					collectionId, itemId);

			if (valouxCollectionItemBean != null) {
				thirdDBSessionFactory.getCurrentSession().delete(valouxCollectionItemBean);
			}
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deletedItemsFromCollection");

	}

	/**
	 * This method will get Valoux Collection ItemBean By Collection And ItemId.
	 */
	private ValouxCollectionItemBean getValouxCollectionItemBeanByCollectionAndItemId(Integer collectionId,
			Integer itemId) {

		LOGGER.debug("CollectionDaoImpl : Enter method getValouxCollectionItemBeanByCollectionAndItemId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionId));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		ValouxCollectionItemBean itemBean = (ValouxCollectionItemBean) criteria.uniqueResult();

		LOGGER.debug("CollectionDaoImpl : Exit method getValouxCollectionItemBeanByCollectionAndItemId");
		return itemBean;
	}
	
	/**
	 * This method delete the ValouxCollectionImageBean
	 */
	public void deletedAllItemsFromCollection(List<ValouxCollectionItemBean> beanList) throws Exception {

		LOGGER.debug("CollectionDaoImpl : Enter Method deletedAllItemsFromCollection");

		for (ValouxCollectionItemBean bean : beanList) {
			thirdDBSessionFactory.getCurrentSession().delete(bean);
		}

		LOGGER.debug("CollectionDaoImpl : Exit Method deletedAllItemsFromCollection");

	}
	
	/**
	 * This method performs get All collections item by id and collection id.
	 */
	@SuppressWarnings("unchecked")
	public List<ValouxCollectionItemBean> getCollectionItemsByCollectionArrayIdAndItemId(JSONArray collectionId,
			Integer itemId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionItemsByCollectionArrayIdAndItemId");
		int collectionIdi = 0;
		List<ValouxCollectionItemBean> collectionItemBeans = null;
		List<ValouxCollectionItemBean> collectionList = new ArrayList<ValouxCollectionItemBean>();
		for (int i = 0; i < collectionId.length(); i++) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession()
					.createCriteria(ValouxCollectionItemBean.class);
			collectionIdi = Integer.parseInt(collectionId.getString(i));
			criteria.add(Restrictions.eq("valouxCollectionBean.vcid", collectionIdi));
			criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
			collectionItemBeans = (List<ValouxCollectionItemBean>) criteria.list();
			collectionList.addAll(collectionItemBeans);
		}
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionItemsByCollectionArrayIdAndItemId");
		return collectionItemBeans;
	}

	public List<ValouxCollectionItemBean> getCollectionItemsByItemIdAndNotThisCollectionId(
			Integer vcid, Integer itemId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionItemsByItemIdAndCollectionId");
		List<ValouxCollectionItemBean> collectionItemBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.ne("valouxCollectionBean.vcid", vcid));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		criteria.add(Restrictions.eq("status", CommonConstants.APPRAISAL_STATUS_APPROVED.byteValue()));
		collectionItemBeans = (List<ValouxCollectionItemBean>) criteria.list();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionItemsByItemIdAndCollectionId");
		return collectionItemBeans;
	}

	public ValouxCollectionItemBean getCollectionItemsByCollectionAndItemId(
			Integer vcid, Integer itemId) throws Exception {
		LOGGER.debug("CollectionDaoImpl : Enter Method getCollectionItemsByCollectionAndItemId");
		ValouxCollectionItemBean collectionItemBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(ValouxCollectionItemBean.class);
		criteria.add(Restrictions.eq("valouxCollectionBean.vcid", vcid));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		collectionItemBeans = (ValouxCollectionItemBean) criteria.uniqueResult();
		LOGGER.debug("CollectionDaoImpl : Exit Method getCollectionItemsByCollectionAndItemId");
		return collectionItemBeans;
	}

	public void updateCollectionItemDetails(
			ValouxCollectionItemBean valouxCollectionItemBean) throws Exception {
		LOGGER.debug("Enter Method updateCollectionItemDetails of CollectionDaoImpl");
		if(valouxCollectionItemBean != null) {
			thirdDBSessionFactory.getCurrentSession().update(valouxCollectionItemBean);
		}
		LOGGER.debug("Exit Method updateCollectionItemDetails of CollectionDaoImpl");
	}


}
