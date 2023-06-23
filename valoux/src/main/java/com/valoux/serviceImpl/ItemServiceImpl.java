/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.transaction.annotation.Transactional;

import com.valoux.bean.AgentBean;
import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.ItemComponentCertificateBean;
import com.valoux.bean.ItemImageBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.bean.ValouxComponentsImageBean;
import com.valoux.bean.ValouxDiamondBean;
import com.valoux.bean.ValouxDiamondMasterPriceBean;
import com.valoux.bean.ValouxGemstoneBean;
import com.valoux.bean.ValouxInclusionBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.bean.ValouxItemTypeBean;
import com.valoux.bean.ValouxMetalBean;
import com.valoux.bean.ValouxMetalsMasterPriceBean;
import com.valoux.bean.ValouxOriginBean;
import com.valoux.bean.ValouxPearlBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AgentDao;
import com.valoux.dao.AppraisalCollectionDao;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.AppraisalItemsDao;
import com.valoux.dao.CountryDao;
import com.valoux.dao.ItemComponentCertificateDao;
import com.valoux.dao.ItemImageDao;
import com.valoux.dao.LoginDao;
import com.valoux.dao.UserDao;
import com.valoux.dao.ValouxCollectionDao;
import com.valoux.dao.ValouxCollectionImageDao;
import com.valoux.dao.ValouxCollectionItemDao;
import com.valoux.dao.ValouxComponentsImageDao;
import com.valoux.dao.ValouxDiamondDao;
import com.valoux.dao.ValouxDiamondMasterPriceDao;
import com.valoux.dao.ValouxGemstoneDao;
import com.valoux.dao.ValouxInclusionDao;
import com.valoux.dao.ValouxItemComponentDao;
import com.valoux.dao.ValouxItemDao;
import com.valoux.dao.ValouxItemTypeDao;
import com.valoux.dao.ValouxMetalDao;
import com.valoux.dao.ValouxMetalMasterPriceDao;
import com.valoux.dao.ValouxOriginDao;
import com.valoux.dao.ValouxPearlDao;
import com.valoux.dao.ValouxSharedRequestDao;
import com.valoux.helper.ItemComponentHelper;
import com.valoux.helper.ItemHelper;
import com.valoux.helper.UserHelper;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemComponentCertificateModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.ItemPriceModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxComponentsImageModel;
import com.valoux.model.ValouxDiamondModel;
import com.valoux.model.ValouxGemstoneModel;
import com.valoux.model.ValouxInclusionModel;
import com.valoux.model.ValouxItemComponentModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxItemTypeModel;
import com.valoux.model.ValouxMetalModel;
import com.valoux.model.ValouxPearlModel;
import com.valoux.model.ValouxSharedRequestModel;
import com.valoux.service.AppraisalService;
import com.valoux.service.ItemService;
import com.valoux.util.CommonMailUtility;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;
import com.valoux.util.MailApi;
import com.valoux.util.PrepareModels;

