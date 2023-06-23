/*
// * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.webservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.valoux.bean.LoginBean;
import com.valoux.constant.CommonConstants;
import com.valoux.helper.AppraisalHelper;
import com.valoux.helper.CollectionHelper;
import com.valoux.model.AgentModel;
import com.valoux.model.AppraisalCollectionModel;
import com.valoux.model.AppraisalModel;
import com.valoux.model.ItemImageModel;
import com.valoux.model.ValouxCollectionImageModel;
import com.valoux.model.ValouxCollectionItemModel;
import com.valoux.model.ValouxCollectionModel;
import com.valoux.model.ValouxItemModel;
import com.valoux.model.ValouxSharedRequestModel;
import com.valoux.service.AgentService;
import com.valoux.service.AppraisalService;
import com.valoux.service.CollectionService;
import com.valoux.service.ItemService;
import com.valoux.service.UserService;
import com.valoux.util.CommonUserUtility;
import com.valoux.util.CommonUtility;
import com.valoux.util.JSONUtility;

/**
 * This <java>class</java> serves request related to different operations
 * manipulating collections
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */

@Path("collection")
@Component
public class CollectionWS {

	@Autowired
	CollectionService collectionService;

	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	@Autowired
	AppraisalService appraisalService;

	@Autowired
	AgentService agentService;

