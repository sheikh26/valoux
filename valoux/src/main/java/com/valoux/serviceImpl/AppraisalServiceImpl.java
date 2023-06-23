/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.transaction.annotation.Transactional;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.ValouxAccessPermissionBean;
import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
import com.valoux.bean.ValouxAppraisalItemsPriceBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AppraisalCollectionDao;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.AppraisalItemsDao;
import com.valoux.dao.LoginDao;
import com.valoux.dao.ValouxAccessPermissionDao;
import com.valoux.dao.ValouxAppraisalItemsComponentPriceDao;
import com.valoux.dao.ValouxAppraisalItemsPriceDao;
import com.valoux.dao.ValouxCollectionDao;
import com.valoux.dao.ValouxCollectionItemDao;
import com.valoux.dao.ValouxItemDao;
import com.valoux.dao.ValouxSharedRequestDao;
import com.valoux.helper.AppraisalHelper;
import com.valoux.helper.UserHelper;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ValouxAccessPermissionModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.service.AppraisalService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.PrepareModels;

/**
 * This <java>class</java> AppraisalServiceImpl use to perform all our service
 * related logics for the Appraisal.
 * 
 * @author param Sheikh
 * 
 */

public class AppraisalServiceImpl implements AppraisalService {

	@Autowired
	AppraisalDao appraisalDao;

	@Autowired
	ValouxSharedRequestDao valouxSharedRequestDao;

	@Autowired
	AppraisalItemsDao appraisalItemsDao;
	
	@Autowired
	AppraisalCollectionDao appraisalCollectionDao;
	
	@Autowired
	ValouxCollectionDao valouxCollectionDao;
	
	@Autowired
	ValouxItemDao valouxItemDao;
	
	@Autowired
	ValouxAccessPermissionDao valouxAccessPermissionDao;
	
	@Autowired
	ValouxCollectionItemDao valouxCollectionItemDao;
	
	@Autowired
	ValouxAppraisalItemsComponentPriceDao valouxAppraisalItemsComponentPriceDao;
	
	@Autowired
	ValouxAppraisalItemsPriceDao valouxAppraisalItemsPriceDao;
	
	@Autowired
	LoginDao loginDao;

	private static final Logger LOGGER = Logger.getLogger(AppraisalServiceImpl.class);

	/**
	 * This method creates Appraisal or update Appraisal.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public AppraisalModel createOrUpdateAppraisal(AppraisalModel appraisalModel) throws Exception {
		LOGGER.debug("Enter Method createOrUpdateAppraisal of AppraisalServiceImpl");
		AppraisalBean appraisalBean = new AppraisalBean();
		// AppraisalBean appraisalBean2 = null;
		AppraisalModel appraisalModelToReturn = null;
		appraisalBean = appraisalDao.getAppraisalDetails(appraisalModel.getAppraisalId());
		if (appraisalBean != null) {
			appraisalBean = CommonUserUtility.prepareAppraisalBeanFromModel(appraisalModel, appraisalBean);
			appraisalBean = appraisalDao.createOrUpdateAppraisal(appraisalBean);
			appraisalModelToReturn = PrepareModels.prepareAppraisalModelFromAppraisalBean(appraisalBean);
		} else {
			appraisalBean = CommonUserUtility.prepareAppraisalBeanFromModel(appraisalModel, appraisalBean);
			appraisalBean = appraisalDao.createOrUpdateAppraisal(appraisalBean);
			appraisalModelToReturn = PrepareModels.prepareAppraisalModelFromAppraisalBean(appraisalBean);
		}
		LOGGER.debug("Exit Method createOrUpdateAppraisal of AppraisalServiceImpl");
		return appraisalModelToReturn;
	}

	/**
	 * This method creates Appraisal for Items.
	 * 
	 * @throws Exception
	 */
	// @Transactional
	// public AppraisalItemsBean addAppraisalItems(AppraisalItemsModel
	// appraisalItemsModel) throws Exception {
	// LOGGER.debug("Enter Method createUser of addAppraisalItems");
	// AppraisalItemsBean appItemsBean = null;
	// if (appraisalItemsModel != null) {
	//
	// JSONArray itemOfItems = appraisalItemsModel.getItemId();
	// //List<AppraisalItemsBean> itemsList = new
	// ArrayList<AppraisalItemsBean>();
	//
	// List<Integer> dbitemList = new ArrayList<Integer>();
	//
	// List<Integer> modelItemList = new ArrayList<Integer>();
	//
	// List<AppraisalItemsBean> itemBeans =
	// appraisalDao.getItemAssociatedWithAppraisal(appraisalItemsModel.getAppraisalId());
	//
	// if (itemBeans != null && itemBeans.size() > 0) {
	// for (AppraisalItemsBean valouxCollectionItemBean : itemBeans) {
	// dbitemList.add(valouxCollectionItemBean.getItemId());
	// }
	// }
	// Collections.sort(dbitemList);
	//
	// for (int i = 0; i < itemOfItems.length(); i++) {
	// modelItemList.add((Integer) itemOfItems.get(i));
	// }
	// Collections.sort(modelItemList);
	//
	// List<Integer> deleteListItems = new ArrayList<Integer>(dbitemList);
	// deleteListItems.removeAll(modelItemList);
	//
	// if (deleteListItems != null && deleteListItems.size() > 0) {
	// appraisalDao.deletedItemsFromAppraisal(deleteListItems,
	// appraisalItemsModel.getAppraisalId());
	// }
	//
	// if (appraisalItemsModel.getItemId() != null) {
	// JSONArray appraisalItemsInJsonArray = appraisalItemsModel.getItemId();
	// List<AppraisalItemsBean> appraisalItemsList = new
	// ArrayList<AppraisalItemsBean>();
	//
	// for (int i = 0; i < appraisalItemsInJsonArray.length(); i++) {
	// int appraisalItems =
	// Integer.parseInt(appraisalItemsInJsonArray.getString(i));
	// appItemsBean = new AppraisalItemsBean();
	// if (appraisalItemsModel.getId() != null) {
	// appItemsBean.setId(appraisalItemsModel.getId());
	// } else {
	// appItemsBean.setCreatedBy(appraisalItemsModel.getCreatedBy());
	// appItemsBean.setCreatedOn(CommonUtility.getDateAndTime());
	// }
	// appItemsBean.setAppraisalId(appraisalItemsModel.getAppraisalId());
	// appItemsBean.setItemId(appraisalItems);
	// appItemsBean.setStatus(appraisalItemsModel.getStatus());
	// appItemsBean.setModifiedBy(appraisalItemsModel.getModifiedBy());
	// appItemsBean.setModifiedOn(appraisalItemsModel.getModifiedOn());
	// appraisalItemsList.add(appItemsBean);
	//
	// }
	// appItemsBean = appraisalDao.saveAppraisalItemList(appraisalItemsList,
	// appraisalItemsModel.getRequestType());
	// }
	// }
	// return appItemsBean;
	// }