/**
 * This <java>class</java> ItemServiceImpl use to perform all our service
 * related logics for the Item.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class ItemServiceImpl implements ItemService {

	private static final Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);

	@Autowired
	ValouxSharedRequestDao valouxSharedRequestDao;
	
	@Autowired
	ValouxCollectionDao valouxCollectionDao;

	@Autowired
	UserDao userDao;

	@Autowired
	AppraisalDao appraisalDao;
	
	@Autowired
	AppraisalService appraisalService;

	@Autowired
	AppraisalItemsDao appraisalItemsDao;
	
	@Autowired
	AppraisalCollectionDao appraisalCollectionDao;
	
	@Autowired
	ValouxCollectionItemDao valouxCollectionItemDao;
	
	@Autowired
	ValouxItemDao valouxItemDao;

	@Autowired
	ValouxDiamondDao valouxDiamondDao;

	@Autowired
	ValouxGemstoneDao valouxGemstoneDao;

	@Autowired
	ValouxPearlDao valouxPearlDao;

	@Autowired
	ValouxMetalDao valouxMetalDao;
	
	@Autowired
	ValouxItemTypeDao valouxItemTypeDao;
	
	@Autowired
	ItemImageDao itemImageDao;
	
	@Autowired
	ValouxComponentsImageDao valouxComponentsImageDao;

	@Autowired
	ValouxCollectionImageDao valouxCollectionImageDao;
	
	@Autowired
	ItemComponentCertificateDao itemComponentCertificateDao;
	
	@Autowired
	ValouxItemComponentDao valouxItemComponentDao;
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	AgentDao  agentDao;
	
	@Autowired
	CountryDao  countryDao;
	
	@Autowired
	ValouxDiamondMasterPriceDao  valouxDiamondMasterPriceDao;
	
	@Autowired
	ValouxMetalMasterPriceDao  valouxMetalMasterPriceDao;
	
	@Autowired
	ValouxOriginDao  valouxOriginDao;
	
	@Autowired
	ValouxInclusionDao  valouxInclusionDao;
	
	/**
	 * This method saves item entity. converts business object to persisting
	 * POJO. calls data access layer to persist converted POJO.
	 */
	@Transactional
	public ValouxItemBean addItem(ValouxItemModel valouxItemModel) throws Exception {
		LOGGER.info("Enter Method addItem of ItemServiceImpl");
		ValouxItemBean itemBean = CommonUserUtility.prepareItemBeanFromItemModel(valouxItemModel);
		valouxItemDao.addItem(itemBean);
		LOGGER.info("Exit Method addItem of ItemServiceImpl");
		return itemBean;
	}

	/**
	 * This method performs get Master Data For Item Type.
	 */
	@Transactional
	public List<ValouxItemTypeModel> getMasterDataForItemType() throws Exception {
		LOGGER.info("Enter Method getMasterDataForItemType of ItemServiceImpl");
		List<ValouxItemTypeBean> itemTypeBeanList = valouxItemTypeDao.getMasterDataForItemType();
		List<ValouxItemTypeModel> itemTypeModelList = new ArrayList<ValouxItemTypeModel>();
		if (itemTypeBeanList != null && itemTypeBeanList.size() > 0) {
			for (ValouxItemTypeBean itemTypeBean : itemTypeBeanList) {
				ValouxItemTypeModel itemTypeModel = CommonUserUtility.convertItemTypeModelIntoBean(itemTypeBean);
				itemTypeModelList.add(itemTypeModel);
			}
		}
		Comparator<ValouxItemTypeModel> comparator = new Comparator<ValouxItemTypeModel>() {
			public int compare(ValouxItemTypeModel c1, ValouxItemTypeModel c2) {
				return c1.getItemType().compareTo(c2.getItemType()); // use your
																		// logic
			}
		};
		Collections.sort(itemTypeModelList, comparator);
		LOGGER.info("Exit Method getMasterDataForItemType of ItemServiceImpl");
		return itemTypeModelList;
	}

	/**
	 * This method performs get Map Item Type.
	 */
	@Transactional
	public Map<Integer, String> getMasterDataForItemTypeMap() throws Exception {
		LOGGER.info("Enter Method getMasterDataForItemTypeMap of ItemServiceImpl");
		List<ValouxItemTypeBean> itemTypeBeanList = valouxItemTypeDao.getMasterDataForItemType();
		Map<Integer, String> itemTypeModelMap = new HashMap<Integer, String>();
		if (itemTypeBeanList != null && itemTypeBeanList.size() > 0) {
			for (ValouxItemTypeBean itemTypeBean : itemTypeBeanList) {
				itemTypeModelMap.put(itemTypeBean.getItemTypeId(), itemTypeBean.getItemType());
			}
		}
		LOGGER.info("Exit Method getMasterDataForItemTypeMap of ItemServiceImpl");
		return itemTypeModelMap;
	}

	/**
	 * This method performs get All Item List.
	 */
	@Transactional
	public List<ValouxItemModel> getAllItemList(String publicKey,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.info("Enter Method getAllItemList of ItemServiceImpl");
		List<ValouxItemBean> itemBeanList = valouxItemDao.getAllItemList(publicKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
		List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
		if (itemBeanList != null && itemBeanList.size() > 0) {
			for (ValouxItemBean itemBean : itemBeanList) {
				ValouxItemModel itemModel = CommonUserUtility.prepareItemModelFromitemBean(itemBean);
				itemModelList.add(itemModel);
			}
		}
		LOGGER.info("Exit Method getAllItemList of ItemServiceImpl");
		return itemModelList;
	}

	/**
	 * This method saves item image.
	 */
	@Transactional
	public void saveItemImage(JSONArray imagejArray, Integer itemId, String rKey) throws Exception {
		LOGGER.info("Enter Method saveItemImage of ItemServiceImpl");
		for (int i = 0; i < imagejArray.length(); i++) {
			JSONObject imageJObject = imagejArray.getJSONObject(i);
			String imageContent = JSONUtility.getSafeString(imageJObject, "itemImageContent");
			String imageName = JSONUtility.getSafeString(imageJObject, "itemImageName");
			Integer maxValue = itemImageDao.getMaximumIndexOfItemImageTable();
			String imagePath;
			imagePath = CommonUtility.saveDocumentInDirectory(imageContent, imageName, "Item_Profile" + maxValue + i,
					itemId);
			ItemImageModel itemImageModel = new ItemImageModel();
			itemImageModel.setCreatedBy(rKey);
			itemImageModel.setCreatedOn(CommonUtility.getDateAndTime());
			itemImageModel.setImageCaption("");
			itemImageModel.setImageType(1);
			itemImageModel.setImageurl(imagePath);
			itemImageModel.setItemId(itemId);
			itemImageModel.setModifiedBy(rKey);
			itemImageModel.setModifiedOn(CommonUtility.getDateAndTime());
			ItemImageBean itemImageBean = new ItemImageBean();
			itemImageBean = CommonUserUtility.prepareItemImageBeanFromModel(itemImageModel);
			itemImageDao.addItemImage(itemImageBean);
		}
		LOGGER.info("Exit Method saveItemImage of ItemServiceImpl");

	}

	/**
	 * This method get the list of item image model
	 */
	@Transactional
	public List<ItemImageModel> getItemImageModelList(Integer itemId) throws Exception {
		LOGGER.info("Enter Method getItemImageModelList of ItemServiceImpl");
		List<ItemImageBean> itemImageBeanList = itemImageDao.getItemImageListByItemId(itemId);
		List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
		if (itemImageBeanList != null && itemImageBeanList.size() > 0) {
			for (ItemImageBean itemImageBean : itemImageBeanList) {
				ItemImageModel itemImageModel = CommonUserUtility.prepareItemImageModelFromBean(itemImageBean);
				itemImageModelList.add(itemImageModel);
			}
		}
		LOGGER.info("Exit Method getItemImageModelList of ItemServiceImpl");
		return itemImageModelList;
	}

	/**
	 * This method save the item receipt
	 */
	@Transactional
	public void saveItemReceipt(String itemReceiptName, String itemReceiptContent, Integer itemId, String rKey)
			throws Exception {
		LOGGER.info("Enter Method saveItemReceipt of ItemServiceImpl");
		String docPath;
		docPath = CommonUtility.saveDocumentInDirectory(itemReceiptContent, itemReceiptName, "Item_Receipt", itemId);
		ItemImageModel itemImageModel = new ItemImageModel();
		itemImageModel.setCreatedBy(rKey);
		itemImageModel.setCreatedOn(CommonUtility.getDateAndTime());
		itemImageModel.setImageCaption("");
		itemImageModel.setImageType(2);
		itemImageModel.setImageurl(docPath);
		itemImageModel.setItemId(itemId);
		itemImageModel.setModifiedBy(rKey);
		itemImageModel.setModifiedOn(CommonUtility.getDateAndTime());
		ItemImageBean itemImageBean = new ItemImageBean();
		itemImageBean = CommonUserUtility.prepareItemImageBeanFromModel(itemImageModel);
		itemImageDao.addItemImage(itemImageBean);
		itemImageModel = CommonUserUtility.prepareItemImageModelFromBean(itemImageBean);
		LOGGER.info("Exit Method saveItemReceipt of ItemServiceImpl");
	}

	/**
	 * This method save the item certificate
	 */
	@Transactional
	public void saveItemCertificate(String itemCertificateName, String itemCertificateContent, Integer itemId,
			String rKey) throws Exception {
		LOGGER.info("Enter Method saveItemCertificate of ItemServiceImpl");
		String docPath;
		docPath = CommonUtility.saveDocumentInDirectory(itemCertificateContent, itemCertificateName,
				"Item_Certificate", itemId);
		ItemImageModel itemImageModel = new ItemImageModel();
		itemImageModel.setCreatedBy(rKey);
		itemImageModel.setCreatedOn(CommonUtility.getDateAndTime());
		itemImageModel.setImageCaption("");
		itemImageModel.setImageType(3);
		itemImageModel.setImageurl(docPath);
		itemImageModel.setItemId(itemId);
		itemImageModel.setModifiedBy(rKey);
		itemImageModel.setModifiedOn(CommonUtility.getDateAndTime());
		ItemImageBean itemImageBean = new ItemImageBean();
		itemImageBean = CommonUserUtility.prepareItemImageBeanFromModel(itemImageModel);
		itemImageDao.addItemImage(itemImageBean);
		itemImageModel = CommonUserUtility.prepareItemImageModelFromBean(itemImageBean);
		LOGGER.info("Exit Method saveItemCertificate of ItemServiceImpl");

	}

	/**
	 * This method get the item detail
	 */
	@Transactional
	public ValouxItemModel updateItemDetail(Integer itemId, String rKey, ValouxItemModel itemModel) throws Exception {
		LOGGER.info("Enter Method updateItemDetail of ItemServiceImpl");
		ValouxItemBean itemBean = valouxItemDao.getItemDetailByItemIdAndRkey(itemId, rKey);
		if (itemBean == null) {
			return null;
		}
		if (itemModel.getDesignerPrice() != null) {
			itemBean.setDesignerPrice(itemModel.getDesignerPrice());
		}
		if (itemModel.getDesignerPriceType() != null) {
			itemBean.setDesignerPriceType(itemModel.getDesignerPriceType());
		}
		if (itemModel.getDesignType() != null) {
			itemBean.setDesignType(itemModel.getDesignType());
		}
		if (itemModel.getGender() != null) {
			itemBean.setGender(itemModel.getGender());
		}
		if (itemModel.getItemTypeIt() != null) {
			ValouxItemTypeBean valouxItemTypeBean = new ValouxItemTypeBean();
			valouxItemTypeBean.setItemTypeId(itemModel.getItemTypeIt());
			itemBean.setValouxItemTypeBean(valouxItemTypeBean);
//			itemBean.setItemTypeIt(itemModel.getItemTypeIt());
		}
		if (itemModel.getModifiedBy() != null) {
			itemBean.setModifiedBy(itemModel.getModifiedBy());
		}
		if (itemModel.getModifiedOn() != null) {
			itemBean.setModifiedOn(itemModel.getModifiedOn());
		}
		if (itemModel.getName() != null) {
			itemBean.setName(itemModel.getName());
		}
		if (itemModel.getQuantity() != null) {
			itemBean.setQuantity(itemModel.getQuantity());
		}
		if (itemModel.getSalesPrice() != null) {
			itemBean.setSalesPrice(itemModel.getSalesPrice());
		}
		if (itemModel.getSalesTax() != null) {
			itemBean.setSalesTax(itemModel.getSalesTax());
		}
		if (itemModel.getsDescription() != null) {
			itemBean.setsDescription(itemModel.getsDescription());
		}
		if (itemModel.getValouxMarketValue() != null) {
			itemBean.setValouxMarketValue(itemModel.getValouxMarketValue());
		}
		if (itemModel.getStoreId() != null) {
			itemBean.setStoreId(itemModel.getStoreId());
		}
		valouxItemDao.updateItemdetail(itemBean);
		itemModel = CommonUserUtility.prepareItemModelFromitemBean(itemBean);
		LOGGER.info("Exit Method updateItemDetail of ItemServiceImpl");
		return itemModel;
	}

	/**
	 * This method provide item details by item id.
	 */
	@Transactional
	public ValouxItemModel getValouxItemDetailsById(Integer itemId) throws Exception {
		LOGGER.info("Enter Method getValouxItemDetailsById of ItemServiceImpl");
		Map<Integer, String> itemTypeMap = new HashMap<Integer, String>();
		ValouxItemModel itemModel = new ValouxItemModel();
		itemTypeMap = getMasterDataForItemTypeMap();
		ValouxItemBean itemBean = valouxItemDao.getValouxItemDetailsById(itemId);

		if (itemBean != null) {
			itemModel.setItemId(itemBean.getItemId());
			itemModel.setDesignerPrice(itemBean.getDesignerPrice());
			itemModel.setDesignerPriceType(itemBean.getDesignerPriceType());
			itemModel.setDesignType(itemBean.getDesignType());
			itemModel.setGender(itemBean.getGender());
			itemModel.setItemTypeIt(itemBean.getValouxItemTypeBean().getItemTypeId());
			itemModel.setName(itemBean.getName());
			itemModel.setQuantity(itemBean.getQuantity());
			itemModel.setSalesPrice(itemBean.getSalesPrice());
			itemModel.setSalesTax(itemBean.getSalesTax());
			itemModel.setsDescription(itemBean.getsDescription());
			itemModel.setStoreId(itemBean.getStoreId());
			itemModel.setValouxMarketValue(itemBean.getValouxMarketValue());
			itemModel.setCreatedBy(itemBean.getCreatedBy());
			itemModel.setCreatedOn(itemBean.getCreatedOn());
			itemModel.setModifiedBy(itemBean.getModifiedBy());
			itemModel.setModifiedOn(itemBean.getModifiedOn());
			itemModel.setItemTypeName(itemTypeMap.get(itemBean.getValouxItemTypeBean().getItemTypeId()));
			itemModel.setrKey(itemBean.getrKey());
			itemModel.setItemStatus(itemBean.getItemStatus());
			itemModel.setLastAppraisaedPrice(itemBean.getLastAppraisaedPrice());
			itemModel.setLastAppraisedDate(itemBean.getLastAppraisedDate());
			itemModel.setMarketValue(itemBean.getMarketValue());
			itemModel.setFinalPrice(itemBean.getFinalPrice());
		}
		LOGGER.info("Exit Method getValouxItemDetailsById of ItemServiceImpl");
		return itemModel;
	}

	/**
	 * This method update item image profile
	 */
	@Transactional
	public void updateItemImageDocument(JSONArray imagesjArray, String itemReceiptContent, String itemReceiptName,
			String itemCertificateName, String itemCertificateContent, Integer itemId, String rKey) throws Exception {
		LOGGER.info("Enter Method updateItemImageDocument of ItemServiceImpl");
		if (imagesjArray != null) {
			saveItemImage(imagesjArray, itemId, rKey);
		}
		if (itemCertificateContent != null && itemCertificateName != null) {
			saveItemCertificate(itemCertificateName, itemCertificateContent, itemId, rKey);
		}
		if (itemReceiptContent != null && itemReceiptName != null) {
			saveItemReceipt(itemReceiptName, itemReceiptContent, itemId, rKey);
		}
		LOGGER.info("Exit Method updateItemImageDocument of ItemServiceImpl");
	}

	/**
	 * This method delete the item document
	 */
	@Transactional
	public Boolean deleteItemDocument(Integer itemId, Integer imageId) throws Exception {
		LOGGER.info("Enter Method deleteItemDocument of ItemServiceImpl");
		ItemImageBean itemImageBean = itemImageDao.getItemImageListByItemIdAndImageId(itemId, imageId);
		if (itemImageBean != null) {
			itemImageDao.deleteItemImages(itemImageBean);
			CommonUtility.deleteDocumentInDirectory(itemImageBean.getImageurl());
			return true;
		}
		LOGGER.info("Exit Method deleteItemDocument of ItemServiceImpl");
		return false;
	}

	/**
	 * This method give all items List not in a collection.
	 */
	@Transactional
	public List<ValouxItemModel> getConsumerItemsNotInCollection(String publicKey, Integer collectionId)
			throws Exception {
		LOGGER.info("Enter Method getConsumerItemsNotInCollection of ItemServiceImpl");
		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsById(collectionId);

		List<Integer> itemList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (ValouxCollectionItemBean collectionItemBean : collectionBeans) {
				itemList.add(collectionItemBean.getValouxItemBean().getItemId());
			}
		}

		List<ValouxItemBean> itemBeanList = valouxItemDao.getConsumerItemsNotInCollection(publicKey, itemList.toArray());
		List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
		if (itemBeanList != null && itemBeanList.size() > 0) {
			for (ValouxItemBean itemBean : itemBeanList) {
				ValouxItemModel itemModel = CommonUserUtility.prepareItemModelFromitemBean(itemBean);
				itemModelList.add(itemModel);
			}
		}
		LOGGER.info("Exit Method getConsumerItemsNotInCollection of ItemServiceImpl");
		return itemModelList;
	}

	/**
	 * This method get the number of item associated with the store
	 */
	@Transactional
	public Integer getNumberOfItemAssociatedWithStore(Integer storeId) throws Exception {
		LOGGER.info("Enter Method getNumberOfItemAssociatedWithStore of ItemServiceImpl");
		List<ValouxItemBean> itemBeanList = valouxItemDao.getItemDetailByStoreId(storeId);
		Integer count = 0;
		if (itemBeanList != null && itemBeanList.size() > 0) {
			count = itemBeanList.size();
		}
		LOGGER.info("Exit Method getNumberOfItemAssociatedWithStore of ItemServiceImpl");
		return count;
	}
	
	/**
	 * This method get the list of item associated with the store
	 */
	@Transactional
	public List<ValouxItemBean> getItemAssociatedWithStore(Integer storeId) throws Exception {
		LOGGER.info("Enter Method getNumberOfItemAssociatedWithStore of ItemServiceImpl");
		List<ValouxItemBean> itemBeanList = valouxItemDao.getItemDetailByStoreId(storeId);
		LOGGER.info("Exit Method getNumberOfItemAssociatedWithStore of ItemServiceImpl");
		return itemBeanList;
	}

	/**
	 * This method checks whether item name already exist for user
	 */
	@Transactional
	public Boolean checkItemNameExistForUser(String publicKey, String itemName, Integer itemId) throws Exception {
		LOGGER.info("Enter Method checkItemNameExistForUser of ItemServiceImpl");
		List<ValouxItemBean> itemBeanList = valouxItemDao.checkItemNameExistForUser(publicKey, itemName);
		if (itemBeanList != null && itemBeanList.size() > 0) {
			if (itemBeanList.get(0).getItemId().equals(itemId)) {
				return false;
			}
			return true;
		}
		LOGGER.info("Exit Method checkItemNameExistForUser of ItemServiceImpl");
		return false;

	}

	/**
	 * This method will save item components
	 */
	@Transactional
	public List<ValouxItemComponentBean> addValouxItemComponents(
			List<ValouxItemComponentModel> valouxItemComponentModelList) throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method addValouxItemComponents ");

		List<ValouxItemComponentBean> componentBeanList = new ArrayList<ValouxItemComponentBean>();

		if (valouxItemComponentModelList != null && valouxItemComponentModelList.size() > 0) {

			for (ValouxItemComponentModel componentModel : valouxItemComponentModelList) {

				ValouxItemComponentBean componentBean = valouxItemComponentDao.getItemComponentByItemAndComponentId(componentModel.getItemId(), componentModel.getVicid());
				
				if(componentBean == null){
					componentBean = new ValouxItemComponentBean();
					componentBean.setCreatedBy(componentModel.getCreatedBy());
					componentBean.setCreatedOn(componentModel.getCreatedOn());
				}

				componentBean.setVicid(componentModel.getVicid());
				componentBean.setComponentsType(componentModel.getComponentsType());
//				componentBean.setItemId(componentModel.getItemId());
				ValouxItemBean valouxItemBean = new ValouxItemBean();
				valouxItemBean.setItemId(componentModel.getItemId());
				componentBean.setValouxItem(valouxItemBean);
				componentBean.setName(componentModel.getName());
				componentBean.setQuantity(componentModel.getQuantity());
				componentBean.setVicStatus(componentModel.getVicStatus());
				componentBean.setModifiedBy(componentModel.getModifiedBy());
				componentBean.setModifiedOn(componentModel.getModifiedOn());
				
				valouxItemComponentDao.saveValouxItemComponent(componentBean);
//				componentBeanList.add(componentBean);
			}

//			componentBeanList = valouxItemComponentDao.saveValouxItemComponents(componentBeanList);
		}

		LOGGER.debug("ItemServiceImpl : Exit Method addValouxItemComponents");
		return componentBeanList;
	}

	/**
	 * This method will fetch item components
	 */
	@Transactional
	public List<ValouxItemComponentModel> getComponentsByItemId(Integer itemId) throws Exception {

		LOGGER.info("ItemServiceImpl : Enter Method getComponentsByItemId");

		List<ValouxItemComponentModel> componentModels = new ArrayList<ValouxItemComponentModel>();
		List<ValouxItemComponentBean> componentBeans = valouxItemComponentDao.getComponentsByItemId(itemId);

		if (componentBeans != null && componentBeans.size() > 0) {

			for (ValouxItemComponentBean valouxComponentItemBean : componentBeans) {

				ValouxItemComponentModel itemModel = new ValouxItemComponentModel();
				itemModel.setItemId(valouxComponentItemBean.getValouxItem().getItemId());
				itemModel.setCreatedBy(valouxComponentItemBean.getCreatedBy());
				itemModel.setCreatedOn(valouxComponentItemBean.getCreatedOn());
				itemModel.setModifiedBy(valouxComponentItemBean.getModifiedBy());
				itemModel.setModifiedOn(valouxComponentItemBean.getModifiedOn());
				itemModel.setName(valouxComponentItemBean.getName());
				itemModel.setComponentsType(valouxComponentItemBean.getComponentsType());
				itemModel.setVicid(valouxComponentItemBean.getVicid());
				itemModel.setQuantity(valouxComponentItemBean.getQuantity());
				itemModel.setVicStatus(valouxComponentItemBean.getVicStatus());

				componentModels.add(itemModel);
			}
		}

		LOGGER.info("ItemServiceImpl : Exit Method getComponentsByItemId");
		return componentModels;
	}

	/**
	 * This method helps in delete component of item
	 */
	@Transactional
	public ValouxItemComponentBean deleteComponentFromItem(Integer itemId, Integer componentId) throws Exception {
		LOGGER.info("ItemServiceImpl : Enter Method deleteComponentFromItem");

		ValouxItemComponentBean componentBean = valouxItemComponentDao.getItemComponentByItemAndComponentId(itemId, componentId);
		if (componentBean != null) {
			
			deleteComponentImagesByComponentId(componentId);
			
			valouxItemComponentDao.deleteValouxItemComponentBean(componentBean);
		}

		LOGGER.info("ItemServiceImpl : Exit Method deleteComponentFromItem");
		return componentBean;
	}

	/**
	 * This method first send email to user to which item is share and then make
	 * entry and request table
	 */
	@Transactional
	public List<ValouxSharedRequestModel> createSharedRequestAndSendEmail(ValouxSharedRequestModel sharedRequestModel,
			String[] sharedToArray, String[] sharedToEmailArray) throws Exception {
		LOGGER.info("ItemServiceImpl : Enter Method createSharedRequestAndSendEmail");
		List<ValouxSharedRequestModel> sharedrequestModelList = new ArrayList<ValouxSharedRequestModel>();
		if (sharedToEmailArray != null && sharedToEmailArray.length>0 && !sharedToEmailArray[0].isEmpty()) {
			for (int i = 0; i < sharedToEmailArray.length; i++) {
				LoginBean loginBean = loginDao.getLoginBeanByEmailid(sharedToEmailArray[i]);
				if (loginBean != null) {
					UserBean userBean = userDao
							.getConsumerDetailByRKey((loginBean.getPrivateKey()));
					if (userBean != null) {
						sharedRequestModel.setSharedItemPermission(1);
					} else {
						AgentBean agentBean = agentDao.getAgentDetailByRKey((loginBean
								.getPrivateKey()));
						if (agentBean != null) {
							sharedRequestModel.setSharedItemPermission(2);
						}
					}
					sharedRequestModel.setIsRegisteredUser(1);
					sharedRequestModel.setSharedTo((loginBean.getPrivateKey()));
					sharedRequestModel.setSharedToEmail(loginBean.getUserName());
					String from = CommonConstants.SIGN_UP_MAIL;
					StringBuffer sub = new StringBuffer();
					sub.append(CommonConstants.ITEM_SHARED_BODY);
					String body = CommonMailUtility.getSharedMailContentForRegisteredUser(loginBean.getFirstName()+" "+loginBean.getLastName());
					if(!(sharedRequestModel.getCreatedBy().equals(sharedRequestModel.getSharedTo())) ){
						if(!(sharedRequestModel.getSharedBy().equals(sharedRequestModel.getSharedTo()))){
					MailApi.SendMail(loginBean.getUserName(), from, sub.toString(), body, 1);
						}
					}
				} else {
					sharedRequestModel.setSharedItemPermission(1);
					sharedRequestModel.setIsRegisteredUser(2);
					sharedRequestModel.setSharedTo(null);
					sharedRequestModel.setSharedToEmail(sharedToEmailArray[i]);
					String from = CommonConstants.SIGN_UP_MAIL;
					StringBuffer sub = new StringBuffer();
					sub.append(CommonConstants.ITEM_SHARED_BODY);

					String body = CommonMailUtility.getSharedMailContentForUnRegisteredUser();
					if(!(sharedRequestModel.getCreatedBy().equals(sharedRequestModel.getSharedTo())) ){
						if(!(sharedRequestModel.getSharedBy().equals(sharedRequestModel.getSharedTo()))){
							MailApi.SendMail(sharedRequestModel.getSharedToEmail(), from, sub.toString(), body, 1);
						}
					}
				}
				ValouxSharedRequestBean sharedRequestBean = CommonUserUtility
						.converSharedRequestModelIntoBean(sharedRequestModel);
				ValouxSharedRequestBean sharedRequest = valouxSharedRequestDao.getSharedRequestBeanByItemIdSharedToEmailAndSharedTo(
						sharedRequestBean.getSharedItemId(), sharedRequestBean.getSharedToEmail(),
						sharedRequestBean.getSharedTo(), sharedRequestBean.getSharedItemType());
				if (sharedRequest == null) {
					if(!(sharedRequestModel.getSharedBy().equals(sharedRequestModel.getSharedTo()))){
					valouxSharedRequestDao.saveSharedRequest(sharedRequestBean);
					if (sharedRequestBean.getSharedRequestId() != null) {
						sharedrequestModelList.add(CommonUserUtility
								.converSharedRequestBeanIntoModel(sharedRequestBean));
					}
					}
				} else{ if (sharedRequest.getStatus() == 2) {
					sharedRequest.setStatus(1);
					sharedRequest.setApproveStatus(1);
				}if(sharedRequest.getApproveStatus()==3){
					sharedRequest.setApproveStatus(1);
				}
					valouxSharedRequestDao.updateSharedRequestBean(sharedRequest);
				}
			}
		}
		if (sharedToArray != null && sharedToArray.length>0 && !sharedToArray[0].isEmpty()) {
			for (int i = 0; i < sharedToArray.length; i++) {
				LoginBean loginBean = loginDao.getLoginDetailByPKey((sharedToArray[i])); // need
																												// to
																												// work(Right
																												// now
																												// removing
																												// decrypt
																												// code)
				// LoginBean loginBean =
				// userDao.getLoginDetailByPKey(sharedToArray[i]);
				if (loginBean != null) {
					UserBean userBean = userDao.getConsumerDetailByRKey(sharedToArray[i]);
					if (userBean != null) {
						sharedRequestModel.setSharedItemPermission(1);
					} else {
						AgentBean agentBean = agentDao.getAgentDetailByRKey(sharedToArray[i]);
						if (agentBean != null) {
							sharedRequestModel.setSharedItemPermission(2);

						}
					}
					sharedRequestModel.setIsRegisteredUser(1);
					sharedRequestModel.setSharedTo(sharedToArray[i]);
					sharedRequestModel.setSharedToEmail(loginBean.getUserName());
					String from = CommonConstants.SIGN_UP_MAIL;
					StringBuffer sub = new StringBuffer();
					sub.append(CommonConstants.ITEM_SHARED_BODY);

					String body = CommonMailUtility.getSharedMailContentForRegisteredUser(loginBean.getFirstName()+" "+loginBean.getLastName());
					if(!(sharedRequestModel.getCreatedBy().equals(sharedRequestModel.getSharedTo()))){
						if(!(sharedRequestModel.getSharedBy().equals(sharedRequestModel.getSharedTo()))){
					MailApi.SendMail(loginBean.getUserName(), from, sub.toString(), body, 1);
						}
					}
				}
				ValouxSharedRequestBean sharedRequestBean = CommonUserUtility
						.converSharedRequestModelIntoBean(sharedRequestModel);
				ValouxSharedRequestBean sharedRequest = valouxSharedRequestDao.getSharedRequestBeanByItemIdSharedToEmailAndSharedTo(
						sharedRequestBean.getSharedItemId(), sharedRequestBean.getSharedToEmail(),
						sharedRequestBean.getSharedTo(), sharedRequestBean.getSharedItemType());
				if (sharedRequest == null) {
					if(!(sharedRequestModel.getSharedBy().equals(sharedRequestModel.getSharedTo()))){
					valouxSharedRequestDao.saveSharedRequest(sharedRequestBean);
					}
					if (sharedRequestBean.getSharedRequestId() != null) {
						sharedrequestModelList.add(CommonUserUtility
								.converSharedRequestBeanIntoModel(sharedRequestBean));
					}
				} else {if (sharedRequest.getStatus() == 2) {
					sharedRequest.setStatus(1);
					sharedRequest.setApproveStatus(1);
				}  if (sharedRequest.getApproveStatus() == 3) {
					sharedRequest.setStatus(1);
					sharedRequest.setApproveStatus(1);
				}
				valouxSharedRequestDao.updateSharedRequestBean(sharedRequest);
				}
			}
		}
		LOGGER.info("ItemServiceImpl : Exit Method createSharedRequestAndSendEmail");
		return sharedrequestModelList;
	}
	
	/**
	 * This method send email to consumer that item/appraisal/collection is added for him
	 */
	@Transactional
	public void sendMailToConsumerForItemCollectionAppraisalIdAdded(Integer itemType,String rKey) throws Exception{
		LOGGER.info("ItemServiceImpl : Enter Method sendMailToConsumerForItemCollectionAppraisalIdAdded");
		LoginBean loginBean = loginDao.getLoginDetailByPKey((rKey));
		String body = "";
		StringBuffer sub = new StringBuffer();
		String from = CommonConstants.SIGN_UP_MAIL;
		
		if(itemType.equals(1)){
			sub.append(CommonConstants.ITEM_ADDED_BODY);
			body = CommonMailUtility.getMailContenForNewItemForConsumer(loginBean.getFirstName()+" "+loginBean.getLastName());
		}
		if(itemType.equals(2)){
			sub.append(CommonConstants.COLLECTION_ADDED_BODY);
			body = CommonMailUtility.getMailContenForNewCollectionForConsumer(loginBean.getFirstName()+" "+loginBean.getLastName());
		}
		if(itemType.equals(3)){
			sub.append(CommonConstants.APPRAISAL_ADDED_BODY);
			body = CommonMailUtility.getMailContenForNewAppraisalForConsumer(loginBean.getFirstName()+" "+loginBean.getLastName());
		}
		MailApi.SendMail(loginBean.getUserName(), from, sub.toString(), body, 1);
		LOGGER.info("ItemServiceImpl : Exit Method sendMailToConsumerForItemCollectionAppraisalIdAdded");
	}
	
	/**
	 * This method first send email to user to which item is unshare and then
	 * inactivate it from db
	 */
	@Transactional
	public void unShareItemAndSendEmail(ValouxSharedRequestModel sharedRequestModel, JSONArray shareToArray)
			throws Exception {
		LOGGER.info("ItemServiceImpl : Enter Method unShareItemAndSendEmail");
		String from = CommonConstants.SIGN_UP_MAIL;
		StringBuffer sub = new StringBuffer();
		sub.append(CommonConstants.ITEM_UNSHARED_BODY);
		String body = "";
		if (shareToArray == null) {
			List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestListBySharedByAndItemType(
					sharedRequestModel.getSharedBy(), sharedRequestModel.getSharedItemType(),
					sharedRequestModel.getSharedItemId(), sharedRequestModel.getSharedTo(),null,null,null,null);

			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean shareRequestbean : sharedRequestBeanList) {
					shareRequestbean.setModifiedBy(sharedRequestModel.getSharedBy());
					shareRequestbean.setModifiedOn(CommonUtility.getDateAndTime());
					shareRequestbean.setStatus(2);
					if (shareRequestbean.getSharedTo() == null) {
						body = CommonMailUtility.getUnSharedMailContentForUnRegisteredUser();
					} else {
						LoginBean loginBean = loginDao.getLoginDetailByPKey((shareRequestbean.getSharedTo()));
						body = CommonMailUtility.getUnSharedMailContentForRegisteredUser(loginBean.getFirstName()+" "+loginBean.getLastName());
					}
					MailApi.SendMail(shareRequestbean.getSharedToEmail(), from, sub.toString(), body, 1);
					valouxSharedRequestDao.updateSharedRequestBean(shareRequestbean);
				}
			}
		}
		if (shareToArray != null && shareToArray.length() > 0) {
			for (int i = 0; i < shareToArray.length(); i++) {
				List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao
						.getSharedRequestListBySharedByAndItemType(sharedRequestModel.getSharedBy(),
								sharedRequestModel.getSharedItemType(), sharedRequestModel.getSharedItemId(),
								shareToArray.getString(i),null,null,null,null);

				if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
					for (ValouxSharedRequestBean shareRequestbean : sharedRequestBeanList) {
						shareRequestbean.setModifiedBy(sharedRequestModel.getSharedBy());
						shareRequestbean.setModifiedOn(CommonUtility.getDateAndTime());
						shareRequestbean.setStatus(2);
						if (shareRequestbean.getSharedTo() == null) {
							body = CommonMailUtility.getUnSharedMailContentForUnRegisteredUser();
						} else {
							LoginBean loginBean = loginDao.getLoginDetailByPKey((shareRequestbean.getSharedTo()));
							body = CommonMailUtility.getUnSharedMailContentForRegisteredUser(loginBean.getFirstName()+" "+loginBean.getLastName());
						}
						MailApi.SendMail(shareRequestbean.getSharedToEmail(), from, sub.toString(), body, 1);
						valouxSharedRequestDao.updateSharedRequestBean(shareRequestbean);
					}
				}
			}
		}
		LOGGER.info("ItemServiceImpl : Exit Method unShareItemAndSendEmail");
	}

	/**
	 * This method helps in adding item component diamond property
	 */
	@Transactional
	public void addValouxComponentDiamondProperty(ValouxDiamondModel diamondModel, String requestType) throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method addValouxComponentDiamondProperty ");

		if (diamondModel != null) {
			
			ValouxDiamondBean diamondBean = valouxDiamondDao.getComponentDiamondBeanByItemAndComponentId(diamondModel.getItemId(),
					diamondModel.getItemComponentId());

			if (diamondBean == null) {
				diamondBean = new ValouxDiamondBean();
				if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getCreatedBy())) {
					diamondBean.setCreatedBy(diamondModel.getCreatedBy());
				}
				if (CommonUserUtility.paparameterNullCheckObject(diamondModel.getCreatedOn())) {
					diamondBean.setCreatedOn(diamondModel.getCreatedOn());
				}
			}

			if (CommonUserUtility.paparameterNullCheckObject(diamondModel.getClarityId())) {
				diamondBean.setClarityId(diamondModel.getClarityId());
			}
			diamondBean.setColor(diamondModel.getColor());
			diamondBean.setCut(diamondModel.getCut());
			diamondBean.setCutlet(diamondModel.getCutlet());
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getDepth())) {
				diamondBean.setDepth(Double.parseDouble(diamondModel.getDepth()));
			} else {
				diamondBean.setDepth(null);
			}
			
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getDepthPercentage())) {
				diamondBean.setDepthPercentage(Double.parseDouble(diamondModel.getDepthPercentage()));
			} else {
				diamondBean.setDepthPercentage(null);
			}
			
			diamondBean.setFancyColor(diamondModel.getFancyColor());
			diamondBean.setFluorescence(diamondModel.getFluorescence());
			diamondBean.setGirdleThicknessDescription(diamondModel.getGirdleThicknessDescription());
			diamondBean.setDiamondHeight(diamondModel.getDiamondHeight());
