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
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AppraisalCollectionDao;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.LoginDao;
import com.valoux.dao.UserDao;
import com.valoux.dao.ValouxCollectionDao;
import com.valoux.dao.ValouxCollectionImageDao;
import com.valoux.dao.ValouxCollectionItemDao;
import com.valoux.dao.ValouxComponentsImageDao;
import com.valoux.dao.ValouxSharedRequestDao;
import com.valoux.helper.CollectionHelper;
import com.valoux.helper.UserHelper;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ValouxCollectionImageModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.service.CollectionService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;

/**
 * This <java>class</java> CollectionServiceImpl use to perform all our service
 * related logics for the Collection.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	UserDao userDao;

	@Autowired
	ValouxCollectionDao valouxCollectionDao;
	
	@Autowired
	ValouxCollectionImageDao valouxCollectionImageDao;
	
	@Autowired
	ValouxCollectionItemDao valouxCollectionItemDao;
	
	@Autowired
	ValouxComponentsImageDao valouxComponentsImageDao;
	
	@Autowired
	AppraisalCollectionDao appraisalCollectionDao;
	
	@Autowired
	AppraisalDao appraisalDao;
	
	@Autowired
	ValouxSharedRequestDao valouxSharedRequestDao;
	
	@Autowired
	LoginDao loginDao;

	private static final Logger LOGGER = Logger.getLogger(CollectionServiceImpl.class);

	/**
	 * This method add update collections data.
	 */
	@Transactional
	public ValouxCollectionModel addUpdateCollectionDetails(ValouxCollectionModel collectionModel, String requestType)
			throws Exception {

		LOGGER.info("CollectionServiceImpl : Enter Method addUpdateCollectionDetails");
		ValouxCollectionModel collectionModel1 = new ValouxCollectionModel();
		
		if (collectionModel != null) {
			
			ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionBeanByCollectionId(collectionModel.getVcid());
			
			if(collectionBean == null){
				collectionBean = new ValouxCollectionBean();
			}
			
			collectionBean = CollectionHelper.populateCollectionBeanFromModel(collectionBean, collectionModel);
			if (collectionBean != null) {
				valouxCollectionDao.addUpdateCollectionDetails(collectionBean, requestType);
				if(collectionBean!=null){
					collectionModel1.setVcid(collectionBean.getVcid());
					collectionModel1.setCname(collectionBean.getCname());
					collectionModel1.setShortDescription(collectionBean.getShortDescription());
					collectionModel1.setCollectionStatus(collectionBean.getCollectionStatus());
					collectionModel1.setCreatedBy(collectionBean.getCreatedBy());
					collectionModel1.setCreatedOn(collectionBean.getCreatedOn());
					collectionModel1.setModifiedBy(collectionBean.getModifiedBy());
					collectionModel1.setModifiedOn(collectionBean.getModifiedOn());
					collectionModel1.setRkey(collectionBean.getRkey());
					collectionModel1.setLastAppraisedDate(collectionBean.getLastAppraisedDate());
					collectionModel1.setLastAppraiserId(collectionBean.getLastAppraiserId());
				}
			}
		}

		LOGGER.info("CollectionServiceImpl : Exit Method addUpdateCollectionDetails");
		return collectionModel1;
	}

	/**
	 * This method for fetching collections data.
	 */
	@Transactional
	public ValouxCollectionModel getCollectionDetailsById(int collectionId) throws Exception {
		LOGGER.info("CollectionServiceImpl : Enter Method getCollectionDetailsById");

		ValouxCollectionBean collectionBean = null;
		
		collectionBean = valouxCollectionDao.getCollectionDetailsById(collectionId);
		ValouxCollectionModel collectionModel = new ValouxCollectionModel();
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
		LOGGER.info("CollectionServiceImpl : Exit Method getCollectionDetailsById");
		return collectionModel;
	}

	/**
	 * This method for saving collections data.
	 * 
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	@Transactional
	public void addItemsInCollection(ValouxCollectionItemModel collectionItemModel) throws Exception {

		LOGGER.info("CollectionServiceImpl : Enter Method addItemsInCollection");

		if (collectionItemModel != null && collectionItemModel.getItems() != null
				&& collectionItemModel.getItems().length() > 0) {

			JSONArray collectionItems = collectionItemModel.getItems();
			List<ValouxCollectionItemBean> itemsList = new ArrayList<ValouxCollectionItemBean>();

			List<Integer> dbitemList = new ArrayList<Integer>();

			List<Integer> modelItemList = new ArrayList<Integer>();

			List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsById(collectionItemModel
					.getCollectionId());

			if (collectionBeans != null && collectionBeans.size() > 0) {
				for (ValouxCollectionItemBean valouxCollectionItemBean : collectionBeans) {
					dbitemList.add(valouxCollectionItemBean.getValouxItemBean().getItemId());
				}
			}
			Collections.sort(dbitemList);

			for (int i = 0; i < collectionItems.length(); i++) {
				modelItemList.add((Integer) collectionItems.get(i));
			}
			Collections.sort(modelItemList);

			List<Integer> deleteListItems = new ArrayList<Integer>(dbitemList);
			deleteListItems.removeAll(modelItemList);

			if (deleteListItems != null && deleteListItems.size() > 0) {
				valouxCollectionItemDao.deletedItemsFromCollection(deleteListItems, collectionItemModel.getCollectionId());
			}

			for (int i = 0; i < collectionItems.length(); i++) {

				int itemId = Integer.parseInt(collectionItems.getString(i));
				boolean flag = false;

				List<ValouxCollectionItemBean> collectionItemBeanList = valouxCollectionItemDao
						.getCollectionItemsByCollectionIdAndItemId(collectionItemModel.getCollectionId(), itemId);
				if (collectionItemBeanList != null && collectionItemBeanList.size() > 0) {
					flag = true;
				}
				if (!flag) {
					ValouxCollectionItemBean itemBean = new ValouxCollectionItemBean();
//					itemBean.setCollectionId(collectionItemModel.getCollectionId());
					ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
					valouxCollectionBean.setVcid(collectionItemModel.getCollectionId());
					itemBean.setValouxCollectionBean(valouxCollectionBean);
					
//					itemBean.setItemId(itemId);
					ValouxItemBean valouxItemBean = new ValouxItemBean();
					valouxItemBean.setItemId(itemId);
					itemBean.setValouxItemBean(valouxItemBean);
					
					itemBean.setStatus(collectionItemModel.isStatus().byteValue());
					if (collectionItemModel.getRequestType().equalsIgnoreCase(CommonConstants.ADD)) {
						itemBean.setCreatedBy(collectionItemModel.getCreatedBy());
						itemBean.setCreatedOn(collectionItemModel.getCreatedOn());
						itemBean.setModifiedBy(collectionItemModel.getModifiedBy());
						itemBean.setModifiedOn(collectionItemModel.getModifiedOn());
					} else if (collectionItemModel.getRequestType().equalsIgnoreCase(CommonConstants.UPDATE)) {
						itemBean.setModifiedBy(collectionItemModel.getModifiedBy());
						itemBean.setModifiedOn(collectionItemModel.getModifiedOn());
					}
					itemsList.add(itemBean);
				}
			}
			valouxCollectionItemDao.addItemsInCollection(itemsList);
		} else if (collectionItemModel != null && collectionItemModel.getCollectionId() != null) {

			List<ValouxCollectionItemBean> beanList = valouxCollectionItemDao.getCollectionItemsById(collectionItemModel
					.getCollectionId());

			if (beanList != null && beanList.size() > 0) {
				valouxCollectionItemDao.deletedAllItemsFromCollection(beanList);
			}
		}

		LOGGER.info("CollectionServiceImpl : Exit Method addItemsInCollection");
	}

	/**
	 * This method for saving collections data.
	 * 
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	@Transactional
	public void addItemInCollection(Integer itemId, JSONArray collectionId) throws Exception {

		LOGGER.info("CollectionServiceImpl : Enter Method addItemInCollection");
		Integer collectionIdi = 0;
		List<ValouxCollectionItemBean> itemsList = new ArrayList<ValouxCollectionItemBean>();

		ValouxCollectionItemBean itemBean = null;
		for (int i = 0; i < collectionId.length(); i++) {
			itemBean = new ValouxCollectionItemBean();
			collectionIdi = Integer.parseInt(collectionId.getString(i));
			
			ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionBeanByCollectionId(collectionIdi);

			if(collectionBean != null && collectionBean.getCollectionStatus() != null && !(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED))){
//				itemBean.setCollectionId(collectionIdi);
				ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
				valouxCollectionBean.setVcid(collectionIdi);
				itemBean.setValouxCollectionBean(valouxCollectionBean);
				
//				itemBean.setItemId(itemId);
				ValouxItemBean valouxItemBean = new ValouxItemBean();
				valouxItemBean.setItemId(itemId);
				itemBean.setValouxItemBean(valouxItemBean);
				// itemBean.setStatus("");
				// itemBean.setCreatedBy(1);
				itemBean.setCreatedOn(CommonUtility.getDateAndTime());
				// itemBean.setModifiedBy(1);
				itemBean.setModifiedOn(CommonUtility.getDateAndTime());

				itemsList.add(itemBean);
			}
		}

		valouxCollectionItemDao.addItemsInCollection(itemsList);

		LOGGER.info("CollectionServiceImpl : Exit Method addItemInCollection");
	}

	/**
	 * This method is used for fetching collections data.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public List<ValouxCollectionModel> getUserCollectionsList(String relationKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {

		LOGGER.info("CollectionServiceImpl : Enter Method getUserCollectionsList");

		List<ValouxCollectionModel> collectionModels = new ArrayList<ValouxCollectionModel>();
		List<ValouxCollectionBean> collectionBeans = valouxCollectionDao.getCollectionDetailsByUserId(relationKey, startRecordNo, numberOfRecords, sortBy, sortOrder);

		if (collectionBeans != null && collectionBeans.size() > 0) {
			for (ValouxCollectionBean valouxCollectionBean : collectionBeans) {
				ValouxCollectionModel collectionModel = new ValouxCollectionModel();
				collectionModel.setVcid(valouxCollectionBean.getVcid());
				collectionModel.setCname(valouxCollectionBean.getCname());
				collectionModel.setShortDescription(valouxCollectionBean.getShortDescription());
				collectionModel.setCollectionStatus(valouxCollectionBean.getCollectionStatus());
				collectionModel.setCreatedBy(valouxCollectionBean.getCreatedBy());
				collectionModel.setCreatedOn(valouxCollectionBean.getCreatedOn());
				collectionModel.setModifiedBy(valouxCollectionBean.getModifiedBy());
				collectionModel.setModifiedOn(valouxCollectionBean.getModifiedOn());
				collectionModel.setRkey(valouxCollectionBean.getRkey());
				collectionModel.setLastAppraisedDate(valouxCollectionBean.getLastAppraisedDate());
				collectionModel.setLastAppraiserId(valouxCollectionBean.getLastAppraiserId());
				collectionModels.add(collectionModel);
			}
		}

		LOGGER.info("CollectionServiceImpl : Exit Method getUserCollectionsList");
		return collectionModels;
	}

	/**
	 * This method is used for fetching list of collection items data.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public List<ValouxCollectionItemModel> getUserCollectionItemsList(Integer collectionId) throws Exception {

		LOGGER.info("CollectionServiceImpl : Enter Method getAllUserCollectionItemsList");

		List<ValouxCollectionItemModel> collectionModels = new ArrayList<ValouxCollectionItemModel>();
		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsById(collectionId);

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (ValouxCollectionItemBean valouxCollectionItemBean : collectionBeans) {

				ValouxCollectionItemModel itemModel = new ValouxCollectionItemModel();
				itemModel.setCollectionId(valouxCollectionItemBean.getValouxCollectionBean().getVcid());
				itemModel.setItemId(valouxCollectionItemBean.getValouxItemBean().getItemId());
				itemModel.setCreatedBy(valouxCollectionItemBean.getCreatedBy());
				itemModel.setCreatedOn(valouxCollectionItemBean.getCreatedOn());
				itemModel.setModifiedBy(valouxCollectionItemBean.getModifiedBy());
				itemModel.setModifiedOn(valouxCollectionItemBean.getModifiedOn());
				itemModel.setStatus((int) valouxCollectionItemBean.getStatus());
				collectionModels.add(itemModel);
			}
		}

		LOGGER.info("CollectionServiceImpl : Exit Method getAllUserCollectionItemsList");
		return collectionModels;
	}

	/**
	 * This method is used for deleting list of collection items.
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void deleteUserCollectionItemsList(Integer collectionId) throws Exception {
		LOGGER.info("CollectionServiceImpl : Enter Method deleteUserCollectionItemsList");
		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsById(collectionId);

		if (collectionBeans != null && collectionBeans.size() > 0) {
			valouxCollectionItemDao.deleteUserCollectionItemsList(collectionBeans);
		}
		LOGGER.info("CollectionServiceImpl : Exit Method deleteUserCollectionItemsList");
	}

	/**
	 * This method checks whether item is already added in collection or not
	 * 
	 * @paparam itemId
	 * @paparam collectionId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean checkItemAlreadyAddedInCollection(Integer itemId, JSONArray collectionId) throws Exception {
		LOGGER.info("CollectionServiceImpl : Enter Method checkItemAlreadyAddedInCollection");
		List<ValouxCollectionItemBean> collectionItemBeanList = valouxCollectionItemDao
				.getCollectionItemsByCollectionArrayIdAndItemId(collectionId, itemId);
		if (collectionItemBeanList != null && collectionItemBeanList.size() > 0) {
			return true;
		}
		LOGGER.info("CollectionServiceImpl : Exit Method checkItemAlreadyAddedInCollection");
		return false;
	}

	/**
	 * This method checks whether item is already added in collection or not
	 * 
	 * @paparam itemId
	 * @paparam collectionId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean deleteItemFromCollection(Integer itemId, Integer collectionId) throws Exception {
		LOGGER.info("CollectionServiceImpl : Enter Method deleteItemFromCollection");

		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsByCollectionIdAndItemId(
				collectionId, itemId);
		Boolean flag = false;

		if (collectionBeans != null && collectionBeans.size() > 0) {
			flag = true;
			valouxCollectionItemDao.deleteUserCollectionItemsList(collectionBeans);
		}

		LOGGER.info("CollectionServiceImpl : Exit Method deleteItemFromCollection");
		return flag;
	}

	/**
	 * This method get the json array of all collection id associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public JSONArray getCollectionIdAssociatedWithItem(Integer itemId) throws Exception {
		LOGGER.debug("CollectionServiceImpl : Enter Method getCollectionIdAssociatedWithItem ");
		JSONArray jArray = new JSONArray();
		List<ValouxCollectionItemBean> valouxCollectionItemBeanList = valouxCollectionItemDao.getCollectionItemsByItemId(itemId);
		if (valouxCollectionItemBeanList != null && valouxCollectionItemBeanList.size() > 0) {
			for (ValouxCollectionItemBean collectionItemBean : valouxCollectionItemBeanList) {
				jArray.put(collectionItemBean.getValouxCollectionBean().getVcid());
			}
		}
		LOGGER.debug("CollectionServiceImpl : Exit Method getCollectionIdAssociatedWithItem");
		return jArray;
	}

	/**
	 * This method returns the collection detail associated with item
	 * 
	 * @paparam itemId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ValouxCollectionModel> getCollectionDetailAssociatedWithItem(Integer itemId) throws Exception {
		LOGGER.debug("CollectionServiceImpl : Enter Method getCollectionDetailAssociatedWithItem ");
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();
		List<ValouxCollectionItemBean> valouxCollectionItemBeanList = valouxCollectionItemDao.getCollectionItemsByItemId(itemId);
		if (valouxCollectionItemBeanList != null && valouxCollectionItemBeanList.size() > 0) {
			for (ValouxCollectionItemBean collectionItemBean : valouxCollectionItemBeanList) {
				ValouxCollectionBean valouxCollectionBean = valouxCollectionDao.getCollectionDetailsById(collectionItemBean
						.getValouxCollectionBean().getVcid());
				ValouxCollectionModel collectionModel = new ValouxCollectionModel();
				collectionModel.setVcid(valouxCollectionBean.getVcid());
				collectionModel.setCname(valouxCollectionBean.getCname());
				collectionModel.setShortDescription(valouxCollectionBean.getShortDescription());
				collectionModel.setCollectionStatus((int) valouxCollectionBean.getCollectionStatus());
				collectionModel.setCreatedBy(valouxCollectionBean.getCreatedBy());
				collectionModel.setCreatedOn(valouxCollectionBean.getCreatedOn());
				collectionModel.setModifiedBy(valouxCollectionBean.getModifiedBy());
				collectionModel.setModifiedOn(valouxCollectionBean.getModifiedOn());
				collectionModel.setRkey(valouxCollectionBean.getRkey());
				collectionModelList.add(collectionModel);
			}
		}
		LOGGER.debug("CollectionServiceImpl : Exit Method getCollectionDetailAssociatedWithItem");
		return collectionModelList;
	}

	/**
	 * This method get the collection image details associated with collection
	 * id
	 * 
	 * @paparam collectionImageModel
	 * @return
	 * @throws Exception
	 */

	@Transactional
	public List<ValouxCollectionImageBean> addCollectionImageDetails(List<ValouxCollectionImageModel> imageModelList)
			throws Exception {

		LOGGER.debug("CollectionServiceImpl : Enter Method addCollectionImageDetails ");

		List<ValouxCollectionImageBean> collectionImageBeanList = new ArrayList<ValouxCollectionImageBean>();

		if (imageModelList != null && imageModelList.size() > 0) {

			List<ValouxCollectionImageBean> imageBeanList = valouxCollectionImageDao
					.getValouxCollectionImageBeanByCollectionId(imageModelList.get(0).getCollectionId());
			//
			if (imageBeanList != null && imageBeanList.size() > 0) {
				valouxCollectionImageDao.deleteValouxCollectionImageBeanByCollectionId(imageBeanList);
			}
			for (ValouxCollectionImageModel collectionImageModel : imageModelList) {

				ValouxCollectionImageBean collectionImageBean = new ValouxCollectionImageBean();

				collectionImageBean.setImgName(collectionImageModel.getImgName());
				collectionImageBean.setImgStatus(collectionImageModel.isImgStatus().byteValue());
				collectionImageBean.setImgUrl(collectionImageModel.getImgUrl());
//				collectionImageBean.setCollectionId(collectionImageModel.getCollectionId());
				ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
				valouxCollectionBean.setVcid(collectionImageModel.getCollectionId());
				collectionImageBean.setValouxCollectionBean(valouxCollectionBean);;

				if (collectionImageModel.getRequestType().equalsIgnoreCase(CommonConstants.ADD)) {
					collectionImageBean.setCreatedBy(collectionImageModel.getCreatedBy());
					collectionImageBean.setCreatedOn(collectionImageModel.getCreatedOn());
					collectionImageBean.setModifiedBy(collectionImageModel.getModifiedBy());
					collectionImageBean.setModifiedOn(collectionImageModel.getModifiedOn());
				} else if (collectionImageModel.getRequestType().equalsIgnoreCase(CommonConstants.UPDATE)) {
					collectionImageBean.setModifiedBy(collectionImageModel.getModifiedBy());
					collectionImageBean.setModifiedOn(collectionImageModel.getModifiedOn());
				}
				collectionImageBeanList.add(collectionImageBean);
			}

			collectionImageBeanList = valouxCollectionImageDao.saveValouxCollectionImageBeanOfCollection(collectionImageBeanList);
		}

		LOGGER.debug("CollectionServiceImpl : Exit Method addCollectionImageDetails");
		return collectionImageBeanList;
	}

	/**
	 * Method for fetching collection image details
	 * 
	 * @paparam vcid
	 * @return
	 */
	@Transactional
	public List<ValouxCollectionImageBean> getCollectionImageDetailsById(Integer vcid) throws Exception {
		LOGGER.debug("CollectionServiceImpl : Enter Method getCollectionImageDetailsById ");
		List<ValouxCollectionImageBean> collectionImageBeanList = valouxCollectionImageDao
				.getValouxCollectionImageBeanByCollectionId(vcid);
		LOGGER.debug("CollectionServiceImpl : Exit Method getCollectionImageDetailsById");
		return collectionImageBeanList;
	}

	/**
	 * Method for deleting collection image details
	 * 
	 * @paparam collectionId
	 * @paparam imageId
	 * @return
	 */
	@Transactional
	public Boolean deleteImageDocumentByCollectionAndImageId(Integer collectionId, Integer imageId) throws Exception {

		LOGGER.debug("CollectionServiceImpl : Enter Method deleteImageDocumentByCollectionAndImageId");

		ValouxCollectionImageBean collectionImageBean = valouxCollectionImageDao.getCollectionImageByCollectionAndImageId(
				collectionId, imageId);
		if (collectionImageBean != null) {
			valouxCollectionImageDao.deleteValouxCollectionImageBeanByCollectionAndImageId(collectionImageBean);
			CommonUtility.deleteDocumentInDirectory(collectionImageBean.getImgUrl());
			return true;
		}

		LOGGER.debug("CollectionServiceImpl : Exit Method deleteImageDocumentByCollectionAndImageId");
		return false;
	}

	/**
	 * Method for check if collection name already exists for collection
	 * 
	 * @paparam publicKey
	 * @paparam collectionName
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean checkCollectionNameExistForUser(String publicKey, String collectionName, Integer collectionId)
			throws Exception {
		LOGGER.debug("CollectionServiceImpl : Enter Method checkCollectionNameExistForUser");

		List<ValouxCollectionBean> collectionBeanList = valouxCollectionDao.checkCollectionNameExistForUser(publicKey,
				collectionName);
		if (collectionBeanList != null && collectionBeanList.size() > 0) {

			if (collectionBeanList.get(0).getVcid().equals(collectionId)) {
				return false;
			}
			return true;
		}

		LOGGER.debug("CollectionServiceImpl : Exit Method checkCollectionNameExistForUser");
		return false;
	}

	/**
	 * This method give all collection List not associated with item.
	 */
	@Transactional
	public List<ValouxCollectionModel> getCollectionDetailNoteAssociatedWithItem(String rKey, Integer itemId)
			throws Exception {
		LOGGER.debug("CollectionServiceImpl : Enter Method getCollectionDetailNoteAssociatedWithItem");
		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsByItemId(itemId);

		List<Integer> collectionList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (ValouxCollectionItemBean collectionItemBean : collectionBeans) {
				collectionList.add(collectionItemBean.getValouxCollectionBean().getVcid());
			}
		}
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();
		List<ValouxCollectionBean> collectionBeanList = valouxCollectionDao.getCollectionListNotAssociatedWithItem(rKey,
				collectionList.toArray());
		if (collectionBeanList != null && collectionBeanList.size() > 0) {
			for (ValouxCollectionBean valouxCollectionBean : collectionBeanList) {
				ValouxCollectionModel collectionModel = new ValouxCollectionModel();
				collectionModel.setVcid(valouxCollectionBean.getVcid());
				collectionModel.setCname(valouxCollectionBean.getCname());
				collectionModel.setShortDescription(valouxCollectionBean.getShortDescription());
				collectionModel.setCollectionStatus(valouxCollectionBean.getCollectionStatus());
				collectionModel.setCreatedBy(valouxCollectionBean.getCreatedBy());
				collectionModel.setCreatedOn(valouxCollectionBean.getCreatedOn());
				collectionModel.setModifiedBy(valouxCollectionBean.getModifiedBy());
				collectionModel.setModifiedOn(valouxCollectionBean.getModifiedOn());
				collectionModel.setRkey(valouxCollectionBean.getRkey());
				collectionModelList.add(collectionModel);
			}
		}
		LOGGER.debug("CollectionServiceImpl : Exit Method getCollectionDetailNoteAssociatedWithItem");
		return collectionModelList;
	}

	/**
	 * This method delete appraisal from collection
	 */
	@Transactional
	public Boolean deleteAppraisalFromCollection(Integer appraisalId, Integer collectionId) throws Exception {
		LOGGER.info("CollectionServiceImpl : Enter Method deleteAppraisalFromCollection");

		List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao
				.getCollectionAppraisalByCollectionIdAndAppraisalId(collectionId, appraisalId);
		Boolean flag = false;

		if (collectionBeans != null && collectionBeans.size() > 0) {
			flag = true;
			appraisalCollectionDao.deleteUserCollectionAppraisalList(collectionBeans);
		}

		LOGGER.info("CollectionServiceImpl : Exit Method deleteAppraisalFromCollection");
		return flag;
	}

	/**
	 * This method get all appraisals not in collection
	 */
	@Transactional
	public List<AppraisalModel> getConsumerAppraisalsNotInCollection(String publicKey, Integer collectionId)
			throws Exception {
		LOGGER.info("CollectionServiceImpl : Enter Method getConsumerAppraisalsNotInCollection");
		List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao.getCollectionAppraisalsById(collectionId);

		List<Integer> appraisalList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (AppraisalCollectionBean collectionBean : collectionBeans) {
				appraisalList.add(collectionBean.getAppraisalBean().getAppraisalId());
			}
		}

		List<AppraisalBean> beanList = appraisalDao.getConsumerAppraisalsNotInCollection(publicKey,
				appraisalList.toArray());
		List<AppraisalModel> modelList = new ArrayList<AppraisalModel>();
		if (beanList != null && beanList.size() > 0) {
			for (AppraisalBean appraisalBean : beanList) {
				AppraisalModel model = CommonUserUtility.prepareAppraisalModelFromAppraisalBean(appraisalBean);
				modelList.add(model);
			}
		}
		LOGGER.info("CollectionServiceImpl : Exit Method getConsumerAppraisalsNotInCollection");
		return modelList;
	}

	/**
	 * This method add all appraisals in collection
	 */
	@Transactional
	public void addAppraisalsInCollection(AppraisalCollectionModel appraisalCollectionModel) throws Exception {

		LOGGER.info("CollectionServiceImpl : Enter Method addAppraisalsInCollection");

		if (appraisalCollectionModel != null && appraisalCollectionModel.getCollectionAppraisals() != null
				&& appraisalCollectionModel.getCollectionAppraisals().length() > 0) {

			JSONArray collectionAppraisals = appraisalCollectionModel.getCollectionAppraisals();
			List<AppraisalCollectionBean> appraisalsList = new ArrayList<AppraisalCollectionBean>();

			List<Integer> dbList = new ArrayList<Integer>();

			List<Integer> modelList = new ArrayList<Integer>();

			List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao
					.getCollectionAppraisalsById(appraisalCollectionModel.getAppraisalCollectionId());

			if (collectionBeans != null && collectionBeans.size() > 0) {
				for (AppraisalCollectionBean appraisalCollectionBean : collectionBeans) {
					dbList.add(appraisalCollectionBean.getAppraisalBean().getAppraisalId());
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
				appraisalCollectionDao.deletedAppraisalsFromCollection(deleteListAppraisals,
						appraisalCollectionModel.getAppraisalCollectionId());
			}

			for (int i = 0; i < collectionAppraisals.length(); i++) {

				int appraisalId = Integer.parseInt(collectionAppraisals.getString(i));
				boolean flag = false;

				List<AppraisalCollectionBean> collectionBeanList = appraisalCollectionDao
						.getCollectionAppraisalByCollectionIdAndAppraisalId(
								appraisalCollectionModel.getAppraisalCollectionId(), appraisalId);
				if (collectionBeanList != null && collectionBeanList.size() > 0) {
					flag = true;
				}
				if (!flag) {
					AppraisalCollectionBean appraisalBean = new AppraisalCollectionBean();
//					appraisalBean.setCollectionId(appraisalCollectionModel.getAppraisalCollectionId());
					ValouxCollectionBean valouxCollectionBean = new ValouxCollectionBean();
					valouxCollectionBean.setVcid(appraisalCollectionModel.getAppraisalCollectionId());
					appraisalBean.setValouxCollectionBean(valouxCollectionBean);
					
//					appraisalBean.setAppraisalId(appraisalId);
					AppraisalBean appraisal = new AppraisalBean();
					appraisal.setAppraisalId(appraisalId);
					appraisalBean.setAppraisalBean(appraisal);
									
					appraisalBean.setStatus(appraisalCollectionModel.getStatus());
					if (appraisalCollectionModel.getRequestType().equalsIgnoreCase(CommonConstants.ADD)) {
						appraisalBean.setCreatedBy(appraisalCollectionModel.getCreatedBy());
						appraisalBean.setCreatedOn(appraisalCollectionModel.getCreatedOn());
						appraisalBean.setModifiedBy(appraisalCollectionModel.getModifiedBy());
						appraisalBean.setModifiedOn(appraisalCollectionModel.getModifiedOn());
					} else if (appraisalCollectionModel.getRequestType().equalsIgnoreCase(CommonConstants.UPDATE)) {
						appraisalBean.setModifiedBy(appraisalCollectionModel.getModifiedBy());
						appraisalBean.setModifiedOn(appraisalCollectionModel.getModifiedOn());
					}

					appraisalsList.add(appraisalBean);
				}
			}
			appraisalCollectionDao.addAppraisalsInCollection(appraisalsList);

		} else if (appraisalCollectionModel != null && appraisalCollectionModel.getAppraisalCollectionId() != null) {

			List<AppraisalCollectionBean> beanList = appraisalCollectionDao.getCollectionAppraisalsById(appraisalCollectionModel
					.getAppraisalCollectionId());

			if (beanList != null && beanList.size() > 0) {
				appraisalCollectionDao.deletedAllAppraisalsFromCollection(beanList);
			}
		}

		LOGGER.info("CollectionServiceImpl : Exit Method addAppraisalsInCollection");
	}
	
	/**
	 * This method will return list of collection
	 */
	@Transactional
	public List<ValouxCollectionModel> getCollectionListAssociatedWithUserAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception{
		LOGGER.debug("CollectionServiceImpl : Enter Method getCollectionListAssociatedWithUserAndHaveKeyword");
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();
		List<ValouxCollectionBean> collectionBeanList = valouxCollectionDao.getCollectionListForGlobalSearchByName(rKey, keyword, null, startRecordNo, numberOfRecords, sortBy, sortOrder);
		if(collectionBeanList!=null){
			for(ValouxCollectionBean collectionBean:collectionBeanList){
					ValouxCollectionModel collectionModel = new ValouxCollectionModel();
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
					collectionModelList.add(collectionModel);
			}
		}
		LOGGER.debug("CollectionServiceImpl : Exit Method getCollectionListAssociatedWithUserAndHaveKeyword");
		return collectionModelList;
	}
	
	/**
	 * This method will return list of collection
	 */
	@Transactional
	public List<ValouxCollectionModel> getCollectionListAssociatedWithAgentAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception{
		LOGGER.debug("CollectionServiceImpl : Enter Method getCollectionListAssociatedWithAgentAndHaveKeyword");
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();
		
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestItemListSharedToUser(rKey, 2, 2, null, null, null, null);
		List<ValouxCollectionBean> collectionBeanList = new ArrayList<ValouxCollectionBean>();
		List<Integer> itemList = new ArrayList<Integer>();
		if(sharedRequestBeanList!=null && sharedRequestBeanList.size()>0){
			for(ValouxSharedRequestBean sharedReuestBean : sharedRequestBeanList){
				itemList.add(sharedReuestBean.getSharedItemId());
			}
		}
		if(itemList!=null && itemList.size()>0){
		collectionBeanList = valouxCollectionDao.getCollectionListForGlobalSearchByName(null, keyword, itemList.toArray(),startRecordNo, numberOfRecords, sortBy, sortOrder);
		if(collectionBeanList!=null){
			for(ValouxCollectionBean collectionBean:collectionBeanList){
					ValouxCollectionModel collectionModel = new ValouxCollectionModel();
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
					collectionModelList.add(collectionModel);
			}
		}
		}
		LOGGER.debug("CollectionServiceImpl : Exit Method getCollectionListAssociatedWithAgentAndHaveKeyword");
		return collectionModelList;
	}

	@Transactional
	public List<ValouxCollectionModel> getTopCollectionsListByUserIdAndLimit(
			String userPublicKey, Integer limit) throws Exception {
		
		LOGGER.debug("CollectionServiceImpl : Enter Method getTopCollectionsListByUserIdAndLimit");
		
		List<ValouxCollectionModel> collectionModels = new ArrayList<ValouxCollectionModel>();
		List<ValouxCollectionBean> collectionBeans = valouxCollectionDao.getTopCollectionsListByUserIdAndLimit(userPublicKey, limit);
		if(collectionBeans != null && collectionBeans.size() > 0){
			for (ValouxCollectionBean valouxCollectionBean : collectionBeans) {
				ValouxCollectionModel collectionModel = new ValouxCollectionModel();
				
				String modifiedByName = UserHelper.getUserNameByPublicKey(loginDao, valouxCollectionBean.getModifiedBy());
				
				if(CommonUserUtility.paparameterNullCheckStringObject(modifiedByName)){
					collectionModel.setModifiedByName(modifiedByName);
				}
				
				CollectionHelper.populateCollectionModelFromBean(valouxCollectionBean, collectionModel);
				collectionModels.add(collectionModel);
			}
		}
		
		LOGGER.debug("CollectionServiceImpl : Exit Method getTopCollectionsListByUserIdAndLimit");
		return collectionModels;
	}

	@Transactional
	public Boolean deleteCollectionAndAllDetails(Integer collectionId,
			String userPublicKey) throws Exception {
		
		LOGGER.debug("CollectionServiceImpl : Enter Method deleteCollectionAndAllDetails");
		
		if(collectionId != null){
			ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionBeanByCollectionId(collectionId);
			if(collectionBean != null && collectionBean.getCollectionStatus() != null && collectionBean.getCollectionStatus() != CommonConstants.APPRAISAL_STATUS_APPROVED){
				
				CollectionHelper.deleteAllCollectionDetailsOfCollection(valouxCollectionDao, collectionBean);
				
				List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getSharedRequestListByItemId(collectionId, CommonConstants.SHARED_TYPE_COLLECTION);
				if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
					for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
						valouxCollectionDao.deleteAnyBeanByObject(valouxSharedRequestBean);
					}
				}
				return true;
			}
		}
		
		LOGGER.debug("CollectionServiceImpl : Exit Method deleteCollectionAndAllDetails");
		return false;
	}
	
	@Transactional
	public Boolean deleteConsumerCollectionAndAllDetails(
			String userPublicKey) throws Exception {
		
		LOGGER.debug("CollectionServiceImpl : Enter Method deleteConsumerCollectionAndAllDetails");
		
		
			List<ValouxCollectionBean> collectionBeanList = valouxCollectionDao.getCollectionDetailsByUserId(userPublicKey, null, null, null, null);
			if(collectionBeanList != null){
				for(ValouxCollectionBean collectionBean:collectionBeanList){
				CollectionHelper.deleteAllCollectionDetailsOfCollection(valouxCollectionDao, collectionBean);
				
				List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getSharedRequestListByItemId(collectionBean.getVcid(), CommonConstants.SHARED_TYPE_COLLECTION);
				if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
					for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
						valouxCollectionDao.deleteAnyBeanByObject(valouxSharedRequestBean);
					}
				}
				
				}
				return true;
			}
		
		
		LOGGER.debug("CollectionServiceImpl : Exit Method deleteConsumerCollectionAndAllDetails");
		return false;
	}

}
