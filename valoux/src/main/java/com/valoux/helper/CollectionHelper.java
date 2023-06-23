/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.ValouxCollectionBean;
import com.valoux.bean.ValouxCollectionImageBean;
import com.valoux.bean.ValouxCollectionItemBean;
import com.valoux.constant.CommonConstants;
import com.valoux.dao.ValouxCollectionDao;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.ValouxCollectionImageModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.service.AppraisalService;
import com.valoux.service.CollectionService;
import com.valoux.service.ItemService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This java class CollectionHelper use to perform all our service populate for
 * the collections.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */

public class CollectionHelper {

	private static final Logger LOGGER = Logger.getLogger(CollectionHelper.class);
	
	/**
	 * Method for populating valoux collection model
	 * 
	 * @paparam collectionModel
	 * @paparam requestType
	 * @paparam collectionInfo
	 */
	public static void populateValouxCollectionModel(ValouxCollectionModel collectionModel, JSONObject jObject,
			String requestType) throws Exception {

		LOGGER.info("CollectionHelper : Enter Method populateValouxCollectionModel");

		JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");

		try {
			collectionModel.setVcid(JSONUtility.getSafeInteger(collectionInfo, "cId"));
			collectionModel.setRkey(JSONUtility.getSafeString(collectionInfo, "userPublicKey"));
			collectionModel.setCname(JSONUtility.getSafeString(collectionInfo, "cName"));
			collectionModel.setShortDescription(JSONUtility.getSafeString(collectionInfo, "cDescription"));
			collectionModel.setCollectionStatus(CommonConstants.STATUS);
			collectionModel.setRequestType(requestType);
			collectionModel.setCreatedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			collectionModel.setCreatedOn(CommonUtility.getDateAndTime());
			collectionModel.setModifiedBy(JSONUtility.getSafeString(jObject, "publicKey"));
			collectionModel.setModifiedOn(CommonUtility.getDateAndTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		LOGGER.info("CollectionHelper : Exit Method populateValouxCollectionModel");
	}

	/**
	 * Method for populating collection item model
	 * 
	 * @paparam ImageModelList
	 * @paparam collectionInfo
	 * @paparam collectionId
	 * @paparam requestType
	 * @throws Exception
	 */
	public static void populateValouxCollectionImageModel(List<ValouxCollectionImageModel> imageModelList,
			JSONObject jObject, Integer collectionId, String requestType) throws Exception {

		LOGGER.info("CollectionHelper : Enter Method populateValouxCollectionImageModel");

		JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");

		JSONArray collectionImages = JSONUtility.getSafeJSONArray(collectionInfo, "cImages");

		if (collectionImages != null && collectionImages.length() > 0) {

			for (int i = 0; i < collectionImages.length(); i++) {

				ValouxCollectionImageModel collectionImageModel = new ValouxCollectionImageModel();

				JSONObject object = collectionImages.getJSONObject(i);

				String imageContent = JSONUtility.getSafeString(object, "imageContent");
				String imageName = JSONUtility.getSafeString(object, "imageName");
				String imagePath = CommonUtility.saveDocumentInDirectory(imageContent, imageName, "Collection_Image_"
						+ collectionId + "_", i);

				collectionImageModel.setImgName(imageName);
				collectionImageModel.setImgStatus(CommonConstants.STATUS);
				collectionImageModel.setImgUrl(imagePath);
				collectionImageModel.setCollectionId(collectionId);
				collectionImageModel.setRequestType(requestType);
				collectionImageModel.setCreatedBy(JSONUtility.getSafeString(jObject, "memberShipKey"));
				collectionImageModel.setCreatedOn(CommonUtility.getDateAndTime());
				collectionImageModel.setModifiedBy(JSONUtility.getSafeString(jObject, "memberShipKey"));
				collectionImageModel.setModifiedOn(CommonUtility.getDateAndTime());
				imageModelList.add(collectionImageModel);
			}
		}

		LOGGER.info("CollectionHelper : Exit Method populateValouxCollectionImageModel");
	}

	/**
	 * Method populating collection item model
	 * 
	 * @paparam collectionItemModel
	 * @paparam requestType
	 * @paparam collectionInfo
	 */
	public static void populateValouxCollectionItemModel(ValouxCollectionItemModel collectionItemModel,
			JSONObject jObject, String requestType) throws Exception {

		LOGGER.info("CollectionHelper : Enter Method populateValouxCollectionItemModel");

		JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");

		try {
			collectionItemModel.setCollectionId(JSONUtility.getSafeInteger(collectionInfo, "cId"));
			collectionItemModel.setItems(JSONUtility.getSafeJSONArray(collectionInfo, "items"));
			collectionItemModel.setRequestType(requestType);
			collectionItemModel.setCreatedBy(JSONUtility.getSafeString(jObject, "memberShipKey"));
			collectionItemModel.setCreatedOn(CommonUtility.getDateAndTime());
			collectionItemModel.setModifiedBy(JSONUtility.getSafeString(jObject, "memberShipKey"));
			collectionItemModel.setModifiedOn(CommonUtility.getDateAndTime());
			collectionItemModel.setStatus(CommonConstants.STATUS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("CollectionHelper : Exit Method populateValouxCollectionItemModel");
	}

	/**
	 * Method for populating valoux collection entity bean
	 * @paparam collectionBean 
	 * 
	 * @paparam collectionBean
	 * @paparam collectionModel
	 */
	public static ValouxCollectionBean populateCollectionBeanFromModel(ValouxCollectionBean collectionBean, ValouxCollectionModel collectionModel)
			throws Exception {

		LOGGER.info("CollectionHelper : Enter Method populateCollectionBeanFromModel");

		collectionBean.setVcid(collectionModel.getVcid());
		
		if (CommonUserUtility.paparameterNullCheckStringObject(collectionModel.getRkey())) {
			collectionBean.setRkey(collectionModel.getRkey());
		}
		
		if (CommonUserUtility.paparameterNullCheckStringObject(collectionModel.getCname())) {
			collectionBean.setCname(collectionModel.getCname().trim());
		}

		if (CommonUserUtility.paparameterNullCheckStringObject(collectionModel.getShortDescription())) {
			collectionBean.setShortDescription(collectionModel.getShortDescription());
		}

		collectionBean.setCollectionStatus(collectionModel.getCollectionStatus());

		if (collectionModel.getRequestType().equalsIgnoreCase(CommonConstants.ADD)) {
			collectionBean.setCreatedBy(collectionModel.getCreatedBy());
			collectionBean.setCreatedOn(collectionModel.getCreatedOn());
			collectionBean.setModifiedBy(collectionModel.getModifiedBy());
			collectionBean.setModifiedOn(collectionModel.getModifiedOn());
		} else if (collectionModel.getRequestType().equalsIgnoreCase(CommonConstants.UPDATE)) {
			collectionBean.setModifiedBy(collectionModel.getModifiedBy());
			collectionBean.setModifiedOn(collectionModel.getModifiedOn());
		}
		LOGGER.info("CollectionHelper : Exit Method populateCollectionBeanFromModel");

		return collectionBean;
	}

	/**
	 * Method for populating collection appraisals
	 * 
	 * @paparam appraisalCollectionModel
	 * @paparam requestType
	 * @paparam collectionInfo
	 * @throws Exception
	 */
	public static void populateValouxAppraisalCollectionModel(AppraisalCollectionModel appraisalCollectionModel,
			JSONObject jObject, String requestType) throws Exception {
		LOGGER.info("CollectionHelper : Enter Method populateValouxAppraisalCollectionModel");

		JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");

		try {
			appraisalCollectionModel.setAppraisalCollectionId(JSONUtility.getSafeInteger(collectionInfo, "cId"));
			appraisalCollectionModel
					.setCollectionAppraisals(JSONUtility.getSafeJSONArray(collectionInfo, "appraisals"));

			appraisalCollectionModel.setRequestType(requestType);
			appraisalCollectionModel.setCreatedBy(JSONUtility.getSafeString(jObject, "memberShipKey"));
			appraisalCollectionModel.setCreatedOn(CommonUtility.getDateAndTime());
			appraisalCollectionModel.setModifiedBy(JSONUtility.getSafeString(jObject, "memberShipKey"));
			appraisalCollectionModel.setModifiedOn(CommonUtility.getDateAndTime());
			appraisalCollectionModel.setStatus(CommonConstants.STATUS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("CollectionHelper : Exit Method populateValouxAppraisalCollectionModel");
	}
	
	/**
	 * @paparam appraisalService 
	 * @paparam itemService 
	 * @paparam cObject
	 * @paparam vcid
	 */
	public static void getAppraisalsOfCollectionById(AppraisalService appraisalService, CollectionService collectionService, ItemService itemService, JSONObject cObject, Integer collectionId, boolean isCount)
			throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getAppraisalsOfCollectionById");

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

				for (AppraisalCollectionModel appCollectionBean : appraisalCollectionBeans) {
					AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(appCollectionBean.getAppraisalId());
					
					if (appraisalBean != null) {
						JSONObject resData = new JSONObject();
						resData.put("consumerPublicKey", appraisalBean.getRelationKey());
						resData.put("name", appraisalBean.getName());
						resData.put("shortDescription", appraisalBean.getShortDescription());
						resData.put("appraisalStatus", appraisalBean.getaStatus());
						resData.put("appraisalId", appraisalBean.getAppraisalId());
						resData.put("consumerPublicKey", appraisalBean.getRelationKey());
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
						AppraisalHelper.addPriceOfAppraisal(appraisalModel, resData, appraisalService, itemService, collectionService,false);
						
						// List of collection of Appraisals
						List<AppraisalCollectionModel> appraisalBeans = appraisalService
								.getAppraisalBeansByAppraisalId(appraisalBean.getAppraisalId());

						if(appraisalBeans != null && appraisalBeans.size()>0){
							resData.put("appraisalCollectionCount", appraisalBeans.size());
						}
						JSONArray collectionList = new JSONArray();
						if (appraisalBeans != null && appraisalBeans.size() > 0) {

							for (AppraisalCollectionModel appraisalCollectionBean : appraisalBeans) {
								ValouxCollectionModel collectionBean = appraisalService
										.getCollectionBeanByCollectionId(appraisalCollectionBean.getValouxCollectionId());

								if (collectionBean != null) {
									JSONObject respData = new JSONObject();
									// Get images details of collection
									getImagesOfCollectionById(collectionService, respData, appraisalCollectionBean.getValouxCollectionId());
									collectionList.put(respData);
								}
							}
						}
						resData.put("collectionList", collectionList);

						getItemsOfAppraisalsById(appraisalService, itemService, resData, appraisalBean.getAppraisalId(), true);

						getItemsOfAppraisalsById(appraisalService, itemService, resData, appraisalBean.getAppraisalId(), false);

						appraisalList.put(resData);
					}
				}
			}
			cObject.put("appraisalList", appraisalList);
		}
		
		LOGGER.info("CollectionHelper : Exit Method getAppraisalsOfCollectionById");
	}

	/**
	 * @paparam appraisalService 
	 * @paparam itemService 
	 * @paparam iObject
	 * @paparam appraisalId
	 */
	public static void getItemsOfAppraisalsById(AppraisalService appraisalService, ItemService itemService, JSONObject iObject, Integer appraisalId, boolean isCount) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getItemsOfAppraisalsById");

		List<AppraisalItemsModel> itemsBeans = appraisalService.getAppraisalItemsBeansByAppraisalId(appraisalId);

		int count = 0;

		if (isCount) {
			if (itemsBeans != null && itemsBeans.size() > 0) {
				count = itemsBeans.size();
			}
			iObject.put("appraisalItemsCount", count);
		} else {
			JSONArray itemsList = new JSONArray();
			if (itemsBeans != null && itemsBeans.size() > 0) {

				List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();

				for (AppraisalItemsModel appraisalItemsBean : itemsBeans) {
					ValouxItemModel itemsBean = appraisalService.getItemsBeanByItemId(appraisalItemsBean.getValouxItemId());

					if (itemsBean != null) {
						JSONArray imageJArray = new JSONArray();
						JSONObject jObject = new JSONObject();

						itemImageModelList = itemService.getItemImageModelList(itemsBean.getItemId());

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
						itemsList.put(jObject);
					}
				}
			}
			iObject.put("appraisalItemsList", itemsList);
		}
		LOGGER.info("CollectionHelper : Exit Method getItemsOfAppraisalsById");
	}

	/**
	 * Method for get collection image
	 * @paparam collectionService 
	 * 
	 * @paparam cObject
	 * @paparam vcid
	 */
	public static void getImagesOfCollectionById(CollectionService collectionService, JSONObject cObject, Integer vcid) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getImagesOfCollectionById");

		List<ValouxCollectionImageBean> collectionImageBeanList = collectionService.getCollectionImageDetailsById(vcid);
		JSONArray jArray = new JSONArray();
		if (collectionImageBeanList != null && collectionImageBeanList.size() > 0) {

			for (ValouxCollectionImageBean valouxCollectionImageBean : collectionImageBeanList) {
				JSONObject imageJson = new JSONObject();
				imageJson.put("imageId", valouxCollectionImageBean.getId());
				imageJson.put("imageUrl", valouxCollectionImageBean.getImgUrl());
				imageJson.put("imageName", valouxCollectionImageBean.getImgName());
				jArray.put(imageJson);
			}
		}
		cObject.put("cImages", jArray);
		
		LOGGER.info("CollectionHelper : Exit Method getImagesOfCollectionById");
	}

	/**
	 * Method for fetching items of collection
	 * @paparam itemService 
	 * @paparam collectionService2 
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @paparam jObject2 
	 * @throws Exception
	 */
	public static void getItemsOfCollectionById(CollectionService collectionService, ItemService itemService, JSONObject cObject, Integer collectionId, boolean isCount, JSONObject rqstObject,boolean requirePriceDetail,Boolean isAgent) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getItemsOfCollectionById");
		
		try {
			List<ValouxCollectionItemModel> itemModels = collectionService.getUserCollectionItemsList(collectionId);
			int itemCount = 0;

			if (isCount && !requirePriceDetail) {
				if (itemModels != null && itemModels.size() > 0) {
					itemCount = itemModels.size();
				}
				cObject.put("itemCount", itemCount);
			} else {
				JSONArray jArray = new JSONArray();
				JSONArray imageJArray = null;
				if(isCount){
					if (itemModels != null && itemModels.size() > 0) {
						itemCount = itemModels.size();
					}
					cObject.put("itemCount", itemCount);
				}
				if (itemModels != null && itemModels.size() > 0) {
					Double marketValue = 0d;
					Double appraisedValue = 0d;
					Double finalPrice = 0d;
					for (ValouxCollectionItemModel collectionitemModel : itemModels) {

						List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();

						ValouxItemModel itemModel = itemService.getValouxItemDetailsById(collectionitemModel
								.getItemId());
						
						
						if (itemModel != null) {
							imageJArray = new JSONArray();
							if(requirePriceDetail && itemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
								if(itemModel.getMarketValue()!=null){
								marketValue=itemModel.getMarketValue()*itemModel.getQuantity()+marketValue;
								}
								if(itemModel.getLastAppraisaedPrice()!=null){
								appraisedValue=itemModel.getLastAppraisaedPrice()+appraisedValue;
								}
							}else{
								if(isAgent){
									if(itemModel.getMarketValue()!=null){
										marketValue=itemModel.getMarketValue()*itemModel.getQuantity()+marketValue;
										}
									if(itemModel.getFinalPrice()!=null){
										finalPrice += itemModel.getFinalPrice()*itemModel.getQuantity();
									}
								}
							}
							
							JSONObject jObject = new JSONObject();
							if(!isCount){
							jObject.put("name", itemModel.getName());
							jObject.put("itemId", itemModel.getItemId());
							jObject.put("sdescription", itemModel.getsDescription());
							jObject.put("designType", itemModel.getDesignType());
							jObject.put("jewelryGender", itemModel.getGender());
							jObject.put("quantity", itemModel.getQuantity());
							jObject.put("itemType", itemModel.getItemTypeIt());
							jObject.put("consumerPublicKey", itemModel.getrKey());
							ItemHelper.addPricesOfItemToJson(itemModel, jObject,isAgent);
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
									if (!itemImageModel.getImageType().equals(3)) {
										jObject.put("itemCertificatePath", itemImageModel.getImageurl());
										jObject.put("itemCertificateId", itemImageModel.getImageId());
									}
								}
								jObject.put("itemImages", imageJArray);
							} else {
								jObject.put("itemImages", imageJArray);
							}
							}

							jArray.put(jObject);
						}
					}
					if(isAgent){
						if(marketValue!=null){
							cObject.put("marketValue", marketValue);
						}
						if(finalPrice!=null){
							cObject.put("finalPrice", finalPrice);
						}
					}
					if(requirePriceDetail && marketValue!=null && appraisedValue!=null){
						
						cObject.put("marketValue", marketValue);
						cObject.put("appraisedValue", appraisedValue);
						Double diff = marketValue-appraisedValue;
						if(!appraisedValue.equals(0d)){
						Double percentage = 0.0;
						
						if(appraisedValue > 0){
							percentage = (diff*100)/appraisedValue;
						}
						if(diff>0){
							cObject.put("priceChange", CommonConstants.PERCENTAGE_INC);
						}
						if(diff<0){
							cObject.put("priceChange", CommonConstants.PERCENTAGE_DEC);
						}
						if(diff==0){
							cObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
						}
						cObject.put("changePercentage", percentage);
						}
					}
				}
				if(!isCount){
				cObject.put("items", jArray);
				
				JSONObject collectionInfo = rqstObject.optJSONObject("collectionInfo");
				if(collectionInfo != null){
					String sharedTo = JSONUtility.getSafeString(rqstObject, "publicKey");
					String sharedBy = JSONUtility.getSafeString(collectionInfo, "userPublicKey");
					
					if(CommonUserUtility.paparameterNullCheckStringObject(sharedBy) && CommonUserUtility.paparameterNullCheckStringObject(sharedTo)){
						List<ValouxItemModel> itemModelList = itemService.getAgentSharedItemsNotInCollection(sharedTo, collectionId, sharedBy);
						if(itemModelList!=null && itemModelList.size()>0){
							cObject.put("itemFlag", true);
						}else{
							cObject.put("itemFlag", false);
						}
						List<AppraisalModel> appraisalModelList = itemService.getAgentSharedAppraisalsNotInCollection(sharedTo, collectionId, sharedBy);
						if(appraisalModelList!=null && appraisalModelList.size()>0){
							cObject.put("appraisalFlag", true);
						}else{
							cObject.put("appraisalFlag", false);
						}	
					}
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.info("CollectionHelper : Exit Method getItemsOfCollectionById");
	}
	
	/**
	 * Method for fetching item images of collection
	 * @paparam itemService 
	 * @paparam collectionService 
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getItemsImagesOfCollectionById(CollectionService collectionService, ItemService itemService, JSONObject cObject, Integer collectionId) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getItemsImagesOfCollectionById");

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

		LOGGER.info("CollectionHelper : Exit Method getItemsImagesOfCollectionById");
	}
	
	/**
	 * Method for fetching items of collection
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getItemsIdsOfCollectionById(CollectionService collectionService, JSONObject cObject, Integer collectionId) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getItemsIdsOfCollectionById");

		try {
			List<ValouxCollectionItemModel> itemModels = collectionService.getUserCollectionItemsList(collectionId);

			JSONArray jArray = new JSONArray();
			if (itemModels != null && itemModels.size() > 0) {
				for (ValouxCollectionItemModel collectionitemModel : itemModels) {
					jArray.put(collectionitemModel.getItemId());
				}
			}
			cObject.put("itemsInCollection", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.info("CollectionHelper : Exit Method getItemsIdsOfCollectionById");
	}

	/**
	 * Method for fetching appraisals of collection
	 * @paparam appraisalService 
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @throws Exception
	 */
	public static void getAppraisalIdsOfCollectionById(AppraisalService appraisalService, JSONObject cObject, Integer collectionId) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getAppraisalIdsOfCollectionById");

		try {
			List<AppraisalCollectionModel> appraisalModels = appraisalService.getAppraisalCollectionBeansByCollectionId(collectionId);

			JSONArray jArray = new JSONArray();
			if (appraisalModels != null && appraisalModels.size() > 0) {
				for (AppraisalCollectionModel collectionBean : appraisalModels) {
					jArray.put(collectionBean.getAppraisalId());
				}
			}
			cObject.put("appraisalsInCollection", jArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.info("CollectionHelper : Exit Method getAppraisalIdsOfCollectionById");
	}
	
	/**
	 * Method for fetching items of collection
	 * @paparam itemService 
	 * @paparam collectionService2 
	 * 
	 * @paparam cObject
	 * @paparam collectionId
	 * @paparam isCount
	 * @paparam jObject2 
	 * @throws Exception
	 */
	public static void getPricesOfCollection(CollectionService collectionService, ItemService itemService, JSONObject cObject, ValouxCollectionModel collectionModel,Boolean isAgent) throws Exception {
		
		LOGGER.info("CollectionHelper : Enter Method getPricesOfCollection");
		
		try {
			cObject.put("cStatus", collectionModel.getCollectionStatus());
			List<ValouxCollectionItemModel> itemModels = collectionService.getUserCollectionItemsList(collectionModel.getVcid());
			
			int itemCount = 0;
			if (itemModels != null && itemModels.size() > 0) {
				itemCount = itemModels.size();
			}
			cObject.put("noOfItem", itemCount);
				cObject.put("marketValue", 0.00);
				cObject.put("appraisedValue", 0.00);
				cObject.put("priceChange",CommonConstants.PERCENTAGE_NO_CHANGE);
				cObject.put("changePercentage",0);
				cObject.put("lastAppraisedDate", "");
				cObject.put("marketValueDate", "");
				cObject.put("lastAppraisedId","");
				cObject.put("finalPrice", 0.00);
				if (collectionModel.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED) || isAgent) {
				cObject.put("marketValue", 0.00);
				cObject.put("appraisedValue", 0.00);
				cObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
				cObject.put("changePercentage",0);
				if (itemModels != null && itemModels.size() > 0) {
					Double marketValue = 0d;
					Double appraisedValue = 0d;
					Double finalPrice = 0.00;
					for (ValouxCollectionItemModel collectionitemModel : itemModels) {

						ValouxItemModel itemModel = itemService.getValouxItemDetailsById(collectionitemModel
								.getItemId());
						
						
						if (itemModel != null && itemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)) {
								
								if(itemModel.getLastAppraisaedPrice()!=null){
								appraisedValue=itemModel.getLastAppraisaedPrice()+appraisedValue;
								}
						}
						if(itemModel!=null &&(itemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED) || isAgent)){
							if(itemModel.getMarketValue()!=null){
								marketValue=itemModel.getMarketValue()*itemModel.getQuantity()+marketValue;
								}
							if(itemModel.getFinalPrice()!=null){
								finalPrice=itemModel.getFinalPrice()*itemModel.getQuantity()+finalPrice;
								}
						}
					}
					
						cObject.put("marketValue", marketValue);
						cObject.put("appraisedValue", appraisedValue);
						if(appraisedValue!=null && !appraisedValue.equals(0d)){
						Double diff = marketValue-appraisedValue;
						Double percentage = 0.0;
						
						if(appraisedValue > 0){
							percentage = (diff*100)/appraisedValue;
						}
						if(diff>0){
							cObject.put("priceChange", CommonConstants.PERCENTAGE_INC);
						}
						if(diff<0){
							cObject.put("priceChange", CommonConstants.PERCENTAGE_DEC);
						}
						if(diff==0){
							cObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
						}
						cObject.put("changePercentage", percentage);
						}
						cObject.put("lastAppraisedDate",collectionModel.getLastAppraisedDate());
						cObject.put("marketValueDate", CommonUtility.getDateAndTime());
						cObject.put("lastAppraisedId",collectionModel.getLastAppraiserId());
						cObject.put("finalPrice", finalPrice);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.info("CollectionHelper : Exit Method getPricesOfCollection");
	}

	/**
	 * @paparam valouxCollectionBean
	 * @paparam collectionModel
	 * @throws Exception
	 */
	public static void populateCollectionModelFromBean(
			ValouxCollectionBean collectionBean,
			ValouxCollectionModel collectionModel) throws Exception{
		
		LOGGER.info("CollectionHelper : Enter Method populateCollectionModelFromBean");

		if(collectionBean != null) {
			collectionModel.setVcid(collectionBean.getVcid());
			collectionModel.setId(collectionBean.getVcid());
			collectionModel.setCname(collectionBean.getCname());
			collectionModel.setName(collectionBean.getCname());
			collectionModel.setShortDescription(collectionBean.getShortDescription());
			collectionModel.setCollectionStatus(collectionBean.getCollectionStatus());
			collectionModel.setStatus(collectionBean.getCollectionStatus());
			collectionModel.setCreatedBy(collectionBean.getCreatedBy());
			collectionModel.setCreatedOn(collectionBean.getCreatedOn());
			collectionModel.setModifiedBy(collectionBean.getModifiedBy());
			collectionModel.setModifiedOn(collectionBean.getModifiedOn());
			collectionModel.setRkey(collectionBean.getRkey());
			collectionModel.setLastAppraisedDate(collectionBean.getLastAppraisedDate());
			collectionModel.setLastAppraiserId(collectionBean.getLastAppraiserId());
			collectionModel.setDescription(collectionBean.getShortDescription());
			
			List<ValouxCollectionImageModel> imageModels = new ArrayList<ValouxCollectionImageModel>();
			
			if(collectionBean.getValouxCollectionImagesBean() != null && collectionBean.getValouxCollectionImagesBean().size() > 0){
				
				List<ValouxCollectionImageBean> collectionImageBeans = collectionBean.getValouxCollectionImagesBean();
				for (ValouxCollectionImageBean valouxCollectionImageBean : collectionImageBeans) {
					
					ValouxCollectionImageModel collectionImageModel = CommonUserUtility.prepareCollectionImageModelFromBean(valouxCollectionImageBean);
					imageModels.add(collectionImageModel);
				}
			}
			collectionModel.setImageModels(imageModels);
		}
		
		LOGGER.info("CollectionHelper : Exit Method populateCollectionModelFromBean");
	}

	/**
	 * @paparam valouxCollectionDao
	 * @paparam collectionBean
	 * @throws Exception
	 */
	public static void deleteAllCollectionDetailsOfCollection(
			ValouxCollectionDao valouxCollectionDao,
			ValouxCollectionBean collectionBean) throws Exception{
		
		LOGGER.info("CollectionHelper : Enter Method deleteAllCollectionDetailsOfCollection");
		
		if(collectionBean != null){
			
			//Delete collection images
			if(collectionBean.getValouxCollectionImagesBean() != null && collectionBean.getValouxCollectionImagesBean().size() > 0){
				List<ValouxCollectionImageBean> imageBeans = collectionBean.getValouxCollectionImagesBean();
				for (ValouxCollectionImageBean valouxCollectionImageBean : imageBeans) {
					valouxCollectionDao.deleteAnyBeanByObject(valouxCollectionImageBean);
				}
			}
			
			//Delete Collection items
			if(collectionBean.getValouxCollectionItemsBean() != null && collectionBean.getValouxCollectionItemsBean().size() > 0){
				List<ValouxCollectionItemBean> collectionItemBeans = collectionBean.getValouxCollectionItemsBean();
				for (ValouxCollectionItemBean valouxCollectionItemBean : collectionItemBeans) {
					valouxCollectionDao.deleteAnyBeanByObject(valouxCollectionItemBean);
				}
			}
			
			//Delete Collection Appraisals
			if(collectionBean.getAppraisalCollectionBean() != null && collectionBean.getAppraisalCollectionBean().size() > 0){
				List<AppraisalCollectionBean> collectionBeans = collectionBean.getAppraisalCollectionBean();
				for (AppraisalCollectionBean appraisalCollectionBean : collectionBeans) {
					valouxCollectionDao.deleteAnyBeanByObject(appraisalCollectionBean);
				}
			}
			
			//Delete collection
			valouxCollectionDao.deleteAnyBeanByObject(collectionBean);
		}
		
		LOGGER.info("CollectionHelper : Exit Method deleteAllCollectionDetailsOfCollection");
	}
}