//			diamondBean.setItemComponentId(diamondModel.getItemComponentId());
			ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
			valouxItemComponent.setVicid(diamondModel.getItemComponentId());
			diamondBean.setValouxItemComponent(valouxItemComponent);
			
//			diamondBean.setItemId(diamondModel.getItemId());
			ValouxItemBean valouxItem = new ValouxItemBean();
			valouxItem.setItemId(diamondModel.getItemId());
			diamondBean.setValouxItem(valouxItem);
			
			diamondBean.setDiamondLength(diamondModel.getDiamondLength());
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getLengthWidthRatio())) {
				diamondBean.setLengthWidthRatio(Double.parseDouble(diamondModel.getLengthWidthRatio()));
			} else {
				diamondBean.setLengthWidthRatio(null);
			}
			
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getMarketValue())) {
				diamondBean.setMarketValue(Double.parseDouble(diamondModel.getMarketValue()));
			} else {
				diamondBean.setMarketValue(null);
			}
			
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getMaxDiameter())) {
				diamondBean.setMaxDiameter(Double.parseDouble(diamondModel.getMaxDiameter()));
			} else {
				diamondBean.setMaxDiameter(null);
			}
			
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getMinDiameter())) {
				diamondBean.setMinDiameter(Double.parseDouble(diamondModel.getMinDiameter()));
			} else {
				diamondBean.setMinDiameter(null);
			}
			
			diamondBean.setModifiedBy(diamondModel.getModifiedBy());
			diamondBean.setModifiedOn(diamondModel.getModifiedOn());
			diamondBean.setPlacement(diamondModel.getPlacement());
			diamondBean.setPolish(diamondModel.getPolish());
			diamondBean.setSecondaryHue(diamondModel.getSecondaryHue());
			diamondBean.setShape(diamondModel.getShape());
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getSingleWeight())) {
				diamondBean.setSingleWeight(Double.parseDouble(diamondModel.getSingleWeight()));
			} else {
				diamondBean.setSingleWeight(null);
			}
			
			diamondBean.setStatus(diamondModel.getStatus().byteValue());
			diamondBean.setSymmetry(diamondModel.getSymmetry());
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getTablePercentage())) {
				diamondBean.setTablePercentage(Double.parseDouble(diamondModel.getTablePercentage()));
			} else {
				diamondBean.setTablePercentage(null);
			}
			
			diamondBean.setThickness(diamondModel.getThickness());
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getTotalWeight())) {
				diamondBean.setTotalWeight(Double.parseDouble(diamondModel.getTotalWeight()));
			} else {
				diamondBean.setTotalWeight(null);
			}
			
			diamondBean.setWeightMeasure(diamondModel.getWeightMeasure());
			
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getPwidth())) {
				diamondBean.setDiamondWidth(Double.parseDouble(diamondModel.getPwidth()));
			} else {
				diamondBean.setDiamondWidth(null);
			}
			
			valouxDiamondDao.addValouxComponentDiamondProperty(diamondBean, requestType);

		}
		LOGGER.debug("ItemServiceImpl : Exit Method addValouxComponentDiamondProperty ");
	}

	/**
	 * This method helps in adding item component gemstone property
	 */
	@Transactional
	public void addValouxComponentGemstoneProperty(ValouxGemstoneModel gemstoneModel) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method addValouxComponentGemstoneProperty ");
		
		if (gemstoneModel != null) {
			
			ValouxGemstoneBean gemstoneBean = valouxGemstoneDao.getComponentGemstoneBeanByItemAndComponentId(gemstoneModel.getItemId(),
					gemstoneModel.getItemComponentId());

			if (gemstoneBean == null) {
				gemstoneBean = new ValouxGemstoneBean();
					gemstoneBean.setCreatedBy(gemstoneModel.getCreatedBy());
					gemstoneBean.setCreatedOn(gemstoneModel.getCreatedOn());
			}
			
			gemstoneBean.setCut(gemstoneModel.getCut());
			gemstoneBean.setEnhancement(gemstoneModel.getEnhancement());
				
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneModel.getGemstonesType())) {
				gemstoneBean.setGemstonesType(gemstoneModel.getGemstonesType());
			}
//			gemstoneBean.setItemComponentId(gemstoneModel.getItemComponentId());
			ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
			valouxItemComponent.setVicid(gemstoneModel.getItemComponentId());
			gemstoneBean.setValouxItemComponent(valouxItemComponent);
			
//			gemstoneBean.setItemId(gemstoneModel.getItemId());
			ValouxItemBean valouxItem = new ValouxItemBean();
			valouxItem.setItemId(gemstoneModel.getItemId());
			gemstoneBean.setValouxItem(valouxItem);
			
			if (CommonUserUtility.paparameterNullCheckStringObject(gemstoneModel.getMeasurements())) {
				gemstoneBean.setMeasurements(gemstoneModel.getMeasurements());
			} else {
				gemstoneBean.setMeasurements(null);
			}
			
			gemstoneBean.setModifiedBy(gemstoneModel.getModifiedBy());
			gemstoneBean.setModifiedOn(gemstoneModel.getModifiedOn());
			gemstoneBean.setOrigin(gemstoneModel.getOrigin());
			gemstoneBean.setPlacement(gemstoneModel.getPlacement());
			gemstoneBean.setShape(gemstoneModel.getShape());
			gemstoneBean.setStatus(gemstoneModel.getStatus().byteValue());
			
			if (CommonUserUtility.paparameterNullCheckStringObject(gemstoneModel.getWeight())) {
				gemstoneBean.setWeight(Double.parseDouble(gemstoneModel.getWeight()));
			} else {
				gemstoneBean.setWeight(null);
			}
			