	private static final Logger LOGGER = Logger.getLogger(CollectionWS.class);

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
	@Path("/addItemsInCollection.csv")
	public Response addItemsInCollection(JSONObject formpaparam) throws Exception {

		LOGGER.info("CollectionWS : Enter Method addItemsInCollection");
		JSONObject json = new JSONObject();
		JSONObject collectionJson = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");
			ValouxCollectionItemModel collectionItemModel = new ValouxCollectionItemModel();

			String requiredPaparams[] = { "cId", "cName", "cDescription" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, collectionInfo);
			String requestType = "";

			if (!CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				Integer collectionId = JSONUtility.getSafeInteger(collectionInfo, "cId");
				if (collectionId != null) {
					requestType = CommonConstants.UPDATE;
					
					ValouxCollectionModel collectionBean = collectionService.getCollectionDetailsById(collectionId);

					if (collectionBean != null && collectionBean.getCollectionStatus() != null) {
						if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED);
							json.putOpt("sCode", CommonConstants.ERROR);
							return Response.status(200).entity(json).build();
						}

						CollectionHelper
								.populateValouxCollectionItemModel(collectionItemModel, collectionInfo, requestType);

						collectionService.addItemsInCollection(collectionItemModel);

						collectionJson.put("cId", collectionBean.getVcid());
						collectionJson.put("cName", collectionBean.getCname());
						collectionJson.put("cDescription", collectionBean.getShortDescription());
						json.put("resData", collectionJson);
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", CommonConstants.ITEM_ADDED_SUCCESSFULL);
					}
				} else {
					json.put("resData", collectionJson);
					json.put("sCode", CommonConstants.INVALID_REQUEST);
					json.put("errorMessage", CommonConstants.INFO_ERROR_MESSAGE);
				} 
			} else {
				json.put("resData", collectionJson);
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

		LOGGER.info("CollectionWS : Exit Method addItemsInCollection");
		return Response.status(200).entity(json).build();
	} // end of method addItemsInCollection

	/**
	 * This method receives request to get details of all the user collection
	 * list with items: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get all the user collection list with items. 4. Prepare
	 * response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserCollectionsList.csv")
	public Response getUserCollectionsList(JSONObject formpaparam) throws Exception {

		LOGGER.info("CollectionWS : Enter Method getUserCollectionsList");
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
			Boolean isAgent = false;
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
				isAgent=true;
			}
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
			JSONObject collectionsObject = new JSONObject();

			List<ValouxCollectionModel> collectionModels = collectionService.getUserCollectionsList(rKey, startRecordNo, numberOfRecords, sortBy, sortOrder);

			JSONArray jArray = new JSONArray();

			if (collectionModels != null && collectionModels.size() > 0) {
				for (ValouxCollectionModel valouxCollectionModel : collectionModels) {
					JSONObject cObject = new JSONObject();
					cObject.put("cId", valouxCollectionModel.getVcid());
					cObject.put("cName", valouxCollectionModel.getCname());
					cObject.put("cDescription", valouxCollectionModel.getShortDescription());
					cObject.put("cStatus", valouxCollectionModel.getCollectionStatus());
					cObject.put("consumerPublicKey", valouxCollectionModel.getRkey());
					Integer cStatus = valouxCollectionModel.getCollectionStatus();
					Boolean priceFlag=false;
					if(cStatus==CommonConstants.APPRAISAL_STATUS_APPROVED){
						priceFlag=true;
						cObject.put("lastAppraisedDate", valouxCollectionModel.getLastAppraisedDate());
						cObject.put("marketValueDate", CommonUtility.getDateAndTime());
						cObject.put("lastAppraisedId", valouxCollectionModel.getLastAppraiserId());
						cObject.put("marketValue", 0.00);
						cObject.put("appraisedValue", 0.00);
						cObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
						cObject.put("changePercentage",0);
						
					}else{
						cObject.put("lastAppraisedDate", "");
						cObject.put("marketValueDate", "");
						cObject.put("lastAppraisedId","");
						cObject.put("marketValue", 0.00);
						cObject.put("appraisedValue", 0.00);
						cObject.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
						cObject.put("changePercentage",0);
					}
					// Get Item count details of collection
					CollectionHelper.getItemsOfCollectionById(collectionService, itemService, cObject, valouxCollectionModel.getVcid(), true, reqObject,priceFlag,isAgent);

					// Get Item images details of collection
					CollectionHelper.getItemsImagesOfCollectionById(collectionService, itemService, cObject, valouxCollectionModel.getVcid());

					// Get images details of collection
					CollectionHelper.getImagesOfCollectionById(collectionService, cObject, valouxCollectionModel.getVcid());

					// Get appraisal details of collection
					CollectionHelper.getAppraisalsOfCollectionById(appraisalService, collectionService, itemService, cObject, valouxCollectionModel.getVcid(), true);
					Map<Integer, Integer> countMap = itemService.getNumberOfAgentAndConsumerToWhichItemIsShared(valouxCollectionModel.getVcid(),2);
					if (countMap != null) {
						if (countMap.get(1) != 0) {
							cObject.put("sharedWithNumberOfConsumer", countMap.get(1));
						}
						if (countMap.get(2) != 0) {
							cObject.put("sharedWithNumberOfAgent", countMap.get(2));
						}
						if(countMap.get(3)!=0){
							cObject.put("sharedWithNumberOfUnregisteredUser", countMap.get(3));
						}
					}
					jArray.put(cObject);
				}
				collectionsObject.put("collections", jArray);
				json.put("resData", collectionsObject);
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
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("CollectionWS : Enter Method getUserCollectionsList");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to add update collection details: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to add new items. 4.
	 * On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addUpdateCollectionDetails.csv")
	public Response addUpdateCollectionDetails(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method addUpdateCollectionDetails");
		JSONObject json = new JSONObject();

		ValouxCollectionModel collectionModel = new ValouxCollectionModel();
		List<ValouxCollectionImageModel> collectionImageModel = new ArrayList<ValouxCollectionImageModel>();
		ValouxCollectionItemModel collectionItemModel = new ValouxCollectionItemModel();
		AppraisalCollectionModel appraisalCollectionModel = new AppraisalCollectionModel();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");
			String requiredPaparams[] = { "requestType" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, collectionInfo);
			String requiredPaparams1[] = { "publicKey" };
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
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams1);
				return Response.status(200).entity(json.toString()).build();
			}
			String requestType = JSONUtility.getSafeString(collectionInfo, "requestType");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			CollectionHelper.populateValouxCollectionModel(collectionModel, jObject, requestType);
			Boolean isAgent = false;
			Integer collectionId = 0;
			String collectionName = "", collectionDescription = "", successMessage = CommonConstants.COLLECTION_ADDED_SUCCESSFULL;
			String createdBy = "", consumerPublicKey = "";

			ValouxCollectionModel collectionBean = null;
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
				isAgent = true;
			}
			if (requestType != null) {
				if (requestType.equalsIgnoreCase(CommonConstants.ADD)) {
					
					collectionId = JSONUtility.getSafeInteger(collectionInfo, "cId");
					
					if(CommonUserUtility.paparameterNullCheckObject(collectionId) && !collectionId.equals(0)) {
						collectionBean = collectionService.getCollectionDetailsById(collectionId);
						if(collectionBean.getCollectionStatus()!= null && collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
							json.put("sCode", CommonConstants.ERROR);
							json.put("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED);
							return Response.status(200).entity(json.toString()).build();
						} 
					}
					
					///////////Start/////////Adding new consumer data////////////////////////////
					LoginBean loginBean = new LoginBean();
					
					String userPublicKey = JSONUtility.getSafeString(collectionInfo, "userPublicKey");
					String encryptedKey = null;
					if(!CommonUserUtility.paparameterNullCheckStringObject(userPublicKey)){
						JSONObject registrationData = collectionInfo.optJSONObject("newConsumerData");
						
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
								userPublicKey = encryptedKey;
								
								AppraisalHelper.userLoginTypeAndRoleCreationViaAgent(userService, agentService, jObject, loginBean, encryptedKey);
								AppraisalHelper.userValouxAccessPermissionCreation(appraisalService, jObject, encryptedKey);
								itemService.updateSharedRequestForNewRegistration(loginBean.getUserName(), encryptedKey,
										CommonConstants.CONSUMER);
							}
							
							collectionModel.setRkey(encryptedKey);
						} else {
							json.put("resData", "");
							json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
							json.putOpt("sCode", CommonConstants.INCOMPLETE);
							json.put("missingPaparams", "Paparameters missing - userPublicKey");
							return Response.status(200).entity(json.toString()).build();
						}
					}
					///////////End/////////Adding new consumer data////////////////////////////
					

					if (collectionModel.getCname() != null && !collectionModel.getCname().isEmpty()) {

						collectionBean = collectionService.addUpdateCollectionDetails(collectionModel, requestType);
						if(collectionBean!=null){
							
						if(collectionBean.getVcid()!=null){
							
							if(agentModel!=null){
								ValouxSharedRequestModel sharedRequestModel = new ValouxSharedRequestModel();
								sharedRequestModel.setApproveStatus(2);
								sharedRequestModel.setCreatedBy(publicKey);
								sharedRequestModel.setCreatedOn(CommonUtility.getDateAndTime());
								sharedRequestModel.setModifiedBy(publicKey);
								sharedRequestModel.setModifiedOn(CommonUtility.getDateAndTime());
								sharedRequestModel.setSharedItemId(collectionBean.getVcid());
								sharedRequestModel.setSharedItemType(2);
								sharedRequestModel.setStatus(1);
								sharedRequestModel.setSharedBy(collectionBean.getRkey());
								String[] sharedToArray = new String[1];
								sharedToArray[0]=publicKey;
								itemService.createSharedRequestAndSendEmail(sharedRequestModel, sharedToArray, null);
								itemService.sendMailToConsumerForItemCollectionAppraisalIdAdded(2, collectionBean.getRkey());
							}
						}
					}
						// Fetching image for updating in collection
						JSONArray imageObject = JSONUtility.getSafeJSONArray(collectionInfo, "cImages");
						if (imageObject != null && collectionBean != null) {
							CollectionHelper.populateValouxCollectionImageModel(collectionImageModel, jObject,
									collectionBean.getVcid(), requestType);

							collectionService.addCollectionImageDetails(collectionImageModel);
						}
						
					} else {
						json.put("resData", "");
						json.putOpt("errorMessage", CommonConstants.COLLECTION_ERROR_MESSAGE);
						json.putOpt("sCode", CommonConstants.INCOMPLETE);
						return Response.status(200).entity(json.toString()).build();
					}

					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", successMessage);
				} else if (requestType.equalsIgnoreCase(CommonConstants.UPDATE)) {

					collectionId = JSONUtility.getSafeInteger(collectionInfo, "cId");

					if (collectionId == null || collectionId == 0) {
						json.put("resData", "");
						json.putOpt("errorMessage", CommonConstants.COLLECTION_ERROR_MESSAGE);
						json.putOpt("sCode", CommonConstants.ERROR);
						return Response.status(200).entity(json.toString()).build();
					}
					
					collectionBean = collectionService.getCollectionDetailsById(collectionId);
					if(collectionBean.getCollectionStatus()!= null && collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
						json.put("sCode", CommonConstants.ERROR);
						json.put("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED);
						
						// Fetching items for updating in collection
						JSONArray appraisalArray = JSONUtility.getSafeJSONArray(collectionInfo, "appraisals");

						if (appraisalArray != null) {

							CollectionHelper.populateValouxAppraisalCollectionModel(appraisalCollectionModel, jObject,
									requestType);

							collectionService.addAppraisalsInCollection(appraisalCollectionModel);
						}
						
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", CommonConstants.COLLECTION_UPDATED_SUCCESSFULL);
						
					} else {
						if (collectionModel.getCname() != null && !collectionModel.getCname().isEmpty()) {
							collectionBean = collectionService.addUpdateCollectionDetails(collectionModel, requestType);
						} 
						
						// Fetching items for updating in collection
						JSONArray jsonArray = JSONUtility.getSafeJSONArray(collectionInfo, "items");

						if (jsonArray != null) {

							CollectionHelper.populateValouxCollectionItemModel(collectionItemModel, jObject, requestType);

							collectionService.addItemsInCollection(collectionItemModel);
						}

						// Fetching image for updating in collection
						JSONArray imageObject = JSONUtility.getSafeJSONArray(collectionInfo, "cImages");

						if (imageObject != null && collectionBean != null) {
							CollectionHelper.populateValouxCollectionImageModel(collectionImageModel, jObject,
									collectionBean.getVcid(), requestType);

							collectionService.addCollectionImageDetails(collectionImageModel);
						}

						// Fetching items for updating in collection
						JSONArray appraisalArray = JSONUtility.getSafeJSONArray(collectionInfo, "appraisals");

						if (appraisalArray != null) {

							CollectionHelper.populateValouxAppraisalCollectionModel(appraisalCollectionModel, jObject,
									requestType);

							collectionService.addAppraisalsInCollection(appraisalCollectionModel);
						}

						successMessage = CommonConstants.COLLECTION_UPDATED_SUCCESSFULL;
						
						json.put("sCode", CommonConstants.SUCCESS);
						json.put("successMessage", successMessage);
					}

//					if (collectionModel.getCname() != null && !collectionModel.getCname().isEmpty()) {
//						collectionBean = collectionService.addUpdateCollectionDetails(collectionModel, requestType);
//					} else {
//						collectionBean = collectionService.getCollectionDetailsById(collectionId);
//					}
					
				} else if (requestType.equalsIgnoreCase(CommonConstants.VIEW)) {
			
					collectionId = JSONUtility.getSafeInteger(collectionInfo, "cId");
					if(collectionId != null){
						collectionBean = collectionService.getCollectionDetailsById(collectionId);
					}
					
					json.put("sCode", CommonConstants.SUCCESS);
				} 
			} else {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.COLLECTION_ERROR_MESSAGE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			JSONObject collectionJson = new JSONObject();
			Boolean priceFlag=false;
			
			if (collectionBean != null) {
				collectionId = collectionBean.getVcid();
				collectionName = collectionBean.getCname();
				collectionDescription = collectionBean.getShortDescription();
				createdBy = collectionBean.getCreatedBy();
				consumerPublicKey = collectionBean.getRkey();
				collectionJson.put("cStatus", collectionBean.getCollectionStatus());
				if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					priceFlag=true;
					collectionJson.put("lastAppraisedDate", collectionBean.getLastAppraisedDate());
					collectionJson.put("marketValueDate", CommonUtility.getDateAndTime());
					collectionJson.put("lastAppraisedId", collectionBean.getLastAppraiserId());
					collectionJson.put("marketValue", 0.00);
					collectionJson.put("appraisedValue", 0.00);
					collectionJson.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
					collectionJson.put("changePercentage",0);
					collectionJson.put("finalPrice", 0.00);
				}else{
					collectionJson.put("lastAppraisedDate", "");
					collectionJson.put("marketValueDate", "");
					collectionJson.put("lastAppraisedId","");
					collectionJson.put("marketValue", 0.00);
					collectionJson.put("appraisedValue", 0.00);
					collectionJson.put("priceChange", CommonConstants.PERCENTAGE_NO_CHANGE);
					collectionJson.put("changePercentage",0);
					collectionJson.put("finalPrice", 0.00);
				}
			}

			collectionJson.put("cId", collectionId);
			collectionJson.put("cName", collectionName);
			collectionJson.put("cDescription", collectionDescription);
			collectionJson.put("createdBy", createdBy);
			collectionJson.put("consumerPublicKey", consumerPublicKey);
			
			// Get Item list details of collection
			CollectionHelper.getItemsOfCollectionById(collectionService, itemService, collectionJson, collectionId, false, jObject,priceFlag,isAgent);

			// Get images details of collection
			CollectionHelper.getImagesOfCollectionById(collectionService, collectionJson, collectionId);

			json.put("resData", collectionJson);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("CollectionWS : Exit Method addUpdateCollectionDetails");
		return Response.status(200).entity(json).build();
	} // end of method addItem

	/**
	 * This method receives request to add Item in collection: 1. Check request
	 * for essential paparameters. 2. Converts <code>JSONObject</code> request to
	 * business objects. 3. Calls service method to add item in collection. 4.
	 * On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addItemToCollection.csv")
	public Response addItemToCollection(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method addItemToCollection");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			Integer itemId = JSONUtility.getSafeInteger(jObject, "itemId");
			// Integer collectionId = JSONUtility.getSafeInteger(jObject,
			// "collectionId");
			JSONArray collectionId = JSONUtility.getSafeJSONArray(jObject, "collectionId");
			String requiredPaparams[] = { "itemId", "collectionId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Boolean isAdded = collectionService.checkItemAlreadyAddedInCollection(itemId, collectionId);
			if (isAdded) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_ALREADY_ADDEDD_IN_COLLECTION);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			collectionService.addItemInCollection(itemId, collectionId);
			json.put("resData", "");
			json.putOpt("successMessage", CommonConstants.ITEM_ADDEDD_IN_COLLECTION);
			json.putOpt("sCode", CommonConstants.SUCCESS);
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("CollectionWS : Exit Method addItemToCollection");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to delete Item from collection: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to add item in
	 * collection. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteItemFromCollection.csv")
	public Response deleteItemFromCollection(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method deleteItemOfCollection");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = jObject.getJSONObject("collectionInfo");
			String requiredPaparams[] = {"userPublicKey","itemId", "collectionId"};
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, collectionInfo);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				Integer itemId = JSONUtility.getSafeInteger(collectionInfo, "itemId");
				Integer collectionId = JSONUtility.getSafeInteger(collectionInfo, "collectionId");
				
				ValouxCollectionModel collectionBean = collectionService.getCollectionDetailsById(collectionId);

				if (collectionBean != null && collectionBean.getCollectionStatus() != null) {
					if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
						json.put("resData", "");
						json.putOpt("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED);
						json.putOpt("sCode", CommonConstants.ERROR);
						return Response.status(200).entity(json).build();
					}
				}
				
				Boolean isItemPresent = collectionService.deleteItemFromCollection(itemId, collectionId);
				if (isItemPresent) {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.ITEM_DELETE_IN_COLLECTION);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.ITEM_NOT_FOUND_IN_COLLECTION);
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
		LOGGER.info("CollectionWS : Exit Method deleteItemOfCollection");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get user item not in collection 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to add item in
	 * collection. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserItemsNotInCollection.csv")
	public Response getConsumerItemsNotInCollection(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method getUserItemsNotInCollection");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject rqstObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = rqstObject.getJSONObject("collectionInfo");
			String rKey = JSONUtility.getSafeString(collectionInfo, "userPublicKey");
			Integer collectionId = JSONUtility.getSafeInteger(collectionInfo, "collectionId");
			String publicKey = JSONUtility.getSafeString(rqstObject, "publicKey");
			String requiredPaparams[] = { "collectionId", "userPublicKey" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, collectionInfo);
			String requiredPaparams1[] = { "publicKey" };

			String missingPaparams1 = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams1, rqstObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			} else if(CommonUserUtility.paparameterNullCheckStringObject(missingPaparams1)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams1);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			} else {
				ValouxCollectionModel collectionModel = collectionService.getCollectionDetailsById(collectionId);
				if(collectionModel.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					json.put("resData", "");
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.NO_ITEM_CAN_BE_ADDDED_COLLECTION);
					return Response.status(200).entity(json.toString()).build();
				}
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				List<ValouxItemModel> itemModelList = new ArrayList<ValouxItemModel>();
				if (agentModel != null) {
					itemModelList = itemService.getAgentSharedItemsNotInCollection(publicKey, collectionId, rKey);
				} else {
					itemModelList = itemService.getConsumerItemsNotInCollection(rKey, collectionId);
				}
				List<ItemImageModel> itemImageModelList = new ArrayList<ItemImageModel>();
				JSONArray jArray = new JSONArray();
				JSONArray imageJArray = null;
				if (itemModelList != null && itemModelList.size() > 0) {
					for (ValouxItemModel itemModel : itemModelList) {
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
					} // end of for loop
					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("itemData", jArray);

					// Get itemid of collection
					CollectionHelper.getItemsIdsOfCollectionById(collectionService, jsonResponse, collectionId);

					jsonResponse.put("collectionId", collectionId);
					json.put("resData", jsonResponse);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", new JSONObject());
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.ITEMS_NOT_FOUND);
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
	}

	/**
	 * This method receives request to delete image of collection. 1. Check
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
	@Path("/deleteImageByCollectionAndImageId.csv")
	public Response deleteImageByCollectionAndImageId(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method deleteImageByCollectionAndImageId");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			JSONObject collectionInfo = reqObject.getJSONObject("collectionInfo");
			JSONObject deleteInfo = collectionInfo.getJSONObject("deleteImage");
			Integer collectionId = JSONUtility.getSafeInteger(deleteInfo, "cId");
			Integer imageId = JSONUtility.getSafeInteger(deleteInfo, "imageId");
			String requiredPaparams[] = { "cId", "imageId" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, deleteInfo);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			
			ValouxCollectionModel collectionBean = collectionService.getCollectionDetailsById(collectionId);

			if (collectionBean != null && collectionBean.getCollectionStatus() != null) {
				if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
					json.put("resData", "");
					json.putOpt("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED);
					json.putOpt("sCode", CommonConstants.ERROR);
					return Response.status(200).entity(json).build();
				}
			}
			
			Boolean flag = collectionService.deleteImageDocumentByCollectionAndImageId(collectionId, imageId);
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
		LOGGER.info("CollectionWS : Exit Method deleteImageByCollectionAndImageId");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to check collection name already exists for
	 * user: 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to get collection. 4. Prepare response and send it for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/checkCollectionNameExistForUser.csv")
	public Response checkCollectionNameExistForUser(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method checkCollectionNameExistForUser");
		JSONObject json = new JSONObject();
		JSONObject resJson = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String publicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			String collectionName = JSONUtility.getSafeString(reqObject, "cName");
			Integer collectionId = JSONUtility.getSafeInteger(reqObject, "cId");
			String requiredPaparams[] = { "cName" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			}
			Boolean nameExist = collectionService.checkCollectionNameExistForUser(publicKey, collectionName,
					collectionId);

			if (!nameExist) {
				resJson.put("isCollectionNameExist", false);
				json.put("resData", resJson);
				json.putOpt("successMessage", CommonConstants.COLLECTION_NAME_NOT_EXIST);
				json.putOpt("sCode", CommonConstants.SUCCESS);
			} else {
				resJson.put("isCollectionNameExist", true);
				json.put("resData", resJson);
				json.putOpt("successMessage", CommonConstants.COLLECTION_NAME_EXIST);
				json.putOpt("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("CollectionWS : Exit Method checkCollectionNameExistForUser");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get Item: 1. Check request for essential
	 * paparameters. 2. Converts <code>JSONObject</code> request to business
	 * objects. 3. Calls service method get collection items. 4. On success send
	 * back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getItemsOfCollection.csv")
	public Response getItemsOfCollection(JSONObject formpaparam) throws Exception {

		LOGGER.info("CollectionWS : Enter Method getItemsOfCollection");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject collectionJson = new JSONObject();
			String requiredPaparams[] = { "cId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer collectionId = JSONUtility.getSafeInteger(jObject, "cId");
			String publicKey = JSONUtility.getSafeString(jObject, "publicKey");
			Boolean isAgent=false;
			if(publicKey!=null){
			AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
			if(agentModel!=null){
				isAgent=true;
			}
			}
			/*
			 * if (collectionId == null) { json.put("resData", "");
			 * json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
			 * json.putOpt("sCode", CommonConstants.INCOMPLETE); return
			 * Response.status(200).entity(json.toString()).build(); }
			 */

			// Get Item list details of collection
			CollectionHelper.getItemsOfCollectionById(collectionService, itemService, collectionJson, collectionId, false, jObject,false,isAgent);

			json.put("resData", collectionJson);
			json.put("sCode", CommonConstants.SUCCESS);

		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("CollectionWS : Exit Method getItemsOfCollection");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get Item: 1. Check request for essential
	 * paparameters. 2. Converts <code>JSONObject</code> request to business
	 * objects. 3. Calls service method get collection items. 4. On success send
	 * back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAppraisalsOfCollection.csv")
	public Response getAppraisalsOfCollection(JSONObject formpaparam) throws Exception {

		LOGGER.info("CollectionWS : Enter Method getAppraisalsOfCollection");
		JSONObject json = new JSONObject();

		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);
			JSONObject collectionJson = new JSONObject();

			String requiredPaparams[] = { "userPublicKey", "cId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				Integer collectionId = JSONUtility.getSafeInteger(jObject, "cId");

				// Get appraisal list details of collection
				CollectionHelper.getAppraisalsOfCollectionById(appraisalService, collectionService, itemService, collectionJson, collectionId, false);

				json.put("resData", collectionJson);
				json.put("sCode", CommonConstants.SUCCESS);
			}
		} catch (Exception e) {
			json.put("errorMessage", e);
			json.put("sCode", CommonConstants.EXCEPTION);
			// e.printStackTrace();
			LOGGER.error("Error - ", e);
			return Response.status(200).entity(json).build();
		}
		LOGGER.info("CollectionWS : Exit Method getAppraisalsOfCollection");
		return Response.status(200).entity(json).build();
	}

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
	@Path("/getCollectionsListAssociatedWithItem.csv")
	public Response getCollectionsListAssociatedWithItem(JSONObject formpaparam) throws Exception {

		LOGGER.info("CollectionWS : Enter Method getCollectionsListAssociatedWithItem");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "itemId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.ITEM_INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer itemId = JSONUtility.getSafeInteger(reqObject, "itemId");
			// String rKey = JSONUtility.getSafeString(reqObject, "publicKey");

			JSONObject collectionsObject = new JSONObject();

			List<ValouxCollectionModel> collectionModels = collectionService
					.getCollectionDetailAssociatedWithItem(itemId);

			JSONArray jArray = new JSONArray();

			if (collectionModels != null && collectionModels.size() > 0) {
				for (ValouxCollectionModel valouxCollectionModel : collectionModels) {
					JSONObject cObject = new JSONObject();
					cObject.put("cId", valouxCollectionModel.getVcid());
					cObject.put("cName", valouxCollectionModel.getCname());
					cObject.put("cDescription", valouxCollectionModel.getShortDescription());
					cObject.put("cStatus", valouxCollectionModel.getCollectionStatus());
					cObject.put("consumerPublicKey", valouxCollectionModel.getRkey());
					
					// Get images details of collection
					CollectionHelper.getImagesOfCollectionById(collectionService, cObject, valouxCollectionModel.getVcid());

					jArray.put(cObject);
				}
				collectionsObject.put("collections", jArray);
				json.put("resData", collectionsObject);
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
			// LOGGER.log(Level.ERROR, e.getMessage(), e);
			return Response.status(200).entity(json).build();
		} // end of try catch block
		LOGGER.info("CollectionWS : Enter Method getCollectionsListAssociatedWithItem");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get collection detail not associated with
	 * item 1. Check request for essential paparameters. 2. Converts
	 * <code>JSONObject</code> request to business objects. 3. Calls service
	 * method to add item in collection. 4. On success send back response for
	 * call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCollectionNotAssociatedWithItem.csv")
	public Response getCollectionNotAssociatedWithItem(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method getCollectionNotAssociatedWithItem");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject rqstObject = new JSONObject(reqPaparam);
			Integer itemId = JSONUtility.getSafeInteger(rqstObject, "itemId");
			String rKey = JSONUtility.getSafeString(rqstObject, "userPublicKey");
			String publicKey = JSONUtility.getSafeString(rqstObject, "publicKey");
			String requiredPaparams[] = { "itemId", "publicKey", "userPublicKey" };

			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, rqstObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.put("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				json.put("successMessage", CommonConstants.INFO_INCOMPLETE);
				return Response.status(200).entity(json.toString()).build();
			} else {
				
				List<ValouxCollectionModel> collectionModels = new ArrayList<ValouxCollectionModel>();
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				if (agentModel != null) {
					collectionModels = itemService.getAgentSharedCollectionNotInItem(publicKey, itemId, rKey);
				} else {
					collectionModels = collectionService.getCollectionDetailNoteAssociatedWithItem(rKey, itemId);
				}
				JSONArray jArray = new JSONArray();

				if (collectionModels != null && collectionModels.size() > 0) {
					for (ValouxCollectionModel valouxCollectionModel : collectionModels) {
						JSONObject cObject = new JSONObject();
						cObject.put("cId", valouxCollectionModel.getVcid());
						cObject.put("cName", valouxCollectionModel.getCname());
						cObject.put("cDescription", valouxCollectionModel.getShortDescription());
						cObject.put("cStatus", valouxCollectionModel.getCollectionStatus());
						// Get images details of collection
						CollectionHelper.getImagesOfCollectionById(collectionService, cObject, valouxCollectionModel.getVcid());

						jArray.put(cObject);
					}
					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("collectionData", jArray);
					jsonResponse.put("itemId", itemId);
					json.put("resData", jsonResponse);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", new JSONObject());
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.ITEMS_NOT_FOUND);
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
		LOGGER.info("CollectionWS : Exit Method getCollectionNotAssociatedWithItem");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to delete Item from collection: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to add item in
	 * collection. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteAppraisalFromCollection.csv")
	public Response deleteAppraisalFromCollection(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method deleteAppraisalFromCollection");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject jObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "collectionId", "appraisalId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, jObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				Integer appraisalId = JSONUtility.getSafeInteger(jObject, "appraisalId");
				Integer collectionId = JSONUtility.getSafeInteger(jObject, "collectionId");
				
//				ValouxCollectionModel collectionBean = collectionService.getCollectionDetailsById(collectionId);
//
//				if (collectionBean != null && collectionBean.getCollectionStatus() != null) {
//					if(collectionBean.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
//						json.put("resData", "");
//						json.putOpt("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED);
//						json.putOpt("sCode", CommonConstants.ERROR);
//						return Response.status(200).entity(json).build();
//					}
//				}

				Boolean isItemPresent = collectionService.deleteAppraisalFromCollection(appraisalId, collectionId);
				if (isItemPresent) {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_DELETE_FROM_COLLECTION);
					json.putOpt("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", "");
					json.putOpt("successMessage", CommonConstants.APPRAISAL_NOT_FOUND_IN_COLLECTION);
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
		LOGGER.info("CollectionWS : Exit Method deleteAppraisalFromCollection");
		return Response.status(200).entity(json).build();
	}

	/**
	 * This method receives request to get user item not in collection 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to add item in
	 * collection. 4. On success send back response for call back.
	 * 
	 * @throws Exception
	 *             : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getUserAppraisalsNotInCollection.csv")
	public Response getConsumerAppraisalNotInCollection(JSONObject formpaparam) throws Exception {
		LOGGER.info("CollectionWS : Enter Method getConsumerAppraisalNotInCollection");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject rqstObject = new JSONObject(reqPaparam);

			String requiredPaparams[] = { "publicKey", "collectionId","userPublicKey" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, rqstObject);

			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			} else {
				String rKey = JSONUtility.getSafeString(rqstObject, "userPublicKey");
				String publicKey = JSONUtility.getSafeString(rqstObject, "publicKey");
				Integer collectionId = JSONUtility.getSafeInteger(rqstObject, "collectionId");
				List<AppraisalModel> appraisalModelList = new ArrayList<AppraisalModel>();
				AgentModel agentModel = agentService.getAgentDetailByRelationKey(publicKey);
				if(agentModel!=null){
					appraisalModelList = itemService.getAgentSharedAppraisalsNotInCollection(publicKey, collectionId, rKey);
				}
				else{
				appraisalModelList = collectionService.getConsumerAppraisalsNotInCollection(rKey,
						collectionId);
				}
				JSONArray jArray = new JSONArray();
				JSONObject jObject = null;
				if (appraisalModelList != null && appraisalModelList.size() > 0) {
					for (AppraisalModel appraisalModel : appraisalModelList) {
						jObject = new JSONObject();
						jObject.put("name", appraisalModel.getName());
						jObject.put("shortDescription", appraisalModel.getShortDescription());
						jObject.put("appraisalStatus", appraisalModel.getaStatus());
						jObject.put("appraisalId", appraisalModel.getAppraisalId());

						// List of collection of Appraisals
						List<AppraisalCollectionModel> appraisalBeans = appraisalService
								.getAppraisalBeansByAppraisalId(appraisalModel.getAppraisalId());
						int count = 0;
						JSONArray collectionList = new JSONArray();
						if (appraisalBeans != null && appraisalBeans.size() > 0) {
							count = appraisalBeans.size();

							for (AppraisalCollectionModel appraisalCollectionBean : appraisalBeans) {
								ValouxCollectionModel collectionBean = appraisalService
										.getCollectionBeanByCollectionId(appraisalCollectionBean.getValouxCollectionId());

								if (collectionBean != null) {
									JSONObject respData = new JSONObject();
									// Get images details of collection
									CollectionHelper.getImagesOfCollectionById(collectionService, respData, appraisalCollectionBean.getValouxCollectionId());
									collectionList.put(respData);
								}
							}
						}
						jObject.put("collectionList", collectionList);

						jObject.put("appraisalCollectionCount", count);

						CollectionHelper.getItemsOfAppraisalsById(appraisalService, itemService, jObject, appraisalModel.getAppraisalId(), true);

						CollectionHelper.getItemsOfAppraisalsById(appraisalService, itemService, jObject, appraisalModel.getAppraisalId(), false);
						jArray.put(jObject);
					}

					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("appraisalData", jArray);

					// Get appraisal of collection
					CollectionHelper.getAppraisalIdsOfCollectionById(appraisalService, jsonResponse, collectionId);

					jsonResponse.put("collectionId", collectionId);
					json.put("resData", jsonResponse);
					json.put("sCode", CommonConstants.SUCCESS);
				} else {
					json.put("resData", new JSONObject());
					json.put("sCode", CommonConstants.SUCCESS);
					json.put("successMessage", CommonConstants.APPRAISAL_NOT_FOUND);
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
		LOGGER.info("CollectionWS : Exit Method getConsumerAppraisalNotInCollection");
		return Response.status(200).entity(json).build();
	}
	
	/**
	 * This method receives request to delete collection and its details: 1. Check
	 * request for essential paparameters. 2. Converts <code>JSONObject</code>
	 * request to business objects. 3. Calls service method to delete collection.
	 * 4. Prepare response and send it for call back.
	 * @throws Exception : error during the execution of operation
	 * 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteCollectionByCollectionId.csv")
	public Response deleteCollectionByCollectionId(JSONObject formpaparam) throws Exception {
		LOGGER.info("Enter deleteCollectionByCollectionId method of CollectionWS");
		JSONObject json = new JSONObject();
		try {
			String reqPaparam = formpaparam.getString("reqPaparam");
			JSONObject reqObject = new JSONObject(reqPaparam);
			String requiredPaparams[] = { "publicKey", "userPublicKey", "collectionId" };
			String missingPaparams = CommonUserUtility.checkJSONRequiredPaparameters(requiredPaparams, reqObject);
			if (CommonUserUtility.paparameterNullCheckStringObject(missingPaparams)) {
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.INFO_INCOMPLETE);
				json.putOpt("sCode", CommonConstants.INCOMPLETE);
				json.put("missingPaparams", "Paparameters missing - " + missingPaparams);
				return Response.status(200).entity(json.toString()).build();
			}
			Integer collectionId = JSONUtility.getSafeInteger(reqObject, "collectionId");
			String userPublicKey = JSONUtility.getSafeString(reqObject, "userPublicKey");
			ValouxCollectionModel collectionModel = collectionService.getCollectionDetailsById(collectionId);
			
			if(collectionModel == null){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.COLLECTION_NOT_FOUND);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			if(collectionModel != null && CommonUserUtility.paparameterNullCheckObject(collectionModel.getCollectionStatus()) && collectionModel.getCollectionStatus().equals(CommonConstants.APPRAISAL_STATUS_APPROVED)){
				json.put("resData", "");
				json.putOpt("errorMessage", CommonConstants.COLLECTION_ALREADY_APPRAISED_ONDELETE);
				json.putOpt("sCode", CommonConstants.ERROR);
				return Response.status(200).entity(json).build();
			}
			
			Boolean flag = collectionService.deleteCollectionAndAllDetails(collectionId, userPublicKey);
			if (flag) {
				json.put("resData", "");
				json.putOpt("successMessage", CommonConstants.COLLECTION_DELETED_SUCCESSFULLY);
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
		LOGGER.info("Exit deleteCollectionByCollectionId method of CollectionWS");
		return Response.status(200).entity(json).build();
	}

}
