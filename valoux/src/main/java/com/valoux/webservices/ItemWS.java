/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.webservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;

import com.valoux.bean.LoginBean;
import com.valoux.bean.ValouxItemBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.bean.ValouxStoreBean;
import com.valoux.constant.CommonConstants;
import com.valoux.enums.ItemComponents;
import com.valoux.helper.AppraisalHelper;
import com.valoux.helper.ItemHelper;
import com.valoux.helper.UserHelper;
import com.valoux.model.AgentModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.ValouxAgentStoreModel;
import com.valoux.model.ValouxDiamondModel;
import com.valoux.model.ValouxItemComponentModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxItemTypeModel;
import com.valoux.model.ValouxMetalModel;
import com.valoux.model.ValouxSharedRequestModel;
import com.valoux.model.ValouxStoreModel;
import com.valoux.service.AgentService;
import com.valoux.service.AppraisalService;
import com.valoux.service.CollectionService;
import com.valoux.service.ItemService;
import com.valoux.service.UserService;
import com.valoux.service.ValouxStoreService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This <java>class</java> serves request related to different operations
 * performed on Item/s
 * 
 * @author Ankita Garg
 * 
 */

@Path("item")
@Component
public class ItemWS {

	@Autowired
	ItemService itemService;

	@Autowired
	UserService userService;

	@Autowired
	ValouxStoreService storeService;

	@Autowired
	AppraisalService appraisalService;

	@Autowired
	CollectionService collectionService;

	@Autowired
	AgentService agentService;

	private static final Logger LOGGER = Logger.getLogger(ItemWS.class);

