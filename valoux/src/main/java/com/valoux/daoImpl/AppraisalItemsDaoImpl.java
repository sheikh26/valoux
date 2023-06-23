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
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AppraisalItemsDao;

public class AppraisalItemsDaoImpl implements AppraisalItemsDao {

	@Autowired
	@Qualifier(value = "thirdDBSessionFactory")
	private SessionFactory thirdDBSessionFactory;

	private static final Logger LOGGER = Logger.getLogger(AppraisalItemsDaoImpl.class);

	public SessionFactory getThirdDBSessionFactory() {
		return thirdDBSessionFactory;
	}

	public void setThirdDBSessionFactory(SessionFactory thirdDBSessionFactory) {
		this.thirdDBSessionFactory = thirdDBSessionFactory;
	}
	
	/**
	 * This method creates Appraisal for Items.
	 */
	public AppraisalItemsBean saveAppraisalItemList(List<AppraisalItemsBean> appraisalItemList, String requestType)
			throws Exception {
		LOGGER.debug("Enter Method saveAppraisalItemList of AppraisalDaoImpl");
		AppraisalItemsBean appItemsBean = null;
		for (AppraisalItemsBean appraisalItem : appraisalItemList) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", (appraisalItem.getAppraisalBean().getAppraisalId())));
			criteria.add(Restrictions.eq("valouxItemBean.itemId", (appraisalItem.getValouxItemBean().getItemId())));

			appItemsBean = (AppraisalItemsBean) criteria.uniqueResult();
			if (appItemsBean == null) {
				appItemsBean = new AppraisalItemsBean();
				appItemsBean.setId(appraisalItem.getId());
//				appItemsBean.setAppraisalId(appraisalItem.getAppraisalId());
				AppraisalBean appraisalBean = new AppraisalBean();
				appraisalBean.setAppraisalId(appraisalItem.getAppraisalBean().getAppraisalId());
				appItemsBean.setAppraisalBean(appraisalBean);
				
//				appItemsBean.setItemId(appraisalItem.getItemId());
				ValouxItemBean valouxItemBean = new ValouxItemBean();
				valouxItemBean.setItemId(appraisalItem.getValouxItemBean().getItemId());
				appItemsBean.setValouxItemBean(valouxItemBean);
				
				appItemsBean.setStatus(appraisalItem.getStatus());
				appItemsBean.setModifiedBy(appraisalItem.getModifiedBy());
				appItemsBean.setModifiedOn(appraisalItem.getModifiedOn());
				if (requestType != null && requestType.equalsIgnoreCase("Update")) {
					thirdDBSessionFactory.getCurrentSession().saveOrUpdate(appItemsBean);
				} else {
					appItemsBean.setCreatedBy(appraisalItem.getCreatedBy());
					appItemsBean.setCreatedOn(appraisalItem.getCreatedOn());
					thirdDBSessionFactory.getCurrentSession().save(appItemsBean);
				}
			}
			LOGGER.debug("Exit Method saveAppraisalItemList of AppraisalDaoImpl");
		}
		return appItemsBean;
	}
	
	/**
	 * This method get all the item associated with appraisal
	 */
	public List<AppraisalItemsBean> getItemAssociatedWithAppraisal(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter Method getItemAssociatedWithAppraisal of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		List<AppraisalItemsBean> appraisalItemsList = (List<AppraisalItemsBean>) criteria.list();
		LOGGER.debug("Exit Method getItemAssociatedWithAppraisal of AppraisalDaoImpl");
		return appraisalItemsList;
	}
	
	/**
	 * This method performs get appraisal collection Bean By appraisalId.
	 */
	public List<AppraisalItemsBean> getAppraisalItemDataByAppraisalId(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter method getAppraisalItemDataByAppraisalId of AppraisalDaoImpl");
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		@SuppressWarnings("unchecked")
		List<AppraisalItemsBean> appraisalItemBeanList = (List<AppraisalItemsBean>) criteria.list();
		LOGGER.debug("Exit method getAppraisalItemDataByAppraisalId of AppraisalDaoImpl");
		return appraisalItemBeanList;
	}
	
	/**
	 * This method performs get All collections item by id and collection id.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalItemsBean> getAppraisalByAppraisalIdAndItemId(JSONArray itemId, Integer appraisalId)
			throws Exception {
		int itemIdi = 0;
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalByAppraisalIdAndItemId");
		List<AppraisalItemsBean> collectionBeans = null;
		List<AppraisalItemsBean> itemList = new ArrayList<AppraisalItemsBean>();
		for (int i = 0; i < itemId.length(); i++) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
			itemIdi = Integer.parseInt(itemId.getString(i));
			criteria.add(Restrictions.eq("valouxItemBean.itemId", itemIdi));
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
			collectionBeans = (List<AppraisalItemsBean>) criteria.list();
			itemList.addAll(collectionBeans);
		}

		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalByAppraisalIdAndItemId");
		return itemList;
	}

	/**
	 * This method performs get All collections item by id and collection id.
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalItemsBean> getAppraisalByAppraisalIdAndItemId(Integer itemId, JSONArray appraisalId)
			throws Exception {
		int appraisalIdi = 0;
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalByAppraisalIdAndItemId");
		List<AppraisalItemsBean> collectionBeans = null;
		List<AppraisalItemsBean> itemList = new ArrayList<AppraisalItemsBean>();
		for (int i = 0; i < appraisalId.length(); i++) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
			appraisalIdi = Integer.parseInt(appraisalId.getString(i));
			criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalIdi));
			collectionBeans = (List<AppraisalItemsBean>) criteria.list();
			itemList.addAll(collectionBeans);
		}

		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalByAppraisalIdAndItemId");
		return itemList;
	}

	/**
	 * This method will delete all the items.
	 */
	public void deleteItemList(List<AppraisalItemsBean> itemBeans) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method deleteItemList");
		for (AppraisalItemsBean valouxItemBean : itemBeans) {
			thirdDBSessionFactory.getCurrentSession().delete(valouxItemBean);
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method deleteItemList");
	}

	/**
	 * This method will get Valoux Appraisal ItemBean By Appraisal And ItemId.
	 */
	private AppraisalItemsBean getValouxAppraisalItemBeanByAppraisalAndItemId(Integer appraisalId, Integer itemId) {

		LOGGER.debug("AppraisalDaoImpl : Enter method getValouxCollectionItemBeanByCollectionAndItemId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		AppraisalItemsBean itemBean = (AppraisalItemsBean) criteria.uniqueResult();

		LOGGER.debug("AppraisalDaoImpl : Exit method getValouxCollectionItemBeanByCollectionAndItemId");
		return itemBean;
	}

	/**
	 * This method will delete all the Appraisal items.
	 */
	public void deletedItemsFromAppraisal(List<Integer> deleteListItems, Integer appraisalId) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method deletedItemsFromCollection");
		for (Integer itemId : deleteListItems) {
			AppraisalItemsBean valouxAppraisalItemBean = getValouxAppraisalItemBeanByAppraisalAndItemId(appraisalId,
					itemId);
			if (valouxAppraisalItemBean != null) {
				thirdDBSessionFactory.getCurrentSession().delete(valouxAppraisalItemBean);
			}
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method deletedItemsFromCollection");
	}

	/**
	 * This method get the appraisal item detail by item id
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalItemsBean> getApraisalItemListByItemId(Integer itemId) throws Exception {
		LOGGER.debug("Exit Method getApraisalItemListByItemId of AppraisalDaoImpl");
		List<AppraisalItemsBean> appraisalItemlist = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		appraisalItemlist = (List<AppraisalItemsBean>) criteria.list();
		LOGGER.debug("Exit Method getApraisalItemListByItemId of AppraisalDaoImpl");
		return appraisalItemlist;
	}

	/**
	 * This method check Existing Appraisal for ItemList.
	 */
	public AppraisalItemsBean checkExistingAppraisalItemList(List<AppraisalItemsBean> appraisalItemList,
			String requestType) throws Exception {
		LOGGER.debug("Enter Method checkExistingAppraisalItemList of AppraisalDaoImpl");
		AppraisalItemsBean appItemsBean = null;
		for (AppraisalItemsBean appraisalItem : appraisalItemList) {
			Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
			criteria.add(Restrictions.eq("appraisalBean.appraisalId", (appraisalItem.getAppraisalBean().getAppraisalId())));
			criteria.add(Restrictions.eq("valouxItemBean.itemId", (appraisalItem.getValouxItemBean().getItemId())));

			appItemsBean = (AppraisalItemsBean) criteria.uniqueResult();

		}
		LOGGER.debug("Exit Method checkExistingAppraisalItemList of AppraisalDaoImpl");
		return appItemsBean;
	}
	
	/**
	 * This method will get all the Appraisal items.
	 */
	public List<AppraisalItemsBean> getAppraisalItemsBeanByAppraisalAndItemId(Integer appraisalId, int itemId)
			throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalItemsBeanByAppraisalAndItemId");
		List<AppraisalItemsBean> itemBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		itemBeans = (List<AppraisalItemsBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalItemsBeanByAppraisalAndItemId");
		return itemBeans;
	}

	/**
	 * This method will add all the Appraisal items.
	 */
	public void addItemsInAppraisals(List<AppraisalItemsBean> itemsList) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method addItemsInAppraisals");
		for (AppraisalItemsBean appraisalItemsBean : itemsList) {
			thirdDBSessionFactory.getCurrentSession().save(appraisalItemsBean);
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method addItemsInAppraisals");
	}

	/**
	 * This method will delete all the Appraisal items.
	 */
	public void deletedAllItemsFromAppraisals(List<AppraisalItemsBean> beanList) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method deletedAllItemsFromAppraisals");
		for (AppraisalItemsBean bean : beanList) {
			thirdDBSessionFactory.getCurrentSession().delete(bean);
		}
		LOGGER.debug("AppraisalDaoImpl : Exit Method deletedAllItemsFromAppraisals");
	}

	public AppraisalItemsBean getAppraisalItemBeanByAppraisalAndItemId(
			Integer appraisalId, int itemId) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter method getAppraisalItemBeanByAppraisalAndItemId");

		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.eq("appraisalBean.appraisalId", appraisalId));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		AppraisalItemsBean itemBean = (AppraisalItemsBean) criteria.uniqueResult();

		LOGGER.debug("AppraisalDaoImpl : Exit method getAppraisalItemBeanByAppraisalAndItemId");
		return itemBean;
	}

	@SuppressWarnings("unchecked")
	public List<AppraisalItemsBean> getAppraisalItemsByItemIdAndNotThisAppraisalId(
			Integer itemId, Integer appraisalId) throws Exception {
		LOGGER.debug("AppraisalDaoImpl : Enter Method getAppraisalItemsByItemIdAndNotThisAppraisalId");
		List<AppraisalItemsBean> itemBeans = null;
		Criteria criteria = thirdDBSessionFactory.getCurrentSession().createCriteria(AppraisalItemsBean.class);
		criteria.add(Restrictions.ne("appraisalBean.appraisalId", appraisalId));
		criteria.add(Restrictions.eq("valouxItemBean.itemId", itemId));
		criteria.add(Restrictions.eq("status", CommonConstants.APPRAISAL_STATUS_APPROVED));
		itemBeans = (List<AppraisalItemsBean>) criteria.list();

		LOGGER.debug("AppraisalDaoImpl : Exit Method getAppraisalItemsByItemIdAndNotThisAppraisalId");
		return itemBeans;
	}

	public void updateAppraisalItemDetails(AppraisalItemsBean appraisalItemsBean)
			throws Exception {
		LOGGER.debug("Enter Method updateAppraisalItemDetails of AppraisalDaoImpl");
		if(appraisalItemsBean != null) {
			thirdDBSessionFactory.getCurrentSession().update(appraisalItemsBean);
		}
		LOGGER.debug("Exit Method updateAppraisalItemDetails of AppraisalDaoImpl");
	}
	
}