	/**
	 * This method for saving collections data.
	 * 
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	@Transactional
	public AppraisalItemsBean addAppraisalItems(AppraisalItemsModel appraisalItemsModel) throws Exception {

		LOGGER.info("AppraisalServiceImpl : Enter Method addAppraisalItems");

		AppraisalItemsBean itemsBean = new AppraisalItemsBean();

		if (appraisalItemsModel != null && appraisalItemsModel.getItemId() != null
				&& appraisalItemsModel.getItemId().length() > 0) {

			JSONArray appraisalItems = appraisalItemsModel.getItemId();
			List<AppraisalItemsBean> itemsList = new ArrayList<AppraisalItemsBean>();

			List<Integer> dbitemList = new ArrayList<Integer>();

			List<Integer> modelItemList = new ArrayList<Integer>();

			List<AppraisalItemsBean> appraisalItemsBeans = appraisalItemsDao
					.getAppraisalItemDataByAppraisalId(appraisalItemsModel.getAppraisalId());

			if (appraisalItemsBeans != null && appraisalItemsBeans.size() > 0) {
				for (AppraisalItemsBean appraisalItemsBean : appraisalItemsBeans) {
					dbitemList.add(appraisalItemsBean.getValouxItemBean().getItemId());
				}
			}
			Collections.sort(dbitemList);

			for (int i = 0; i < appraisalItems.length(); i++) {
				modelItemList.add((Integer) appraisalItems.get(i));
			}
			Collections.sort(modelItemList);

			List<Integer> deleteListItems = new ArrayList<Integer>(dbitemList);
			deleteListItems.removeAll(modelItemList);

			if (deleteListItems != null && deleteListItems.size() > 0) {
				appraisalItemsDao.deletedItemsFromAppraisal(deleteListItems, appraisalItemsModel.getAppraisalId());
			}

			for (int i = 0; i < appraisalItems.length(); i++) {

				int itemId = Integer.parseInt(appraisalItems.getString(i));
				boolean flag = false;

				List<AppraisalItemsBean> itemsBeans = appraisalItemsDao
						.getAppraisalItemsBeanByAppraisalAndItemId(appraisalItemsModel.getAppraisalId(), itemId);
				if (itemsBeans != null && itemsBeans.size() > 0) {
					flag = true;
				}
				if (!flag) {
					AppraisalItemsBean itemBean = new AppraisalItemsBean();
//					itemBean.setAppraisalId(appraisalItemsModel.getAppraisalId());
					AppraisalBean appraisalBean = new AppraisalBean();
					appraisalBean.setAppraisalId(appraisalItemsModel.getAppraisalId());
					itemBean.setAppraisalBean(appraisalBean);
					
//					itemBean.setItemId(itemId);
					ValouxItemBean valouxItemBean = new ValouxItemBean();
					valouxItemBean.setItemId(itemId);
					itemBean.setValouxItemBean(valouxItemBean);
					
					itemBean.setStatus(appraisalItemsModel.getStatus());
					itemBean.setCreatedBy(appraisalItemsModel.getCreatedBy());
					itemBean.setCreatedOn(CommonUtility.getDateAndTime());
					itemBean.setModifiedBy(appraisalItemsModel.getModifiedBy());
					itemBean.setModifiedOn(CommonUtility.getDateAndTime());
					itemsList.add(itemBean);
				}
			}
			appraisalItemsDao.addItemsInAppraisals(itemsList);
		} else if (appraisalItemsModel != null && appraisalItemsModel.getAppraisalId() != null) {

			List<AppraisalItemsBean> beanList = appraisalItemsDao
					.getAppraisalItemDataByAppraisalId(appraisalItemsModel.getAppraisalId());

			if (beanList != null && beanList.size() > 0) {
				appraisalItemsDao.deletedAllItemsFromAppraisals(beanList);
			}
		}
		LOGGER.info("AppraisalServiceImpl : Exit Method addAppraisalItems");
		return itemsBean;
	}

	/**
	 * This method add item in appraisals
	 * 
	 * @throws Exception
	 */
	@Transactional
	public AppraisalItemsBean addItemInAppraisals(AppraisalItemsModel appraisalItemsModel) throws Exception {
		LOGGER.debug("Enter Method addItemInAppraisals of AppraisalServiceImpl");

		AppraisalItemsBean appItemsBean = null;

		if (appraisalItemsModel.getAppraisalIdArray() != null) {
			JSONArray appraisalItemsInJsonArray = appraisalItemsModel.getAppraisalIdArray();
			List<AppraisalItemsBean> appraisalItemsList = new ArrayList<AppraisalItemsBean>();

			for (int i = 0; i < appraisalItemsInJsonArray.length(); i++) {
				int appraisalId = Integer.parseInt(appraisalItemsInJsonArray.getString(i));
				
				AppraisalBean valouxAppraisalBean = appraisalDao.getAppraisalDetails(appraisalId);
				
				if(valouxAppraisalBean != null && !(valouxAppraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED))){
					appItemsBean = new AppraisalItemsBean();

					appItemsBean.setCreatedBy(appraisalItemsModel.getCreatedBy());
					appItemsBean.setCreatedOn(CommonUtility.getDateAndTime());
//					appItemsBean.setAppraisalId(appraisalId);
					AppraisalBean appraisalBean = new AppraisalBean();
					appraisalBean.setAppraisalId(appraisalId);
					appItemsBean.setAppraisalBean(appraisalBean);
					
//					appItemsBean.setItemId(appraisalItemsModel.getSingleItemId());
					ValouxItemBean valouxItemBean = new ValouxItemBean();
					valouxItemBean.setItemId(appraisalItemsModel.getSingleItemId());
					appItemsBean.setValouxItemBean(valouxItemBean);
					
					appItemsBean.setStatus(appraisalItemsModel.getStatus());
					appItemsBean.setModifiedBy(appraisalItemsModel.getModifiedBy());
					appItemsBean.setModifiedOn(CommonUtility.getDateAndTime());
					appraisalItemsList.add(appItemsBean);
				}
			}
			appItemsBean = appraisalItemsDao.saveAppraisalItemList(appraisalItemsList, appraisalItemsModel.getRequestType());
		}
		LOGGER.debug("Exit Method addItemInAppraisals of AppraisalServiceImpl");
		return appItemsBean;
	}

	/**
	 * This method creates Appraisal for Collection.
	 * 
	 * @throws Exception
	 */
	// @Transactional
	// public AppraisalCollectionBean
	// addAppraisalCollection(AppraisalCollectionModel appraisalCollectionModel)
	// throws Exception {
	// LOGGER.debug("Enter Method createUser of addAppraisalCollection");
	// AppraisalCollectionBean appCollectionBean = null;
	// if (appraisalCollectionModel != null) {
	//
	// JSONArray collectionOfCollections =
	// appraisalCollectionModel.getCollectionId();
	// //List<AppraisalItemsBean> itemsList = new
	// ArrayList<AppraisalItemsBean>();
	//
	// List<Integer> dbcollectionList = new ArrayList<Integer>();
	//
	// List<Integer> modelCollectionList = new ArrayList<Integer>();
	//
	// List<AppraisalCollectionBean> collectionBeans =
	// appraisalDao.getAppraisalCollectionDataByAppraisalId(appraisalCollectionModel.getAppraisalId());
	//
	// if (collectionBeans != null && collectionBeans.size() > 0) {
	// for (AppraisalCollectionBean valouxCollectionBean : collectionBeans) {
	// dbcollectionList.add(valouxCollectionBean.getCollectionId());
	// }
	// }
	// Collections.sort(dbcollectionList);
	//
	// for (int i = 0; i < collectionOfCollections.length(); i++) {
	// modelCollectionList.add((Integer) collectionOfCollections.get(i));
	// }
	// Collections.sort(modelCollectionList);
	//
	// List<Integer> deleteCollection = new
	// ArrayList<Integer>(dbcollectionList);
	// deleteCollection.removeAll(modelCollectionList);
	//
	// if (deleteCollection != null && deleteCollection.size() > 0) {
	// appraisalDao.deletedCollectionFromAppraisal(deleteCollection,
	// appraisalCollectionModel.getAppraisalId());
	// }
	// if (appraisalCollectionModel.getCollectionId() != null) {
	// JSONArray appraisalItemsInJsonArray =
	// appraisalCollectionModel.getCollectionId();
	// List<AppraisalCollectionBean> appraisalCollectionList = new
	// ArrayList<AppraisalCollectionBean>();
	//
	// for (int i = 0; i < appraisalItemsInJsonArray.length(); i++) {
	// int appraisalCollection =
	// Integer.parseInt(appraisalItemsInJsonArray.getString(i));
	// appCollectionBean = new AppraisalCollectionBean();
	// if (appraisalCollectionModel.getId() != null) {
	// appCollectionBean.setId(appraisalCollectionModel.getId());
	// }
	// appCollectionBean.setAppraisalId(appraisalCollectionModel.getAppraisalId());
	// appCollectionBean.setCollectionId(appraisalCollection);
	// appCollectionBean.setStatus(appraisalCollectionModel.getStatus());
	// appCollectionBean.setCreatedBy(appraisalCollectionModel.getCreatedBy());
	// appCollectionBean.setCreatedOn(CommonUtility.getDateAndTime());
	// appraisalCollectionList.add(appCollectionBean);
	//
	// }
	// appCollectionBean =
	// appraisalDao.saveAppraisalCollectionList(appraisalCollectionList,
	// appraisalCollectionModel.getRequestType());
	// }
	// }
	// return appCollectionBean;
	// }

	/**
	 * This method add all appraisals in collection
	 */
	@Transactional
	public AppraisalCollectionBean addAppraisalCollection(AppraisalCollectionModel appraisalCollectionModel)
			throws Exception {

		LOGGER.info("AppraisalServiceImpl : Enter Method addAppraisalCollection");

		AppraisalCollectionBean collectionBean = new AppraisalCollectionBean();

		if (appraisalCollectionModel != null && appraisalCollectionModel.getCollectionId() != null
				&& appraisalCollectionModel.getCollectionId().length() > 0) {

			JSONArray collectionAppraisals = appraisalCollectionModel.getCollectionId();
			List<AppraisalCollectionBean> appraisalsList = new ArrayList<AppraisalCollectionBean>();

			List<Integer> dbList = new ArrayList<Integer>();

			List<Integer> modelList = new ArrayList<Integer>();

			List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao
					.getAppraisalCollectionDataByAppraisalId(appraisalCollectionModel.getAppraisalId());

			if (collectionBeans != null && collectionBeans.size() > 0) {
				for (AppraisalCollectionBean appraisalCollectionBean : collectionBeans) {
					dbList.add(appraisalCollectionBean.getValouxCollectionBean().getVcid());
				}
			}
			Collections.sort(dbList);

			for (int i = 0; i < collectionAppraisals.length(); i++) {
				modelList.add((Integer) collectionAppraisals.get(i));
			}
			Collections.sort(modelList);

			List<Integer> deleteListAppraisals = new ArrayList<Integer>(dbList);
			deleteListAppraisals.removeAll(modelList);

			if (deleteListAppraisals != null && deleteListAppraisals.size() > 0) {
				appraisalCollectionDao.deletedCollectionFromAppraisal(deleteListAppraisals,
						appraisalCollectionModel.getAppraisalId());
			}

			for (int i = 0; i < collectionAppraisals.length(); i++) {

				int collectionId = Integer.parseInt(collectionAppraisals.getString(i));
				boolean flag = false;

				List<AppraisalCollectionBean> collectionBeanList = appraisalCollectionDao
						.getCollectionAppraisalByCollectionIdAndAppraisalId(collectionId,
								appraisalCollectionModel.getAppraisalId());
				if (collectionBeanList != null && collectionBeanList.size() > 0) {
					flag = true;
				}
				if (!flag) {
					AppraisalCollectionBean appraisalBean = new AppraisalCollectionBean();
//					appraisalBean.setCollectionId(collectionId);
					ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
					valouxCollectionBean.setVcid(collectionId);
					appraisalBean.setValouxCollectionBean(valouxCollectionBean);
					
//					appraisalBean.setAppraisalId(appraisalCollectionModel.getAppraisalId());
					AppraisalBean appraisal = new AppraisalBean();
					appraisal.setAppraisalId(appraisalCollectionModel.getAppraisalId());
					appraisalBean.setAppraisalBean(appraisal);
					
					appraisalBean.setStatus(appraisalCollectionModel.getStatus());
					appraisalBean.setCreatedBy(appraisalCollectionModel.getCreatedBy());
					appraisalBean.setCreatedOn(CommonUtility.getDateAndTime());
					appraisalBean.setModifiedBy(appraisalCollectionModel.getModifiedBy());
					appraisalBean.setModifiedOn(CommonUtility.getDateAndTime());
					appraisalsList.add(appraisalBean);
				}
			}
			appraisalCollectionDao.addAppraisalsInCollection(appraisalsList);

		} else if (appraisalCollectionModel != null && appraisalCollectionModel.getAppraisalId() != null) {

			List<AppraisalCollectionBean> beanList = appraisalCollectionDao
					.getAppraisalBeansByAppraisalId(appraisalCollectionModel.getAppraisalId());

			if (beanList != null && beanList.size() > 0) {
				appraisalCollectionDao.deletedAllAppraisalsFromCollection(beanList);
			}
		}

		LOGGER.info("AppraisalServiceImpl : Exit Method addAppraisalCollection");
		return collectionBean;
	}

	/**
	 * This method check Existing Appraisal for Items.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public AppraisalItemsBean checkExistingAppraisalItems(AppraisalItemsModel appraisalItemsModel) throws Exception {
		LOGGER.debug("Enter Method checkExistingAppraisalItems of AppraisalServiceImpl");
		AppraisalItemsBean appItemsBean = null;
		if (appraisalItemsModel.getItemId() != null) {
			JSONArray appraisalItemsInJsonArray = appraisalItemsModel.getItemId();
			List<AppraisalItemsBean> appraisalItemsList = new ArrayList<AppraisalItemsBean>();

			for (int i = 0; i < appraisalItemsInJsonArray.length(); i++) {
				int appraisalItems = Integer.parseInt(appraisalItemsInJsonArray.getString(i));
				appItemsBean = new AppraisalItemsBean();
				if (appraisalItemsModel.getId() != null) {
					appItemsBean.setId(appraisalItemsModel.getId());
				}
//				appItemsBean.setAppraisalId(appraisalItemsModel.getAppraisalId());
				AppraisalBean appraisalBean = new AppraisalBean();
				appraisalBean.setAppraisalId(appraisalItemsModel.getAppraisalId());
				appItemsBean.setAppraisalBean(appraisalBean);
				
//				appItemsBean.setItemId(appraisalItems);
				ValouxItemBean valouxItemBean = new ValouxItemBean();
				valouxItemBean.setItemId(appraisalItems);
				appItemsBean.setValouxItemBean(valouxItemBean);
				
				appItemsBean.setStatus(appraisalItemsModel.getStatus());
				appItemsBean.setCreatedBy(appraisalItemsModel.getCreatedBy());
				appItemsBean.setCreatedOn(CommonUtility.getDateAndTime());
				appraisalItemsList.add(appItemsBean);

			}
			appItemsBean = appraisalItemsDao.checkExistingAppraisalItemList(appraisalItemsList,
					appraisalItemsModel.getRequestType());
		}
		LOGGER.debug("Exit Method checkExistingAppraisalItems of AppraisalServiceImpl");
		return appItemsBean;
	}

	/**
	 * This method check Existing Appraisal for Collection.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public AppraisalCollectionBean checkExistingAppraisalCollection(AppraisalCollectionModel appraisalCollectionModel)
			throws Exception {
		LOGGER.debug("Enter Method createUser of checkExistingAppraisalCollection of AppraisalServiceImpl");
		AppraisalCollectionBean appCollectionBean = null;
		if (appraisalCollectionModel.getCollectionId() != null) {
			JSONArray appraisalCollectionInJsonArray = appraisalCollectionModel.getCollectionId();
			List<AppraisalCollectionBean> appraisalCollectionList = new ArrayList<AppraisalCollectionBean>();

			for (int i = 0; i < appraisalCollectionInJsonArray.length(); i++) {
				int appraisalCollection = Integer.parseInt(appraisalCollectionInJsonArray.getString(i));
				appCollectionBean = new AppraisalCollectionBean();
				if (appraisalCollectionModel.getId() != null) {
					appCollectionBean.setId(appraisalCollectionModel.getId());
				}
//				appCollectionBean.setAppraisalId(appraisalCollectionModel.getAppraisalId());
				AppraisalBean appraisal = new AppraisalBean();
				appraisal.setAppraisalId(appraisalCollectionModel.getAppraisalId());
				appCollectionBean.setAppraisalBean(appraisal);
				
//				appCollectionBean.setCollectionId(appraisalCollection);
				ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
				valouxCollectionBean.setVcid(appraisalCollection);
				appCollectionBean.setValouxCollectionBean(valouxCollectionBean);
				
				appCollectionBean.setStatus(appraisalCollectionModel.getStatus());
				appCollectionBean.setCreatedBy(appraisalCollectionModel.getCreatedBy());
				appCollectionBean.setCreatedOn(CommonUtility.getDateAndTime());
				appraisalCollectionList.add(appCollectionBean);

			}
			appCollectionBean = appraisalCollectionDao.checkExistingAppraisalCollectionList(appraisalCollectionList,
					appraisalCollectionModel.getRequestType());
		}
		LOGGER.debug("Exit Method createUser of checkExistingAppraisalCollection of AppraisalServiceImpl");
		return appCollectionBean;
	}

	/**
	 * This method get the json array of all appraisal id associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public JSONArray getAppraisalIdAssociatedWithItem(Integer itemId) throws Exception {
		LOGGER.debug("Enter Method getAppraisalIdAssociatedWithItem of AppraisalServiceImpl");
		JSONArray jArray = new JSONArray();
		List<AppraisalItemsBean> appraisalitemList = appraisalItemsDao.getApraisalItemListByItemId(itemId);
		if (appraisalitemList != null && appraisalitemList.size() > 0) {
			for (AppraisalItemsBean appraisalItemBean : appraisalitemList) {
				jArray.put(appraisalItemBean.getAppraisalBean().getAppraisalId());
			}
		}
		LOGGER.debug("Exit Method getAppraisalIdAssociatedWithItem of AppraisalServiceImpl");
		return jArray;
	}

	/**
	 * This method get the appraisal detail associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AppraisalBean> getAppraisalDetailAssociatedWithItem(Integer itemId) throws Exception {
		LOGGER.debug("Enter Method getAppraisalDetailAssociatedWithItem of AppraisalServiceImpl");
		List<AppraisalBean> appraisalBeanList = new ArrayList<AppraisalBean>();
		List<AppraisalItemsBean> appraisalitemList = appraisalItemsDao.getApraisalItemListByItemId(itemId);
		if (appraisalitemList != null && appraisalitemList.size() > 0) {
			for (AppraisalItemsBean appraisalItemBean : appraisalitemList) {
				AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalItemBean.getAppraisalBean().getAppraisalId());
				appraisalBeanList.add(appraisalBean);
			}
		}
		LOGGER.debug("Exit Method getAppraisalDetailAssociatedWithItem of AppraisalServiceImpl");
		return appraisalBeanList;
	}

	/**
	 * This method get all appraisal detail which is associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AppraisalBean> getAppraisalDetailNotAssociatedWithItem(String rKey, Integer itemId) throws Exception {
		LOGGER.debug("Enter Method getAppraisalDetailNotAssociatedWithItem of AppraisalServiceImpl");
		List<AppraisalBean> appraisalBeanList = new ArrayList<AppraisalBean>();
		List<Integer> appraisalIdList = new ArrayList<Integer>();
		List<AppraisalItemsBean> appraisalitemList = appraisalItemsDao.getApraisalItemListByItemId(itemId);
		if (appraisalitemList != null && appraisalitemList.size() > 0) {
			for (AppraisalItemsBean appraisalItemBean : appraisalitemList) {
				appraisalIdList.add(appraisalItemBean.getAppraisalBean().getAppraisalId());
			}
		}
		appraisalBeanList = appraisalDao.getAppraisalListNotAssociatedWithItem(rKey, appraisalIdList.toArray());
		LOGGER.debug("Exit Method getAppraisalDetailNotAssociatedWithItem of AppraisalServiceImpl");
		return appraisalBeanList;
	}

	/**
	 * This method performs check Appraisal if already exist. This method
	 * implements logic to check if an user with same Appraisal already exist.
	 * It calls data access layer to check rKey and name in persisting data.
	 */
	@Transactional
	public boolean checkAppraisalAlreadyExist(String rKey, String name) throws Exception {
		LOGGER.debug("Enter Method checkAppraisalAlreadyExist of AppraisalServiceImpl");
		boolean isAppraisalExist = false;
		isAppraisalExist = appraisalDao.checkAppraisalAlreadyExist(rKey, name);
		LOGGER.debug("Exit Method checkAppraisalAlreadyExist of AppraisalServiceImpl");
		return isAppraisalExist;
	}

	/**
	 * This method get the list of all appraisals
	 */
	@Transactional
	public List<AppraisalBean> getAllAppraisalListForSupoerAdmin() throws Exception {
		LOGGER.debug("Enter Method getAllAppraisalListForSupoerAdmin of AppraisalServiceImpl");
		return appraisalDao.getAllAppraisalListForSupoerAdmin();
	}

	/**
	 * This method get number of collection associated with appraisal
	 */
	@Transactional
	public Integer getNumberOfCollectionOfAppraisal(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter Method getNumberOfCollectionOfAppraisal of AppraisalServiceImpl");
		List<AppraisalCollectionBean> appraisalCollectionBean = appraisalCollectionDao
				.getCollectionAssociatedWithAppraisal(appraisalId);
		if (appraisalCollectionBean != null && appraisalCollectionBean.size() > 0) {
			return appraisalCollectionBean.size();
		}
		LOGGER.debug("Exit Method getNumberOfCollectionOfAppraisal of AppraisalServiceImpl");
		return null;
	}

	/**
	 * This method get number of Items associated with appraisal
	 */
	@Transactional
	public Integer getNumberOfItemsOfAppraisal(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter Method getNumberOfItemsOfAppraisal of AppraisalServiceImpl");
		List<AppraisalItemsBean> appraisalItemsBean = appraisalItemsDao.getItemAssociatedWithAppraisal(appraisalId);
		if (appraisalItemsBean != null && appraisalItemsBean.size() > 0) {
			return appraisalItemsBean.size();
		}
		LOGGER.debug("Exit Method getNumberOfItemsOfAppraisal of AppraisalServiceImpl");
		return null;
	}

	/**
	 * This method performs check Appraisal if already approved. This method
	 * implements logic to check if an user with same Appraisal already
	 * approved. It calls data access layer to check AppraisalModel in
	 * persisting data.
	 */
	@Transactional
	public boolean checkAppraisalAlreadyApproved(Integer appraisalId, Integer status) throws Exception {
		LOGGER.debug("Enter Method checkAppraisalAlreadyApproved of AppraisalServiceImpl");
		boolean isAppraisalApproved = false;
		isAppraisalApproved = appraisalDao.checkAppraisalAlreadyApproved(appraisalId, status);
		LOGGER.debug("Exit Method checkAppraisalAlreadyApproved of AppraisalServiceImpl");
		return isAppraisalApproved;
	}

	/**
	 * This method approved or disapproved Appraisal.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public AppraisalBean approvedOrDisapprovedAppraisal(AppraisalModel appraisalModel) throws Exception {
		LOGGER.debug("Enter Method approvedOrDisapprovedAppraisal of AppraisalServiceImpl");
		AppraisalBean appraisalBean = null;
		appraisalBean = appraisalDao.getAppraisalDetails(appraisalModel.getAppraisalId());
		if (appraisalBean != null) {
			appraisalBean = CommonUserUtility.prepareAppraisalApprovedDisapprovedBeanFromModel(appraisalModel,
					appraisalBean);
			appraisalBean = appraisalDao.approvedOrDisapprovedAppraisal(appraisalBean);
		}
		LOGGER.debug("Exit Method approvedOrDisapprovedAppraisal of AppraisalServiceImpl");
		return appraisalBean;
	}

	/**
	 * This method is used for fetching Appraisal data.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public List<AppraisalModel> getUserAppraisalList(String relationKey,Integer aStatus) throws Exception {

		LOGGER.info("AppraisalServiceImpl : Enter Method getUserAppraisalList");

		List<AppraisalModel> appraisalModels = new ArrayList<AppraisalModel>();
		List<AppraisalBean> appraisalBeans = appraisalDao.getAppraisalDetailsByUserId(relationKey,aStatus,null);

		if (appraisalBeans != null && appraisalBeans.size() > 0) {
			for (AppraisalBean valouxAppraisalBean : appraisalBeans) {
				AppraisalModel appraisalModel = new AppraisalModel();
				appraisalModel.setAppraisalId(valouxAppraisalBean.getAppraisalId());
				appraisalModel.setName(valouxAppraisalBean.getName());
				appraisalModel.setShortDescription(valouxAppraisalBean.getShortDescription());
				appraisalModel.setaStatus(valouxAppraisalBean.getaStatus());
				appraisalModel.setApprovedBy(valouxAppraisalBean.getApprovedBy());
				appraisalModel.setApprovedOn(valouxAppraisalBean.getApprovedOn());
				appraisalModel.setCreatedBy(valouxAppraisalBean.getCreatedBy());
				appraisalModel.setCreatedOn(valouxAppraisalBean.getCreatedOn());
				appraisalModel.setModifiedBy(valouxAppraisalBean.getModifiedBy());
				appraisalModel.setModifiedOn(valouxAppraisalBean.getModifiedOn());
				appraisalModel.setRelationKey(valouxAppraisalBean.getRelationKey());
				appraisalModel.setLastAppraisaedPrice(valouxAppraisalBean.getLastAppraisaedPrice());
				appraisalModels.add(appraisalModel);
			}
		}

		LOGGER.info("AppraisalServiceImpl : Exit Method getUserAppraisalList");
		return appraisalModels;
	}

	/**
	 * This method returns the number of item associated with collection
	 */
	@Transactional
	public Integer getItemAssociatedWithCollection(Integer vcid) throws Exception {
		LOGGER.debug("Enter method getItemAssociatedWithCollection of AppraisalServiceImpl");
		Integer count = 0;
		List<ValouxCollectionItemBean> itemBeanList = valouxCollectionItemDao.getItemAssociatedWithCollection(vcid);
		if (itemBeanList != null && itemBeanList.size() > 0) {
			count = itemBeanList.size();
		}
		LOGGER.debug("Exit method getItemAssociatedWithCollection of AppraisalServiceImpl");
		return count;
	}

	/**
	 * This method returns the number of collection associated with appraisal
	 */
	@Transactional
	public Integer getNumberOfCollectionAssociatedWithAppraisal(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter method getNumberOfCollectionAssociatedWithAppraisal of AppraisalServiceImpl");
		Integer count = 0;
		List<AppraisalCollectionBean> appraisalCollectionBeanList = appraisalCollectionDao
				.getAppraisalCollectionDataByAppraisalId(appraisalId);
		if (appraisalCollectionBeanList != null && appraisalCollectionBeanList.size() > 0) {
			count = appraisalCollectionBeanList.size();
		}
		LOGGER.debug("Exit method getNumberOfCollectionAssociatedWithAppraisal of AppraisalServiceImpl");
		return count;
	}

	/**
	 * This method returns the number of item associated with appraisal
	 */
	@Transactional
	public Integer getNumberOfItemAssociatedWithAppraisal(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter method getNumberOfItemAssociatedWithAppraisal of AppraisalServiceImpl");
		Integer count = 0;
		List<AppraisalItemsBean> appraisalitemBeanList = appraisalItemsDao.getAppraisalItemDataByAppraisalId(appraisalId);
		if (appraisalitemBeanList != null && appraisalitemBeanList.size() > 0) {
			count = appraisalitemBeanList.size();
		}
		LOGGER.debug("Exit method getNumberOfItemAssociatedWithAppraisal of AppraisalServiceImpl");
		return count;
	}

	/**
	 * This method give list of appraisals of collection
	 */
	@Transactional
	public List<AppraisalCollectionModel> getAppraisalCollectionBeansByCollectionId(Integer collectionId)
			throws Exception {
		LOGGER.debug("Enter method getAppraisalCollectionBeansByCollectionId of AppraisalServiceImpl");
		List<AppraisalCollectionBean> appraisalCollectionBeans = appraisalCollectionDao
				.getAppraisalCollectionBeansByCollectionId(collectionId);

		if (appraisalCollectionBeans != null && appraisalCollectionBeans.size() > 0) {
			List<AppraisalCollectionModel> appraisalCollectionModelList = PrepareModels.prepareAppraisalCollectionModelListFromBean(appraisalCollectionBeans);
			return appraisalCollectionModelList;
			
		}
		LOGGER.debug("Exit method getAppraisalCollectionBeansByCollectionId of AppraisalServiceImpl");
		return null;
	}

	/**
	 * This method give appraisal details
	 */
	@Transactional
	public AppraisalBean getAppraisalBeanById(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter method getAppraisalBeanById of AppraisalServiceImpl");
		AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalId);
		LOGGER.debug("Exit method getAppraisalBeanById of AppraisalServiceImpl");
		return appraisalBean;
	}

	/**
	 * This method give list of appraisals
	 */
	@Transactional
	public List<AppraisalCollectionModel> getAppraisalBeansByAppraisalId(Integer appraisalId) throws Exception {
		LOGGER.debug("Enter method getAppraisalBeansByAppraisalId of AppraisalServiceImpl");
		List<AppraisalCollectionBean> appraisalBeans = appraisalCollectionDao.getAppraisalBeansByAppraisalId(appraisalId);

		if (appraisalBeans != null && appraisalBeans.size() > 0) {
			List<AppraisalCollectionModel> appraisalCollectionModelList = PrepareModels.prepareAppraisalCollectionModelListFromBean(appraisalBeans);
			return appraisalCollectionModelList;
		}
		LOGGER.debug("Exit method getAppraisalBeansByAppraisalId of AppraisalServiceImpl");
		return null;
	}

	/**
	 * This method give collection details
	 */
	@Transactional
	public ValouxCollectionModel getCollectionBeanByCollectionId(Integer collectionId) throws Exception {
		LOGGER.debug("Enter method getCollectionBeanByCollectionId of AppraisalServiceImpl");
		ValouxCollectionModel collectionModel = new ValouxCollectionModel();
		ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionBeanByCollectionId(collectionId);
		if(collectionBean!=null){
			collectionModel.setVcid(collectionBean.getVcid());
			collectionModel.setCname(collectionBean.getCname());
			collectionModel.setShortDescription(collectionBean.getShortDescription());
			collectionModel.setCollectionStatus(collectionBean.getCollectionStatus());
			collectionModel.setCreatedBy(collectionBean.getCreatedBy());
			collectionModel.setCreatedOn(collectionBean.getCreatedOn());
			collectionModel.setModifiedBy(collectionBean.getModifiedBy());
			collectionModel.setModifiedOn(collectionBean.getModifiedOn());
			collectionModel.setRkey(collectionBean.getRkey());
			collectionModel.setLastAppraisedDate(collectionBean.getLastAppraisedDate());
			collectionModel.setLastAppraiserId(collectionBean.getLastAppraiserId());
		}
		LOGGER.debug("Exit method getCollectionBeanByCollectionId of AppraisalServiceImpl");
		return collectionModel;
	}

	/**
	 * This method give all collection List not in a appraisal.
	 */
	@Transactional
	public List<ValouxCollectionModel> getCollectionListNotInAppraisal(String publicKey, Integer appraisalId)
			throws Exception {
		LOGGER.debug("Enter method getCollectionListNotInAppraisal of AppraisalServiceImpl");
		List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao.getAppraisalBeansByAppraisalId(appraisalId);

		List<Integer> collectionList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (AppraisalCollectionBean collectionBean : collectionBeans) {
				collectionList.add(collectionBean.getValouxCollectionBean().getVcid());
			}
		}

		List<ValouxCollectionBean> collectionBeanList = valouxCollectionDao.getCollectionListNotInAppraisal(publicKey,
				collectionList.toArray());
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();
		if (collectionBeanList != null && collectionBeanList.size() > 0) {
			for (ValouxCollectionBean collectionBean : collectionBeanList) {
				ValouxCollectionModel collectionModel = CommonUserUtility
						.prepareCollectionModelFromCollectionBean(collectionBean);
				collectionModelList.add(collectionModel);
			}
		}
		LOGGER.debug("Exit method getCollectionListNotInAppraisal of AppraisalServiceImpl");
		return collectionModelList;
	}

	/**
	 * This method checks whether appraisal name already exist for user
	 */
	@Transactional
	public Boolean checkAppraisalNameExistForUser(String publicKey, String appraisalName, Integer appraisalId)
			throws Exception {
		LOGGER.info("Enter Method checkAppraisalNameExistForUser of AppraisalServiceImpl");
		List<AppraisalBean> appraisalBeanList = appraisalDao.checkAppraisalNameExistForUser(publicKey, appraisalName);
		if (appraisalBeanList != null && appraisalBeanList.size() > 0) {
			if (appraisalBeanList.get(0).getAppraisalId().equals(appraisalId)) {
				return false;
			}
			return true;
		}
		LOGGER.info("Exit Method checkAppraisalNameExistForUser of AppraisalServiceImpl");
		return false;

	}

	/**
	 * This method give list of appraisals
	 */
	@Transactional
	public List<AppraisalItemsModel> getAppraisalItemsBeansByAppraisalId(Integer appraisalId) throws Exception {
		LOGGER.info("Enter Method getAppraisalItemsBeansByAppraisalId of AppraisalServiceImpl");
		List<AppraisalItemsBean> appraisalBeans = appraisalItemsDao.getAppraisalItemDataByAppraisalId(appraisalId);

		if (appraisalBeans != null && appraisalBeans.size() > 0) {
			List<AppraisalItemsModel> appraisalItemsModelList = PrepareModels.prepareAppraisalItemsModelListFromBean(appraisalBeans);
			return appraisalItemsModelList;
		}
		LOGGER.info("Exit Method getAppraisalItemsBeansByAppraisalId of AppraisalServiceImpl");
		return null;
	}

	/**
	 * This method give items details
	 */
	@Transactional
	public ValouxItemModel getItemsBeanByItemId(Integer itemId) throws Exception {
		LOGGER.info("Enter Method getItemsBeanByItemId of AppraisalServiceImpl");
		ValouxItemBean itemsBean = valouxItemDao.getItemsBeanByItemId(itemId);
		ValouxItemModel itemsModel= null;
		if (itemsBean != null) {
			 itemsModel = PrepareModels.prepareitemsModelFromItemsBean(itemsBean);
		}
		LOGGER.info("Exit Method getItemsBeanByItemId of AppraisalServiceImpl");
		return itemsModel;
	}

	/**
	 * This method give all items List not in a appraisal.
	 */
	@Transactional
	public List<ValouxItemModel> getItemsListNotInAppraisal(String publicKey, Integer appraisalId) throws Exception {
		LOGGER.info("Enter Method getItemsListNotInAppraisal of AppraisalServiceImpl");
		List<AppraisalItemsBean> appraisalBeans = appraisalItemsDao.getAppraisalItemDataByAppraisalId(appraisalId);

		List<Integer> itemsList = new ArrayList<Integer>();

		if (appraisalBeans != null && appraisalBeans.size() > 0) {

			for (AppraisalItemsBean itemsBean : appraisalBeans) {
				itemsList.add(itemsBean.getValouxItemBean().getItemId());
			}
		}

		List<ValouxItemBean> itemsBeanList = valouxItemDao.getItemsListNotInAppraisal(publicKey, itemsList.toArray());
		List<ValouxItemModel> itemsModelList = new ArrayList<ValouxItemModel>();
		if (itemsBeanList != null && itemsBeanList.size() > 0) {
			for (ValouxItemBean itemsBean : itemsBeanList) {
				ValouxItemModel itemsModel = CommonUserUtility.prepareitemsModelFromItemsBean(itemsBean);
				itemsModelList.add(itemsModel);
			}
		}
		LOGGER.info("Exit Method getItemsListNotInAppraisal of AppraisalServiceImpl");
		return itemsModelList;
	}

	/**
	 * This method delete collection from appraisal.
	 */
	@Transactional
	public Boolean deleteCollectionFromAppraisal(JSONArray collectionId, Integer appraisalId) throws Exception {
		Boolean flag = false;
		if (collectionId != null) {
			LOGGER.info("AppraisalServiceImpl : Enter Method deleteItemFromCollection");
			List<AppraisalCollectionBean> appraisalCollectionBeans = appraisalCollectionDao
					.getAppraisalByAppraisalIdAndCollectionId(collectionId, appraisalId);
			if (appraisalCollectionBeans != null) {
				appraisalCollectionDao.deleteCollectionList(appraisalCollectionBeans);
				flag = true;
			}
			LOGGER.info("AppraisalServiceImpl : Exit Method deleteItemFromCollection");
		}
		return flag;
	}

	/**
	 * This method delete item from appraisal.
	 */
	@Transactional
	public Boolean deleteItemFromAppraisal(JSONArray itemId, Integer appraisalId) throws Exception {
		Boolean flag = false;
		if (itemId != null) {
			LOGGER.info("AppraisalServiceImpl : Enter Method deleteItemFromAppraisal");
			List<AppraisalItemsBean> appraisalItemBeans = appraisalItemsDao.getAppraisalByAppraisalIdAndItemId(itemId,
					appraisalId);
			if (appraisalItemBeans != null) {
				appraisalItemsDao.deleteItemList(appraisalItemBeans);
				flag = true;
			}
			LOGGER.info("AppraisalServiceImpl : Exit Method deleteItemFromAppraisal");
		}
		return flag;
	}

	/**
	 * This method delete item from appraisal.
	 */
	@Transactional
	public Boolean deleteSingleItemFromAppraisals(JSONArray appraisalId, Integer itemId) throws Exception {
		Boolean flag = false;
		if (appraisalId != null) {
			LOGGER.info("AppraisalServiceImpl : Enter Method deleteSingleItemFromAppraisals");
			List<AppraisalItemsBean> appraisalItemBeans = appraisalItemsDao.getAppraisalByAppraisalIdAndItemId(itemId,
					appraisalId);
			List<AppraisalItemsBean> appraisalItemBeansToDelete = new ArrayList<AppraisalItemsBean>();
			if (appraisalItemBeans != null && appraisalItemBeans.size() > 0) {
				for(AppraisalItemsBean appraisalItemBean : appraisalItemBeans){
					AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalItemBean.getAppraisalBean().getAppraisalId());
					if(appraisalBean!=null && !(appraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED))){
						appraisalItemBeansToDelete.add(appraisalItemBean);
					}
				}
				appraisalItemsDao.deleteItemList(appraisalItemBeansToDelete);
				flag = true;
			}
			LOGGER.info("AppraisalServiceImpl : Exit Method deleteSingleItemFromAppraisals");
		}
		return flag;
	}

	/**
	 * This method used to add access permission
	 */
	@Transactional
	public void addValouxAccessPermission(ValouxAccessPermissionModel accessPermissionModel) throws Exception {

		LOGGER.info("AppraisalServiceImpl : Enter Method addValouxAccessPermission");

		ValouxAccessPermissionBean accessPermissionBean = new ValouxAccessPermissionBean();

		if (accessPermissionModel != null) {
			accessPermissionBean.setCreatedBy(accessPermissionModel.getCreatedBy());
			accessPermissionBean.setCreatedOn(accessPermissionModel.getCreatedOn());
			accessPermissionBean.setGivenPermissionBy(accessPermissionModel.getGivenPermissionBy());
			accessPermissionBean.setGivenPermissionTo(accessPermissionModel.getGivenPermissionTo());
			accessPermissionBean.setModifiedBy(accessPermissionModel.getModifiedBy());
			accessPermissionBean.setModifiedOn(accessPermissionModel.getModifiedOn());
			accessPermissionBean.setIsAddedAppraisal(CommonConstants.STATUS_ACTIVE.byteValue());
			accessPermissionBean.setIsAddedCollection(CommonConstants.STATUS_ACTIVE.byteValue());
			accessPermissionBean.setIsAddedItem(CommonConstants.STATUS_ACTIVE.byteValue());

			valouxAccessPermissionDao.addValouxAccessPermission(accessPermissionBean);

			LOGGER.info("AppraisalServiceImpl : Enter Method addValouxAccessPermission");
		}
	}

	/**
	 * This method will return list of appraisal
	 */
	@Transactional
	public List<AppraisalBean> getAppraisalListAssociatedWithUserAndHaveKeyword(String rKey, String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("AppraisalServiceImpl : Enter Method getAppraisalListAssociatedWithUserAndHaveKeyword");
		List<AppraisalBean> appraisalBeanList = appraisalDao.getAppraisalListForGlobalSearchByName(rKey, keyword, null, startRecordNo, numberOfRecords, sortBy, sortOrder);
		LOGGER.debug("AppraisalServiceImpl : Exit Method getAppraisalListAssociatedWithUserAndHaveKeyword");
		return appraisalBeanList;
	}

	/**
	 * This method will return list of appraisal
	 */
	@Transactional
	public List<AppraisalBean> getAppraisalListAssociatedWithAgentAndHaveKeyword(String rKey, String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("AppraisalServiceImpl : Enter Method getAppraisalListAssociatedWithAgentAndHaveKeyword");
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestItemListSharedToUser(rKey, 3, 2,null,null,null,null);
		List<Integer> itemList = new ArrayList<Integer>();
		List<AppraisalBean> appraisalBeanList = new ArrayList<AppraisalBean>();
		if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
			for (ValouxSharedRequestBean sharedReuestBean : sharedRequestBeanList) {
				itemList.add(sharedReuestBean.getSharedItemId());
			}
		}
		if(itemList!=null && itemList.size()>0){
		appraisalBeanList = appraisalDao.getAppraisalListForGlobalSearchByName(null, keyword,
				itemList.toArray(), startRecordNo, numberOfRecords, sortBy, sortOrder);
		}
		LOGGER.debug("AppraisalServiceImpl : Exit Method getAppraisalListAssociatedWithAgentAndHaveKeyword");
		return appraisalBeanList;
	}
	
	/**
	 * This method will return list of appraisal
	 */
	@Transactional
	public List<AppraisalModel> getAppraisalListAssociatedWithAgentWithStatus(String rKey, Integer aStatus)
			throws Exception {
		LOGGER.debug("AppraisalServiceImpl : Enter Method getAppraisalListAssociatedWithAgentWithStatus");
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestItemListSharedToUser(rKey, 3, 2,null,null,null,null);
		List<Integer> appraisalIdsList = new ArrayList<Integer>();
		List<AppraisalModel> appraisalModels = new ArrayList<AppraisalModel>();
		
		if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
			for (ValouxSharedRequestBean sharedReuestBean : sharedRequestBeanList) {
				appraisalIdsList.add(sharedReuestBean.getSharedItemId());
			}
		}
		if(appraisalIdsList!=null && appraisalIdsList.size()>0){
			List<AppraisalBean> appraisalBeans = appraisalDao.getAppraisalDetailsByUserId(null,aStatus,appraisalIdsList.toArray());

			if (appraisalBeans != null && appraisalBeans.size() > 0) {
				for (AppraisalBean valouxAppraisalBean : appraisalBeans) {
					AppraisalModel appraisalModel = new AppraisalModel();
					appraisalModel.setAppraisalId(valouxAppraisalBean.getAppraisalId());
					appraisalModel.setName(valouxAppraisalBean.getName());
					appraisalModel.setShortDescription(valouxAppraisalBean.getShortDescription());
					appraisalModel.setaStatus(valouxAppraisalBean.getaStatus());
					appraisalModel.setApprovedBy(valouxAppraisalBean.getApprovedBy());
					appraisalModel.setApprovedOn(valouxAppraisalBean.getApprovedOn());
					appraisalModel.setCreatedBy(valouxAppraisalBean.getCreatedBy());
					appraisalModel.setCreatedOn(valouxAppraisalBean.getCreatedOn());
					appraisalModel.setModifiedBy(valouxAppraisalBean.getModifiedBy());
					appraisalModel.setModifiedOn(valouxAppraisalBean.getModifiedOn());
					appraisalModel.setRelationKey(valouxAppraisalBean.getRelationKey());
					appraisalModel.setLastAppraisaedPrice(valouxAppraisalBean.getLastAppraisaedPrice());
					appraisalModels.add(appraisalModel);
				}
			}
		}
		LOGGER.debug("AppraisalServiceImpl : Exit Method getAppraisalListAssociatedWithAgentWithStatus");
		return appraisalModels;
	}

	/**
	 * This method will give list of agent having access permission of consumers
	 */
	@Transactional
	public List<ValouxAccessPermissionBean> getAgentAccessPermissionUsers(String agentKey) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method getAgentAccessPermissionUsers");

		List<ValouxAccessPermissionBean> accessPermissionBeans = valouxAccessPermissionDao.getAgentAccessPermissionUsers(agentKey);
		
		LOGGER.debug("AppraisalServiceImpl : Exit method getAgentAccessPermissionUsers");
		return accessPermissionBeans;
	}

	/**
	 * This method returns the number of item associated with collection
	 */
	@Transactional
	public List<ValouxCollectionItemModel> getItemAssociatedWithCollectionList(Integer vcid) throws Exception {
		LOGGER.debug("Enter method getItemAssociatedWithCollection of getItemAssociatedWithCollectionList");
		// Integer count = 0;
		List<ValouxCollectionItemBean> itemBeanList = valouxCollectionItemDao.getItemAssociatedWithCollection(vcid);
		/*
		 * if (itemBeanList != null && itemBeanList.size() > 0) { count =
		 * itemBeanList.size(); }
		 */
		List<ValouxCollectionItemModel> valouxCollectionItemModelList = PrepareModels.prepareValouxCollectionItemModelListFromBean(itemBeanList);
		LOGGER.debug("Exit method getItemAssociatedWithCollection of getItemAssociatedWithCollectionList");
		return valouxCollectionItemModelList;
	}

	/**
	 * This method change appraisal status
	 */
	@Transactional
	public AppraisalModel changeStatusToAppraised(AppraisalModel appraisalModel)
			throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method changeStatusToAppraised");
		
		Double appraisalAppraisedPrice = 0.0;
		Double itemAppraisedPrice = 0.0;
		Double collectionAppraisedPrice = 0.0;
		Double collectionAppraisedPriceDistinctItems = 0.0;
		Integer collectionItemId = 0;
		
		if(appraisalModel != null){
			AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalModel.getAppraisalId());
			
			if(appraisalBean != null){
				
				//Adding Appraisal Items
				List<AppraisalItemsBean> appraisalItemsBeans = appraisalBean.getAppraisalItemsBean();
				if(appraisalItemsBeans != null && appraisalItemsBeans.size()>0){
					for (AppraisalItemsBean appraisalItemsBean : appraisalItemsBeans) {
						ValouxItemBean itemBean = valouxItemDao.getItemsBeanByItemId(appraisalItemsBean.getValouxItemBean().getItemId());
						if(itemBean != null){
							
//							addValouxAppraisalItemPrice(appraisalModel, appraisalBean, itemBean, itemAppraisedPrice);
							
							//Appraisal Item component Price Update
							List<ValouxItemComponentBean> componentBeans = itemBean.getValouxItemComponents();
							
							if(componentBeans != null && componentBeans.size() > 0){
								for (ValouxItemComponentBean valouxItemComponentBean : componentBeans) {
									ValouxAppraisalItemsComponentPriceBean appraisalComponentPriceBean = valouxAppraisalItemsComponentPriceDao.getAppraisalItemsComponentPriceByAppraisalAndComponentId(valouxItemComponentBean.getVicid(), appraisalBean.getAppraisalId());
									
									if(appraisalComponentPriceBean == null) {
										appraisalComponentPriceBean = new ValouxAppraisalItemsComponentPriceBean();
									}
									appraisalComponentPriceBean.setValouxAppraisal(appraisalBean);
									AppraisalHelper.prepareAppraisalItemsComponentFromComponent(appraisalComponentPriceBean, valouxItemComponentBean);
									
									appraisalComponentPriceBean.setAppraisedDate(appraisalModel.getModifiedOn());
									appraisalComponentPriceBean.setAppraiserId(appraisalModel.getModifiedBy());
									valouxAppraisalItemsComponentPriceDao.addAppraisalItemsComponentPrice(appraisalComponentPriceBean);
								}
							}
							Double itemApparaisalPrice = 0.0;
							if(itemBean.getFinalPrice() != null){
								itemAppraisedPrice = itemAppraisedPrice + (itemBean.getFinalPrice() * itemBean.getQuantity());
								itemApparaisalPrice = itemBean.getFinalPrice() * itemBean.getQuantity();
							}
							
							itemBean.setLastAppraisaedPrice(itemApparaisalPrice);
							itemBean.setItemStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
							itemBean.setModifiedBy(appraisalModel.getModifiedBy());
							itemBean.setModifiedOn(appraisalModel.getModifiedOn());
							itemBean.setValouxAppraisal(appraisalBean);
							itemBean.setLastAppraiserId(appraisalModel.getModifiedBy());
							itemBean.setLastAppraisedDate(appraisalModel.getModifiedOn());
							valouxItemDao.updateItemdetail(itemBean);
							
							//Appraisal Item Price Update
							ValouxAppraisalItemsPriceBean appraisalItemsPriceBean = valouxAppraisalItemsPriceDao.getAppraisalItemsComponentPriceByAppraisalAndItemId(itemBean.getItemId(), appraisalBean.getAppraisalId());
							
							if(appraisalItemsPriceBean == null) {
								appraisalItemsPriceBean = new ValouxAppraisalItemsPriceBean();
							}
							appraisalItemsPriceBean.setValouxAppraisal(appraisalBean);
							AppraisalHelper.prepareAppraisalItemsFromItem(appraisalItemsPriceBean, itemBean);
							
							valouxAppraisalItemsPriceDao.addAppraisalItemsPrice(appraisalItemsPriceBean);
							
							// Appraisal Item bean status changes
							appraisalItemsBean = appraisalItemsDao.getAppraisalItemBeanByAppraisalAndItemId(appraisalBean.getAppraisalId(), itemBean.getItemId());
							if(appraisalItemsBean != null){
								appraisalItemsBean.setStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
								appraisalItemsBean.setModifiedBy(appraisalModel.getModifiedBy());
								appraisalItemsBean.setModifiedOn(appraisalModel.getModifiedOn());
								appraisalItemsDao.updateAppraisalItemDetails(appraisalItemsBean);
							}
						}
					}
				}
				
				//Adding Appraisal Collection Items
				List<AppraisalCollectionBean> appraisalCollectionBeans = appraisalBean.getAppraisalCollectionBean();
				if(appraisalCollectionBeans != null && appraisalCollectionBeans.size() > 0) {
					for (AppraisalCollectionBean appraisalCollectionBean : appraisalCollectionBeans) {
						ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionDetailsById(appraisalCollectionBean.getValouxCollectionBean().getVcid());
						if(collectionBean != null){
							
//							addValouxAppraisalCollectionPrice(appraisalModel, appraisalBean, collectionBean, collectionAppraisedPrice);
							List<ValouxCollectionItemBean> collectionItemBean = collectionBean.getValouxCollectionItemsBean();
							
							if(collectionItemBean != null && collectionItemBean.size() >0){
								for (ValouxCollectionItemBean valouxCollectionItemBean : collectionItemBean) {
									
									if(valouxCollectionItemBean.getValouxItemBean() != null){
										
										ValouxItemBean itemBean = valouxCollectionItemBean.getValouxItemBean();
										
										//Removing duplicate Appraisal item final price
										AppraisalItemsBean itemsBeanAppraisal = appraisalItemsDao.getAppraisalItemBeanByAppraisalAndItemId(appraisalBean.getAppraisalId(), itemBean.getItemId());
										
										if(itemsBeanAppraisal == null) {
											if(itemBean.getFinalPrice() != null){
												
												if(!itemBean.getItemId().equals(collectionItemId)) {
													collectionAppraisedPriceDistinctItems = collectionAppraisedPriceDistinctItems + (itemBean.getFinalPrice() * itemBean.getQuantity());
												}
												collectionItemId = itemBean.getItemId();
											}
										} 
										
										Double collectionAppraisedItemPrice = 0.0;
										//Adding all item price in collection
										if(itemBean.getFinalPrice() != null){
											collectionAppraisedPrice = collectionAppraisedPrice + (itemBean.getFinalPrice() * itemBean.getQuantity());
											collectionAppraisedItemPrice = itemBean.getFinalPrice() * itemBean.getQuantity();
										}
										
										if(itemBean.getValouxItemComponents() != null) {
											//Appraisal Item component Price Update
											List<ValouxItemComponentBean> componentBeans = itemBean.getValouxItemComponents();
											
											if(componentBeans != null && componentBeans.size() > 0){
												for (ValouxItemComponentBean valouxItemComponentBean : componentBeans) {
													ValouxAppraisalItemsComponentPriceBean appraisalComponentPriceBean = valouxAppraisalItemsComponentPriceDao.getAppraisalItemsComponentPriceByAppraisalAndComponentId(valouxItemComponentBean.getVicid(), appraisalBean.getAppraisalId());
													
													if(appraisalComponentPriceBean == null) {
														appraisalComponentPriceBean = new ValouxAppraisalItemsComponentPriceBean();
													}
													appraisalComponentPriceBean.setValouxAppraisal(appraisalBean);
													AppraisalHelper.prepareAppraisalItemsComponentFromComponent(appraisalComponentPriceBean, valouxItemComponentBean);
													
													appraisalComponentPriceBean.setAppraisedDate(appraisalModel.getModifiedOn());
													appraisalComponentPriceBean.setAppraiserId(appraisalModel.getModifiedBy());
													valouxAppraisalItemsComponentPriceDao.addAppraisalItemsComponentPrice(appraisalComponentPriceBean);
												}
											}
										}
										
										itemBean.setLastAppraisaedPrice(collectionAppraisedItemPrice);
										itemBean.setItemStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
										itemBean.setModifiedBy(appraisalModel.getModifiedBy());
										itemBean.setModifiedOn(appraisalModel.getModifiedOn());
										itemBean.setValouxAppraisal(appraisalBean);
										itemBean.setLastAppraiserId(appraisalModel.getModifiedBy());
										itemBean.setLastAppraisedDate(appraisalModel.getModifiedOn());
										valouxItemDao.updateItemdetail(itemBean);
										
										//Appraisal Item Price Update
										ValouxAppraisalItemsPriceBean appraisalItemsPriceBean = valouxAppraisalItemsPriceDao.getAppraisalItemsComponentPriceByAppraisalAndItemId(valouxCollectionItemBean.getValouxItemBean().getItemId(), appraisalBean.getAppraisalId());
										
										if(appraisalItemsPriceBean == null) {
											appraisalItemsPriceBean = new ValouxAppraisalItemsPriceBean();
										}
										appraisalItemsPriceBean.setValouxAppraisal(appraisalBean);
										AppraisalHelper.prepareAppraisalItemsFromItem(appraisalItemsPriceBean, valouxCollectionItemBean.getValouxItemBean());
										
										valouxAppraisalItemsPriceDao.addAppraisalItemsPrice(appraisalItemsPriceBean);
										
										//Collection Item status update
										valouxCollectionItemBean = valouxCollectionItemDao.getCollectionItemsByCollectionAndItemId(collectionBean.getVcid(), itemBean.getItemId());
										if(valouxCollectionItemBean != null) {
											valouxCollectionItemBean.setStatus(CommonConstants.APPRAISAL_STATUS_APPROVED.byteValue());
											valouxCollectionItemBean.setModifiedBy(appraisalModel.getModifiedBy());
											valouxCollectionItemBean.setModifiedOn(appraisalModel.getModifiedOn());
											valouxCollectionItemDao.updateCollectionItemDetails(valouxCollectionItemBean);
										}
									}
								}
							}
							collectionBean.setLastAppraisaedPrice(collectionAppraisedPrice);
							collectionBean.setCollectionStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
							collectionBean.setModifiedBy(appraisalModel.getModifiedBy());
							collectionBean.setModifiedOn(appraisalModel.getModifiedOn());
							collectionBean.setValouxAppraisal(appraisalBean);
							collectionBean.setLastAppraiserId(appraisalModel.getModifiedBy());
							collectionBean.setLastAppraisedDate(appraisalModel.getModifiedOn());
							valouxCollectionDao.addUpdateCollectionDetails(collectionBean, CommonConstants.UPDATE);
							
							// Appraisal collection bean status changes
							appraisalCollectionBean = appraisalCollectionDao.getAppraisalCollectionByCollectionAndAppraisalId(collectionBean.getVcid(), appraisalBean.getAppraisalId());
							if(appraisalCollectionBean != null){
								appraisalCollectionBean.setStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
								appraisalCollectionBean.setModifiedBy(appraisalModel.getModifiedBy());
								appraisalCollectionBean.setModifiedOn(appraisalModel.getModifiedOn());
								appraisalCollectionDao.updateAppraisalCollectionDetails(appraisalCollectionBean);
							}
							collectionAppraisedPrice = 0.0;
						}
					}
				}
				
				appraisalAppraisedPrice = collectionAppraisedPriceDistinctItems + itemAppraisedPrice;
				appraisalBean.setLastAppraisaedPrice(appraisalAppraisedPrice);
				appraisalBean.setaStatus(CommonConstants.APPRAISAL_STATUS_APPROVED);
				appraisalBean.setModifiedBy(appraisalModel.getModifiedBy());
				appraisalBean.setModifiedOn(appraisalModel.getModifiedOn());
				appraisalBean.setApprovedBy(appraisalModel.getModifiedBy());
				appraisalBean.setApprovedOn(appraisalModel.getModifiedOn());
				
				appraisalDao.approvedOrDisapprovedAppraisal(appraisalBean);
			}
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method changeStatusToAppraised");
		return appraisalModel;
	}

	/**
	 * This method get Appraised item price of appraisal
	 */
	@Transactional
	public ValouxAppraisalItemsPriceBean getAppraisedItemPriceOfAppraisal(Integer itemId,Integer appraisalId) throws Exception{
		
		LOGGER.debug("AppraisalServiceImpl : Enter method getAppraisedItemPriceOfAppraisal");
		ValouxAppraisalItemsPriceBean appraisalItemsPriceBean = valouxAppraisalItemsPriceDao.getAppraisalItemsComponentPriceByAppraisalAndItemId(itemId,appraisalId);
		LOGGER.debug("AppraisalServiceImpl : Exit method getAppraisedItemPriceOfAppraisal");
		return appraisalItemsPriceBean;
		
	}

	@Transactional
	public AppraisalModel changeStatusToUnAppraised(
			AppraisalModel appraisalModel) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method changeStatusToUnAppraised");
		
		if(appraisalModel != null){
			AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalModel.getAppraisalId());
			
			if(appraisalBean != null){
				
				Integer appraisalId = appraisalBean.getAppraisalId();
				
				List<AppraisalItemsBean> appraisalItemsBeans = appraisalBean.getAppraisalItemsBean();
				if(appraisalItemsBeans != null && appraisalItemsBeans.size()>0){
					for (AppraisalItemsBean appraisalItemsBean : appraisalItemsBeans) {
						
						List<AppraisalItemsBean> notAppraisalItemsBeans = appraisalItemsDao.getAppraisalItemsByItemIdAndNotThisAppraisalId(appraisalItemsBean.getValouxItemBean().getItemId(), appraisalId);
						
						if(notAppraisalItemsBeans == null || notAppraisalItemsBeans.isEmpty()){
							
							ValouxItemBean itemBean = valouxItemDao.getItemsBeanByItemId(appraisalItemsBean.getValouxItemBean().getItemId());
							if(itemBean != null){
								changeValouxAppraisalItemStatus(appraisalModel, appraisalBean, itemBean);
							}
						}
						
						// Appraisal Item bean status changes
						AppraisalItemsBean appItemsBean = appraisalItemsDao.getAppraisalItemBeanByAppraisalAndItemId(appraisalBean.getAppraisalId(), appraisalItemsBean.getValouxItemBean().getItemId());
						if(appItemsBean != null){
							appItemsBean.setStatus(CommonConstants.APPRAISAL_STATUS_ACTIVE);
							appItemsBean.setModifiedBy(appraisalModel.getModifiedBy());
							appItemsBean.setModifiedOn(appraisalModel.getModifiedOn());
							appraisalItemsDao.updateAppraisalItemDetails(appItemsBean);
						}
					}
				}
				
				List<AppraisalCollectionBean> appraisalCollectionBeans = appraisalBean.getAppraisalCollectionBean();
				if(appraisalCollectionBeans != null && appraisalCollectionBeans.size() > 0) {
					for (AppraisalCollectionBean appraisalCollectionBean : appraisalCollectionBeans) {
						if(appraisalCollectionBean != null) {
							ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionDetailsById(appraisalCollectionBean.getValouxCollectionBean().getVcid());
							if(collectionBean != null){
								
								List<AppraisalCollectionBean> notappraisalCollectionBeans = appraisalCollectionDao.getAppraisalCollectionssByCollectionAndNotThisAppraisalId(collectionBean.getVcid(), appraisalId);
								
								if(notappraisalCollectionBeans == null || notappraisalCollectionBeans.isEmpty()){
									changeValouxAppraisalCollectionStatus(appraisalModel, appraisalBean, collectionBean);
								}
								
								// Appraisal collection bean status changes
								AppraisalCollectionBean appCollectionBean = appraisalCollectionDao.getAppraisalCollectionByCollectionAndAppraisalId(collectionBean.getVcid(), appraisalBean.getAppraisalId());
								if(appraisalCollectionBean != null){
									appCollectionBean.setStatus(CommonConstants.APPRAISAL_STATUS_ACTIVE);
									appCollectionBean.setModifiedBy(appraisalModel.getModifiedBy());
									appCollectionBean.setModifiedOn(appraisalModel.getModifiedOn());
									appraisalCollectionDao.updateAppraisalCollectionDetails(appCollectionBean);
								}
							}
						}
					}
				}
				
				appraisalBean.setaStatus(CommonConstants.APPRAISAL_STATUS_ACTIVE);
				appraisalBean.setModifiedBy(appraisalModel.getModifiedBy());
				appraisalBean.setModifiedOn(appraisalModel.getModifiedOn());
				appraisalBean.setApprovedBy(appraisalModel.getModifiedBy());
				appraisalBean.setApprovedOn(appraisalModel.getModifiedOn());
				
				appraisalDao.approvedOrDisapprovedAppraisal(appraisalBean);
			}
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method changeStatusToUnAppraised");
		return appraisalModel;
	}

	/**
	 * @paparam appraisalModel
	 * @paparam appraisalBean
	 * @paparam collectionBean
	 * @throws Exception
	 */
	private void changeValouxAppraisalCollectionStatus(
			AppraisalModel appraisalModel, AppraisalBean appraisalBean,
			ValouxCollectionBean collectionBean) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method changeValouxAppraisalCollectionStatus");
		
		List<ValouxCollectionItemBean> collectionItemBean = collectionBean.getValouxCollectionItemsBean();
		
		if(collectionItemBean != null && collectionItemBean.size() >0){
			for (ValouxCollectionItemBean valouxCollectionItemBean : collectionItemBean) {
				
				if(valouxCollectionItemBean.getValouxItemBean() != null){
					
					ValouxItemBean itemBean = valouxItemDao.getItemsBeanByItemId(valouxCollectionItemBean.getValouxItemBean().getItemId());
					if(itemBean != null){
						changeValouxAppraisalItemStatus(appraisalModel, appraisalBean, itemBean);
					}
				}
			}
		}
		collectionBean.setCollectionStatus(CommonConstants.APPRAISAL_STATUS_ACTIVE);
		collectionBean.setModifiedBy(appraisalModel.getModifiedBy());
		collectionBean.setModifiedOn(appraisalModel.getModifiedOn());
		collectionBean.setValouxAppraisal(appraisalBean);
		collectionBean.setLastAppraiserId(appraisalModel.getModifiedBy());
		collectionBean.setLastAppraisedDate(appraisalModel.getModifiedOn());
		valouxCollectionDao.addUpdateCollectionDetails(collectionBean, CommonConstants.UPDATE);
		
		LOGGER.debug("AppraisalServiceImpl : Exit method changeValouxAppraisalCollectionStatus");
	}

	/**
	 * @paparam appraisalModel
	 * @paparam appraisalBean
	 * @paparam itemBean
	 * @throws Exception
	 */
	private void changeValouxAppraisalItemStatus(AppraisalModel appraisalModel,
			AppraisalBean appraisalBean, ValouxItemBean itemBean) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method changeValouxAppraisalItemStatus");
		
		if(appraisalBean!=null &&  itemBean != null){
			itemBean.setItemStatus(CommonConstants.APPRAISAL_STATUS_ACTIVE);
			itemBean.setModifiedBy(appraisalModel.getModifiedBy());
			itemBean.setModifiedOn(appraisalModel.getModifiedOn());
			itemBean.setValouxAppraisal(appraisalBean);
			itemBean.setLastAppraiserId(appraisalModel.getModifiedBy());
			itemBean.setLastAppraisedDate(appraisalModel.getModifiedOn());
			valouxItemDao.updateItemdetail(itemBean);
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method changeValouxAppraisalItemStatus");
	}
	
	/**
	 * This method change appraisal status
	 */
	@Transactional
	public boolean checkAppraisedItemFinalValue(Integer appraisalId)
			throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method checkAppraisedItemFinalValue");
		
		if(appraisalId != null){
			AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalId);
			
			if(appraisalBean != null){
				
				List<AppraisalItemsBean> appraisalItemsBeans = appraisalBean.getAppraisalItemsBean();
				if(appraisalItemsBeans != null && appraisalItemsBeans.size()>0){
					
					for (AppraisalItemsBean appraisalItemsBean : appraisalItemsBeans) {
						
						ValouxItemBean itemBean = valouxItemDao.getItemsBeanByItemId(appraisalItemsBean.getValouxItemBean().getItemId());
						if(itemBean != null){
							if(itemBean.getFinalPrice() == null){
								return false;
							}
						}
					}
				}
				
				List<AppraisalCollectionBean> appraisalCollectionBeans = appraisalBean.getAppraisalCollectionBean();
				if(appraisalCollectionBeans != null && appraisalCollectionBeans.size() > 0) {
					
					for (AppraisalCollectionBean appraisalCollectionBean : appraisalCollectionBeans) {
						ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionDetailsById(appraisalCollectionBean.getValouxCollectionBean().getVcid());
						if(collectionBean != null){
							List<ValouxCollectionItemBean> collectionItemBean = collectionBean.getValouxCollectionItemsBean();
							
							if(collectionItemBean != null && collectionItemBean.size() >0){
								for (ValouxCollectionItemBean valouxCollectionItemBean : collectionItemBean) {
									
									if(valouxCollectionItemBean.getValouxItemBean() != null){
										
										ValouxItemBean itemBean = valouxCollectionItemBean.getValouxItemBean();
										
										if(itemBean.getFinalPrice() == null){
											return false;
										}
									}
								}
							}
						}
					}
				}
				return true;
			}
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method checkAppraisedItemFinalValue");
		return false;
	}

	@Transactional
	public void addAppraisalPdfFile(AppraisalModel appraisalModel) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method addAppraisalPdfFile");
		if(appraisalModel != null){
			AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalModel.getAppraisalId());
			if(appraisalBean != null){
				appraisalBean.setPdfFile(appraisalModel.getPdfFile());
				appraisalDao.approvedOrDisapprovedAppraisal(appraisalBean);
			}
		}
		LOGGER.debug("AppraisalServiceImpl : Exit method addAppraisalPdfFile");
	}

	@Transactional
	public AppraisalModel getAppraisalDetailsById(Integer appraisalId)
			throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method getAppraisalDetailsById");
		
		AppraisalModel appraisalModel = null;
		
		AppraisalBean valouxAppraisalBean = appraisalDao.getAppraisalDetails(appraisalId);
		if(valouxAppraisalBean != null){
			appraisalModel = new AppraisalModel();
			appraisalModel.setAppraisalId(valouxAppraisalBean.getAppraisalId());
			appraisalModel.setName(valouxAppraisalBean.getName());
			appraisalModel.setShortDescription(valouxAppraisalBean.getShortDescription());
			appraisalModel.setaStatus(valouxAppraisalBean.getaStatus());
			appraisalModel.setApprovedBy(valouxAppraisalBean.getApprovedBy());
			appraisalModel.setApprovedOn(valouxAppraisalBean.getApprovedOn());
			appraisalModel.setCreatedBy(valouxAppraisalBean.getCreatedBy());
			appraisalModel.setCreatedOn(valouxAppraisalBean.getCreatedOn());
			appraisalModel.setModifiedBy(valouxAppraisalBean.getModifiedBy());
			appraisalModel.setModifiedOn(valouxAppraisalBean.getModifiedOn());
			appraisalModel.setRelationKey(valouxAppraisalBean.getRelationKey());
			appraisalModel.setLastAppraisaedPrice(valouxAppraisalBean.getLastAppraisaedPrice());
			appraisalModel.setPdfFile(valouxAppraisalBean.getPdfFile());
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method getAppraisalDetailsById");
		return appraisalModel;
	}

	@Transactional
	public List<AppraisalModel> getAllAppraisalsList() throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method getAllAppraisalsList");
		 
		List<AppraisalBean> appraisalBeans = new ArrayList<AppraisalBean>();
		
		List<AppraisalModel> appraisalModels = new ArrayList<AppraisalModel>();
		
		appraisalBeans = appraisalDao.getAllAppraisalsList();
		
		if(appraisalBeans != null && appraisalBeans.size() > 0){
			for (AppraisalBean valouxAppraisalBean : appraisalBeans) {
				
				AppraisalModel appraisalModel = new AppraisalModel();
				populateAppraisalDetailsFromBean(appraisalModel, valouxAppraisalBean);
				appraisalModels.add(appraisalModel);
			}
		}
		LOGGER.debug("AppraisalServiceImpl : Exit method getAllAppraisalsList");
		return appraisalModels;
	}

	/**
	 * @paparam appraisalModel
	 * @paparam valouxAppraisalBean
	 * @throws Exception
	 */
	private void populateAppraisalDetailsFromBean(
			AppraisalModel appraisalModel, AppraisalBean valouxAppraisalBean) throws Exception{
		
		LOGGER.debug("AppraisalServiceImpl : Enter method populateAppraisalDetailsFromBean");
		
		if(valouxAppraisalBean != null){
			appraisalModel.setAppraisalId(valouxAppraisalBean.getAppraisalId());
			appraisalModel.setName(valouxAppraisalBean.getName());
			appraisalModel.setShortDescription(valouxAppraisalBean.getShortDescription());
			appraisalModel.setaStatus(valouxAppraisalBean.getaStatus());
			appraisalModel.setRelationKey(valouxAppraisalBean.getRelationKey());
			appraisalModel.setApprovedBy(valouxAppraisalBean.getApprovedBy());
			
			if(CommonUserUtility.paparameterNullCheckStringObject(valouxAppraisalBean.getApprovedBy())){
				LoginBean loginBean = loginDao.getLoginDetailByPKey((valouxAppraisalBean.getApprovedBy()));
				String approverName = "";
				if(loginBean != null){
					String nameArray [] = {loginBean.getFirstName(), loginBean.getLastName()};
					
					approverName = CommonUserUtility.concatStrings(nameArray);
					if(CommonUserUtility.paparameterNullCheckStringObject(approverName)){
						appraisalModel.setApprovedByName(approverName);
					}
				}
			}
			
			appraisalModel.setApprovedOn(valouxAppraisalBean.getApprovedOn());
			appraisalModel.setCreatedBy(valouxAppraisalBean.getCreatedBy());
			
			if(CommonUserUtility.paparameterNullCheckStringObject(valouxAppraisalBean.getRelationKey())){
				LoginBean loginBean = loginDao.getLoginDetailByPKey((valouxAppraisalBean.getRelationKey()));
				String userName = "";
				if(loginBean != null){
					String nameArray [] = {loginBean.getFirstName(), loginBean.getLastName()};
					
					userName = CommonUserUtility.concatStrings(nameArray);
					if(CommonUserUtility.paparameterNullCheckStringObject(userName)){
						appraisalModel.setUserName(userName);
					}
				}
			}
			
			appraisalModel.setCreatedOn(valouxAppraisalBean.getCreatedOn());
			appraisalModel.setModifiedBy(valouxAppraisalBean.getModifiedBy());
			appraisalModel.setModifiedOn(valouxAppraisalBean.getModifiedOn());
			appraisalModel.setLastAppraisaedPrice(valouxAppraisalBean.getLastAppraisaedPrice());
			appraisalModel.setPdfFile(valouxAppraisalBean.getPdfFile());
			
			Integer itemCount = 0;
			if(valouxAppraisalBean.getAppraisalItemsBean() != null && valouxAppraisalBean.getAppraisalItemsBean().size() > 0){
				itemCount = valouxAppraisalBean.getAppraisalItemsBean().size();
			}
			appraisalModel.setItemCount(itemCount);
			
			Integer collectionCount = 0;
			if(valouxAppraisalBean.getAppraisalCollectionBean()!= null && valouxAppraisalBean.getAppraisalCollectionBean().size() > 0){
				collectionCount = valouxAppraisalBean.getAppraisalCollectionBean().size();
			}
			appraisalModel.setCollectionCount(collectionCount);
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method populateAppraisalDetailsFromBean");
	}

	@Transactional
	public List<AppraisalModel> getTopAppraisalsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method getTopAppraisalsListByUserIdAndLimit");

		List<AppraisalModel> appraisalModels = new ArrayList<AppraisalModel>();
		
		List<AppraisalBean> appraisalBeans = appraisalDao.getTopAppraisalsListByUserIdAndLimit(userPublicKey, limit);
		if(appraisalBeans != null && appraisalBeans.size() > 0) {
			for (AppraisalBean appraisalBean : appraisalBeans) {
				AppraisalModel appraisalModel = new AppraisalModel();
				
				String modifiedByName = UserHelper.getUserNameByPublicKey(loginDao, appraisalBean.getModifiedBy());
				
				if(CommonUserUtility.paparameterNullCheckStringObject(modifiedByName)){
					appraisalModel.setModifiedByName(modifiedByName);
				}
				AppraisalHelper.populateAppraisalModelFromBean(appraisalModel, appraisalBean);
				appraisalModels.add(appraisalModel);
			}
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method getTopAppraisalsListByUserIdAndLimit");
		return appraisalModels;
	}

	@Transactional
	public Boolean deleteAppraisalAndAllDetails(Integer appraisalId,
			String userPublicKey) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method deleteAppraisalAndAllDetails");
		
		if(appraisalId != null) {
			AppraisalBean appraisalBean = appraisalDao.getAppraisalDetails(appraisalId);
			
			if(appraisalBean != null) {
				AppraisalHelper.deleteAllAppraisalDetailsOfAppraisal(appraisalDao, valouxCollectionDao, valouxItemDao, appraisalBean);
				
				List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getSharedRequestListByItemId(appraisalId, CommonConstants.SHARED_TYPE_APPRAISAL);
				if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
					for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
						appraisalDao.deleteAnyBeanByObject(valouxSharedRequestBean);
					}
				}
				return true;
			}
		}
		
		LOGGER.debug("AppraisalServiceImpl : Exit method deleteAppraisalAndAllDetails");
		return false;
	}
	
	@Transactional
	public Boolean deleteConsumerAppraisalAndAllDetails(String userPublicKey) throws Exception {
		
		LOGGER.debug("AppraisalServiceImpl : Enter method deleteConsumerAppraisalAndAllDetails");
		
		
			List<AppraisalBean> appraisalBeanList = appraisalDao.getAppraisalDetailsByUserId(userPublicKey, null, null);
			
			if(appraisalBeanList != null) {
				for(AppraisalBean appraisalBean:appraisalBeanList){
				AppraisalHelper.deleteAllAppraisalDetailsOfAppraisal(appraisalDao, valouxCollectionDao, valouxItemDao, appraisalBean);
				
				List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getSharedRequestListByItemId(appraisalBean.getAppraisalId(), CommonConstants.SHARED_TYPE_APPRAISAL);
				if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
					for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
						appraisalDao.deleteAnyBeanByObject(valouxSharedRequestBean);
					}
				}
			}
				return true;
			}
		
		
		LOGGER.debug("AppraisalServiceImpl : Exit method deleteConsumerAppraisalAndAllDetails");
		return false;
	}

}