//			gemstoneBean.setInternalInclusions(gemstoneModel.getInternalInclusions());
//			gemstoneBean.setExternalInclusions(gemstoneModel.getExternalInclusions());
			
			valouxGemstoneDao.addValouxComponentGemstoneProperty(gemstoneBean);
		}

		LOGGER.debug("ItemServiceImpl : Exit Method addValouxComponentGemstoneProperty ");

	}

	/**
	 * This method helps in adding item component pearl property
	 */
	@Transactional
	public void addValouxComponentPearlProperty(ValouxPearlModel pearlModel) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method addValouxComponentPearlProperty.");
		
		if (pearlModel != null) {
			
			ValouxPearlBean pearlBean = valouxPearlDao.getComponentPearlBeanByItemAndComponentId(pearlModel.getItemId(),
					pearlModel.getItemComponentId());

			if (pearlBean == null) {
				pearlBean = new ValouxPearlBean();
				pearlBean.setCreatedBy(pearlModel.getCreatedBy());
				pearlBean.setCreatedOn(pearlModel.getCreatedOn());
			}
			pearlBean.setBlemish(pearlModel.getBlemish());
			pearlBean.setColor(pearlModel.getColor());
			pearlBean.setComposition(pearlModel.getComposition());
			pearlBean.setDrilled(pearlModel.getDrilled());
			pearlBean.setEnhancements(pearlModel.getEnhancements());
//			pearlBean.setItemComponentId(pearlModel.getItemComponentId());
			ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
			valouxItemComponent.setVicid(pearlModel.getItemComponentId());
			pearlBean.setValouxItemComponent(valouxItemComponent);
			
//			pearlBean.setItemId(pearlModel.getItemId());
			ValouxItemBean valouxItem = new ValouxItemBean();
			valouxItem.setItemId(pearlModel.getItemId());
			pearlBean.setValouxItem(valouxItem);
			
			pearlBean.setPearlsLength(pearlModel.getPearlsLength());
			pearlBean.setLuster(pearlModel.getLuster());
			pearlBean.setMatching(pearlModel.getMatching());
			pearlBean.setPearlsMax(pearlModel.getPearlsMax());
			if (CommonUserUtility.paparameterNullCheckStringObject(pearlModel.getMeasurements())) {
				pearlBean.setMeasurements(pearlModel.getMeasurements());
			} else {
				pearlBean.setMeasurements(null);
			}
			
			pearlBean.setPearlsMin(pearlModel.getPearlsMin());
			pearlBean.setModifiedBy(pearlModel.getModifiedBy());
			pearlBean.setModifiedOn(pearlModel.getModifiedOn());
			pearlBean.setOrigin(pearlModel.getOrigin());
			pearlBean.setPearlType(pearlModel.getPearlType());
			pearlBean.setShape(pearlModel.getShape());
			pearlBean.setStatus(pearlModel.getStatus().byteValue());
			pearlBean.setPearlsStyle(pearlModel.getPstyle());
			pearlBean.setStyleUserEntered(pearlModel.getStyleUserEntered());
			
			if (CommonUserUtility.paparameterNullCheckStringObject(pearlModel.getWeight())) {
				pearlBean.setWeight(Double.parseDouble(pearlModel.getWeight()));
			}  else {
				pearlBean.setWeight(null);
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(pearlModel.getAppraisedValue())) {
				pearlBean.setAppraisedValue(Double.parseDouble(pearlModel.getAppraisedValue()));
			} else {
				pearlBean.setAppraisedValue(null);
			}
			valouxPearlDao.addValouxComponentPearlProperty(pearlBean);
		}
		LOGGER.debug("ItemServiceImpl : Exit Method addValouxComponentPearlProperty.");
	}

	/**
	 * This method helps in adding item component metal property
	 */
	@Transactional
	public void addValouxComponentMetalProperty(ValouxMetalModel metalModel) throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method addValouxComponentMetalProperty.");
		
		if (metalModel != null) {
			
			ValouxMetalBean metalBean = valouxMetalDao.getComponentMetalBeanByItemAndComponentId(metalModel.getItemId(),
					metalModel.getItemComponentId());

			if (metalBean == null) {
				metalBean = new ValouxMetalBean();
				metalBean.setCreatedBy(metalModel.getCreatedBy());
				metalBean.setCreatedOn(metalModel.getCreatedOn());
			}
			
			metalBean.setColor(metalModel.getColor());
			
			ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
			valouxItemComponent.setVicid(metalModel.getItemComponentId());
			metalBean.setValouxItemComponent(valouxItemComponent);
			
			ValouxItemBean valouxItem = new ValouxItemBean();
			valouxItem.setItemId(metalModel.getItemId());
			metalBean.setValouxItem(valouxItem);
			
			if (CommonUserUtility.paparameterNullCheckStringObject(metalModel.getMarketValue())) {
				metalBean.setMarketValue(Double.parseDouble(metalModel.getMarketValue()));
			} else {
				metalBean.setMarketValue(null);
			}
			
			if (CommonUserUtility.paparameterNullCheckStringObject(metalModel.getMeasurements())) {
				 metalBean.setMeasurements(metalModel.getMeasurements());
			} else {
				metalBean.setMeasurements(null);
			}
			
			metalBean.setMetalsType(metalModel.getMetalsType());
			
			if (CommonUserUtility.paparameterNullCheckStringObject(metalModel.getModifiedBy())) {
				metalBean.setModifiedBy(metalModel.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalModel.getModifiedOn())) {
				metalBean.setModifiedOn(metalModel.getModifiedOn());
			}
			
			metalBean.setPurity(metalModel.getPurity());
			
			metalBean.setStatus(metalModel.getStatus().byteValue());
			
			metalBean.setTypeDetermined(metalModel.getTypeDetermined());
			metalBean.setTypeSpecified(metalModel.getTypeSpecified());
			
			if (CommonUserUtility.paparameterNullCheckStringObject(metalModel.getWeight())) {
				metalBean.setWeight(Double.parseDouble(metalModel.getWeight()));
			} else {
				metalBean.setWeight(null);
			}
			
			if (CommonUserUtility.paparameterNullCheckStringObject(metalModel.getAppraisedValue())) {
				metalBean.setAppraisedValue(Double.parseDouble(metalModel.getAppraisedValue()));
			} else {
				metalBean.setAppraisedValue(null);
			}
			valouxMetalDao.addValouxComponentMetalProperty(metalBean);
		}
		LOGGER.debug("ItemServiceImpl : Exit Method addValouxComponentMetalProperty.");
	}

	/**
	 * This method helps in adding item component images
	 */
	@Transactional
	public void addValouxComponentsImageDetails(List<ValouxComponentsImageModel> valouxComponentsImageModel)
			throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method addValouxComponentsImageDetails ");

		List<ValouxComponentsImageBean> imageBeanList = new ArrayList<ValouxComponentsImageBean>();

		if (valouxComponentsImageModel != null && valouxComponentsImageModel.size() > 0) {

			for (ValouxComponentsImageModel imageModel : valouxComponentsImageModel) {

				ValouxComponentsImageBean imageBean = new ValouxComponentsImageBean();

				if (CommonUserUtility.paparameterNullCheckObject(imageModel.getCid())) {
					imageBean.setCid(imageModel.getCid());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getCreatedBy())) {
					imageBean.setCreatedBy(imageModel.getCreatedBy());
				}
				if (CommonUserUtility.paparameterNullCheckObject(imageModel.getCreatedOn())) {
					imageBean.setCreatedOn(imageModel.getCreatedOn());
				}
				if (CommonUserUtility.paparameterNullCheckObject(imageModel.getFileType())) {
					imageBean.setFileType(imageModel.getFileType().byteValue());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getImgName())) {
					imageBean.setImgName(imageModel.getImgName());
				}
				if (CommonUserUtility.paparameterNullCheckObject(imageModel.getImgStatus())) {
					imageBean.setImgStatus(imageModel.getImgStatus().byteValue());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getImgUrl())) {
					imageBean.setImgUrl(imageModel.getImgUrl());
				}
				if (CommonUserUtility.paparameterNullCheckObject(imageModel.getItemComponentId())) {
//					imageBean.setItemComponentId(imageModel.getItemComponentId());
					ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
					valouxItemComponent.setVicid(imageModel.getItemComponentId());
					imageBean.setValouxItemComponent(valouxItemComponent);
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getModifiedBy())) {
					imageBean.setModifiedBy(imageModel.getModifiedBy());
				}
				if (CommonUserUtility.paparameterNullCheckObject(imageModel.getModifiedOn())) {
					imageBean.setModifiedOn(imageModel.getModifiedOn());
				}

				imageBeanList.add(imageBean);
			}

			valouxComponentsImageDao.saveValouxComponentsImageBean(imageBeanList);
		}

		LOGGER.debug("ItemServiceImpl : Exit Method addValouxComponentsImageDetails");
	}

	/**
	 * This method helps in adding item component certificate
	 */
	@Transactional
	public void addItemComponentCertificate(ItemComponentCertificateModel certificateModel) throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method addItemComponentCertificate ");

		if (certificateModel != null) {
			
			ItemComponentCertificateBean certificateBean = itemComponentCertificateDao.getComponentCertificateBeanByItemAndComponentId(
					certificateModel.getItemId(), certificateModel.getItemComponentId());

			if (certificateBean == null) {
				certificateBean = new ItemComponentCertificateBean();
				if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getCreatedBy())) {
					certificateBean.setCreatedBy(certificateModel.getCreatedBy());
				}
				if (CommonUserUtility.paparameterNullCheckObject(certificateModel.getCreatedOn())) {
					certificateBean.setCreatedOn(certificateModel.getCreatedOn());
				}
			}
			
			certificateBean.setCertificateNumber(certificateModel.getCertificateNumber());
			if (CommonUserUtility.paparameterNullCheckObject(certificateModel.getDateOfCertificate())) {
				certificateBean.setDateOfCertificate(certificateModel.getDateOfCertificate());
			}
			if (CommonUserUtility.paparameterNullCheckObject(certificateModel.getItemComponentId())) {
//				certificateBean.setItemComponentId(certificateModel.getItemComponentId());
				ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
				valouxItemComponent.setVicid(certificateModel.getItemComponentId());
				certificateBean.setValouxItemComponent(valouxItemComponent);
			}
			if (CommonUserUtility.paparameterNullCheckObject(certificateModel.getItemId())) {
//				certificateBean.setItemId(certificateModel.getItemId());
				ValouxItemBean valouxItem = new ValouxItemBean();
				valouxItem.setItemId(certificateModel.getItemId());
				certificateBean.setValouxItem(valouxItem);
			}
			if (CommonUserUtility.paparameterNullCheckObject(certificateModel.getLab()) && certificateModel.getLab() != 0) {
				certificateBean.setLab(certificateModel.getLab().byteValue());
			} else {
				certificateBean.setLab(null);
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getLaserIdNumber())) {
				certificateBean.setLaserIdNumber(certificateModel.getLaserIdNumber());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getLaserInscription())) {
				certificateBean.setLaserInscription(certificateModel.getLaserInscription());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getModifiedBy())) {
				certificateBean.setModifiedBy(certificateModel.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(certificateModel.getModifiedOn())) {
				certificateBean.setModifiedOn(certificateModel.getModifiedOn());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getName())) {
				certificateBean.setName(certificateModel.getName());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getOriginOfDiamond())) {
				certificateBean.setOriginOfDiamond(certificateModel.getOriginOfDiamond());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(certificateModel.getUrl())) {
				certificateBean.setUrl(certificateModel.getUrl());
			}

			itemComponentCertificateDao.addItemComponentCertificate(certificateBean);
		}

		LOGGER.debug("ItemServiceImpl : Enter Method addItemComponentCertificate ");
	}

	/**
	 * This method get list of shared contact
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfSharedContacts(String sharedBy, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedContacts");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getSharedRequestListBySharedByAndItemType(sharedBy, itemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedContacts");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared request
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfSharedRequest(String sharedBy, Integer itemType, Integer itemId,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedRequest");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getSharedRequestListBySharedByAndItemType(sharedBy, itemType, itemId, null, startRecordNo, numberOfRecords, sortBy, sortOrder);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedRequest");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared items
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfSharedItems(String sharedBy, Integer itemType, Integer sharedWith,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedItems");
		List<ValouxSharedRequestBean> sharedBeanListSharedWith = new ArrayList<ValouxSharedRequestBean>();
		List<ValouxSharedRequestBean> sharedBeanList = new ArrayList<ValouxSharedRequestBean>();
		Map<Integer, Integer> itemIdMap = new HashMap<Integer, Integer>();
		if (sharedWith == null) {
			sharedBeanList = valouxSharedRequestDao.getSharedRequestListBySharedByAndItemTypeDistinctItemId(sharedBy, itemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
		} else {
			sharedBeanList = valouxSharedRequestDao.getSharedRequestListBySharedByAndItemType(sharedBy, itemType, null, null, startRecordNo, numberOfRecords, sortBy, sortOrder);
		}
		if (sharedWith != null && sharedBeanList != null && sharedBeanList.size() > 0) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedBeanList) {
				// sharedWith is variable indicating 1-consumer 2-agent/store
				// 3-non registered user
				if (sharedRequestBean.getSharedTo() == null && sharedWith == 3) {
					if (itemIdMap != null && itemIdMap.size() > 0
							&& itemIdMap.containsKey(sharedRequestBean.getSharedItemId())) {
						int i = itemIdMap.get(sharedRequestBean.getSharedItemId());
						if ((i != 0 && i != sharedRequestBean.getSharedItemType()) || i == 0) {
							sharedBeanListSharedWith.add(sharedRequestBean);
							itemIdMap.put(sharedRequestBean.getSharedItemId(), sharedRequestBean.getSharedItemType());
						}
					} else {
						sharedBeanListSharedWith.add(sharedRequestBean);
						itemIdMap.put(sharedRequestBean.getSharedItemId(), sharedRequestBean.getSharedItemType());
					}
				} else {
					UserBean userBean = userDao.getConsumerDetailByRKey(sharedRequestBean.getSharedTo());
					if (userBean != null && sharedWith == 1) {
						if (itemIdMap != null && itemIdMap.size() > 0
								&& itemIdMap.containsKey(sharedRequestBean.getSharedItemId())) {
							int i = itemIdMap.get(sharedRequestBean.getSharedItemId());
							if ((i != 0 && i != sharedRequestBean.getSharedItemType()) || i == 0) {
								sharedBeanListSharedWith.add(sharedRequestBean);
								itemIdMap.put(sharedRequestBean.getSharedItemId(),
										sharedRequestBean.getSharedItemType());
							}
						} else {
							sharedBeanListSharedWith.add(sharedRequestBean);
							itemIdMap.put(sharedRequestBean.getSharedItemId(), sharedRequestBean.getSharedItemType());
						}
					} else {
						AgentBean agentBean = agentDao.getAgentDetailByRKey(sharedRequestBean.getSharedTo());
						if (agentBean != null && sharedWith == 2) {
							if (itemIdMap != null && itemIdMap.size() > 0
									&& itemIdMap.containsKey(sharedRequestBean.getSharedItemId())) {
								int i = itemIdMap.get(sharedRequestBean.getSharedItemId());
								if ((i != 0 && i != sharedRequestBean.getSharedItemType()) || i == 0) {
									sharedBeanListSharedWith.add(sharedRequestBean);
									itemIdMap.put(sharedRequestBean.getSharedItemId(),
											sharedRequestBean.getSharedItemType());
								}
							} else {
								sharedBeanListSharedWith.add(sharedRequestBean);
								itemIdMap.put(sharedRequestBean.getSharedItemId(),
										sharedRequestBean.getSharedItemType());
							}
						}
					}
				}
			}
			return sharedBeanListSharedWith;
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedItems");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared items
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfSharedItemsToUser(String sharedTo, Integer itemType,
			Integer approvedStatus,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedItems");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getSharedRequestItemListSharedToUser(sharedTo, itemType, approvedStatus, startRecordNo, numberOfRecords, sortBy, sortOrder);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedItems");
		return sharedBeanList;
	}
	
	/**
	 * This method get list of shared items
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfSharedItemsToUserByConsumer(String sharedTo, Integer itemType,
			Integer approvedStatus,String sharedBy,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedItemsToUserByConsumer");
		
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getSharedRequestItemListSharedToUserByConsumer(sharedTo, itemType, approvedStatus, sharedBy, startRecordNo, numberOfRecords, sortBy, sortOrder);
		
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedItemsToUserByConsumer");
		return sharedBeanList;
	}

	/**
	 * This method get list of shared items
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfSharedItemsBySharedTo(String sharedTo,Integer sharedItemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedItemsBySharedTo");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getSharedRequestListBySharedTo(sharedTo, sharedItemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedItemsBySharedTo");
		return sharedBeanList;
	}

	/**
	 * This method approve or disapprove shared request
	 */
	@Transactional
	public Boolean approveOrRejectSharedRequest(Integer sharedRequestId, Integer approvedStatus) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method approveOrRejectSharedRequest");
		ValouxSharedRequestBean shareRequestBean = valouxSharedRequestDao.getSaredRequestBySharedRequestId(sharedRequestId);
		LoginBean loginBean = loginDao.getLoginDetailByPKey((shareRequestBean.getSharedBy()));
		if (loginBean.getUserName() != null) {
			String from = CommonConstants.SIGN_UP_MAIL;
			StringBuffer sub = new StringBuffer();
			String body = "";
			if (approvedStatus == 2) {
				body = CommonMailUtility.getSharedItemAcceptedContent(loginBean.getFirstName()+" "+loginBean.getLastName());
				sub.append(CommonConstants.ITEM_ACCEPT_BODY);
				
			} else if (approvedStatus == 3) {
				body = CommonMailUtility.getSharedItemRejectedContent(loginBean.getFirstName()+" "+loginBean.getLastName());
				sub.append(CommonConstants.ITEM_REJECT_BODY);
				
			}
			MailApi.SendMail(loginBean.getUserName(), from, sub.toString(), body, 1);
		}
		shareRequestBean.setApproveStatus(approvedStatus);
		valouxSharedRequestDao.updateSharedRequestBean(shareRequestBean);
		if (shareRequestBean.getApproveStatus().equals(approvedStatus)) {
			return true;
		}
		LOGGER.debug("ItemServiceImpl : Exit Method approveOrRejectSharedRequest");
		return false;
	}

	/**
	 * This method get list of shared items to user in requested state
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfRequestedSharedItemsToUser(String sharedTo, Integer itemType,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfRequestedSharedItemsToUser");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getRequetedItemListSharedToUser(sharedTo, itemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfRequestedSharedItemsToUser");
		return sharedBeanList;
	}

	/**
	 * This method get number of agent and consumer shared with item
	 */
	@Transactional
	public Map<Integer, Integer> getNumberOfAgentAndConsumerToWhichItemIsShared(Integer sharedItemId,Integer sharedItemType) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getNumberOfAgentAndConsumerToWhichItemIsShared");
		Integer consumerCount = 0;
		Integer agentCount = 0;
		Integer unRegisteredCount = 0;
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestListByItemId(sharedItemId,sharedItemType);
		if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
			for (ValouxSharedRequestBean shareRequestBean : sharedRequestBeanList) {
				if (shareRequestBean.getSharedTo() != null) {
					UserBean userBean = userDao.getConsumerDetailByRKey(shareRequestBean.getSharedTo());
					if (userBean != null) {
						consumerCount++;
					} else {
						AgentBean agentBean = agentDao.getAgentDetailByRKey(shareRequestBean.getSharedTo());
						if (agentBean != null) {
							agentCount++;
						}
					}

				}else{
					unRegisteredCount++;
				}
			}
		}
		Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();
		countMap.put(1, consumerCount);
		countMap.put(2, agentCount);
		countMap.put(3, unRegisteredCount);
		LOGGER.debug("ItemServiceImpl : Enter Method getNumberOfAgentAndConsumerToWhichItemIsShared");
		return countMap;
	}

	@Transactional
	public void updateSharedRequestForNewRegistration(String emailid, String publicKey, String role) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method updateSharedRequestForNewRegistration");
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestListBySharedToEmail(emailid);
		if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
			for (ValouxSharedRequestBean shareRequestBean : sharedRequestBeanList) {
				shareRequestBean.setSharedTo(publicKey);
				shareRequestBean.setModifiedBy(publicKey);
				shareRequestBean.setModifiedOn(CommonUtility.getDateAndTime());
				if (CommonConstants.AGENT.equals(role)) {
					shareRequestBean.setSharedItemPermission(2);
				}
				valouxSharedRequestDao.updateSharedRequestBean(shareRequestBean);
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method updateSharedRequestForNewRegistration");

	}

	/**
	 * This method get item component details
	 */
	@Transactional
	public ValouxItemComponentModel getComponentsByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentsByItemAndComponentId");
		ValouxItemComponentBean valouxComponentItemBean = valouxItemComponentDao.getItemComponentByItemAndComponentId(itemId, componentId);
		ValouxItemComponentModel itemModel = null;
		if(valouxComponentItemBean!=null){
			itemModel = new ValouxItemComponentModel();
			itemModel.setItemId(valouxComponentItemBean.getValouxItem().getItemId());
			itemModel.setCreatedBy(valouxComponentItemBean.getCreatedBy());
			itemModel.setCreatedOn(valouxComponentItemBean.getCreatedOn());
			itemModel.setModifiedBy(valouxComponentItemBean.getModifiedBy());
			itemModel.setModifiedOn(valouxComponentItemBean.getModifiedOn());
			itemModel.setName(valouxComponentItemBean.getName());
			itemModel.setComponentsType(valouxComponentItemBean.getComponentsType());
			itemModel.setVicid(valouxComponentItemBean.getVicid());
			itemModel.setQuantity(valouxComponentItemBean.getQuantity());
			itemModel.setVicStatus(valouxComponentItemBean.getVicStatus());
			
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getAdjustmentPrice())) {
				itemModel.setAdjustmentPrice(valouxComponentItemBean.getAdjustmentPrice().toString());
			}
			if(CommonUserUtility.paparameterNullCheckStringObject(valouxComponentItemBean.getBrandPriceAdjustmentNotes())) {
				itemModel.setBrandPriceAdjustmentNotes(valouxComponentItemBean.getBrandPriceAdjustmentNotes());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getBrandPriceAdjustmentOperator())) {
				itemModel.setBrandPriceAdjustmentOperator(valouxComponentItemBean.getBrandPriceAdjustmentOperator());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getBrandPriceAdjustmentType())) {
				itemModel.setBrandPriceAdjustmentType(valouxComponentItemBean.getBrandPriceAdjustmentType());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getBrandPriceAdjustmentValue())) {
				itemModel.setBrandPriceAdjustmentValue(valouxComponentItemBean.getBrandPriceAdjustmentValue().toString());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getCurrentUnitPrice())) {
				itemModel.setCurrentUnitPrice(valouxComponentItemBean.getCurrentUnitPrice().toString());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getFinalPrice())) {
				itemModel.setFinalPrice(valouxComponentItemBean.getFinalPrice());
			}
			if(CommonUserUtility.paparameterNullCheckStringObject(valouxComponentItemBean.getGeneralPriceAdjustmentNotes())) {
				itemModel.setGeneralPriceAdjustmentNotes(valouxComponentItemBean.getGeneralPriceAdjustmentNotes());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getGeneralPriceAdjustmentOperator())) {
				itemModel.setGeneralPriceAdjustmentOperator(valouxComponentItemBean.getGeneralPriceAdjustmentOperator());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getGeneralPriceAdjustmentType())) {
				itemModel.setGeneralPriceAdjustmentType(valouxComponentItemBean.getGeneralPriceAdjustmentType());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getGeneralPriceAdjustmentValue())) {
				itemModel.setGeneralPriceAdjustmentValue(valouxComponentItemBean.getGeneralPriceAdjustmentValue().toString());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getMarketValue())) {
				itemModel.setMarketValue(valouxComponentItemBean.getMarketValue());
			}
			if(CommonUserUtility.paparameterNullCheckObject(valouxComponentItemBean.getPurchasePrice())) {
				itemModel.setPurchasePrice(valouxComponentItemBean.getPurchasePrice());
			}
			
		}
		
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentsByItemAndComponentId");
		return itemModel;
	}

	/**
	 * This method helps in adding item component certificate
	 */
	@Transactional
	public ItemComponentCertificateModel getComponentCertificateBeanByItemAndComponentId(Integer itemId,
			Integer componentId) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentCertificateBeanByItemAndComponentId");
		ItemComponentCertificateBean componentCertificateBean = itemComponentCertificateDao
				.getComponentCertificateBeanByItemAndComponentId(itemId, componentId);

		ItemComponentCertificateModel certificateModel = null;

		if (componentCertificateBean != null) {
			certificateModel = new ItemComponentCertificateModel();

			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getCcid())) {
				certificateModel.setCcid(componentCertificateBean.getCcid());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getCertificateNumber())) {
				certificateModel.setCertificateNumber(componentCertificateBean.getCertificateNumber());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getCreatedBy())) {
				certificateModel.setCreatedBy(componentCertificateBean.getCreatedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getCreatedOn())) {
				certificateModel.setCreatedOn(componentCertificateBean.getCreatedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getDateOfCertificate())) {
				certificateModel.setDateOfCertificate(componentCertificateBean.getDateOfCertificate());
			}
			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getValouxItemComponent().getVicid())) {
				certificateModel.setItemComponentId(componentCertificateBean.getValouxItemComponent().getVicid());
			}
			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getValouxItem().getItemId())) {
				certificateModel.setItemId(componentCertificateBean.getValouxItem().getItemId());
			}
			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getLab())) {
				certificateModel.setLab(Integer.valueOf(componentCertificateBean.getLab()));
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getLaserIdNumber())) {
				certificateModel.setLaserIdNumber(componentCertificateBean.getLaserIdNumber());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getLaserInscription())) {
				certificateModel.setLaserInscription(componentCertificateBean.getLaserInscription());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getModifiedBy())) {
				certificateModel.setModifiedBy(componentCertificateBean.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(componentCertificateBean.getModifiedOn())) {
				certificateModel.setModifiedOn(componentCertificateBean.getModifiedOn());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getName())) {
				certificateModel.setName(componentCertificateBean.getName());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getOriginOfDiamond())) {
				certificateModel.setOriginOfDiamond(componentCertificateBean.getOriginOfDiamond());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(componentCertificateBean.getUrl())) {
				certificateModel.setUrl(componentCertificateBean.getUrl());
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentCertificateBeanByItemAndComponentId");
		return certificateModel;
	}

	/**
	 * This method helps in adding item component diamond
	 */
	@Transactional
	public ValouxDiamondModel getComponentDiamondBeanByItemAndComponentId(Integer itemId, Integer componentId)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentDiamondBeanByItemAndComponentId");
		ValouxDiamondBean diamondComponentBean = valouxDiamondDao.getComponentDiamondBeanByItemAndComponentId(itemId,
				componentId);

		ValouxDiamondModel diamondComponentModel = null;

		if (diamondComponentBean != null) {
			diamondComponentModel = new ValouxDiamondModel();

			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getVdid())) {
				diamondComponentModel.setVdid(diamondComponentBean.getVdid());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getClarityId())) {
				diamondComponentModel.setClarityId(diamondComponentBean.getClarityId());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getColor())) {
				diamondComponentModel.setColor(diamondComponentBean.getColor());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondComponentBean.getCreatedBy())) {
				diamondComponentModel.setCreatedBy(diamondComponentBean.getCreatedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getCreatedOn())) {
				diamondComponentModel.setCreatedOn(diamondComponentBean.getCreatedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getCut())) {
				diamondComponentModel.setCut(diamondComponentBean.getCut());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getCutlet())) {
				diamondComponentModel.setCutlet(diamondComponentBean.getCutlet());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getDepth())) {
				diamondComponentModel.setDepth(String.valueOf(diamondComponentBean.getDepth()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getDepthPercentage())) {
				diamondComponentModel.setDepthPercentage(String.valueOf(diamondComponentBean.getDepthPercentage()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getFancyColor())) {
				diamondComponentModel.setFancyColor(diamondComponentBean.getFancyColor());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getFluorescence())) {
				diamondComponentModel.setFluorescence(diamondComponentBean.getFluorescence());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getGirdleThicknessDescription())) {
				diamondComponentModel.setGirdleThicknessDescription(diamondComponentBean
						.getGirdleThicknessDescription());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getDiamondHeight())){
				diamondComponentModel.setDiamondHeight(diamondComponentBean.getDiamondHeight());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getValouxItemComponent().getVicid())) {
				diamondComponentModel.setItemComponentId(diamondComponentBean.getValouxItemComponent().getVicid());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getValouxItem().getItemId())) {
				diamondComponentModel.setItemId(diamondComponentBean.getValouxItem().getItemId());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getDiamondLength())) {
				diamondComponentModel.setDiamondLength(diamondComponentBean.getDiamondLength());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getLengthWidthRatio())) {
				diamondComponentModel.setLengthWidthRatio(String.valueOf(diamondComponentBean.getLengthWidthRatio()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getMarketValue())) {
				diamondComponentModel.setMarketValue(String.valueOf(diamondComponentBean.getMarketValue()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getMaxDiameter())) {
				diamondComponentModel.setMaxDiameter(String.valueOf(diamondComponentBean.getMaxDiameter()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getMinDiameter())) {
				diamondComponentModel.setMinDiameter(String.valueOf(diamondComponentBean.getMinDiameter()));
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(diamondComponentBean.getModifiedBy())) {
				diamondComponentModel.setModifiedBy(diamondComponentBean.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getModifiedOn())) {
				diamondComponentModel.setModifiedOn(diamondComponentBean.getModifiedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getPlacement())) {
				diamondComponentModel.setPlacement(diamondComponentBean.getPlacement());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getPolish())) {
				diamondComponentModel.setPolish(diamondComponentBean.getPolish());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getSecondaryHue())) {
				diamondComponentModel.setSecondaryHue(diamondComponentBean.getSecondaryHue());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getShape())) {
				diamondComponentModel.setShape(diamondComponentBean.getShape());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getSingleWeight())) {
				diamondComponentModel.setSingleWeight(String.valueOf(diamondComponentBean.getSingleWeight()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getStatus())) {
				diamondComponentModel.setStatus(diamondComponentBean.getStatus().intValue());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getSymmetry())) {
				diamondComponentModel.setSymmetry(diamondComponentBean.getSymmetry());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getTablePercentage())) {
				diamondComponentModel.setTablePercentage(String.valueOf(diamondComponentBean.getTablePercentage()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getThickness())) {
				diamondComponentModel.setThickness(diamondComponentBean.getThickness());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getTotalWeight())) {
				diamondComponentModel.setTotalWeight(String.valueOf(diamondComponentBean.getTotalWeight()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getWeightMeasure())) {
				diamondComponentModel.setWeightMeasure(diamondComponentBean.getWeightMeasure());
			}
			if (CommonUserUtility.paparameterNullCheckObject(diamondComponentBean.getDiamondWidth())) {
				diamondComponentModel.setPwidth(String.valueOf(diamondComponentBean.getDiamondWidth()));
			}
			
			JSONArray intArray = new JSONArray();
			List<ValouxInclusionBean> inclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(diamondComponentBean.getValouxItemComponent().getVicid(), CommonConstants.COMPONENT_INCLUSION_INTERNAL);
			if(inclusionBeans != null && inclusionBeans.size() > 0){
				for (ValouxInclusionBean valouxInclusionBean : inclusionBeans) {
					intArray.put(valouxInclusionBean.getInclusion());
				}
			}
			diamondComponentModel.setInternalInclusions(intArray);
			
			JSONArray extArray = new JSONArray();
			List<ValouxInclusionBean> exclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(diamondComponentBean.getValouxItemComponent().getVicid(), CommonConstants.COMPONENT_INCLUSION_EXTERNAL);
			if(exclusionBeans != null && exclusionBeans.size() > 0){
				for (ValouxInclusionBean valouxInclusionBean : exclusionBeans) {
					extArray.put(valouxInclusionBean.getInclusion());
				}
			}
			diamondComponentModel.setExternalInclusions(extArray);
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentDiamondBeanByItemAndComponentId");
		return diamondComponentModel;
	}

	/**
	 * This method helps in fetching item component images
	 */
	@Transactional
	public List<ValouxComponentsImageModel> getComponentsImageModelByComponentIdAndType(Integer componentId)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentsImageModelByComponentIdAndType");
		List<ValouxComponentsImageBean> componentsImageBeans = valouxComponentsImageDao
				.getComponentsImageBeanByComponentIdAndType(componentId);
		List<ValouxComponentsImageModel> componentsImageModels = new ArrayList<ValouxComponentsImageModel>();

		if (componentsImageBeans != null && componentsImageBeans.size() > 0) {

			for (ValouxComponentsImageBean ValouxComponentsImageBean : componentsImageBeans) {
				ValouxComponentsImageModel componentsImageModel = new ValouxComponentsImageModel();

				if (CommonUserUtility.paparameterNullCheckObject(ValouxComponentsImageBean.getCid())) {
					componentsImageModel.setCid(ValouxComponentsImageBean.getCid());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(ValouxComponentsImageBean.getCreatedBy())) {
					componentsImageModel.setCreatedBy(ValouxComponentsImageBean.getCreatedBy());
				}
				if (CommonUserUtility.paparameterNullCheckObject(ValouxComponentsImageBean.getCreatedOn())) {
					componentsImageModel.setCreatedOn(ValouxComponentsImageBean.getCreatedOn());
				}
				if (CommonUserUtility.paparameterNullCheckObject(ValouxComponentsImageBean.getFileType())) {
					componentsImageModel.setFileType(ValouxComponentsImageBean.getFileType().intValue());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(ValouxComponentsImageBean.getImgName())) {
					componentsImageModel.setImgName(ValouxComponentsImageBean.getImgName());
				}
				if (CommonUserUtility.paparameterNullCheckObject(ValouxComponentsImageBean.getImgStatus())) {
					componentsImageModel.setImgStatus(ValouxComponentsImageBean.getImgStatus().intValue());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(ValouxComponentsImageBean.getImgUrl())) {
					componentsImageModel.setImgUrl(ValouxComponentsImageBean.getImgUrl());
				}
				if (CommonUserUtility.paparameterNullCheckObject(ValouxComponentsImageBean.getValouxItemComponent().getVicid())) {
					componentsImageModel.setItemComponentId(ValouxComponentsImageBean.getValouxItemComponent().getVicid());
				}
				if (CommonUserUtility.paparameterNullCheckStringObject(ValouxComponentsImageBean.getModifiedBy())) {
					componentsImageModel.setModifiedBy(ValouxComponentsImageBean.getModifiedBy());
				}
				if (CommonUserUtility.paparameterNullCheckObject(ValouxComponentsImageBean.getModifiedOn())) {
					componentsImageModel.setModifiedOn(ValouxComponentsImageBean.getModifiedOn());
				}
				componentsImageModels.add(componentsImageModel);
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentsImageModelByComponentIdAndType");
		return componentsImageModels;
	}

	/**
	 * This method helps in delete item component images
	 */
	@Transactional
	public Boolean deleteItemComponentDocument(Integer componentId, Integer imageId) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method deleteItemComponentDocument");
		ValouxComponentsImageBean itemImageBean = valouxComponentsImageDao.getItemComponentImageListByIdAndImageId(componentId, imageId);
		if (itemImageBean != null) {
			valouxComponentsImageDao.deleteItemComponentImages(itemImageBean);
			CommonUtility.deleteDocumentInDirectory(itemImageBean.getImgUrl());
			return true;
		}
		LOGGER.debug("ItemServiceImpl : Exit Method deleteItemComponentDocument");
		return false;
	}

	/**
	 * This method give all share items list to agent not in a collection.
	 */
	@Transactional
	public List<ValouxItemModel> getAgentSharedItemsNotInCollection(String sharedTo, Integer collectionId,
			String sharedBy) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getAgentSharedItemsNotInCollection");
		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsById(collectionId);

		List<Integer> itemList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (ValouxCollectionItemBean collectionItemBean : collectionBeans) {
				itemList.add(collectionItemBean.getValouxItemBean().getItemId());
			}
		}
		List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();

		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getShareItemListNotInItemList(sharedTo,
				itemList.toArray(), sharedBy);
		if (sharedRequestBeanList != null) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
				if (sharedRequestBean.getSharedItemType() == 1) {
					ValouxItemBean itemBean = valouxItemDao.getValouxItemDetailsById(sharedRequestBean.getSharedItemId());
					if (itemBean != null) {
						ValouxItemModel itemModel = CommonUserUtility.prepareItemModelFromitemBean(itemBean);
						itemModelList.add(itemModel);
					}
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getAgentSharedItemsNotInCollection");
		return itemModelList;
	}

	/**
	 * This method give all share items list to agent not in a appraisal.
	 */
	@Transactional
	public List<ValouxItemModel> getAgentSharedItemsNotInAppraisal(String sharedTo, Integer appraisalId,
			String sharedBy) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getAgentSharedItemsNotInAppraisal");
		List<AppraisalItemsBean> appraisalBeans = appraisalItemsDao.getAppraisalItemDataByAppraisalId(appraisalId);

		List<Integer> itemsList = new ArrayList<Integer>();

		if (appraisalBeans != null && appraisalBeans.size() > 0) {

			for (AppraisalItemsBean itemsBean : appraisalBeans) {
				itemsList.add(itemsBean.getValouxItemBean().getItemId());
			}
		}

		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getShareItemListNotInItemList(sharedTo,
				itemsList.toArray(), sharedBy);
		List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
		if (sharedRequestBeanList != null) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
				if (sharedRequestBean.getSharedItemType() == 1) {
					ValouxItemBean itemBean = valouxItemDao.getValouxItemDetailsById(sharedRequestBean.getSharedItemId());
					if (itemBean != null) {
						ValouxItemModel itemModel = CommonUserUtility.prepareItemModelFromitemBean(itemBean);
						itemModelList.add(itemModel);
					}
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getAgentSharedItemsNotInAppraisal");
		return itemModelList;
	}

	/**
	 * This method get all appraisals not in collection
	 */
	@Transactional
	public List<AppraisalModel> getAgentSharedAppraisalsNotInCollection(String sharedTo, Integer collectionId,String sharedBy)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getAgentSharedAppraisalsNotInCollection");
		List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao.getCollectionAppraisalsById(collectionId);

		List<Integer> appraisalList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (AppraisalCollectionBean collectionBean : collectionBeans) {
				appraisalList.add(collectionBean.getAppraisalBean().getAppraisalId());
			}
		}
		List<AppraisalModel> modelList = new ArrayList<AppraisalModel>();
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getShareAppraisalListNotInItemList(sharedTo, appraisalList.toArray(), sharedBy);
		if (sharedRequestBeanList != null) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
		AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(sharedRequestBean
				.getSharedItemId());
		if(appraisalBean!=null && !appraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
		AppraisalModel model = CommonUserUtility.prepareAppraisalModelFromAppraisalBean(appraisalBean);
		modelList.add(model);
		}
			}
		}
				
		LOGGER.debug("ItemServiceImpl : Exit Method getAgentSharedAppraisalsNotInCollection");
		return modelList;
	}
	
	/**
	 * This method get all appraisals not in collection
	 */
	@Transactional
	public List<AppraisalBean> getAgentSharedAppraisalsNotInItem(String sharedTo, Integer itemId,String sharedBy)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getAgentSharedAppraisalsNotInItem");
		List<AppraisalItemsBean> appraisalitemList = appraisalItemsDao.getApraisalItemListByItemId(itemId);
		List<Integer> appraisalList = new ArrayList<Integer>();
		if (appraisalitemList != null && appraisalitemList.size() > 0) {
			for (AppraisalItemsBean appraisalItemBean : appraisalitemList) {
				appraisalList.add(appraisalItemBean.getAppraisalBean().getAppraisalId());
			}
		}
		List<AppraisalBean> appraisalBeanList = new ArrayList<AppraisalBean>();
		
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getShareAppraisalListNotInItemList(sharedTo, appraisalList.toArray(), sharedBy);
		if (sharedRequestBeanList != null) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
		AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(sharedRequestBean
				.getSharedItemId());
		if(appraisalBean!=null && !appraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
		appraisalBeanList.add(appraisalBean);
		}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getAgentSharedAppraisalsNotInItem");
		return appraisalBeanList;
	}
	
	/**
	  * This method give all share collection list to agent not in a appraisal.
	 */
	@Transactional
	public List<ValouxCollectionModel> getAgentSharedCollectionNotInAppraisal(String sharedTo, Integer appraisalId,
			String sharedBy) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getAgentSharedCollectionNotInAppraisal");
		List<AppraisalCollectionBean> collectionBeans = appraisalCollectionDao.getAppraisalBeansByAppraisalId(appraisalId);

		List<Integer> collectionList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (AppraisalCollectionBean collectionBean : collectionBeans) {
				collectionList.add(collectionBean.getValouxCollectionBean().getVcid());
			}
		}
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();

		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getShareCollectionListNotInItemList(sharedTo,
				collectionList.toArray(), sharedBy);
		if (sharedRequestBeanList != null) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
				ValouxCollectionBean collectionBean = valouxCollectionDao.getCollectionDetailsById(sharedRequestBean
						.getSharedItemId());
				if(collectionBean!=null){
				ValouxCollectionModel collectionModel = CommonUserUtility
						.prepareCollectionModelFromCollectionBean(collectionBean);
				collectionModelList.add(collectionModel);
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getAgentSharedCollectionNotInAppraisal");
		return collectionModelList;
	}

	/**
	 * This method give all share collection list to agent not in a appraisal.
	 */
	@Transactional
	public List<ValouxCollectionModel> getAgentSharedCollectionNotInItem(String sharedTo, Integer itemId,
			String sharedBy) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getAgentSharedCollectionNotInItem");
		List<ValouxCollectionItemBean> collectionBeans = valouxCollectionItemDao.getCollectionItemsByItemId(itemId);

		List<Integer> collectionList = new ArrayList<Integer>();

		if (collectionBeans != null && collectionBeans.size() > 0) {

			for (ValouxCollectionItemBean collectionItemBean : collectionBeans) {
				collectionList.add(collectionItemBean.getValouxCollectionBean().getVcid());
			}
		}
		List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();

		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getShareCollectionListNotInItemList(sharedTo,
				collectionList.toArray(), sharedBy);
		if (sharedRequestBeanList != null) {
			for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
				ValouxCollectionBean valouxCollectionBean = valouxCollectionDao.getCollectionDetailsById(sharedRequestBean
						.getSharedItemId());
				if (valouxCollectionBean != null && !valouxCollectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)) {
					ValouxCollectionModel collectionModel = new ValouxCollectionModel();
					collectionModel.setVcid(valouxCollectionBean.getVcid());
					collectionModel.setCname(valouxCollectionBean.getCname());
					collectionModel.setShortDescription(valouxCollectionBean.getShortDescription());
					collectionModel.setCollectionStatus(valouxCollectionBean.getCollectionStatus());
					collectionModel.setCreatedBy(valouxCollectionBean.getCreatedBy());
					collectionModel.setCreatedOn(valouxCollectionBean.getCreatedOn());
					collectionModel.setModifiedBy(valouxCollectionBean.getModifiedBy());
					collectionModel.setModifiedOn(valouxCollectionBean.getModifiedOn());
					collectionModelList.add(collectionModel);
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getAgentSharedCollectionNotInItem");
		return collectionModelList;
	}

	/**
	 * This method helps in get item component metal
	 */
	@Transactional
	public ValouxMetalModel getComponentMetalBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentDiamondBeanByItemAndComponentId");
		ValouxMetalBean metalComponentBean = valouxMetalDao.getComponentMetalBeanByItemAndComponentId(itemId,
				componentId);

		ValouxMetalModel metalComponentModel = null;

		if (metalComponentBean != null) {
			metalComponentModel = new ValouxMetalModel();
			
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getColor())) {
				metalComponentModel.setColor(metalComponentBean.getColor());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(metalComponentBean.getCreatedBy())) {
				metalComponentModel.setCreatedBy(metalComponentBean.getCreatedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getCreatedOn())) {
				metalComponentModel.setCreatedOn(metalComponentBean.getCreatedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getValouxItemComponent().getVicid())) {
				metalComponentModel.setItemComponentId(metalComponentBean.getValouxItemComponent().getVicid());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getValouxItem().getItemId())) {
				metalComponentModel.setItemId(metalComponentBean.getValouxItem().getItemId());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getMarketValue())) {
				metalComponentModel.setMarketValue(metalComponentBean.getMarketValue().toString());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getMeasurements())) {
				metalComponentModel.setMeasurements(metalComponentBean.getMeasurements().toString());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getMetalsType())) {
				metalComponentModel.setMetalsType(metalComponentBean.getMetalsType());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(metalComponentBean.getModifiedBy())) {
				metalComponentModel.setModifiedBy(metalComponentBean.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getModifiedOn())) {
				metalComponentModel.setModifiedOn(metalComponentBean.getModifiedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getPurity())) {
				metalComponentModel.setPurity(metalComponentBean.getPurity());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getStatus())) {
				metalComponentModel.setStatus(Integer.valueOf(metalComponentBean.getStatus()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getTypeDetermined())) {
				metalComponentModel.setTypeDetermined(metalComponentBean.getTypeDetermined());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(metalComponentBean.getTypeSpecified())) {
				metalComponentModel.setTypeSpecified(metalComponentBean.getTypeSpecified());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getWeight())) {
				metalComponentModel.setWeight(metalComponentBean.getWeight().toString());
			}
			if (CommonUserUtility.paparameterNullCheckObject(metalComponentBean.getAppraisedValue())) {
				metalComponentModel.setAppraisedValue(metalComponentBean.getAppraisedValue().toString());
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentDiamondBeanByItemAndComponentId");
		return metalComponentModel;
	}

	/**
	 * This method helps in get item component pearl
	 */
	@Transactional
	public ValouxPearlModel getComponentPearlBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentPearlBeanByItemAndComponentId");
		ValouxPearlBean pearlComponentBean = valouxPearlDao.getComponentPearlBeanByItemAndComponentId(itemId,
				componentId);

		ValouxPearlModel pearlComponentModel = null;

		if (pearlComponentBean != null) {
			pearlComponentModel = new ValouxPearlModel();
			
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getBlemish())) {
				pearlComponentModel.setBlemish(pearlComponentBean.getBlemish());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getColor())) {
				pearlComponentModel.setColor(pearlComponentBean.getColor());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getComposition())) {
				pearlComponentModel.setComposition(pearlComponentBean.getComposition());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(pearlComponentBean.getCreatedBy())) {
				pearlComponentModel.setCreatedBy(pearlComponentBean.getCreatedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getCreatedOn())) {
				pearlComponentModel.setCreatedOn(pearlComponentBean.getCreatedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getDrilled())) {
				pearlComponentModel.setDrilled(pearlComponentBean.getDrilled());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getEnhancements())) {
				pearlComponentModel.setEnhancements(pearlComponentBean.getEnhancements());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getValouxItemComponent().getVicid())) {
				pearlComponentModel.setItemComponentId(pearlComponentBean.getValouxItemComponent().getVicid());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getValouxItem().getItemId())) {
				pearlComponentModel.setItemId(pearlComponentBean.getValouxItem().getItemId());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getPearlsLength())) {
				pearlComponentModel.setPearlsLength(pearlComponentBean.getPearlsLength());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getLuster())) {
				pearlComponentModel.setLuster(pearlComponentBean.getLuster());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getMatching())) {
				pearlComponentModel.setMatching(pearlComponentBean.getMatching());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getPearlsMax())) {
				pearlComponentModel.setPearlsMax(pearlComponentBean.getPearlsMax());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getMeasurements())) {
				pearlComponentModel.setMeasurements(pearlComponentBean.getMeasurements().toString());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getPearlsMin())) {
				pearlComponentModel.setPearlsMin(pearlComponentBean.getPearlsMin());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(pearlComponentBean.getModifiedBy())) {
				pearlComponentModel.setModifiedBy(pearlComponentBean.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getModifiedOn())) {
				pearlComponentModel.setModifiedOn(pearlComponentBean.getModifiedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getOrigin())) {
				pearlComponentModel.setOrigin(pearlComponentBean.getOrigin());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getPearlType())) {
				pearlComponentModel.setPearlType(pearlComponentBean.getPearlType());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getShape())) {
				pearlComponentModel.setShape(pearlComponentBean.getShape());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getStatus())) {
				pearlComponentModel.setStatus(Integer.valueOf(pearlComponentBean.getStatus()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getPearlsStyle())) {
				pearlComponentModel.setPstyle(pearlComponentBean.getPearlsStyle());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getStyleUserEntered())) {
				pearlComponentModel.setStyleUserEntered(pearlComponentBean.getStyleUserEntered());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getWeight())) {
				pearlComponentModel.setWeight(pearlComponentBean.getWeight().toString());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getAppraisedValue())) {
				pearlComponentModel.setAppraisedValue(pearlComponentBean.getAppraisedValue().toString());
			}
			if (CommonUserUtility.paparameterNullCheckObject(pearlComponentBean.getOrigin())) {
				ValouxOriginBean originBean = valouxOriginDao.getCountryOriginNameByCountryId(pearlComponentBean.getOrigin());
				if(originBean !=null){
					pearlComponentModel.setOriginName(originBean.getNicename());
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentPearlBeanByItemAndComponentId");
		return pearlComponentModel;
	}

	/**
	 * This method helps in get item component gemstone
	 */
	@Transactional
	public ValouxGemstoneModel getComponentGemstoneBeanByItemAndComponentId(
			Integer itemId, Integer componentId) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentGemstoneBeanByItemAndComponentId");
		ValouxGemstoneBean gemstoneComponentBean = valouxGemstoneDao.getComponentGemstoneBeanByItemAndComponentId(itemId,
				componentId);

		ValouxGemstoneModel gemstoneComponentModel = null;

		if (gemstoneComponentBean != null) {
			gemstoneComponentModel = new ValouxGemstoneModel();
			
			if (CommonUserUtility.paparameterNullCheckStringObject(gemstoneComponentBean.getCreatedBy())) {
				gemstoneComponentModel.setCreatedBy(gemstoneComponentBean.getCreatedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getCreatedOn())) {
				gemstoneComponentModel.setCreatedOn(gemstoneComponentBean.getCreatedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getCut())) {
				gemstoneComponentModel.setCut(gemstoneComponentBean.getCut());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getEnhancement())) {
				gemstoneComponentModel.setEnhancement(gemstoneComponentBean.getEnhancement());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getGemstonesType())) {
				gemstoneComponentModel.setGemstonesType(gemstoneComponentBean.getGemstonesType());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getValouxItemComponent().getVicid())) {
				gemstoneComponentModel.setItemComponentId(gemstoneComponentBean.getValouxItemComponent().getVicid());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getValouxItem().getItemId())) {
				gemstoneComponentModel.setItemId(gemstoneComponentBean.getValouxItem().getItemId());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getMeasurements())) {
				gemstoneComponentModel.setMeasurements(gemstoneComponentBean.getMeasurements().toString());
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(gemstoneComponentBean.getModifiedBy())) {
				gemstoneComponentModel.setModifiedBy(gemstoneComponentBean.getModifiedBy());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getModifiedOn())) {
				gemstoneComponentModel.setModifiedOn(gemstoneComponentBean.getModifiedOn());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getOrigin())) {
				gemstoneComponentModel.setOrigin(gemstoneComponentBean.getOrigin());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getPlacement())) {
				gemstoneComponentModel.setPlacement(gemstoneComponentBean.getPlacement());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getShape())) {
				gemstoneComponentModel.setShape(gemstoneComponentBean.getShape());
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getStatus())) {
				gemstoneComponentModel.setStatus(Integer.valueOf(gemstoneComponentBean.getStatus()));
			}
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getWeight())) {
				gemstoneComponentModel.setWeight(gemstoneComponentBean.getWeight().toString());
			}
