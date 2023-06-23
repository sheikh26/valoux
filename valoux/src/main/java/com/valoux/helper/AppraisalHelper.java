/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.MasterRoleBean;
import com.valoux.bean.UserBean;
import com.valoux.bean.ValouxAccessPermissionBean;
import com.valoux.bean.ValouxAppraisalItemsComponentPriceBean;
import com.valoux.bean.ValouxAppraisalItemsPriceBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxItemComponentBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.AppraisalDao;
import com.valoux.dao.ValouxCollectionDao;
import com.valoux.dao.ValouxItemDao;
import com.valoux.enums.UserRegistratonEnums;
import com.valoux.model.AgentModel;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.ValouxAccessPermissionModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.service.AppraisalService;
import com.valoux.service.CollectionService;
import com.valoux.service.ItemService;
import com.valoux.service.UserService;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonMailUtility;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;
import com.valoux.util.MailApi;

/**
 * This java class AppraisalHelper use to perform all our service populate for
 * the collections.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
public class AppraisalHelper {
	
	private static final Logger LOGGER = Logger.getLogger(AppraisalHelper.class);
	
	/**
	 * Method for get appraisal collections items
	 * @paparam appraisalService
	 * @paparam itemService
	 * @paparam collectionService
	 * @paparam resData
	 * @paparam appraisalId
	 * @throws Exception
	 */
	public static void getAppraisalCollectionsItemsDetails(AppraisalService appraisalService, ItemService itemService, CollectionService collectionService, JSONObject resData, Integer appraisalId,Boolean isAgent) throws Exception {
		LOGGER.info("Enter Method getAppraisalCollectionsItemsDetails of AppraisalWS");
		AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(appraisalId);

		if (appraisalBean != null) {
			
			getAppraisalsByAppraisalId(resData, appraisalBean, appraisalService, itemService, collectionService,isAgent);

			getCollectionOfAppraisalsById(appraisalService, itemService, collectionService, resData, appraisalBean.getAppraisalId(), true,isAgent);

			getCollectionOfAppraisalsById(appraisalService, itemService, collectionService, resData, appraisalBean.getAppraisalId(), false,isAgent);

			getAppraisalsItemById(appraisalService, itemService, resData, appraisalBean.getAppraisalId(), true,isAgent);

			getAppraisalsItemById(appraisalService, itemService, resData, appraisalBean.getAppraisalId(), false,isAgent);
			
		}
		LOGGER.info("Exit Method getAppraisalCollectionsItemsDetails of AppraisalWS");
	}

	/**
	 * Method for get appraisal items or count
	 * @paparam appraisalService
	 * @paparam itemService
	 * @paparam iObject
	 * @paparam appraisalId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getAppraisalsItemById(AppraisalService appraisalService, ItemService itemService, JSONObject iObject, Integer appraisalId, boolean isCount, Boolean isAgent) throws Exception {
		LOGGER.info("Enter Method getAppraisalsItemById of AppraisalWS");
		List<AppraisalItemsModel> itemsBeans = appraisalService.getAppraisalItemsBeansByAppraisalId(appraisalId);

		int count = 0;

		if (isCount) {
			if (itemsBeans != null && itemsBeans.size() > 0) {
				count = itemsBeans.size();
			}
			iObject.put("itemsCount", count);
		} else {
			JSONArray itemsList = new JSONArray();
			List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();

			// JSONObject resData = null;
			JSONObject resData = new JSONObject();
			if (itemsBeans != null && itemsBeans.size() > 0) {

				for (AppraisalItemsModel appraisalItemsBean : itemsBeans) {
					ValouxItemModel itemsBean = appraisalService.getItemsBeanByItemId(appraisalItemsBean.getValouxItemId());
					if (itemsBean != null) {
						itemImageModelList = itemService.getItemImageModelList(itemsBean.getItemId());
						// if (itemsBean != null && itemImageModelList !=null) {
						resData = new JSONObject();
						JSONArray imageJArray = new JSONArray();
						resData.put("itemId", itemsBean.getItemId());
						resData.put("name", itemsBean.getName());
						resData.put("designerPrice", itemsBean.getDesignerPrice());
						resData.put("designerPriceType", itemsBean.getDesignerPriceType());
						resData.put("designType", itemsBean.getDesignType());
						resData.put("gender", itemsBean.getGender());
						//resData.put("designType", itemsBean.getItemTypeIt());
						resData.put("gender", itemsBean.getGender());
						resData.put("itemTypeIt", itemsBean.getItemTypeIt());
						resData.put("quantity", itemsBean.getQuantity());
						resData.put("salesPrice", itemsBean.getSalesPrice());
						resData.put("salesTax", itemsBean.getSalesTax());
						resData.put("sDescription", itemsBean.getsDescription());
						resData.put("storeId", itemsBean.getStoreId());
						ItemHelper.addPricesOfAppraisalItemToJson(itemsBean, resData, isAgent, appraisalService, appraisalItemsBean.getAppraisalId());
						// Get component list details of item
						ItemHelper.getComponentsByItemId(itemService, resData, appraisalItemsBean.getValouxItemId());
						getImagesOfItemsById(itemImageModelList, imageJArray, resData);
						itemsList.put(resData);
						// }
					}
				}
			}
			iObject.put("itemsList", itemsList);
		}
		LOGGER.info("Exit Method getAppraisalsItemById of AppraisalWS");
	}
	
	/**
	 * Method for collection of appraisal
	 * @paparam appraisalService
	 * @paparam collectionService
	 * @paparam cObject
	 * @paparam appraisalId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getCollectionOfAppraisalsById(AppraisalService appraisalService, ItemService itemService, CollectionService collectionService,  JSONObject cObject, Integer appraisalId, boolean isCount,Boolean isAgent)
			throws Exception {
		LOGGER.info("AppraisalWS : Enter Method getCollectionOfAppraisalsById");
		List<AppraisalCollectionModel> appraisalBeans = appraisalService.getAppraisalBeansByAppraisalId(appraisalId);
		List<ValouxCollectionItemModel> vaCollectionItemBeansList = new ArrayList<ValouxCollectionItemModel>();
		List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
		//JSONArray itemsList = new JSONArray();
		int count = 0;

		if (isCount) {
			if (appraisalBeans != null && appraisalBeans.size() > 0) {
				count = appraisalBeans.size();
			}
			cObject.put("appraisalCount", count);
		} else {
			JSONArray collectionList = new JSONArray();
			//JSONArray itemList =null;
			
			if (appraisalBeans != null && appraisalBeans.size() > 0) {

				for (AppraisalCollectionModel appraisalCollectionBean : appraisalBeans) {
					ValouxCollectionModel collectionBean = appraisalService
							.getCollectionBeanByCollectionId(appraisalCollectionBean.getValouxCollectionId());

					if (collectionBean != null) {
						JSONObject resData = new JSONObject();
						JSONObject itemData = null;
						JSONArray itemList = new JSONArray();
						resData.put("vcid", collectionBean.getVcid());
						resData.put("cname", collectionBean.getCname());
						resData.put("shortDescription", collectionBean.getShortDescription());
						resData.put("collectionStatus", collectionBean.getCollectionStatus());
						resData.put("consumerPublicKey", collectionBean.getRkey());
						if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
							cObject.put("lastAppraisedDate", collectionBean.getLastAppraisedDate());
							cObject.put("marketValueDate", CommonUtility.getDateAndTime());
							cObject.put("lastAppraisedId", collectionBean.getLastAppraiserId());
							cObject.put("marketValue", 0);
							cObject.put("appraisedValue", 0);
							cObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
							cObject.put("changePercentage",0);
							cObject.put("finalPrice", 0);
						}else{
							cObject.put("lastAppraisedDate", "");
							cObject.put("marketValueDate", "");
							cObject.put("lastAppraisedId","");
							cObject.put("marketValue", "");
							cObject.put("appraisedValue", "");
							cObject.put("priceChange", "");
							cObject.put("changePercentage",0);
							cObject.put("finalPrice", 0);
						}
						
						if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
							
							resData.put("lastAppraisedDate", collectionBean.getLastAppraisedDate());
							resData.put("marketValueDate", CommonUtility.getDateAndTime());
							resData.put("lastAppraisedId", collectionBean.getLastAppraiserId());
							resData.put("marketValue", 0);
							resData.put("appraisedValue", 0);
							resData.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
							resData.put("changePercentage",0);
							resData.put("finalPrice", 0);
						} else {
							resData.put("lastAppraisedDate", "");
							resData.put("marketValueDate", "");
							resData.put("lastAppraisedId","");
							resData.put("marketValue", 0);
							resData.put("appraisedValue", 0);
							resData.put("priceChange", "");
							resData.put("changePercentage",0);
							resData.put("finalPrice", 0);
						} 
						// Get images details of collection
						getImagesOfCollectionById(collectionService, resData, appraisalCollectionBean.getValouxCollectionId());
						Integer countOfItem = appraisalService.getItemAssociatedWithCollection(appraisalCollectionBean
								.getValouxCollectionId());
						
						//vaCollectionItemBeansList =appraisalService.getItemAssociatedWithCollectionList(appraisalCollectionBean.getCollectionId());
						vaCollectionItemBeansList =appraisalService.getItemAssociatedWithCollectionList(collectionBean.getVcid());
						Double marketValue = 0d;
						Double appraisedValue = 0d;
						Double finalPrice = 0d;
						for (ValouxCollectionItemModel collectionItemBean : vaCollectionItemBeansList) {
							ValouxItemModel itemsBean = appraisalService.getItemsBeanByItemId(collectionItemBean.getItemId());
							if (itemsBean != null) {
								itemImageModelList = itemService.getItemImageModelList(itemsBean.getItemId());
								// if (itemsBean != null && itemImageModelList !=null) {
								//resData = new JSONObject();
								JSONArray imageJArray = new JSONArray();
								itemData = new JSONObject();
								itemData.put("marketValue", 0.00);
								itemData.put("appraisedValue", 0.00);
								itemData.put("lastAppraisedDate", 0.00);
								itemData.put("marketValueDate", 0.00);
								itemData.put("lastAppraisedId","");
								itemData.put("finalPrice", 0.00);
								itemData.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
								
								itemData.put("changePercentage", 0.00);
								if(isAgent){
									if(itemsBean.getMarketValue()!=null){
										itemData.put("marketValue", itemsBean.getMarketValue()*itemsBean.getQuantity());
										marketValue=itemsBean.getMarketValue()*itemsBean.getQuantity()+marketValue;
									}
									if(itemsBean.getFinalPrice()!=null){
										itemData.put("finalPrice", itemsBean.getFinalPrice()*itemsBean.getQuantity());
										finalPrice += itemsBean.getFinalPrice()*itemsBean.getQuantity();
									}
								}
								
								if(itemsBean.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
									if(itemsBean.getQuantity()!=null){
										if(itemsBean.getMarketValue()!=null){
									marketValue=itemsBean.getMarketValue()*itemsBean.getQuantity()+marketValue;
									itemData.put("marketValue", itemsBean.getMarketValue()*itemsBean.getQuantity());
										}
										ValouxAppraisalItemsPriceBean appraisalItempriceBean = appraisalService.getAppraisedItemPriceOfAppraisal(itemsBean.getItemId(), appraisalCollectionBean.getAppraisalId());
										if(appraisalItempriceBean!=null){
											
											if(appraisalItempriceBean.getFinalPrice()!=null){
												appraisedValue=appraisalItempriceBean.getFinalPrice()+appraisedValue;
												itemData.put("appraisedValue", appraisalItempriceBean.getFinalPrice());
												itemData.put("lastAppraisedDate", appraisalItempriceBean.getAppraisedDate());
												itemData.put("lastAppraisedId",appraisalItempriceBean.getAppraiserId());
											}
										}
										if(itemsBean.getMarketValue()!=null && appraisalItempriceBean!=null && appraisalItempriceBean.getFinalPrice()!=null){
											Double itemMarketPrice = itemsBean.getMarketValue()*itemsBean.getQuantity();
											Double diff = itemMarketPrice-appraisalItempriceBean.getFinalPrice();
											Double percentage = 0.0;
											
											if(appraisalItempriceBean.getFinalPrice() > 0){
												percentage = (diff*100)/appraisalItempriceBean.getFinalPrice();
											}
											if(diff>0){
												itemData.put("priceChange", CommonConstants.PERCENTAGE_INC);
											}
											if(diff<0){
												itemData.put("priceChange", CommonConstants.PERCENTAGE_DEC);
											}
											if(diff==0){
												itemData.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
											}
											itemData.put("changePercentage", percentage);
										}
									}
									
									itemData.put("marketValueDate", CommonUtility.getDateAndTime());
									
								}
								itemData.put("itemId", itemsBean.getItemId());
								itemData.put("name", itemsBean.getName());
								itemData.put("designerPrice", itemsBean.getDesignerPrice());
								itemData.put("designerPriceType", itemsBean.getDesignerPriceType());
								itemData.put("designType", itemsBean.getDesignType());
								itemData.put("gender", itemsBean.getGender());
								//itemData.put("designType", itemsBean.getItemTypeIt());
								itemData.put("gender", itemsBean.getGender());
								itemData.put("itemTypeIt", itemsBean.getItemTypeIt());
								itemData.put("quantity", itemsBean.getQuantity());
								itemData.put("salesPrice", itemsBean.getSalesPrice());
								itemData.put("salesTax", itemsBean.getSalesTax());
								itemData.put("sDescription", itemsBean.getsDescription());
								itemData.put("storeId", itemsBean.getStoreId());
								itemData.put("valouxMarketValue", itemsBean.getValouxMarketValue());
								itemData.put("itemStatus", itemsBean.getItemStatus());
								// Get component list details of item
								ItemHelper.getComponentsByItemId(itemService, itemData, collectionItemBean.getItemId());
								getImagesOfItemsById(itemImageModelList, imageJArray, itemData);
								itemList.put(itemData);
								resData.put("itemList", itemList);
								//itemsList.put(resData);
								// }
							}
						}
						if(isAgent){
							if(marketValue!=null){
								resData.put("marketValue", marketValue);
							}
							if(finalPrice!=null){
								resData.put("finalPrice", finalPrice);
							}
						}
						if(vaCollectionItemBeansList!=null && vaCollectionItemBeansList.size()>0){
							resData.put("marketValue", marketValue);
							resData.put("appraisedValue", appraisedValue);
							Double diff = marketValue-appraisedValue;
							Double percentage = 0.0;
							
							if(appraisedValue > 0){
								percentage = (diff*100)/appraisedValue;
							}
							if(diff>0){
								resData.put("priceChange", CommonConstants.PERCENTAGE_INC);
							}
							if(diff<0){
								resData.put("priceChange", CommonConstants.PERCENTAGE_DEC);
							}
							if(diff==0){
								resData.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
							}
							resData.put("changePercentage", percentage);
						}
						resData.put("noOfItem", countOfItem);
						collectionList.put(resData);
					}
				}
			}
			cObject.put("collectionList", collectionList);
		}
		LOGGER.info("AppraisalWS : Exit Method getCollectionOfAppraisalsById");
	}
	
	/**
	 * Method for get collection Ids of appraisal
	 * @paparam appraisalService
	 * @paparam jObject
	 * @paparam appraisalId
	 * @throws Exception
	 */
	public static void getCollectionIdsOfAppraisalById(AppraisalService appraisalService, JSONObject jObject, Integer appraisalId) throws Exception {
		LOGGER.info("CollectionWS : Enter Method getCollectionIdsOfAppraisalById");
		try {
			List<AppraisalCollectionModel> appraisalColletionBeans = appraisalService
					.getAppraisalBeansByAppraisalId(appraisalId);

			JSONArray jArray = new JSONArray();
			if (appraisalColletionBeans != null && appraisalColletionBeans.size() > 0) {
				for (AppraisalCollectionModel collectionBean : appraisalColletionBeans) {
					jArray.put(collectionBean.getValouxCollectionId());
				}
			}
			jObject.put("collectionsInAppraisal", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("CollectionWS : Exit Method getCollectionIdsOfAppraisalById");
	}

	/**
	 * Method for get collection image
	 * @paparam collectionService
	 * @paparam cObject
	 * @paparam vcid
	 * @throws Exception
	 */
	public static void getImagesOfCollectionById(CollectionService collectionService, JSONObject cObject, Integer vcid) throws Exception {
		LOGGER.info("CollectionWS : Enter Method getImagesOfCollectionById");
		List<ValouxCollectionImageBean> collectionImageBeanList = collectionService.getCollectionImageDetailsById(vcid);

		if (collectionImageBeanList != null && collectionImageBeanList.size() > 0) {
			JSONObject imageJson = null;
			JSONArray jArray = null;
			for (ValouxCollectionImageBean valouxCollectionImageBean : collectionImageBeanList) {
				jArray = new JSONArray();
				imageJson = new JSONObject();
				imageJson.put("imageId", valouxCollectionImageBean.getId());
				imageJson.put("imageUrl", valouxCollectionImageBean.getImgUrl());
				imageJson.put("imageName", valouxCollectionImageBean.getImgName());
				jArray.put(imageJson);
				cObject.put("cImages", jArray);
			}
			cObject.put("cImages", jArray);
		}
		LOGGER.info("CollectionWS : Exit Method getImagesOfCollectionById");
	}

	/**
	 * Method for get item ids of appraisal
	 * @paparam appraisalService
	 * @paparam jObject
	 * @paparam appraisalId
	 * @throws Exception
	 */
	public static void getItemIdsOfAppraisalById(AppraisalService appraisalService, JSONObject jObject, Integer appraisalId) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method getItemIdsOfAppraisalById");
		try {
			List<AppraisalItemsModel> itemsBeans = appraisalService.getAppraisalItemsBeansByAppraisalId(appraisalId);

			JSONArray jArray = new JSONArray();
			if (itemsBeans != null && itemsBeans.size() > 0) {
				for (AppraisalItemsModel itemBean : itemsBeans) {
					jArray.put(itemBean.getValouxItemId());
				}
			}
			jObject.put("itemsInAppraisal", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("AppraisalWS : Exit Method getItemIdsOfAppraisalById");
	}

	/**
	 * Method for get image of items
	 * @paparam itemImageModelList
	 * @paparam imageJArray
	 * @paparam jObject
	 * @throws JSONException
	 */
	public static void getImagesOfItemsById(List<ItemImageModel> itemImageModelList, JSONArray imageJArray, JSONObject jObject)
			throws JSONException {
		LOGGER.info("AppraisalWS : Enter Method getImagesOfItemsById");
		if (itemImageModelList != null && itemImageModelList.size() > 0) {
			for (ItemImageModel itemImageModel : itemImageModelList) {

				if (itemImageModel.getImageType().equals(1)) {
					JSONObject imageJObject = new JSONObject();
					imageJObject.put("itemImagePath", itemImageModel.getImageurl());
					imageJObject.put("imageId", itemImageModel.getImageId());
					imageJArray.put(imageJObject);
				}
				if (itemImageModel.getImageType().equals(2)) {
					jObject.put("itemReceiptPath", itemImageModel.getImageurl());
					jObject.put("itemReceiptId", itemImageModel.getImageId());
				}
				if (itemImageModel.getImageType().equals(3)) {
					jObject.put("itemCertificatePath", itemImageModel.getImageurl());
					jObject.put("itemCertificateId", itemImageModel.getImageId());
				}
			}
			jObject.put("itemImages", imageJArray);
		}
		LOGGER.info("AppraisalWS : Exit Method getImagesOfItemsById");
	}
	
	/**
	 * Method for fetching items of collection
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getItemsOfCollectionById(CollectionService collectionService, ItemService itemService, JSONObject cObject, Integer collectionId, boolean isCount,Boolean isAgent) throws Exception {
		LOGGER.info("Enter getItemsOfCollectionById method of AppraisalWS");
		try {
			List<ValouxCollectionItemModel> itemModels = collectionService.getUserCollectionItemsList(collectionId);
			int itemCount = 0;

			if (isCount) {
				if (itemModels != null && itemModels.size() > 0) {
					itemCount = itemModels.size();
				}
				cObject.put("noOfItem", itemCount);
			} else {
				JSONArray jArray = new JSONArray();
				JSONArray imageJArray = null;
				if (itemModels != null && itemModels.size() > 0) {
					for (ValouxCollectionItemModel collectionitemModel : itemModels) {

						List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();

						ValouxItemModel itemModel = itemService.getValouxItemDetailsById(collectionitemModel
								.getItemId());

						if (itemModel != null) {
							imageJArray = new JSONArray();
							JSONObject jObject = new JSONObject();
							jObject.put("name", itemModel.getName());
							jObject.put("itemId", itemModel.getItemId());
							jObject.put("sdescription", itemModel.getsDescription());
							jObject.put("designType", itemModel.getDesignType());
							jObject.put("jewelryGender", itemModel.getGender());
							jObject.put("quantity", itemModel.getQuantity());
							jObject.put("itemType", itemModel.getItemTypeIt());

							itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());

							if (itemImageModelList != null && itemImageModelList.size() > 0) {
								for (ItemImageModel itemImageModel : itemImageModelList) {

									if (itemImageModel.getImageType().equals(1)) {
										JSONObject imageJObject = new JSONObject();
										imageJObject.put("itemImagePath", itemImageModel.getImageurl());
										imageJObject.put("imageId", itemImageModel.getImageId());
										imageJArray.put(imageJObject);
									}
									if (itemImageModel.getImageType().equals(2)) {
										jObject.put("itemReceiptPath", itemImageModel.getImageurl());
										jObject.put("itemReceiptId", itemImageModel.getImageId());
									}
									if (itemImageModel.getImageType().equals(3)) {
										jObject.put("itemCertificatePath", itemImageModel.getImageurl());
										jObject.put("itemCertificateId", itemImageModel.getImageId());
									}
								}
								jObject.put("itemImages", imageJArray);
							}

							jArray.put(jObject);
						}
					}
				}
				cObject.put("items", jArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit getItemsOfCollectionById method of AppraisalWS");
	}

	/**
	 * Method for fetching item images of collection
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getItemsImagesOfCollectionById(CollectionService collectionService, ItemService itemService, JSONObject cObject, Integer collectionId) throws Exception {
		LOGGER.info("Enter getItemsImagesOfCollectionById method of AppraisalWS");
		try {
			List<ValouxCollectionItemModel> itemModels = collectionService.getUserCollectionItemsList(collectionId);

			JSONArray jArray = new JSONArray();
			JSONArray imageJArray = null;
			if (itemModels != null && itemModels.size() > 0) {
				for (ValouxCollectionItemModel collectionitemModel : itemModels) {

					List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();

					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(collectionitemModel.getItemId());

					if (itemModel != null) {
						imageJArray = new JSONArray();
						JSONObject jObject = new JSONObject();
						// jObject.put("itemId", itemModel.getItemId());

						itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());

						if (itemImageModelList != null && itemImageModelList.size() > 0) {
							for (ItemImageModel itemImageModel : itemImageModelList) {

								if (itemImageModel.getImageType().equals(1)) {
									JSONObject imageJObject = new JSONObject();
									imageJObject.put("itemImagePath", itemImageModel.getImageurl());
									imageJObject.put("imageId", itemImageModel.getImageId());
									imageJArray.put(imageJObject);
								}
								if (itemImageModel.getImageType().equals(2)) {
									jObject.put("itemReceiptPath", itemImageModel.getImageurl());
									jObject.put("itemReceiptId", itemImageModel.getImageId());
								}
								if (itemImageModel.getImageType().equals(3)) {
									jObject.put("itemCertificatePath", itemImageModel.getImageurl());
									jObject.put("itemCertificateId", itemImageModel.getImageId());
								}
							}
							jObject.put("itemImages", imageJArray);
							jArray.put(jObject);
						}
					}
				}
			}
			cObject.put("items", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit getItemsImagesOfCollectionById method of AppraisalWS");
	}

	/**
	 * Method for get appraisals of collection
	 * @paparam appraisalService
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getAppraisalsOfCollectionById(AppraisalService appraisalService, JSONObject cObject, Integer collectionId, boolean isCount,Boolean isAgent)
			throws Exception {
		LOGGER.info("Enter getAppraisalsOfCollectionById method of AppraisalWS");
		List<AppraisalCollectionModel> appraisalCollectionBeans = appraisalService
				.getAppraisalCollectionBeansByCollectionId(collectionId);

		int count = 0;

		if (isCount) {
			if (appraisalCollectionBeans != null && appraisalCollectionBeans.size() > 0) {
				count = appraisalCollectionBeans.size();
			}
			cObject.put("appraisalCount", count);
		} else {
			JSONArray appraisalList = new JSONArray();
			if (appraisalCollectionBeans != null && appraisalCollectionBeans.size() > 0) {

				for (AppraisalCollectionModel appraisalCollectionBean : appraisalCollectionBeans) {
					AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(appraisalCollectionBean.getAppraisalId());
							
					if (appraisalBean != null) {
						JSONObject resData = new JSONObject();
						resData.put("name", appraisalBean.getName());
						resData.put("shortDescription", appraisalBean.getShortDescription());
						resData.put("appraisalStatus", appraisalBean.getaStatus());
						resData.put("appraisalId", appraisalBean.getAppraisalId());

						appraisalList.put(resData);
					}
				}
			}
			cObject.put("appraisalList", appraisalList);
		}
		LOGGER.info("Exit getAppraisalsOfCollectionById method of AppraisalWS");
	}
	
	/**
	 * Method for user valoux permission creation
	 * @paparam registrationData
	 * @paparam encryptedKey
	 * @throws Exception
	 */
	public static void userValouxAccessPermissionCreation( AppraisalService appraisalService, 
			JSONObject jObject, String encryptedKey) throws Exception{
		LOGGER.info("Enter Method userValouxAccessPermissionCreation of AppraisalWS");
		ValouxAccessPermissionModel accessPermissionModel = new ValouxAccessPermissionModel();
		
		accessPermissionModel.setCreatedBy(JSONUtility.getSafeString(jObject, "publicKey"));
		accessPermissionModel.setCreatedOn(CommonUtility.getDateAndTime());
		accessPermissionModel.setGivenPermissionBy(encryptedKey);
		accessPermissionModel.setGivenPermissionTo(JSONUtility.getSafeString(jObject, "publicKey"));
		accessPermissionModel.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
		accessPermissionModel.setModifiedOn(CommonUtility.getDateAndTime());
		accessPermissionModel.setIsAddedAppraisal(CommonConstants.STATUS_ACTIVE);
		accessPermissionModel.setIsAddedCollection(CommonConstants.STATUS_ACTIVE);
		accessPermissionModel.setIsAddedItem(CommonConstants.STATUS_ACTIVE);
		
		appraisalService.addValouxAccessPermission(accessPermissionModel);
		LOGGER.info("Exit Method userValouxAccessPermissionCreation of AppraisalWS");
	}

	/**
	 * Method for user login bean creation via agent
	 * @paparam registrationData
	 * @paparam loginBean
	 * @throws Exception
	 */
	public static LoginBean userLoginBeanCreationViaAgent(UserService userService, JSONObject registrationData,
			LoginBean loginBean) throws Exception{
		LOGGER.info("Enter Method userLoginBeanCreationViaAgent of AppraisalWS");
		LoginModel loginModel = new LoginModel();
		
		loginModel.setUserName(JSONUtility.getSafeString(registrationData, "emailId"));
		loginModel.setPassword((String.valueOf(CommonUtility.generateRandom(4))));
		loginModel.setFirstName(JSONUtility.getSafeString(registrationData, "firstName"));
		loginModel.setLastName(JSONUtility.getSafeString(registrationData, "lastName"));
		loginModel.setUserStatus(CommonConstants.USER_STATUS_INCOMPLETE);
		loginModel.setAuthenticationCode(CommonUtility.generatetToken());
		loginModel.setForgetPasswordKey(CommonUtility.generatetToken());
		loginModel.setPrivateKey(CommonUtility.generatetToken());
		loginModel.setAuthCodeMobile(String.valueOf(CommonUtility.generateRandom(6)));
		loginModel.setCreatedOn(CommonUtility.getDateAndTime());
		loginModel.setModifiedOn(CommonUtility.getDateAndTime());
		loginModel.setCreatedBy(JSONUtility.getSafeString(registrationData, "publicKey"));
		loginModel.setModifiedBy(JSONUtility.getSafeString(registrationData, "publicKey"));

		loginBean = userService.saveLoginInfo(loginModel);
		LOGGER.info("Exit Method userLoginBeanCreationViaAgent of AppraisalWS");
		return loginBean;
	}

	/**
	 * Method for User registration via agent
	 * @paparam registrationData
	 * @paparam loginBean 
	 * @paparam encryptedKey 
	 * @throws Exception
	 */
	public static void userLoginTypeAndRoleCreationViaAgent(UserService userService, AgentService agentService, JSONObject jObject, LoginBean loginBean, String encryptedKey) throws Exception{
		UserBean userBean = new UserBean();
		LOGGER.info("Enter Method userLoginTypeAndRoleCreationViaAgent of AppraisalWS");
		
		JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");
		JSONObject registrationData = collectionInfo.optJSONObject("newConsumerData");
		if (loginBean != null) {
			userBean.setEmailId(JSONUtility.getSafeString(registrationData, "emailId"));
			userBean.setCreatedOn(CommonUtility.getDateAndTime());
			userBean.setModifiedOn(CommonUtility.getDateAndTime());
			userBean.setRelationKey(encryptedKey);
			userBean.setCreatedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			userBean.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
//			userBean.setMobile(JSONUtility.getSafeString(registrationData, "mobilePhone"));
			
			MasterRoleBean masterRoleBean = agentService.getRoleData(String.valueOf(CommonConstants.CONSUMER_ADMIN));

			userService.createUserTypeAndUserRoleForConsumer(userBean, loginBean, masterRoleBean);
		}
		LOGGER.info("Exit Method userLoginTypeAndRoleCreationViaAgent of AppraisalWS");
	}
	
	/**
	 * Method for User registration via agent
	 * @paparam registrationData
	 * @paparam loginBean 
	 * @paparam encryptedKey 
	 * @throws Exception
	 */
	public static void userLoginTypeAndRoleCreationViaAgentForAppraisal(UserService userService, AgentService agentService, JSONObject jObject, LoginBean loginBean, String encryptedKey) throws Exception{
		UserBean userBean = new UserBean();
		LOGGER.info("Enter Method userLoginTypeAndRoleCreationViaAgent of AppraisalWS");
		
		JSONObject collectionInfo = jObject.getJSONObject("appraisalItemOrCollectionData");
		JSONObject registrationData = collectionInfo.optJSONObject("newConsumerData");
		if (loginBean != null) {
			userBean.setEmailId(JSONUtility.getSafeString(registrationData, "emailId"));
			userBean.setCreatedOn(CommonUtility.getDateAndTime());
			userBean.setModifiedOn(CommonUtility.getDateAndTime());
			userBean.setRelationKey(encryptedKey);
			userBean.setCreatedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			userBean.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
//			userBean.setMobile(JSONUtility.getSafeString(registrationData, "mobilePhone"));
			
			MasterRoleBean masterRoleBean = agentService.getRoleData(String.valueOf(CommonConstants.CONSUMER_ADMIN));

			userService.createUserTypeAndUserRoleForConsumer(userBean, loginBean, masterRoleBean);
		}
		LOGGER.info("Exit Method userLoginTypeAndRoleCreationViaAgent of AppraisalWS");
	}
	
	/**
	 * Method for User registration via agent
	 * @paparam registrationData
	 * @paparam loginBean 
	 * @paparam encryptedKey 
	 * @throws Exception
	 */
	public static void userLoginTypeAndRoleCreationViaAgentForItem(UserService userService, AgentService agentService, JSONObject jObject, LoginBean loginBean, String encryptedKey) throws Exception{
		UserBean userBean = new UserBean();
		LOGGER.info("Enter Method userLoginTypeAndRoleCreationViaAgent of AppraisalWS");
		
		JSONObject collectionInfo = jObject.getJSONObject("itemInfo");
		JSONObject registrationData = collectionInfo.optJSONObject("newConsumerData");
		if (loginBean != null) {
			userBean.setEmailId(JSONUtility.getSafeString(registrationData, "emailId"));
			userBean.setCreatedOn(CommonUtility.getDateAndTime());
			userBean.setModifiedOn(CommonUtility.getDateAndTime());
			userBean.setRelationKey(encryptedKey);
			userBean.setCreatedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			userBean.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
//			userBean.setMobile(JSONUtility.getSafeString(registrationData, "mobilePhone"));
			
			MasterRoleBean masterRoleBean = agentService.getRoleData(String.valueOf(CommonConstants.CONSUMER_ADMIN));

			userService.createUserTypeAndUserRoleForConsumer(userBean, loginBean, masterRoleBean);
		}
		LOGGER.info("Exit Method userLoginTypeAndRoleCreationViaAgent of AppraisalWS");
	}
	
	/**
	 * Method for fetching agents having consumer access
	 * @paparam userService
	 * @paparam agentService
	 * @paparam appraisalService
	 * @paparam itemService
	 * @paparam collectionService
	 * @paparam jObject
	 * @paparam resData
	 * @throws Exception
	 */
	public static void getAgentAccessPermissionUsers(UserService userService, AppraisalService appraisalService, ItemService itemService, CollectionService collectionService, AgentModel agentModel, JSONObject jObject, JSONObject resData) throws Exception {

		LOGGER.info("Enter Method getAgentAccessPermissionUsers of AppraisalWS");
		
		String agentKey = JSONUtility.getSafeString(jObject, "publicKey");
		
		if(agentModel != null){
			JSONArray jArray = new JSONArray();
			JSONObject responseJson = null;
			List<ValouxAccessPermissionBean> accessPermissionBeans = appraisalService.getAgentAccessPermissionUsers(agentKey);
			
			if(accessPermissionBeans != null && accessPermissionBeans.size() > 0){
				for (ValouxAccessPermissionBean valouxAccessPermissionBean : accessPermissionBeans) {
					if(CommonUserUtility.paparameterNullCheckStringObject(valouxAccessPermissionBean.getGivenPermissionBy())){
						String userRKey = valouxAccessPermissionBean.getGivenPermissionBy();
						
						//String pKey = (userRKey);
						String pKey = "";
						LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
						if(loginModel != null){
							responseJson = new JSONObject();
							AppraisalHelper.getResponseUserDetails(userService, loginModel, responseJson, userRKey);
							
							//Adding item count of user
							int itemCount = 0;
							List<ValouxItemModel> itemModelList = itemService.getAllItemList(userRKey, null, null, null, null);
							if(itemModelList != null && itemModelList.size() > 0){
								itemCount = itemModelList.size();
							}
							responseJson.put("itemCount", itemCount);
							
							//Adding collection count of user
							int collectionCount = 0;
							List<ValouxCollectionModel> collectionModels = collectionService.getUserCollectionsList(userRKey, null, null, null, null);
							if(collectionModels != null && collectionModels.size() > 0){
								collectionCount = collectionModels.size();
							}
							responseJson.put("collectionCount", collectionCount);
							
							//Adding appraisal count of user
							int appraisalCount = 0;
							List<AppraisalModel> appraisalModels = appraisalService.getUserAppraisalList(userRKey,null);
							if(appraisalModels != null && appraisalModels.size() > 0){
								appraisalCount = appraisalModels.size();
							}
							responseJson.put("appraisalCount", appraisalCount);
							
							jArray.put(responseJson);
						}
					}
				}
			}
			resData.put("userList", jArray);
		}
		LOGGER.info("Exit Method getAgentAccessPermissionUsers of AppraisalWS");
	}

	/**
	 * @paparam userService
	 * @paparam loginModel
	 * @paparam responseJson
	 * @paparam userRKey
	 * @throws Exception
	 */
	public static void getResponseUserDetails(UserService userService, LoginModel loginModel, JSONObject responseJson, String userRKey) throws Exception {
		
		LOGGER.info("Enter Method getResponseUserDetails of AppraisalWS");
		
		responseJson.put("firstName", loginModel.getFirstName());
		responseJson.put("lastName", loginModel.getLastName());
		responseJson.put("middleName", loginModel.getMiddleName());
		responseJson.put("emailId", loginModel.getUserName());
		responseJson.put("userStatus", loginModel.getUserStatus());
		
		UserModel userModel = userService.getConsumerDetailByRKey(userRKey);
		if(userModel != null){
			responseJson.put("mobilePhone", userModel.getMobile());
			responseJson.put("gender", userModel.getGender());
			responseJson.put("imageUrl", userModel.getImageURL());
			responseJson.put("consumerKey", userModel.getRelationKey());
			
			JSONObject addressJson = new JSONObject();
			addressJson.put("address", userModel.getGlobalAddress());
			addressJson.put("addressLine1", userModel.getAddressLine1());
			addressJson.put("addressLine2", userModel.getAddressLine2());
			addressJson.put("streetNo", userModel.getStreetNo());
			addressJson.put("country", userModel.getCountryName());
			addressJson.put("city", userModel.getCity());
			addressJson.put("state", userModel.getStateName());
			addressJson.put("zipCode", userModel.getZipCode());
			responseJson.put("userAddress", addressJson);
		} else {
			responseJson.put("userAddress", new JSONObject());
		}
		LOGGER.info("Exit Method getResponseUserDetails of AppraisalWS");
	}
	
	/**
	 * Method for get appraisals
	 * @paparam cObject
	 * @paparam AppraisalBean appraisalBean
	 * @throws Exception
	 */
	public static void getAppraisalsByAppraisalId(JSONObject cObject, AppraisalBean appraisalBean,AppraisalService appraisalService,ItemService itemService,CollectionService collectionService,Boolean isAgents)
			throws Exception {
		JSONObject resData = new JSONObject();
		//JSONObject appraisalList = new JSONObject();
			if (appraisalBean != null) {
				
				resData.put("name", appraisalBean.getName());
				resData.put("shortDescription", appraisalBean.getShortDescription());
				resData.put("appraisalStatus", appraisalBean.getaStatus());
				resData.put("appraisalId", appraisalBean.getAppraisalId());
				resData.put("consumerPublickey", appraisalBean.getRelationKey());

				AppraisalModel appraisalModel = new AppraisalModel();
				appraisalModel.setAppraisalId(appraisalBean.getAppraisalId());
				appraisalModel.setName(appraisalBean.getName());
				appraisalModel.setShortDescription(appraisalBean.getShortDescription());
				appraisalModel.setaStatus(appraisalBean.getaStatus());
				appraisalModel.setApprovedBy(appraisalBean.getApprovedBy());
				appraisalModel.setApprovedOn(appraisalBean.getApprovedOn());
				appraisalModel.setCreatedBy(appraisalBean.getCreatedBy());
				appraisalModel.setCreatedOn(appraisalBean.getCreatedOn());
				appraisalModel.setModifiedBy(appraisalBean.getModifiedBy());
				appraisalModel.setModifiedOn(appraisalBean.getModifiedOn());
				appraisalModel.setRelationKey(appraisalBean.getRelationKey());
				appraisalModel.setLastAppraisaedPrice(appraisalBean.getLastAppraisaedPrice());
				AppraisalHelper.addPriceOfAppraisal(appraisalModel, resData, appraisalService, itemService, collectionService,isAgents);
			}
			cObject.put("appraisalList", resData);
		LOGGER.info("Exit getAppraisalsOfCollectionById method of AppraisalWS");
	}

	/**
	 * Method to change Appraisal status to appraised
	 * @paparam appraisalService
	 * @paparam jObject
	 * @throws Exception
	 */
	public static void changeStatusToAppraised(
			AppraisalService appraisalService, JSONObject jObject) throws Exception {
		
		Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
		
		AppraisalModel appraisalModel = new AppraisalModel();
		if(CommonUserUtility.paparameterNullCheckObject(appraisalId)){
			
			appraisalModel.setAppraisalId(appraisalId);
			appraisalModel.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			appraisalModel.setModifiedOn(CommonUtility.getDateAndTime());
			
			appraisalModel = appraisalService.changeStatusToAppraised(appraisalModel);
			
		}
		
	}

	/**
	 * @paparam appraisalComponentPriceBean
	 * @paparam valouxItemComponentBean
	 * @throws Exception
	 */
	public static void prepareAppraisalItemsComponentFromComponent(
			ValouxAppraisalItemsComponentPriceBean appraisalComponentPriceBean,
			ValouxItemComponentBean valouxItemComponentBean) throws Exception {
		
		appraisalComponentPriceBean.setAdjustmentPrice(valouxItemComponentBean.getAdjustmentPrice());
		appraisalComponentPriceBean.setBrandPriceAdjustmentNotes(valouxItemComponentBean.getBrandPriceAdjustmentNotes());
		appraisalComponentPriceBean.setBrandPriceAdjustmentOperator(valouxItemComponentBean.getBrandPriceAdjustmentOperator());
		appraisalComponentPriceBean.setBrandPriceAdjustmentType(valouxItemComponentBean.getBrandPriceAdjustmentType());
		appraisalComponentPriceBean.setBrandPriceAdjustmentValue(valouxItemComponentBean.getBrandPriceAdjustmentValue());
		appraisalComponentPriceBean.setCreatedBy(valouxItemComponentBean.getCreatedBy());
		appraisalComponentPriceBean.setCreatedOn(valouxItemComponentBean.getCreatedOn());
		appraisalComponentPriceBean.setCurrentUnitPrice(valouxItemComponentBean.getCurrentUnitPrice());
		appraisalComponentPriceBean.setFinalPrice(valouxItemComponentBean.getFinalPrice());
		appraisalComponentPriceBean.setGeneralPriceAdjustmentNotes(valouxItemComponentBean.getGeneralPriceAdjustmentNotes());
		appraisalComponentPriceBean.setGeneralPriceAdjustmentOperator(valouxItemComponentBean.getGeneralPriceAdjustmentOperator());
		appraisalComponentPriceBean.setGeneralPriceAdjustmentType(valouxItemComponentBean.getGeneralPriceAdjustmentType());
		appraisalComponentPriceBean.setGeneralPriceAdjustmentValue(valouxItemComponentBean.getGeneralPriceAdjustmentValue());
		appraisalComponentPriceBean.setMarketValue(valouxItemComponentBean.getMarketValue());
		appraisalComponentPriceBean.setModifiedBy(valouxItemComponentBean.getModifiedBy());
		appraisalComponentPriceBean.setModifiedOn(valouxItemComponentBean.getModifiedOn());
		appraisalComponentPriceBean.setPurchasePrice(valouxItemComponentBean.getPurchasePrice());
		appraisalComponentPriceBean.setStatus(valouxItemComponentBean.getVicStatus());
		appraisalComponentPriceBean.setValouxItemComponent(valouxItemComponentBean);
	}

	/**
	 * @paparam appraisalItemsPriceBean
	 * @paparam itemBean
	 * @throws Exception
	 */
	public static void prepareAppraisalItemsFromItem(
			ValouxAppraisalItemsPriceBean appraisalItemsPriceBean,
			ValouxItemBean itemBean) throws Exception {
		
		appraisalItemsPriceBean.setAdjustmentPrice(itemBean.getAdjustmentPrice());
		appraisalItemsPriceBean.setAppraisedDate(itemBean.getLastAppraisedDate());
		appraisalItemsPriceBean.setAppraiserId(itemBean.getLastAppraiserId());
		appraisalItemsPriceBean.setBrandPriceAdjustmentNotes(itemBean.getBrandPriceAdjustmentNotes());
		appraisalItemsPriceBean.setBrandPriceAdjustmentOperator(itemBean.getBrandPriceAdjustmentOperator());
		appraisalItemsPriceBean.setBrandPriceAdjustmentType(itemBean.getBrandPriceAdjustmentType());
		appraisalItemsPriceBean.setBrandPriceAdjustmentValue(itemBean.getBrandPriceAdjustmentValue());
		appraisalItemsPriceBean.setCreatedBy(itemBean.getCreatedBy());
		appraisalItemsPriceBean.setCreatedOn(itemBean.getCreatedOn());
		appraisalItemsPriceBean.setFinalPrice(itemBean.getLastAppraisaedPrice());
		appraisalItemsPriceBean.setGeneralPriceAdjustmentNotes(itemBean.getGeneralPriceAdjustmentNotes());
		appraisalItemsPriceBean.setGeneralPriceAdjustmentOperator(itemBean.getGeneralPriceAdjustmentOperator());
		appraisalItemsPriceBean.setGeneralPriceAdjustmentType(itemBean.getGeneralPriceAdjustmentType());
		appraisalItemsPriceBean.setGeneralPriceAdjustmentValue(itemBean.getGeneralPriceAdjustmentValue());
		appraisalItemsPriceBean.setMarketValue(itemBean.getLastAppraisaedPrice());
		appraisalItemsPriceBean.setModifiedBy(itemBean.getModifiedBy());
		appraisalItemsPriceBean.setModifiedOn(itemBean.getModifiedOn());
		appraisalItemsPriceBean.setPurchasePrice(itemBean.getPurchasePrice());
		if(itemBean.getItemStatus() != null){
			appraisalItemsPriceBean.setStatus(itemBean.getItemStatus().byteValue());
		}
		appraisalItemsPriceBean.setValouxItem(itemBean);
	}
	
	public static void addPriceOfAppraisal(AppraisalModel appraisalModel,JSONObject jObject,AppraisalService appraisalService,ItemService itemService,CollectionService collectionService,Boolean isAgent) throws Exception{
		jObject.put("lastAppraisedDate", "");
		jObject.put("marketValueDate", "");
		jObject.put("marketValue", 0.00);
		jObject.put("appraisedValue", 0.00);
		jObject.put("finalPrice", 0.00);
		jObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
		jObject.put("changePercentage",0);
		jObject.put("aStatus",appraisalModel.getaStatus());
		Double marketprice = 0.00;
		Double finalPrice = 0.00;
		Map<Integer, Integer> itemIdsMap = new HashMap<Integer, Integer>();
		JSONArray cArray = new JSONArray();
		JSONArray iArray = new JSONArray();
		jObject.put("noOfItem", 0);
		jObject.put("noOfCollection", 0);
		List<AppraisalItemsModel> appraisalItemModelList = appraisalService
				.getAppraisalItemsBeansByAppraisalId(appraisalModel.getAppraisalId());
		if(appraisalItemModelList!=null && appraisalItemModelList.size()>0){
			List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
			jObject.put("noOfItem", appraisalItemModelList.size());
			for(AppraisalItemsModel aItemModel:appraisalItemModelList){
				itemImageModelList = itemService.getItemImageModelList(aItemModel.getValouxItemId());
				JSONObject iObject = new JSONObject();
				JSONArray imageJArray = new JSONArray();
				// Get images details of items
				AppraisalHelper.getImagesOfItemsById(itemImageModelList, imageJArray, iObject);
				if (!iObject.toString().equalsIgnoreCase("{}")) {
					iArray.put(iObject);
				}
				if(appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED) || isAgent){
					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(aItemModel.getValouxItemId());
					if(itemModel!=null){
						itemIdsMap.put(itemModel.getItemId(), itemModel.getItemId());
					
					if(itemModel.getMarketValue()!=null && itemModel.getQuantity()!=null){
						marketprice += itemModel.getMarketValue()*itemModel.getQuantity();
					}
					if(itemModel.getFinalPrice()!=null){
						finalPrice += itemModel.getFinalPrice()*itemModel.getQuantity();
					}
					}
				}
			}
			
		}
		List<AppraisalCollectionModel> appraisalColletionBeans = appraisalService
				.getAppraisalBeansByAppraisalId(appraisalModel.getAppraisalId());
		if(appraisalColletionBeans!=null && appraisalColletionBeans.size()>0){
			jObject.put("noOfCollection", appraisalColletionBeans.size());
			for(AppraisalCollectionModel aCollectionModel:appraisalColletionBeans){
				JSONObject cObject = new JSONObject();
				AppraisalHelper.getImagesOfCollectionById(collectionService, cObject, aCollectionModel.getValouxCollectionId());
				if (!cObject.toString().equalsIgnoreCase("{}")) {
					cArray.put(cObject);
				}
				if(appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED) || isAgent){
					List<ValouxCollectionItemModel> collectionItemModelList = collectionService.getUserCollectionItemsList(aCollectionModel.getValouxCollectionId());
					if(collectionItemModelList!=null && collectionItemModelList.size()>0){
						for(ValouxCollectionItemModel collectionItemModel:collectionItemModelList){
							ValouxItemModel itemModel = itemService.getValouxItemDetailsById(collectionItemModel.getItemId());
							if(itemModel!=null){
								if(itemIdsMap!=null && itemIdsMap.isEmpty()){
									itemIdsMap.put(itemModel.getItemId(), itemModel.getItemId());
									if(itemModel.getMarketValue()!=null && itemModel.getQuantity()!=null){
										marketprice += itemModel.getMarketValue()*itemModel.getQuantity();
									}
									if(itemModel.getFinalPrice()!=null){
										finalPrice += itemModel.getFinalPrice()*itemModel.getQuantity();
									}
								}
								if(itemIdsMap!=null && !itemIdsMap.containsKey(itemModel.getItemId())){
									 if(itemModel.getMarketValue()!=null && itemModel.getQuantity()!=null){
									marketprice += itemModel.getMarketValue()*itemModel.getQuantity();
									 }
									 if(itemModel.getFinalPrice()!=null){
										 finalPrice += itemModel.getFinalPrice()*itemModel.getQuantity();
									 }
									itemIdsMap.put(itemModel.getItemId(), itemModel.getItemId());
								}
							}
							
						}
					}
					
				}
			}
		}
		if(isAgent){
			jObject.put("finalPrice", finalPrice);
			jObject.put("marketValue", marketprice);
		}
		if(appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
			Double appraisedValue = 0.00;
			appraisedValue = appraisalModel.getLastAppraisaedPrice();
			jObject.put("lastAppraisedDate", appraisalModel.getApprovedOn());
			jObject.put("marketValueDate", CommonUtility.getDateAndTime());
			jObject.put("finalPrice", finalPrice);
			if(marketprice != null && appraisedValue != null){
				jObject.put("appraisedValue", appraisedValue);
				jObject.put("marketValue", marketprice);
				if(!appraisedValue.equals(0.00)){
				Double diff = marketprice-appraisedValue;
				Double percentage = 0.0;
				
				if(appraisedValue > 0){
					percentage = (diff*100)/appraisedValue;
				}
				if(diff>0){
				jObject.put("priceChange", CommonConstants.PERCENTAGE_INC);
				}
				if(diff<0){
				jObject.put("priceChange", CommonConstants.PERCENTAGE_DEC);
				}
				if(diff==0){
					jObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
				}
				jObject.put("changePercentage", percentage);
				}
			}
		}
		jObject.put("collectionImages", cArray);
		jObject.put("itemImages", iArray);
	}

	/**
	 * Method to change Appraisal status to appraised
	 * @paparam userService 
	 * @paparam appraisalService
	 * @paparam jObject
	 * @throws Exception
	 */
	public static void changeStatusToUnAppraised(AppraisalService appraisalService, JSONObject jObject) throws Exception {
		
		Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
		
		AppraisalModel appraisalModel = new AppraisalModel();
		if(CommonUserUtility.paparameterNullCheckObject(appraisalId)){
			
			appraisalModel.setAppraisalId(appraisalId);
			appraisalModel.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			appraisalModel.setModifiedOn(CommonUtility.getDateAndTime());
			
			appraisalModel = appraisalService.changeStatusToUnAppraised(appraisalModel);
		}
	}

	/**
	 * @paparam loginModel
	 * @paparam agentModel 
	 * @throws Exception
	 */
	public static void sendAppraisedMailToConsumer(UserService userService, LoginModel loginModel, AgentModel agentModel) throws Exception {

		if(loginModel != null){
			String from = CommonConstants.SIGN_UP_MAIL;
			StringBuffer sub = new StringBuffer();
			
			sub.append(CommonConstants.APPRAISAL_APPRAISED_BODY);
			String body = CommonMailUtility.getUserAppraisalAppraisedMailContent(loginModel);
			
			MailApi.SendMail(loginModel.getUserName(), from, sub.toString(), body,
					UserRegistratonEnums.UsertType.Customer.getType());
		}
	}

	/**
	 * @paparam appraisalService
	 * @paparam resData 
	 * @paparam json 
	 * @paparam appraisalId 
	 */
	public static void addAppraisalPdfFile(AppraisalService appraisalService, JSONObject resObject, Integer appraisalId) throws Exception {
		
		String valouxPropertyFileName = "valoux.properties";
		Properties prop = new Properties();
		prop = CommonUtility.getProperty(valouxPropertyFileName);
	
		final String pdfService = prop.getProperty("valoux.pdf.php.service");
		
		String url = null;
		if(pdfService != null){
			url = pdfService;
		}
		
		String response = null;
		if(resObject != null){
			
			String resData = resObject.getString("resData");
			if(resData != null) {
				String message = resObject.toString();
				DefaultHttpClient httpClient = new DefaultHttpClient();
		        response = ApacheHttpClientPost.postToURL(url, message, httpClient);
		        
		        if(CommonUserUtility.paparameterNullCheckStringObject(response)){
		        	 JSONObject jsonObj = new JSONObject(response);
			        if(jsonObj != null){
			        	String pdfName = jsonObj.optString("pdf_name");
			        	if(CommonUserUtility.paparameterNullCheckStringObject(pdfName)){
			        		AppraisalModel appraisalModel = new AppraisalModel();
			        		appraisalModel.setAppraisalId(appraisalId);
			        		appraisalModel.setPdfFile(pdfName);
			        		appraisalService.addAppraisalPdfFile(appraisalModel);
			        	}
			        }
	        	}
			}
		}
	}

	/**
	 * @paparam appraisalService
	 * @paparam agentService
	 * @paparam userService
	 * @paparam storeService
	 * @paparam appraisalId
	 * @paparam resData
	 * @throws Exception
	 */
	public static void getApprovedAgentDetails(
			AppraisalService appraisalService, AgentService agentService, UserService userService, ValouxStoreService storeService, Integer appraisalId,
			JSONObject resData) throws Exception {
		
		AppraisalModel appraisalModel = appraisalService.getAppraisalDetailsById(appraisalId);
		if(appraisalModel != null){
			if(CommonUserUtility.paparameterNullCheckStringObject(appraisalModel.getApprovedBy())){
				AgentModel agentProfileModel = agentService.getAgentDetailByRelationKey(appraisalModel.getApprovedBy());
				if(agentProfileModel != null){
					
					JSONObject storeJson = new JSONObject();
					JSONObject storeAddressJson = new JSONObject();
					JSONObject responseJson = new JSONObject();
					
					ValouxStoreModel storeModel = new ValouxStoreModel();
					ValouxAgentStoreModel agentStoreModel = new ValouxAgentStoreModel();
					//LoginModel loginModel = userService.getLoginDetailByPKey((agentProfileModel.getRelationKey()));
					LoginModel loginModel = userService.getLoginDetailByPKey((agentProfileModel.getRelationKey()));
					agentStoreModel = agentService.getStoreDataByRelationKey(agentProfileModel.getRelationKey());
					if (agentStoreModel != null) {
						
						responseJson.put("emailId", agentProfileModel.getEmailId());
						responseJson.put("mobilePhone", agentProfileModel.getMobile());
						responseJson.put("signUrl", agentProfileModel.getSignUrl());
						responseJson.put("signName", agentProfileModel.getSignName());
						
						if(loginModel != null) {
							responseJson.put("firstName", loginModel.getFirstName());
							responseJson.put("lastName", loginModel.getLastName());
							responseJson.put("middeName", loginModel.getMiddleName());
						}
						
						storeModel = storeService.getStoreDataByStoreId(agentStoreModel.getStoreId());
						if(storeModel != null) {
							storeJson.put("storeId", storeModel.getStoreId());
							storeJson.put("storeName", storeModel.getName());
							storeAddressJson.put("streetNo", storeModel.getStreetNumber());
							storeAddressJson.put("addressLine1", storeModel.getAddressLine1());
							storeAddressJson.put("addressLine2", storeModel.getAddressLine2());
							storeAddressJson.put("country", storeModel.getCountryName());
							storeAddressJson.put("state", storeModel.getStateName());
							storeAddressJson.put("zipCode", storeModel.getZipcode());
							storeAddressJson.put("city", storeModel.getCity());
							storeJson.put("storeAddress", storeAddressJson);
						}
						responseJson.put("storeData", storeJson);
						resData.put("agentData", responseJson);
					}
				}
			}
		}
	}

	/**
	 * @paparam userService
	 * @paparam userKey
	 * @paparam agentKey
	 * @throws Exception
	 */
	public static void sendUnAppraisedMailToConsumer(UserService userService,
			String userKey, String agentKey) throws Exception {
		
		LoginModel loginModel = userService.getLoginDetailByPKey((userKey));
		
		if(loginModel != null){
			String from = CommonConstants.SIGN_UP_MAIL;
			StringBuffer sub = new StringBuffer();
			
			sub.append(CommonConstants.APPRAISAL_UNAPPRAISED_BODY);
			String body = CommonMailUtility.getUserAppraisalUnAppraisedMailContent(loginModel);
			
			MailApi.SendMail(loginModel.getUserName(), from, sub.toString(), body,
					UserRegistratonEnums.UsertType.Customer.getType());
		}
	}

	/**
	 * @paparam appraisalModel
	 * @paparam appraisalBean
	 * @throws Exception
	 */
	public static void populateAppraisalModelFromBean(
			AppraisalModel appraisalModel, AppraisalBean valouxAppraisalBean) throws Exception {

		if(valouxAppraisalBean != null){
			appraisalModel.setAppraisalId(valouxAppraisalBean.getAppraisalId());
			appraisalModel.setId(valouxAppraisalBean.getAppraisalId());
			appraisalModel.setName(valouxAppraisalBean.getName());
			appraisalModel.setShortDescription(valouxAppraisalBean.getShortDescription());
			appraisalModel.setaStatus(valouxAppraisalBean.getaStatus());
			appraisalModel.setStatus(valouxAppraisalBean.getaStatus());
			appraisalModel.setApprovedBy(valouxAppraisalBean.getApprovedBy());
			appraisalModel.setApprovedOn(valouxAppraisalBean.getApprovedOn());
			appraisalModel.setCreatedBy(valouxAppraisalBean.getCreatedBy());
			appraisalModel.setCreatedOn(valouxAppraisalBean.getCreatedOn());
			appraisalModel.setModifiedBy(valouxAppraisalBean.getModifiedBy());
			appraisalModel.setModifiedOn(valouxAppraisalBean.getModifiedOn());
			appraisalModel.setRelationKey(valouxAppraisalBean.getRelationKey());
			appraisalModel.setLastAppraisaedPrice(valouxAppraisalBean.getLastAppraisaedPrice());
			appraisalModel.setPdfFile(valouxAppraisalBean.getPdfFile());
			appraisalModel.setDescription(valouxAppraisalBean.getShortDescription());
		}
	}

	/**
	 * @paparam appraisalDao
	 * @paparam valouxItemDao 
	 * @paparam valouxCollectionDao 
	 * @paparam appraisalBean
	 * @throws Exception
	 */
	public static void deleteAllAppraisalDetailsOfAppraisal(
			AppraisalDao appraisalDao, ValouxCollectionDao valouxCollectionDao, ValouxItemDao valouxItemDao, AppraisalBean appraisalBean) throws Exception {
		
		if(appraisalBean!=null){
			
			//Delete Appraisal collections
			if(appraisalBean.getAppraisalCollectionBean() != null && appraisalBean.getAppraisalCollectionBean().size() > 0){
				List<AppraisalCollectionBean> collectionBeans = appraisalBean.getAppraisalCollectionBean();
				for (AppraisalCollectionBean appraisalCollectionBean : collectionBeans) {
					appraisalDao.deleteAnyBeanByObject(appraisalCollectionBean);
				}
			}
			
			//Delete Appraisal items
			if(appraisalBean.getAppraisalItemsBean() != null && appraisalBean.getAppraisalItemsBean().size() > 0){
				List<AppraisalItemsBean> itemsBeans = appraisalBean.getAppraisalItemsBean();
				for (AppraisalItemsBean appraisalItemsBean : itemsBeans) {
					appraisalDao.deleteAnyBeanByObject(appraisalItemsBean);
				}
			}
			
			//Delete Appraisal Component price
			if(appraisalBean.getValouxAppraisalItemsComponentPrices() != null && appraisalBean.getValouxAppraisalItemsComponentPrices().size() > 0){
				List<ValouxAppraisalItemsComponentPriceBean> componentPriceBeans = appraisalBean.getValouxAppraisalItemsComponentPrices();
				for (ValouxAppraisalItemsComponentPriceBean valouxAppraisalItemsComponentPriceBean : componentPriceBeans) {
					appraisalDao.deleteAnyBeanByObject(valouxAppraisalItemsComponentPriceBean);
				}
			}
			
			//Delete Appraisal item price
			if(appraisalBean.getValouxAppraisalItemsPrices() != null && appraisalBean.getValouxAppraisalItemsPrices().size() > 0){
				List<ValouxAppraisalItemsPriceBean> itemsPriceBeans = appraisalBean.getValouxAppraisalItemsPrices();
				for (ValouxAppraisalItemsPriceBean valouxAppraisalItemsPriceBean : itemsPriceBeans) {
					appraisalDao.deleteAnyBeanByObject(valouxAppraisalItemsPriceBean);
				}
			}
			
			//Update Appraisal Collection details
			if(appraisalBean.getValouxCollections() != null && appraisalBean.getValouxCollections().size() > 0){
				List<ValouxCollectionBean> collectionBeans = appraisalBean.getValouxCollections();
				for (ValouxCollectionBean collectionBean : collectionBeans) {
					collectionBean.setModifiedOn(CommonUtility.getDateAndTime());
					collectionBean.setValouxAppraisal(null);
					valouxCollectionDao.addUpdateCollectionDetails(collectionBean, CommonConstants.UPDATE);
				}
			}
			
			//Update Appraisal Collection details
			if(appraisalBean.getValouxItems() != null && appraisalBean.getValouxItems().size() > 0){
				List<ValouxItemBean> itemBeans = appraisalBean.getValouxItems();
				for (ValouxItemBean itemBean : itemBeans) {
					itemBean.setModifiedOn(CommonUtility.getDateAndTime());
					itemBean.setValouxAppraisal(null);
					valouxItemDao.updateItemdetail(itemBean);
				}
			}
			
			//Delete appraisal
			appraisalDao.deleteAnyBeanByObject(appraisalBean);
		}
	}

}