	/**
	 * This method receives request to add Item: 1. Check request for essential
	 * paparameters. 2. Converts <code>JSONObject</code> request to business
	 * objects. 3. Calls service method to add new items. 4. On success send
	 * back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addOrUpdateItem.csv")
	public Response addOrUpdateItem(JSONObject formpaparam, @Context HttpServletRequest request) throws Exception {
		LOGGER.info("Enter Method addOrUpdateItem of ItemWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			ValouxItemModel itemModel = null;
			ValouxStoreModel storeModel = null;
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey", "action","publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			String action = JSONUtility.getSafeString(jObject, "action");
			JSONObject itemData = jObject.getJSONObject("itemInfo");
			Integer itemId = JSONUtility.getSafeInteger(itemData, "itemId");
			
			Integer itmId = 0;
			if(itemId != null){
				itmId = itemId;
			}
			
			ValouxItemModel valouxItemModel = itemService.getValouxItemDetailsById(itmId);
			
			if(valouxItemModel != null && CommonUserUtility.paparameterNullCheckObject(valouxItemModel.getItemStatus()) && valouxItemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_APPRAISED);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			String rKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			String name = JSONUtility.getSafeString(itemData, "name");
			String sdescription = JSONUtility.getSafeString(itemData, "sdescription");
			String designType = JSONUtility.getSafeString(itemData, "designType");
			Double designerPrice = JSONUtility.getSafeDouble(itemData, "designerPrice");
			Integer designerPriceType = JSONUtility.getSafeInteger(itemData, "designerPriceType");
			Integer jewelryGender = JSONUtility.getSafeInteger(itemData, "jewelryGender");
			Integer quantity = JSONUtility.getSafeInteger(itemData, "quantity");
			Integer itemType = JSONUtility.getSafeInteger(itemData, "itemType");
			Double salesPrice = JSONUtility.getSafeDouble(itemData, "salesPrice");
			Double salesTax = JSONUtility.getSafeDouble(itemData, "salesTax");
			Double valouxMarketValue = JSONUtility.getSafeDouble(itemData, "valouxMarketValue");
			Boolean storeExist = itemData.getBoolean("storeExist");
			JSONObject storeData = itemData.getJSONObject("storeData");
			Integer storeId = JSONUtility.getSafeInteger(storeData, "storeId");
			String storeName = JSONUtility.getSafeString(storeData, "storeName");
			String storePhone = JSONUtility.getSafeString(storeData, "storePhone");
			JSONArray imagesjArray = JSONUtility.getSafeJSONArray(itemData, "itemImages");
			String itemReceiptContent = JSONUtility.getSafeString(itemData, "itemReceiptContent");
			String itemReceiptName = JSONUtility.getSafeString(itemData, "itemReceiptName");
			String itemCertificateContent = JSONUtility.getSafeString(itemData, "itemCertificateContent");
			String itemCertificateName = JSONUtility.getSafeString(itemData, "itemCertificateName");
			boolean isStoreCreate = false;
			if (storeName != null && storeName != "" && !storeName.isEmpty()) {
				isStoreCreate = true;
			} else if (storeId != null && storeId != 0) {
				isStoreCreate = true;
			}
			if (isStoreCreate) {
				storeModel = new ValouxStoreModel();
				if (storeData.has("storeAddress")) {
					JSONObject storeAddress = storeData.getJSONObject("storeAddress");
					String streetNo = JSONUtility.getSafeString(storeAddress, "streetNo");
					String addressLine1 = JSONUtility.getSafeString(storeAddress, "addressLine1");
					String addressLine2 = JSONUtility.getSafeString(storeAddress, "addressLine2");
					String city = JSONUtility.getSafeString(storeAddress, "city");
					String state = JSONUtility.getSafeString(storeAddress, "state");
					String country = JSONUtility.getSafeString(storeAddress, "country");
					String zipCode = JSONUtility.getSafeString(storeAddress, "zipCode");
					Integer countryId = userService.getCountryId(country);
					Integer stateId = userService.getStateIdentifier(state, countryId);
					storeModel.setName(storeName);
					storeModel.setStreetNumber(streetNo);
					storeModel.setAddressLine1(addressLine1);
					storeModel.setAddressLine2(addressLine2);
					storeModel.setCity(city);
					storeModel.setStateId(stateId);
					storeModel.setCountryId(countryId);
					storeModel.setZipcode(zipCode);
					storeModel.setStoreId(storeId);
					storeModel.setName(storeName);
					storeModel.setPhone(storePhone);
					storeModel.setStatus(CommonConstants.USER_STATUS_INACTIVE);
					String ipaddress = request.getRemoteAddr();
					storeModel.setIpaddress(ipaddress);
					storeModel.setModifiedBy(rKey);
					storeModel.setModifiedOn(CommonUtility.getDateAndTime());
					storeModel.setCreatedBy(rKey);
					storeModel.setCreatedOn(CommonUtility.getDateAndTime());
				} // end of if block if storeAddress is not null
				String ipaddress = request.getRemoteAddr();
				storeModel.setStoreId(storeId);
				storeModel.setIpaddress(ipaddress);
				storeModel.setModifiedBy(publicKey);
				storeModel.setModifiedOn(CommonUtility.getDateAndTime());
				storeModel.setCreatedBy(publicKey);
				storeModel.setCreatedOn(CommonUtility.getDateAndTime());
			}
			itemModel = new ValouxItemModel();
			itemModel.setDesignerPrice(designerPrice);
			itemModel.setDesignerPriceType(designerPriceType);
			itemModel.setDesignType(designType);
			itemModel.setGender(jewelryGender);
			itemModel.setItemTypeIt(itemType);
			itemModel.setModifiedBy(publicKey);
			itemModel.setModifiedOn(CommonUtility.getDateAndTime());
			itemModel.setName(name);
			itemModel.setQuantity(quantity);
			itemModel.setrKey(rKey);
			itemModel.setSalesPrice(salesPrice);
			itemModel.setSalesTax(salesTax);
			itemModel.setsDescription(sdescription);
			itemModel.setStoreExist(storeExist);
			itemModel.setStoreId(storeId);
			itemModel.setValouxMarketValue(valouxMarketValue);
			itemModel.setItemId(itemId);
			Boolean isValidRequest = (itemId == null ? false : true);
			if (action.equals(CommonConstants.ADD)) {
				isValidRequest = CommonUserUtility.checkValidRequestForAddItem(itemModel);
			}
			if (!isValidRequest) {
				resData.put("itemInfo", new JSONObject());
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}// end of if block if request is not valid
			if (action.equals(CommonConstants.ADD)) {
				///////////Start/////////Adding new consumer data////////////////////////////
				LoginBean loginBean = new LoginBean();
				
				String encryptedKey = null;
				if(!CommonUserUtility.paparameterNullCheckStringObject(rKey)){
					JSONObject registrationData = itemData.optJSONObject("newConsumerData");
					
					if(registrationData != null){
						String requiredPaparams2[] = new String[] {"firstName","lastName","emailId"};
						missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams2, registrationData);
						if(CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)){
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
							json.putOpt("sCode", CommonConstants.INCOMPLETE);
							json.put("missingPaparams", "Paparameters missing - "+ missingPaparams);
							return Response.status(200).entity(json.toString()).build();
						}
						
						String emailId = JSONUtility.getSafeString(registrationData, "emailId");
						boolean isEmailExist = userService.checkEmailAlreadyRegistered(emailId);
						if (isEmailExist) {
							json.put("resData", "");
							json.put("errorMessage", CommonConstants.EMAIL_ID_ALREADY_EXIST);
							json.put("sCode", CommonConstants.ERROR);
							return Response.status(200).entity(json).build();
						}
						loginBean = AppraisalHelper.userLoginBeanCreationViaAgent(userService, registrationData, loginBean);
						
						if(loginBean != null){
							encryptedKey = (loginBean.getPrivateKey());
							rKey = encryptedKey;
							
							AppraisalHelper.userLoginTypeAndRoleCreationViaAgentForItem(userService, agentService, jObject, loginBean, encryptedKey);
							AppraisalHelper.userValouxAccessPermissionCreation(appraisalService, jObject, encryptedKey);
							itemService.updateSharedRequestForNewRegistration(loginBean.getUserName(), rKey,
									CommonConstants.CONSUMER);
						}
						
						itemModel.setrKey(encryptedKey);
					} else {
						json.put("resData", "");
						json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
						json.putOpt("sCode", CommonConstants.INCOMPLETE);
						json.put("missingPaparams", "Paparameters missing - userPublicKey");
						return Response.status(200).entity(json.toString()).build();
					}
				}
				///////////End/////////Adding new consumer data////////////////////////////
							/*storeModel.setModifiedBy(rKey);
							storeModel.setCreatedBy(rKey);*/
				ValouxStoreBean storeBean = storeService.createStore(storeModel, null, null);
				if (storeBean != null) {
					if (storeBean.getStoreId() != null) {
						itemModel.setStoreId(storeBean.getStoreId());
					}
				}
				itemModel.setCreatedBy(publicKey);
				itemModel.setCreatedOn(CommonUtility.getDateAndTime());
				itemModel.setItemStatus(CommonConstants.STATUS_ACTIVE);
				ValouxItemBean itemBean = new ValouxItemBean();
				itemBean = itemService.addItem(itemModel);
				if (itemBean == null) {
					json.put("resData", new JSONObject());
					json.put("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
					json.put("sCode", CommonConstants.ERROR);
				} else {
					if (imagesjArray != null) {
						itemService.saveItemImage(imagesjArray, itemBean.getItemId(), rKey);
					}
					if (itemCertificateContent != null && itemCertificateName != null) {
						itemService.saveItemCertificate(itemCertificateName, itemCertificateContent,
								itemBean.getItemId(), rKey);
					}
					if (itemReceiptContent != null && itemReceiptName != null) {
						itemService.saveItemReceipt(itemReceiptName, itemReceiptContent, itemBean.getItemId(), rKey);
					}
					AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
					if(agentModel!=null){
						ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
						sharedRequestModel.setApproveStatus(2);
						sharedRequestModel.setCreatedBy(publicKey);
						sharedRequestModel.setCreatedOn(CommonUtility.getDateAndTime());
						sharedRequestModel.setModifiedBy(publicKey);
						sharedRequestModel.setModifiedOn(CommonUtility.getDateAndTime());
						sharedRequestModel.setSharedItemId(itemBean.getItemId());
						sharedRequestModel.setSharedItemType(1);
						sharedRequestModel.setStatus(1);
						sharedRequestModel.setSharedBy(rKey);
						String[] sharedToArray = new String[1];
						sharedToArray[0]=publicKey;
						itemService.createSharedRequestAndSendEmail(sharedRequestModel, sharedToArray, null);
						itemService.sendMailToConsumerForItemCollectionAppraisalIdAdded(1, rKey);
					}
					JSONObject resJson = new JSONObject();
					resJson.put("userPublicKey", rKey);
					resJson.put("itemId", itemBean.getItemId());
					json.put("resData", resJson);
					json.put("successMessage", CommonConstants.ITEM_ADDED_SUCCESSFULL);
					json.put("sCode", CommonConstants.SUCCESS);
					return Response.status(200).entity(json.toString()).build();
				}
			}
			if (action.equals(CommonConstants.UPDATE)) {
//				if (storeData != null) {
					if (storeModel != null) {
						ValouxStoreBean storeBean = storeService.createStore(storeModel, null, null);

						if (storeBean != null) {
							if (storeBean.getStoreId() != null) {
								itemModel.setStoreId(storeBean.getStoreId());
							}
						}
					} 				
//					}

				itemService.updateItemDetail(itemId, rKey, itemModel);
				if (imagesjArray != null || itemReceiptContent != null || itemReceiptName != null
						|| itemCertificateContent != null || itemReceiptName != null) {
					itemService.updateItemImageDocument(imagesjArray, itemReceiptContent, itemReceiptName,
							itemCertificateName, itemCertificateContent, itemId, rKey);
				}
				JSONObject resJson = new JSONObject();
				resJson.put("itemId", itemId);
				json.put("resData", resJson);
				json.put("successMessage", CommonConstants.ITEM_UPDATED_SUCCESSFULL);
				json.put("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method addOrUpdateItem of ItemWS");
		return Response.status(200).entity(json.toString()).build();
	} // end of method addnUpdateItem