//			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getPlacement())) {
//				gemstoneComponentModel.setInternalInclusions(gemstoneComponentBean.getInternalInclusions());
//			}
//			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getPlacement())) {
//				gemstoneComponentModel.setExternalInclusions(gemstoneComponentBean.getExternalInclusions());
//			}
			
			JSONArray intArray = new JSONArray();
			List<ValouxInclusionBean> inclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(gemstoneComponentBean.getValouxItemComponent().getVicid(), CommonConstants.COMPONENT_INCLUSION_INTERNAL);
			if(inclusionBeans != null && inclusionBeans.size() > 0){
				for (ValouxInclusionBean valouxInclusionBean : inclusionBeans) {
					intArray.put(valouxInclusionBean.getInclusion());
				}
			}
			gemstoneComponentModel.setInternalInclusions(intArray);
			
			JSONArray extArray = new JSONArray();
			List<ValouxInclusionBean> exclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(gemstoneComponentBean.getValouxItemComponent().getVicid(), CommonConstants.COMPONENT_INCLUSION_EXTERNAL);
			if(exclusionBeans != null && exclusionBeans.size() > 0){
				for (ValouxInclusionBean valouxInclusionBean : exclusionBeans) {
					extArray.put(valouxInclusionBean.getInclusion());
				}
			}
			gemstoneComponentModel.setExternalInclusions(extArray);
			
			if (CommonUserUtility.paparameterNullCheckObject(gemstoneComponentBean.getOrigin())) {
				ValouxOriginBean originBean = valouxOriginDao.getCountryOriginNameByCountryId(gemstoneComponentBean.getOrigin());
				if(originBean !=null){
					gemstoneComponentModel.setOriginName(originBean.getNicename());
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentGemstoneBeanByItemAndComponentId");
		return gemstoneComponentModel;
	}
	
	/**
	 * This method get list of shared items to agent in requested state
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getListOfRequestedSharedItemsToAgent(String relationKey)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfRequestedSharedItemsToAgent");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getRequetedItemListSharedToAgent(relationKey);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfRequestedSharedItemsToAgent");
		return sharedBeanList;
	}
	/**
	 * This method will return list of item
	 */
	@Transactional
	public List<ValouxItemModel> getItemListAssociatedWithUserAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception{
		LOGGER.debug("ItemServiceImpl : Enter Method getItemListAssociatedWithUserAndHaveKeyword");
		List<ValouxItemModel> valouxItemModelList = null;
		List<ValouxItemBean> itemBeanList = valouxItemDao.getItemListForGlobalSearchByName(rKey, keyword, null, startRecordNo, numberOfRecords, sortBy, sortOrder);
		if (itemBeanList != null) {
			valouxItemModelList = PrepareModels
					.prepareValouxItemBeanModelListFromBean(itemBeanList);
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getItemListAssociatedWithUserAndHaveKeyword");
		return valouxItemModelList;
	}
	
	/**
	 * This method will return list of item
	 */
	@Transactional
	public List<ValouxItemModel> getItemListAssociatedWithAgentAndHaveKeyword(String rKey,String keyword,Integer startRecordNo,Integer numberOfRecords,String sortBy,String sortOrder) throws Exception{
		LOGGER.debug("ItemServiceImpl : Enter Method getItemListAssociatedWithAgentAndHaveKeyword");
		List<ValouxItemModel> valouxItemModelList = null;
		List<ValouxSharedRequestBean> sharedRequestBeanList = valouxSharedRequestDao.getSharedRequestItemListSharedToUser(rKey, 1, 2, null,null,null,null);
		List<Integer> itemList = new ArrayList<Integer>();
		List<ValouxItemBean> itemBeanList = new ArrayList<ValouxItemBean>();
		if(sharedRequestBeanList!=null && sharedRequestBeanList.size()>0){
			for(ValouxSharedRequestBean sharedReuestBean : sharedRequestBeanList){
				itemList.add(sharedReuestBean.getSharedItemId());
			}
		}
		if(itemList!=null && itemList.size()>0){
		 itemBeanList = valouxItemDao.getItemListForGlobalSearchByName(null, keyword, itemList.toArray(), startRecordNo, numberOfRecords, sortBy, sortOrder);
		 if (itemBeanList != null) {
				valouxItemModelList = PrepareModels
						.prepareValouxItemBeanModelListFromBean(itemBeanList);
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getItemListAssociatedWithAgentAndHaveKeyword");
		return valouxItemModelList;
	}

	/**
	 * This method delete diamond component from item
	 */
	@Transactional
	public void deleteDiamondComponentFromItem(Integer itemId,
			Integer componentId) throws Exception {
		
		LOGGER.info("ItemServiceImpl : Enter Method deleteDiamondComponentFromItem");

		ValouxDiamondBean componentBean = valouxDiamondDao.getComponentDiamondBeanByItemAndComponentId(itemId, componentId);

		if (componentBean != null) {
			
			deleteComponentImagesByComponentId(componentId);
			
			ItemComponentCertificateBean componentCertificateBean = itemComponentCertificateDao.getComponentCertificateBeanByItemAndComponentId(itemId, componentId);
			
			if(componentCertificateBean != null){
				valouxItemDao.deleteAnyBeanByObject(componentCertificateBean);
			}
			
			List<ValouxInclusionBean> inclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(componentId);
			
			if(inclusionBeans != null && inclusionBeans.size() > 0) {
				for (ValouxInclusionBean valouxInclusionBean : inclusionBeans) {
					valouxItemDao.deleteAnyBeanByObject(valouxInclusionBean);
				}
			}
			
			valouxItemDao.deleteAnyBeanByObject(componentBean);
		}

		LOGGER.info("ItemServiceImpl : Exit Method deleteDiamondComponentFromItem");
	}

	/**
	 * @paparam componentId
	 * @throws Exception
	 */
	public void deleteComponentImagesByComponentId(Integer componentId) throws Exception {
		List<ValouxComponentsImageBean> componentsImageBeans = valouxComponentsImageDao.getComponentsImageBeanByComponentIdAndType(componentId);
		
		if(componentsImageBeans != null && componentsImageBeans.size() > 0){
			for (ValouxComponentsImageBean valouxComponentsImageBean : componentsImageBeans) {
				ValouxComponentsImageBean itemImageBean = valouxComponentsImageDao.getItemComponentImageListByIdAndImageId(componentId, valouxComponentsImageBean.getCid());
				if (itemImageBean != null) {
					valouxComponentsImageDao.deleteItemComponentImages(itemImageBean);
					CommonUtility.deleteDocumentInDirectory(itemImageBean.getImgUrl());
				}
			}
		}
	}

	/**
	 * This method delete gemstone component from item
	 */
	@Transactional
	public void deleteGemstoneComponentFromItem(Integer itemId,
			Integer componentId) throws Exception {
		LOGGER.info("ItemServiceImpl : Enter Method deleteGemstoneComponentFromItem");

		ValouxGemstoneBean componentBean = valouxGemstoneDao.getComponentGemstoneBeanByItemAndComponentId(itemId, componentId);

		if (componentBean != null) {
			deleteComponentImagesByComponentId(componentId);
			
			List<ValouxInclusionBean> inclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(componentId);
			
			if(inclusionBeans != null && inclusionBeans.size() > 0) {
				for (ValouxInclusionBean valouxInclusionBean : inclusionBeans) {
					valouxItemDao.deleteAnyBeanByObject(valouxInclusionBean);
				}
			}
			
			valouxItemDao.deleteAnyBeanByObject(componentBean);
		}

		LOGGER.info("ItemServiceImpl : Exit Method deleteGemstoneComponentFromItem");
	}

	/**
	 * This method delete pearl component from item
	 */
	@Transactional
	public void deletePearlComponentFromItem(Integer itemId, Integer componentId)
			throws Exception {
		LOGGER.info("ItemServiceImpl : Enter Method deletePearlComponentFromItem");

		ValouxPearlBean componentBean = valouxPearlDao.getComponentPearlBeanByItemAndComponentId(itemId, componentId);

		if (componentBean != null) {
			deleteComponentImagesByComponentId(componentId);
			valouxItemDao.deleteAnyBeanByObject(componentBean);
		}

		LOGGER.info("ItemServiceImpl : Exit Method deletePearlComponentFromItem");
	}

	/**
	 * This method delete metal component from item
	 */
	@Transactional
	public void deleteMetalComponentFromItem(Integer itemId, Integer componentId)
			throws Exception {
		LOGGER.info("ItemServiceImpl : Enter Method deleteMetalComponentFromItem");

		ValouxMetalBean componentBean = valouxMetalDao.getComponentMetalBeanByItemAndComponentId(itemId, componentId);

		if (componentBean != null) {
			deleteComponentImagesByComponentId(componentId);
			valouxItemDao.deleteAnyBeanByObject(componentBean);
		}

		LOGGER.info("ItemServiceImpl : Exit Method deleteMetalComponentFromItem");
	}
	
	/**
	 * This method will fetch Diamond components
	 */
	@Transactional
	public List<ValouxDiamondModel> getDiamondComponentsByComponentId(int componentId) throws Exception {

		LOGGER.info("ItemServiceImpl : Enter Method getDiamondComponentsByComponentId");

		List<ValouxDiamondModel> diamondComponentModels = new ArrayList<ValouxDiamondModel>();
		List<ValouxDiamondBean> diamondComponentBeans = valouxDiamondDao.getDiamondComponentsByComponentId(componentId);

		if (diamondComponentBeans != null && diamondComponentBeans.size() > 0) {

			for (ValouxDiamondBean valouxComponentDiamondBean : diamondComponentBeans) {

				ValouxDiamondModel diamondModel = new ValouxDiamondModel();
				diamondModel.setItemId(valouxComponentDiamondBean.getValouxItem().getItemId());
				diamondModel.setCreatedBy(valouxComponentDiamondBean.getCreatedBy());
				diamondModel.setCreatedOn(valouxComponentDiamondBean.getCreatedOn());
				diamondModel.setModifiedBy(valouxComponentDiamondBean.getModifiedBy());
				diamondModel.setModifiedOn(valouxComponentDiamondBean.getModifiedOn());
				diamondModel.setClarityId(valouxComponentDiamondBean.getClarityId());
				diamondModel.setColor(valouxComponentDiamondBean.getColor());
				diamondModel.setCut(valouxComponentDiamondBean.getCut());
				diamondModel.setCutlet(valouxComponentDiamondBean.getCutlet());
				diamondModel.setDepth(String.valueOf(valouxComponentDiamondBean.getDepth()));
				diamondModel.setDepthPercentage(String.valueOf(valouxComponentDiamondBean.getDepthPercentage()));
				diamondModel.setFancyColor(valouxComponentDiamondBean.getFancyColor());
				diamondModel.setFluorescence(valouxComponentDiamondBean.getFluorescence());
				diamondModel.setGirdleThicknessDescription(valouxComponentDiamondBean.getGirdleThicknessDescription());
				diamondModel.setDiamondHeight(valouxComponentDiamondBean.getDiamondHeight());
				diamondModel.setItemComponentId(valouxComponentDiamondBean.getValouxItemComponent().getVicid());
				diamondModel.setItemId(valouxComponentDiamondBean.getValouxItem().getItemId());
				diamondModel.setDiamondLength(valouxComponentDiamondBean.getDiamondLength());
				diamondModel.setLengthWidthRatio(String.valueOf(valouxComponentDiamondBean.getLengthWidthRatio()));
				diamondModel.setMarketValue(String.valueOf(valouxComponentDiamondBean.getMarketValue()));
				diamondModel.setMaxDiameter(String.valueOf(valouxComponentDiamondBean.getMaxDiameter()));
				diamondModel.setMinDiameter(String.valueOf(valouxComponentDiamondBean.getMinDiameter()));
				diamondModel.setPlacement(valouxComponentDiamondBean.getPlacement());
				diamondModel.setPolish(valouxComponentDiamondBean.getPolish());
				diamondModel.setSecondaryHue(valouxComponentDiamondBean.getSecondaryHue());
				diamondModel.setShape(valouxComponentDiamondBean.getShape());
				diamondModel.setSingleWeight(String.valueOf(valouxComponentDiamondBean.getSingleWeight()));
				diamondModel.setStatus(Integer.valueOf(valouxComponentDiamondBean.getStatus()));
				diamondModel.setSymmetry(valouxComponentDiamondBean.getSymmetry());
				diamondModel.setTablePercentage(String.valueOf(valouxComponentDiamondBean.getTablePercentage()));
				diamondModel.setThickness(valouxComponentDiamondBean.getThickness());
				diamondModel.setTotalWeight(String.valueOf(valouxComponentDiamondBean.getTotalWeight()));
				diamondModel.setWeightMeasure(valouxComponentDiamondBean.getWeightMeasure());
				diamondModel.setPwidth(String.valueOf(valouxComponentDiamondBean.getDiamondWidth()));

				diamondComponentModels.add(diamondModel);
			}
		}

		LOGGER.info("ItemServiceImpl : Exit Method getDiamondComponentsByComponentId");
		return diamondComponentModels;
	}
	
	/**
	 * This method will fetch Gemstone components
	 */
	@Transactional
	public List<ValouxGemstoneModel> getGemstoneComponentsByComponentId(int componentId) throws Exception {

		LOGGER.info("ItemServiceImpl : Enter Method getGemstoneComponentsByComponentId");

		List<ValouxGemstoneModel> gemstoneComponentModels = new ArrayList<ValouxGemstoneModel>();
		List<ValouxGemstoneBean> gemstoneComponentBeans = valouxGemstoneDao.getGemstoneComponentsByComponentId(componentId);

		if (gemstoneComponentBeans != null && gemstoneComponentBeans.size() > 0) {

			for (ValouxGemstoneBean valouxComponentGemstoneBean : gemstoneComponentBeans) {

				ValouxGemstoneModel gemstoneModel = new ValouxGemstoneModel();
			//	gemstoneModel.setItemId(valouxComponentGemstoneBean.getItemId());
				gemstoneModel.setCreatedBy(valouxComponentGemstoneBean.getCreatedBy());
				gemstoneModel.setCreatedOn(valouxComponentGemstoneBean.getCreatedOn());
				gemstoneModel.setModifiedBy(valouxComponentGemstoneBean.getModifiedBy());
				gemstoneModel.setModifiedOn(valouxComponentGemstoneBean.getModifiedOn());
				gemstoneModel.setCut(valouxComponentGemstoneBean.getCut());
				gemstoneModel.setItemComponentId(valouxComponentGemstoneBean.getValouxItemComponent().getVicid());
				gemstoneModel.setItemId(valouxComponentGemstoneBean.getValouxItem().getItemId());
				gemstoneModel.setPlacement(valouxComponentGemstoneBean.getPlacement());
				gemstoneModel.setShape(valouxComponentGemstoneBean.getShape());
				gemstoneModel.setStatus(Integer.valueOf(valouxComponentGemstoneBean.getStatus()));
				gemstoneModel.setEnhancement(valouxComponentGemstoneBean.getEnhancement());
//				gemstoneModel.setExternalInclusions(valouxComponentGemstoneBean.getExternalInclusions());
				gemstoneModel.setGemstonesType(valouxComponentGemstoneBean.getGemstonesType());
//				gemstoneModel.setInternalInclusions(valouxComponentGemstoneBean.getInternalInclusions());
				//gemstoneModel.setMarketValue(String.valueOf(valouxComponentGemstoneBean.getMarketValue()); need 
				gemstoneModel.setMeasurements(String.valueOf(valouxComponentGemstoneBean.getMeasurements()));
				gemstoneModel.setOrigin(valouxComponentGemstoneBean.getOrigin());
				gemstoneModel.setWeight(String.valueOf(valouxComponentGemstoneBean.getWeight()));

				gemstoneComponentModels.add(gemstoneModel);
			}
		}

		LOGGER.info("ItemServiceImpl : Exit Method getGemstoneComponentsByComponentId");
		return gemstoneComponentModels;
	}
	
	/**
	 * This method will fetch Pearl components
	 */
	@Transactional
	public List<ValouxPearlModel> getPearlComponentsByComponentId(int componentId) throws Exception {

		LOGGER.info("ItemServiceImpl : Enter Method getPearlComponentsByComponentId");

		List<ValouxPearlModel> pearlComponentModels = new ArrayList<ValouxPearlModel>();
		List<ValouxPearlBean> pearlComponentBeans = valouxPearlDao.getPearlComponentsByComponentId(componentId);

		if (pearlComponentBeans != null && pearlComponentBeans.size() > 0) {

			for (ValouxPearlBean valouxComponentPearlBean : pearlComponentBeans) {

				ValouxPearlModel pearlModel = new ValouxPearlModel();
			//	gemstoneModel.setItemId(valouxComponentGemstoneBean.getItemId());
				pearlModel.setCreatedBy(valouxComponentPearlBean.getCreatedBy());
				pearlModel.setCreatedOn(valouxComponentPearlBean.getCreatedOn());
				pearlModel.setModifiedBy(valouxComponentPearlBean.getModifiedBy());
				pearlModel.setModifiedOn(valouxComponentPearlBean.getModifiedOn());
				pearlModel.setAppraisedValue(String.valueOf(valouxComponentPearlBean.getAppraisedValue()));
				pearlModel.setItemComponentId(valouxComponentPearlBean.getValouxItemComponent().getVicid());
				pearlModel.setItemId(valouxComponentPearlBean.getValouxItem().getItemId());
				pearlModel.setBlemish(valouxComponentPearlBean.getBlemish());
				pearlModel.setShape(valouxComponentPearlBean.getShape());
				pearlModel.setStatus(Integer.valueOf(valouxComponentPearlBean.getStatus()));
				pearlModel.setColor(valouxComponentPearlBean.getColor());
				pearlModel.setComposition(valouxComponentPearlBean.getComposition());
				pearlModel.setDrilled(valouxComponentPearlBean.getDrilled());
				pearlModel.setEnhancements(valouxComponentPearlBean.getEnhancements());
				//gemstoneModel.setMarketValue(String.valueOf(valouxComponentGemstoneBean.getMarketValue()); need 
				//gemstoneModel.setMeasurements(String.valueOf(valouxComponentPearlBean.getMeasurements()));
				pearlModel.setOrigin(valouxComponentPearlBean.getOrigin());
				pearlModel.setWeight(String.valueOf(valouxComponentPearlBean.getWeight()));
				
				pearlModel.setLuster(valouxComponentPearlBean.getLuster());
				pearlModel.setMatching(valouxComponentPearlBean.getMatching());
				pearlModel.setMeasurements(String.valueOf(valouxComponentPearlBean.getMeasurements()));
				//gemstoneModel.setModifiedOn(valouxComponentPearlBean.getModifiedOn());
				//gemstoneModel.setOrigin(String.valueOf(valouxComponentPearlBean.getOrigin()));
				pearlModel.setPearlType(valouxComponentPearlBean.getPearlType());
				pearlModel.setPearlsLength(valouxComponentPearlBean.getPearlsLength());
				pearlModel.setPearlsMax(valouxComponentPearlBean.getPearlsMax());
				pearlModel.setPearlsMin(valouxComponentPearlBean.getPearlsMin());
				pearlModel.setPstyle(valouxComponentPearlBean.getPearlsStyle());
			//	gemstoneModel.setShape(valouxComponentPearlBean.getShape());
				pearlModel.setStyleUserEntered(valouxComponentPearlBean.getStyleUserEntered());
				//gemstoneModel.setWeight(String.valueOf(valouxComponentPearlBean.getWeight()));

				pearlComponentModels.add(pearlModel);
			}
		}

		LOGGER.info("ItemServiceImpl : Exit Method getPearlComponentsByComponentId");
		return pearlComponentModels;
	}
	/**
	 * This method will fetch Metal components
	 */
	@Transactional
	public List<ValouxMetalModel> getMetalComponentsByComponentId(int componentId) throws Exception {

		LOGGER.info("ItemServiceImpl : Enter Method getMetalComponentsByComponentId");

		List<ValouxMetalModel> metalComponentModels = new ArrayList<ValouxMetalModel>();
		List<ValouxMetalBean> metalComponentBeans = valouxMetalDao.getMetalComponentsByComponentId(componentId);

		if (metalComponentBeans != null && metalComponentBeans.size() > 0) {

			for (ValouxMetalBean valouxComponentMetalBean : metalComponentBeans) {

				ValouxMetalModel metalModel = new ValouxMetalModel();
				metalModel.setCreatedBy(valouxComponentMetalBean.getCreatedBy());
				metalModel.setCreatedOn(valouxComponentMetalBean.getCreatedOn());
				metalModel.setModifiedBy(valouxComponentMetalBean.getModifiedBy());
				metalModel.setModifiedOn(valouxComponentMetalBean.getModifiedOn());
				metalModel.setAppraisedValue(String.valueOf(valouxComponentMetalBean.getAppraisedValue()));
				metalModel.setItemComponentId(valouxComponentMetalBean.getValouxItemComponent().getVicid());
				metalModel.setItemId(valouxComponentMetalBean.getValouxItem().getItemId());
				metalModel.setMarketValue(String.valueOf(valouxComponentMetalBean.getMarketValue()));
				metalModel.setPurity(valouxComponentMetalBean.getPurity());
				metalModel.setStatus(Integer.valueOf(valouxComponentMetalBean.getStatus()));
				metalModel.setColor(valouxComponentMetalBean.getColor());
				metalModel.setTypeDetermined(valouxComponentMetalBean.getTypeDetermined());
				metalModel.setTypeSpecified(valouxComponentMetalBean.getTypeSpecified());
				metalModel.setWeight(String.valueOf(valouxComponentMetalBean.getWeight()));
				
				metalModel.setMeasurements(String.valueOf(valouxComponentMetalBean.getMeasurements()));
				
				metalComponentModels.add(metalModel);
			}
		}

		LOGGER.info("ItemServiceImpl : Exit Method getMetalComponentsByComponentId");
		return metalComponentModels;
	}

	/**
	 * This method will fetch user shared with agent
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getUserListSharedToAgent(String sharedTo) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedContacts");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getUserListSharedToAgent(sharedTo);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedContacts");
		return sharedBeanList;
	}

	/**
	 * This method will fetch user shared with agent
	 */
	@Transactional
	public List<ValouxSharedRequestBean> getUserListSharedToAgentSharedByUser(
			String sharedBy, String sharedTo) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfSharedContacts");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getUserListSharedToAgentSharedByUser(sharedBy, sharedTo);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfSharedContacts");
		return sharedBeanList;
	}
	
	/**
	 *  Method for updating item component price property details
	 */
	@Transactional
	public void addUpdateValouxItemComponentModelPriceDetails(
			ValouxItemComponentModel valouxItemComponentModel) throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method addUpdateValouxItemComponentModelPriceDetails");
		
		if(valouxItemComponentModel != null){
			ValouxItemComponentBean componentBean = valouxItemComponentDao.getItemComponentByItemAndComponentId(valouxItemComponentModel.getItemId(), valouxItemComponentModel.getVicid());
			if(componentBean != null){
				if(CommonUserUtility.paparameterNullCheckStringObject(valouxItemComponentModel.getAdjustmentPrice())) {
					componentBean.setAdjustmentPrice(Double.valueOf(valouxItemComponentModel.getAdjustmentPrice()));
				} else {
					componentBean.setAdjustmentPrice(null);
				}
				
				componentBean.setBrandPriceAdjustmentNotes(valouxItemComponentModel.getBrandPriceAdjustmentNotes());
				
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getBrandPriceAdjustmentOperator())) {
					componentBean.setBrandPriceAdjustmentOperator(valouxItemComponentModel.getBrandPriceAdjustmentOperator());
				}
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getBrandPriceAdjustmentType())) {
					componentBean.setBrandPriceAdjustmentType(valouxItemComponentModel.getBrandPriceAdjustmentType());
				}
				if(CommonUserUtility.paparameterNullCheckStringObject(valouxItemComponentModel.getBrandPriceAdjustmentValue())) {
					componentBean.setBrandPriceAdjustmentValue(Double.valueOf(valouxItemComponentModel.getBrandPriceAdjustmentValue()));
				} else {
					componentBean.setBrandPriceAdjustmentValue(null);
				}
				
				if(CommonUserUtility.paparameterNullCheckStringObject(valouxItemComponentModel.getCurrentUnitPrice())) {
					componentBean.setCurrentUnitPrice(Double.valueOf(valouxItemComponentModel.getCurrentUnitPrice()));
				} else {
					componentBean.setCurrentUnitPrice(null);
				}
				
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getFinalPrice())) {
					componentBean.setFinalPrice(valouxItemComponentModel.getFinalPrice());
				} else {
					componentBean.setFinalPrice(null);
				}
				
				componentBean.setGeneralPriceAdjustmentNotes(valouxItemComponentModel.getGeneralPriceAdjustmentNotes());
				
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getGeneralPriceAdjustmentOperator())) {
					componentBean.setGeneralPriceAdjustmentOperator(valouxItemComponentModel.getGeneralPriceAdjustmentOperator());
				}
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getGeneralPriceAdjustmentType())) {
					componentBean.setGeneralPriceAdjustmentType(valouxItemComponentModel.getGeneralPriceAdjustmentType());
				}
				if(CommonUserUtility.paparameterNullCheckStringObject(valouxItemComponentModel.getGeneralPriceAdjustmentValue())) {
					componentBean.setGeneralPriceAdjustmentValue(Double.valueOf(valouxItemComponentModel.getGeneralPriceAdjustmentValue()));
				} else {
					componentBean.setGeneralPriceAdjustmentValue(null);
				}
				
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getMarketValue())) {
					componentBean.setMarketValue(valouxItemComponentModel.getMarketValue());
				} else {
					componentBean.setMarketValue(null);
				}
				
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentModel.getPurchasePrice())) {
					componentBean.setPurchasePrice(valouxItemComponentModel.getPurchasePrice());
				} else {
					componentBean.setPurchasePrice(null);
				}
				valouxItemDao.addUpdateItemComponent(componentBean);
			}
		}
		LOGGER.debug("ItemServiceImpl : Enter Method addUpdateValouxItemComponentModelPriceDetails");
	}
	
	/**
	 *  Method for fetching item component diamone specify price
	 */
	@Transactional
	public void getItemComponentDiamondSpecifyPrice(
			ValouxDiamondModel diamondModel) throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getItemComponentDiamondSpecifyPrice");
		
		ValouxDiamondMasterPriceBean priceBean = new ValouxDiamondMasterPriceBean();
		
		String color = null;
		String shape = null;
		Double weight = 0d;
		
		if(CommonUserUtility.paparameterNullCheckObject(diamondModel.getColor())){
			color = ItemComponentHelper.DIAMOND_COLOR_MAP.get(diamondModel.getColor());
		}
		if(CommonUserUtility.paparameterNullCheckObject(diamondModel.getShape())){
			shape = ItemComponentHelper.getDiamondShapeName(ItemComponentHelper.DIAMOND_SHAPE_MAP.get(diamondModel.getShape()), CommonConstants.ONE); 
		}
		if(CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getTotalWeight())){
			weight = Double.valueOf(diamondModel.getTotalWeight());
		}
		
		Double caratFrom = null;
		Double caratTo = null;
		Double specifiedValue = null;
		
		if(weight >= 0.18 && weight <= 0.22 ){
			caratFrom = 0.18;
			caratTo = 0.22;
			
		} else if(weight >= 0.23 && weight <= 0.29 ){
			caratFrom = 0.23;
			caratTo = 0.29;
			
		} else if(weight >= 0.3 && weight <= 0.39 ){
			caratFrom = 0.3;
			caratTo = 0.39;
			
		} else if(weight >= 0.4 && weight <= 0.44 ){
			caratFrom = 0.4;
			caratTo = 0.44;
			
		} else if(weight >= 0.45 && weight <= 0.49 ){
			caratFrom = 0.45;
			caratTo = 0.49;
			
		} else if(weight >= 0.5 && weight <= 0.69 ){
			caratFrom = 0.5;
			caratTo = 0.69;
			
		} else if(weight >= 0.7 && weight <= 0.79 ){
			caratFrom = 0.7;
			caratTo = 0.79;
			
		} else if(weight >= 0.8 && weight <= 0.89 ){
			caratFrom = 0.8;
			caratTo = 0.89;
			
		} else if(weight >= 0.9 && weight <= 0.99 ){
			caratFrom = 0.9;
			caratTo = 0.99;
			
		} else if(weight >= 1.0 && weight <= 1.24 ){
			caratFrom = 1.0;
			caratTo = 1.24;
			
		} else if(weight >= 1.25 && weight <= 1.49 ){
			caratFrom = 1.25;
			caratTo = 1.49;
			
		} else if(weight >= 1.5 && weight <= 1.99 ){
			caratFrom = 1.5;
			caratTo = 1.99;
			
		} else if(weight >= 2.0 && weight <= 2.99 ){
			caratFrom = 2.0;
			caratTo = 2.99;
			
		} else if(weight >= 3.0 && weight <= 3.99 ){
			caratFrom = 3.0;
			caratTo = 3.99;
			
		} else if(weight >= 4.0 && weight <= 4.99 ){
			caratFrom = 4.0;
			caratTo = 4.99;
			
		} else if(weight >= 5.0 && weight <= 5.99 ){
			caratFrom = 5.0;
			caratTo = 5.99;
			
		} else {
			caratFrom = 0.0;
			caratTo = 0.0;
		}
		priceBean = valouxDiamondMasterPriceDao.getItemComponentDiamondSpecifyPrice(caratFrom, caratTo, shape, color);
		
		if(priceBean != null){
			Double clarity = ItemHelper.getClarityValueById(diamondModel.getClarityId(), priceBean);
			// clarity '\'1 Flawless, 2 Internally Flawless,3 VVS1, 4 VVS2, 5 VS1, 6 VS2, 7 Si1, 8 Si2, 9 Si3, 10 I1, 11 I2, 12 I3, 13Opaque\',',
			if(clarity != null){
				
				specifiedValue = weight * clarity * CommonConstants.HUNDRED;
			}
			if(specifiedValue != null){
				diamondModel.setSpecifiedValue(CommonConstants.DECIMAL_FORMAT.format(specifiedValue));
				diamondModel.setSpecifiedDate(priceBean.getPriceDate());
			} else {
				diamondModel.setSpecifiedValue(CommonConstants.DOUBLE_ZERO.toString());
			}
		} else {
			diamondModel.setSpecifiedValue(CommonConstants.DOUBLE_ZERO.toString());
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getItemComponentDiamondSpecifyPrice");
	}

	@Transactional
	public void getItemComponentMetalSpecifyPrice(ValouxMetalModel metalModel)
			throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getItemComponentMetalSpecifyPrice");
		
		ValouxMetalsMasterPriceBean priceBean = new ValouxMetalsMasterPriceBean();
		String metalName = null;
		
		if(CommonUserUtility.paparameterNullCheckObject(metalModel.getMetalsType())){
			metalName = ItemComponentHelper.METAL_NAME_MAP.get(metalModel.getMetalsType());
		}
		
		priceBean = valouxMetalMasterPriceDao.getItemComponentMetalSpecifyPrice(metalName);
		
		if(priceBean != null){
			Double perUnitPrice = ItemHelper.getMetalComponentPerUnitPrice(priceBean.getUnitPrice());
			
			if(perUnitPrice != null){
				
				Double specifiedPrice =  null;
				
				if(metalModel.getMetalsType().equals(1)){ //Gold
					specifiedPrice = ItemHelper.getComponentMetalGoldSpecifiedPrice(perUnitPrice, metalModel.getPurity(), Double.valueOf(metalModel.getWeight()));
				} else if(metalModel.getMetalsType().equals(2)){ //Silver
					specifiedPrice = ItemHelper.getComponentMetalSilverSpecifiedPrice(perUnitPrice, metalModel.getPurity(), Double.valueOf(metalModel.getWeight()));
				} else if(metalModel.getMetalsType().equals(3)){ //Platinum
					specifiedPrice = ItemHelper.getComponentMetalPlatinumSpecifiedPrice(perUnitPrice, metalModel.getPurity(), Double.valueOf(metalModel.getWeight()));
				} else if(metalModel.getMetalsType().equals(4) || metalModel.getMetalsType().equals(5)){ //Rhodium //Palladium
					Integer purity = 1;
					specifiedPrice = ItemHelper.getComponentMetalAllSpecifiedPrice(perUnitPrice, purity, Double.valueOf(metalModel.getWeight()));
				} else {
					specifiedPrice = CommonConstants.DOUBLE_ZERO;
				}
				
				if(specifiedPrice != null){
					metalModel.setSpecifiedValue(CommonConstants.DECIMAL_FORMAT.format(specifiedPrice));
					metalModel.setSpecifiedDate(priceBean.getCreatedOn());
				} else {
					metalModel.setSpecifiedValue(CommonConstants.DOUBLE_ZERO.toString());
				}
				
			} else {
				metalModel.setSpecifiedValue(CommonConstants.DOUBLE_ZERO.toString());
			}
		} else {
			metalModel.setSpecifiedValue(CommonConstants.DOUBLE_ZERO.toString());
		}
		
		LOGGER.debug("ItemServiceImpl : Exit Method getItemComponentMetalSpecifyPrice");
	}

	@Transactional
	public void callMetalPriceUpdateService() throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method callMetalPriceUpdateService");
		
		ItemPriceModel itemPriceModel = new ItemPriceModel();
		
		try {
			for (int metalCount = 1; metalCount <= 5; metalCount++) {
				
				ValouxMetalsMasterPriceBean masterPriceBean = new ValouxMetalsMasterPriceBean();
				
				//current date
				String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				
				itemPriceModel.setNewestAvailableDate(startDate);
				itemPriceModel.setMetalType(metalCount);
				itemPriceModel = getComponentMetalAMPMPriceByDate(itemPriceModel, startDate);
				if(itemPriceModel !=null){
					if(!itemPriceModel.isDataAvailable()){
						
						startDate = itemPriceModel.getNewestAvailableDate();
						itemPriceModel = getComponentMetalAMPMPriceByDate(itemPriceModel, startDate);
					}
					
					while(itemPriceModel.getPriceAMorPM() == null){
						Date oldStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(oldStartDate);
						calendar.add(Calendar.DATE, -1);
						
						Date newStartDate = calendar.getTime();
						startDate = new SimpleDateFormat("yyyy-MM-dd").format(newStartDate);
						itemPriceModel = getComponentMetalAMPMPriceByDate(itemPriceModel, startDate);
						itemPriceModel.setNewestAvailableDate(startDate);
					} 
					if(itemPriceModel.getPriceAMorPM() != null){
						
						Date createdOn = new SimpleDateFormat("yyyy-MM-dd").parse(itemPriceModel.getNewestAvailableDate());
						String metalType = ItemComponentHelper.METAL_NAME_MAP.get(itemPriceModel.getMetalType());
						
						masterPriceBean = valouxMetalMasterPriceDao.getMetalsMasterPriceByTypeAndDate(createdOn, metalType);
						
						if(masterPriceBean == null){
							masterPriceBean = new ValouxMetalsMasterPriceBean();
						}
						
						ItemHelper.getValouxMetalsMasterPriceBean(itemPriceModel, masterPriceBean);
						
						if(masterPriceBean != null){
							valouxMetalMasterPriceDao.addValouxMetalsMasterPrice(masterPriceBean);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.debug("ItemServiceImpl : Exit Method callMetalPriceUpdateService");
	}
	
	/**
	 * @paparam itemPriceModel
	 * @paparam startDate
	 * @return
	 * @throws Exception
	 */
	public static ItemPriceModel getComponentMetalAMPMPriceByDate(ItemPriceModel itemPriceModel, String startDate) throws Exception{
		
		LOGGER.debug("ItemServiceImpl : Enter Method getComponentMetalAMPMPriceByDate");
		
		String URL_QUANDL = null;
		String TEXT_AM = null;
		String TEXT_PM = null;
		Double pricePM = null;
		Double priceAM = null;
		Double priceAMorPM = null;
		Integer indexAM = null;
		Integer indexPM = null;
		
		try {
			//Api key
//			String api_key = "m7BGDo1H6Vt7uaikK5VS";
			
			String valouxPropertyFileName = "valoux.properties";
			Properties prop = new Properties();
			prop = CommonUtility.getProperty(valouxPropertyFileName);
		
			String api_key = prop.getProperty("valoux.metal.quandl.api.key");
			
			if(itemPriceModel.getMetalType().equals(CommonConstants.METAL_GOLD_ID)){
				String gold_url = prop.getProperty("valoux.metal.quandl.gold.url");
				//Quandl url
				URL_QUANDL = gold_url+"&api_key="+api_key;
				//Constants
				TEXT_AM = "USD (AM)";
				TEXT_PM = "USD (PM)";
			} else if(itemPriceModel.getMetalType().equals(CommonConstants.METAL_SILVER_ID)){
				String silver_url = prop.getProperty("valoux.metal.quandl.silver.url");
				//Quandl url
				URL_QUANDL = silver_url+"&api_key="+api_key;
				//Constants
				TEXT_AM = "USD";
			} else if(itemPriceModel.getMetalType().equals(CommonConstants.METAL_PLATINUM_ID)){
				String platimun_url = prop.getProperty("valoux.metal.quandl.platinum.url");
				//Quandl url
				URL_QUANDL = platimun_url+"&api_key="+api_key;
				//Constants
				TEXT_AM = "USD AM";
				TEXT_PM = "USD PM";
			} else if(itemPriceModel.getMetalType().equals(CommonConstants.METAL_RHODIUM_ID)){
				String rhodium_url = prop.getProperty("valoux.metal.quandl.rhodium.url");
				//Quandl url
				URL_QUANDL = rhodium_url+"&api_key="+api_key;
				//Constants
				TEXT_AM = "New York 9:30";
				TEXT_PM = "New York 15:00";
			} else if(itemPriceModel.getMetalType().equals(CommonConstants.METAL_PALLADIUM_ID)){
				String rhodium_url = prop.getProperty("valoux.metal.quandl.palladium.url");
				//Quandl url
				URL_QUANDL = rhodium_url+"&api_key="+api_key;
				//Constants
				TEXT_AM = "USD AM";
				TEXT_PM = "USD PM";
			} 
			
			URL url = new URL(URL_QUANDL + "&start_date=" +startDate + "&end_date=" +startDate);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "";
			
			StringBuffer json = new StringBuffer();
			while ((output = br.readLine()) != null) {
				json.append(output);
			}
			//Json response
			JSONObject jsonResponse = new JSONObject(json.toString());
			//dataset object
			JSONObject datasetObject = jsonResponse.optJSONObject("dataset");
			
			String newestAvailableDate = datasetObject.optString("newest_available_date");
			if(CommonUserUtility.paparameterNullCheckStringObject(newestAvailableDate)){
				itemPriceModel.setNewestAvailableDate(newestAvailableDate);
			}
			
			//JSONArray column
			JSONArray jsonArrayColumnNames = datasetObject.optJSONArray("column_names");
			
			//Fetching index column number for USD (AM) USD (PM)
			if(jsonArrayColumnNames != null  && jsonArrayColumnNames.length()>0){
				for (int i = 0; i < jsonArrayColumnNames.length(); i++) {
					if(jsonArrayColumnNames.get(i).equals(TEXT_AM)){
						indexAM = i;
					}
					if(jsonArrayColumnNames.get(i).equals(TEXT_PM)){
						indexPM = i;
					}
				}
			}
			
			//data array
			JSONArray dataArray = datasetObject.optJSONArray("data");
			
			if(dataArray!=null && dataArray.length()>0){
				JSONArray jArray = dataArray.getJSONArray(0);
				
				if(jArray == null){
					itemPriceModel.setDataAvailable(true);
				} else {
					if(CommonUserUtility.paparameterNullCheckObject(indexAM)){
						
						if(jArray.get(indexAM) != null && !"null".equalsIgnoreCase(jArray.get(indexAM).toString())){
							priceAM = jArray.getDouble(indexAM);
						}
					}
					
					if(CommonUserUtility.paparameterNullCheckObject(indexPM)){
						
						if(jArray.get(indexPM) != null  && !"null".equalsIgnoreCase(jArray.get(indexPM).toString())){
							pricePM = jArray.getDouble(indexPM);
						}
					}
				}
			}
			
			if(pricePM != null){
				priceAMorPM = pricePM;
			} else if(priceAM != null){
				priceAMorPM = priceAM;
			}
			
			itemPriceModel.setPriceAMorPM(priceAMorPM);
			conn.disconnect();
		} catch (Exception e) {
			LOGGER.error("Error - ", e);
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getComponentMetalAMPMPriceByDate");
		
		return itemPriceModel;
	}

	@Transactional
	public void updateItemPriceProperties(ValouxItemModel itemModel)
			throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method updateItemPriceProperties");
		ValouxItemBean itemBean = valouxItemDao.getItemDetailByItemIdAndRkey(itemModel.getItemId(), itemModel.getrKey());
		if(itemBean != null){
			ItemHelper.populateItemPriceBeanFromModel(itemBean, itemModel);
			
			valouxItemDao.updateItemPriceProperties(itemBean);
		}
		
		LOGGER.debug("ItemServiceImpl : Enter Method updateItemPriceProperties");
	}

	@Transactional
	public ValouxItemModel getUpdatedItemPriceProperties(Integer itemId) throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getUpdatedItemPriceProperties");
		ValouxItemModel itemModel = new ValouxItemModel();
		ValouxItemBean itemBean = valouxItemDao.getValouxItemDetailsById(itemId);
		if(itemBean != null){
			ItemHelper.populateItemPriceModelFromBean(itemBean, itemModel);
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getUpdatedItemPriceProperties");
		return itemModel;
	}

	@Transactional
	public void updateItemMarketValueFromComponents(Integer itemId)
			throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method updateItemMarketValueFromComponents");
		
		ValouxItemBean itemBean = valouxItemDao.getValouxItemDetailsById(itemId);
		if(itemBean != null){
			Double marketValue = 0.0;
			
			List<ValouxItemComponentBean> componentBeans = valouxItemComponentDao.getComponentsByItemId(itemId);
			if(componentBeans != null && componentBeans.size() > 0){
				for (ValouxItemComponentBean valouxItemComponentBean : componentBeans) {
					Integer quantity = 1;
					if(valouxItemComponentBean.getQuantity() > 0){
						quantity = valouxItemComponentBean.getQuantity();
					}
					Double finalPrice = 0.0;
					if(valouxItemComponentBean.getFinalPrice() != null){
						finalPrice = valouxItemComponentBean.getFinalPrice();
					}
					marketValue = marketValue + (quantity * finalPrice);
				}
			}
			itemBean.setMarketValue(marketValue);
			Double newFinalPrice = ItemHelper.getNewFinalAdjustedPrice(itemBean, marketValue);
			itemBean.setFinalPrice(newFinalPrice);
			valouxItemDao.updateItemPriceProperties(itemBean);
			
		}
		LOGGER.debug("ItemServiceImpl : Exit Method updateItemMarketValueFromComponents");
	}
	
	
	@Transactional
	public List<ValouxItemBean> getItemDetailByStoreIdAndRKey(Integer storeId,String rKey)
			throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getItemDetailByStoreIdAndRKey");
		List<ValouxItemBean> itemBeanList = valouxItemDao.getItemDetailByStoreIdAndRkey(storeId, rKey);
			
		LOGGER.debug("ItemServiceImpl : Enter Method getItemDetailByStoreIdAndRKey");
		return itemBeanList;
	}

	@Transactional
	public void addInclusionsTypeFromRequest(
			List<ValouxInclusionModel> valouxInclusionModels) throws Exception {
				
		if(valouxInclusionModels != null && valouxInclusionModels.size() >0 ){
			
			Integer componentId = valouxInclusionModels.get(0).getItemComponentId();
			Integer inclusionType = valouxInclusionModels.get(0).getInclusionType();
			
			if(componentId != null && inclusionType != null){
				List<ValouxInclusionBean> inclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(componentId, inclusionType);
				
				if(inclusionBeans != null && inclusionBeans.size() > 0){
					valouxInclusionDao.deleteInclusionsTypeFromRequest(inclusionBeans);
				}
				
				List<ValouxInclusionBean> valouxInclusionBeans = new ArrayList<ValouxInclusionBean>();
				
				for (ValouxInclusionModel valouxInclusionModel : valouxInclusionModels) {
					ValouxInclusionBean inclusionBean = new ValouxInclusionBean();
					
					inclusionBean.setInclusion(valouxInclusionModel.getInclusion());
					inclusionBean.setInclusionType(valouxInclusionModel.getInclusionType());
					inclusionBean.setCreatedBy(valouxInclusionModel.getCreatedBy());
					inclusionBean.setCreatedOn(valouxInclusionModel.getCreatedOn());
					
					ValouxItemComponentBean componentBean = new ValouxItemComponentBean();
					componentBean.setVicid(valouxInclusionModel.getItemComponentId());
					inclusionBean.setValouxItemComponent(componentBean);
					
					inclusionBean.setModifiedBy(valouxInclusionModel.getModifiedBy());
					inclusionBean.setModifiedOn(valouxInclusionModel.getModifiedOn());
					inclusionBean.setStatus(valouxInclusionModel.getStatus());
					valouxInclusionBeans.add(inclusionBean);
				}
				valouxInclusionDao.saveInclusionsTypeFromRequest(valouxInclusionBeans);
			}
		}
	}

	@Transactional
	public void deleteAllInclusionsByComponentAndType(Integer componentId,
			Integer inclusionType) throws Exception {
		
		List<ValouxInclusionBean> inclusionBeans = valouxInclusionDao.getValouxInclusionBeanListByComponentAndType(componentId, inclusionType);
		
		if(inclusionBeans != null && inclusionBeans.size() > 0){
			valouxInclusionDao.deleteInclusionsTypeFromRequest(inclusionBeans);
		}
		
	}

	@Transactional
	public ValouxItemComponentModel getComponentsById(Integer componentId)
			throws Exception {
		ValouxItemComponentModel itemModel = null;
		ValouxItemComponentBean valouxComponentItemBean = valouxItemComponentDao.getItemComponentByComponentId(componentId);
		if(valouxComponentItemBean != null){
			itemModel = new ValouxItemComponentModel();
			itemModel.setItemId(valouxComponentItemBean.getValouxItem().getItemId());
			itemModel.setCreatedBy(valouxComponentItemBean.getCreatedBy());
			itemModel.setCreatedOn(valouxComponentItemBean.getCreatedOn());
			itemModel.setModifiedBy(valouxComponentItemBean.getModifiedBy());
			itemModel.setModifiedOn(valouxComponentItemBean.getModifiedOn());
			itemModel.setName(valouxComponentItemBean.getName());
			itemModel.setComponentsType(valouxComponentItemBean.getComponentsType());
			itemModel.setVicid(valouxComponentItemBean.getVicid());
			itemModel.setQuantity(valouxComponentItemBean.getQuantity());
			itemModel.setVicStatus(valouxComponentItemBean.getVicStatus());
		}
		return itemModel;
	}

	@Transactional
	public void addValouxComponentsCertificateImageDetails(
			List<ValouxComponentsImageModel> valouxComponentsImageModel)
			throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method addValouxComponentsCertificateImageDetails ");

		if (valouxComponentsImageModel != null && valouxComponentsImageModel.size() > 0) {
			
			for (ValouxComponentsImageModel imageModel : valouxComponentsImageModel) {

				List<ValouxComponentsImageBean> imageBeans = valouxComponentsImageDao.getItemComponentImageByIdAndType(imageModel.getItemComponentId(), imageModel.getFileType());
				
				boolean isNotAvailableFlag = true;
				if(imageBeans!= null && imageBeans.size() > 0){
					for (ValouxComponentsImageBean valouxComponentsImageBean : imageBeans) {
						if(imageModel.getImgName().equals(valouxComponentsImageBean.getImgName())){
							isNotAvailableFlag = false;
							break;
						} else if(imageModel.getImgName().contains(CommonConstants.DIAMOND_PROPIMG) && valouxComponentsImageBean.getImgName().contains(CommonConstants.DIAMOND_PROPIMG)){
							valouxComponentsImageDao.deleteItemComponentImages(valouxComponentsImageBean);
							CommonUtility.deleteDocumentInDirectory(valouxComponentsImageBean.getImgUrl());
							isNotAvailableFlag = true;
							break;
						} else if(imageModel.getImgName().contains(CommonConstants.DIAMOND_PLOTIMG) && valouxComponentsImageBean.getImgName().contains(CommonConstants.DIAMOND_PLOTIMG)){
							valouxComponentsImageDao.deleteItemComponentImages(valouxComponentsImageBean);
							CommonUtility.deleteDocumentInDirectory(valouxComponentsImageBean.getImgUrl());
							isNotAvailableFlag = true;
							break;
						} else if(imageModel.getImgName().contains(CommonConstants.DIAMOND_ERPTIMG) && valouxComponentsImageBean.getImgName().contains(CommonConstants.DIAMOND_ERPTIMG)){
							valouxComponentsImageDao.deleteItemComponentImages(valouxComponentsImageBean);
							CommonUtility.deleteDocumentInDirectory(valouxComponentsImageBean.getImgUrl());
							isNotAvailableFlag = true;
							break;
						} else if(imageModel.getImgName().contains(CommonConstants.DIAMOND_CERTIFICATE) && valouxComponentsImageBean.getImgName().contains(CommonConstants.DIAMOND_CERTIFICATE)){
							valouxComponentsImageDao.deleteItemComponentImages(valouxComponentsImageBean);
							CommonUtility.deleteDocumentInDirectory(valouxComponentsImageBean.getImgUrl());
							isNotAvailableFlag = true;
							break;
						}
					}
				}
				
				if(isNotAvailableFlag){
					ValouxComponentsImageBean imageBean = new ValouxComponentsImageBean();

					if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getCreatedBy())) {
						imageBean.setCreatedBy(imageModel.getCreatedBy());
					}
					if (CommonUserUtility.paparameterNullCheckObject(imageModel.getCreatedOn())) {
						imageBean.setCreatedOn(imageModel.getCreatedOn());
					}
					if (CommonUserUtility.paparameterNullCheckObject(imageModel.getFileType())) {
						imageBean.setFileType(imageModel.getFileType().byteValue());
					}
					if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getImgName())) {
						imageBean.setImgName(imageModel.getImgName());
					}
					if (CommonUserUtility.paparameterNullCheckObject(imageModel.getImgStatus())) {
						imageBean.setImgStatus(imageModel.getImgStatus().byteValue());
					}
					if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getImgUrl())) {
						imageBean.setImgUrl(imageModel.getImgUrl());
					}
					if (CommonUserUtility.paparameterNullCheckObject(imageModel.getItemComponentId())) {
						ValouxItemComponentBean valouxItemComponent = new ValouxItemComponentBean();
						valouxItemComponent.setVicid(imageModel.getItemComponentId());
						imageBean.setValouxItemComponent(valouxItemComponent);
					}
					if (CommonUserUtility.paparameterNullCheckStringObject(imageModel.getModifiedBy())) {
						imageBean.setModifiedBy(imageModel.getModifiedBy());
					}
					if (CommonUserUtility.paparameterNullCheckObject(imageModel.getModifiedOn())) {
						imageBean.setModifiedOn(imageModel.getModifiedOn());
					}
					valouxComponentsImageDao.saveValouxComponentsCertificateImage(imageBean);
				}
			}
		}

		LOGGER.debug("ItemServiceImpl : Exit Method addValouxComponentsCertificateImageDetails");
		
	}

	@Transactional
	public boolean getImageModelByComponentIdNameFileType(
			Integer componentId, Integer fileType, String imageName)
			throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getImageModelByComponentIdNameFileType");
		ValouxComponentsImageBean imageBean = valouxComponentsImageDao.getItemComponentImageByIdNameAndType(componentId, fileType, imageName);
		if(imageBean != null){
			return true;
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getImageModelByComponentIdNameFileType");
		return false;
	}

	@Transactional
	public List<ValouxSharedRequestBean> getListOfRequestedAndAcceptedSharedItemsToAgent(
			String relationKey) throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method getListOfRequestedSharedItemsToAgent");
		List<ValouxSharedRequestBean> sharedBeanList = valouxSharedRequestDao.getListOfRequestedAndAcceptedSharedItemsToAgent(relationKey);
		LOGGER.debug("ItemServiceImpl : Exit Method getListOfRequestedSharedItemsToAgent");
		return sharedBeanList;
	}

	@Transactional
	public void callItemMarketPriceUpdateService() throws Exception {
		LOGGER.debug("ItemServiceImpl : Enter Method callItemMarketPriceUpdateService");
		List<ValouxItemBean> itemBeans = valouxItemDao.getAllListOfItems();
		
		if(itemBeans != null && itemBeans.size() > 0) {
			for (ValouxItemBean valouxItemBean : itemBeans) {
				Double itemSpecifiedValue = 0d;
				boolean isItemPurchasedValue = false;
				
				//If item purchased price available no need to update item market value.
				if(CommonUserUtility.paparameterNullCheckObject(valouxItemBean.getPurchasePrice())){
					isItemPurchasedValue = true;
				}
				
				if(valouxItemBean.getValouxItemComponents() != null && valouxItemBean.getValouxItemComponents().size() > 0){
					List<ValouxItemComponentBean> componentBeans = valouxItemBean.getValouxItemComponents();
					
					for (ValouxItemComponentBean valouxItemComponentBean : componentBeans) {
						Double itemComponentSpecifiedValue = 0d;
						Integer quantity = 1;
						
						//Item component quantity
						if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentBean.getQuantity())){
							quantity = valouxItemComponentBean.getQuantity();
						}
						
						if (Integer.valueOf(valouxItemComponentBean.getComponentsType()).equals(CommonConstants.COMPONENT_DIAMOND)) {
							
							if(valouxItemComponentBean.getValouxDiamonds() != null && valouxItemComponentBean.getValouxDiamonds().size() > 0) {
								List<ValouxDiamondBean> diamondBeans = valouxItemComponentBean.getValouxDiamonds();
								for (ValouxDiamondBean valouxDiamondBean : diamondBeans) {
									
									ValouxDiamondModel diamondModel = new ValouxDiamondModel();
									if(CommonUserUtility.paparameterNullCheckObject(valouxDiamondBean.getClarityId())){
										diamondModel.setClarityId(valouxDiamondBean.getClarityId());
									} 
									
									if(CommonUserUtility.paparameterNullCheckObject(valouxDiamondBean.getColor())){
										diamondModel.setColor(valouxDiamondBean.getColor());
									} 
									
									if(CommonUserUtility.paparameterNullCheckObject(valouxDiamondBean.getShape())){
										diamondModel.setShape(valouxDiamondBean.getShape());
									} 
									
									if(CommonUserUtility.paparameterNullCheckObject(valouxDiamondBean.getSingleWeight())){
										diamondModel.setTotalWeight(valouxDiamondBean.getSingleWeight().toString());
									}
									
									getItemComponentDiamondSpecifyPrice(diamondModel);
									
									if(CommonUserUtility.paparameterNullCheckStringObject(diamondModel.getSpecifiedValue())){
										itemComponentSpecifiedValue = Double.valueOf(diamondModel.getSpecifiedValue());
									}
								}
							}
						} else if (Integer.valueOf(valouxItemComponentBean.getComponentsType()).equals(CommonConstants.COMPONENT_GEMSTONES)) {
							
							if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentBean.getPurchasePrice())){
								itemComponentSpecifiedValue = valouxItemComponentBean.getPurchasePrice();
							}
							
						} else if (Integer.valueOf(valouxItemComponentBean.getComponentsType()).equals(CommonConstants.COMPONENT_PEARLS)) {
							
							if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentBean.getPurchasePrice())){
								itemComponentSpecifiedValue = valouxItemComponentBean.getPurchasePrice();
							}
							
						} else if (Integer.valueOf(valouxItemComponentBean.getComponentsType()).equals(CommonConstants.COMPONENT_METALS)) {
							
							if(valouxItemComponentBean.getValouxMetals()!= null && valouxItemComponentBean.getValouxMetals().size() > 0) {
								List<ValouxMetalBean> metalBeans = valouxItemComponentBean.getValouxMetals();
								for (ValouxMetalBean valouxMetalBean : metalBeans) {
									
									ValouxMetalModel metalModel = new ValouxMetalModel();
									metalModel.setMetalsType(valouxMetalBean.getMetalsType());
									
									if(CommonUserUtility.paparameterNullCheckObject(valouxMetalBean.getPurity())) {
										metalModel.setPurity(valouxMetalBean.getPurity());
									} else {
										metalModel.setPurity(0);
									}
									
									if(CommonUserUtility.paparameterNullCheckObject(valouxMetalBean.getWeight())){
										metalModel.setWeight(valouxMetalBean.getWeight().toString());
									} else {
										metalModel.setWeight(CommonConstants.DOUBLE_ZERO.toString());
									}
									//Get current market value
									getItemComponentMetalSpecifyPrice(metalModel);
									if(CommonUserUtility.paparameterNullCheckStringObject(metalModel.getSpecifiedValue())){
										itemComponentSpecifiedValue = Double.valueOf(metalModel.getSpecifiedValue());
									}
								}
							}
						}
						valouxItemComponentBean.setMarketValue(itemComponentSpecifiedValue);
						valouxItemComponentBean.setModifiedOn(CommonUtility.getDateAndTime());
						valouxItemComponentDao.saveValouxItemComponent(valouxItemComponentBean);
						
						//If component purchased price is available then it will be a market value for calculation in item
						if(CommonUserUtility.paparameterNullCheckObject(valouxItemComponentBean.getPurchasePrice())){
							itemComponentSpecifiedValue = valouxItemComponentBean.getPurchasePrice();
						}
						//If any adjustment done
						Double newMarketValue = ItemHelper.getNewFinalAdjustedComponentPrice(valouxItemComponentBean, itemComponentSpecifiedValue);
						//Multiplying component quantity
						newMarketValue = newMarketValue * quantity;
						itemSpecifiedValue = itemSpecifiedValue + newMarketValue;
					}
				}
				if(!isItemPurchasedValue) {
					valouxItemBean.setMarketValue(itemSpecifiedValue);
					valouxItemBean.setModifiedOn(CommonUtility.getDateAndTime());
					valouxItemDao.updateItemdetail(valouxItemBean);
				}
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method callItemMarketPriceUpdateService");
	}

	@Transactional
	public List<ValouxItemModel> getTopItemsListByUserIdAndLimit(String userPublicKey, Integer limit)
			throws Exception {
		
		LOGGER.debug("ItemServiceImpl : Enter Method getTopItemsListByUserIdAndLimit");
		
		List<ValouxItemModel> itemModels = new ArrayList<ValouxItemModel>();
		
		List<ValouxItemBean> itemBeans = valouxItemDao.getTopItemsListByUserIdAndLimit(userPublicKey, limit);
		if(itemBeans != null && itemBeans.size() > 0){
			Map<Integer, String> itemTypeMap = new HashMap<Integer, String>();
			itemTypeMap = getMasterDataForItemTypeMap();
			for (ValouxItemBean valouxItemBean : itemBeans) {
				ValouxItemModel itemModel = new ValouxItemModel();
				
				String modifiedByName = UserHelper.getUserNameByPublicKey(loginDao, valouxItemBean.getModifiedBy());
				
				if(CommonUserUtility.paparameterNullCheckStringObject(modifiedByName)){
					itemModel.setModifiedByName(modifiedByName);
				}
				
				ItemHelper.populateItemModelDetailsFromBean(itemModel, valouxItemBean, itemTypeMap);
				itemModels.add(itemModel);
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method getTopItemsListByUserIdAndLimit");
		return itemModels;
	}

	@Transactional
	public Boolean deleteItemAndAllDetails(Integer itemId, String userPublicKey) throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method deleteItemAndAllDetails");
		if(itemId != null) {
			ValouxItemBean valouxItemBean = valouxItemDao.getItemDetailByItemIdAndRkey(itemId, userPublicKey);
			if(valouxItemBean != null && valouxItemBean.getItemStatus() != null && valouxItemBean.getItemStatus() != CommonConstants.APPRAISAL_STATUS_APPROVED){
				
				ItemHelper.deleteAllItemDetailsOfItem(valouxItemDao, valouxItemBean);
				
				List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getSharedRequestListByItemId(itemId, CommonConstants.SHARED_TYPE_ITEM);
				if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
					for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
						valouxItemDao.deleteAnyBeanByObject(valouxSharedRequestBean);
					}
				}
				return true;
			}
		}
		LOGGER.debug("ItemServiceImpl : Exit Method deleteItemAndAllDetails");
		return false;
	}
	
	@Transactional
	public Boolean deleteConsumerItemAndAllDetails(String userPublicKey) throws Exception {

		LOGGER.debug("ItemServiceImpl : Enter Method deleteConsumerItemAndAllDetails");
		
			List<ValouxItemBean> valouxItemBeanList = valouxItemDao.getAllItemList(userPublicKey, null, null, null, null);
			if(valouxItemBeanList != null && valouxItemBeanList.size()>0){
				for(ValouxItemBean valouxItemBean:valouxItemBeanList){
				ItemHelper.deleteAllItemDetailsOfItem(valouxItemDao, valouxItemBean);
				
				List<ValouxSharedRequestBean> sharedRequestBeans = valouxSharedRequestDao.getSharedRequestListByItemId(valouxItemBean.getItemId(), CommonConstants.SHARED_TYPE_ITEM);
				if(sharedRequestBeans != null && sharedRequestBeans.size() > 0){
					for (ValouxSharedRequestBean valouxSharedRequestBean : sharedRequestBeans) {
						valouxItemDao.deleteAnyBeanByObject(valouxSharedRequestBean);
					}
				}
				
				}
				return true;
			}
		
		LOGGER.debug("ItemServiceImpl : Exit Method deleteConsumerItemAndAllDetails");
		return false;
	}

}
