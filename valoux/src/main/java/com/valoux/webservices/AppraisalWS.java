/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */
package com.valoux.webservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springfparamework.beans.factory.annotation.Autowired;
import org.springfparamework.stereotype.Component;

import com.valoux.bean.AppraisalBean;
import com.valoux.bean.AppraisalCollectionBean;
import com.valoux.bean.AppraisalItemsBean;
import com.valoux.bean.LoginBean;
import com.valoux.bean.ValouxSharedRequestBean;
import com.valoux.constant.CommonConstants;
import com.valoux.helper.AppraisalHelper;
import com.valoux.helper.ItemHelper;
import com.valoux.model.AgentModel;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalItemsModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.LoginModel;
import com.valoux.model.UserModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxSharedRequestModel;
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
 * performed on Appraisal/s
 * 
 * @author param Sheikh
 * 
 */

@Path("appraisal")
@Component
public class AppraisalWS {

	@Autowired
	AppraisalService appraisalService;

	@Autowired
	UserService userService;

	@Autowired
	AgentService agentService;

	@Autowired
	CollectionService collectionService;

	@Autowired
	ItemService itemService;
	
	@Autowired
	ValouxStoreService storeService;

	private static final Logger LOGGER = Logger.getLogger(AppraisalWS.class);

	/**
	 * This method receives request to create or update an Appraisal and
	 * performs following operations: 1. Check request for essential paparameters.
	 * 2. Converts <code>JSONObject</code> request to business objects. 3. If
	 * validations are not failing call service method to create or update
	 * Appraisal. 4. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	/*@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createOrUpdateAppraisal.csv")
	public Response createOrUpdateAppraisal(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method createOrUpdateAppraisal of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		JSONObject createAppraisalData = new JSONObject();
		AppraisalModel appraisalModel = new AppraisalModel();
		AppraisalBean appraisalBean = new AppraisalBean();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject appraisalData = jObject.getJSONObject("appraisalData");
			String requiredPaparams[] = { "userPublicKey", "name", "shortDescription", "requestType" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, appraisalData);
			String requiredPaparams1[] = { "publicKey"};
			String missingPaparams1 = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1, jObject);
			
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams1)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer appraisalId = JSONUtility.getSafeInteger(appraisalData, "appraisalId");
			String rKey = JSONUtility.getSafeString(appraisalData, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			String name = JSONUtility.getSafeString(appraisalData, "name");
			String shortDescription = JSONUtility.getSafeString(appraisalData, "shortDescription");
			String requestType = JSONUtility.getSafeString(appraisalData, "requestType");
			appraisalModel.setAppraisalId(appraisalId);
			appraisalModel.setRelationKey(rKey);
			appraisalModel.setName(name);
			appraisalModel.setShortDescription(shortDescription);
			appraisalModel.setRequestType(requestType);
			appraisalModel.setModifiedOn(CommonUtility.getDateAndTime());
			appraisalModel.setModifiedBy(publicKey);
			if (appraisalId == null) {
				appraisalModel.setCreatedBy(publicKey);
			}
			boolean isValidRequest = CommonUserUtility.checkValidRequestForAppraisal(appraisalModel);
			if (!isValidRequest) {
				JSONObject blankJsonObject = new JSONObject();
				json.put("resData", blankJsonObject);
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				return Response.status(200).entity(json).build();
			}
			
			boolean isAppraisalExist = appraisalService.checkAppraisalAlreadyExist(rKey, name);
			if (isAppraisalExist) {
				json.put("resData", createAppraisalData);
				json.put("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.APPRAISAL_ALREADY_EXIST);
				return Response.status(200).entity(json).build();
			}
			if(requestType.equalsIgnoreCase(CommonConstants.ADD)){
				appraisalModel.setCreatedOn(CommonUtility.getDateAndTime());
				
		///////////Start/////////Adding new consumer data////////////////////////////
							LoginBean loginBean = new LoginBean();
							
							String encryptedKey = null;
							if(!CommonUserUtility.paparameterNullCheckStringObject(rKey)){
								JSONObject registrationData = appraisalData.optJSONObject("newConsumerData");
								
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
									encryptedKey = (loginBean.getPrivateKey());
									rKey = encryptedKey;
									if(loginBean != null){
										AppraisalHelper.userLoginTypeAndRoleCreationViaAgentForAppraisal(userService, agentService, jObject, loginBean, encryptedKey);
										AppraisalHelper.userValouxAccessPermissionCreation(appraisalService, jObject, encryptedKey);
									}
									
									appraisalModel.setRelationKey(encryptedKey);
									appraisalModel.setCreatedBy(rKey);
								} else {
									json.put("resData", "");
									json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
									json.putOpt("sCode", CommonConstants.INCOMPLETE);
									json.put("missingPaparams", "Paparameters missing - userPublicKey");
									return Response.status(200).entity(json.toString()).build();
								}
							}
							///////////End/////////Adding new consumer data////////////////////////////
							
			}
			appraisalBean = appraisalService.createOrUpdateAppraisal(appraisalModel);

			if (appraisalBean == null) {
				json.put("resData", createAppraisalData);
				json.put("errorMessage", CommonConstants.APPRAISAL_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			if(appraisalBean.getAppraisalId()!=null){
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				if(agentModel!=null){
					ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
					sharedRequestModel.setApproveStatus(2);
					sharedRequestModel.setCreatedBy(publicKey);
					sharedRequestModel.setCreatedOn(CommonUtility.getDateAndTime());
					sharedRequestModel.setModifiedBy(publicKey);
					sharedRequestModel.setModifiedOn(CommonUtility.getDateAndTime());
					sharedRequestModel.setSharedItemId(appraisalBean.getAppraisalId());
					sharedRequestModel.setSharedItemType(3);
					sharedRequestModel.setStatus(1);
					sharedRequestModel.setSharedBy(rKey);
					String[] sharedToArray = new String[1];
					sharedToArray[0]=publicKey;
					itemService.createSharedRequestAndSendEmail(sharedRequestModel, sharedToArray, null);
				}
			}
			if (appraisalId != null && appraisalBean != null) {
				
				resData.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("resData", resData);
				// json.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("successMessage", CommonConstants.APPRAISAL_UPDATE_COMPLETE_MESSAGE);

				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				resData.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("resData", resData);
				// json.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE);

				json.put("sCode", CommonConstants.SUCCESS);

			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.ERROR);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method createOrUpdateAppraisal of AppraisalWS");
		return Response.status(200).entity(json).build();
	} // end of method createAppraisal
*/
	/**
	 * This method receives request to create an Appraisal for Items or
	 * Collection and performs following operations: 1. Check request for
	 * essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. If validations are not failing call service method
	 * to create Appraisal for Items or Collection. 4. Send back response for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createOrUpdateAppraisalForItemsOrCollection.csv")
	public Response createAppraisalForItemsOrCollection(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method createAppraisalForItemsOrCollection of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		JSONObject createAppraisalData = new JSONObject();
		AppraisalItemsModel appraisalItemsModel = new AppraisalItemsModel();
		AppraisalCollectionModel appraisalCollectionModel = new AppraisalCollectionModel();
		AppraisalItemsBean appraisalItemsBean = new AppraisalItemsBean();
		AppraisalCollectionBean appraisalCollectionBean = new AppraisalCollectionBean();
		AppraisalModel appraisalModel = new AppraisalModel();
		AppraisalModel appraisalBean = new AppraisalModel();
		Boolean isAgent = false;
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject appraisalItemOrCollectionData = jObject.getJSONObject("appraisalItemOrCollectionData");
			String requiredPaparams[] = { "requestType", "userPublicKey"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams,
					appraisalItemOrCollectionData);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String requiredPaparams1[] = { "publicKey"};
			String missingPaparams1 = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1,
					jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams1)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer appraisalId = JSONUtility.getSafeInteger(appraisalItemOrCollectionData, "appraisalId");
			
			Integer appId = 0;
			if(appraisalId != null){
				appId = appraisalId;
			}
			AppraisalModel appraisalmodel = appraisalService.getAppraisalDetailsById(appId);
			if(appraisalmodel != null && appraisalmodel.getaStatus() != null && appraisalmodel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", resData);
				json.putOpt("errorMessage", CommonConstants.APPRAISAL_ALREADY_APPRAISED);
				json.putOpt("sCode", CommonConstants.ERROR);
			} else {
				JSONArray itemId = JSONUtility.getSafeJSONArray(appraisalItemOrCollectionData, "itemId");
				JSONArray collectionId = JSONUtility.getSafeJSONArray(appraisalItemOrCollectionData, "collectionId");
				String requestType = JSONUtility.getSafeString(appraisalItemOrCollectionData, "requestType");
				String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				JSONObject registrationData = appraisalItemOrCollectionData.optJSONObject("newConsumerData");
				if(publicKey!=null){
				if(agentModel!=null){
					isAgent=true;
				}
				}
				/*
				 * Integer appraisalItemId =
				 * JSONUtility.getSafeInteger(appraisalItemOrCollectionData,
				 * "appraisalItemId"); Integer appraisalCollectionId =
				 * JSONUtility.getSafeInteger(appraisalItemOrCollectionData,
				 * "appraisalCollectionId");
				 */