	/**
	 * This method receives request to get supporting master data related to
	 * item: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get master data for relatd to item. 4. Prepare response based
	 * on data and send back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getMasterDataForItemType.csv")
	public Response getMasterDataForItemType() throws Exception {
		LOGGER.info("Enter getMasterDataForItemType method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			List<ValouxItemTypeModel> itemTypeModelList = itemService.getMasterDataForItemType();
			JSONArray jarray = new JSONArray();
			if (itemTypeModelList != null && itemTypeModelList.size() > 0) {
				for (ValouxItemTypeModel itemTypeModel : itemTypeModelList) {
					JSONObject jObject = new JSONObject();
					jObject.put("itemTypeId", itemTypeModel.getItemTypeId());
					jObject.put("itemType", itemTypeModel.getItemType());
					jObject.put("status", itemTypeModel.getStatus());
					jarray.put(jObject);
				}// end of for loop
			} // end of if block if itemTypeModelList is not null
			JSONObject resData = new JSONObject();
			resData.put("itemTypeList", jarray);
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit getMasterDataForItemType method of ItemWS");
		return Response.status(200).entity(json).build();
	} // end of method getMasterDataForItemType

	/**
	 * This method receives request to get details of all the items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get all item
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllItemList.csv")
	public Response getAllItemList(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter getAllItemList method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey","publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(reqObject, "publicKey");
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			Integer startRecordNo = JSONUtility.getSafeInteger(reqObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(reqObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(reqObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(reqObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(reqObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
			Boolean isAgent = false;
			if(agentModel!=null){
				isAgent = true;
				List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedItemsToUserByConsumer(publicKey,CommonConstants.SHARED_TYPE_ITEM, CommonConstants.STATUS_ACCEPTED,rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
				if(sharedRequestBeanList!=null && sharedRequestBeanList.size()>0){
					for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
						ValouxItemModel itemModel = itemService.getValouxItemDetailsById(sharedRequestBean.getSharedItemId());
						itemModelList.add(itemModel);
					}
				}
			}else{
					
				itemModelList =	itemService.getAllItemList(rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
			}
			List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
			JSONArray jArray = new JSONArray();
			JSONArray imageJArray = null;
			if (itemModelList != null && itemModelList.size() > 0) {
				for (ValouxItemModel itemModel : itemModelList) {
					imageJArray = new JSONArray();
					JSONObject jObject = new JSONObject();
					jObject.put("consumerPublicKey", itemModel.getrKey());
					jObject.put("name", itemModel.getName());
					jObject.put("itemId", itemModel.getItemId());
					jObject.put("sdescription", itemModel.getsDescription());
					jObject.put("designType", itemModel.getDesignType());
					jObject.put("jewelryGender", itemModel.getGender());
					jObject.put("quantity", itemModel.getQuantity());
					jObject.put("itemType", itemModel.getItemTypeIt());
					jObject.put("appraisalAssociatedWithItem",
							appraisalService.getAppraisalIdAssociatedWithItem(itemModel.getItemId()));
					jObject.put("collectionAssociatedWithItem",
							collectionService.getCollectionIdAssociatedWithItem(itemModel.getItemId()));
					ItemHelper.addPricesOfItemToJson(itemModel, jObject,isAgent);
					Map<Integer, Integer> countMap = itemService.getNumberOfAgentAndConsumerToWhichItemIsShared(itemModel.getItemId(),1);
					if (countMap != null) {
						if (countMap.get(1) != 0) {
							jObject.put("sharedWithNumberOfConsumer", countMap.get(1));
						}
						if (countMap.get(2) != 0) {
							jObject.put("sharedWithNumberOfAgent", countMap.get(2));
						}
						if(countMap.get(3)!=0){
							jObject.put("sharedWithNumberOfUnregisteredUser", countMap.get(3));
						}
					}
					itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());

					if (itemImageModelList != null && itemImageModelList.size() > 0) {
						for (ItemImageModel itemImageModel : itemImageModelList) {
							if (itemImageModel.getImageType() == 1) {
								JSONObject imageJObject = new JSONObject();
								imageJObject.put("itemImagePath", itemImageModel.getImageurl());
								imageJObject.put("imageId", itemImageModel.getImageId());
								imageJArray.put(imageJObject);
							}
							if (itemImageModel.getImageType() == 2) {
								jObject.put("itemReceiptPath", itemImageModel.getImageurl());
								jObject.put("itemReceiptId", itemImageModel.getImageId());
							}
							if (itemImageModel.getImageType() == 3) {
								jObject.put("itemCertificatePath", itemImageModel.getImageurl());
								jObject.put("itemCertificateId", itemImageModel.getImageId());
							}
						}
						jObject.put("itemImages", imageJArray);
					}
					ItemHelper.getComponentsByItemId(itemService, jObject, itemModel.getItemId());
					jArray.put(jObject);
				} // end of for loop
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("itemData", jArray);
				json.put("resData", jsonResponse);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.ITEMS_NOT_FOUND);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit getAllItemList method of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get details of all the items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get all item
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteImageByImageId.csv")
	public Response deleteImageByImageId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteImageByImageId method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "itemId", "imageId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
			Integer imageId = JSONUtility.getSafeInteger(reqObject, "imageId");
			if (itemId == null || imageId == null) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			
			ValouxItemModel valouxItemModel = itemService.getValouxItemDetailsById(itemId);
			
			if(valouxItemModel != null && CommonUserUtility.paparameterNullCheckObject(valouxItemModel.getItemStatus()) && valouxItemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_APPRAISED);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			Boolean flag = itemService.deleteItemDocument(itemId, imageId);
			if (flag) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.IMAGE_DELETED_SUCCESSFULLY);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			}
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
			json.putOpt("sCode", CommonConstants.ERROR);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit deleteImageByImageId method of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get details of the item: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to get all item list. 4.
	 * Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemListByItemId.csv")
	public Response getItemListByItemId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter getItemListByItemId method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "itemId","publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
			if (itemId == null) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemId);
			ValouxStoreModel storeModel = null;
			String publicKey = JSONUtility.getSafeString(reqObject, "publicKey");
			Boolean isAgent = false;
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
			isAgent=true;
			}
			List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
			JSONArray imageJArray = new JSONArray();
			if (itemModel != null) {
				if (itemModel.getStoreId() != null) {
					storeModel = storeService.getStoreDataByStoreId(itemModel.getStoreId());
				}
				JSONObject jObject = new JSONObject();
				JSONObject storeJson = new JSONObject();
				jObject.put("userPublickKey", itemModel.getrKey());
				jObject.put("name", itemModel.getName());
				jObject.put("itemId", itemModel.getItemId());
				jObject.put("sdescription", itemModel.getsDescription());
				jObject.put("designType", itemModel.getDesignType());
				jObject.put("jewelryGender", itemModel.getGender());
				jObject.put("quantity", itemModel.getQuantity());
				jObject.put("itemType", itemModel.getItemTypeIt());
				jObject.put("itemTypeName", itemModel.getItemTypeName());
				jObject.put("consumerPublicKey", itemModel.getrKey());
				
				JSONObject userObject = new JSONObject();
				
				UserHelper.getUserDetailsFromLogin(userService, userObject, itemModel.getrKey());
				
				jObject.put("consumerDetails", userObject);
				
				ItemHelper.addPricesOfItemToJson(itemModel, jObject,isAgent);
				itemImageModelList = itemService.getItemImageModelList(itemModel.getItemId());
				if (itemImageModelList != null && itemImageModelList.size() > 0) {
					for (ItemImageModel itemImageModel : itemImageModelList) {
						if (itemImageModel.getImageType() == 1) {
							JSONObject imageJObject = new JSONObject();
							imageJObject.put("itemImagePath", itemImageModel.getImageurl());
							imageJObject.put("imageId", itemImageModel.getImageId());
							imageJArray.put(imageJObject);
						}
						if (itemImageModel.getImageType() == 2) {
							jObject.put("itemReceiptPath", itemImageModel.getImageurl());
							jObject.put("itemReceiptId", itemImageModel.getImageId());
						}
						if (itemImageModel.getImageType() == 3) {
							jObject.put("itemCertificatePath", itemImageModel.getImageurl());
							jObject.put("itemCertificateId", itemImageModel.getImageId());
						}
					}
					jObject.put("itemImages", imageJArray);
				}
				if (storeModel != null) {
					storeJson.put("storeId", storeModel.getStoreId());
					storeJson.put("storeName", storeModel.getName());
					storeJson.put("phone", storeModel.getPhone());
					JSONObject addressJson = new JSONObject();
					addressJson.put("gAddress", storeModel.getgAddress());
					addressJson.put("addressLine1", storeModel.getAddressLine1());
					addressJson.put("addressLine2", storeModel.getAddressLine2());
					// addressJson.put("streetNo",
					// storeModel.getStreetNumber());
					addressJson.put("country", storeModel.getCountryName());
					addressJson.put("city", storeModel.getCity());
					addressJson.put("state", storeModel.getStateName());
					addressJson.put("zipCode", storeModel.getZipcode());
					addressJson.put("street_number", storeModel.getStreetNumber());
					storeJson.put("storeAddress", addressJson);
					jObject.put("storeData", storeJson);
				}
				jObject.put("appraisalAssociatedWithItem",
						appraisalService.getAppraisalIdAssociatedWithItem(itemModel.getItemId()).length());
				jObject.put("collectionAssociatedWithItem",
						collectionService.getCollectionIdAssociatedWithItem(itemModel.getItemId()).length());
				
				// Get item price property details of item
				ItemHelper.getUpdatedItemPriceProperties(itemService, jObject, itemId);
				
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("itemData", jObject);
				json.put("resData", jsonResponse);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.ITEMS_NOT_FOUND);
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit getItemListByItemId method of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get details of the item: 1. Prepare
	 * response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getComponentsList.csv")
	public Response getComponentsList() throws Exception {
		LOGGER.info("Enter getComponentsList method of ItemWS");

		JSONObject resJson = new JSONObject();
		JSONObject newJson = new JSONObject();
		JSONArray jArray = new JSONArray();
		try {
			for (ItemComponents.Components component : ItemComponents.Components.values()) {
				JSONObject json = new JSONObject();
				json.put("componentId", component.getId());
				json.put("componentName", component);
				jArray.put(json);
			}
			newJson.put("componentList", jArray);
			resJson.put("resData", newJson);
			resJson.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			resJson.put("errorMessage", e);
			resJson.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(resJson).build();
		}
		LOGGER.info("Exit getComponentsList method of ItemWS");
		return Response.status(200).entity(resJson).build();

	}

	/**
	 * This method receives request to get details of the item: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to get all item list. 4.
	 * Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/checkItemNameExistForUser.csv")
	public Response checkItemNameExistForUser(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter checkItemNameExistForUser method of ItemWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "userPublicKey", "itemName" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {

				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
				String itemName = JSONUtility.getSafeString(reqObject, "itemName");
				Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
				Boolean nameExist = itemService.checkItemNameExistForUser(userPublicKey, itemName, itemId);

				if (!nameExist) {
					resJson.put("isitemNameExist", false);
					json.put("resData", resJson);
					json.putOpt("successMessage", CommonConstants.ITEM_NAME_NOTEXIST);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				} else {
					resJson.put("isitemNameExist", true);
					json.put("resData", resJson);
					json.putOpt("successMessage", CommonConstants.ITEM_NAME_EXIST);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit checkItemNameExistForUser method of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to save component details of the item: 1.
	 * Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to save all component in item. 4. Prepare response and send it for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addUpdateItemComponents.csv")
	public Response addUpdateItemComponents(JSONObject formpaparam) throws Exception {

		LOGGER.info("ItemWS : Enter method addUpdateItemComponents");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
//				UserModel userModel = userService.getConsumerDetailByRKey(userPublicKey);
				LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
				
				if(loginModel != null){
					
					List<ValouxItemComponentModel> valouxItemComponentModel = new ArrayList<ValouxItemComponentModel>();
					Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
					
					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemId);
					if(itemModel != null){
						resJson.put("itemId", itemId);
						
						if(CommonUserUtility.paparameterNullCheckObject(itemModel.getItemStatus()) && itemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
							json.put("resData", resJson);
							json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_APPRAISED);
							json.putOpt("sCode", CommonConstants.ERROR);
						} else {
							// Fetching components for updating in item
							JSONArray componentObject = JSONUtility.getSafeJSONArray(reqObject, "itemComponents");

							if (componentObject != null) {
								ItemHelper.populateValouxItemComponentModel(valouxItemComponentModel, reqObject);

								itemService.addValouxItemComponents(valouxItemComponentModel);
								itemService.updateItemMarketValueFromComponents(itemId);

								json.put("resData", resJson);
								json.put("sCode", CommonConstants.SUCCESS);
								json.put("successMessage", CommonConstants.ITEM_UPDATED_SUCCESSFULL);
							} else {
								json.put("resData", resJson);
								json.put("sCode", CommonConstants.SUCCESS);
								json.put("successMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
							}
						}
						// Get component list details of item
						ItemHelper.getComponentsByItemId(itemService, resJson, itemId);
					} else {
						json.put("resData", resJson);
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", CommonConstants.ITEM_NAME_NOTEXIST);
					}
				} else {
					json.put("resData", resJson);
					json.putOpt("errorMessage", CommonConstants.INVALID_REQUEST);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("ItemWS : Exit method addUpdateItemComponents");
		return Response.status(200).entity(json).build();

	}

	/**
	 * This method receives request to delete component from item: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service delete component from item
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteComponentFromItem.csv")
	public Response deleteComponentFromItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("ItemWS : Enter Method deleteComponentFromItem");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "itemId", "itemComponentId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
				Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
				
				ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemId);
				if(itemModel != null){
					if(CommonUserUtility.paparameterNullCheckObject(itemModel.getItemStatus()) && itemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
						json.put("resData", resJson);
						json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_APPRAISED);
						json.putOpt("sCode", CommonConstants.ERROR);
						return Response.status(200).entity(json).build();
					} else {
						ValouxItemComponentModel itemComponentBean = itemService.getComponentsByItemAndComponentId(itemId, componentId);
						if (itemComponentBean != null) {
							if(itemComponentBean.getComponentsType() != null){
								Integer componentType = Integer.valueOf(itemComponentBean.getComponentsType());
								if (componentType.equals(CommonConstants.COMPONENT_DIAMOND)) {
									itemService.deleteDiamondComponentFromItem(itemId, componentId);

								} else if (componentType.equals(CommonConstants.COMPONENT_GEMSTONES)) {
									itemService.deleteGemstoneComponentFromItem(itemId, componentId);
									
								} else if (componentType.equals(CommonConstants.COMPONENT_PEARLS)) {
									itemService.deletePearlComponentFromItem(itemId, componentId);
									
								} else if (componentType.equals(CommonConstants.COMPONENT_METALS)) {
									itemService.deleteMetalComponentFromItem(itemId, componentId);
									
								}
							}
							itemService.deleteComponentFromItem(itemId, componentId);
							itemService.updateItemMarketValueFromComponents(itemId);
							
							json.put("resData", resJson);
							json.putOpt("successMessage", CommonConstants.ITEM_COMPONENTS_DELETED_SUCCESSFULL);
							json.putOpt("sCode", CommonConstants.SUCCESS);
						} else {
							json.put("resData", resJson);
							json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
							json.putOpt("sCode", CommonConstants.ERROR);
						}
					}
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("ItemWS : Exit Method deleteComponentFromItem");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get component details of the item: 1.
	 * Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to save all component in item. 4. Prepare response and send it for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemComponents.csv")
	public Response getItemComponents(JSONObject formpaparam) throws Exception {

		LOGGER.info("ItemWS : Enter method getValouxItemComponents");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
				
				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
//				UserModel userModel = userService.getConsumerDetailByRKey(userPublicKey);
//				if(userModel != null){
				LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
				
				if(loginModel != null){

					json.put("resData", resJson);
					json.put("sCode", CommonConstants.SUCCESS);

					// Get component list details of item
					ItemHelper.getComponentsByItemId(itemService, resJson, itemId);
				} else {
					json.put("resData", resJson);
					json.putOpt("errorMessage", CommonConstants.INVALID_REQUEST);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("ItemWS : Exit method getValouxItemComponents");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to save item component property details of
	 * the item: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to save item component property in item. 4. Prepare response and
	 * send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * @author Paparamjeet
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addUpdateItemComponentProperties.csv")
	public Response addUpdateItemComponentProperties(JSONObject formpaparam) throws Exception {

		LOGGER.info("ItemWS : Enter method addUpdateItemComponentProperties");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId", "itemComponentId", "componentType", "requestType" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				
				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
//				UserModel userModel = userService.getConsumerDetailByRKey(userPublicKey);
				LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
				
				if(loginModel != null){
					JSONObject componentProperty = reqObject.optJSONObject("componentProperty");
					if (componentProperty != null) {

						String requestType = JSONUtility.getSafeString(reqObject, "requestType");
						Integer componentType = JSONUtility.getSafeInteger(reqObject, "componentType");

						String requiredPaparams2[] = null;
						
						if (componentType.equals(CommonConstants.COMPONENT_DIAMOND)) {
							if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
								requiredPaparams2 = new String[] { "clarityId" };
							} 
						} else if (componentType.equals(CommonConstants.COMPONENT_GEMSTONES)) {
							if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
								requiredPaparams2 = new String[] { "gemstonesType", "internalInclusions" };
							} 
						} else if (componentType.equals(CommonConstants.COMPONENT_PEARLS)) {
							if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
								requiredPaparams2 = new String[] { "pearlType" };
							} 
						} else if (componentType.equals(CommonConstants.COMPONENT_METALS)) {
//							if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
//								requiredPaparams2 = new String[] { "color" };
//							} 
						}
						missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams2, componentProperty);

						if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
							json.putOpt("sCode", CommonConstants.INCOMPLETE);
							json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
							return Response.status(200).entity(json.toString()).build();
						} else {
							JSONArray componentImages = JSONUtility.getSafeJSONArray(componentProperty, "componentImages");

							if (componentImages != null && componentImages.length() > 0) {
								
								requiredPaparams2 = new String[] { "fileType", "imageContent", "imageName" };

								for (int i = 0; i < componentImages.length(); i++) {

									JSONObject object = componentImages.getJSONObject(i);

									missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams2, object);
									
									if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
										json.put("resData", "");
										json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
										json.putOpt("sCode", CommonConstants.INCOMPLETE);
										json.put("missingPaparams", "Paparameters missing - "+ missingPaparams);
										return Response.status(200).entity(json.toString()).build();
									}
								}
							}
							
							Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
							Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
							
							Integer itemStatus = 1;
							ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemId);
							if(itemModel != null){
								
								if(itemModel.getItemStatus() != null){
									itemStatus = itemModel.getItemStatus();
								}
								
								ValouxItemComponentModel componentModel = itemService.getComponentsByItemAndComponentId(itemId, componentId);
								if(componentModel != null){
									
									// Adding component by type
									ItemHelper.addValouxComponentByType(itemService, reqObject, itemStatus);
									// fetching component by type
									ItemHelper.getValouxItemComponentProperty(itemService, reqObject, resJson);

									json.put("resData", resJson);
									json.put("sCode", CommonConstants.SUCCESS);
									if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
										json.put("successMessage", CommonConstants.ITEM_COMPONENT_ADDED_SUCCESSFULL);
									} else {
										json.put("successMessage", CommonConstants.ITEM_COMPONENT_UPDATED_SUCCESSFULL);
									}
								} else {
									json.put("resData", resJson);
									json.put("sCode", CommonConstants.SUCCESS);
									json.put("successMessage", CommonConstants.ITEM_NAME_NOTEXIST);
								}
							} else {
								json.put("resData", resJson);
								json.put("sCode", CommonConstants.SUCCESS);
								json.put("successMessage", CommonConstants.ITEM_NAME_NOTEXIST);
							}
							
							if(itemStatus.equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
								json.put("successMessage", CommonConstants.ITEM_ALREADY_APPRAISED);
							}
						}
					} else {
						json.put("resData", resJson);
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
						json.put("missingPaparams", "Paparameters missing - componentProperty");
					}
				} else {
					json.put("resData", resJson);
					json.putOpt("errorMessage", CommonConstants.INVALID_REQUEST);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("ItemWS : Exit method addUpdateItemComponentProperties");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to share Item: 1. Check request for
	 * essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to share item. 4. On success
	 * send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/shareItem.csv")
	public Response shareItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method shareItem of ItemWS");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedBy", "sharedItemId", "sharedItemType" };
			String sharedBy = JSONUtility.getSafeString(jObject, "sharedBy");
			String sharedTo = JSONUtility.getSafeString(jObject, "sharedTo");
			String[] sharedToArray = null;
			String[] sharedToEmailArray = null;

			if (sharedTo != null) {
				sharedToArray = sharedTo.split(",");
			}
			String sharedToEmail = JSONUtility.getSafeString(jObject, "sharedToEmail");
			if (sharedToEmail != null) {
				sharedToEmailArray = sharedToEmail.split(",");
			}
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)
					|| (sharedToEmailArray == null && sharedToArray == null)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer sharedItemId = JSONUtility.getSafeInteger(jObject, "sharedItemId");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer approveStatus = 1;
			ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
			sharedRequestModel.setApproveStatus(approveStatus);
			sharedRequestModel.setCreatedBy(sharedBy);
			sharedRequestModel.setCreatedOn(CommonUtility.getDateAndTime());
			sharedRequestModel.setModifiedBy(sharedBy);
			sharedRequestModel.setModifiedOn(CommonUtility.getDateAndTime());
			sharedRequestModel.setSharedItemId(sharedItemId);
			sharedRequestModel.setSharedItemType(sharedItemType);
			sharedRequestModel.setStatus(1);
			sharedRequestModel.setSharedBy(sharedBy);
			List<ValouxSharedRequestModel> sharedRequestModelList = itemService.createSharedRequestAndSendEmail(
					sharedRequestModel, sharedToArray, sharedToEmailArray);
			if (sharedRequestModelList == null) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			} else {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.SHARED_REQUEST_SENDED);
				json.putOpt("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}

		LOGGER.info("Exit Method shareItem of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get list of shared contacts: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get list of
	 * shared contacts. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getListOfSharedContact.csv")
	public Response getListOfSharedContact(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getListOfSharedContact of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedBy" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedBy = JSONUtility.getSafeString(jObject, "sharedBy");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedContacts(sharedBy, sharedItemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedTo() != null) {
						LoginModel loginModel = userService.getLoginDetailByPKey((sharedRequestBean.getSharedTo()));
						JSONObject resData = new JSONObject();
						resData.put("publickKey", sharedRequestBean.getSharedTo());
						resData.put("emailId", loginModel.getUserName());
						resData.put("firstName", loginModel.getFirstName());
						resData.put("middleName", loginModel.getMiddleName());
						resData.put("lastName", loginModel.getLastName());
						jArray.put(resData);
					}
				}
			}
			json.put("contactList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getListOfSharedContact of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get list of shared items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get list of
	 * shared items. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getListOfItemsSharedByUser.csv")
	public Response getListOfItemsSharedByUser(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getListOfItemsSharedByUser of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedBy" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedBy = JSONUtility.getSafeString(jObject, "sharedBy");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer sharedWith = JSONUtility.getSafeInteger(jObject, "sharedWith");// sharedWith is variable indicating 1-consumer 2-agent/store
			// 3-non registered user
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedItems(sharedBy, sharedItemType, sharedWith, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					JSONObject resJson = new JSONObject();
					ItemHelper.getItemComponentAppraisalDetail(itemService, collectionService, appraisalService, sharedRequestBean, resJson,false);
					jArray.put(resJson);
				}
			}
			json.put("sharedItemList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getListOfItemsSharedByUser of ItemWS");
		return Response.status(200).entity(json).build();
	}

	

	/**
	 * This method receives request to get list of shared contacts: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get list of
	 * shared contacts. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemComponentProperty.csv")
	public Response getItemComponentProperty(JSONObject formpaparam) throws Exception {
		LOGGER.info("ItemWS : Enter Method getItemComponentProperty");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId", "itemComponentId", "componentType" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
				
//				UserModel userModel = userService.getConsumerDetailByRKey(userPublicKey);
//				if(userModel != null){
				LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
				
				if(loginModel != null){
					// Adding component by type
					ItemHelper.getValouxItemComponentProperty(itemService, reqObject, resJson);

					json.put("resData", resJson);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", resJson);
					json.putOpt("errorMessage", CommonConstants.INVALID_REQUEST);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}

		LOGGER.info("ItemWS : Exit Method getItemComponentProperty");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to share Item: 1. Check request for
	 * essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to share item. 4. On success
	 * send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unShareItem.csv")
	public Response unShareItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method unShareItem of ItemWS");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey", "sharedItemId", "sharedItemType" };
			// String sharedTo = JSONUtility.getSafeString(jObject, "sharedTo");

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer sharedItemId = JSONUtility.getSafeInteger(jObject, "sharedItemId");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String sharedTo = JSONUtility.getSafeString(jObject, "sharedTo");
			JSONArray shareToArray = JSONUtility.getSafeJSONArray(jObject, "shareToArray");

			ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
			sharedRequestModel.setSharedItemId(sharedItemId);
			sharedRequestModel.setSharedItemType(sharedItemType);
			sharedRequestModel.setSharedBy(userPublicKey);
			sharedRequestModel.setSharedTo(sharedTo);
			itemService.unShareItemAndSendEmail(sharedRequestModel, shareToArray);

			json.put("resData", "");
			json.putOpt("successMessage", CommonConstants.UNSHARED_REQUEST_SENDED);
			json.putOpt("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}

		LOGGER.info("Exit Method unShareItem of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get list of agent to which item is
	 * shared: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get list of shared request. 4. On success send back response
	 * for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAgentListSharedWithItem.csv")
	public Response getAgentListSharedWithItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getAgentListSharedWithItem of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedBy", "sharedItemId", "sharedItemType" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedBy = JSONUtility.getSafeString(jObject, "sharedBy");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer sharedItemId = JSONUtility.getSafeInteger(jObject, "sharedItemId");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedRequest(sharedBy, sharedItemType, sharedItemId, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedTo() != null) {
						JSONObject addressJson = new JSONObject();
						String rKey = sharedRequestBean.getSharedTo();
						String pKey = (sharedRequestBean.getSharedTo());
						LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
						AgentModel agentProfileModel = agentService.getAgentDetailByRelationKey(rKey);

						JSONObject resData = new JSONObject();
						if (agentProfileModel != null) {
							ValouxAgentStoreModel agentStoreModel = agentService.getStoreDataByRelationKey(rKey);

							resData.put("publickKey", sharedRequestBean.getSharedTo());
							resData.put("emailId", loginModel.getUserName());
							resData.put("firstName", loginModel.getFirstName());
							resData.put("middleName", loginModel.getMiddleName());
							resData.put("lastName", loginModel.getLastName());
							resData.put("mobilePhone", agentProfileModel.getMobile());
							if (agentStoreModel != null && agentStoreModel.getStoreId() != null) {
								ValouxStoreModel storeModel = storeService.getStoreDataByStoreId(agentStoreModel
										.getStoreId());
								if(storeModel != null){
									resData.put("storeId", storeModel.getStoreId());
									resData.put("storeName", storeModel.getName());
									addressJson.put("streetNo", storeModel.getStreetNumber());
									addressJson.put("addressLine1", storeModel.getAddressLine1());
									addressJson.put("addressLine2", storeModel.getAddressLine2());
									addressJson.put("country", storeModel.getCountryName());
									addressJson.put("state", storeModel.getStateName());
									addressJson.put("zipCode", storeModel.getZipcode());
									addressJson.put("city", storeModel.getCity());
									resData.put("agentAddress", addressJson);
								}
							}
							jArray.put(resData);
						}
					}
				}
			}
			json.put("agentList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getAgentListSharedWithItem of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get list of un registered user to which item is
	 * shared: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get list of shared request. 4. On success send back response
	 * for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUnRegisteredUserListSharedWithItem.csv")
	public Response getUnRegisteredUserListSharedWithItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getUnRegisteredUserListSharedWithItem of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedBy", "sharedItemId", "sharedItemType" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedBy = JSONUtility.getSafeString(jObject, "sharedBy");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer sharedItemId = JSONUtility.getSafeInteger(jObject, "sharedItemId");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedRequest(sharedBy, sharedItemType, sharedItemId, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedTo() == null) {
						JSONObject resData = new JSONObject();
							resData.put("emailId", sharedRequestBean.getSharedToEmail());
							jArray.put(resData);
						
					}
				}
			}
			json.put("unRegisteredUserList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getUnRegisteredUserListSharedWithItem of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get list of consumer to which item is
	 * shared: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get list of shared request. 4. On success send back response
	 * for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getConsumerListSharedWithItem.csv")
	public Response getConsumerListSharedWithItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getConsumerListSharedWithItem of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedBy", "sharedItemId", "sharedItemType" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedBy = JSONUtility.getSafeString(jObject, "sharedBy");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer sharedItemId = JSONUtility.getSafeInteger(jObject, "sharedItemId");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}

			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedRequest(sharedBy, sharedItemType, sharedItemId, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedTo() != null) {
						JSONObject addressJson = new JSONObject();
						JSONObject responseJson = new JSONObject();
						String rKey = sharedRequestBean.getSharedTo();
						String pKey = (sharedRequestBean.getSharedTo());
						LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
						UserModel userModel = userService.getConsumerDetailByRKey(rKey);
						if (userModel != null) {
							responseJson.put("publicKey", rKey);
							responseJson.put("firstName", loginModel.getFirstName());
							responseJson.put("lastName", loginModel.getLastName());
							responseJson.put("middleName", loginModel.getMiddleName());
							responseJson.put("emailId", loginModel.getUserName());
							responseJson.put("mobilePhone", userModel.getMobile());
							responseJson.put("imageUrl", userModel.getImageURL());
							addressJson.put("address", userModel.getGlobalAddress());
							addressJson.put("addressLine1", userModel.getAddressLine1());
							addressJson.put("addressLine2", userModel.getAddressLine2());
							addressJson.put("streetNo", userModel.getStreetNo());
							addressJson.put("country", userModel.getCountryName());
							addressJson.put("city", userModel.getCity());
							addressJson.put("state", userModel.getStateName());
							addressJson.put("zipCode", userModel.getZipCode());
							responseJson.put("userAddress", addressJson);
							jArray.put(responseJson);
						}
					}
				}
			}
			json.put("consumerList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getConsumerListSharedWithItem of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get details of all the items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get all item
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteItemComponentImage.csv")
	public Response deleteItemComponentImage(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteItemComponentImage method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "itemComponentId", "cid" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {

				Integer componentId = JSONUtility.getSafeInteger(reqObject, "itemComponentId");
				Integer imageId = JSONUtility.getSafeInteger(reqObject, "cid");
				
				ValouxItemComponentModel itemComponentBean = itemService.getComponentsById(componentId);
				if(itemComponentBean != null && itemComponentBean.getItemId() != null){
					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemComponentBean.getItemId());
					if(itemModel != null){
						if(CommonUserUtility.paparameterNullCheckObject(itemModel.getItemStatus()) && itemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_APPRAISED);
							json.putOpt("sCode", CommonConstants.ERROR);
							return Response.status(200).entity(json).build();
						} else {
							Boolean flag = itemService.deleteItemComponentDocument(componentId, imageId);
							if (flag) {
								json.put("resData", "");
								json.putOpt("successMessage", CommonConstants.IMAGE_DELETED_SUCCESSFULLY);
								json.putOpt("sCode", CommonConstants.SUCCESS);
								return Response.status(200).entity(json.toString()).build();
							} else {
								json.put("resData", "");
								json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
								json.putOpt("sCode", CommonConstants.ERROR);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit deleteItemComponentImage method of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get list of shared items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get list of
	 * shared items. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getListOfRequestedItemsSharedToAgent.csv")
	public Response getListOfRequestedItemsSharedToAgent(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getListOfRequestedItemsSharedToAgent of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publickKey" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedTo = JSONUtility.getSafeString(jObject, "publickKey");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfRequestedSharedItemsToUser(sharedTo, sharedItemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					JSONObject resJson = new JSONObject();
					ItemHelper.getItemComponentAppraisalDetail(itemService, collectionService, appraisalService, sharedRequestBean, resJson,true);
					LoginModel loginModel = userService.getLoginDetailByPKey((sharedRequestBean
							.getSharedBy()));
					if (loginModel != null) {
						JSONObject sharedByJson = new JSONObject();
						sharedByJson.put("firstName", loginModel.getFirstName());
						sharedByJson.put("middleName", loginModel.getMiddleName());
						sharedByJson.put("lastName", loginModel.getLastName());
						sharedByJson.put("consumerPublicKey", sharedRequestBean.getSharedBy());
						resJson.put("sharedBy", sharedByJson);
					}
					resJson.put("approvedStatus", sharedRequestBean.getApproveStatus());
					resJson.put("sharedRequestId", sharedRequestBean.getSharedRequestId());
					resJson.put("userPublickKey", sharedRequestBean.getSharedBy());
					jArray.put(resJson);
				}
			}
			json.put("sharedItemList", jArray);
			json.put("sharedTo", sharedTo);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getListOfRequestedItemsSharedToAgent of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get list of shared items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get list of
	 * shared items. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getListOfItemsSharedToUser.csv")
	public Response getListOfItemsSharedToUser(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getListOfItemsSharedToUser of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedTo" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedTo = JSONUtility.getSafeString(jObject, "sharedTo");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer approvedStatus = JSONUtility.getSafeInteger(jObject, "approvedStatus");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedItemsToUser(sharedTo, sharedItemType, approvedStatus, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					JSONObject resJson = new JSONObject();
					Boolean isAgent = false;
					AgentModel agentModel = agentService.getAgentDetailByRelationKey(sharedTo);
					if(agentModel!=null){
						isAgent=true;
					}
					ItemHelper.getItemComponentAppraisalDetail(itemService, collectionService, appraisalService, sharedRequestBean, resJson,isAgent);
					LoginModel loginModel = userService.getLoginDetailByPKey((sharedRequestBean
							.getSharedBy()));
					UserModel userModel = userService.getConsumerDetailByRKey(sharedRequestBean.getSharedBy());
					if (loginModel != null) {
						JSONObject sharedByJson = new JSONObject();
						sharedByJson.put("firstName", loginModel.getFirstName());
						sharedByJson.put("middleName", loginModel.getMiddleName());
						sharedByJson.put("lastName", loginModel.getLastName());
						sharedByJson.put("consumerPublicKey", sharedRequestBean.getSharedBy());
						if(userModel!=null){
							sharedByJson.put("imageUrl", userModel.getImageURL());
						}
						resJson.put("lastUpdated", sharedRequestBean.getModifiedOn());
						resJson.put("sharedBy", sharedByJson);
					}
					resJson.put("sharedRequestId", sharedRequestBean.getSharedRequestId());
					resJson.put("approvedStatus", sharedRequestBean.getApproveStatus());
					resJson.put("userPublickKey", sharedRequestBean.getSharedBy());
					jArray.put(resJson);
				}
			}
			json.put("sharedItemList", jArray);
			json.put("sharedTo", sharedTo);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getListOfItemsSharedToUser of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get list of consumer who shared item with
	 * agent: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get list of shared request. 4. On success send back response
	 * for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getConsumerListWhoSharedItemWithAgent.csv")
	public Response getConsumerListWhoSharedItemWithAgent(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getConsumerListWhoSharedItemWithAgent of ItemWS");
		JSONObject json = new JSONObject();

		JSONArray jArray = new JSONArray();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			String sharedTo = JSONUtility.getSafeString(jObject, "userPublicKey");
			Integer sharedItemType = JSONUtility.getSafeInteger(jObject, "sharedItemType");
			Integer startRecordNo = JSONUtility.getSafeInteger(jObject, "startRecordNo");
			Integer numberOfRecords = JSONUtility.getSafeInteger(jObject, "numberOfRecords");
			String searchKey = JSONUtility.getSafeString(jObject, "searchKey");
			String sortBy = JSONUtility.getSafeString(jObject, "sortBy");
			String sortOrder = JSONUtility.getSafeString(jObject, "sortOrder");
			if(startRecordNo!=null && numberOfRecords==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - numberOfRecords");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			if(sortBy!=null && sortOrder==null){
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - sortOrder");
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedItemsBySharedTo(sharedTo, sharedItemType, startRecordNo, numberOfRecords, sortBy, sortOrder);
			if (sharedRequestBeanList != null && sharedRequestBeanList.size() > 0) {
				for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
					if (sharedRequestBean.getSharedBy() != null) {
						JSONObject addressJson = new JSONObject();
						JSONObject responseJson = new JSONObject();
						String rKey = sharedRequestBean.getSharedBy();
						String pKey = (sharedRequestBean.getSharedBy());
						LoginModel loginModel = userService.getLoginDetailByPKey(pKey);
						UserModel userModel = userService.getConsumerDetailByRKey(rKey);
						if (userModel != null) {
							responseJson.put("consumerPublicKey", rKey);
							responseJson.put("firstName", loginModel.getFirstName());
							responseJson.put("lastName", loginModel.getLastName());
							responseJson.put("middleName", loginModel.getMiddleName());
							responseJson.put("emailId", loginModel.getUserName());
							responseJson.put("mobilePhone", userModel.getMobile());
							responseJson.put("imageUrl", userModel.getImageURL());
							addressJson.put("address", userModel.getGlobalAddress());
							addressJson.put("addressLine1", userModel.getAddressLine1());
							addressJson.put("addressLine2", userModel.getAddressLine2());
							addressJson.put("streetNo", userModel.getStreetNo());
							addressJson.put("country", userModel.getCountryName());
							addressJson.put("city", userModel.getCity());
							addressJson.put("state", userModel.getStateName());
							addressJson.put("zipCode", userModel.getZipCode());
							responseJson.put("userAddress", addressJson);
							jArray.put(responseJson);
						}
						else{
							responseJson.put("consumerPublicKey", rKey);
							responseJson.put("firstName", loginModel.getFirstName());
							responseJson.put("lastName", loginModel.getLastName());
							responseJson.put("middleName", loginModel.getMiddleName());
							responseJson.put("emailId", loginModel.getUserName());
							jArray.put(responseJson);
						}
					}
				}
			}
			json.put("consumerList", jArray);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getConsumerListWhoSharedItemWithAgent of ItemWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to accept or reject share request: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to accept or reject
	 * shared item request. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/acceptOrRejectShareRequest.csv")
	public Response acceptOrRejectShareRequest(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method acceptOrRejectShareRequest of ItemWS");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "sharedRequestId", "approvedStatus" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer sharedRequestId = JSONUtility.getSafeInteger(jObject, "sharedRequestId");
			Integer approvedStatus = JSONUtility.getSafeInteger(jObject, "approvedStatus");
			// 1 Requested, 2 Accepted, 3 Rejected
			Boolean flag = itemService.approveOrRejectSharedRequest(sharedRequestId, approvedStatus);
			if (flag) {
				json.put("successmessage", CommonConstants.ACCEPT_OR_REJECT_SHARE_MESSAGE);
				json.put("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getListOfItemsSharedToUser of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get diamond price: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to accept or reject
	 * shared item request. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemComponentDiamondSpecifyPrice.csv")
	public Response getItemComponentDiamondSpecifyPrice(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getItemComponentDiamondSpecifyPrice of ItemWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "clarityId", "color", "shape", "totalWeight"};

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}

			ValouxDiamondModel diamondModel = new ValouxDiamondModel();
			
			ItemHelper.populateValouxDiamondModelForPrice(diamondModel, reqObject);
			if(diamondModel != null){
				itemService.getItemComponentDiamondSpecifyPrice(diamondModel);
			}
			
			resData.put("specifiedValue", diamondModel.getSpecifiedValue());
			resData.put("specifiedDate", diamondModel.getSpecifiedDate());
			
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getItemComponentDiamondSpecifyPrice of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get diamond price: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to accept or reject
	 * shared item request. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemComponentMetalSpecifyPrice.csv")
	public Response getItemComponentMetalSpecifyPrice(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter Method getItemComponentMetalSpecifyPrice of ItemWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "purity", "metalsType", "weight"};

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}

			ValouxMetalModel metalModel = new ValouxMetalModel();
			
			ItemHelper.populateValouxMetalModelForPrice(metalModel, reqObject);
			if(metalModel != null){
				itemService.getItemComponentMetalSpecifyPrice(metalModel);
			}
			resData.put("specifiedValue", metalModel.getSpecifiedValue());
			resData.put("specifiedDate", metalModel.getSpecifiedDate());
			
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method getItemComponentMetalSpecifyPrice of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to update metal master price: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to accept or reject
	 * shared item request. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Path("/callMetalPriceUpdateService.csv")
	public Response callMetalPriceUpdateService() throws Exception {
		LOGGER.info("Enter Method callMetalPriceUpdateService of ItemWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			itemService.callMetalPriceUpdateService();
			
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);
			json.put("successMessage", CommonConstants.ITEM_METAL_PRICE_UPDATED_SUCCESSFULL);
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method callMetalPriceUpdateService of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to save component details of the item: 1.
	 * Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to save all component in item. 4. Prepare response and send it for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateItemPriceProperties.csv")
	public Response updateItemPriceProperties(JSONObject formpaparam) throws Exception {

		LOGGER.info("ItemWS : Enter method addUpdateItemComponents");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
				LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
				
				if(loginModel != null){
					Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
					
					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemId);
					if(itemModel != null){
						resJson.put("itemId", itemId);

						// Fetching price property for updating in item
						JSONObject priceProperty = reqObject.optJSONObject("priceProperty");

						if (priceProperty != null) {
							ItemHelper.populateValouxItemPricePropertyModel(itemModel, reqObject);

							itemService.updateItemPriceProperties(itemModel);

							json.put("resData", resJson);
							json.put("sCode", CommonConstants.SUCCESS);
							json.put("successMessage", CommonConstants.ITEM_UPDATED_SUCCESSFULL);
						} else {
							json.put("resData", resJson);
							json.put("sCode", CommonConstants.SUCCESS);
							json.put("successMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
						}
						
						// Get item price property details of item
						ItemHelper.getUpdatedItemPriceProperties(itemService, resJson, itemId);
					} else {
						json.put("resData", resJson);
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", CommonConstants.ITEM_NAME_NOTEXIST);
					}
				} else {
					json.put("resData", resJson);
					json.putOpt("errorMessage", CommonConstants.INVALID_REQUEST);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("ItemWS : Exit method addUpdateItemComponents");
		return Response.status(200).entity(json).build();

	}
	
	/**
	 * This method receives request to save component details of the item: 1.
	 * Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to save all component in item. 4. Prepare response and send it for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemPriceProperties.csv")
	public Response getItemPriceProperties(JSONObject formpaparam) throws Exception {

		LOGGER.info("ItemWS : Enter method getItemPriceProperties");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
				LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
				
				if(loginModel != null){
					Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
					
					ValouxItemModel itemModel = itemService.getValouxItemDetailsById(itemId);
					if(itemModel != null){
						resJson.put("itemId", itemId);
						// Get item price property details of item
						ItemHelper.getUpdatedItemPriceProperties(itemService, resJson, itemId);
						
						json.put("resData", resJson);
						json.put("sCode", CommonConstants.SUCCESS);
					} else {
						json.put("resData", resJson);
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", CommonConstants.ITEM_NAME_NOTEXIST);
					}
				} else {
					json.put("resData", resJson);
					json.putOpt("errorMessage", CommonConstants.INVALID_REQUEST);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("ItemWS : Exit method getItemPriceProperties");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to update metal master price: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to accept or reject
	 * shared item request. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Path("/callItemMarketPriceUpdateService.csv")
	public Response callItemMarketPriceUpdateService() throws Exception {
		LOGGER.info("Enter Method callItemMarketPriceUpdateService of ItemWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();

		try {
			itemService.callItemMarketPriceUpdateService();
			
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);
			json.put("successMessage", CommonConstants.ITEM_MARKET_PRICE_UPDATED_SUCCESSFULL);
		} catch (Exception e) {

			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit Method callItemMarketPriceUpdateService of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get details of all the items: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get all item
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteItemByItemId.csv")
	public Response deleteItemByItemId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteImageByImageId method of ItemWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey", "userPublicKey", "itemId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
			String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			ValouxItemModel valouxItemModel = itemService.getValouxItemDetailsById(itemId);
			
			if(valouxItemModel == null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_NOT_FOUND);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			if(valouxItemModel != null && CommonUserUtility.paparameterNullCheckObject(valouxItemModel.getItemStatus()) && valouxItemModel.getItemStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_APPRAISED_ONDELETE);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			Boolean flag = itemService.deleteItemAndAllDetails(itemId, userPublicKey);
			if (flag) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.ITEM_DELETED_SUCCESSFULLY);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			}
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.ITEM_ERROR_MESSAGE);
			json.putOpt("sCode", CommonConstants.ERROR);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit deleteImageByImageId method of ItemWS");
		return Response.status(200).entity(json).build();
	}
	
}// end of class