				String rKey = JSONUtility.getSafeString(appraisalItemOrCollectionData, "userPublicKey");
				String name = JSONUtility.getSafeString(appraisalItemOrCollectionData, "name");
				String shortDescription = JSONUtility.getSafeString(appraisalItemOrCollectionData, "shortDescription");

				appraisalModel.setAppraisalId(appraisalId);
				appraisalModel.setRelationKey(rKey);
				appraisalModel.setName(name);
				appraisalModel.setShortDescription(shortDescription);
				appraisalModel.setRequestType(requestType);
				appraisalModel.setModifiedOn(CommonUtility.getDateAndTime());
				appraisalModel.setCreatedOn(CommonUtility.getDateAndTime());
				appraisalModel.setModifiedBy(publicKey);
				if (appraisalId == null) {
					appraisalModel.setCreatedBy(publicKey);
				}
				if(requestType.equalsIgnoreCase(CommonConstants.ADD)){
					appraisalModel.setCreatedOn(CommonUtility.getDateAndTime());
					
					///////////Start/////////Adding new consumer data////////////////////////////
					LoginBean loginBean = new LoginBean();
					
					String encryptedKey = null;
					if(!CommonUserUtility.paparameterNullCheckStringObject(rKey)){
						//JSONObject appraisalItemOrCollectionData = jObject.getJSONObject("appraisalItemOrCollectionData");
						
						
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
								AppraisalHelper.userLoginTypeAndRoleCreationViaAgentForAppraisal(userService, agentService, jObject, loginBean, encryptedKey);
								AppraisalHelper.userValouxAccessPermissionCreation(appraisalService, jObject, encryptedKey);
								itemService.updateSharedRequestForNewRegistration(loginBean.getUserName(), rKey,
										CommonConstants.CONSUMER);
								resData.put("userPublicKey", rKey);
							}
							
							appraisalModel.setRelationKey(rKey);
							appraisalModel.setCreatedBy(publicKey);
						} else {
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
							json.putOpt("sCode", CommonConstants.INCOMPLETE);
							json.put("missingPaparams", "Paparameters missing - userPublicKey");
							return Response.status(200).entity(json.toString()).build();
						}
					}
					///////////End/////////Adding new consumer data////////////////////////////
								
				}
				appraisalBean = appraisalService.createOrUpdateAppraisal(appraisalModel);

				if (appraisalBean == null) {
					json.put("resData", createAppraisalData);
					json.put("errorMessage", CommonConstants.APPRAISAL_ERROR_MESSAGE);
					json.put("sCode", CommonConstants.ERROR);
					return Response.status(200).entity(json).build();
				}
				if(appraisalBean.getAppraisalId()!=null){
					
					if(agentModel!=null){
						ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
						sharedRequestModel.setApproveStatus(2);
						sharedRequestModel.setCreatedBy(publicKey);
						sharedRequestModel.setCreatedOn(CommonUtility.getDateAndTime());
						sharedRequestModel.setModifiedBy(publicKey);
						sharedRequestModel.setModifiedOn(CommonUtility.getDateAndTime());
						sharedRequestModel.setSharedItemId(appraisalBean.getAppraisalId());
						sharedRequestModel.setSharedItemType(3);
						sharedRequestModel.setStatus(1);
						sharedRequestModel.setSharedBy(rKey);
						String[] sharedToArray = new String[1];
						sharedToArray[0]=publicKey;
						itemService.createSharedRequestAndSendEmail(sharedRequestModel, sharedToArray, null);
						itemService.sendMailToConsumerForItemCollectionAppraisalIdAdded(3, rKey);
					}
				}
				if (appraisalId != null && appraisalBean != null) {
					resData.put("appraisalId", appraisalBean.getAppraisalId());
					resData.put("name", appraisalBean.getName());
					resData.put("shortDescription", appraisalBean.getShortDescription());
					// resData.put("appraisalId", appraisalBean.getAppraisalId());
					json.put("resData", resData);
					json.put("successMessage", CommonConstants.APPRAISAL_UPDATE_COMPLETE_MESSAGE);

					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					resData.put("appraisalId", appraisalBean.getAppraisalId());
					resData.put("name", appraisalBean.getName());
					resData.put("shortDescription", appraisalBean.getShortDescription());
					json.put("resData", resData);
					json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE);
					json.put("sCode", CommonConstants.SUCCESS);

				}
				if (requestType.equalsIgnoreCase("Add")) {
					appraisalItemsModel.setAppraisalId(appraisalBean.getAppraisalId());
					appraisalCollectionModel.setAppraisalId(appraisalBean.getAppraisalId());
				}
				appraisalItemsModel.setRequestType(requestType);
				appraisalCollectionModel.setRequestType(requestType);
				/*
				 * appraisalItemsModel.setId(appraisalItemId);
				 * appraisalCollectionModel.setId(appraisalCollectionId);
				 */

				appraisalItemsModel.setItemId(itemId);
				appraisalItemsBean = appraisalService.checkExistingAppraisalItems(appraisalItemsModel);
				if (appraisalItemsBean != null) {
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.APPRAISAL_ITEM_EXISTING_MESSAGE);
					json.putOpt("sCode", CommonConstants.ERROR);
					return Response.status(200).entity(json.toString()).build();
				}

				appraisalCollectionModel.setCollectionId(collectionId);
				appraisalCollectionBean = appraisalService.checkExistingAppraisalCollection(appraisalCollectionModel);
				if (appraisalCollectionBean != null) {
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.APPRAISAL_COLLECTION_EXISTING_MESSAGE);
					json.putOpt("sCode", CommonConstants.ERROR);
					return Response.status(200).entity(json.toString()).build();
				}

				if (itemId != null && collectionId != null) {
					appraisalItemsModel.setAppraisalId(appraisalBean.getAppraisalId());
					appraisalItemsModel.setItemId(itemId);
					appraisalCollectionModel.setAppraisalId(appraisalBean.getAppraisalId());
					appraisalCollectionModel.setCollectionId(collectionId);
					boolean isValidRequestForAppraisalItemsAndCollection = CommonUserUtility
							.checkValidRequestForAppraisalItemsAndCollection(appraisalItemsModel, appraisalCollectionModel);
					if (!isValidRequestForAppraisalItemsAndCollection) {
						JSONObject blankJsonObject = new JSONObject();
						json.put("resData", blankJsonObject);
						json.put("sCode", CommonConstants.INCOMPLETE);
						json.put("errorMessage", CommonConstants.INVALID_REQUEST);
						return Response.status(200).entity(json).build();
					}

					if (appraisalItemsModel.getRequestType().equalsIgnoreCase("Update")
							&& appraisalCollectionModel.getRequestType().equalsIgnoreCase("Update")) {
						appraisalItemsBean = appraisalService.addAppraisalItems(appraisalItemsModel);
						appraisalCollectionBean = appraisalService.addAppraisalCollection(appraisalCollectionModel);
						json.put("resData", resData);
						json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE_FOR_ITEM_OR_COLLECTION_UPDATE);
						json.put("sCode", CommonConstants.SUCCESS);
					} else {
						appraisalItemsBean = appraisalService.addAppraisalItems(appraisalItemsModel);
						appraisalCollectionBean = appraisalService.addAppraisalCollection(appraisalCollectionModel);
						json.put("resData", resData);
						json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE_FOR_ITEM_OR_COLLECTION);
						json.put("sCode", CommonConstants.SUCCESS);
					}
				} else if (itemId != null && itemId.length() > 0) {
					appraisalItemsModel.setAppraisalId(appraisalBean.getAppraisalId());
					appraisalItemsModel.setItemId(itemId);
					boolean isValidRequestForAppraisalItems = CommonUserUtility
							.checkValidRequestForAppraisalItems(appraisalItemsModel);
					if (!isValidRequestForAppraisalItems) {
						JSONObject blankJsonObject = new JSONObject();
						json.put("resData", blankJsonObject);
						json.put("sCode", CommonConstants.INCOMPLETE);
						json.put("errorMessage", CommonConstants.INVALID_REQUEST);
						return Response.status(200).entity(json).build();
					}
					appraisalItemsBean = appraisalService.addAppraisalItems(appraisalItemsModel);
					if (appraisalItemsBean != null) {
						if (appraisalItemsModel.getRequestType().equalsIgnoreCase("update")) {
							json.put("resData", resData);
							json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE_ITEMS_UPDATE);
							json.put("sCode", CommonConstants.SUCCESS);
						} else {
							json.put("resData", resData);
							json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE_ITEMS);
							json.put("sCode", CommonConstants.SUCCESS);
						}
					} else {
						json.put("resData", createAppraisalData);
						json.put("errorMessage", CommonConstants.APPRAISAL_ERROR_MESSAGE);
						json.put("sCode", CommonConstants.ERROR);
						return Response.status(200).entity(json).build();
					}
				} else if (collectionId != null && collectionId.length() > 0) {
					appraisalCollectionModel.setAppraisalId(appraisalBean.getAppraisalId());
					appraisalCollectionModel.setCollectionId(collectionId);
					boolean isValidRequestForAppraisalCollection = CommonUserUtility
							.checkValidRequestForAppraisalCollection(appraisalCollectionModel);
					if (!isValidRequestForAppraisalCollection) {
						JSONObject blankJsonObject = new JSONObject();
						json.put("resData", blankJsonObject);
						json.put("sCode", CommonConstants.INCOMPLETE);
						json.put("errorMessage", CommonConstants.INVALID_REQUEST);
						return Response.status(200).entity(json).build();
					}
					appraisalCollectionBean = appraisalService.addAppraisalCollection(appraisalCollectionModel);
					if (appraisalCollectionBean != null) {
						if (appraisalCollectionModel.getRequestType().equalsIgnoreCase("update")) {
							json.put("resData", resData);
							json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE_COLLECTION_UPDATE);
							json.put("sCode", CommonConstants.SUCCESS);

						} else {
							json.put("resData", resData);
							json.put("successMessage", CommonConstants.APPRAISAL_COMPLETE_MESSAGE_COLLECTION);
							json.put("sCode", CommonConstants.SUCCESS);
						}
					} else {
						json.put("resData", createAppraisalData);
						json.put("errorMessage", CommonConstants.APPRAISAL_ERROR_MESSAGE);
						json.put("sCode", CommonConstants.ERROR);
						return Response.status(200).entity(json).build();
					}
				}
			}
			AppraisalHelper.getAppraisalCollectionsItemsDetails(appraisalService, itemService, collectionService, resData, appraisalBean.getAppraisalId(),isAgent);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.ERROR);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method createAppraisalForItemsOrCollection of AppraisalWS");
		return Response.status(200).entity(json).build();
	} // end of method createAppraisalForItemsOrCollection

	/**
	 * This method get All apraisal data . Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllAppraisalListForSuperAdmin.csv")
	public Response getAllAppraisalListForSuperAdmin() throws Exception {
		LOGGER.info("Enter Method getAllAppraisalListForSuperAdmin of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		JSONArray appraisalList = new JSONArray();
		List<AppraisalBean> appraisalbeanList = new ArrayList<AppraisalBean>();
		appraisalbeanList = appraisalService.getAllAppraisalListForSupoerAdmin();
		if (appraisalbeanList != null && appraisalbeanList.size() > 0) {
			for (AppraisalBean appraisalBean : appraisalbeanList) {
				resData.put("publicKey", appraisalBean.getRelationKey());
				resData.put("name", appraisalBean.getName());
				resData.put("shortDescription", appraisalBean.getShortDescription());
				resData.put("appraisalStatus", appraisalBean.getaStatus());
				resData.put("appraisalId", appraisalBean.getAppraisalId());
				resData.put("numberOfCollectionAssociated",
						appraisalService.getNumberOfCollectionOfAppraisal(appraisalBean.getAppraisalId()));
				resData.put("numberOfItemsAssociated",
						appraisalService.getNumberOfItemsOfAppraisal(appraisalBean.getAppraisalId()));
				UserModel userModel = userService.getConsumerDetailByRKey(appraisalBean.getRelationKey());
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(appraisalBean.getRelationKey());
				LoginModel loginModel = userService.getLoginDetailByPKey((appraisalBean
						.getRelationKey()));
				if (userModel != null) {
					resData.put("consumerName", loginModel.getFirstName());
				}
				if (agentModel != null) {
					resData.put("agentName", loginModel.getFirstName());
				}

				appraisalList.put(resData);
			}
			json.put("appraisalList", appraisalList);
		} else {
			json.put("appraisalList", "");
			json.put("errorMessage", CommonConstants.LIST_EMPTY);
			json.put("sCode", CommonConstants.SUCCESS);
		}

		json.put("sCode", CommonConstants.SUCCESS);
		LOGGER.info("Exit Method getAllAppraisalListForSuperAdmin of AppraisalWS");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to approved Or disapproved an Appraisal and
	 * performs following operations: 1. Check request for essential paparameters.
	 * 2. Converts <code>JSONObject</code> request to business objects. 3. If
	 * validations are not failing call service method to approved Or
	 * disapproved an Appraisal. 4. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/approvedOrDisapprovedAppraisal.csv")
	public Response approvedOrDisapprovedAppraisal(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method approvedOrDisapprovedAppraisal of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		JSONObject createAppraisalData = new JSONObject();
		AppraisalModel appraisalModel = new AppraisalModel();
		AppraisalBean appraisalBean = new AppraisalBean();
		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject appraisalDataForApprovedOrDisapproved = jObject.getJSONObject("approvedOrDisapprovedAppraisal");
			String requiredPaparams[] = { "appraisalId", "status", "agentId", "userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams,
					appraisalDataForApprovedOrDisapproved);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer appraisalId = JSONUtility.getSafeInteger(appraisalDataForApprovedOrDisapproved, "appraisalId");
			Integer status = JSONUtility.getSafeInteger(appraisalDataForApprovedOrDisapproved, "status");
			String agentId = JSONUtility.getSafeString(appraisalDataForApprovedOrDisapproved, "agentId");
			String rKey = JSONUtility.getSafeString(appraisalDataForApprovedOrDisapproved, "userPublicKey");
			appraisalModel.setCreatedBy(rKey);
			appraisalModel.setAppraisalId(appraisalId);
			appraisalModel.setaStatus(status);
			appraisalModel.setApprovedBy(agentId);

			boolean isValidRequest = CommonUserUtility
					.checkValidRequestForAppraisalApprovedOrDisapproved(appraisalModel);
			if (!isValidRequest) {
				JSONObject blankJsonObject = new JSONObject();
				json.put("resData", blankJsonObject);
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("errorMessage", CommonConstants.INVALID_REQUEST);
				return Response.status(200).entity(json).build();
			}
			boolean isAppraisalAlredyApproved = appraisalService.checkAppraisalAlreadyApproved(appraisalId, status);
			if (isAppraisalAlredyApproved) {
				json.put("resData", createAppraisalData);
				json.put("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.APPRAISAL_ALREADY_APPROVED);
				return Response.status(200).entity(json).build();
			}
			
			AppraisalModel appraisalmodel = appraisalService.getAppraisalDetailsById(appraisalId);
			if(appraisalmodel != null && appraisalmodel.getaStatus() != null){
				if(appraisalmodel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.APPRAISAL_ALREADY_APPRAISED);
					json.putOpt("sCode", CommonConstants.ERROR);
					return Response.status(200).entity(json).build();
				}
			}

			appraisalBean = appraisalService.approvedOrDisapprovedAppraisal(appraisalModel);

			if (appraisalBean == null) {
				json.put("resData", createAppraisalData);
				json.put("errorMessage", CommonConstants.APPRAISAL_ERROR_MESSAGE);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			if (appraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)) {
				resData.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("resData", resData);
				// json.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("successMessage", CommonConstants.APPRAISAL_APPROVED_MESSAGE);

				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				resData.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("resData", resData);
				// json.put("appraisalId", appraisalBean.getAppraisalId());
				json.put("successMessage", CommonConstants.APPRAISAL_DIS_APPROVED_MESSAGE);

				json.put("sCode", CommonConstants.SUCCESS);

			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.ERROR);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit Method approvedOrDisapprovedAppraisal of AppraisalWS");
		return Response.status(200).entity(json).build();
	} // end of method approvedOrDisapprovedAppraisal

	/**
	 * This method receives request to get details of all the user appraisal
	 * list with collection and items: 1. Check request for essential
	 * paparameters. 2. Converts <code>JSONObject</code> request to business
	 * objects. 3. Calls service method to get all the user appraisal list with
	 * collection and items. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserAppraisalList.csv")
	public Response getUserAppraisalList(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method getUserAppraisalList of AppraisalWS");
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
			Integer aStatus = JSONUtility.getSafeInteger(reqObject, "aStatus");//1 Active, 2 Inactive, 3 Approved/Appraised
			JSONObject appraisalObject = new JSONObject();
			List<AppraisalModel> appraisalModels = new ArrayList<AppraisalModel>();
			UserModel userModel = userService.getConsumerDetailByRKey(publicKey);
			Boolean isAgent = false;
			if(userModel!=null){
			appraisalModels = appraisalService.getUserAppraisalList(rKey,aStatus);
			}else{
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				if(agentModel!=null){
					isAgent = true;
					appraisalModels = appraisalService.getAppraisalListAssociatedWithAgentWithStatus(publicKey, aStatus);
				}
			}
			JSONArray jArray = new JSONArray();

			if (appraisalModels != null && appraisalModels.size() > 0) {
				for (AppraisalModel valouxAppraisalModel : appraisalModels) {
					JSONObject aObject = new JSONObject();
					aObject.put("appraisalId", valouxAppraisalModel.getAppraisalId());
					aObject.put("name", valouxAppraisalModel.getName());
					aObject.put("shortDescription", valouxAppraisalModel.getShortDescription());
					aObject.put("aStatus", valouxAppraisalModel.getaStatus());
					aObject.put("approvedBy", valouxAppraisalModel.getApprovedBy());
					aObject.put("approvedOn", valouxAppraisalModel.getApprovedOn());
					aObject.put("consumerPublicKey", valouxAppraisalModel.getRelationKey());
					aObject.put("lastUpdatedOn", valouxAppraisalModel.getModifiedOn());
					AppraisalHelper.addPriceOfAppraisal(valouxAppraisalModel, aObject, appraisalService, itemService, collectionService,isAgent);
					Map<Integer, Integer> countMap = itemService.getNumberOfAgentAndConsumerToWhichItemIsShared(valouxAppraisalModel.getAppraisalId(),3);
					if (countMap != null) {
						if (countMap.get(1) != 0) {
							aObject.put("sharedWithNumberOfConsumer", countMap.get(1));
						}
						if (countMap.get(2) != 0) {
							aObject.put("sharedWithNumberOfAgent", countMap.get(2));
						}
						if(countMap.get(3)!=0){
							aObject.put("sharedWithNumberOfUnregisteredUser", countMap.get(3));
						}
					}
					

					
					jArray.put(aObject);
				}
				appraisalObject.put("appraisal", jArray);
				json.put("resData", appraisalObject);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.APPRAISAL_NOT_FOUND);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("AppraisalWS : Enter Method getUserAppraisalList of AppraisalWS");
		return Response.status(200).entity(json).build();
	}// end of method getUserAppraisalList

	/**
	 * This method receives request to get Collection of appraisal: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method get collection of
	 * appraisal. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCollectionOfAppraisal.csv")
	public Response getCollectionOfAppraisal(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method getCollectionOfAppraisal");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey", "appraisalId","publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			JSONObject collectionJson = new JSONObject();
			String rKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			Boolean isAgent = false;
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
			isAgent=true;
			}
			// Get collection list details of appraisal
			AppraisalHelper.getCollectionOfAppraisalsById(appraisalService, itemService, collectionService, collectionJson, appraisalId, false,isAgent);
			if (collectionJson.has("collectionList")) {
				json.put("resData", collectionJson);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", collectionJson);
				json.put("errorMessage", CommonConstants.LIST_EMPTY);
				json.put("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method getCollectionOfAppraisal");
		return Response.status(200).entity(json).build();
	}// end of the method getCollectionOfAppraisal

	
	/**
	 * This method receives request to get user collection list not in appraisal
	 * 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get collection list from collection. 4. On success send back
	 * response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCollectionListNotInAppraisal.csv")
	public Response getCollectionListNotInAppraisal(JSONObject formpaparam) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method getCollectionListNotInAppraisal");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject rqstObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = rqstObject.getJSONObject("collectionInfo");
			String requiredPaparams[] = { "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, collectionInfo);
			String requiredPaparams1[] = { "publicKey" };
			String missingPaparams1 = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1, rqstObject);
			
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams1)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(collectionInfo, "userPublicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(collectionInfo, "appraisalId");
			String publicKey = JSONUtility.getSafeString(rqstObject, "publicKey");
			if (appraisalId == null) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			} else {
				AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(appraisalId);
				if(appraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					json.put("resData", "");
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.NO_COLLECTION_CAN_BE_ADDDED_APPRAISAL);
					return Response.status(200).entity(json.toString()).build();
				}
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				List<ValouxCollectionModel> collectionModelList = new ArrayList<ValouxCollectionModel>();
				if(agentModel!=null){
					collectionModelList = itemService.getAgentSharedCollectionNotInAppraisal(publicKey, appraisalId, rKey);
				}else{
				 collectionModelList = appraisalService.getCollectionListNotInAppraisal(
						rKey, appraisalId);
				}
				JSONArray jArray = new JSONArray();
				if (collectionModelList != null && collectionModelList.size() > 0) {
					for (ValouxCollectionModel collectionModel : collectionModelList) {
						JSONObject jObject = new JSONObject();
						jObject.put("vcid", collectionModel.getVcid());
						jObject.put("cname", collectionModel.getCname());
						jObject.put("shortDescription", collectionModel.getShortDescription());
						jObject.put("collectionStatus", collectionModel.getCollectionStatus());
						// Get images details of collection
						AppraisalHelper.getImagesOfCollectionById(collectionService, jObject, collectionModel.getVcid());
						Integer countOfItem = appraisalService.getItemAssociatedWithCollection(collectionModel
								.getVcid());
						jObject.put("noOfItem", countOfItem);

						jArray.put(jObject);
					} // end of for loop
					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("collectionData", jArray);
					jsonResponse.put("appraisalId", appraisalId);

					// Get collectionId of appraisal
					AppraisalHelper.getCollectionIdsOfAppraisalById(appraisalService, jsonResponse, appraisalId);

					json.put("resData", jsonResponse);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", new JSONObject());
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.COLLECTION_NOT_FOUND);
				}
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("CollectionWS : Exit Method getUserItemsNotInCollection");
		return Response.status(200).entity(json).build();
	} // end of the method getCollectionListNotInAppraisal

	
	/**
	 * This method receives request to get details of the appraisal: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to get all appraisal
	 * list. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/checkAppraisalNameExistForUser.csv")
	public Response checkAppraisalNameExistForUser(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter checkAppraisalNameExistForUser method of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey", "appraisalName" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String publicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			String appraisalName = JSONUtility.getSafeString(reqObject, "appraisalName");
			Integer appraisalId = JSONUtility.getSafeInteger(reqObject, "appraisalId");
			if (publicKey == null || appraisalName == null) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.APPRAISAL_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Boolean isAppraisalExist = appraisalService.checkAppraisalNameExistForUser(publicKey, appraisalName,
					appraisalId);

			if (!isAppraisalExist) {
				resJson.put("isAppraisalExist", false);
				json.put("resData", resJson);
				json.putOpt("successMessage", CommonConstants.APPRAISAL_NAME_NOT_EXIST);
				json.putOpt("sCode", CommonConstants.SUCCESS);
			} else {
				resJson.put("isAppraisalExist", true);
				json.put("resData", resJson);
				json.putOpt("successMessage", CommonConstants.APPRAISAL_NAME_EXIST);
				json.putOpt("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit checkAppraisalNameExistForUser method of AppraisalWS");
		return Response.status(200).entity(json).build();
	}// end of the method checkAppraisalNameExistForUser

	/**
	 * This method receives request to get details of all the item collection
	 * list with items: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get all the item collection list 4. Prepare response and send
	 * it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAppraisalListAssociatedWithItem.csv")
	public Response getAppraisalListAssociatedWithItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method getAppraisalListAssociatedWithItem");
		JSONObject json = new JSONObject();
		JSONObject appraisals = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "itemId", "userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
			String rKey = JSONUtility.getSafeString(reqObject, "userPublicKey");

			JSONArray appraisalObject = new JSONArray();
			List<AppraisalBean> appraisalBeanList = appraisalService.getAppraisalDetailAssociatedWithItem(itemId);
			if (appraisalBeanList != null && appraisalBeanList.size() > 0) {
				for (AppraisalBean appraisalBean : appraisalBeanList) {
					JSONObject aObject = new JSONObject();
					// appraisalObject = new JSONObject();
					aObject.put("publicKey", appraisalBean.getRelationKey());
					aObject.put("name", appraisalBean.getName());
					aObject.put("shortDescription", appraisalBean.getShortDescription());
					aObject.put("appraisalStatus", appraisalBean.getaStatus());
					aObject.put("appraisalId", appraisalBean.getAppraisalId());
					List<AppraisalItemsModel> itemsBeans = appraisalService
							.getAppraisalItemsBeansByAppraisalId(appraisalBean.getAppraisalId());
					int count = 0;
					if (itemsBeans != null && itemsBeans.size() > 0) {
						count = itemsBeans.size();
						aObject.put("itemsCount", count);
					}
					
					List<AppraisalCollectionModel> appraisalColletionBeans = appraisalService
							.getAppraisalBeansByAppraisalId(appraisalBean.getAppraisalId());
					// if (appraisalColletionBeans != null) {
					JSONArray cArray = new JSONArray();
					JSONArray iArray = new JSONArray();
					if (appraisalColletionBeans != null) {
						for (AppraisalCollectionModel appraisalCollectionBean : appraisalColletionBeans) {
							// Get images details of collection
							JSONObject cObject = new JSONObject();
							AppraisalHelper.getImagesOfCollectionById(collectionService, cObject, appraisalCollectionBean.getValouxCollectionId());
							if (!cObject.toString().equalsIgnoreCase("{}")) {
								cArray.put(cObject);
							}

						}// need to work add items images also.
						aObject.put("collectionImages", cArray);
					} else {
						aObject.put("collectionImages", cArray);
					}

					if (itemsBeans != null) {
						List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
						if (itemsBeans != null && itemsBeans.size() > 0) {
							for (AppraisalItemsModel appraisalItemsBean : itemsBeans) {
								itemImageModelList = itemService.getItemImageModelList(appraisalItemsBean.getValouxItemId());
								JSONObject iObject = new JSONObject();
								JSONArray imageJArray = new JSONArray();
								// Get images details of items
								AppraisalHelper.getImagesOfItemsById(itemImageModelList, imageJArray, iObject);
								if (!iObject.toString().equalsIgnoreCase("{}")) {
									iArray.put(iObject);
								}

							}

						}
						aObject.put("itemImages", iArray);
					} else {
						aObject.put("itemImages", iArray);
					}
					appraisalObject.put(aObject);
				}
				appraisals.put("appraisals", appraisalObject);
				json.put("resData", appraisals);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				
				appraisals.put("appraisals", appraisalObject);
				json.put("resData", appraisals);
				json.put("errorMessage", CommonConstants.LIST_EMPTY);
				json.put("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("AppraisalWS : Enter Method getAppraisalListAssociatedWithItem");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get Items of appraisal: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method get Items of appraisal. 4. On
	 * success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemsOfAppraisal.csv")
	public Response getItemsOfAppraisal(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method getItemsOfAppraisal");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject itemsJson = new JSONObject();
			String requiredPaparams[] = { "userPublicKey", "appraisalId","publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			Boolean isAgent = false;
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
			isAgent=true;
			}
			// Get Items list details of appraisal
			// getItemsOfAppraisalsById(itemsJson, appraisalId, false);

			AppraisalHelper.getAppraisalsItemById(appraisalService, itemService, itemsJson, appraisalId, false,isAgent);

			json.put("resData", itemsJson);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method getItemsOfAppraisal");
		return Response.status(200).entity(json).build();
	}// end of the method getItemsOfAppraisal

	/**
	 * This method receives request to get details of all the item collection
	 * list with items: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get all the item collection list 4. Prepare response and send
	 * it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAppraisalListNotAssociatedWithItem.csv")
	public Response getAppraisalListNotAssociatedWithItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method getAppraisalListNotAssociatedWithItem");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "userPublicKey", "itemId" ,"publicKey"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
			String publicKey = JSONUtility.getSafeString(reqObject, "publicKey");
			JSONObject appraisalObject = new JSONObject();
			List<AppraisalBean> appraisalBeanList = new ArrayList<AppraisalBean>();
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
				appraisalBeanList = itemService.getAgentSharedAppraisalsNotInItem(publicKey, itemId, rKey);
			}
			else {
				appraisalBeanList = appraisalService.getAppraisalDetailNotAssociatedWithItem(rKey,
					itemId);
			}
			JSONArray jArray = new JSONArray();

			if (appraisalBeanList != null && appraisalBeanList.size() > 0) {
				for (AppraisalBean appraisalBean : appraisalBeanList) {
					JSONObject aObject = new JSONObject();
					aObject.put("publicKey", appraisalBean.getRelationKey());
					aObject.put("name", appraisalBean.getName());
					aObject.put("shortDescription", appraisalBean.getShortDescription());
					aObject.put("appraisalStatus", appraisalBean.getaStatus());
					aObject.put("appraisalId", appraisalBean.getAppraisalId());

					jArray.put(aObject);
				}
				appraisalObject.put("appraisals", jArray);
				json.put("resData", appraisalObject);
				json.put("sCode", CommonConstants.SUCCESS);
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("AppraisalWS : Enter Method getAppraisalListNotAssociatedWithItem");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get user items list not in appraisal 1.
	 * Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get items list from items. 4. On success send back response for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemsListNotInAppraisal.csv")
	public Response getItemsListNotInAppraisal(JSONObject formpaparam) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method getItemsListNotInAppraisal");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject rqstObject = new JSONObject(reqPaparam);
			JSONObject itemsInfo = rqstObject.getJSONObject("itemsInfo");
			String requiredPaparams[] = { "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, itemsInfo);
			String requiredPaparams1[] = { "publicKey" };
			String missingPaparams1 = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1, rqstObject);
			
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams1)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(itemsInfo, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(rqstObject, "publicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(itemsInfo, "appraisalId");
			AppraisalBean appraisalBean = appraisalService.getAppraisalBeanById(appraisalId);
			if(appraisalBean.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", "");
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.NO_ITEM_CAN_BE_ADDDED_APPRAISAL);
				return Response.status(200).entity(json.toString()).build();
			}
			List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
				itemModelList = itemService.getAgentSharedItemsNotInAppraisal(publicKey, appraisalId, rKey);
			}else{
				itemModelList = appraisalService.getItemsListNotInAppraisal(rKey, appraisalId);
			}
				List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
				JSONArray jArray = new JSONArray();
				JSONArray imageJArray = null;
				if (itemModelList != null && itemModelList.size() > 0) {
					for (ValouxItemModel itemsModel : itemModelList) {
						JSONObject jObject = new JSONObject();
						imageJArray = new JSONArray();
						jObject.put("itemId", itemsModel.getItemId());
						jObject.put("designerPrice", itemsModel.getDesignerPrice());
						jObject.put("designerPriceType", itemsModel.getDesignerPriceType());
						jObject.put("designType", itemsModel.getDesignType());
						jObject.put("gender", itemsModel.getGender());
						jObject.put("itemTypeIt", itemsModel.getItemTypeIt());
						jObject.put("itemTypeName", itemsModel.getItemTypeName());
						jObject.put("name", itemsModel.getName());
						jObject.put("quantity", itemsModel.getQuantity());
						jObject.put("salesPrice", itemsModel.getSalesPrice());
						jObject.put("salesTax", itemsModel.getSalesTax());
						jObject.put("sDescription", itemsModel.getsDescription());
						jObject.put("storeExist", itemsModel.getStoreExist());
						jObject.put("storeId", itemsModel.getStoreId());
						jObject.put("valouxMarketValue", itemsModel.getValouxMarketValue());

						itemImageModelList = itemService.getItemImageModelList(itemsModel.getItemId());

						// Get images details of items
						AppraisalHelper.getImagesOfItemsById(itemImageModelList, imageJArray, jObject);
						/*
						 * Integer countOfItem =
						 * appraisalService.getItemAssociatedWithCollection
						 * (itemsModel.getItemId()); jObject.put("noOfItem",
						 * countOfItem);
						 */

						jArray.put(jObject);

					} // end of for loop

					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("itemData", jArray);
					jsonResponse.put("appraisalId", appraisalId);

					// Get itemid of appraisal
					AppraisalHelper.getItemIdsOfAppraisalById(appraisalService, jsonResponse, appraisalId);

					json.put("resData", jsonResponse);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", new JSONObject());
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.APPRAISAL_ITEMS_NOT_FOUND);
				}
			

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method getItemsListNotInAppraisal");
		return Response.status(200).entity(json).build();
	} // end of the method getCollectionListNotInAppraisal

	
	/**
	 * This method receives request to get details of all the items and
	 * collection: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get all item and collection list. 4. Prepare response and send
	 * it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllItemAndCollectionList.csv")
	public Response getAllItemAndCollectionList(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter getAllItemAndCollectionList method of AppraisalWS");
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
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
			JSONObject jsonResponse = new JSONObject();
			JSONObject jObject = null;
			JSONArray iArray = new JSONArray();
			JSONArray cArray = new JSONArray();
			if(agentModel!=null){
				List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedItemsToUserByConsumer(publicKey,CommonConstants.SHARED_TYPE_ITEM, CommonConstants.STATUS_ACCEPTED,rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
				if(sharedRequestBeanList!=null && sharedRequestBeanList.size()>0){
					for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
						ValouxItemModel itemModel = itemService.getValouxItemDetailsById(sharedRequestBean.getSharedItemId());
						itemModelList.add(itemModel);
					}
				}
			}else{
				itemModelList = itemService.getAllItemList(rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
			}
			if (itemModelList != null && itemModelList.size() > 0) {
				List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
				JSONArray imageJArray = null;
				if (itemModelList != null && itemModelList.size() > 0) {
					for (ValouxItemModel itemModel : itemModelList) {
						imageJArray = new JSONArray();
						jObject = new JSONObject();
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
						iArray.put(jObject);
					}
				} // end of for loop
				jsonResponse.put("itemData", iArray);
			}
			List<ValouxCollectionModel> collectionModels = new ArrayList<ValouxCollectionModel>();
			Boolean isAgent = false;
			if(agentModel!=null){
				isAgent = true;
				List<ValouxSharedRequestBean> sharedRequestBeanList = itemService.getListOfSharedItemsToUserByConsumer(publicKey,CommonConstants.SHARED_TYPE_COLLECTION, CommonConstants.STATUS_ACCEPTED,rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
				if(sharedRequestBeanList!=null && sharedRequestBeanList.size()>0){
					for (ValouxSharedRequestBean sharedRequestBean : sharedRequestBeanList) {
						ValouxCollectionModel valouxCollectionBean = collectionService.getCollectionDetailsById(sharedRequestBean
								.getSharedItemId());
								collectionModels.add(valouxCollectionBean);
							
					}
				}
			}
			else{
			collectionModels =	collectionService.getUserCollectionsList(rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);
			}
			if (collectionModels != null && collectionModels.size() > 0) {
				for (ValouxCollectionModel valouxCollectionModel : collectionModels) {
					JSONObject cObject = new JSONObject();
					cObject.put("vcid", valouxCollectionModel.getVcid());
					cObject.put("cname", valouxCollectionModel.getCname());
					cObject.put("cDescription", valouxCollectionModel.getShortDescription());
					cObject.put("cStatus", valouxCollectionModel.getCollectionStatus());

					// Get Item count details of collection
					AppraisalHelper.getItemsOfCollectionById(collectionService, itemService, cObject, valouxCollectionModel.getVcid(), true,isAgent);

					// Get Item images details of collection
					AppraisalHelper.getItemsImagesOfCollectionById(collectionService, itemService, cObject, valouxCollectionModel.getVcid());

					// Get images details of collection
					AppraisalHelper.getImagesOfCollectionById(collectionService, cObject, valouxCollectionModel.getVcid());

					// Get appraisal details of collection
					AppraisalHelper.getAppraisalsOfCollectionById(appraisalService, cObject, valouxCollectionModel.getVcid(), true,isAgent);

					cArray.put(cObject);
				}
				// JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("collectionData", cArray);
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.APPRAISAL_ITEM_OR_COLLECTION_NOT_FOUND);
			}
			json.put("resData", jsonResponse);
			json.put("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit getAllItemAndCollectionList method of AppraisalWS");
		return Response.status(200).entity(json).build();
	}

	

	/**
	 * This method receives request to delete Item or collection from appraisal:
	 * 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to delete item or collection from appraisal. 4. On success send
	 * back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteCollectionOrItemFromAppraisal.csv")
	public Response deleteCollectionOrItemFromAppraisal(JSONObject formpaparam) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method deleteCollectionOrItemFromAppraisal");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject appraisalInfo = jObject.getJSONObject("appraisalInfo");
			String requiredPaparams[] = { "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, appraisalInfo);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(appraisalInfo, "userPublicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(appraisalInfo, "appraisalId");
			JSONArray collectionId = JSONUtility.getSafeJSONArray(appraisalInfo, "collectionId");
			JSONArray itemId = JSONUtility.getSafeJSONArray(appraisalInfo, "itemId");
			
			AppraisalModel appraisalmodel = appraisalService.getAppraisalDetailsById(appraisalId);
			if(appraisalmodel != null && appraisalmodel.getaStatus() != null){
				if(appraisalmodel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.APPRAISAL_ALREADY_APPRAISED);
					json.putOpt("sCode", CommonConstants.ERROR);
					return Response.status(200).entity(json).build();
				}
			}
			
			/*
			 * String requiredPaparams[] = {"itemId","collectionId"};
			 * 
			 * String missingPaparams =
			 * CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams,
			 * jObject);
			 * if(CommonUserUtility.paparameterNullCheckStringObject(missingPaparams
			 * )){ json.put("resData", ""); json.put("sCode",
			 * CommonConstants.INCOMPLETE); json.put("missingPaparams",
			 * "Paparameters missing - "+ missingPaparams);
			 * json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
			 * return Response.status(200).entity(json.toString()).build(); }
			 */
			if (appraisalId != null && collectionId != null && itemId != null) {
				Boolean isCollectionPresent = appraisalService.deleteCollectionFromAppraisal(collectionId, appraisalId);
				Boolean isItemPresent = appraisalService.deleteItemFromAppraisal(itemId, appraisalId);
				if (isCollectionPresent && isItemPresent) {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_COLLECTION_ITEM_DELETE_IN_APPRAISAL);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				}
			}
			if (appraisalId != null && collectionId != null && itemId == null) {
				Boolean isItemPresent = appraisalService.deleteCollectionFromAppraisal(collectionId, appraisalId);
				if (isItemPresent) {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_COLLECTION_DELETE_IN_APPRAISAL);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_COLLECTION_NOT_FOUND_IN_APPRAISAL);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}
			if (appraisalId != null && itemId != null && collectionId == null) {
				Boolean isItemPresent = appraisalService.deleteItemFromAppraisal(itemId, appraisalId);
				if (isItemPresent) {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_COLLECTION_DELETE_IN_APPRAISAL);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_COLLECTION_NOT_FOUND_IN_APPRAISAL);
					json.putOpt("sCode", CommonConstants.ERROR);
				}
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method deleteCollectionOrItemFromAppraisal");
		return Response.status(200).entity(json).build();
	}

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
	@Path("/addItemsInAppraisal.csv")
	public Response addItemsInAppraisal(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method addItemsInAppraisal");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			AppraisalItemsModel appraisalItemModel = new AppraisalItemsModel();

			String requiredPaparams[] = { "appraisalId", "itemId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);

			if (!CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				
				Integer itemId = JSONUtility.getSafeInteger(jObject, "itemId");
				JSONArray appraisalIdArray = JSONUtility.getSafeJSONArray(jObject, "appraisalId");
				String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
				appraisalItemModel.setAppraisalIdArray(appraisalIdArray);
				appraisalItemModel.setSingleItemId(itemId);
				appraisalItemModel.setRequestType(CommonConstants.ADD);
				appraisalItemModel.setCreatedBy(publicKey);
				appraisalItemModel.setCreatedOn(CommonUtility.getDateAndTime());
				appraisalItemModel.setModifiedBy(publicKey);
				appraisalItemModel.setModifiedOn(CommonUtility.getDateAndTime());
				appraisalService.addItemInAppraisals(appraisalItemModel);
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.ITEM_ADDED_SUCCESSFULL);
			} else {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		}

		LOGGER.info("AppraisalWS : Exit Method addItemsInAppraisal");
		return Response.status(200).entity(json).build();
	} // end of method addItemsInCollection

	/**
	 * This method receives request to delete Item or collection from appraisal:
	 * 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to delete item or collection from appraisal. 4. On success send
	 * back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteItemFromAppraisals.csv")
	public Response deleteItemFromAppraisals(JSONObject formpaparam) throws Exception {
		LOGGER.info("AppraisalWS : Enter Method deleteItemFromAppraisals");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			Integer itemId = JSONUtility.getSafeInteger(jObject, "itemId");
			JSONArray appraisalArray = JSONUtility.getSafeJSONArray(jObject, "appraisalId");
			String requiredPaparams[] = { "itemId", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Boolean isItemPresent = appraisalService.deleteSingleItemFromAppraisals(appraisalArray, itemId);
			if (isItemPresent) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.APPRAISAL_COLLECTION_DELETE_IN_APPRAISAL);
				json.putOpt("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method deleteItemFromAppraisals");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to register an User and performs following
	 * operations: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to check if user already exist. 4. If validations are not failing
	 * call service method to create User. 5. On successful creation of User
	 * send email. 6. Send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/userRegistrationViaAgent.csv")
	public Response userRegistrationViaAgent(JSONObject formPaparam) throws Exception {
		LOGGER.info("Enter Method userRegistrationViaAgent of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject userRegistrationData = new JSONObject();
		LoginBean loginBean = new LoginBean();

		try {
			String reqPaparam = formPaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");
			JSONObject registrationData = collectionInfo.optJSONObject("newConsumerData");
			String requiredPaparams[] = {"firstName","lastName","emailId","publicKey"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, registrationData);
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
				json.put("resData", userRegistrationData);
				json.put("errorMessage", CommonConstants.EMAIL_ID_ALREADY_EXIST);
				json.put("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			loginBean = AppraisalHelper.userLoginBeanCreationViaAgent(userService, registrationData, loginBean);
			if(loginBean != null){
				String encryptedKey = (loginBean.getPrivateKey());
				AppraisalHelper.userLoginTypeAndRoleCreationViaAgent(userService, agentService, jObject, loginBean, encryptedKey);
				AppraisalHelper.userValouxAccessPermissionCreation(appraisalService, registrationData, encryptedKey);
			}

			json.put("resData", userRegistrationData);
			json.put("successMessage", CommonConstants.REGISTRATION_COMPLETE_MESSAGE);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.SUCCESS);
			e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		
		LOGGER.info("Exit Method userRegistrationViaAgent of AppraisalWS");
		return Response.status(200).entity(json).build();
	}
	/**
	 * This method receives request to get collections and Items of appraisal: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method get collections and Items of appraisal. 4. On
	 * success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAppraisalDetails.csv")
	public Response getAppraisalDetails(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method getItemsOfAppraisal");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject resData = new JSONObject();
			String requiredPaparams[] = { "userPublicKey", "appraisalId","publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			String rKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
			Boolean isAgent = false;
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
				isAgent = true;
			}
			// Get Collections and Items list details of appraisal
			AppraisalHelper.getAppraisalCollectionsItemsDetails(appraisalService, itemService, collectionService, resData, appraisalId,isAgent);
			
			AppraisalHelper.getApprovedAgentDetails(appraisalService, agentService, userService, storeService, appraisalId, resData);

			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method getItemsOfAppraisal");
		return Response.status(200).entity(json).build();
	}// end of the method getItemsOfAppraisal
	
	/**
	 * This method receives request to get agent access consumer details: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method get collections and Items of appraisal. 4. On
	 * success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAgentAccessUsersList.csv")
	public Response getAgentAccessUsersList(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method getAgentAccessUsers");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject resData = new JSONObject();
			String requiredPaparams[] = { "publicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			String agentKey = JSONUtility.getSafeString(jObject, "publicKey");
			
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(agentKey);
			if(agentModel != null){
				AppraisalHelper.getAgentAccessPermissionUsers(userService, appraisalService, itemService, collectionService, agentModel, jObject, resData);
			}
			
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method getAgentAccessUsers");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to change appraisal status to appraised: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method get collections and Items of appraisal. 4. On
	 * success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/changeStatusToAppraised.csv")
	public Response changeStatusToAppraised(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method changeStatusToAppraised");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject resData = new JSONObject();
			String requiredPaparams[] = { "publicKey", "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			String agentKey = JSONUtility.getSafeString(jObject, "publicKey");
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
			
			AppraisalModel appraisalModel = appraisalService.getAppraisalDetailsById(appraisalId);
			if(appraisalModel != null && !(appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED))) {
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(agentKey);
				if(agentModel != null){
					LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
					if(loginModel != null){
						
//						boolean flag = appraisalService.checkAppraisedItemFinalValue(appraisalId);
//						if(flag){
							AppraisalHelper.changeStatusToAppraised(appraisalService, jObject);
							AppraisalHelper.sendAppraisedMailToConsumer(userService, loginModel, agentModel);
							
							Boolean isAgent = true;
							
							// Get Collections and Items list details of appraisal
							AppraisalHelper.getAppraisalCollectionsItemsDetails(appraisalService, itemService, collectionService, resData, appraisalId, isAgent);
							
							AppraisalHelper.getApprovedAgentDetails(appraisalService, agentService, userService, storeService, appraisalId, resData);
							
							JSONObject resObject = new JSONObject();
							resObject.put("resData", resData);
							
							AppraisalHelper.addAppraisalPdfFile(appraisalService, resObject, appraisalId);
							
							json.put("resData", new JSONObject());
							json.put("sCode", CommonConstants.SUCCESS);
							json.put("successMessage", CommonConstants.APPRAISAL_APPRAISED_COMPLETE_MESSAGE);
//						} else {
//							json.put("resData", resData);
//							json.put("sCode", CommonConstants.INCOMPLETE);
//							json.put("successMessage", CommonConstants.APPRAISAL_APPRAISED_INCOMPLETE_MESSAGE);
//						}
					}
				}
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.ERROR);
				json.put("successMessage", CommonConstants.APPRAISAL_ALREADY_APPROVED);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method changeStatusToAppraised");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to change appraisal status to unappraised: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method get collections and Items of appraisal. 4. On
	 * success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/changeStatusToUnAppraised.csv")
	public Response changeStatusToUnAppraised(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method changeStatusToUnAppraised");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject resData = new JSONObject();
			String requiredPaparams[] = { "publicKey", "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			String userKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String agentKey = JSONUtility.getSafeString(jObject, "publicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
			AppraisalModel appraisalModel = appraisalService.getAppraisalDetailsById(appraisalId);
			if(appraisalModel != null && appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)) {
				AppraisalHelper.changeStatusToUnAppraised(appraisalService, jObject);
				
				AppraisalHelper.sendUnAppraisedMailToConsumer(userService, userKey, agentKey);
				
				json.put("resData", resData);
				json.put("sCode", CommonConstants.SUCCESS);
				json.put("successMessage", CommonConstants.APPRAISAL_UNAPPRAISED_COMPLETE_MESSAGE);
			} else {
				json.put("resData", resData);
				json.put("sCode", CommonConstants.ERROR);
				json.put("errorMessage", CommonConstants.APPRAISAL_APPRAISED_ERROR_MESSAGE);
			}

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method changeStatusToUnAppraised");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to change appraisal status to unappraised: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method get collections and Items of appraisal. 4. On
	 * success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/downloadAppraisalPdfFile.csv")
	public Response downloadAppraisalPdfFile(JSONObject formpaparam) throws Exception {

		LOGGER.info("AppraisalWS : Enter Method downloadAppraisalPdfFile");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject resData = new JSONObject();
			String requiredPaparams[] = { "publicKey", "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			
			String userPublicKey = JSONUtility.getSafeString(jObject, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
			
			String valouxPropertyFileName = "valoux.properties";
			Properties prop = new Properties();
			prop = CommonUtility.getProperty(valouxPropertyFileName);
		
			final String pdfLocation = prop.getProperty("valoux.pdf.location");
			
			LoginModel loginModel = userService.getLoginDetailByPKey((userPublicKey));
			JSONObject resObject = new JSONObject();
			if(loginModel != null){
				AppraisalModel appraisalModel = appraisalService.getAppraisalDetailsById(appraisalId);
				
				if(appraisalModel != null && appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)) {
					if( CommonUserUtility.paparameterNullCheckStringObject(appraisalModel.getPdfFile())){
						resObject.put("pdfFile", pdfLocation + appraisalModel.getPdfFile());
						resObject.put("pdfName", appraisalModel.getPdfFile());
					} else {
						Boolean isAgent = false;
						AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
						if(agentModel != null){
							isAgent = true;
						}
						AppraisalHelper.getAppraisalCollectionsItemsDetails(appraisalService, itemService, collectionService, resData, appraisalId, isAgent);
						
						AppraisalHelper.getApprovedAgentDetails(appraisalService, agentService, userService, storeService, appraisalId, resData);
						
						JSONObject dataObject = new JSONObject();
						dataObject.put("resData", resData);
						AppraisalHelper.addAppraisalPdfFile(appraisalService, dataObject, appraisalId);
						appraisalModel = appraisalService.getAppraisalDetailsById(appraisalId);
						if(appraisalModel != null && CommonUserUtility.paparameterNullCheckStringObject(appraisalModel.getPdfFile())){
							resObject.put("pdfFile", pdfLocation + appraisalModel.getPdfFile());
							resObject.put("pdfName", appraisalModel.getPdfFile());
						} 
					}
					json.put("resData", resObject);
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.APPRAISAL_APPRAISED_PDF_MESSAGE);
				} else {
					json.put("resData", new JSONObject());
					json.put("sCode", CommonConstants.ERROR);
					json.put("successMessage", CommonConstants.APPRAISAL_INVALID_MESSAGE);
				}
			} else {
				json.put("resData", new JSONObject());
				json.put("sCode", CommonConstants.ERROR);
				json.put("successMessage", CommonConstants.INVALID_REQUEST);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			 e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("AppraisalWS : Exit Method downloadAppraisalPdfFile");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to get all appraisals list: 1. Check request for essential paparameters. 2. Converts
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
	@Path("/getAllAppraisalsList.csv")
	public Response getAllAppraisalsList() throws Exception {
		LOGGER.info("Enter getAllAppraisalsList method of AppraisalWS");
		JSONObject json = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			List<AppraisalModel> appraisalModels = new ArrayList<AppraisalModel>();
			appraisalModels = appraisalService.getAllAppraisalsList();
			if(appraisalModels != null && appraisalModels.size() > 0){
				
				ItemHelper.getAllAppraisalsList(appraisalModels, resData);
				
			} else {
				json.put("successMessage", CommonConstants.APPRAISALS_NOT_FOUND);
			}
			
			json.put("resData", resData);
			json.put("sCode", CommonConstants.SUCCESS);
			
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("Exit getAllAppraisalsList method of AppraisalWS");
		return Response.status(200).entity(json).build();
	} 
	
	/**
	 * This method receives request to delete details of the appraisal: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to delete appraisal.
	 * 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteAppraisalByAppraisalId.csv")
	public Response deleteAppraisalByAppraisalId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteAppraisalByAppraisalId method of AppraisalWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey", "userPublicKey", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer appraisalId = JSONUtility.getSafeInteger(reqObject, "appraisalId");
			String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			AppraisalModel appraisalModel = appraisalService.getAppraisalDetailsById(appraisalId);
			
			if(appraisalModel == null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.APPRAISAL_NOT_FOUND);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			if(appraisalModel != null && CommonUserUtility.paparameterNullCheckObject(appraisalModel.getaStatus()) && appraisalModel.getaStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.APPRAISAL_ALREADY_APPRAISED_ONDELETE);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			Boolean flag = appraisalService.deleteAppraisalAndAllDetails(appraisalId, userPublicKey);
			if (flag) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.APPRAISAL_DELETED_SUCCESSFULLY);
				json.putOpt("sCode", CommonConstants.SUCCESS);
				return Response.status(200).entity(json.toString()).build();
			}
			json.put("resData", "");
			json.putOpt("errorMessage", CommonConstants.ERROR_MESSAGE);
			json.putOpt("sCode", CommonConstants.ERROR);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("Exit deleteAppraisalByAppraisalId method of AppraisalWS");
		return Response.status(200).entity(json).build();
	}
	
}// end of class
